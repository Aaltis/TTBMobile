package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;


@Entity(tableName = "worklogs",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "user_id",
                childColumns = "user_id",
                onDelete = ForeignKey.NO_ACTION),
        indices = {@Index("user_id")})
public class WorkLog {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "worklog_id")
    private Long workLogId;
    @ColumnInfo(name = "user_id")
    private Long userId;
    private String name;
    private String description;
    private String comment;

    private boolean template;

    private Timestamp timestamp;

    @Ignore
    private List<Workout> workoutList;

    @Ignore
    private List<WorkoutCycle> workoutCycles;

    public WorkLog() {
    }
    @Ignore
    public WorkLog(long userId) {
        this.userId = userId;
    }

    @Ignore
    public WorkLog(String name, ArrayList<Workout> workouts, boolean template) {
        this.name = name;
        this.workoutList = workouts;
        this.template = template;

    }

    @NonNull
    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(@NonNull Long workLogId) {
        this.workLogId = workLogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public List<WorkoutCycle> getCycles() {
        return workoutCycles;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public void setCycles(List<WorkoutCycle> cycles) {
        this.workoutCycles = cycles;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }
}
