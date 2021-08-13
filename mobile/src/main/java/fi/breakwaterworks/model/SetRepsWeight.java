package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "set_reps_weights",
        foreignKeys = @ForeignKey(entity = Exercise.class,
                parentColumns = "exercise_id",
                childColumns = "parent_exercise_id",
                onDelete = CASCADE),
        indices = {@Index("set_reps_weight_id"),
                @Index("parent_exercise_id")})
public class SetRepsWeight {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "set_reps_weight_id")
    private Long setRepsWeightId;
    @ColumnInfo(name = "parent_exercise_id")
    private Long exerciseId;
    private int orderNumber;

    private double weight;
    @ColumnInfo(name = "weight_unit")
    private String weightUnit;
    //in case of dropsets
    @ColumnInfo(name = "percentage_of_first_set")
    private Long percentageOfFirstSet;

    @ColumnInfo(name = "success")
    private boolean success;
    private int reps;
    private int sets;

    public SetRepsWeight() {
    }

    @Ignore
    public SetRepsWeight(int sets, int reps) {
        super();
        this.setOrderNumber(1);
        this.setSets(sets);
        this.setReps(reps);
        this.setWeightUnit("kg");
    }

    @Ignore
    public SetRepsWeight(int sets, int reps, long weight) {
        super();
        this.setOrderNumber(1);
        this.setSets(sets);
        this.setReps(reps);
        this.setWeight(weight);
        this.setWeightUnit("kg");
    }

    @Ignore
    public SetRepsWeight(int orderNumber, int set, int reps, double weight, String weightUnit) {
        super();
        this.setOrderNumber(orderNumber);
        this.setSets(set);
        this.setReps(reps);
        this.setWeight(weight);
        this.setWeightUnit(weightUnit);
    }

    public Long getId() {
        return setRepsWeightId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int set) {
        this.sets = set;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    @NonNull
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setSetRepsWeightId(@NonNull Long setRepsWeightId) {
        this.setRepsWeightId = setRepsWeightId;
    }

    public Long getSetRepsWeightId() {
        return setRepsWeightId;
    }

    public Long getPercentageOfFirstSet() {
        return percentageOfFirstSet;
    }

    public void setPercentageOfFirstSet(Long percentageOfFirstSet) {
        this.percentageOfFirstSet = percentageOfFirstSet;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAsString() {
        StringBuilder builder = new StringBuilder();
        if (getSets() != 0) {
            builder.append(getSets());
            builder.append("x");
        }
        builder.append(getReps());
        builder.append("x");

        builder.append(getWeight());
        return builder.toString();
    }
}
