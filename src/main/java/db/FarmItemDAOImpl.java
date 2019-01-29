package main.java.db;

import main.java.model.FarmItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmItemDAOImpl implements FarmItemDAO {
    private ConnectionPool dataSource;

    public FarmItemDAOImpl() {
        dataSource = ConnectionPool.getInstance();
    }

    @Override
    public List<FarmItem> findAll() {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<FarmItem> farmItemList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM FarmItem");
            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                farmItemList.add(this.getFarmItemFromResultSet(resultSet));
            }

            return farmItemList;
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
    public List<FarmItem> findApprovedOrdered(String orderByColumns, String searchTerm, String termLike) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<FarmItem> farmItemList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT * FROM FarmItem WHERE IsApproved=TRUE AND " +
                        searchTerm + " LIKE '%" + termLike + "%' ORDER BY " + orderByColumns);
            } else {
                preStatement = connection.prepareStatement("SELECT * FROM FarmItem WHERE IsApproved=TRUE " +
                        "ORDER BY " + orderByColumns);
            }

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                farmItemList.add(this.getFarmItemFromResultSet(resultSet));
            }

            return farmItemList;
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
    public List<FarmItem> findPendingOrdered(String orderByColumns) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<FarmItem> farmItemList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("SELECT * FROM FarmItem WHERE IsApproved=FALSE " +
                        "ORDER BY " + orderByColumns);


            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                farmItemList.add(this.getFarmItemFromResultSet(resultSet));
            }

            return farmItemList;
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
    public boolean insertFarmItem(FarmItem farmItem) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("INSERT INTO FarmItem (Name, IsApproved, Type) " +
                    "VALUES (?, ?, ?)");

            preStatement.setString(1, farmItem.getName());
            preStatement.setBoolean(2, farmItem.getIsApproved());
            preStatement.setString(3, farmItem.getItemType().name());

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
    public boolean updateFarmItem(FarmItem farmItem) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("UPDATE FarmItem SET Name=?, IsApproved=?, Type=? WHERE Name=?");

            preStatement.setString(1, farmItem.getName());
            preStatement.setBoolean(2, farmItem.getIsApproved());
            preStatement.setString(3, farmItem.getItemType().name());
            preStatement.setString(4, farmItem.getName());

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
    public boolean deleteFarmItem(FarmItem farmItem) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("DELETE FROM FarmItem WHERE Name=?");

            preStatement.setString(1, farmItem.getName());

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

    private FarmItem getFarmItemFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("Name");
        Boolean isApproved = resultSet.getBoolean("IsApproved");
        FarmItem.FarmItemType farmItemType= FarmItem.FarmItemType.valueOf(resultSet.getString("Type"));

        return new FarmItem(name, isApproved, farmItemType);
    }
}
