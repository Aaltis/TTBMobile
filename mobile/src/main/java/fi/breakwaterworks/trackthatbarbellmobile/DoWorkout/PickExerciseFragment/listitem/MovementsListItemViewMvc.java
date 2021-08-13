package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.listitem;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.common.ObservableViewMvc;

public interface MovementsListItemViewMvc extends ObservableViewMvc<MovementsListItemViewMvc.Listener> {

    void bindMovement(Movement movement);

    public interface Listener {
        void onMovementClicked(Movement movement);
    }

}
