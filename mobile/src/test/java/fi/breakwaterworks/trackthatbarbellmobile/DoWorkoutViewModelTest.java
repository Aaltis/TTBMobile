package fi.breakwaterworks.trackthatbarbellmobile;

import org.junit.Test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.local.repository.MovementRepository;
import fi.breakwaterworks.networking.local.repository.WorkoutRepository;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutActionProcessHolder;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutIntent;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutViewModel;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutViewState;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.SchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.Task;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DoWorkoutViewModelTest {
/*
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private MovementRepository movementRepository;

    private BaseSchedulerProvider mSchedulerProvider;
    private DoWorkoutViewModel doWorkoutViewModel;
    private TestObserver<DoWorkoutViewState> mTestObserver;

    @Before
    public void setupMocksAndView() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.openMocks(this);

        mSchedulerProvider = new ImmediateSchedulerProvider();

        doWorkoutViewModel = new DoWorkoutViewModel(
                new DoWorkoutActionProcessHolder(workoutRepository, movementRepository, mSchedulerProvider));
        mTestObserver = doWorkoutViewModel.states().test();
    }

    @Test
    public void saveNewTaskToRepository_showsSuccessMessageUi() {
        // When task saving intent is emitted by the view
        doWorkoutViewModel.processIntents(Observable.just(
                DoWorkoutIntent.SaveWorkout.create(new Workout())
        ));

        // Then a task is saved in the repository and the view updates
        verify(workoutRepository).saveWorkout(any(Workout.class)); // saved to the model
        mTestObserver.assertValueAt(1, state -> state.workoutSaved());
    }*/
}