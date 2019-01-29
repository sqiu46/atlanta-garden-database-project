package main.java.db;

import main.java.model.Property;
import main.java.model.PropertyView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyViewServiceImpl implements PropertyViewService {
    private ConnectionPool connectionPool;

    public PropertyViewServiceImpl() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public List<PropertyView> findByOwnerOrdered(String orderByColumns, String searchTerm, String termLike, String owner) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<PropertyView> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT Name, Street, City, Zip, Size, PropertyType, " +
                        "IsPublic, IsCommercial, ID, IF(ApprovedBy IS NULL, false, true) AS isValid, Visits, " +
                        "Avg_Rating FROM Property, VISIT_SUMMARY WHERE PropertyID = ID AND Owner=? AND " +
                        searchTerm + " LIKE '%" + termLike + "%' ORDER BY " + orderByColumns);
            } else {
                preStatement = connection.prepareStatement("SELECT Name, Street, City, Zip, Size, PropertyType, " +
                        "IsPublic, IsCommercial, ID, IF(ApprovedBy IS NULL, false, true) AS isValid, Visits, " +
                        "Avg_Rating FROM Property, VISIT_SUMMARY WHERE PropertyID = ID AND Owner=? " +
                        "ORDER BY " + orderByColumns);
            }

            preStatement.setString(1, owner);
            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyViewFromResultSet(resultSet, true, false));
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
    public List<PropertyView> findAllConfirmedOrdered(String orderByColumns, String searchTerm, String termLike) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<PropertyView> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT P.Name, P.Street, P.City, P.Zip, P.Size, " +
                        "P.PropertyType, P.isPublic, P.isCommercial, P.ID, V.Visits, V.Avg_Rating, P.ApprovedBy " +
                        "FROM Property AS P, VISIT_SUMMARY AS V WHERE P.ID = V.PropertyID AND " +
                        "P.ApprovedBy IS NOT NULL AND " + searchTerm + " LIKE '%" + termLike + "%' ORDER BY " +
                        orderByColumns);
            } else {
                preStatement = connection.prepareStatement("SELECT P.Name, P.Street, P.City, P.Zip, P.Size, " +
                        "P.PropertyType, P.isPublic, P.isCommercial, P.ID, V.Visits, V.Avg_Rating, P.ApprovedBy " +
                        "FROM Property AS P, VISIT_SUMMARY AS V WHERE P.ID = V.PropertyID AND " +
                        "P.ApprovedBy IS NOT NULL ORDER BY " + orderByColumns);
            }

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyViewFromResultSet(resultSet, false, true));
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
    public List<PropertyView> findAllOtherConfirmedOrdered(String orderByColumns, String searchTerm, String termLike,
                                                           String owner) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<PropertyView> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT P.Name, P.Street, P.City, P.Zip, P.Size, " +
                        "P.PropertyType, P.isPublic, P.isCommercial, P.ID, V.Visits, V.Avg_Rating, P.ApprovedBy " +
                        "FROM Property AS P, VISIT_SUMMARY AS V WHERE P.ID = V.PropertyID AND " +
                        "P.ApprovedBy IS NOT NULL AND Owner!=? AND " + searchTerm + " LIKE '%" + termLike + "%' ORDER BY " +
                        orderByColumns);
            } else {
                preStatement = connection.prepareStatement("SELECT P.Name, P.Street, P.City, P.Zip, P.Size, " +
                        "P.PropertyType, P.isPublic, P.isCommercial, P.ID, V.Visits, V.Avg_Rating, P.ApprovedBy " +
                        "FROM Property AS P, VISIT_SUMMARY AS V WHERE P.ID = V.PropertyID AND " +
                        "P.ApprovedBy IS NOT NULL AND Owner!=? ORDER BY " + orderByColumns);
            }

            preStatement.setString(1, owner);
            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyViewFromResultSet(resultSet, false, false));
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

    private PropertyView getPropertyViewFromResultSet(ResultSet resultSet, boolean isValidShown, boolean isApproverShown) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("Name");
        float size = resultSet.getFloat("Size");
        boolean isCommercial = resultSet.getBoolean("IsCommercial");
        boolean isPublic = resultSet.getBoolean("IsPublic");
        String street = resultSet.getString("Street");
        String city = resultSet.getString("City");
        int zipcode = resultSet.getInt("Zip");
        Property.PropertyType propertyType = Property.PropertyType.valueOf(resultSet.getString(
                "PropertyType"));
        boolean isValid;
        String approver;
        String owner;

        if (isValidShown) {
            isValid = resultSet.getBoolean("isValid");
        } else {
            isValid = true;
        }

        if (isApproverShown) {
            approver = resultSet.getString("ApprovedBy");
        } else {
            approver = "";
        }

        int visits = resultSet.getInt("Visits");
        int averageRating = resultSet.getInt("Avg_Rating");

        return new PropertyView(id, name, size, isCommercial, isPublic, street, city, zipcode, propertyType, isValid, visits, averageRating, approver);
    }
}
