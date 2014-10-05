package com.sergo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;

import java.io.File;
import java.util.*;

public class Schedule {
    static volatile int currentRow;
    static MongoClient mongoClient;
    static DB db;
    static DBCollection coll;

    static ArrayList<String> workWeek = new ArrayList<>();
    static ArrayList<PlanFields> curPlan = null;
    static Map<String, String> tempMap = new HashMap<>();
    static Map<String, RulesFields> rulesMap = new HashMap<>();
    static ArrayList<DBObject> resultArray = new ArrayList<>();

    static boolean checkGeneByRules(String aCurPlanId, String day) {
        RulesFields rule = null;
        try {
            rule = rulesMap.get(day);
        } catch (Exception ignored) {
        }
        if (rule != null) {
            if (!rule.getLessonId().equals(aCurPlanId)) {
                return false;
            }
        }
        return true;
    }

    public static void genNewPlan(int curPosition) {
        for (PlanFields aCurPlan : curPlan) {
            if (checkGeneByRules(aCurPlan.getId(), workWeek.get(curPosition))) {
                tempMap.put(workWeek.get(curPosition), aCurPlan.getId());
                if (curPosition == workWeek.size() - 1) {
                    //resultArray.add(new BasicDBObject(tempMap));
                    try {
                        //coll.insert(new BasicDBObject(tempMap));
                        DataBaseTread dbThread = new DataBaseTread(tempMap);
                        dbThread.run();
                        //resultArray = new ArrayList<>();
                    } catch (Exception ignored) {
                    }
                } else {
                    genNewPlan(curPosition + 1);
                }
            }
        }
    }

    public static synchronized int getCurrentRow() {
        return currentRow;
    }

    public static synchronized void setCurrentRow(int currRow) {
        currentRow = currRow;
    }

    public static void main(String[] args) throws Exception {
        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("diplom");
        coll = db.getCollection("scheduleID" /*+ new ObjectId()*/);
        coll.drop();

        ObjectMapper mapper = new ObjectMapper();
        //File weekJSONFile = new File(args[0]);
        File weekJSONFile = new File("C:\\work.json");
        //File planJSONFile = new File(args[1]);
        File planJSONFile = new File("C:\\plan.json");
        //File rulesJSONFile = new File(args[2]);
        File rulesJSONFile = new File("C:\\rules.json");

        workWeek = mapper.readValue(weekJSONFile, new TypeReference<ArrayList<String>>() {});
        curPlan = mapper.readValue(planJSONFile, new TypeReference<ArrayList<PlanFields>>() {
        });
        ArrayList<RulesFields> rulseTempPlan = mapper.readValue(rulesJSONFile, new TypeReference<ArrayList<RulesFields>>() {
        });
        for (RulesFields el : rulseTempPlan) {
            rulesMap.put(el.getDay(), el);
        }

 /*       workWeek.add("week-1:monday-1");
        workWeek.add("week-2:monday-1");
        workWeek.add("week-1:tuesday-1");
        workWeek.add("week-2:tuesday-1");
        workWeek.add("week-1:wednesday-1");
        workWeek.add("week-2:wednesday-1");
       workWeek.add("week-1:thursday-1");
        workWeek.add("week-2:thursday-1");*/
//        workWeek.add("week-1:friday-1");
//        workWeek.add("week-2:friday-1");

//        curPlan.add("1");
//        curPlan.add("2");
//        curPlan.add("4");
//        curPlan.add("3");
        //curPlan.add("4");

        long start = new Date().getTime();

        System.out.println(new Date().toString());
        genNewPlan(0);
        System.out.println(new Date().getTime() - start);

        System.out.println(coll.count());
        //db.requestDone();
        /*DBCursor cursor = coll.find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }*/
    }
}
