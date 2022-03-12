package fi.breakwaterworks.model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.*;

import static androidx.room.ForeignKey.CASCADE;

import fi.breakwaterworks.networking.server.response.WorkoutCreatedResponse;

@Entity(tableName = "workouts",
        foreignKeys = @ForeignKey(entity = WorkLog.class,
                parentColumns = "worklog_id",
                childColumns = "worklog_id",
                onDelete = CASCADE),
        indices = {@Index("worklog_id")})
public class Workout {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_id")
    private long workoutId;
    @ColumnInfo(name = "worklog_id")
    private long worklogId;
    @ColumnInfo(name = "name")
    private String name;
    private Timestamp timestamp;
    private String comment;
    private boolean template;


    //used at finding from server;
    private String unigueId;

    public Workout(WorkoutCreatedResponse response) {
        this.unigueId = response.getUnigueId();
        this.name = response.getName();
        this.timestamp = new Timestamp(response.getDate().getTime());
        this.exercises = new ArrayList<>(response.getExercises());
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    private boolean onGoing;

    @Ignore
    List<Exercise> exercises;

    public Workout() {
    }



    @Ignore
    public Workout(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Ignore
    public Workout(String name, List<Exercise> exercises, boolean template) {
        this.worklogId = worklogId;
        this.exercises = exercises;
        this.name = name;
        this.template = template;
    }

    public void setWorklogId(long worklogId) {
        this.worklogId = worklogId;
    }

    public long getWorklogId() {
        return worklogId;
    }

    public long getId() {
        return workoutId;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
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

