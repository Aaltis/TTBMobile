package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.Datasource;
import fi.breakwaterworks.networking.MovementsLoader;
import fi.breakwaterworks.networking.local.usecase.LoadConfigUseCase;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.MovementsRecyclerAdapter;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.listitem.MovementsListItemViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseObservableViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.common.ViewMvcFactory;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MovementsListViewMvcImpl extends BaseObservableViewMvc<MovementsListViewMvc.Listener>
        implements MovementsListViewMvc,
        MovementsListItemViewMvc.Listener,
        MovementsRecyclerAdapter.Listener,
        MovementsLoader.Listener {

    private MovementsLoader movementsLoader;
    private final RecyclerView exerciseListRecyclerView;
    private final MovementsRecyclerAdapter mAdapter;
    private final SearchView searchViewMenuItem;
    private Config temporaryConfig;
    private RadioGroup movementDataSourceRadioGroup;

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
                movementsLoader.LoadMovementsWithNameLike(temporaryConfig.getDataSource(), query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    movementsLoader.LoadMovementsWithNameLike(temporaryConfig.getDataSource(), newText);
                    return true;
                }else{
                    movementsLoader.LoadAllMovements(temporaryConfig.getDataSource());
                }
                return false;
            }
        });

        movementDataSourceRadioGroup = findViewById(R.id.radioGroupDataSource);
        movementDataSourceRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_remote:
                    movementsLoader.LoadAllMovements(Datasource.REMOTE);
                    searchViewMenuItem.setQuery("", false);
                    searchViewMenuItem.clearFocus();
                    break;
                case R.id.radio_local:
                    movementsLoader.LoadAllMovements(Datasource.LOCAL);
                    searchViewMenuItem.setQuery("", false);
                    searchViewMenuItem.clearFocus();
                    break;
            }
        });
        movementsLoader = new MovementsLoader(getContext());
        movementsLoader.movementLoaderListener = this;
        loadConfigAndInitRetrofit();
    }

    @Override
    public void onMovementClicked(Movement movement) {
        for (Listener listener : getListeners()) {
            listener.onMovementClicked(movement);
        }
    }

    private void loadConfigAndInitRetrofit() {
        LoadConfigUseCase loadConfigUseCase = new LoadConfigUseCase(getContext());
        Single<Config> observable = loadConfigUseCase.Load();
        temporaryConfig = new Config(Datasource.LOCAL);
        observable.subscribe((new SingleObserver<Config>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull Config config) {
                temporaryConfig = config;
                if (config.getServerUrl() != null) {
                    movementsLoader.InitRemoteRepository(config.getServerUrl());
                    movementDataSourceRadioGroup.check(R.id.radio_remote);
                    return;
                }
                findViewById(R.id.radio_remote).setEnabled(false);
                movementDataSourceRadioGroup.check(R.id.radio_local);
                movementsLoader.LoadAllMovements(config.getDataSource());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                for (Listener listener : getListeners()) {
                    listener.onError(e.toString());
                }
            }
        }));
    }

    @Override
    public void ReturnMovements(List<Movement> movements) {
        mAdapter.bindMovements(movements);
    }

    @Override
    public void returnError(String error) {

    }

}
