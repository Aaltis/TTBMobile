package fi.breakwaterworks.trackthatbarbellmobile.MainView;

import android.content.Context;

import androidx.annotation.NonNull;
import fi.breakwaterworks.mvibase.MviIntent;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static fi.breakwaterworks.trackthatbarbellmobile.MainView.MainActivityViewState.GoToWorkoutTemplatesListActivity;

public class MainActivityPresenter {

    @NonNull
    private PublishSubject<MainActivityIntent> mIntentsSubject;
    @NonNull
    private Observable<MainActivityViewState> mStatesObservable;
    public DatabaseInitiatorInterractor databaseInitiatorIterractor;

    public MainActivityPresenter(Context context) {
        mIntentsSubject = PublishSubject.create();
        mStatesObservable = compose();
        databaseInitiatorIterractor = new DatabaseInitiatorInterractor(context);
    }

    public Observable<MainActivityViewState> states() {
        return mStatesObservable;
    }

    public void processIntents(Observable<MainActivityIntent> intents) {
        intents.subscribe(mIntentsSubject);
    }

    /**
     * Compose all components to create the stream logic
     */
    private Observable<MainActivityViewState> compose() {
        return mIntentsSubject
                .map(this::actionFromIntent);
    }

    private MainActivityViewState actionFromIntent(MviIntent intent) {

        if (intent instanceof MainActivityIntent.OpenWorkoutTemplatesListActivity) {
            return new GoToWorkoutTemplatesListActivity();
        }
        if (intent instanceof MainActivityIntent.DoWorkoutActivity) {
            return new MainActivityViewState.DoWorkoutActivityState();
        }

        return new MainActivityViewState.DatabaseSave.Error(new Exception("fug"));
    }

}

