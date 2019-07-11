package org.runnerer.redis.database;

import java.sql.*;

public class MySQL
{

    public static MySQL Instance;
    protected static Connection connection;

    public MySQL()
    {
        Instance = this;
    }

    public static Connection openConnection() throws SQLException, ClassNotFoundException
    {
        if (checkConnection())
        {
            return connection;
        }
        Class.forName("com.mysql.jdbc.Driver");

        connection = DriverManager
                .getConnection("db.qubehost.com" + "/" + "Data?zeroDateTimeBehavior=convertToNull&useUnicode=true&useFastDateParsing=false&characterEncoding=UTF-8", "REMOVED", "REMOVED");
        return connection;
    }

    public static boolean checkConnection() throws SQLException
    {
        return connection != null && !connection.isClosed();
    }

    public static Connection getConnection()
    {
        return connection;
    }

    public static ResultSet querySQL(String query) throws SQLException, ClassNotFoundException
    {
        if (!checkConnection())
        {
            openConnection();
        }

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery(query);

        return result;
    }

    public static int updateSQL(String query) throws SQLException, ClassNotFoundException
    {
        if (!checkConnection())
        {
            openConnection();
        }

        Statement statement = connection.createStatement();

        int result = statement.executeUpdate(query);

        return result;
    }

    public boolean closeConnection() throws SQLException
    {
        if (connection == null)
        {
            return false;
        }
        connection.close();
        return true;
    }
}
