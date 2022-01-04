package fi.breakwaterworks.trackthatbarbellmobile.Config;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.networking.local.usecase.LoadConfigUseCase;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class ConfigActivity extends AppCompatActivity {

    LoadConfigUseCase loadConfigUseCase;

    Button buttonCreateOrLoginUser;
    TextView textViewUrl;
    TextView textViewToken;
    CheckBox checkBoxSaveLocallyAlways;
    CheckBox checkBoxSaveRemoteAlways;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        buttonCreateOrLoginUser = findViewById(R.id.buttonCreateUserOrLogin);
        textViewUrl = findViewById(R.id.textViewUrlShowHere);
        textViewToken = findViewById(R.id.textViewTokenSavedShowhere);
        textViewToken = findViewById(R.id.textViewTokenSavedShowhere);
        checkBoxSaveLocallyAlways = findViewById(R.id.checkBoxSaveLocallyAlways);
        checkBoxSaveRemoteAlways = findViewById(R.id.checkBoxSaveRemoteAlways);

        loadConfigUseCase = new LoadConfigUseCase(this);
        init();
    }

    private void init() {
        Single<Config> observable = loadConfigUseCase.Load();
        observable.subscribe((new SingleObserver<Config>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Config config) {
                textViewUrl.setText(config.getServerUrl());
                textViewToken.setText(config.getToken());
                checkBoxSaveLocallyAlways.setChecked(config.isSaveAlwaysLocally());
                checkBoxSaveRemoteAlways.setChecked(config.isSaveAlwaysRemote());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        }));
    }

    public void ChangeToCreateUserActivity(View view) {
        Context context = ConfigActivity.this;
        Intent intent = new Intent(context, AuthenticationActivity.class);
        context.startActivity(intent);
    }
}