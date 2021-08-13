package fi.breakwaterworks.utility;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.repository.WorkLogRepository;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WorkoutGenerator {
    WorkLogRepository workLogRepository;

    public WorkoutGenerator(Context context) {
        workLogRepository = new WorkLogRepository(context);
    }

    public Observable<WorkLog> generateWorkLog(List<Movement> movements, Long workLogId) {
        return Observable.fromCallable(() -> {
            WorkLog resultWorkLog = new WorkLog();

            try {
                WorkLog templateWorklog = workLogRepository.loadWorkLogWithId(workLogId);

                for (Workout templateWorkout : templateWorklog.getWorkoutList()) {
                    Workout newWorkout = new Workout();
                    for (Exercise exercise : templateWorkout.getExercises()) {
                        List<Exercise> exerciseList = new ArrayList<>();
                        switch (exercise.getSetTypeEnum()) {
                            case STRAIGHT_SET:
                                exerciseList.add(CalculateStraightSet(movements, exercise));
                                break;
                        }
                        newWorkout.setExercises(exerciseList);
                    }

                }

            } catch (Exception ex) {
                Log.e(WorkoutGenerator.class.getName(), "generateWorkLog: ",ex );
            }
            return resultWorkLog;
        }).subscribeOn(Schedulers.newThread());
    }

    // 1. Compare user given movement max to exercise. if match, ok
    // 2. Calculate max from user given movement and return.
    //TODO 3. Check database for Exercise with same movement, sets and reps with success. return.
    private Exercise CalculateStraightSet(List<Movement> movements, Exercise exercise) {
        Movement userGivenMovement = movements.stream().filter(movement -> exercise.getMovementName().equals(movement.getName())).findAny().orElse(null);
        long weight;
        if (userGivenMovement.getReps() == exercise.getSetRepsWeights().get(0).getReps()) {
            weight = (long) exercise.getSetRepsWeights().get(0).getWeight();
        } else {
            weight = OneRepCalculator.calculateRepMax(userGivenMovement.getReps(), userGivenMovement.getWeight(), exercise.getSetRepsWeights().get(0).getReps());
        }
        return new Exercise(exercise.getOrderNumber(),
                exercise.getMovement(),
                new SetRepsWeight(exercise.getSetRepsWeights().get(0).getSets(),
                        exercise.getSetRepsWeights().get(0).getReps(), weight));
    }

}