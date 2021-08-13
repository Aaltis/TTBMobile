package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization.WorkoutTemplateInitializationActivity;
import fi.breakwaterworks.model.WorkLog;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class WorkoutTemplatesListActivity extends AppCompatActivity implements WorkoutTemplatesListView {

    private ListView workoutLayoutsListView;
    private WorkoutTemplateListPresenter workoutTemplateListPresenter;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    public static String WORKLOG_ID_EXTRA = "worklogId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_templates_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        workoutLayoutsListView = findViewById(R.id.workoutLayoutsListView);
        setSupportActionBar(toolbar);
        workoutTemplateListPresenter = new WorkoutTemplateListPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        bind();
    }

    private void bind() {
        try {
            mDisposables.add(workoutTemplateListPresenter.states().subscribe(this::render));
            workoutTemplateListPresenter.processIntents(loadIntents());
        } catch (Exception ex) {
            Log.d(this.getLocalClassName(), ex.getLocalizedMessage());
        }
    }

    @Override
    public Observable<WorkoutTemplatesListIntent.InitialIntent> loadIntents() {
        return initialIntent();
    }

    private Observable<WorkoutTemplatesListIntent.InitialIntent> initialIntent() {
        return Observable.just(new WorkoutTemplatesListIntent.InitialIntent().create());
    }

    @Override
    public void render(WorkoutTemplatesListState viewState) {
        if (viewState instanceof WorkoutTemplatesListState.Load) {
            switch (((WorkoutTemplatesListState.Load) viewState).getStatus()) {
                case SUCCESS:
                    Log.d(this.getLocalClassName(), "loaded");
                    renderLoaded(((WorkoutTemplatesListState.Load) viewState).getResult(), this);
                case FAILURE:
                    Throwable g = ((WorkoutTemplatesListState.Load) viewState).getError();
                    if (((WorkoutTemplatesListState.Load) viewState).getError().getMessage() != null) {
                        Log.e(this.getLocalClassName(), ((WorkoutTemplatesListState.Load) viewState).getError().getMessage());
                    } else {
                        Log.e(this.getLocalClassName(), "failure:");
                    }
                case RUNNING:
                    Log.d(this.getLocalClassName(), "running");
            }

        }
    }

    public void renderLoaded(List<WorkLog> result, Context context) {

        runOnUiThread(() -> {
            if (result.size() == 0) {
                Toast.makeText(context, "Workouts Not Found.", Toast.LENGTH_SHORT);
                return;
            }
            WorkoutTemplatesListAdapter workoutTemplatesListAdapter
                    = new WorkoutTemplatesListAdapter(context, result);
            workoutLayoutsListView.setAdapter(workoutTemplatesListAdapter);
            workoutLayoutsListView.setOnItemClickListener((parent, view, position, id) -> {
                WorkLog workLog = (WorkLog) workoutLayoutsListView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), WorkoutTemplateInitializationActivity.class);
                intent.putExtra(WORKLOG_ID_EXTRA, workLog.getWorkLogId());
                startActivity(intent);
            });
        });
    }
}
