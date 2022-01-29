package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.local.repository.ConfigRepository;
import fi.breakwaterworks.networking.local.usecase.LoadConfigUseCase;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.networking.server.WorkoutService;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutFragment;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view.PickMovementFragment;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoWorkoutActivity extends FragmentActivity
        implements DoWorkoutFragment.Listener,
        PickMovementFragment.Listener,
        LeaveWorkoutDialog.LeaveWorkoutDialogListener {

    private WorkoutService workoutService;
    ConfigRepository configRepository;
    private static final String DO_WORKOUT_FRAGMENT_TAG = "DoWorkoutFragment";
    private static final String FIND_MOVEMENT_FRAGMENT_TAG = "FindMovementFragment";
    LoadConfigUseCase loadConfigUseCase;
    private String token;
    public LeaveWorkoutDialog leaveWorkoutDialog;

    //TODO what could be better way to store this?
    private List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exercises = new ArrayList();
        setContentView(R.layout.do_workout_activity);
        loadConfigAndInitRetrofit();

        leaveWorkoutDialog = new LeaveWorkoutDialog(this);
        leaveWorkoutDialog.mListener = this;

        configRepository = new ConfigRepository(this);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        DoWorkoutFragment fragment = new DoWorkoutFragment(this);
        if (savedInstanceState != null) {
            fragment = (DoWorkoutFragment) getSupportFragmentManager().findFragmentById(R.id.do_workout_framelayout);
        }
        fragment.DoWorkoutFragmentListener = this;
        ft.replace(R.id.do_workout_framelayout, fragment, DO_WORKOUT_FRAGMENT_TAG);
        ft.commit();

    }

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
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        }));
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

        Call<List<String>> call = workoutService.saveWorkoutForUser(token, new Workout(exercises));
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Toast.makeText(DoWorkoutActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(DoWorkoutActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
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
        this.finish();
    }

    @Override
    public void cancel() {
        leaveWorkoutDialog.hide();
    }
}


