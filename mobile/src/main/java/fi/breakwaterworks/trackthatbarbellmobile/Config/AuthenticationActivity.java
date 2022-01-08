package fi.breakwaterworks.trackthatbarbellmobile.Config;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.networking.local.usecase.SaveToken;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MainActivity;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class AuthenticationActivity extends AppCompatActivity implements LoginFragment.Listener, CreateUserFragment.Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        CreateUserFragment createUserFragment;
        if (savedInstanceState == null) {
            createUserFragment = new CreateUserFragment();
        } else {
            createUserFragment = (CreateUserFragment) getSupportFragmentManager().findFragmentById(R.id.authentication_activity_framelayout);
        }
        assert createUserFragment != null;
        createUserFragment.listener = this;
        ft.replace(R.id.authentication_activity_framelayout, createUserFragment, "CREATE_USER_FRAGMENT_TAG");
        ft.commit();
    }

    public void ChangeToLogin(String username, String password, String serverUrl) {
        LoginFragment newLoginFragment = LoginFragment.newInstance(username, password, serverUrl);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        newLoginFragment.listener = this;
        transaction.replace(R.id.authentication_activity_framelayout, newLoginFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void saveTokenAndUrlToLocalDatabase(String token, String url) {
        SaveToken saveTokenUseCase = new SaveToken(this);
        Observable<String> o = saveTokenUseCase.saveToken(token, url);
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

    @Override
    public void toastMessage(String message) {
        Toast.makeText( AuthenticationActivity.this, message, Toast.LENGTH_LONG).show();

    }
}
