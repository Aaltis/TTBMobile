package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view;


import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.trackthatbarbellmobile.common.ObservableViewMvc;

public interface ExerciseListViewMvc extends ObservableViewMvc<ExerciseListViewMvc.Listener> {
    public interface Listener {
        void onOpenAddMovementsClicked();
        void saveWorkout();

        public void onSearchQuerySubmitted(String movementName);

    }

    void bindExercises(List<Exercise> exercises);

}
