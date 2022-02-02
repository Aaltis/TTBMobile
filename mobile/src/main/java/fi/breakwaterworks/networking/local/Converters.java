package fi.breakwaterworks.networking.local;

import androidx.room.TypeConverter;

import fi.breakwaterworks.model.ExerciseType;

public class Converters {

    @TypeConverter
    public String fromExerciseType(ExerciseType exerciseType){
        return exerciseType.toString();
    }

    @TypeConverter
    public ExerciseType ToExerciseType(String text) {
        return ExerciseType.valueOf(text);
    }
}
