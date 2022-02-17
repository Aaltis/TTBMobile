package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout;

import androidx.annotation.NonNull;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.mvibase.MviAction;
import fi.breakwaterworks.mvibase.MviResult;
import fi.breakwaterworks.networking.local.repository.MovementRepository;
import fi.breakwaterworks.networking.local.repository.WorkoutRepository;
import fi.breakwaterworks.trackthatbarbellmobile.Schedulers.BaseSchedulerProvider;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutAction;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.DoWorkoutResult;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class DoWorkoutActionProcessHolder {

    @NonNull
    private WorkoutRepository workoutRepository;
    private MovementRepository movementRepository;
    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    public DoWorkoutActionProcessHolder(WorkoutRepository workoutRepository,
                                        MovementRepository movementRepository,
                                        BaseSchedulerProvider baseSchedulerProvider) {
        this.workoutRepository = workoutRepository;
        this.movementRepository = movementRepository;
        mSchedulerProvider = baseSchedulerProvider;
    }

    private ObservableTransformer<DoWorkoutAction.SaveWorkout, DoWorkoutResult.SaveWorkout>
            saveWorkoutProcessor =
            actions -> actions.flatMap(action ->
                    workoutRepository.SaveWorkout(action.workout(), action.token())
                            .map(DoWorkoutResult.SaveWorkout::success)
                            .onErrorReturn(DoWorkoutResult.SaveWorkout::failure)
                            .subscribeOn(mSchedulerProvider.io())
                            .observeOn(mSchedulerProvider.ui())
                            .startWith(DoWorkoutResult.SaveWorkout.running()));



    private ObservableTransformer<DoWorkoutAction.LoadMovements, DoWorkoutResult.LoadMovements>
            loadMovementsProcessor =
            actions -> actions.map(action -> {
                // Workout workout = action.workout();
                //String userToken = action.token();

                /*TODO check that data is right.

                Task task = new Task(action.title(), action.description());


                if (task.isEmpty()) {
                    return AddEditTaskResult.CreateTask.empty();
                }*/
                List<Movement> movementList = (List<Movement>) movementRepository.GetAllMovements();
                return DoWorkoutResult.LoadMovements.success(movementList);
            });

    /**
     * Splits the {@link Observable< MviAction >} to match each type of {@link MviAction} to
     * its corresponding business logic processor. Each processor takes a defined {@link MviAction},
     * returns a defined {@link MviResult}
     * The global actionProcessor then merges all {@link Observable<MviResult>} back to
     * one unique {@link Observable<MviResult>}.
     * <p>
     * The splitting is done using  which allows almost anything
     * on the passed {@link Observable} as long as one and only one {@link Observable} is returned.
     * <p>
     * An security layer is also added for unhandled {@link MviAction} to allow early crash
     * at runtime to easy the maintenance.
     */

    public ObservableTransformer<DoWorkoutAction, DoWorkoutResult> actionProcessor =
            actions -> actions.publish(shared -> Observable.merge(
                    shared.ofType(DoWorkoutAction.SaveWorkout.class).compose(saveWorkoutProcessor),
                    shared.ofType(DoWorkoutAction.LoadMovements.class).compose(loadMovementsProcessor)
            ));

}
