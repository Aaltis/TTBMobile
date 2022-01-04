package fi.breakwaterworks.networking.local.repository;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.room.Transaction;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.model.WorkoutCycle;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WorkLogRepository {

    TTBDatabase database;

    public WorkLogRepository(Context context) {
        this.database = TTBDatabase.getInstance(context);

    }

    public WorkLog loadWorkLogWithId(Long workLogId) {
        try {
            WorkLog workLog = database.worklogDAO().loadWorkLogWithId(workLogId);
            workLog.setWorkoutList(database.workoutDAO().loadWithWorklogId(workLogId));

            for (Workout workout : workLog.getWorkoutList()) {
                workout.setExercises(database.exerciseDAO().loadExercisesWithWorkoutId(workout.getId()));
                for (Exercise exercise : workout.getExercises()) {
                    exercise.setMovement(database.movementDAO().getMovementWithId(exercise.getMovementId()));
                    exercise.setSetRepsWeights(database.setRepsWeightDAO().loadWithExerciseId(exercise.getExerciseId()));
                }
            }
            return workLog;

        } catch (Exception ex) {
            Log.e(WorkLogRepository.class.getName(), ex.getLocalizedMessage());
            throw ex;
        }
    }


    public Observable<List<WorkLog>> loadAllTemplates() {
        return Observable.fromCallable(() -> {
            try {
                return database.worklogDAO().loadAllTemplates(true);
            } catch (Exception ex) {
                Log.e(WorkLogRepository.class.getName(), ex.getLocalizedMessage());
                throw ex;
            }
        }).subscribeOn(Schedulers.newThread());
    }

    @Transaction
    public void insertWorkLogTemplate(List<WorkLog> templateWorklogs) {
        for (WorkLog templateWorklog : templateWorklogs) {
            long workLogId = database.worklogDAO().insert(templateWorklog);
            if (templateWorklog.getCycles() != null) {
                for (WorkoutCycle cycle : templateWorklog.getCycles()) {
                    for (Workout workout : cycle.getWorkouts()) {
                        saveTemplateWorkout(workout);
                    }
                }
            } else {
                for (Workout workout : templateWorklog.getWorkoutList()) {
                    workout.setWorklogId(workLogId);
                    saveTemplateWorkout(workout);
                }
            }
        }
    }

    public void saveTemplateWorkout(Workout workout) {
        long workoutId = database.workoutDAO().insert(workout);
        List<Exercise> exercises = workout.getExercises();
        for (int exerciseIndex = 0; exerciseIndex < exercises.size(); exerciseIndex++) {
            workout.getExercises().get(exerciseIndex).setWorkoutId(workoutId);
            findMovementIdForExercises(exercises);
            long exerciseId = database.exerciseDAO().insert(workout.getExercises().get(exerciseIndex));
            List<SetRepsWeight> srwList = workout.getExercises().get(exerciseIndex).getSetRepsWeights();
            for (int srwIndex = 0; srwIndex < srwList.size(); srwIndex++) {
                srwList.get(srwIndex).setExerciseId(exerciseId);
                database.setRepsWeightDAO().insert(srwList.get(srwIndex));
            }
        }
    }

    private void findMovementIdForExercises(List<Exercise> exercises){
        for (Exercise ex: exercises) {
            Movement movement = database.movementDAO().getMovementWithName(ex.getMovementName());
            if (movement != null) {
                ex.setMovementId(movement.getMovementId());
            }
        }
    }
    public Observable<List<Movement>> loadMovementsFromWorkLog(long Id) {
        return Observable.fromCallable(() -> {
            try {
                List<Movement> movements = new ArrayList<>();

                List<Long> movementIds=new ArrayList<>();

                WorkLog workLog = database.worklogDAO().loadWorkLogWithId(Id);
                List<Workout> workouts = database.workoutDAO().loadWorkOutsInWorklogWithId(workLog.getWorkLogId());
                for (Workout workout : workouts) {
                    List<Exercise> exercises = database.exerciseDAO().loadExercisesWithWorkoutId(workout.getId());
                    for (Exercise exercise : exercises) {
                        Movement movement = database.movementDAO().getMovementWithId(exercise.getMovementId());
                        if (!movementIds.contains(movement.getMovementId())) {
                            movementIds.add(movement.getMovementId());
                            movements.add(movement);
                        }
                    }
                }


                List<Movement> result = new ArrayList(new HashSet(movements));
                return result;
            } catch (Exception ex) {
                throw ex;
            }
        }).subscribeOn(Schedulers.newThread());
    }
}

