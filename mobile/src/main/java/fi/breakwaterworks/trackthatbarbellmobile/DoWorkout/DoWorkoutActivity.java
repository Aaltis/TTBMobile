package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.mvibase.MviView;
import fi.breakwaterworks.networking.local.repository.ConfigRepository;
import fi.breakwaterworks.networking.local.usecase.LoadConfigUseCase;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.networking.server.WorkoutService;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutFragment;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutIntent;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutViewState;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view.PickMovementFragment;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MainActivity;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class DoWorkoutActivity extends FragmentActivity
        implements DoWorkoutFragment.Listener,
        PickMovementFragment.Listener,
        LeaveWorkoutDialog.LeaveWorkoutDialogListener,
        MviView<DoWorkoutIntent, DoWorkoutViewState> {

    private WorkoutService workoutService;
    ConfigRepository configRepository;
    private static final String DO_WORKOUT_FRAGMENT_TAG = "DoWorkoutFragment";
    private static final String FIND_MOVEMENT_FRAGMENT_TAG = "FindMovementFragment";
    LoadConfigUseCase loadConfigUseCase;
    private String token;
    public LeaveWorkoutDialog leaveWorkoutDialog;

    //TODO what could be better way to store this?
    public List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exercises = new ArrayList();
        setContentView(R.layout.do_workout_activity);
        loadConfigAndInitRetrofit();

        leaveWorkoutDialog = new LeaveWorkoutDialog(this);
        leaveWorkoutDialog.mListener = this;

        configRepository = new ConfigRepository(this);

    }

    //TODO move this to DoWorkoutFragment processholder and do in initialization
    private void loadConfigAndInitRetrofit() {
        loadConfigUseCase = new LoadConfigUseCase(this);
        Single<Config> observable = loadConfigUseCase.Load();
        observable.subscribe((new SingleObserver<Config>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull Config config) {
                if (config.getServerUrl() != null) {
                    workoutService = RetrofitClientInstance.getRetrofitInstance(config.getServerUrl()).create(WorkoutService.class);
                }
                token = config.getToken();
                initDoWorkout(config);
            }


            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("DoWorkoutActivity", "onError: ", e);
            }
        }));
    }

    private void initDoWorkout(Config config) {
        DoWorkoutFragment fragment = new DoWorkoutFragment(this, config);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();

        fragment.DoWorkoutFragmentListener = this;
        ft.replace(R.id.do_workout_framelayout, fragment, DO_WORKOUT_FRAGMENT_TAG);
        ft.commit();
    }

    @Override
    public void onMovementClicked(Movement movement) {
        exercises.add(new Exercise(exercises.size() + 1, movement));
        //no need to destroy fragment every time.
        DoWorkoutFragment fragment = hidePickMovementFragment();
        fragment.bindExercises(exercises);
    }

    @Override
    public void ToastError(String error) {
        Toast.makeText(DoWorkoutActivity.this, error, Toast.LENGTH_LONG).show();

    }

    public DoWorkoutFragment hidePickMovementFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DoWorkoutFragment fragment = (DoWorkoutFragment) getSupportFragmentManager().findFragmentByTag(DO_WORKOUT_FRAGMENT_TAG);
        if (fragment == null) {
            ft.add(R.id.do_workout_framelayout, fragment, DO_WORKOUT_FRAGMENT_TAG);
        } else {
            ft.show(fragment);
        }

        PickMovementFragment movementFragment = (PickMovementFragment) getSupportFragmentManager().findFragmentByTag(FIND_MOVEMENT_FRAGMENT_TAG);
        if (movementFragment != null) {
            ft.hide(movementFragment);
        }
        ft.commit();
        return fragment;
    }

    @Override
    public void onBackPressed() {
        PickMovementFragment movementFragment = (PickMovementFragment) getSupportFragmentManager().findFragmentByTag(FIND_MOVEMENT_FRAGMENT_TAG);
        if (movementFragment != null && movementFragment.isVisible()) {
            hidePickMovementFragment();
        } else {
            leaveWorkoutDialog.show();
        }
    }


    @Override
    public void saveWorkout() {
        if (token == null || token.isEmpty()) {
            Toast.makeText(DoWorkoutActivity.this, "Token is missing, cant save.", Toast.LENGTH_LONG).show();
            return;
        }

        if (workoutService == null) {
            Toast.makeText(DoWorkoutActivity.this, "Check that your url is correct.", Toast.LENGTH_LONG).show();
            return;
        }

    }

    @Override
    public void deleteExercise(Exercise exercise) {
        exercises.remove(exercise);
        DoWorkoutFragment fragment = (DoWorkoutFragment) getSupportFragmentManager().findFragmentByTag(DO_WORKOUT_FRAGMENT_TAG);
        if (fragment != null) {
            fragment.bindExercises(exercises);
        }
    }

    @Override
    public void WorkoutSaved() {
        Context context = DoWorkoutActivity.this;
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        this.finish();
    }

    @Override
    public void ChangeToPickMovementFragment() {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        PickMovementFragment pickMovementFragment = (PickMovementFragment) getSupportFragmentManager().findFragmentByTag(FIND_MOVEMENT_FRAGMENT_TAG);
        if (pickMovementFragment == null) {
            pickMovementFragment = new PickMovementFragment();
            pickMovementFragment.pickExerciseFragmentListener = this;
            ft.add(R.id.do_workout_framelayout, pickMovementFragment, FIND_MOVEMENT_FRAGMENT_TAG);
        } else {
            ft.show(pickMovementFragment);
        }

        //no need to destroy fragment every time, so we hide it.
        DoWorkoutFragment doWorkoutFragment = (DoWorkoutFragment) getSupportFragmentManager().findFragmentByTag(DO_WORKOUT_FRAGMENT_TAG);
        if (doWorkoutFragment != null) {
            ft.hide(doWorkoutFragment);
        }
        ft.commit();
    }

    // Backbutton pressing shows dialog, these handle dialog return.
    @Override
    public void saveWorkoutAndExit(boolean save) {
        if (save) {
            saveWorkout();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void cancel() {
        leaveWorkoutDialog.hide();
    }

    @Override
    public Observable<DoWorkoutIntent> intents() {
        return null;
    }

    @Override
    public void render(DoWorkoutViewState state) {

    }

    @Override
    public void bind() {

    }
}


