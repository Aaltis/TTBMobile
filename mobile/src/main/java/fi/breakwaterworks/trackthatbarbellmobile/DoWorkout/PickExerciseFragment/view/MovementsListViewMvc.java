package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.common.ObservableViewMvc;

public interface MovementsListViewMvc extends ObservableViewMvc<MovementsListViewMvc.Listener> {

    public interface Listener {
        void onMovementClicked(Movement movement);

        void onSearchQuerySubmitted(String query);
    }

    void bindMovements(List<Movement> questions);

    void showProgressIndication();

    void hideProgressIndication();

}
