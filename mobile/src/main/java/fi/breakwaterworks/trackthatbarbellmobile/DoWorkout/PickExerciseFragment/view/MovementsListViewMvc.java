package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.Datasource;
import fi.breakwaterworks.trackthatbarbellmobile.common.ObservableViewMvc;

public interface MovementsListViewMvc extends ObservableViewMvc<MovementsListViewMvc.Listener> {

    interface Listener {
        void onMovementClicked(Movement movement);
        void onError(String errorText);
    }

}
