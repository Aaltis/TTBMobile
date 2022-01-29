package fi.breakwaterworks.networking;

import android.content.Context;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.local.usecase.LoadMovementsUseCase;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.RemoteRepositoryMovementsHandler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MovementsLoader implements RemoteRepositoryMovementsHandler.Listener {
    public Listener movementLoaderListener;

    private RemoteRepositoryMovementsHandler remoteRepositoryMovementsHandler;
    private Context context;
    LoadMovementsUseCase loadMovementsUseCase;

    public interface Listener {
        void ReturnMovements(List<Movement> movements);

        void returnError(String error);
    }

    public MovementsLoader(Context context) {

        this.context = context;
        loadMovementsUseCase = new LoadMovementsUseCase(context);
    }

    public void InitRemoteRepository(String serverUrl) {
        if (serverUrl != null && !serverUrl.isEmpty()) {
            remoteRepositoryMovementsHandler = new RemoteRepositoryMovementsHandler(this, serverUrl);
        }
    }

    /**
     * We load movements from remote or local database
     * return to returnMovements
     */
    public void LoadAllMovements(Datasource datasource) {
        if (remoteRepositoryMovementsHandler != null && datasource != Datasource.LOCAL) {
            remoteRepositoryMovementsHandler.loadMovements();
        } else {
            Single<List<Movement>> observable = loadMovementsUseCase.LoadAllMovements();
            observable.subscribe((new SingleObserver<List<Movement>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                }

                @Override
                public void onSuccess(@NonNull List<Movement> movements) {
                    movementLoaderListener.ReturnMovements(movements);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    movementLoaderListener.returnError(e.getMessage());
                }
            }));
        }
    }

    public void LoadMovementsWithNameLike(Datasource datasource, String query) {
        if (remoteRepositoryMovementsHandler != null && datasource != Datasource.LOCAL) {
            remoteRepositoryMovementsHandler.LoadMovementsWithNameLike(query);
        } else {
            Single<List<Movement>> observable = loadMovementsUseCase.LoadMovementsWithName(query);
            observable.subscribe((new SingleObserver<List<Movement>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                }

                @Override
                public void onSuccess(@NonNull List<Movement> movements) {
                    movementLoaderListener.ReturnMovements(movements);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    movementLoaderListener.returnError(e.getMessage());
                }
            }));
        }
    }

    @Override
    public void onRemoteError(String errorText) {
        movementLoaderListener.returnError(errorText);
    }

    @Override
    public void returnMovementsFromRemoteDatabase(List<Movement> movements) {
        movementLoaderListener.ReturnMovements(movements);
    }
}
