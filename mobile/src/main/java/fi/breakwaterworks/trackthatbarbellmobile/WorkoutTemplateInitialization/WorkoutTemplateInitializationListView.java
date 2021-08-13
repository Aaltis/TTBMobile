package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;

import io.reactivex.Observable;

public interface WorkoutTemplateInitializationListView{

    Observable<WorkoutTemplateInitializationIntent> loadIntents();
    void render(WorkoutTemplateInitializationListState viewState);
}
