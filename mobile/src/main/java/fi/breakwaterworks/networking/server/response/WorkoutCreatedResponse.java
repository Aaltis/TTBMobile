package fi.breakwaterworks.networking.server.response;

import java.sql.Date;
import java.util.Set;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.WorkLog;


public class WorkoutCreatedResponse {

    private String name;
    private String unigueId;
    private Set<Exercise> exercises;
    private Set<WorkLog> worklogs;
    private Date date;
    private String comment;

    public WorkoutCreatedResponse() {
    }

    public Set<Exercise> getExercises() {
        return this.exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercises(Exercise ex) {
        this.exercises.add(ex);
    }

    public Set<WorkLog> getWorklogs() {
        return this.worklogs;
    }

    public void setWorklogs(Set<WorkLog> worklogs) {
        this.worklogs = worklogs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnigueId() {
        return unigueId;
    }

    public void setUnigueId(String unigueId) {
        this.unigueId = unigueId;
    }


}
