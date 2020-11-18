package com.example;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class jdbcDemo {
    private static final String INSERT = "insert into user(id, name) values(1,'name')";
    private static final String SELECT = "select * from user where id = 1";
    private static final String UPDATE = "update user set name='update name' where id =1";
    private static final String DELETE = "delete from user where id=1";

    public void jdbcOriginalInterfaceDemo() throws ClassNotFoundException, SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute(INSERT);
        statement.execute(SELECT);
        statement.execute(UPDATE);
        statement.execute(DELETE);
    }

    public void jdbcBatchAndPrepareStatementDemo() throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into user(id,name) values (?,?)");
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2,"name1");
        preparedStatement.addBatch();

        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2,"name2");
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:8089/test";
        return DriverManager.getConnection(url);
    }

}
