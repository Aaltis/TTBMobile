package fi.breakwaterworks.model;

import androidx.room.*;

@Entity(tableName = "set_reps_weights_set_type_join",
        primaryKeys = {"special_method_type_id", "setTypeId"},
        foreignKeys = {
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "set_reps_weight_id",
                        childColumns = "set_reps_weight_id"),
                @ForeignKey(entity = SetType.class,
                        parentColumns = "set_type_id",
                        childColumns = "set_type_id")
        })
public class SetRepsWeightsSetTypeJoin {
    public final long setRepsWeightsId;
    public final long setTypeId;

    public SetRepsWeightsSetTypeJoin(final int setRepsWeightsId, final int setTypeId) {
        this.setRepsWeightsId = setRepsWeightsId;
        this.setTypeId = setTypeId;
    }
}