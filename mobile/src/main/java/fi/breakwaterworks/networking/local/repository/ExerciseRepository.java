package fi.breakwaterworks.networking.local.repository;

import android.content.Context;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.utility.SetTypeEnum;

public class ExerciseRepository {
    TTBDatabase database;

    public ExerciseRepository(Context context) {
        this.database = TTBDatabase.getInstance(context);
    }

    public Exercise getLastSuccessfullExerciseWithTypeAndSRW(SetTypeEnum setTypeEnum, SetRepsWeight srw) {
        return database.exerciseDAO().loadSuccessfullExerciseWithSetRepsWeight(setTypeEnum, srw.getSets(), srw.getReps(), true);
    }
}
