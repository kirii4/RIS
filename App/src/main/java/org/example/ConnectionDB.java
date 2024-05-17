package org.example;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionDB {
    private static ConnectionDB instance;
    protected Connection connect;
    protected Statement statement;
    private ResultSet resultSet;
    ArrayList<String[]> masResult;

    public ConnectionDB() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProductsDB", "postgres", "150720");
            this.statement = this.connect.createStatement();
        } catch (ClassNotFoundException | SQLException SQLException) {
            System.out.println("Problem with JDBC Driver");
            SQLException.printStackTrace();
        }
    }

    public Connection getConnect(){
        return connect;
    }

    public void setResultSet(String str) {
        try {
            this.resultSet = this.statement.executeQuery(str);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void execute(String query) {
        try {
            this.statement.execute(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static synchronized ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }

        return instance;
    }

    public ArrayList<String[]> getArrayResult(String str) {
        this.masResult = new ArrayList();

        try {
            this.resultSet = this.statement.executeQuery(str);
            int count = this.resultSet.getMetaData().getColumnCount();

            while(this.resultSet.next()) {
                String[] arrayString = new String[count];

                for(int i = 1; i <= count; ++i) {
                    arrayString[i - 1] = this.resultSet.getString(i);
                }

                this.masResult.add(arrayString);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return this.masResult;
    }

    public void close() {
        try {
            this.connect.close();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }
    public Connection getConnection() {
        return connect;
    }
}
