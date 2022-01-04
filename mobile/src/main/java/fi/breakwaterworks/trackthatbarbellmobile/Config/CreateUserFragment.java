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

import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.networking.server.response.ServerResponse;
import fi.breakwaterworks.networking.server.request.UserRegisterRequest;
import fi.breakwaterworks.networking.server.UserService;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserFragment extends Fragment {
    private TextView textViewServerUrl;
    private TextView textViewPassword;
    private TextView textViewRePassword;
    private TextView textViewUserName;
    private UserService userService;
    private AuthenticationActivity parentActivity;

    public CreateUserFragment(AuthenticationActivity parentActivity) {
        this.parentActivity = parentActivity;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
        textViewServerUrl = view.findViewById(R.id.editTextServerUrl);
        textViewUserName = view.findViewById(R.id.editTextUserName);
        textViewPassword = view.findViewById(R.id.editTextPassword);
        textViewRePassword = view.findViewById(R.id.editTextRePassword);
        Button createUserButton = view.findViewById(R.id.buttonCreateUser);
        createUserButton.setOnClickListener(v -> createUser());
        TextView textviewToLogin = view.findViewById(R.id.textViewChangeToLogin);
        textviewToLogin.setOnClickListener(v -> parentActivity.ChangeToLogin(null, null, null));
        return view;
    }

    public void createUser() {
        if (textViewServerUrl.getText().toString() == null || textViewServerUrl.getText().toString().isEmpty()) {
            Toast.makeText(parentActivity, "Add server url", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            this.userService = RetrofitClientInstance.getRetrofitInstance(textViewServerUrl.getText().toString()).create(UserService.class);
        }catch (Exception ex){
            Toast.makeText(parentActivity, "bad url", Toast.LENGTH_LONG).show();
            return;
        }
        String g=textViewServerUrl.getText().toString();
        Call<ServerResponse> call = userService.registerUser(new UserRegisterRequest(textViewUserName.getText().toString(),
                textViewPassword.getText().toString(),
                textViewRePassword.getText().toString()));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(parentActivity, "User Created", Toast.LENGTH_LONG).show();

                    parentActivity.ChangeToLogin(textViewRePassword.getText().toString(), textViewPassword.getText().toString(),textViewServerUrl.getText().toString());
                } else {
                    Gson gson = new Gson();
                    ServerResponse message = gson.fromJson(response.errorBody().charStream(),
                            ServerResponse.class);
                    Toast.makeText(parentActivity, message.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(parentActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}