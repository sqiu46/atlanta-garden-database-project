package main.java.db;

import main.java.model.FarmItem;
import main.java.model.Visit;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VisitDAOImpl implements VisitDAO {
    private ConnectionPool dataSource;

    public VisitDAOImpl() {
        dataSource = ConnectionPool.getInstance();
    }

    @Override
    public List<Visit> findAll() {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<Visit> visitList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preStatement =connection.prepareStatement("SELECT * FROM Visit");
            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                visitList.add(this.getVisitFromResultSet(resultSet));
            }

            return visitList;
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
    public List<Visit> findByProperty(int propertyID) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<Visit> visitList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Visit WHERE PropertyID=?");
            preStatement.setInt(1, propertyID);

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                visitList.add(this.getVisitFromResultSet(resultSet));
            }

            return visitList;
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
    public List<Visit> findByUsernameOrdered(String username, String orderByColumns, String searchTerm, String termLike) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<Visit> visitList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT * FROM Visit WHERE Username=? AND " +
                        searchTerm + " LIKE '%" + termLike + "%' ORDER BY " + orderByColumns);
            } else {
                preStatement = connection.prepareStatement("SELECT * FROM Visit WHERE Username=? " +
                        "ORDER BY " + orderByColumns);
            }

            preStatement.setString(1, username);
            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                visitList.add(this.getVisitFromResultSet(resultSet));
            }

            return visitList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
            DBUtil.closeDBObject(resultSet);
        }

        return null;
    }

    @Override
    public boolean updateVisit(Visit visit) {
        Connection connection = null;
        PreparedStatement preStatement = null;

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("UPDATE Visit SET Username=?, PropertyID=?, VisitDate=?, " +
                    "Rating=? WHERE Username=? AND PropertyID=?");

            preStatement.setString(1, visit.getUsername());
            preStatement.setInt(2, visit.getPropertyID());
            preStatement.setTimestamp(3, visit.getVisitDate());
            preStatement.setInt(4, visit.getRating());
            preStatement.setString(5, visit.getUsername());
            preStatement.setInt(6, visit.getPropertyID());

            int flag = preStatement.executeUpdate();

            if (flag == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return false;
    }

    @Override
    public boolean insertVisit(Visit visit) {
        Connection connection = null;
        PreparedStatement preStatement = null;

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("INSERT INTO Visit (Username, PropertyID, VisitDate, " +
                    "Rating) VALUES (?, ?, ?, ?)");

            preStatement.setString(1, visit.getUsername());
            preStatement.setInt(2, visit.getPropertyID());
            preStatement.setTimestamp(3, visit.getVisitDate());
            preStatement.setInt(4, visit.getRating());

            int flag = preStatement.executeUpdate();

            if (flag == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return false;
    }

    @Override
    public boolean deleteVisit(Visit visit) {
        Connection connection = null;
        PreparedStatement preStatement = null;

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("DELETE FROM Visit WHERE Username=? AND PropertyID=?");

            preStatement.setString(1, visit.getUsername());
            preStatement.setInt(2, visit.getPropertyID());

            int flag = preStatement.executeUpdate();

            if (flag == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return false;
    }

    private Visit getVisitFromResultSet(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("Username");
        int property = resultSet.getInt("PropertyID");
        Timestamp time = resultSet.getTimestamp("VisitDate");
        int rating = resultSet.getInt("Rating");

        return new Visit(username, property, time, rating);
    }
}
