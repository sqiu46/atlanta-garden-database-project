package main.java.db;

import main.java.model.FarmItem;
import main.java.model.Has;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HasDAOImpl implements HasDAO{
    private ConnectionPool dataSource;

    public HasDAOImpl() {
        dataSource = ConnectionPool.getInstance();
    }

    @Override
    public List<Has> findAnimalsByProperty(String propertyID) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<Has> hasList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Has WHERE ItemName in " +
                    "(SELECT Name FROM FarmItem WHERE IsApproved = TRUE and Type='ANIMAL' ) AND PropertyID=?");
            preStatement.setString(1, propertyID);

            resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                Has has = this.getHasFromResultSet(resultSet);
                hasList.add(has);
            }

            return hasList;
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
    public List<Has> findCropsByProperty(String propertyID) {
        PreparedStatement preStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<Has> hasList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Has WHERE ItemName in " +
                    "(SELECT Name FROM FarmItem WHERE IsApproved=TRUE and Type!='ANIMAL' ) AND PropertyID=?");
            preStatement.setString(1, propertyID);

            resultSet = preStatement.executeQuery();

            while (resultSet.next()) {
                Has has = this.getHasFromResultSet(resultSet);
                hasList.add(has);
            }

            return hasList;
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
    public boolean insertHas(Has has) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("INSERT INTO Has (PropertyID, ItemName) " +
                    "VALUES (?, ?)");

            preStatement.setInt(1, has.getPropertyID());
            preStatement.setString(2, has.getItemName());

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
    public boolean updateHas(Has has) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("UPDATE Has SET PropertyID=?, ItemName=? WHERE PropertyId=? " +
                    "AND ItemName=?");

            preStatement.setInt(1, has.getPropertyID());
            preStatement.setString(2, has.getItemName());
            preStatement.setInt(3, has.getPropertyID());
            preStatement.setString(4, has.getItemName());

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
    public boolean deleteHas(Has has) {
        PreparedStatement preStatement = null;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            preStatement = connection.prepareStatement("DELETE FROM Has WHERE PropertyID=? AND ItemName=?");

            preStatement.setInt(1, has.getPropertyID());
            preStatement.setString(2, has.getItemName());

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

    private Has getHasFromResultSet(ResultSet resultSet) throws SQLException {
        int propertyID = resultSet.getInt("PropertyID");
        String itemName = resultSet.getString("ItemName");

        return new Has(propertyID, itemName);
    }
}
