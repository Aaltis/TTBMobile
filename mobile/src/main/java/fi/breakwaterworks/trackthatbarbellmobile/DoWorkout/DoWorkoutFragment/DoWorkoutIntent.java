package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.mvibase.MviIntent;

public interface DoWorkoutIntent extends MviIntent {

    @AutoValue
    abstract class SaveWorkout implements DoWorkoutIntent {

        @Nullable
        public abstract String userToken();

        @Nullable
        public abstract Workout workout();

        public static SaveWorkout create(@Nullable String userToken, @Nullable Workout workout) {
            return new AutoValue_DoWorkoutIntent_SaveWorkout(userToken, workout);
        }
    }

    @AutoValue
    abstract class LoadMovements implements DoWorkoutIntent {
        @Nullable

        public static LoadMovements create() {
            return new AutoValue_DoWorkoutIntent_LoadMovements();
        }
    }
}
