package fi.breakwaterworks.trackthatbarbellmobile.Config;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import fi.breakwaterworks.networking.server.request.AuthenticationRequest;
import fi.breakwaterworks.networking.server.response.AuthenticationResponse;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.networking.server.response.ServerResponse;
import fi.breakwaterworks.networking.server.UserService;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private static final String ARG_PASSWORD = "password";
    private static final String ARG_SERVER_URL = "serverUrl";

    private String username;
    private String password;
    private String serverUrl;
    public AuthenticationActivity parentActivity;
    private UserService userService;

    private TextView textViewUserName;
    private TextView textViewPassword;
    private TextView textViewServerUrl;

    public LoginFragment() {
    }


    public static LoginFragment newInstance(String username, String password, String serverUrl) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_PASSWORD, password);
        args.putString(ARG_SERVER_URL, serverUrl);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            password = getArguments().getString(ARG_PASSWORD);
            serverUrl = getArguments().getString(ARG_SERVER_URL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        textViewServerUrl = view.findViewById(R.id.editTextServerUrl);
        textViewUserName = view.findViewById(R.id.editTextUserName);
        textViewPassword = view.findViewById(R.id.editTextPassword);
        if (username != null) {
            textViewUserName.setText(username);
        }
        if (password != null) {
            textViewPassword.setText(password);
        }
        if (serverUrl != null) {
            textViewServerUrl.setText(serverUrl);
        }
        if (username != null && password != null && serverUrl != null) {
            LoginUser(username, password,serverUrl);

        }
        Button createUserButton = view.findViewById(R.id.buttonLogin);
        createUserButton.setOnClickListener(v -> LoginUser(textViewUserName.getText().toString(), textViewPassword.getText().toString(),textViewServerUrl.getText().toString()));
        return view;
    }

    private void LoginUser(String username, String password, String serverUrl) {
        this.userService = RetrofitClientInstance.getRetrofitInstance(serverUrl).create(UserService.class);

        Call<AuthenticationResponse> call = userService.loginUser(new AuthenticationRequest(
                username,
                password));
        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(parentActivity, "Login successful", Toast.LENGTH_LONG).show();
                    parentActivity.SaveTokenToDatabase(response.body().getToken(),serverUrl);
                } else {
                    Gson gson = new Gson();
                    ServerResponse message = gson.fromJson(response.errorBody().charStream(),
                            ServerResponse.class);
                    //Toast.makeText(parentActivity, message.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Toast.makeText(parentActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}