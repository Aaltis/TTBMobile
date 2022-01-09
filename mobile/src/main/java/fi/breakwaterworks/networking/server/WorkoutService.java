package fi.breakwaterworks.networking.server;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.Workout;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WorkoutService {

    @Headers({"Accept: application/json"})
    @POST("user/workouts")
    Call<List<String>> saveWorkoutForUser(@Header("X-Auth-Token") String token, @Body Workout workout);
}