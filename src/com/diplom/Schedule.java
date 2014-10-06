package com.diplom;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Schedule {
    static volatile int currentRow;
    static int SEMESTER_WEEK = 4;
    static MongoClient mongoClient;
    static DB db;
    static DBCollection coll;

    static ArrayList<String> workWeek = new ArrayList<>();
    static ArrayList<PlanFields> curPlan = null;
    static Map<String, String> tempMap = new HashMap<>();
    static Map<String, RulesFields> rulesMap = new HashMap<>();
    static ArrayList<DBObject> resultArray = new ArrayList<>();

    static boolean checkGeneByRules(PlanFields curPlan, String day) {
        RulesFields rule = null;
        int countHours = 0;
        int maxLessOnWeek = curPlan.getMaxLessWeek();
        int hourOnSemester = curPlan.getHoursSemester();
        int semester = 3;
        int lessInWeek1 = 0;
        int lessInWeek2 = 0;


        try {
            rule = rulesMap.get(day);
        } catch (Exception ignored) {}
        if (rule != null) {
            if (rule.getLessonId().equals(curPlan.getId())) {
                return false;
            }
        }

        for (Map.Entry<String, String> schedElement : tempMap.entrySet()){
            String idOfLesson = schedElement.getValue();
            if (idOfLesson.equals(curPlan.getId())){
                if (schedElement.getKey().contains("week-1")){
                    if (++lessInWeek1 > maxLessOnWeek){return false;}
                }else{
                    if (++lessInWeek2 > maxLessOnWeek){return false;}
                }
            }
        }

        for (Map.Entry<String, String> schedElement : tempMap.entrySet()){
            //System.out.println(schedElement);
            String idOfLesson = schedElement.getValue();
            if (idOfLesson.equals(curPlan.getId())){
                //System.out.println((countHours));
                if ((++countHours*2*semester) == hourOnSemester ){return false;}
                //System.out.println(hourOnSemester);
            }
        }
        return true;
    }

    public static void genNewPlan(int curPosition) {
        for (PlanFields aCurPlan : curPlan) {
            if (checkGeneByRules(aCurPlan, workWeek.get(curPosition))) {
                tempMap.put(workWeek.get(curPosition), aCurPlan.getId());
                if (curPosition == workWeek.size() - 1) {
                    try {
                        DataBaseTread dbThread = new DataBaseTread(tempMap);
                        dbThread.run();
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
        curPlan = mapper.readValue(planJSONFile, new TypeReference<ArrayList<PlanFields>>() {  });
        ArrayList<RulesFields> rulseTempPlan = mapper.readValue(rulesJSONFile, new TypeReference<ArrayList<RulesFields>>() {
        });

        for (RulesFields el : rulseTempPlan) {
            //rulesMap.put(el.getDay(), el);
        }


       /* workWeek.add("week-1:monday-1");
        workWeek.add("week-2:monday-1");
        workWeek.add("week-1:tuesday-1");
        workWeek.add("week-2:tuesday-1");
       /* workWeek.add("week-1:wednesday-1");
    workWeek.add("week-2:wednesday-1");
       workWeek.add("week-1:thursday-1");
        workWeek.add("week-2:thursday-1");
        workWeek.add("week-1:friday-1");
        workWeek.add("week-2:friday-1");*/


//        curPlan.add("1");
//        curPlan.add("2");
//        curPlan.add("4");
//        curPlan.add("3");
        //curPlan.add("4");

        long start = new Date().getTime();

        //System.out.println(new Date().toString());
        genNewPlan(0);
        //System.out.println(new Date().getTime() - start);

        System.out.println(coll.count());
        //db.requestDone();
        DBCursor cursor = coll.find();
       /* while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }*/
    }
}
