package com.ctection.cpermanager.util;

public class SQLFactory {
    private String host;
    private String port;
    private String db;
    private String usr;
    private String pw;

    public SQLFactory(String host, String port, String db, String usr, String pw) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.usr = usr;
        this.pw = pw;
    }

    public SQL get() {
        SQL sql = new SQL(host, port, db, usr, pw);
        sql.connect();
        return sql;
    }
}
