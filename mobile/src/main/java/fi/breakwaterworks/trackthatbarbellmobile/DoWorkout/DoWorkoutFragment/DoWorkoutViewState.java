package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import fi.breakwaterworks.mvibase.MviViewState;

@AutoValue
public abstract class DoWorkoutViewState implements MviViewState {
    abstract String title();

    abstract String description();

    @Nullable
    abstract Throwable error();
    public abstract boolean loading();

    public abstract boolean workoutSaved();

    public abstract Builder buildWith();
    public static DoWorkoutViewState idle() {
        return new AutoValue_DoWorkoutViewState.Builder()
                .title("")
                .description("")
                .loading(false)
                .error(null)
                .workoutSaved(false)
                .build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        abstract Builder title(String title);

        abstract Builder description(String description);

        public abstract Builder loading(boolean loading);

        abstract Builder error(@Nullable Throwable error);

        public abstract DoWorkoutViewState build();

        public abstract Builder workoutSaved(boolean saved);
    }
}
