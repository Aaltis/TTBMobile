package fi.breakwaterworks.trackthatbarbellmobile.MainView;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.Datasource;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.model.User;
import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.schedulers.Schedulers;
import fi.breakwaterworks.utility.TextParser;
import fi.breakwaterworks.utility.WorkoutTemplateInitiator;

public class DatabaseInitiatorInterractor {

    WorkoutTemplateInitiator workoutTemplateInitiator;
    TextParser textParser;
    Context context;

    public DatabaseInitiatorInterractor(Context context) {
        this.workoutTemplateInitiator = new WorkoutTemplateInitiator(context);
        this.textParser = new TextParser(context);
        this.context = context;
    }

    public Observable<MainActivityViewState> initDatabase() {
        return new ObservableFromCallable((Callable<MainActivityViewState>) () -> {
            try {
                if (!TTBDatabase.getInstance(context).userDAO().isInitialized("me")) {
                    Log.d("initDatabase", "init config.");
                    TTBDatabase.getInstance(context).ConfigDAO().insert(new Config(Datasource.LOCAL));
                    Log.d("initDatabase", "Loading movements");

                    TTBDatabase.getInstance(context).movementDAO().insertAll(textParser.LoadMovements(context));
                    Log.d("initDatabase", "Movements saved.");
                    Log.d("initDatabase", "Load and save workouts");
                    List<WorkLog> templateWorkLog = textParser.loadWorkoutTemplates(context);
                    for (WorkLog worklog : templateWorkLog) {
                        for (Workout workout : worklog.getWorkoutList()) {
                            for (Exercise exercise : workout.getExercises()) {
                                Movement m = TTBDatabase.getInstance(context).movementDAO().getMovementWithName(exercise.getMovementName());
                                if (m != null) {
                                    exercise.setMovement(m);
                                }
                            }
                        }
                    }
                    workoutTemplateInitiator.SaveWorkoutsFromFiles(templateWorkLog);
                    TTBDatabase.getInstance(context).userDAO().insert(new User("me", true));
                }
                return new MainActivityViewState.DatabaseSave.Success("done");
            } catch (Exception ex) {
                return new MainActivityViewState.DatabaseSave.Error(ex);
            }
        }).subscribeOn(Schedulers.newThread())
                .startWith(new MainActivityViewState.DatabaseSave.Init());
    }

    public void LoadMovements() {

    }
}


