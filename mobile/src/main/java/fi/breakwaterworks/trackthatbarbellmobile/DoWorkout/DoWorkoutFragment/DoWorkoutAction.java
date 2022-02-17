package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.mvibase.MviAction;

public interface DoWorkoutAction extends MviAction {
    @AutoValue
    abstract class SaveWorkout implements DoWorkoutAction {
        @Nullable
        public abstract String token();

        @NonNull
        public abstract Workout workout();

        public static SaveWorkout create(@Nullable String token, @NonNull Workout workout) {
            return new AutoValue_DoWorkoutAction_SaveWorkout(token, workout);
        }
    }

    @AutoValue
    abstract class LoadMovements implements DoWorkoutAction {
        public abstract String MovementName();

        public static LoadMovements create(String movementName) {
            return new AutoValue_DoWorkoutAction_LoadMovements(movementName);
        }
    }
}
