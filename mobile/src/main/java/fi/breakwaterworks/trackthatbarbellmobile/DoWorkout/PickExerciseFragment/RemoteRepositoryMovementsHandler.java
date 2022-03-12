package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment;


import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.server.MovementsService;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.networking.server.response.MovementResponse;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public class RemoteRepositoryMovementsHandler {
    private MovementsService movementsService;

    Listener listener;

    public RemoteRepositoryMovementsHandler(Listener listener, String serverUrl) {
        this.listener = listener;
        movementsService = RetrofitClientInstance.getRetrofitInstance(serverUrl).create(MovementsService.class);
    }

    public interface Listener {
        void onRemoteError(String errorText);

        void returnMovementsFromRemoteDatabase(List<Movement> movements);
    }

    public void loadMovements() {

        if (movementsService == null) {
            this.listener.onRemoteError("Missing remote url");
        }
        //  List<Movement> movements = (List<Movement>) movementsService.getAllMovements();
        Single<List<MovementResponse>> call = movementsService.getAllMovements();
        call.subscribeWith(new DisposableSingleObserver<List<MovementResponse>>() {

            @Override
            public void onError(Throwable e) {
                listener.onRemoteError(e.getMessage());
            }

            @Override
            public void onSuccess(List<MovementResponse> response) {
                List<Movement> movements= new ArrayList<>();
                for (MovementResponse remoteMovement: response) {
                    movements.add(new Movement(remoteMovement));
                }

                listener.returnMovementsFromRemoteDatabase(movements);
            }
        });
    }

    public void LoadMovementsWithNameLike(String query) {
        if (movementsService == null) {
            listener.onRemoteError("Missing remote url");
            return;
        }
        Single<List<Movement>> call = movementsService.getMovementsWithName(query);



        call.subscribeWith(new DisposableSingleObserver<List<Movement>>() {

            @Override
            public void onError(Throwable e) {
                listener.onRemoteError(e.getMessage());
            }

            @Override
            public void onSuccess(List<Movement> response) {
                listener.returnMovementsFromRemoteDatabase(response);
            }
        });
    }
}
