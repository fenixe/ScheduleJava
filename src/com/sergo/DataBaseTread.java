package com.sergo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DataBaseTread implements Runnable {
    private Map<String, String> tempMap = null;
    private List<DBObject> resultArray = new ArrayList<>();

    DataBaseTread(Map<String, String> tempMap) {
        this.tempMap = tempMap;
    }

    DataBaseTread(List<DBObject> resultArray) {
        this.resultArray = resultArray;
    }

    @Override
    public void run() {
        try {
            //query.put("key", key);
            //updateObj.put("", tempMap);
            Schedule.coll.insert(new BasicDBObject(tempMap));
            //Schedule.coll.update(query, updateObj, true, true);
        } catch (Exception ignored) {
        }

    }
}

