package fi.breakwaterworks.networking.server;

import fi.breakwaterworks.networking.server.request.AuthenticationRequest;
import fi.breakwaterworks.networking.server.request.UserRegisterRequest;
import fi.breakwaterworks.networking.server.response.AuthenticationResponse;
import fi.breakwaterworks.networking.server.response.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("register")
    Call<ServerResponse> registerUser(@Body UserRegisterRequest body);
    @POST("login")
    Call<AuthenticationResponse> loginUser(@Body AuthenticationRequest body);
}
