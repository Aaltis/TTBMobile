package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;
import fi.breakwaterworks.networking.repository.WorkLogRepository;
import fi.breakwaterworks.utility.WorkoutGenerator;

class WorkoutTemplateInitializationActionProcessorHolder {

    private WorkLogRepository workLogRepository;

    // There is nothing wrong with handler classes (done right)
    private WorkoutGenerator workoutGenerator;

    WorkoutTemplateInitializationActionProcessorHolder(Context context) {
        this.workLogRepository = new WorkLogRepository(context);
        this.workoutGenerator = new WorkoutGenerator(context);
    }

    private ObservableTransformer<WorkoutTemplateInitializationListAction.InitialTask, WorkoutTemplateInitializationListState.Load>
            loadTaskProcessor =
            actions -> actions.flatMap(action ->
            {
                return workLogRepository.loadMovementsFromWorkLog(action.getTemplateId())
                        .map(WorkoutTemplateInitializationListState.Load::success)
                        .onErrorReturn(WorkoutTemplateInitializationListState.Load::failure)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .startWith(WorkoutTemplateInitializationListState.Load.running());
            });

    private ObservableTransformer<WorkoutTemplateInitializationListAction.CreateTemplate, WorkoutTemplateInitializationListState.CreateTemplate>
            generateWorkLogTemplateProcessor =
            actions -> actions.flatMap(action ->
            {
                return workoutGenerator.generateWorkLog(action.getMovements(), action.getWorklogTemplateId())
                        .map(WorkoutTemplateInitializationListState.CreateTemplate::success)
                        .onErrorReturn(WorkoutTemplateInitializationListState.CreateTemplate::failure)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .startWith(WorkoutTemplateInitializationListState.CreateTemplate.running());
            });

    ObservableTransformer<WorkoutTemplateInitializationListAction, WorkoutTemplateInitializationListState> actionProcessor =
            actions -> actions.publish(shared -> Observable.merge(
                    shared.ofType(WorkoutTemplateInitializationListAction.InitialTask.class).compose(loadTaskProcessor),
                    shared.ofType(WorkoutTemplateInitializationListAction.CreateTemplate.class).compose(generateWorkLogTemplateProcessor)
            ));


}
