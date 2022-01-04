package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutActivity;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view.ExerciseListViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseFragment;
import fi.breakwaterworks.trackthatbarbellmobile.common.onBackPressedListener;

public class DoWorkoutFragment extends
        BaseFragment implements onBackPressedListener, ExerciseListViewMvc.Listener {
    private ExerciseListViewMvc mViewMvc;
    public DoWorkoutFragment.Listener DoWorkoutFragmentListener;
    public Activity parentActivity;

    public DoWorkoutFragment(DoWorkoutActivity doWorkoutActivity) {
        super();
        parentActivity = doWorkoutActivity;
    }

    public interface Listener {
        void openAddMovementsClicked();
        void onSearchQuerySubmitted(String name);
        void saveWorkout();

    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewMvc = getCompositionRoot().getViewMvcFactory().getExerciseListViewMvc(container, parentActivity);
        mViewMvc.registerListener(this);
        return mViewMvc.getRootView();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof DoWorkoutFragmentViewMvc.DoWorkoutFragmentListener) {
            listener = (DoWorkoutFragmentViewMvc.DoWorkoutFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }*/
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onOpenAddMovementsClicked() {
        DoWorkoutFragmentListener.openAddMovementsClicked();
    }

    @Override
    public void saveWorkout() {
        DoWorkoutFragmentListener.saveWorkout();
    }

    @Override
    public void onSearchQuerySubmitted(String movementName) {
        DoWorkoutFragmentListener.onSearchQuerySubmitted(movementName);
    }

    public void bindExercises(List<Exercise> exercises) {
        mViewMvc.bindExercises(exercises);
    }
}
