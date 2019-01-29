package main.java.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {
    public static void closeDBObject(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            /* ignored */
        }
    }

    public static void closeDBObject(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (Exception e) {
            /* ignored */
        }
    }

    public static void closeDBObject(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            /* ignored */
        }
    }
}
