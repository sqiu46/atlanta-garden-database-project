package main.java.db;

import main.java.model.OwnerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnerViewServiceImpl implements OwnerViewService {
    private ConnectionPool connectionPool;

    public OwnerViewServiceImpl() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public List<OwnerView> findAllOrdered(String orderByColumns, String searchTerm, String termLike) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<OwnerView> owners = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("Select User.Username AS Username, Email, " +
                        "Test.PropertyCount FROM User, (Select Owner, COUNT(Name) AS PropertyCount " +
                        "FROM Property GROUP BY Owner) AS Test WHERE Test.Owner = User.Username AND " + searchTerm +
                        " LIKE '%" + termLike + "%' ORDER BY " + orderByColumns);
            } else {
                preStatement = connection.prepareStatement("Select User.Username AS Username, Email, " +
                        "Test.PropertyCount FROM User, (Select Owner, COUNT(Name) AS PropertyCount " +
                        "FROM Property GROUP BY Owner) AS Test WHERE Test.Owner = User.Username ORDER BY " +
                        orderByColumns);
            }

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                owners.add(this.getOwnerViewFromResultSet(resultSet));
            }

            return owners;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    private OwnerView getOwnerViewFromResultSet(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("Username");
        String email = resultSet.getString("Email");
        int propertyCount = resultSet.getInt("PropertyCount");

        return new OwnerView(username, email, propertyCount);
    }
}
