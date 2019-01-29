package main.java.db;

import main.java.model.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDAOImpl implements UserDAO{
    private ConnectionPool dataSource;

    public UserDAOImpl() {
        dataSource = ConnectionPool.getInstance();
    }

    @Override
    public User findByUsername(String username) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM User WHERE Username=?");
            preStatement.setString(1, username);

            resultSet = preStatement.executeQuery();

            if (resultSet.next()) {
                return this.getUserFromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //OPEN ERROR WINDOW
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    @Override
    public User findByEmail(String email) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("SELECT * FROM User WHERE Email=?");
            preStatement.setString(1, email);

            resultSet = preStatement.executeQuery();

            if (resultSet.next()) {
                return this.getUserFromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //OPEN ERROR WINDOW
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            preStatement = connection.prepareStatement("SELECT * FROM User WHERE Username=? AND Password=?");
            preStatement.setString(1, username);
            preStatement.setString(2, password);

            resultSet = preStatement.executeQuery();

            if (resultSet.next()) {
                return this.getUserFromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //OPEN ERROR WINDOW
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    @Override
    public List<User> findByType(User.UserType userType) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("SELECT * FROM User WHERE UserType=?");
            preStatement.setString(1, userType.name());

            resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                userList.add(this.getUserFromResultSet(resultSet));
            }

            return userList;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //OPEN ERROR WINDOW
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        PreparedStatement preStatement = null;
        Connection connection = null;

        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM User");

            resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                users.add(this.getUserFromResultSet(resultSet));
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //OPEN ERROR WINDOW
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    @Override
    public List<User> findByUsernameOrEmail(String username, String email) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("SELECT * FROM User WHERE Username=? OR Email=?");
            preStatement.setString(1, username);
            preStatement.setString(2, email);

            resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                users.add(this.getUserFromResultSet(resultSet));
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            //OPEN ERROR WINDOW
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    @Override
    public List<User> findAllUsersOfTypeOrdered(User.UserType userType, String orderByColumn, String searchTerm,
                                                String termLike, boolean isAscending) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<User> properties = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            String order = (isAscending) ? "ASC" : "DESC";

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT * FROM User WHERE UserType=? AND " + searchTerm + " LIKE '%" + termLike + "%' ORDER BY " + orderByColumn + " " + order);
            } else {
                preStatement = connection.prepareStatement("SELECT * FROM User WHERE UserType=? ORDER BY " + orderByColumn + " " + order);
            }

            preStatement.setString(1, userType.name());
            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getUserFromResultSet(resultSet));
            }

            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    @Override
    public boolean insertUser(User user) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("INSERT INTO User (Username, Email, Password, UserType) " +
                    "VALUES (?, ?, ?, ?)");

            preStatement.setString(1, user.getUsername());
            preStatement.setString(2, user.getEmail());
            preStatement.setString(3, user.getPassword());
            preStatement.setString(4, user.getUserType().name());

            int flag = preStatement.executeUpdate();

            if (flag == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("UPDATE User SET Username=?, Email=?, Password=?, UserType=? " +
                    "WHERE Username=?");

            preStatement.setString(1, user.getUsername());
            preStatement.setString(2, user.getEmail());
            preStatement.setString(3, user.getPassword());
            preStatement.setString(4, user.getUserType().name());
            preStatement.setString(5, user.getUsername());

            int flag = preStatement.executeUpdate();

            if (flag == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("DELETE FROM User WHERE Username=?");

            preStatement.setString(1, user.getUsername());

            int flag = preStatement.executeUpdate();

            if (flag == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return false;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("Username");
        String email = resultSet.getString("Email");
        String password = resultSet.getString("Password");
        User.UserType userType = User.UserType.valueOf(resultSet.getString("UserType"));

        return new User(username, email, password, userType);
    }
}
