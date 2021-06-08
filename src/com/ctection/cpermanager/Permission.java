package com.ctection.cpermanager;

import com.ctection.cpermanager.util.DataFormatter;
import com.ctection.cpermanager.util.SQL;
import com.google.gson.annotations.SerializedName;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Permission {
	private final String permPack;
    @SerializedName("name")
    private final String permName;
    @SerializedName("id")
    private final String permId;

    public Permission(String pack, String name) {
        this.permPack = pack;
        this.permName = name;
        this.permId = pack + "." + name;
    }
    public Permission(String id) throws InvalidArgumentException {
        if (!id.contains(".")) {
            throw new IllegalArgumentException("The Permission ID has to contain a Permission Pack and a Name! Example: pack.name");
        }
        this.permPack = id.split("[.]")[0];
        this.permName = id.split("[.]")[1];
        this.permId = id;
    }


    public String getId() {
        return permPack + "." + permName;
    }

    public String getName() {
        return permName;
    }

    public String getPackage() {
        return permPack;
    }

    public boolean removeFromUser(String setId, String guildId, String userId) {

        Permission sourcePermisssion = this;
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = sql.resultSet
                        ("SELECT perms FROM CPermUserPerms WHERE guild_id='" + guildId + "' AND user_id='" + userId + "' AND setid='" + setId + "';");
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(permStatement.split(" ")));
        boolean isRemoved = false;
        for (String s : stringArrayList) {
            String s1 = s.replace(".", " ");
            Permission perm = new Permission(s1.split(" ")[0], s1.split(" ")[1]);
            if (perm.getId().equals(sourcePermisssion.getId())){
                stringArrayList.remove(s);
                isRemoved = true;
                break;
            }

        }
        if (isRemoved) {
            String statement = "UPDATE CPermUserPerms SET perms='" + String.join(" ", stringArrayList) +
                    "' WHERE guild_id='" + guildId + "' AND user_id='" + userId + "' AND setid='" + setId + "';";
            try {
                sql.update(statement);
            } catch (SQLException e) {
                sql.disconnect();
                return false;
            }
        }
        sql.disconnect();
        return isRemoved;
    }
    public boolean removeFromRole(String setId, String guildId, String roleId) {
        Permission sourcePermisssion = this;
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = sql.resultSet
                ("SELECT perms FROM CPermRolePerms WHERE guild_id='" + guildId + "' AND role_id='" + roleId + "' AND setid='" + setId + "';");
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(permStatement.split(" ")));
        boolean isRemoved = false;
        for (String s : stringArrayList) {
            String s1 = s.replace(".", " ");
            Permission perm = new Permission(s1.split(" ")[0], s1.split(" ")[1]);
            if (perm.getId().equals(sourcePermisssion.getId())) {
                stringArrayList.remove(s);
                isRemoved = true;
                break;
            }

        }
        if (isRemoved) {
            String statement = "UPDATE CPermRolePerms SET perms='" + String.join(" ", stringArrayList) +
                    "' WHERE guild_id='" + guildId + "' AND role_id='" + roleId + "' AND setid='" + setId + "';";
            try {
                sql.update(statement);
            } catch (SQLException e) {
                sql.disconnect();
                return false;
            }
        }
        sql.disconnect();
        return isRemoved;

    }

    public static void setMemberPermissions(String setId, String guildId, String memberId, ArrayList<Permission> permissions) {

        ArrayList<String> permNameList = new ArrayList();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();

        for (Permission perm: permissions) {
            if (perm != null)
                permNameList.add(perm.getId());
            else
                permissions.remove(perm);
        }

        try {
            String probe = sql.resultSet("SELECT * FROM CPermUserPerms WHERE" +
                    " guild_id='" + guildId + "' AND user_id='" + memberId + "' AND setid='" + setId + "';");
            if (probe != null) {
                sql.update("UPDATE CPermUserPerms SET perms='" +
                        String.join(" ", permNameList)
                        + "' WHERE guild_id='" + guildId + "' AND user_id='" + memberId + "' AND setid='" + setId + "';");

            }
            else {
                sql.update("INSERT INTO CPermUserPerms (setid, guild_id, user_id, perms) VALUES ('" + setId + "', '" + guildId + "', '"
                        + memberId + "', '" + String.join(" ", permNameList) + "');");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            sql.disconnect();
        }
        sql.disconnect();
    }
    public static void setRolePermissions(String setId, String guildId, String roleId, ArrayList<Permission> permissions) {
        ArrayList<String> permNameList = new ArrayList();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        for (Permission perm: permissions) {
            if (perm != null)
                permNameList.add(perm.getId());
            else
                permissions.remove(perm);
        }

        try {

            String probe = sql.resultSet("SELECT * FROM CPermRolePerms WHERE" +
                    " guild_id='" + guildId + "' AND role_id='" + roleId + "' AND setid='" + setId + "';");
            if (probe != null) {
                sql.update("update CPermRolePerms SET perms='" +
                        String.join(" ", permNameList)
                        + "' WHERE guild_id='" + guildId + "' AND role_id='" + roleId + "' AND setid='" + setId + "';");
            }
            else {
                sql.update("INSERT INTO CPermRolePerms(setid, guild_id, role_id, perms) VALUES ('" + setId + "', '" + guildId + "', '"
                        + roleId + "', '" + String.join(" ", permNameList) + "');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sql.disconnect();
        }
        sql.disconnect();
    }

    public static void addMemberPermission(String guildId, String memberId, String setId, ArrayList<Permission> permissions) {
        ArrayList<Permission> permList = getMemberPermissions(guildId, memberId, setId);
        for (Permission p: permissions) {

            if (p  != null && !permList.contains(p)){
                permList.add(p);

            }

        }
        setMemberPermissions(setId, guildId, memberId, permList);
    }
    public static void addRolePermission(String guildId, String roleId, String setId, ArrayList<Permission> permissions) {
        ArrayList permList = getRolePermissions(guildId, roleId, setId);
        for (Permission p: permissions) {

            if (p  != null && !permList.contains(p)){
                permList.add(p);

            }

        }
        setRolePermissions(setId, guildId, roleId, permList);
    }

    public static ArrayList<Permission> getMemberPermissions(String guildId, String memberId, String setId) {
        ArrayList<Permission> permList = new ArrayList<>();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = "SELECT perms FROM CPermUserPerms WHERE guild_id='" + guildId + "' AND user_id='" + memberId + "' AND setid='" + setId + "';";
        String setPerms = sql.resultSet(permStatement);
        if (setPerms == null) {
            sql.disconnect();
            return permList;
        }
        for (String s : setPerms.split("\\s+")) {
            Permission p = PermissionManager.getInstance().getSet().getById(s);


            if (p != null) {
                permList.add(p);

            }

        }
        sql.disconnect();
        return permList;
    }
    public static ArrayList<Permission> getRolePermissions(String guildId, String RoleId, String setId) {
        ArrayList<Permission> permList = new ArrayList<>();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = "SELECT perms FROM CPermRolePerms WHERE guild_id='" + guildId + "' AND role_id='" + RoleId + "' AND setid='" + setId + "';";
        String setPerms = sql.resultSet(permStatement);
        if (setPerms == null) {
            sql.disconnect();
            return permList;
        }
        for (String s: setPerms.split("\\s+")) {
            Permission p = PermissionManager.getInstance().getSet().getById(s);

            if (p  != null){
                permList.add(p);

            }

        }
        sql.disconnect();
        return permList;
    }

    public static String getMemberPermissionsAsJson(String guildId, String memberId, String setId) {
        ArrayList<Permission> permList = new ArrayList<>();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = "SELECT perms FROM CPermUserPerms WHERE guild_id='" + guildId + "' AND user_id='" + memberId + "' AND setid='" + setId + "';";
        String setPerms = sql.resultSet(permStatement);

        if (setPerms == null){
            return DataFormatter.createEntityJson("user", memberId, guildId, permList);
        }
        for (String s : setPerms.split("\\s+")) {
            Permission p = PermissionManager.getInstance().getSet().getById(s);

            if (p != null) {
                permList.add(p);

            }

        }
        sql.disconnect();
        return DataFormatter.createEntityJson("user", memberId, guildId, permList);
    }
    public static String getRolePermissionsAsJson(String guildId, String roleId, String setId) {
        ArrayList<Permission> permList = new ArrayList<>();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = "SELECT perms FROM CPermRolePerms WHERE guild_id='" + guildId + "' AND role_id='" + roleId + "' AND setid='" + setId + "';";
        String setPerms = sql.resultSet(permStatement);

        if (setPerms == null){
            return DataFormatter.createEntityJson("role", roleId, guildId, permList);
        }
        for (String s: setPerms.split("\\s+")) {
            Permission p = PermissionManager.getInstance().getSet().getById(s);

            if (p  != null){
                permList.add(p);

            }

        }
        sql.disconnect();
        return DataFormatter.createEntityJson("role", roleId, guildId, permList);
    }

    public static String getMemberPermissionsAsSCM(String guildId, String memberId, String setId) {
        ArrayList<Permission> permList = new ArrayList<>();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = "SELECT perms FROM CPermUserPerms WHERE guild_id='" + guildId + "' AND user_id='" + memberId + "' AND setid='" + setId + "';";
        String setPerms = sql.resultSet(permStatement);

        if (setPerms == null){
            return DataFormatter.createEntitySCM("user", memberId, guildId, permList);
        }
        for (String s : setPerms.split("\\s+")) {
            Permission p = PermissionManager.getInstance().getSet().getById(s);

            if (p != null) {
                permList.add(p);

            }

        }
        sql.disconnect();
        return DataFormatter.createEntitySCM("user", memberId, guildId, permList);
    }
    public static String getRolePermissionsAsSCM(String guildId, String roleId, String setId) {
        ArrayList<Permission> permList = new ArrayList<>();
        SQL sql = PermissionManager.getInstance().getSqlFactory().get();
        sql.connect();
        String permStatement = "SELECT perms FROM CPermRolePerms WHERE guild_id='" + guildId + "' AND role_id='" + roleId + "' AND setid='" + setId + "';";
        String setPerms = sql.resultSet(permStatement);

        if (setPerms == null){
            return DataFormatter.createEntitySCM("role", roleId, guildId, permList);
        }
        for (String s: setPerms.split("\\s+")) {
            Permission p = PermissionManager.getInstance().getSet().getById(s);

            if (p  != null){
                permList.add(p);

            }

        }
        sql.disconnect();
        return DataFormatter.createEntitySCM("role", roleId, guildId, permList);
    }

    public boolean memberHasPermission(String guildId, String memberId, String setId){
        ArrayList<Permission> permissions = getMemberPermissions(guildId, memberId, setId);
        for (Permission p : permissions) {
            if (p.getId().equals(this.getId())
                || p.getId().equals(this.getPackage() + ".*")
                || p.getId().equals("permission.*")) {
                return true;
            }
        }
        return false;
    }
    public boolean roleHasPermission(String guildId, String roleId, String setId){
        ArrayList<Permission> permissions = getRolePermissions(guildId, roleId, setId);
        for (Permission p : permissions) {
            if (p.getId().equals(this.getId())
                    || p.getId().equals(this.getPackage() + ".*")
                    || p.getId().equals("permission.*")) {
                return true;
            }
        }
        return false;
    }

}
