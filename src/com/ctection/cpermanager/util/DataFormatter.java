package com.ctection.cpermanager.util;

import com.ctection.cpermanager.Permission;
import com.google.gson.Gson;
import cscm.SCMBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DataFormatter {

    public static String createEntityJson(String entityType, String entityId, String guildId, ArrayList<Permission> permissionArrayList){
        /*
          {
          "entityType": "user", "entityId": "123456789", "guildId": "987654321",
          "permissions": [
               {"set": "set", "name": "name", "id": "set.name"}
               {"set": "set", "name": "name", "id": "set.name"}
               {"set": "set", "name": "name", "id": "set.name"}
           ]
          }
         */
        Gson gson = new Gson();
        Entity entity = new Entity(entityType, entityId, guildId, permissionArrayList);
        return gson.toJson(entity);
    }
    public static String createEntitySCM(String entityType, String entityId, String guildId, ArrayList<Permission> permissionArrayList) {

        LinkedHashMap<String, Object> collection = new LinkedHashMap<>();
        collection.put("entityType", entityType);
        collection.put("entityId", entityId);
        collection.put("guildId", guildId);
        ArrayList<String> s = new ArrayList<>();
        for (Permission p: permissionArrayList) {
            s.add(p.getId());
        }
        collection.put("permissions", s);
        SCMBuilder builder = new SCMBuilder();
        builder.setPrettyPrinting(true);
        builder.add(collection);
        return builder.build();
    }
}
