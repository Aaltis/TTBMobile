package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;

import io.reactivex.Observable;

public interface WorkoutTemplatesListView {
    /**
     * The search intent
     *
     * @return An observable emitting the search query text
     */

    Observable<WorkoutTemplatesListIntent.InitialIntent> loadIntents();

    /**
     * Renders the View
     *
     * @param viewState The current viewState state that should be displayed
     */
    void render(WorkoutTemplatesListState viewState);
}
