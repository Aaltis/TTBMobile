package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import fi.breakwaterworks.mvibase.MviIntent;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.subjects.PublishSubject;

import static fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList.WorkoutTemplatesListState.Load.idle;

public class WorkoutTemplateListPresenter implements WorkoutTemplatesListState {
    private Observable<WorkoutTemplatesListState> mStatesObservable;

    @NonNull
    public PublishSubject<WorkoutTemplatesListIntent> mIntentsSubject;

    private WorkoutTemplatesListActionProcessHolder workoutTemplatesListActionProcessHolder;

    public Observable<WorkoutTemplatesListState> states() {
        return mStatesObservable;
    }

    public WorkoutTemplateListPresenter(Context context) {

        workoutTemplatesListActionProcessHolder = new WorkoutTemplatesListActionProcessHolder(context);
        mIntentsSubject = PublishSubject.create();
        mStatesObservable = compose();
    }


    public void processIntents(Observable<WorkoutTemplatesListIntent.InitialIntent> intents) {
        intents.subscribe(mIntentsSubject);
    }

    /**
     * Compose all components to create the stream logic
     */
    private Observable<WorkoutTemplatesListState> compose() {
        return mIntentsSubject
                .map(this::actionFromIntent)
                .compose(workoutTemplatesListActionProcessHolder.actionProcessor)
                .scan(idle(), reducer)
               // .distinctUntilChanged()
                .replay(1)
                .autoConnect(0);
    }

    private WorkoutTemplatesListAction actionFromIntent(MviIntent intent) {

        if (intent instanceof WorkoutTemplatesListIntent.InitialIntent) {
            return new WorkoutTemplatesListAction.InitialTask().create();
        }
        throw new IllegalArgumentException("do not know how to treat this intent " + intent);
    }
    private static BiFunction<WorkoutTemplatesListState, WorkoutTemplatesListResult, WorkoutTemplatesListState> reducer =
            (previousState, result) -> {
                if (result instanceof WorkoutTemplatesListResult.Load) {
                    WorkoutTemplatesListResult.Load newState =
                            (WorkoutTemplatesListResult.Load) result;
                    WorkoutTemplatesListState.Load stateBuilder = (WorkoutTemplatesListState.Load) previousState;
                    switch (newState.getStatus()) {
                        case SUCCESS:
                            WorkoutTemplatesListState.Load returnState = new WorkoutTemplatesListState.Load(stateBuilder);
                            returnState.setResult(newState.getResult());
                            returnState.setStatus(newState.getStatus());
                            Log.d(WorkoutTemplateListPresenter.class.getName(), "success");
                            return (WorkoutTemplatesListState) returnState;
                        case FAILURE:
                            Throwable error = newState.getError();
                            stateBuilder.setError(error);
                            return (WorkoutTemplatesListState) previousState;
                        case RUNNING:
                            return stateBuilder;
                    }
                }

                throw new IllegalStateException("Mishandled result? Should not happen―as always: " + result);
            };
}