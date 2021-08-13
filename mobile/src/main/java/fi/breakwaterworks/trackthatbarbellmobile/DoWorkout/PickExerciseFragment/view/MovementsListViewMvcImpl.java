package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view.ExerciseListViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.MovementsRecyclerAdapter;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.listitem.MovementsListItemViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseObservableViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.common.ViewMvcFactory;

public class MovementsListViewMvcImpl extends BaseObservableViewMvc<MovementsListViewMvc.Listener>
        implements MovementsListViewMvc,
        MovementsListItemViewMvc.Listener,
        MovementsRecyclerAdapter.Listener {

    private final RecyclerView exerciseListRecyclerView;
    private final MovementsRecyclerAdapter mAdapter;
    private final SearchView searchViewMenuItem;

    public MovementsListViewMvcImpl(LayoutInflater inflater, ViewGroup parent,
                                    ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.fragment_pick_movement, parent, false));
        exerciseListRecyclerView = findViewById(R.id.recycler_movements);
        exerciseListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MovementsRecyclerAdapter(this, viewMvcFactory);
        exerciseListRecyclerView.setAdapter(mAdapter);
        searchViewMenuItem = findViewById(R.id.searchview_movements);

        searchViewMenuItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (MovementsListViewMvc.Listener listener : getListeners()) {
                    listener.onSearchQuerySubmitted(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) ;// clearSearch();
                return false;
            }
        });
    }

    @Override
    public void onMovementClicked(Movement movement) {
        for (Listener listener : getListeners()) {
            listener.onMovementClicked(movement);
        }
    }

    @Override
    public void bindMovements(List<Movement> movements) {
        mAdapter.bindMovements(movements);
    }

    @Override
    public void showProgressIndication() {

    }

    @Override
    public void hideProgressIndication() {

    }
}
