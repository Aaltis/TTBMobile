package fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM;


import androidx.annotation.Nullable;
import fi.breakwaterworks.mvibase.MviIntent;

public interface MainActivityIntent extends MviIntent {

    class InitialIntent implements MainActivityIntent {

        @Nullable
        public InitialIntent create() {
            return this;
        }
    }

    class DoWorkoutActivity implements MainActivityIntent {
        @Nullable
        public DoWorkoutActivity create() {
            return this;
        }
    }

    class UpdateFromServer implements MainActivityIntent {
        @Nullable
        public UpdateFromServer create() {
            return this;
        }
    }
}