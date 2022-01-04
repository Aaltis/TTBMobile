package fi.breakwaterworks.networking.local.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import fi.breakwaterworks.model.Workout;
import io.reactivex.Flowable;


@Dao
public interface WorkoutDAO extends BaseDAO<Workout> {
    @Query("select * from workouts where template = :templateValue")
    Flowable<List<Workout>> loadAllIncludingOrExcludingTemplates(boolean templateValue);

    @Query("select * from workouts where workout_id = :workoutId")
    Workout loadWithId(String workoutId);

    @Query("select * from workouts where worklog_id = :worklogId")
    List<Workout> loadWithWorklogId(long worklogId);

    @Query("select * from workouts where name = :name")
    Workout loadWithName(String name);

    @Query("select * from workouts where name = :name and template = :isTemplate")
    Workout loadTemplateWithName(String name, boolean isTemplate);

    @Query("select * from workouts where worklog_id = :id")
    List<Workout> loadWorkOutsInWorklogWithId(Long id);

    @Query("select * from workouts where onGoing = 'true' ")
    List<Workout> LoadUnfinushedWorkouts();
}
