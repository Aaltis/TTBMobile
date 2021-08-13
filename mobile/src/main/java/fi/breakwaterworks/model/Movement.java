package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;


@Entity(tableName = "movements")
public class Movement {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movement_id")
    private Long movementId;

    private String name;
    private String type;
    private String stance;
    private String grip;
    private int reps;
    private Long weight;

    public Movement() {
    }

    @Ignore
    public Movement(String name, String type, String stance, String grip) {
        this.name = name;
        this.type = type;
        this.stance = stance;
        this.grip = grip;
    }

    public Movement(String name, String details, String type) {
        this.name = name;
        this.type = type;
        this.stance = details;
    }

    @NonNull
    public Long getMovementId() {
        return movementId;
    }

    public void setMovementId(@NonNull Long movementId) {
        this.movementId = movementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStance() {
        return stance;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public String getGrip() {
        return grip;
    }

    public void setGrip(String grip) {
        this.grip = grip;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
}