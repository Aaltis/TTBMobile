package fi.breakwaterworks.trackthatbarbellmobile.Config;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import fi.breakwaterworks.networking.local.usecase.SaveToken;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MainActivity;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        CreateUserFragment fragment;
        if (savedInstanceState == null) {
            fragment = new CreateUserFragment(this);
        } else {
            fragment = (CreateUserFragment) getSupportFragmentManager().findFragmentById(R.id.authentication_activity_framelayout);
        }
        ft.replace(R.id.authentication_activity_framelayout, fragment, "CREATE_USER_FRAGMENT_TAG");
        ft.commit();

    }

    public void ChangeToLogin(String username, String password, String serverUrl) {
        LoginFragment newFragment = LoginFragment.newInstance(username, password, serverUrl);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        newFragment.parentActivity = this;
        transaction.replace(R.id.authentication_activity_framelayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void SaveTokenToDatabase(String token, String url) {
        SaveToken saveTokenUsecase = new SaveToken(this);
        Observable<String> o = saveTokenUsecase.saveToken(token,url);
        o.subscribe((new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Toast.makeText(AuthenticationActivity.this, s, Toast.LENGTH_LONG).show();
                Context context = AuthenticationActivity.this;
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        }));

    }

    //TODO move to usecase later, dont use asynctask because deprecated.
    //this exist because we cant do database query in main thread.
    /*private class SaveToken extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... strings) {
            android.os.Debug.waitForDebugger();
            List<Config> configs = TTBDatabase.getInstance(getApplicationContext()).ConfigDAO().loadConfigs();
            //We want to use only one config, delete all others.
            if (configs.size() == 1) {
                TTBDatabase.getInstance(getApplicationContext()).ConfigDAO().updateToken(strings[0], configs.get(0).getConfigId());
                return "Token Saved.";
            }
            if (configs.size() == 0) {
                TTBDatabase.getInstance(getApplicationContext()).ConfigDAO().insert(new Config(strings[0], strings[1]));
                return "Token Saved.";
            }
            return "fug";
        }

        protected void onPostExecute(String result) {
            Toast.makeText(AuthenticationActivity.this, result, Toast.LENGTH_LONG).show();
            Context context = AuthenticationActivity.this;
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }*/
}
