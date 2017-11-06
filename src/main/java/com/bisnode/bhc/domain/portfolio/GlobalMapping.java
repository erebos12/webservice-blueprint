package com.bisnode.bhc.domain.portfolio;

import java.util.HashMap;

public class GlobalMapping {

    public static HashMap<String, Integer> systemIdMap = new HashMap<>();
    public static HashMap<String, Integer> profileIdMap = new HashMap<>();

    static {
        systemIdMap.put("PBC", 1);
        systemIdMap.put("P2R", 2);
        systemIdMap.put("P4S", 3);

        profileIdMap.put("SMALL", 1);
        profileIdMap.put("MEDIUM", 2);
        profileIdMap.put("LARGE", 3);
    }
}
