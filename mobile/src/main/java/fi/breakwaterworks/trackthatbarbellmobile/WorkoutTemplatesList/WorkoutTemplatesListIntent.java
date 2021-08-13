package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;

import androidx.annotation.Nullable;

import fi.breakwaterworks.mvibase.MviIntent;

interface WorkoutTemplatesListIntent extends MviIntent {
    class InitialIntent implements WorkoutTemplatesListIntent {

        @Nullable
        public InitialIntent create() {
            return this;
        }
    }

    class OpenWorkoutTemplatesInitializationActivity implements WorkoutTemplatesListIntent {
        @Nullable
        public OpenWorkoutTemplatesInitializationActivity create() {
            return this;
        }
    }

}
