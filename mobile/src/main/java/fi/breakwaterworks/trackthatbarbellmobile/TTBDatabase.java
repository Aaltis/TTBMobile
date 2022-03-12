package fi.breakwaterworks.trackthatbarbellmobile;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.io.File;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.model.User;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.local.DAO.ConfigDAO;
import fi.breakwaterworks.networking.local.DAO.ExerciseDAO;
import fi.breakwaterworks.networking.local.DAO.MovementDAO;
import fi.breakwaterworks.networking.local.DAO.SetRepsWeightDAO;
import fi.breakwaterworks.networking.local.DAO.UserDAO;
import fi.breakwaterworks.networking.local.DAO.WorkLogDAO;
import fi.breakwaterworks.networking.local.DAO.WorkoutDAO;
import fi.breakwaterworks.utility.DateConverter;

@Database(entities = {User.class, Exercise.class, Movement.class, WorkLog.class, Workout.class, SetRepsWeight.class, Config.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class TTBDatabase extends RoomDatabase {

    private static String DB_NAME = "ttb-database.db";

    public TTBDatabase() {
    }

    private static TTBDatabase INSTANCE;

    public abstract MovementDAO movementDAO();

    public abstract ExerciseDAO exerciseDAO();

    public abstract UserDAO userDAO();

    public abstract WorkLogDAO worklogDAO();

    public abstract WorkoutDAO workoutDAO();

    public abstract SetRepsWeightDAO setRepsWeightDAO();

    public abstract ConfigDAO ConfigDAO();

    public TTBDatabase(TTBDatabase ttbDatabase) {
        this.INSTANCE = ttbDatabase;
    }

    public synchronized static TTBDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }
    public static TTBDatabase buildDatabase(Context context) {
        final File dbFile = context.getDatabasePath(DB_NAME);

        if(!dbFile.exists()) {
            copyDatabaseFile(dbFile.getAbsolutePath());
        }
        return Room.databaseBuilder(context.getApplicationContext(), TTBDatabase.class, "ttb-database.db") .build();
    }

    private static void copyDatabaseFile(String absolutePath) {
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }
}