package com.bisnode.bhc.domain.portfolio;

import java.util.HashMap;
import java.util.Map;

public class GlobalMapping {

    private static HashMap<String, Integer> systemIdMap = new HashMap<>();
    private static HashMap<String, Integer> profileIdMap = new HashMap<>();

    static {
        systemIdMap.put("P2R", 1);
        systemIdMap.put("PBC", 2);
        systemIdMap.put("P4S", 3);

        profileIdMap.put("SMALL", 1);
        profileIdMap.put("MEDIUM", 2);
        profileIdMap.put("LARGE", 3);
    }

    public static Integer getSystemIdValue(String strValue) {
        return systemIdMap.get(strValue);
    }

    public static Integer getProfileIdValue(String strValue) {
        return profileIdMap.get(strValue);
    }

    public static String getSystemNameValue(Integer intValue) {
        return systemIdMap.entrySet()
                .stream()
                .filter(i -> i.getValue() == intValue)
                .map(Map.Entry::getKey).findFirst()
                .orElse(null);
    }

    public static String getProfileNameValue(Integer intValue) {
        return profileIdMap.entrySet()
                .stream()
                .filter(i -> i.getValue() == intValue)
                .map(Map.Entry::getKey).findFirst()
                .orElse(null);
    }
}
