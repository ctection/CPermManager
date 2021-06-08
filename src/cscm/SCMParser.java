package cscm;

import java.util.*;

public class SCMParser {
    public ArrayList<LinkedHashMap<String, Object>> parse(String s) {
        s = s.replace("\n", "");
        boolean onlyContainsSemicolon = true;
        for (Byte b: s.getBytes()) {
            if (b != ';') {
                onlyContainsSemicolon = false;
                 break;
            }
        }
        if (onlyContainsSemicolon) {
            s = s + "a";
        }
        ArrayList<LinkedHashMap<String, Object>> objects = new ArrayList<>();
        String[] collections = s.split("(?<!\\\\)(?:\\\\\\\\)*;");
        if (onlyContainsSemicolon) {
            String[] newColl = new String[collections.length -1];
            for (int i = 0; i < collections.length -1; i++) {
                newColl[i] = collections[i];
            }
            collections = newColl;
        }
        for (String st: collections) {
            LinkedHashMap<String, Object> t_hm = new LinkedHashMap<>();
            for (int i = 0; i<st.chars().filter(ch -> ch == '{').count();i++){
                if (st.contains("{") && st.contains("}")) {
                    String s1 = null;
                    try {
                       s1 = st.split("(?:\\{)")[i+1].split("(})")[0];
                    }catch (ArrayIndexOutOfBoundsException e){
                        break;
                    }
                    st = st.replace(s1, s1.replace("&", "\\&"));
                    st = st.replace(s1, s1.replace(",", "&"));
                }
            }
            String[] vaulePairs = st.split("(?<!\\\\)(?:\\\\\\\\)*,");
            for (int i = 0; i < vaulePairs.length; i++) {
                String[] kvPairs = vaulePairs[i].split("(?<!\\\\)(?:\\\\\\\\)*:");
                if (kvPairs.length > 1) {
                    String key = kvPairs[0];
                    String value = kvPairs[1];
                    if (value.contains("{") && value.contains("}")) {
                        value = value.replaceFirst("\\{", "");
                        value = value.substring(0, (value.length() - 1));
                        key = key.replace("\\&", "&");
                        value = value.replace("&", ",");
                        value = value.replace("\\&", "&");
                        ArrayList<String> values = new ArrayList<>();
                        if (!value.equals("")) {
                            values = new ArrayList<>(Arrays.asList(value.split(",")));
                        }
                        t_hm.put(key, values);
                    } else {
                        key = key.replace("\\&", "&");
                        value = value.replace("\\&", "&");
                        t_hm.put(key, value);
                    }
                }
            }
            objects.add(t_hm);
        }
        return objects;
    }
    public ArrayList<HashMap<String, Object>> parseAsHashMap(String s) {
        s = s.replace("\n", "");
        boolean onlyContainsSemicolon = true;
        for (Byte b: s.getBytes()) {
            if (b != ';') {
                onlyContainsSemicolon = false;
                break;
            }
        }
        if (onlyContainsSemicolon) {
            s = s + "a";
        }
        ArrayList<HashMap<String, Object>> objects = new ArrayList<>();
        String[] collections = s.split("(?<!\\\\)(?:\\\\\\\\)*;");
        if (onlyContainsSemicolon) {
            String[] newColl = new String[collections.length -1];
            for (int i = 0; i < collections.length -1; i++) {
                newColl[i] = collections[i];
            }
            collections = newColl;
        }
        for (String st: collections) {
            LinkedHashMap<String, Object> t_hm = new LinkedHashMap<>();
            for (int i = 0; i<st.chars().filter(ch -> ch == '{').count();i++){
                if (st.contains("{") && st.contains("}")) {
                    String s1 = null;
                    try {
                        s1 = st.split("(?:\\{)")[i+1].split("(})")[0];
                    }catch (ArrayIndexOutOfBoundsException e){
                        break;
                    }
                    st = st.replace(s1, s1.replace("&", "\\&"));
                    st = st.replace(s1, s1.replace(",", "&"));
                }
            }
            String[] vaulePairs = st.split("(?<!\\\\)(?:\\\\\\\\)*,");
            for (int i = 0; i < vaulePairs.length; i++) {
                String[] kvPairs = vaulePairs[i].split("(?<!\\\\)(?:\\\\\\\\)*:");
                if (kvPairs.length > 1) {
                    String key = kvPairs[0];
                    String value = kvPairs[1];
                    if (value.contains("{") && value.contains("}")) {
                        value = value.replaceFirst("\\{", "");
                        value = value.substring(0, (value.length() - 1));
                        key = key.replace("\\&", "&");
                        value = value.replace("&", ",");
                        value = value.replace("\\&", "&");
                        ArrayList<String> values = new ArrayList<>();
                        if (!value.equals("")) {
                            values = new ArrayList<>(Arrays.asList(value.split(",")));
                        }
                        t_hm.put(key, values);
                    } else {
                        key = key.replace("\\&", "&");
                        value = value.replace("\\&", "&");
                        t_hm.put(key, value);
                    }
                }
            }
            objects.add(t_hm);
        }
        return objects;
    }
    public ArrayList<Map<String, Object>> parseAsMap(String s) {
        s = s.replace("\n", "");
        boolean onlyContainsSemicolon = true;
        for (Byte b: s.getBytes()) {
            if (b != ';') {
                onlyContainsSemicolon = false;
                break;
            }
        }
        if (onlyContainsSemicolon) {
            s = s + "a";
        }
        ArrayList<Map<String, Object>> objects = new ArrayList<>();
        String[] collections = s.split("(?<!\\\\)(?:\\\\\\\\)*;");
        if (onlyContainsSemicolon) {
            String[] newColl = new String[collections.length -1];
            for (int i = 0; i < collections.length -1; i++) {
                newColl[i] = collections[i];
            }
            collections = newColl;
        }
        for (String st: collections) {
            LinkedHashMap<String, Object> t_hm = new LinkedHashMap<>();
            for (int i = 0; i<st.chars().filter(ch -> ch == '{').count();i++){
                if (st.contains("{") && st.contains("}")) {
                    String s1 = null;
                    try {
                        s1 = st.split("(?:\\{)")[i+1].split("(})")[0];
                    }catch (ArrayIndexOutOfBoundsException e){
                        break;
                    }
                    st = st.replace(s1, s1.replace("&", "\\&"));
                    st = st.replace(s1, s1.replace(",", "&"));
                }
            }
            String[] vaulePairs = st.split("(?<!\\\\)(?:\\\\\\\\)*,");
            for (int i = 0; i < vaulePairs.length; i++) {
                String[] kvPairs = vaulePairs[i].split("(?<!\\\\)(?:\\\\\\\\)*:");
                if (kvPairs.length > 1) {
                    String key = kvPairs[0];
                    String value = kvPairs[1];
                    if (value.contains("{") && value.contains("}")) {
                        value = value.replaceFirst("\\{", "");
                        value = value.substring(0, (value.length() - 1));
                        key = key.replace("\\&", "&");
                        value = value.replace("&", ",");
                        value = value.replace("\\&", "&");
                        ArrayList<String> values = new ArrayList<>();
                        if (!value.equals("")) {
                            values = new ArrayList<>(Arrays.asList(value.split(",")));
                        }
                        t_hm.put(key, values);
                    } else {
                        key = key.replace("\\&", "&");
                        value = value.replace("\\&", "&");
                        t_hm.put(key, value);
                    }
                }
            }
            objects.add(t_hm);
        }
        return objects;
    }
}