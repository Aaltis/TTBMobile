package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseFragment;

public class PickMovementFragment extends BaseFragment implements MovementsListViewMvc.Listener {

    private List<Movement> movements;

    public Listener pickExerciseFragmentListener;


    public interface Listener {
        void onMovementClicked(Movement movement);

        void onSearchQuerySubmitted(String query);
    }

    public PickMovementFragment(List<Movement> movements) {
        this.movements = movements;
    }

    MovementsListViewMvc viewMvc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewMvc = getCompositionRoot().getViewMvcFactory().getMovementsListViewMvc(container);

        viewMvc.registerListener(this);
        return viewMvc.getRootView();
    }

    //bind straight to activity.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Listener) {
            pickExerciseFragmentListener = (Listener) activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement OnSearchListener interface");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        viewMvc.bindMovements(movements);
    }


    @Override
    public void onMovementClicked(Movement movement) {
        pickExerciseFragmentListener.onMovementClicked(movement);
    }

    @Override
    public void onSearchQuerySubmitted(String query) {
        pickExerciseFragmentListener.onSearchQuerySubmitted(query);
    }

    public void bindMovements(List<Movement> movements) {
        viewMvc.bindMovements(movements);
    }

}
