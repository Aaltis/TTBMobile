package fi.breakwaterworks.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "set_reps")
public class SetReps {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "set_reps_id")
    private int set;
    private int reps;

    public SetReps() {
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    @NonNull
    public int getSet() {
        return set;
    }

    public void setSet(@NonNull int set) {
        this.set = set;
    }
}
