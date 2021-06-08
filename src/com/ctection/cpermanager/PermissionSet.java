package com.ctection.cpermanager;

import com.ctection.cpermanager.util.SQL;

import java.sql.SQLException;
import java.util.ArrayList;

public class PermissionSet {

    private ArrayList<Permission> permissions;
    private String setId;
    public PermissionSet(String setId) {
        this.permissions = new ArrayList<>();
        this.setId = setId;
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }
    public void addPermission(Permission permission) {
        permissions.add(permission);
        updatePermissionTable();
    }
    public void removePermission(Permission permission) {
        permissions.remove(permission);
        updatePermissionTable();
    }

    public Permission getById(String permId) {
        if (permissions.isEmpty()) {
            return null;
        }
        for (Permission permission : permissions) {
            if (permission.getId().equals(permId)) {
                return permission;
            }
        }
        return null;
    }


    private void updatePermissionTable() {
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        ArrayList<String> permNames = new ArrayList<>();
        for (Permission p : permissions) {
            permNames.add(p.getId());
        }
        try {
            sql.update("UPDATE CPermPermList set perms='" + String.join(" ",permNames) + "' WHERE setid='" + setId + "';");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
