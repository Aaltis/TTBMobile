package fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM;

import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

import fi.breakwaterworks.mvibase.MviAction;

public interface MainActivityAction extends MviAction {
    @AutoValue
    abstract class Initialization implements MainActivityAction {
        @NonNull
        public static Initialization create() {
            return new AutoValue_MainActivityAction_Initialization();
        }
    }

    @AutoValue
    abstract class OpenDoWorkoutActivity implements MainActivityAction {
        @NonNull
        public static OpenDoWorkoutActivity create() {
            return new AutoValue_MainActivityAction_OpenDoWorkoutActivity();
        }
    }

    @AutoValue
    abstract class UpdateFromServer implements MainActivityAction {
        @NonNull
        public static UpdateFromServer create() {
            return new AutoValue_MainActivityAction_UpdateFromServer();
        }
    }
}
