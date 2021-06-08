package com.ctection.cpermanager;

import java.util.ArrayList;

public class PermissionSet {

    private ArrayList<Permission> permissions;

    public PermissionSet() {
        this.permissions = new ArrayList<>();
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }
    public void addPermission(Permission permission) {
        permissions.add(permission);
    }
    public void removePermission(Permission permission) {
        permissions.remove(permission);
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
}
