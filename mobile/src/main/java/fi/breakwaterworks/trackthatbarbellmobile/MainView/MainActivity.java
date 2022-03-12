package fi.breakwaterworks.trackthatbarbellmobile.MainView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding3.view.RxView;

import fi.breakwaterworks.mvibase.MviView;
import fi.breakwaterworks.networking.local.repository.WorkLogRepository;
import fi.breakwaterworks.trackthatbarbellmobile.Config.ConfigActivity;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutActivity;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.SchedulerProvider;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityIntent;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityViewState;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.Processors.LocalInitializationProcessor;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.Processors.RemoteUpdateProcess;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList.WorkoutTemplatesListActivity;
import fi.breakwaterworks.utility.TextParser;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements MviView<MainActivityIntent, fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityViewState> {
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private MainActivityViewModel mainActivityViewModel;
    Button buttonWorkoutTemplates;
    Button buttonDoWorkout;
    Button buttonUpdateFromServer;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialog = new LoadingDialog(this);
        this.buttonWorkoutTemplates = findViewById(R.id.btnWorkoutTemplates);
        this.buttonDoWorkout = findViewById(R.id.btnDoWorkout);
        this.buttonUpdateFromServer = findViewById(R.id.btnUpdateFromServer);
        mainActivityViewModel = new MainActivityViewModel(
                new MainActivityActionProcessHolder(SchedulerProvider.getInstance(),
                        new LocalInitializationProcessor(TTBDatabase.getInstance(this),  new TextParser(this),this, new WorkLogRepository(TTBDatabase.getInstance(this))),
                new RemoteUpdateProcess(TTBDatabase.getInstance(this),this,  new WorkLogRepository(TTBDatabase.getInstance(this)))
                )
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        bind();
    }

    @Override
    public void onResume(){
        super.onResume();

    }
    public void bind() {
        try {
            mDisposables.add(mainActivityViewModel.states().subscribe(this::render));
            mainActivityViewModel.processIntents(intents());
        } catch (Exception ex) {
            Log.d(this.getLocalClassName(), ex.getLocalizedMessage());
        }
    }

//region Rendering

    @Override
    public void render(MainActivityViewState viewState) {

        if (viewState.goToDoWorkoutView()) {
            Log.d(this.getLocalClassName(), "render");
            Context context = MainActivity.this;
            Intent intent = new Intent(context, DoWorkoutActivity.class);
            context.startActivity(intent);
        }
        if (viewState.initializationStarted()) {
            loadingDialog.show();
        }

        if (viewState.initializationEnded()) {
            runOnUiThread(() -> loadingDialog.hide());
        }
        if (viewState.error() != null) {
            Log.e("error", viewState.error().getLocalizedMessage());
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(viewState.error().getLocalizedMessage())

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

//endregion

//region Intents

    @Override
    public Observable<MainActivityIntent> intents() {
        return Observable.merge(initialIntent(), openDoWorkout(), UpdateFromServer());
    }

    private Observable<MainActivityIntent.InitialIntent> initialIntent() {
        return Observable.just(new MainActivityIntent.InitialIntent().create());
    }

    private Observable<MainActivityIntent.DoWorkoutActivity> openDoWorkout() {
        return RxView.clicks(buttonDoWorkout).map(ignored ->
                new MainActivityIntent.DoWorkoutActivity().create());
    }

    private Observable<MainActivityIntent.UpdateFromServer> UpdateFromServer() {
        return RxView.clicks(buttonUpdateFromServer).map(ignored ->
                new MainActivityIntent.UpdateFromServer().create());
    }

    public void ChangeToConfigActivity(View view) {
        Context context = MainActivity.this;
        Intent intent = new Intent(context, ConfigActivity.class);
        context.startActivity(intent);
    }

    public void ChangeToWorkoutTemplates(View view) {
        Context context = MainActivity.this;
        Intent intent = new Intent(context, WorkoutTemplatesListActivity.class);
        context.startActivity(intent);
    }
//endregion
}
