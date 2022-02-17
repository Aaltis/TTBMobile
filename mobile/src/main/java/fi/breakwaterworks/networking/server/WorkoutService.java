package fi.breakwaterworks.networking.server;

import fi.breakwaterworks.model.Workout;

import fi.breakwaterworks.networking.server.response.WorkoutCreatedResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WorkoutService {

    @Headers({"Accept: application/json"})
    @POST("user/workouts")
    Observable<WorkoutCreatedResponse> saveWorkoutForUser(@Header("X-Auth-Token") String token, @Body Workout workout);
}
