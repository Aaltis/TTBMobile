package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.mvibase.MviAction;
import fi.breakwaterworks.mvibase.MviIntent;
import fi.breakwaterworks.mvibase.MviResult;
import fi.breakwaterworks.mvibase.MviView;
import fi.breakwaterworks.mvibase.MviViewModel;
import fi.breakwaterworks.mvibase.MviViewState;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutActionProcessHolder;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.subjects.PublishSubject;

public class DoWorkoutViewModel extends ViewModel
        implements MviViewModel<DoWorkoutIntent, DoWorkoutViewState> {
    /**
     * Proxy subject used to keep the stream alive even after the UI gets recycled.
     * This is basically used to keep ongoing events and the last cached State alive
     * while the UI disconnects and reconnects on config changes.
     */
    @NonNull
    private final PublishSubject<DoWorkoutIntent> mIntentsSubject;
    @NonNull
    private final Observable<DoWorkoutViewState> mStatesObservable;
    /**
     * Contains and executes the business logic of all emitted actions.
     */
    @NonNull
    private final DoWorkoutActionProcessHolder mActionProcessorHolder;

    public DoWorkoutViewModel(@NonNull DoWorkoutActionProcessHolder actionProcessorHolder) {
        mActionProcessorHolder = actionProcessorHolder;
        mIntentsSubject = PublishSubject.create();
        mStatesObservable = compose();
    }

    @Override
    public void processIntents(io.reactivex.Observable<DoWorkoutIntent> intents) {
        intents.subscribe(mIntentsSubject);
    }

    @Override
    public io.reactivex.Observable<DoWorkoutViewState> states() {
        return mStatesObservable;
    }

    /**
     * Compose all components to create the stream logic
     */
    private Observable<DoWorkoutViewState> compose() {
        return mIntentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(mActionProcessorHolder.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(DoWorkoutViewState.idle(), reducer)
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
    private final ObservableTransformer<DoWorkoutIntent, DoWorkoutIntent> intentFilter =
            intents -> intents.publish(shared ->
                    Observable.merge(
                            shared.ofType(DoWorkoutIntent.SaveWorkout.class).take(1),
                            shared.filter(intent -> !(intent instanceof DoWorkoutIntent.SaveWorkout))
                    )
            );

    /**
     * Translate an {@link MviIntent} to an {@link MviAction}.
     * Used to decouple the UI and the business logic to allow easy testings and reusability.
     */
    private DoWorkoutAction actionFromIntent(MviIntent intent) {
        if (intent instanceof DoWorkoutIntent) {
            Workout workout = ((DoWorkoutIntent.SaveWorkout) intent).workout();

            //TODO check that workout is acceptable
            //TODO where do we take token for remote load?
            assert workout != null;
            return DoWorkoutAction.SaveWorkout.create(((DoWorkoutIntent.SaveWorkout) intent).userToken(),workout);
        }

        throw new IllegalArgumentException("do not know how to treat this intent " + intent);
    }

    /**
     * The Reducer is where {@link MviViewState}, that the {@link MviView} will use to
     * render itself, are created.
     * It takes the last cached {@link MviViewState}, the latest {@link MviResult} and
     * creates a new {@link MviViewState} by only updating the related fields.
     * This is basically like a big switch statement of all possible types for the {@link MviResult}
     */
    private static final BiFunction<DoWorkoutViewState, DoWorkoutResult, DoWorkoutViewState> reducer =
            (previousState, result) -> {
                DoWorkoutViewState.Builder stateBuilder = previousState.buildWith();
                if (result instanceof DoWorkoutResult.SaveWorkout) {
                    DoWorkoutResult.SaveWorkout saveWorkoutResult =
                            (DoWorkoutResult.SaveWorkout) result;
                    switch (saveWorkoutResult.status()) {
                        case SUCCESS:
                            return stateBuilder.build();
                        case FAILURE:
                           return stateBuilder.error(((DoWorkoutResult.SaveWorkout) result).error()).build();
                        case RUNNING:
                            stateBuilder.loading(true);
                            return stateBuilder.build();
                    }
                }

                // Fail for unhandled results
                throw new IllegalStateException("Mishandled result? Should not happen―as always: " + result);
            };
}