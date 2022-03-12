package fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM;


import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import fi.breakwaterworks.mvibase.MviViewState;


@AutoValue
public abstract class MainActivityViewState implements MviViewState {

    abstract String title();

    abstract String description();

    @Nullable
    public abstract Throwable error();
    public abstract boolean loading();

    public abstract boolean goToTemplates();
    public abstract boolean goToDoWorkoutView();
    public abstract boolean initializationStarted();
    public abstract boolean initializationEnded();
    public abstract MainActivityViewState.Builder buildWith();
    public static MainActivityViewState idle() {
        return new AutoValue_MainActivityViewState.Builder()
                .title("")
                .description("")
                .loading(false)
                .goToDoWorkoutView(false)
                .goToTemplates(false)
                .error(null)
                .initializationStarted(false)
                .initializationEnded(false)
                .build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        abstract MainActivityViewState.Builder title(String title);

        abstract MainActivityViewState.Builder description(String description);

        public abstract MainActivityViewState.Builder loading(boolean loading);

        public abstract MainActivityViewState.Builder error(@Nullable Throwable error);
        public abstract MainActivityViewState build();
        public abstract MainActivityViewState.Builder goToTemplates(boolean gotoTemplates);
        public abstract MainActivityViewState.Builder goToDoWorkoutView(boolean gotoTemplates);
        public abstract MainActivityViewState.Builder initializationStarted(boolean initializationStarted);
        public abstract MainActivityViewState.Builder initializationEnded(boolean initializationEnded);

    }
}
