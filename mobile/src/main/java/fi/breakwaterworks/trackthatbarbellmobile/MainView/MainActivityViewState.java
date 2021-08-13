package fi.breakwaterworks.trackthatbarbellmobile.MainView;


import fi.breakwaterworks.mvibase.MviViewState;

public interface MainActivityViewState extends MviViewState {

    final class GoToWorkoutTemplatesListActivity implements MainActivityViewState {
        public GoToWorkoutTemplatesListActivity() {
        }
    }
    final class DoWorkoutActivityState implements MainActivityViewState {
        public DoWorkoutActivityState() {
        }
    }
    class DatabaseSave implements MainActivityViewState {

        public static class Success extends DatabaseSave {
            String message;

            public Success(String message) {
                this.message = message;
            }
        }

        public static class Init extends DatabaseSave {
        }

        public static class Error extends DatabaseSave {
            Throwable error;

            public Error(Throwable throwable) {
                this.error = throwable;
            }
        }
    }


}
