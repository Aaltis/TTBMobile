package fi.breakwaterworks.networking.local.repository;

import android.content.Context;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.SetType;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;

public class ExerciseRepository {
    TTBDatabase database;

    public ExerciseRepository(Context context) {
        this.database = TTBDatabase.getInstance(context);
    }

    public Exercise getLastSuccessfullExerciseWithTypeAndSRW(SetType setType, SetRepsWeight srw) {
        return database.exerciseDAO().loadSuccessfullExerciseWithSetRepsWeight(SetType.valueOf(setType.name()).toString(), srw.getSets(), srw.getReps(), true);
    }
}
