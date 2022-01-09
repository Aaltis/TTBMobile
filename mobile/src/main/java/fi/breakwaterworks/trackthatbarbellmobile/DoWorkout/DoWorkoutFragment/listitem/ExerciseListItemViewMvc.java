package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.listitem;

import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.trackthatbarbellmobile.common.ObservableViewMvc;

public interface
ExerciseListItemViewMvc extends ObservableViewMvc<ExerciseListItemViewMvc.Listener> {
    void bindExercise(Exercise exercise);

    void refreshSetRepsWeightList(List<SetRepsWeight> setRepsWeightList);

    interface Listener {
        void DeleteExercise(Exercise exercise);
    }
}
