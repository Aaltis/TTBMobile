package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.server.MovementsService;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutFragment;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view.PickMovementFragment;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoWorkoutActivity extends FragmentActivity
        implements DoWorkoutFragment.Listener,
        PickMovementFragment.Listener {

    private MovementsService movementsService;
    private static final String DO_WORKOUT_FRAGMENT_TAG = "DoWorkoutFragment";
    private static final String FIND_MOVEMENT_FRAGMENT_TAG = "FindMovementFragment";

    //this is model for active activity.
    //TODO what could be better way to store this?
    private List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exercises = new ArrayList();
        setContentView(R.layout.do_workout_activity);
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        DoWorkoutFragment fragment;
        if (savedInstanceState == null) {
            fragment = new DoWorkoutFragment(this);
        } else {
            fragment = (DoWorkoutFragment) getSupportFragmentManager().findFragmentById(R.id.do_workout_framelayout);
        }
        fragment.DoWorkoutFragmentListener = this;
        ft.replace(R.id.do_workout_framelayout, fragment, DO_WORKOUT_FRAGMENT_TAG);
        ft.commit();

        movementsService = RetrofitClientInstance.getRetrofitInstance().create(MovementsService.class);
    }


    public void ChangeToMovementPickerFragment(List<Movement> movements) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        PickMovementFragment fragment = (PickMovementFragment) getSupportFragmentManager().findFragmentByTag(FIND_MOVEMENT_FRAGMENT_TAG);
        if (fragment == null) {
            ft.add(R.id.do_workout_framelayout, new PickMovementFragment(movements), FIND_MOVEMENT_FRAGMENT_TAG);
        } else {
            ft.show(fragment);
        }

        //no need to destroy fragment every time, so we hide it.
        DoWorkoutFragment doWorkoutFragment = (DoWorkoutFragment) getSupportFragmentManager().findFragmentByTag(DO_WORKOUT_FRAGMENT_TAG);
        if (doWorkoutFragment != null) {
            ft.hide(doWorkoutFragment);
        }
        ft.commit();
    }

    @Override
    public void onMovementClicked(Movement movement) {
        exercises.add(new Exercise(exercises.size() + 1, movement));

        //no need to destroy fragment every time.
        DoWorkoutFragment fragment = hidePickMovementFragment();
        fragment.bindExercises(exercises);
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
        }
    }

    @Override
    public void onSearchQuerySubmitted(String query) {

        Call<List<Movement>> call = movementsService.getMovementsWithName(query);
        call.enqueue(new Callback<List<Movement>>() {
            @Override
            public void onResponse(Call<List<Movement>> call, Response<List<Movement>> response) {
                refreshMovementsInMovementFragment(response.body());
            }

            @Override
            public void onFailure(Call<List<Movement>> call, Throwable t) {
                Toast.makeText(DoWorkoutActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void openAddMovementsClicked() {

        Call<List<Movement>> call = movementsService.getAllMovements();
        call.enqueue(new Callback<List<Movement>>() {
            @Override
            public void onResponse(Call<List<Movement>> call, Response<List<Movement>> response) {
                ChangeToMovementPickerFragment(response.body());

            }

            @Override
            public void onFailure(Call<List<Movement>> call, Throwable t) {
                Toast.makeText(DoWorkoutActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    public void refreshMovementsInMovementFragment(List<Movement> movements) {
        PickMovementFragment fragment = (PickMovementFragment) getSupportFragmentManager().findFragmentByTag(FIND_MOVEMENT_FRAGMENT_TAG);
        if (fragment != null) {
            fragment.bindMovements(movements);
        }

    }
}


