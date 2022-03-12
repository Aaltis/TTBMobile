package fi.breakwaterworks.networking.server;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.server.response.MovementResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovementsService {

    @GET("movement")
    Single<List<MovementResponse>> getAllMovements();

    @GET("movement")
    Single<List<Movement>> getMovementsWithName(@Query("name") String movementName);

    @GET("movement/updateTime")
    Single<String> getTimeMovementsUpdated();

}
