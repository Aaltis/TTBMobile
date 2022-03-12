package fi.breakwaterworks.trackthatbarbellmobile.MainView;

import androidx.annotation.NonNull;

import fi.breakwaterworks.mvibase.MviIntent;
import fi.breakwaterworks.mvibase.MviResult;
import fi.breakwaterworks.mvibase.MviView;
import fi.breakwaterworks.mvibase.MviViewState;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityAction;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityIntent;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityResult;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityViewState;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.subjects.PublishSubject;

public class MainActivityViewModel {

    @NonNull
    private final PublishSubject<MainActivityIntent> mIntentsSubject;
    @NonNull
    private final Observable<MainActivityViewState> mStatesObservable;
    public MainActivityActionProcessHolder mActionProcessorHolder;

    public MainActivityViewModel(MainActivityActionProcessHolder activityActionProcessHolder) {
        mIntentsSubject = PublishSubject.create();
        mActionProcessorHolder = activityActionProcessHolder;
        mStatesObservable = compose();
    }

    public Observable<MainActivityViewState> states() {
        return mStatesObservable;
    }

    public void processIntents(Observable<MainActivityIntent> intents) {
        intents.subscribe(mIntentsSubject);
    }


    private MainActivityAction actionFromIntent(MviIntent intent) {

        if (intent instanceof MainActivityIntent.InitialIntent) {
            return MainActivityAction.Initialization.create();
        }
        if (intent instanceof MainActivityIntent.DoWorkoutActivity) {
            return MainActivityAction.OpenDoWorkoutActivity.create();
        }
        if (intent instanceof MainActivityIntent.UpdateFromServer) {
            return MainActivityAction.UpdateFromServer.create();
        }
        return null;

    }

    /**
     * Compose all components to create the stream logic
     */
    private Observable<MainActivityViewState> compose() {
        return mIntentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(mActionProcessorHolder.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(MainActivityViewState.idle(), reducer)
                // When a reducer just emits previousState, there's no reason to call render. In fact,
                // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
                // by showing the same snackbar twice in rapid succession).
                .distinctUntilChanged()
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0);
    }

    /**
     * take only the first ever InitialIntent and all intents of other types
     * to avoid reloading data on config changes
     */
    private final ObservableTransformer<MainActivityIntent, MainActivityIntent> intentFilter =
            intents -> intents.publish(shared ->
                    Observable.merge(
                            shared.ofType(MainActivityIntent.class).take(1),
                            shared.filter(intent -> !(intent instanceof MainActivityIntent.InitialIntent))
                    )
            );


    /**
     * The Reducer is where {@link MviViewState}, that the {@link MviView} will use to
     * render itself, are created.
     * It takes the last cached {@link MviViewState}, the latest {@link MviResult} and
     * creates a new {@link MviViewState} by only updating the related fields.
     * This is basically like a big switch statement of all possible types for the {@link MviResult}
     */
    private static final BiFunction<MainActivityViewState, MainActivityResult, MainActivityViewState> reducer =
            (previousState, result) -> {
                MainActivityViewState.Builder stateBuilder = previousState.buildWith();
                if (result instanceof MainActivityResult.Initialization) {
                    MainActivityResult.Initialization initialization =
                            (MainActivityResult.Initialization) result;
                    switch (initialization.status()) {
                        case SUCCESS:
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(((MainActivityResult.Initialization) result).error()).build();
                        case RUNNING:
                            stateBuilder.loading(true);
                            return stateBuilder.build();
                    }
                }
                if (result instanceof MainActivityResult.OpenWorkoutTemplatesResult) {
                    MainActivityResult.OpenWorkoutTemplatesResult open =
                            (MainActivityResult.OpenWorkoutTemplatesResult) result;
                    switch (open.status()) {
                        case SUCCESS:
                            stateBuilder.goToDoWorkoutView(true);
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(((MainActivityResult.OpenWorkoutTemplatesResult) result).error()).build();
                        case RUNNING:
                            stateBuilder.loading(true);
                            return stateBuilder.build();
                    }
                }
                if (result instanceof MainActivityResult.RemoteUpdateResult) {
                    MainActivityResult.RemoteUpdateResult open =
                            (MainActivityResult.RemoteUpdateResult) result;
                    switch (open.status()) {
                        case SUCCESS:
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(((MainActivityResult.RemoteUpdateResult) result).error()).build();
                        case RUNNING:
                            stateBuilder.loading(true);
                            return stateBuilder.build();
                    }
                }


                // Fail for unhandled results
                throw new IllegalStateException("Mishandled result? Should not happen―as always: " + result);
            };
}
