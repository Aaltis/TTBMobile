package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.mvibase.MviResult;
import fi.breakwaterworks.utility.Status;

public interface DoWorkoutResult extends MviResult {
    @AutoValue
    abstract class SaveWorkout implements DoWorkoutResult {
        @NonNull
        abstract Status status();

        @Nullable
        abstract Workout workout();

        @Nullable
        abstract Throwable error();

        @NonNull
        public static SaveWorkout success(Workout workout) {
            return new AutoValue_DoWorkoutResult_SaveWorkout(Status.SUCCESS, workout , null);
        }

        @NonNull
        public static SaveWorkout failure(Throwable error) {
            return new AutoValue_DoWorkoutResult_SaveWorkout(Status.FAILURE, null, error);
        }

        @NonNull
        public static SaveWorkout running() {
            return new AutoValue_DoWorkoutResult_SaveWorkout(Status.RUNNING, null, null);
        }
    }


    @AutoValue
    abstract class LoadMovements implements DoWorkoutResult {
        @NonNull
        abstract Status status();

        @Nullable
        abstract List<Movement> movements();

        @Nullable
        abstract Throwable error();

        @NonNull
        public static LoadMovements success(List<Movement> movements) {
            return new AutoValue_DoWorkoutResult_LoadMovements(Status.SUCCESS, movements, null);
        }

        @NonNull
        static fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutResult.LoadMovements failure(Throwable error) {
            return new AutoValue_DoWorkoutResult_LoadMovements(Status.FAILURE, null, error);
        }

        @NonNull
        static fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutResult.LoadMovements running() {
            return new AutoValue_DoWorkoutResult_LoadMovements(Status.RUNNING, null, null);
        }
    }
}
