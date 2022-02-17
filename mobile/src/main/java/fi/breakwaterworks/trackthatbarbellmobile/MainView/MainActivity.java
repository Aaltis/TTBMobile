package fi.breakwaterworks.trackthatbarbellmobile.MainView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding3.view.RxView;

import fi.breakwaterworks.mvibase.MviView;
import fi.breakwaterworks.trackthatbarbellmobile.Config.ConfigActivity;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutActivity;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList.WorkoutTemplatesListActivity;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements MviView<MainActivityIntent, MainActivityViewState> {
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private MainActivityPresenter mainActivityPresenter;
    Button buttonWorkoutTemplates;
    Button buttonDoWorkout;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialog = new LoadingDialog(this);
        this.buttonWorkoutTemplates = findViewById(R.id.btnWorkoutTemplates);
        this.buttonDoWorkout = findViewById(R.id.btnDoWorkout);
        mainActivityPresenter = new MainActivityPresenter(MainActivity.this);
    }

    @Override
    public void onStart() {
        super.onStart();
        bind();
    }

    public void bind() {
        try {
            mDisposables.add(mainActivityPresenter.states().subscribe(this::render));
            mainActivityPresenter.processIntents(intents());
            mainActivityPresenter.databaseInitiatorIteractor.initDatabase().subscribe(state -> this.render(state));
        } catch (Exception ex) {
            Log.d(this.getLocalClassName(), ex.getLocalizedMessage());
        }
    }

//region Rendering

    @Override
    public void render(MainActivityViewState viewState) {

        if (viewState instanceof MainActivityViewState.GoToWorkoutTemplatesListActivity) {
            Log.d(this.getLocalClassName(), "render");
            Context context = MainActivity.this;
            Intent intent = new Intent(context, WorkoutTemplatesListActivity.class);
            context.startActivity(intent);
        }
        if (viewState instanceof MainActivityViewState.DoWorkoutActivityState) {
            Log.d(this.getLocalClassName(), "render");
            Context context = MainActivity.this;
            Intent intent = new Intent(context, DoWorkoutActivity.class);
            context.startActivity(intent);
        }
        if (viewState instanceof MainActivityViewState.DatabaseSave.Init) {
            loadingDialog.show();
        }

        if (viewState instanceof MainActivityViewState.DatabaseSave.Success) {
            Log.d(this.getLocalClassName(), ((MainActivityViewState.DatabaseSave.Success) viewState).message);
            runOnUiThread(() -> loadingDialog.hide());
        }
        if (viewState instanceof MainActivityViewState.DatabaseSave.Error) {
            Log.e(this.getLocalClassName(), ((MainActivityViewState.DatabaseSave.Error) viewState).error.getLocalizedMessage());
        }
    }

//endregion

//region Intents

    @Override
    public Observable<MainActivityIntent> intents() {
        return Observable.merge(initialIntent(), openWorkoutTemplateList(), openDoWorkout());
    }

    private Observable<MainActivityIntent.InitialIntent> initialIntent() {
        return Observable.just(new MainActivityIntent.InitialIntent().create());
    }

    private Observable<MainActivityIntent.OpenWorkoutTemplatesListActivity> openWorkoutTemplateList() {
        return RxView.clicks(buttonWorkoutTemplates).map(ignored ->
                new MainActivityIntent.OpenWorkoutTemplatesListActivity().create());
    }

    private Observable<MainActivityIntent.DoWorkoutActivity> openDoWorkout() {
        return RxView.clicks(buttonDoWorkout).map(ignored ->
                new MainActivityIntent.DoWorkoutActivity().create());
    }

    public void ChangeToConfigActivity(View view) {
        Context context = MainActivity.this;
        Intent intent = new Intent(context, ConfigActivity.class);
        context.startActivity(intent);
    }
//endregion
}
