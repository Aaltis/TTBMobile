package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "one_rep_maxes",
        foreignKeys = @ForeignKey(entity = Movement.class,
                parentColumns = "movement_id",
                childColumns = "movement_id"),
        indices = {@Index("one_rep_max_id"),
                @Index("exercise_id")})
public class OneRm {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "one_rep_max_id")
    private int oneRepMaxId;
    @ColumnInfo(name = "movement_id")
    private Long movement_id;

    double OneRm;
}
