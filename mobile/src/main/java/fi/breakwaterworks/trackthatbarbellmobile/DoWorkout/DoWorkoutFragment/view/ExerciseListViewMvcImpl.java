package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseObservableViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.common.ViewMvcFactory;

public class ExerciseListViewMvcImpl extends BaseObservableViewMvc<ExerciseListViewMvc.Listener> implements ExerciseRecyclerAdapter.Listener,
        ExerciseListViewMvc {

    private final RecyclerView exerciseListRecyclerView;
    private final ExerciseRecyclerAdapter mAdapter;

    public ExerciseListViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent, Activity parentActivity, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.do_workout_fragment, parent, false));
        exerciseListRecyclerView = findViewById(R.id.recycler_exercise);
        exerciseListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ExerciseRecyclerAdapter(this,parentActivity, viewMvcFactory);
        exerciseListRecyclerView.setAdapter(mAdapter);
        exerciseListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        findViewById(R.id.btnAddExercise).setOnClickListener(view1 -> {
            for (ExerciseListViewMvc.Listener listener : getListeners()) {
                listener.onOpenAddMovementsClicked();
            }
        });
        findViewById(R.id.btnSaveExercise).setOnClickListener(view1 -> {
            for (ExerciseListViewMvc.Listener listener : getListeners()) {
                listener.saveWorkout();
            }
        });
    }

    @Override
    public void onExerciseClicked(Movement movement) {
    }

    @Override
    public void bindExercises(List<Exercise> exercises) {
        mAdapter.bindExercises(exercises);
    }

    @Override
    public void DeleteExercise(Exercise exercise) {
        for (ExerciseListViewMvc.Listener listener : getListeners()) {
            listener.deleteExercise(exercise);
        }
    }
}
