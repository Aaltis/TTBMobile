package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;

import android.content.Context;

import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;
import fi.breakwaterworks.networking.repository.WorkLogRepository;

public class WorkoutTemplatesListActionProcessHolder {

    private WorkLogRepository workLogRepository;

    public WorkoutTemplatesListActionProcessHolder(Context context) {
        this.workLogRepository = new WorkLogRepository(context);
    }

    private ObservableTransformer<WorkoutTemplatesListAction.InitialTask, WorkoutTemplatesListResult.Load>
            loadTaskProcessor = actions -> actions.flatMap(action ->
    {
        return workLogRepository.loadAllTemplates()
                .map(WorkoutTemplatesListResult.Load::success)
                .onErrorReturn(WorkoutTemplatesListResult.Load::failure)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .startWith(WorkoutTemplatesListResult.Load.running());
    });
    ObservableTransformer<WorkoutTemplatesListAction, WorkoutTemplatesListResult> actionProcessor =
            actions -> actions.publish(shared -> shared.ofType(WorkoutTemplatesListAction.InitialTask.class).compose(loadTaskProcessor)
            );

}
