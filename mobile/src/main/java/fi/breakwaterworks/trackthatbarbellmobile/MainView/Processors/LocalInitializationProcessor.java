package fi.breakwaterworks.trackthatbarbellmobile.MainView.Processors;

import android.content.Context;
import android.util.Log;

import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.User;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.Datasource;
import fi.breakwaterworks.networking.local.repository.WorkLogRepository;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.utility.TextParser;
import io.reactivex.Observable;

public class LocalInitializationProcessor {
    TTBDatabase ttbDatabase;
    TextParser textParser;
    Context context;
    WorkLogRepository workLogRepository;

    public LocalInitializationProcessor(TTBDatabase ttbDatabase, TextParser textParser, Context context, WorkLogRepository workLogRepository) {
        this.ttbDatabase = ttbDatabase;
        this.textParser = textParser;
        this.context = context;
        this.workLogRepository = workLogRepository;
    }

    public Observable<Config> Initialize() {
        return InitializeConfigIfNeeded()
                .flatMap(this::InitializeMovements)
                .flatMap(this::InitializeWorkoutTemplates);
    }

    /**
     * Initialize config if not done before.
     *
     * @return Config
     */
    public Observable<Config> InitializeConfigIfNeeded() {

        Log.d("InitializationProcessor:initDatabase", "init config.");
        return Observable.fromCallable(() -> {
            Config config = ttbDatabase.ConfigDAO().loadConfig();
            if (config == null) {
                Config newConfig = new Config(Datasource.LOCAL);
                ttbDatabase.ConfigDAO().insert(newConfig);
            }
            return ttbDatabase.ConfigDAO().loadConfig();
        });
    }


    //TODO how to handle favorites?

    /** initialize movements from txt if not done before
     * @param config
     * @return List<Movement>
     */
    public Observable<List<Movement>> InitializeMovements(Config config) {

        if (!ttbDatabase.userDAO().isInitialized("me")) {
            Log.d("InitializationProcessor:initDatabase", "init config.");
            TTBDatabase.getInstance(context).movementDAO().insertAll(textParser.LoadMovements());
        }
        return ttbDatabase.movementDAO().getAllMovementsAsObservable();
    }


    public Observable<Config> InitializeWorkoutTemplates(List<Movement> movements) {

        //TODO update templates  from server
        if (!ttbDatabase.userDAO().isInitialized("me")) {
            return Observable.fromCallable(() -> {
                Log.d("initDatabase", "Load and save workouts");
                List<WorkLog> templateWorkLog = textParser.loadWorkoutTemplates();
                for (WorkLog worklog : templateWorkLog) {
                    for (Workout workout : worklog.getWorkoutList()) {
                        for (Exercise exercise : workout.getExercises()) {
                            Movement m = ttbDatabase.movementDAO().getMovementWithName(exercise.getMovementName());
                            if (m != null) {
                                exercise.setMovement(m);
                            }
                        }
                    }
                }
                workLogRepository.insertWorkLogTemplate(templateWorkLog);
                TTBDatabase.getInstance(context).userDAO().insert(new User("me", true));
                return ttbDatabase.ConfigDAO().loadConfig();
            });
        }
        return ttbDatabase.ConfigDAO().loadOneConfigObservable();

    }


}
