package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList.WorkoutTemplatesListActivity;
import fi.breakwaterworks.model.Movement;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class WorkoutTemplateInitializationActivity extends AppCompatActivity implements WorkoutTemplateInitializationListView {
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private WorkoutTemplateInitializationListPresenter presenter;
    private FloatingActionButton initializeOk;
    private ListView workoutTemplateInitializationListView;
    private Long worklogId;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitViews();
        worklogId = getIntent().getLongExtra(WorkoutTemplatesListActivity.WORKLOG_ID_EXTRA, 0);
        presenter = new WorkoutTemplateInitializationListPresenter(this);
        bind();
    }

    private void InitViews() {
        setContentView(R.layout.activity_workout_template_initialization);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initializeOk = findViewById(R.id.initialize_ok);
        this.workoutTemplateInitializationListView = findViewById(R.id.workoutInitializationListView);
    }

    private void bind() {
        try {
            mDisposables.add(presenter.states().subscribe(this::render));
            presenter.processIntents(loadIntents());
        } catch (Exception ex) {
            Log.d(this.getLocalClassName(), ex.getLocalizedMessage());
        }
    }

    @Override
    public Observable<WorkoutTemplateInitializationIntent> loadIntents() {
        return Observable.merge(initialIntent(worklogId), CreateProgram());
    }

    private Observable<WorkoutTemplateInitializationIntent.InitialIntent> initialIntent(long worklogId) {
        return Observable.just(new WorkoutTemplateInitializationIntent.InitialIntent().create(worklogId));
    }

    private Observable<WorkoutTemplateInitializationIntent.CreateProgram> CreateProgram() {
        return RxView.clicks(initializeOk).map(
                activated -> new WorkoutTemplateInitializationIntent.CreateProgram().create(getMovementsWithSetsReps(), worklogId)
        );
    }

    public List<Movement> getMovementsWithSetsReps(){
        List<Movement> result = new ArrayList<>();
        for (int i = 0; i < workoutTemplateInitializationListView.getAdapter().getCount(); i++) {
            Movement movement = (Movement) workoutTemplateInitializationListView.getAdapter().getItem(i);
            View view = workoutTemplateInitializationListView.getChildAt(i);
            String weight = ((EditText) view.findViewById(R.id.item_workout_template_movement_weight_amount_edit_text)).getText().toString();
            String reps = ((EditText) view.findViewById(R.id.item_workout_template_movement_reps_amount_edit_text)).getText().toString();
            try {
                if (!reps.isEmpty()) {
                    movement.setReps(Integer.valueOf(reps));
                }
                if (!weight.isEmpty()) {
                    movement.setWeight(Long.parseLong(weight));
                }
            }catch (Exception ex){
                Log.e(WorkoutTemplateInitializationActivity.class.getName(), "getMovementsWithSetsReps: ", ex );
            }
            result.add(movement);
        }
        return result;
    }

    @Override
    public void render(WorkoutTemplateInitializationListState viewState) {

        if (viewState instanceof WorkoutTemplateInitializationListState.Load) {
            switch (((WorkoutTemplateInitializationListState.Load) viewState).getStatus()) {
                case SUCCESS:
                    updateTitle("TTB");
                    renderLoadedMovementList(((WorkoutTemplateInitializationListState.Load) viewState).getResult());
                    break;
                case FAILURE:
                    ((WorkoutTemplateInitializationListState.Load) viewState).getError().getMessage();
                    updateTitle("error");
                    break;
                case RUNNING:
                    updateTitle("loading");
                    break;
            }
        }
    }

    private void updateTitle(String text) {
        runOnUiThread(() -> toolbar.setTitle(text));
    }

    private void renderLoadedMovementList(List<Movement> result) {
        runOnUiThread(() -> {
            WorkoutTemplateInitializationListAdapter workoutTemplatesListAdapter
                    = new WorkoutTemplateInitializationListAdapter(this, result);
            workoutTemplateInitializationListView.setAdapter(workoutTemplatesListAdapter);

        });
    }
}
