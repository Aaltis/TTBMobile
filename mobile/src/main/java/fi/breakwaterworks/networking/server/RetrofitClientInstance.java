package fi.breakwaterworks.networking.server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    //TODO user has to set this or hide it somewhere.
    private static final String BASE_URL = "10.0.2.2:8081/api/";
    //"http://breakwaterworks.redirectme.net:8081/api/";
    public static Retrofit getRetrofitInstance(String url) {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}