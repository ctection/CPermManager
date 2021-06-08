package com.ctection.cpermanager.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class SQL {
    private String HOST = "";
    private String PORT = "";
    private String DATABASE = "";
    private String USER = "";
    private String PASSWORD = "";
    private Connection con;
    public SQL(String host, String port, String database, String user, String password){
        this.HOST = host;
        this.PORT = port;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.con = DriverManager.getConnection(
                    "jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE + "?autoReconnect=true" +
                            "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin"
                            , this.USER, this.PASSWORD
            );
            System.out.println("[CPermManager] [SQL] Successfully connected!");
        } catch (SQLException e) {
            System.err.println("[CPermManager] [SQL] Connection failed:\n");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(String qry) throws SQLException {

        try {
            if (!this.con.isValid(2))
                connect();
            Statement st = this.con.createStatement();
            st.executeUpdate(qry);
            st.close();
            System.out.println("[CPermManager] [SQL] Successfully updated!");

        } catch (SQLException e) {
            System.err.println("[CPermManager] [SQL] Update failed:\n");
            e.printStackTrace();
        }


    }
    public String resultSet(String qry) {

        try {
            if (!this.con.isValid(2))

                connect();
            Statement st = this.con.createStatement();
            ResultSet rs = st.executeQuery(qry);
            if (rs.next()) {
                String s = rs.getString(1);

                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
    public ResultSet resultSetMeta(String qry) {
        try {
            Statement st = this.con.createStatement();
            return st.executeQuery(qry);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public void disconnect() {
        try {
            this.con.close();
            System.out.println("[CPermManager] [SQL] Connection closed.");
        } catch (SQLException e) {
            System.err.println("[CPermManager] [SQL] Connection closing caused an error:");
            e.printStackTrace();
        }

    }
}

