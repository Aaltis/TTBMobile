package fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.mvibase.MviResult;
import fi.breakwaterworks.utility.Status;

public interface MainActivityResult extends MviResult {
    @AutoValue
    abstract class Initialization implements MainActivityResult {
        @NonNull
        public abstract Status status();

        @Nullable
        public abstract Config success();

        @Nullable
        public abstract Throwable error();

        @NonNull
        public static Initialization success(Config success) {
            return new AutoValue_MainActivityResult_Initialization(Status.SUCCESS, success, null);
        }

        @NonNull
        public static Initialization failure(Throwable error) {
            return new AutoValue_MainActivityResult_Initialization(Status.FAILURE, null, error);
        }

        @NonNull
        public static Initialization running() {
            return new AutoValue_MainActivityResult_Initialization(Status.RUNNING, null, null);
        }
    }
    @AutoValue
    abstract class OpenWorkoutTemplatesResult implements MainActivityResult {
        @NonNull
        public abstract Status status();

        @Nullable
        public abstract Boolean success();

        @Nullable
        public abstract Throwable error();

        @NonNull
        public static OpenWorkoutTemplatesResult success(Boolean success) {
            return new AutoValue_MainActivityResult_OpenWorkoutTemplatesResult(Status.SUCCESS, success, null);
        }

        @NonNull
        public static OpenWorkoutTemplatesResult failure(Throwable error) {
            return new AutoValue_MainActivityResult_OpenWorkoutTemplatesResult(Status.FAILURE, null, error);
        }

        @NonNull
        public static OpenWorkoutTemplatesResult running() {
            return new AutoValue_MainActivityResult_OpenWorkoutTemplatesResult(Status.RUNNING, null, null);
        }
    }
    @AutoValue
    abstract class RemoteUpdateResult implements MainActivityResult {
        @NonNull
        public abstract Status status();

        @Nullable
        public abstract Boolean success();

        @Nullable
        public abstract Throwable error();

        @NonNull
        public static RemoteUpdateResult success(Boolean success) {
            return new AutoValue_MainActivityResult_RemoteUpdateResult(Status.SUCCESS, success, null);
        }

        @NonNull
        public static RemoteUpdateResult failure(Throwable error) {
            return new AutoValue_MainActivityResult_RemoteUpdateResult(Status.FAILURE, null, error);
        }

        @NonNull
        public static RemoteUpdateResult running() {
            return new AutoValue_MainActivityResult_RemoteUpdateResult(Status.RUNNING, null, null);
        }
    }
}
