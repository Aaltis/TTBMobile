package fi.breakwaterworks.networking.server;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovementsService {

    @GET("movement")
    Call<List<Movement>> getAllMovements();
    @GET("movement")
    Call<List<Movement>> getMovementsWithName(@Query("name") String movementName);
}
