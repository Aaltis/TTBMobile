package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment;


import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.server.MovementsService;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view.PickMovementFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepositoryMovementsHandler {
    private MovementsService movementsService;

    Listener listener;

    public RemoteRepositoryMovementsHandler(PickMovementFragment pickMovementFragment, Config config) {
        this.listener = pickMovementFragment;
        movementsService = RetrofitClientInstance.getRetrofitInstance(config.getServerUrl()).create(MovementsService.class);
    }

    public interface Listener {
        void onError(String errorText);
        void returnMovementsFromLocalOrRemoteDatabase(List<Movement> movements);
    }

    public void loadMovements() {

        if (movementsService == null) {
            this.listener.onError("Missing remote url");
        }

        Call<List<Movement>> call = movementsService.getAllMovements();
        call.enqueue(new Callback<List<Movement>>() {
            @Override
            public void onResponse(Call<List<Movement>> call, Response<List<Movement>> response) {
                listener.returnMovementsFromLocalOrRemoteDatabase(response.body());
            }

            @Override
            public void onFailure(Call<List<Movement>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void onSearchQuerySubmitted(String query) {
        if (movementsService == null) {
            listener.onError("Missing remote url");
            return;
        }
        Call<List<Movement>> call = movementsService.getMovementsWithName(query);
        call.enqueue(new Callback<List<Movement>>() {
            @Override
            public void onResponse(Call<List<Movement>> call, Response<List<Movement>> response) {
                listener.returnMovementsFromLocalOrRemoteDatabase(response.body());
            }

            @Override
            public void onFailure(Call<List<Movement>> call, Throwable t) {
                listener.onError(t.getMessage());
            }

        });
    }
}
