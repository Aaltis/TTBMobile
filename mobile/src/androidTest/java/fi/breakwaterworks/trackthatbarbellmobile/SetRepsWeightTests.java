package fi.breakwaterworks.trackthatbarbellmobile;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

import fi.breakwaterworks.DAO.SetRepsWeightDAO;
import fi.breakwaterworks.networking.TTBDatabase;
import fi.breakwaterworks.utility.WorkoutTemplateInitiator;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class SetRepsWeightTests {
    private static TTBDatabase mDatabase;
    private SetRepsWeightDAO srwDAO;
    WorkoutTemplateInitiator workoutTemplateInitiator;

    @Before
    public static void initDb() throws Exception {

     /*   mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), TTBDatabase.class)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> mDatabase.movementDAO().insertAll(Movement.populateData(InstrumentationRegistry.getTargetContext())));
                    }
                }).build();
    }

    @Test
    public void Save() throws Exception {

    }*/
    }
}
