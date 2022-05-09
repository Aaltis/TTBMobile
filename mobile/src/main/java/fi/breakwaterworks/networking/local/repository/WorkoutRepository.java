package fi.breakwaterworks.networking.local.repository;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.local.DAO.ExerciseDAO;
import fi.breakwaterworks.networking.local.DAO.SetRepsWeightDAO;
import fi.breakwaterworks.networking.local.DAO.WorkLogDAO;
import fi.breakwaterworks.networking.local.DAO.WorkoutDAO;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.networking.server.WorkoutService;
import fi.breakwaterworks.networking.server.response.WorkoutCreatedResponse;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class WorkoutRepository {
    WorkoutDAO workoutDAO;
    ExerciseDAO exerciseDao;
    WorkLogDAO workLogDAO;

    SetRepsWeightDAO srwDao;
    WorkoutService workoutService;

    public WorkoutRepository(TTBDatabase database, String serverUrl) {
        this.workoutDAO = database.workoutDAO();
        this.exerciseDao = database.exerciseDAO();
        this.srwDao = database.setRepsWeightDAO();
        this.workLogDAO = database.worklogDAO();

        if (serverUrl != null && !serverUrl.isEmpty()) {
            workoutService = RetrofitClientInstance.getRetrofitInstance(serverUrl).create(WorkoutService.class);
        }
    }

    public Flowable<List<Workout>> loadAllTemplates() {
        return workoutDAO.loadAllIncludingOrExcludingTemplates(true);
    }


    public long saveWorkout(Workout workout) {
        long workoutId = workoutDAO.insert(workout);
        List<Exercise> exercises = workout.getExercises();
        for (int i = 0; i < exercises.size() - 1; i++) {
            workout.getExercises().get(i).setWorkoutId(workoutId);
            Exercise exercise = workout.getExercises().get(i);
            long exerciseId = exerciseDao.insert(workout.getExercises().get(i));
            List<SetRepsWeight> srwList = workout.getExercises().get(i).getSetRepsWeights();
            for (int j = 0; j < srwList.size() - 1; j++) {
                srwList.get(j).setExerciseId(exerciseId);
                srwDao.insert(srwList.get(i));
            }
        }
        return workoutId;
    }

    public Observable<List<Workout>> LoadUnFinishedWorkouts() {
        return io.reactivex.Observable.fromCallable(() -> {
            List<Workout> result = workoutDAO.LoadUnfinushedWorkouts();
            if (result == null) {
                List<Workout> empty = new ArrayList<>();
                return empty;
            }
            return result;
        }).subscribeOn(Schedulers.newThread());
    }

    /**
     * If we have remote configuration save database remote and locally.
     * otherwise only locally
     *
     * @return Workout
     */
    public Observable<Workout> SaveWorkout(@NonNull final Workout workout, String userToken) {

        return SaveWorkoutLocally(workout)
                .flatMap(savedLocalWorkout -> {
                    if (savedLocalWorkout != null  && workoutService != null) {
                         SaveWorkoutRemote(userToken, workout)
                         .map(this::ResponseToWorkout)
                         .map(remoteWorkout ->{
                            return updateLocalWorkoutRemoteId(savedLocalWorkout, remoteWorkout);
                         });

                    }
                    return Observable.just(savedLocalWorkout);
                });
    }

    public Observable<WorkoutCreatedResponse> SaveWorkoutRemote(@NonNull String userToken, @NonNull final Workout workout) {
        return workoutService.saveWorkoutForUser(userToken, workout);
    }

    private Workout ResponseToWorkout(WorkoutCreatedResponse response) {
        return new Workout(response);
    }

    private Observable<Workout> updateLocalWorkoutRemoteId(Workout savedLocalWorkout, Workout remoteWorkout) {
        return Observable.fromCallable(() -> {
            savedLocalWorkout.setRemoteId(remoteWorkout.getRemoteId());
            workoutDAO.insert(savedLocalWorkout);

            return savedLocalWorkout;
        });
    }

    private Observable<Workout> SaveWorkoutLocally(Workout workout) {
        return Observable.fromCallable(() -> {
            List<WorkLog> workLogs = workLogDAO.loadAll();
            if (workLogs.size() == 1) {
                workout.setWorklogId(workLogs.get(0).getWorkLogId());
                long id = saveWorkout(workout);
                return workoutDAO.loadWithId(id);
            } else {
                //todo are multiple logs relevant yet?.
                return workout;
            }
        });
    }

}



