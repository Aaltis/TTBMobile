package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.Datasource;
import fi.breakwaterworks.networking.local.usecase.LoadConfigUseCase;
import fi.breakwaterworks.networking.local.usecase.LoadMovementsUseCase;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.RemoteRepositoryMovementsHandler;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseFragment;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class PickMovementFragment extends BaseFragment implements
        MovementsListViewMvc.Listener {

    public Listener pickExerciseFragmentListener;

    public interface Listener {
        void onMovementClicked(Movement movement);

        void ToastError(String error);
    }

    public PickMovementFragment() {
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onMovementClicked(Movement movement) {
        pickExerciseFragmentListener.onMovementClicked(movement);
    }

    @Override
    public void onError(String errorText) {
        pickExerciseFragmentListener.ToastError(errorText);
    }

}
