package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import static androidx.room.ForeignKey.CASCADE;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.utility.SetTypeEnum;
import fi.breakwaterworks.utility.SetTypeEnumConverter;


@Entity(tableName = "exercises",
        foreignKeys = @ForeignKey(entity = Workout.class,
                parentColumns = "workout_id",
                childColumns = "workout_id",
                onDelete = CASCADE),
        indices = {@Index("exercise_id"),
                @Index("workout_id")})
public class Exercise {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exercise_id")
    private Long exerciseId;
    @ColumnInfo(name = "workout_id")
    private Long workoutId;
    @ColumnInfo(name = "order_number")
    private int orderNumber;
    private double onRepMax;

    @TypeConverters(SetTypeEnumConverter.class)
    private SetTypeEnum setTypeEnum;

    @Ignore
    private SetType setType;
    @Ignore
    private String movementName;
    @ColumnInfo(name = "cycle_one_rm_percent")
    private long cycleOneRMPercent;
    @ColumnInfo(name = "of_training_max")
    private long ofTrainingMax;
    @ColumnInfo(name = "template")
    private boolean template;
    @ColumnInfo(name = "movement_id")
    private long movementId;
    @Ignore
    private Movement movement;
    @Ignore
    public List<SetRepsWeight> setRepsWeights;

    public Exercise() {
    }

    @Ignore
    public Exercise(int orderNumber, String movementName, SetTypeEnum setTypeEnum, List<SetRepsWeight> repetitionList) {
        this.orderNumber = orderNumber;
        this.setTypeEnum = setTypeEnum;
        this.movementName = movementName;
        this.setRepsWeights = repetitionList;
    }

    @Ignore
    public Exercise(int orderNumber, Movement movement, SetRepsWeight repetitionList) {
        this.orderNumber = orderNumber;
        this.movementName = movement.getName();
        this.movement = movement;
        this.setRepsWeights = new ArrayList<>();
        this.setRepsWeights.add(repetitionList);
    }

    @Ignore
    public Exercise(int orderNumber, Movement movement) {
        this.orderNumber = orderNumber;
        this.movementName = movement.getName();
        this.movement = movement;
        this.setRepsWeights = new ArrayList<>();
    }


    @NonNull
    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(@NonNull Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getOnRepMax() {
        return onRepMax;
    }

    public void setOnRepMax(double onRepMax) {
        this.onRepMax = onRepMax;
    }

    public long getCycleOneRMPercent() {
        return cycleOneRMPercent;
    }

    public void setCycleOneRMPercent(long cycleOneRMPercent) {
        this.cycleOneRMPercent = cycleOneRMPercent;
    }

    public long getOfTrainingMax() {
        return ofTrainingMax;
    }

    public void setOfTrainingMax(long ofTrainingMax) {
        this.ofTrainingMax = ofTrainingMax;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movementName = movement.getName();
        this.movement = movement;
    }

    public List<SetRepsWeight> getSetRepsWeights() {
        return setRepsWeights;
    }

    public void setSetRepsWeights(List<SetRepsWeight> setRepsWeights) {
        this.setRepsWeights = setRepsWeights;
    }

    public long getMovementId() {
        return movementId;
    }

    public void setMovementId(long movementId) {
        this.movementId = movementId;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public SetType getSetType() {
        return setType;
    }

    public void setSetType(SetType setType) {
        this.setType = setType;
    }

    public String getMovementName() {
        return movementName;
    }

    public void setMovementName(String movementName) {
        this.movementName = movementName;
    }

    public SetTypeEnum getSetTypeEnum() {
        return setTypeEnum;
    }

    public void setSetTypeEnum(SetTypeEnum setTypeEnum) {
        this.setTypeEnum = setTypeEnum;
    }

    /**
     * If latest is same reps and weigh, add it to previous set.
     *
     * @param setRepsWeightList
     */
    public void AddToSetRepsWeights(List<SetRepsWeight> setRepsWeightList) {
        if (setRepsWeights.size() == 0) {
            this.setRepsWeights.addAll(setRepsWeightList);
            return;
        }
        for (SetRepsWeight latest : setRepsWeightList) {
            SetRepsWeight previous = this.setRepsWeights.get(this.setRepsWeights.size() - 1);
            if (previous.getReps() == latest.getReps() && previous.getWeight() == latest.getWeight()) {
                previous.setSets(previous.getSets() + 1);
            } else {
                this.setRepsWeights.add(latest);
            }
        }

    }
}
