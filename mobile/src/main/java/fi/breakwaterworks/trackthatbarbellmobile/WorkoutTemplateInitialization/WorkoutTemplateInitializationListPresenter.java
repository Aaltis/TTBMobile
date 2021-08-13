package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class WorkoutTemplateInitializationListPresenter implements WorkoutTemplateInitializationListState {
    @NonNull
    private PublishSubject<WorkoutTemplateInitializationIntent> mIntentsSubject;
    @NonNull
    private Observable<WorkoutTemplateInitializationListState> mStatesObservable;

    /**
     * Contains and executes the business logic of all emitted actions.
     */
    @NonNull
    private WorkoutTemplateInitializationActionProcessorHolder mActionProcessorHolder;

    public Observable<WorkoutTemplateInitializationListState> states() {
        return mStatesObservable;
    }

    public WorkoutTemplateInitializationListPresenter(Context context) {
        this.mActionProcessorHolder = new WorkoutTemplateInitializationActionProcessorHolder(context);
        mIntentsSubject = PublishSubject.create();
        mStatesObservable = compose();
    }


    public void processIntents(Observable<WorkoutTemplateInitializationIntent> intents) {
        intents.subscribe(mIntentsSubject);
    }

    private WorkoutTemplateInitializationListAction actionFromIntent(WorkoutTemplateInitializationIntent intent) {

        if (intent instanceof WorkoutTemplateInitializationIntent.InitialIntent) {

            return new WorkoutTemplateInitializationListAction.InitialTask().create(((WorkoutTemplateInitializationIntent.InitialIntent) intent).getWorklogId());
        }

        if (intent instanceof WorkoutTemplateInitializationIntent.CreateProgram) {
            Log.d("actionFromIntent",((WorkoutTemplateInitializationIntent.CreateProgram) intent).getMovements().toString());
            return new WorkoutTemplateInitializationListAction.CreateTemplate().create(
                    ((WorkoutTemplateInitializationIntent.CreateProgram) intent).getMovements(),
                    ((WorkoutTemplateInitializationIntent.CreateProgram) intent).getWorklogId()
            );
        }
        return null;
    }

    public PublishSubject<WorkoutTemplateInitializationIntent> getIntentSubject(){
        return this.mIntentsSubject;
    }
    /**
     * Compose all components to create the stream logic
     */
    private Observable<WorkoutTemplateInitializationListState> compose() {
        return mIntentsSubject
                .map(this::actionFromIntent)
                // Special case where we do not want to pass this event down the stream
                .compose(mActionProcessorHolder.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                // .scan(AddEditTaskViewState.idle(), reducer)
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
}