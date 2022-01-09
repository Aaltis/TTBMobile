package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view;


import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.trackthatbarbellmobile.common.ObservableViewMvc;

public interface ExerciseListViewMvc extends ObservableViewMvc<ExerciseListViewMvc.Listener> {

    interface Listener {
        void onOpenAddMovementsClicked();
        void saveWorkout();
        void deleteExercise(Exercise exercise);
    }

    void bindExercises(List<Exercise> exercises);

}
