package fi.breakwaterworks.networking.repository;


import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.networking.DAO.ExerciseDAO;
import fi.breakwaterworks.networking.DAO.SetRepsWeightDAO;
import fi.breakwaterworks.networking.DAO.WorkoutDAO;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.model.*;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class WorkoutRepository {
    WorkoutDAO workoutDAO;
    ExerciseDAO exerciseDao;
    SetRepsWeightDAO srwDao;

    public WorkoutRepository(Context context) {
        TTBDatabase db = TTBDatabase.getInstance(context);
        this.workoutDAO = db.workoutDAO();
        this.exerciseDao = db.exerciseDAO();
        this.srwDao = db.setRepsWeightDAO();
    }

    public Flowable<List<Workout>> loadAllTemplates() {
        return workoutDAO.loadAllIncludingOrExcludingTemplates(true);
    }


    public void saveWorkout(Workout workout) {
        long workoutId = workoutDAO.insert(workout);
        List<Exercise> exercises = workout.getExercises();
        for (int i = 0; i < exercises.size() - 1; i++) {
            workout.getExercises().get(i).setWorkoutId(workoutId);
            long exerciseId = exerciseDao.insert(workout.getExercises().get(i));
            List<SetRepsWeight> srwList = workout.getExercises().get(i).getSetRepsWeights();
            for (int j = 0; j < srwList.size() - 1; j++) {
                srwList.get(j).setExerciseId(exerciseId);
                srwDao.insert(srwList.get(i));
            }
        }
    }

    public Observable<List<Workout>> LoadUnFinishedWorkouts() {
        return io.reactivex.Observable.fromCallable(() -> {
            List<Workout> result = workoutDAO.LoadUnfinushedWorkouts();
            if(result==null) {
                List<Workout> empty= new ArrayList<>();
                return empty;
            }return result;
        }).subscribeOn(Schedulers.newThread());
    }

}
