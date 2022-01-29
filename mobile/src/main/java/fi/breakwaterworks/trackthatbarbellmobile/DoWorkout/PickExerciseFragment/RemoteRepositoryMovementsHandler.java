package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment;


import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.server.MovementsService;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Call<List<Movement>> call = movementsService.getAllMovements();
        call.enqueue(new Callback<List<Movement>>() {
            @Override
            public void onResponse(Call<List<Movement>> call, Response<List<Movement>> response) {
                listener.returnMovementsFromRemoteDatabase(response.body());
            }

            @Override
            public void onFailure(Call<List<Movement>> call, Throwable t) {
                listener.onRemoteError(t.getMessage());
            }
        });
    }

    public void LoadMovementsWithNameLike(String query) {
        if (movementsService == null) {
            listener.onRemoteError("Missing remote url");
            return;
        }
        Call<List<Movement>> call = movementsService.getMovementsWithName(query);
        call.enqueue(new Callback<List<Movement>>() {
            @Override
            public void onResponse(Call<List<Movement>> call, Response<List<Movement>> response) {
                listener.returnMovementsFromRemoteDatabase(response.body());
            }

            @Override
            public void onFailure(Call<List<Movement>> call, Throwable t) {
                listener.onRemoteError(t.getMessage());
            }

        });
    }
}
