package main.java.edu.ingsoft.sonrisas.perfectas.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

    private static Connection connection;

    private DataBaseConnection() {
    }

    public static Connection getConnectionDataBase() throws Exception {

        if (connection == null || connection.isClosed()) {

            connection = DriverManager.getConnection(
                    Credentials.URL_MYSQL_DB,
                    Credentials.USER_DB,
                    Credentials.PASS_DB
            );
        }

        return connection;
    }
}