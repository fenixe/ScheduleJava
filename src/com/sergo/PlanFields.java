package com.sergo;

import com.fasterxml.jackson.annotation.*;

public class PlanFields {
    @JsonProperty
    private int hoursSemester;
    @JsonProperty
    private int year;
    @JsonProperty
    private String flow;
    @JsonProperty
    private String institute;
    @JsonProperty
    private int maxLessWeek;
    @JsonProperty
    private String id;
    @JsonProperty
    private String lessonName;
    @JsonProperty
    private String subType;
    @JsonProperty
    private String teachName;
    @JsonProperty
    private int course;
    @JsonProperty
    private double employDuration;
    @JsonProperty
    private int semester;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getTeachName() {
        return teachName;
    }

    public void setTeachName(String teachName) {
        this.teachName = teachName;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public double getEmployDuration() {
        return employDuration;
    }

    public void setEmployDuration(double employDuration) {
        this.employDuration = employDuration;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public int getMaxLessWeek() {
        return maxLessWeek;
    }
    public void setMaxLessWeek(int maxLessWeek) {
        this.maxLessWeek = maxLessWeek;
    }

    public int getHoursSemester() {
        return hoursSemester;
    }

    public void setHoursSemester(int hoursSemester) {
        this.hoursSemester = hoursSemester;
    }

}
