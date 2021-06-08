package com.ctection.cpermanager;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        PermissionManager manager = new PermissionManager("127.0.0.1", "3306", "local", "root", "", "setId");
        manager.getSet().addPermission(new Permission("test", "help"));
    }
}
