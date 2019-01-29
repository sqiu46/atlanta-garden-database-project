package main.java.db;

import main.java.model.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAOImpl implements PropertyDAO {
    private ConnectionPool connectionPool;

    public PropertyDAOImpl() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public List<Property> findAll() {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<Property> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Property");
            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyFromResultSet(resultSet));
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
    public List<Property> findByOwner(String owner) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<Property> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Property WHERE Owner=?");
            preStatement.setString(1, owner);

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyFromResultSet(resultSet));
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
    public List<Property> findByType(Property.PropertyType propertyType) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<Property> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Property WHERE PropertyType=?");
            preStatement.setString(1, propertyType.name());

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyFromResultSet(resultSet));
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
    public List<Property> findByNameLike(String nameLike) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<Property> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Property WHERE Name LIKE ?");
            preStatement.setString(1, ("%" + nameLike + "%"));

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyFromResultSet(resultSet));
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
    public List<Property> findUnapprovedOrdered(String orderByColumns, String searchTerm,
                                                String termLike) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<Property> properties = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT * FROM Property WHERE ApprovedBy IS NULL AND " +
                        searchTerm + " LIKE '%" + termLike + "%' ORDER BY " + orderByColumns);
            } else {
                preStatement = connection.prepareStatement("SELECT * FROM Property WHERE ApprovedBy IS NULL " +
                        "ORDER BY " + orderByColumns);
            }

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                properties.add(this.getPropertyFromResultSet(resultSet));
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
    public Property findByName(String propertyName) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Property WHERE Name=?");
            preStatement.setString(1, propertyName);

            resultSet = preStatement.executeQuery();

            if (resultSet.next()) {
                return this.getPropertyFromResultSet(resultSet);
            }
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
    public Property findByID(int propertyID) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("SELECT * FROM Property WHERE ID=?");
            preStatement.setInt(1, propertyID);

            resultSet = preStatement.executeQuery();

            if (resultSet.next()) {
                return this.getPropertyFromResultSet(resultSet);
            }
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
    public boolean insertProperty(Property property) {
        Connection connection = null;
        PreparedStatement preStatement = null;

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("INSERT INTO Property (ID, Name, Size, IsCommercial, " +
                    "IsPublic, Street, City, Zip, PropertyType, Owner, ApprovedBy) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preStatement.setInt(1, property.getId());
            preStatement.setString(2, property.getName());
            preStatement.setFloat(3, property.getSize());
            preStatement.setBoolean(4, property.getIsCommercial());
            preStatement.setBoolean(5, property.getIsPublic());
            preStatement.setString(6, property.getStreet());
            preStatement.setString(7, property.getCity());
            preStatement.setInt(8, property.getZipcode());
            preStatement.setString(9, property.getPropertyType().name());
            preStatement.setString(10, property.getOwnerUsername());
            preStatement.setString(11, property.getApproverUsername());

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
    public boolean updateProperty(Property property) {
        Connection connection = null;
        PreparedStatement preStatement = null;

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("UPDATE Property SET ID=?, Name=?, Size=?, IsCommercial=?, " +
                    "IsPublic=?, Street=?, City=?, Zip=?, PropertyType=?, Owner=?, ApprovedBy=? WHERE ID=?");

            preStatement.setInt(1, property.getId());
            preStatement.setString(2, property.getName());
            preStatement.setFloat(3, property.getSize());
            preStatement.setBoolean(4, property.getIsCommercial());
            preStatement.setBoolean(5, property.getIsPublic());
            preStatement.setString(6, property.getStreet());
            preStatement.setString(7, property.getCity());
            preStatement.setInt(8, property.getZipcode());
            preStatement.setString(9, property.getPropertyType().name());
            preStatement.setString(10, property.getOwnerUsername());
            preStatement.setString(11, property.getApproverUsername());
            preStatement.setInt(12, property.getId());

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
    public boolean deleteProperty(Property property) {
        Connection connection = null;
        PreparedStatement preStatement = null;

        try {
            connection = connectionPool.getConnection();
            preStatement = connection.prepareStatement("DELETE FROM Property WHERE ID=?");

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

    private Property getPropertyFromResultSet(ResultSet resultSet) throws SQLException {
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
        String owner = resultSet.getString("Owner");
        String approvedBy = resultSet.getString("ApprovedBy");

        return new Property(id, name, size, isCommercial, isPublic, street, city, zipcode, propertyType, owner,
                approvedBy);
    }
}
