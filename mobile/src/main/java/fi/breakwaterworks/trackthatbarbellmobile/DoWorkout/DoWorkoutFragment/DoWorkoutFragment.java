package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.local.repository.MovementRepository;
import fi.breakwaterworks.networking.local.repository.WorkoutRepository;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutActionProcessHolder;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutActivity;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view.ExerciseListViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.SchedulerProvider;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseFragment;
import fi.breakwaterworks.trackthatbarbellmobile.common.onBackPressedListener;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class DoWorkoutFragment extends
        BaseFragment implements onBackPressedListener,
        ExerciseListViewMvc.Listener {
    private ExerciseListViewMvc mViewMvc;
    public DoWorkoutFragment.Listener DoWorkoutFragmentListener;
    public DoWorkoutActivity parentActivity;
    private Config config;
    // Used to manage the data flow lifecycle and avoid memory leak.
    private CompositeDisposable mDisposables = new CompositeDisposable();
    DoWorkoutViewModel mViewModel;

    private final PublishSubject<DoWorkoutIntent.SaveWorkout> saveWorkoutPublishSubject = PublishSubject.create();
    private final PublishSubject<DoWorkoutIntent.LoadMovements> loadMovementsPublishSubject = PublishSubject.create();

    public DoWorkoutFragment(DoWorkoutActivity doWorkoutActivity, Config config) {
        super();
        parentActivity = doWorkoutActivity;
        this.config=config;
    }

    @Override
    public void onBackPressed() {
    }

    public interface Listener {
        void ChangeToPickMovementFragment();
        void saveWorkout();
        void deleteExercise(Exercise exercise);
        void WorkoutSaved();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewMvc = getCompositionRoot().getViewMvcFactory().getExerciseListViewMvc(container, parentActivity);
        mViewMvc.registerListener(this);
        mViewModel = new DoWorkoutViewModel(new DoWorkoutActionProcessHolder(
                new WorkoutRepository(TTBDatabase.getInstance(getContext()), config.getServerUrl()),
                new MovementRepository(getContext()),
                SchedulerProvider.getInstance()));
        mDisposables = new CompositeDisposable();
        return mViewMvc.getRootView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);

        bind();
    }

    private void bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        mDisposables.add(mViewModel.states().subscribe(this::render));
        // Pass the UI's intents to the ViewModel
        mViewModel.processIntents(intents());


    }

    private void render(DoWorkoutViewState doWorkoutViewState) {
        if (doWorkoutViewState.error() != null) {
            Log.e("Error", doWorkoutViewState.error().getMessage());
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage(doWorkoutViewState.error().getLocalizedMessage())

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
        if(doWorkoutViewState.workoutSaved()){
            DoWorkoutFragmentListener.WorkoutSaved();
        }
    }

    private Observable<DoWorkoutIntent> intents() {
        return Observable.merge(saveWorkoutIntent(), LoadMovements());
    }


    private Observable<DoWorkoutIntent.SaveWorkout> saveWorkoutIntent() {
        return saveWorkoutPublishSubject;
    }

    private Observable<DoWorkoutIntent.LoadMovements> LoadMovements() {
        return loadMovementsPublishSubject;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof DoWorkoutFragmentViewMvc.DoWorkoutFragmentListener) {
            listener = (DoWorkoutFragmentViewMvc.DoWorkoutFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }*/
    }


    @Override
    public void onOpenAddMovementsClicked() {
        DoWorkoutFragmentListener.ChangeToPickMovementFragment();
    }

    @Override
    public void saveWorkout() {
        saveWorkoutPublishSubject.onNext(DoWorkoutIntent.SaveWorkout.create(config.getToken(), new Workout(parentActivity.exercises)));
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        DoWorkoutFragmentListener.deleteExercise(exercise);
    }

    public void bindExercises(List<Exercise> exercises) {
        mViewMvc.bindExercises(exercises);
    }


}
