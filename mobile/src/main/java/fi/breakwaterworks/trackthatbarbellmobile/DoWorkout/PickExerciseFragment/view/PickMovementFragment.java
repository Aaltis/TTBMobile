package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.local.usecase.LoadConfigUseCase;
import fi.breakwaterworks.networking.local.usecase.LoadMovementsUseCase;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.RemoteRepositoryMovementsHandler;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseFragment;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class PickMovementFragment extends BaseFragment implements
        MovementsListViewMvc.Listener,
        RemoteRepositoryMovementsHandler.Listener {

    public Listener pickExerciseFragmentListener;
    private RemoteRepositoryMovementsHandler remoteRepositoryMovementsHandler;


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
        loadConfigAndInitRetrofit();
    }

    private void loadConfigAndInitRetrofit() {
        LoadConfigUseCase loadConfigUseCase = new LoadConfigUseCase(getContext());
        Single<Config> observable = loadConfigUseCase.Load();
        observable.subscribe((new SingleObserver<Config>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull Config config) {
                CallLoadMovementsFromLocalOrRemoteDatabase(config);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                CallLoadMovementsFromLocalOrRemoteDatabase(null);
            }
        }));
    }

    /**
     * We load movements from remote or local database
     * return to retunrMovements
     */
    private void CallLoadMovementsFromLocalOrRemoteDatabase(Config config) {
        if (config != null && config.getServerUrl() != null) {
            remoteRepositoryMovementsHandler = new RemoteRepositoryMovementsHandler(this, config);
            remoteRepositoryMovementsHandler.loadMovements();
        } else {
            LoadMovementsUseCase loadMovementsUseCase = new LoadMovementsUseCase(getContext());
            Single<List<Movement>> observable = loadMovementsUseCase.LoadAllMovements();
            observable.subscribe((new SingleObserver<List<Movement>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                }

                @Override
                public void onSuccess(@NonNull List<Movement> movements) {
                    returnMovementsFromLocalOrRemoteDatabase(movements);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    pickExerciseFragmentListener.ToastError(e.getMessage());
                }
            }));
        }
    }

    @Override
    public void returnMovementsFromLocalOrRemoteDatabase(List<Movement> movements) {
        viewMvc.bindMovements(movements);
    }

    @Override
    public void onMovementClicked(Movement movement) {
        pickExerciseFragmentListener.onMovementClicked(movement);
    }

    @Override
    public void onSearchQuerySubmitted(String query) {
        remoteRepositoryMovementsHandler.onSearchQuerySubmitted(query);
    }

    @Override
    public void onError(String errorText) {
        pickExerciseFragmentListener.ToastError(errorText);
    }


}
