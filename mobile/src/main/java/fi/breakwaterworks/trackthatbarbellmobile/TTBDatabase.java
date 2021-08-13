package fi.breakwaterworks.trackthatbarbellmobile;

import androidx.room.*;
import android.content.Context;

import java.io.File;

import fi.breakwaterworks.networking.DAO.*;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.model.User;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.utility.DateConverter;
import fi.breakwaterworks.utility.SetTypeEnumConverter;

@Database(entities = {User.class, Exercise.class, Movement.class, WorkLog.class, Workout.class, SetRepsWeight.class}, version = 1)
@TypeConverters({DateConverter.class, SetTypeEnumConverter.class})
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