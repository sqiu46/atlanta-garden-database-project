package main.java.db;

import main.java.model.VisitorView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitorViewServiceImpl implements  VisitorViewService {
    private ConnectionPool connectionPool;

    public VisitorViewServiceImpl() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public List<VisitorView> findAllOrdered(String orderByColumns, String searchTerm, String termLike) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        List<VisitorView> visitors = new ArrayList<>();

        try {
            connection = connectionPool.getConnection();

            if (termLike != null) {
                preStatement = connection.prepareStatement("SELECT User.Username AS Username, Email, Test.Count AS LoggedVisits " +
                        "FROM User, (SELECT Username, COUNT(Username) AS Count " +
                        "from Visit " +
                        "group by Visit.Username) AS Test WHERE Test.Username = User.Username AND " + searchTerm + " LIKE '%" + termLike +
                        "%' ORDER BY " + orderByColumns);
            } else {
                preStatement = connection.prepareStatement("SELECT User.Username AS Username, Email, Test.Count AS LoggedVisits " +
                        "FROM User, (SELECT Username, COUNT(Username) AS Count " +
                        "from Visit " +
                        "group by Visit.Username) AS Test WHERE Test.Username = User.Username ORDER BY " + orderByColumns);
            }

            resultSet = preStatement.executeQuery();

            while(resultSet.next()) {
                visitors.add(this.getVisitorViewFromResultSet(resultSet));
            }

            return visitors;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBObject(resultSet);
            DBUtil.closeDBObject(preStatement);
            DBUtil.closeDBObject(connection);
        }

        return null;
    }

    private VisitorView getVisitorViewFromResultSet(ResultSet resultSet) throws SQLException {
        String username = resultSet.getString("Username");
        String email = resultSet.getString("Email");
        int loggedVisits = resultSet.getInt("LoggedVisits");

        return new VisitorView(username, email, loggedVisits);
    }
}
