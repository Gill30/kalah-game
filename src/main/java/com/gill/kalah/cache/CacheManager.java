package com.gill.kalah.cache;

import com.gill.kalah.model.State;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


public class CacheManager {
    public Map<String, State> cache;
    private static CacheManager instance;

    private CacheManager() {
        cache = new HashMap<>();
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            //synchronized block to remove overhead
            synchronized (CacheManager.class) {
                if (instance == null) {
                    // if instance is null, initialize
                    instance = new CacheManager();
                }

            }
        }
        return instance;
    }


}