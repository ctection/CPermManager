package com.ctection.cpermanager;

import com.ctection.cpermanager.util.SQL;
import com.ctection.cpermanager.util.SQLFactory;
import com.sun.javaws.exceptions.InvalidArgumentException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class PermissionManager {

    private final SQLFactory sqlFactory;
    private static PermissionManager manager;
    private final PermissionSet set;
    private final String setId;
    public PermissionManager(String sqlHost, String sqlPort, String sqlDB, String sqlUsr, String sqlPw, String setId) throws SQLException {
        this.sqlFactory = new SQLFactory(sqlHost, sqlPort, sqlDB, sqlUsr, sqlPw);
        this.manager = this;
        this.set = new PermissionSet(setId);
        this.setId = setId;
        SQL sql = sqlFactory.get();
        sql.update("CREATE TABLE IF NOT EXISTS CPermPermList(" +
                "setid VARCHAR(50) UNIQUE NOT NULL," +
                "perms TEXT(4294967295)" +
                ");");
        sql.update("CREATE TABLE IF NOT EXISTS CPermUserPerms(" +
                "id SERIAL PRIMARY KEY," +
                "setid VARCHAR(50) UNIQUE NOL NULL," +
                "guild_id VARCHAR (50) NOT NULL," +
                "user_id VARCHAR (50) NOT NULL, " +
                "perms TEXT (4294967295) NOT NULL, " +
                "FOREIGN KEY (`setid`) REFERENCES `CPermPermList` (`setid`)" +
                ");");
        sql.update("CREATE TABLE IF NOT EXISTS CPermRolePerms(" +
                "id SERIAL PRIMARY KEY," +
                "setid VARCHAR(50) UNIQUE NOL NULL," +
                "guild_id VARCHAR (50) NOT NULL," +
                "role_id VARCHAR (50) NOT NULL, " +
                "perms TEXT (4294967295) NOT NULL," +
                "FOREIGN KEY (`setid`) REFERENCES `CPermPermList` (`setid`)" +
                ");");
        String result = sql.resultSet("SELECT perms from CPermPermList WHERE setid='" + set + "';");
        for (String perm : result.split(" ")) {
            try {
                this.set.addPermission(new Permission(perm));
            } catch (InvalidArgumentException e) {
                System.err.println("There is an invalid permission in the PermissionSet:\n" +
                        "Permission: " + perm + "\nSet ID: " + setId);
                e.printStackTrace();
            }
        }
        sql.disconnect();
    }

    protected SQLFactory getSqlFactory() {
        return sqlFactory;
    }
    public static PermissionManager getInstance() {
        return manager;
    }
    public PermissionSet getSet() {
        return set;
    }

    public void addPermissionToSet(Permission... permissions) {
        for (Permission p : permissions) {
            this.set.addPermission(p);
        }
    }
    public void removePermissionFromSet(Permission... permissions) {
        for (Permission p : permissions) {
            this.set.removePermission(p);
        }
    }
    public ArrayList<Permission> getAvailablePermissions() {
        return this.set.getPermissions();
    }
    public Permission getById(String id) {
        return this.set.getById(id);
    }

    public void removePermission(Member member, Permission... permissions) {
        for (Permission p: permissions) {
            p.removeFromUser(this.setId, member.getGuild().getId(), member.getId());
        }
    }
    public void removePermission(Role role, Permission... permissions) {
        for (Permission p: permissions
        ) {
            p.removeFromRole(this.setId, role.getGuild().getId(), role.getId());
        }
    }

    public void setPermissions(Member member, Permission... permissions) {
        ArrayList<Permission> permissionArrayList = new ArrayList<>(Arrays.asList(permissions));
            Permission.setMemberPermissions(this.setId, member.getGuild().getId(), member.getId(), permissionArrayList);
    }
    public void setPermissions(Role role, Permission... permissions) {
        ArrayList<Permission> permissionArrayList = new ArrayList<Permission>(Arrays.asList(permissions));
        Permission.setRolePermissions(this.setId, role.getGuild().getId(), role.getId(), permissionArrayList);
    }

    public void addPermission(Member member, Permission... permissions) {
        ArrayList<Permission> permissionArrayList = new ArrayList<>(Arrays.asList(permissions));
        Permission.addMemberPermission(member.getGuild().getId(), member.getId(), this.setId, permissionArrayList);
    }
    public void addPermission(Role role, Permission... permissions) {
        ArrayList<Permission> permissionArrayList = new ArrayList<>(Arrays.asList(permissions));
        Permission.addRolePermission(role.getGuild().getId(), role.getId(), this.setId, permissionArrayList);
    }

    public String getPermissionsAsData(Member member,DataType dataType) {
        switch (dataType) {
            case JSON:
                return Permission.getMemberPermissionsAsJson(member.getGuild().getId(), member.getId(), this.setId);
            case SCM:
            default:
                return Permission.getMemberPermissionsAsSCM(member.getGuild().getId(), member.getId(), this.setId);
        }
    }
    public String getPermissionsAsData(Role role,DataType dataType) {
        switch (dataType) {
            case JSON:
                return Permission.getRolePermissionsAsJson(role.getGuild().getId(), role.getId(), this.setId);
            case SCM:
            default:
                return Permission.getRolePermissionsAsSCM(role.getGuild().getId(), role.getId(), this.setId);
        }
    }

    public ArrayList<Permission> getPermissions(Member member) {
        return Permission.getMemberPermissions(member.getGuild().getId(), member.getId(), this.setId);
    }
    public ArrayList<Permission> getPermissions(Role role) {
        return Permission.getRolePermissions(role.getGuild().getId(), role.getId(), this.setId);
    }

    public boolean hasPermission(Member member, Permission permission) {
        return permission.memberHasPermission(member.getGuild().getId(), member.getId(), this.setId);
    }
    public boolean hasPermission(Role role, Permission permission) {
        return permission.roleHasPermission(role.getGuild().getId(), role.getId(), this.setId);
    }
}
enum DataType {
    SCM,
    JSON;
}
