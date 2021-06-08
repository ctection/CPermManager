package com.ctection.cpermanager.util;

import com.ctection.cpermanager.Permission;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Entity {
    private final String entityType;
    private final String entityId;
    private final String guildId;
    @SerializedName("permissions")
    private final ArrayList<Permission> permissionArrayList;

    public Entity(String entityType, String entityId, String guildId, ArrayList<Permission> permissionArrayList) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.guildId = guildId;
        this.permissionArrayList = permissionArrayList;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public ArrayList<Permission> getPermissionArrayList() {
        return permissionArrayList;
    }
}
