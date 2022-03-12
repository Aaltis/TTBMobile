package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.listitem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseObservableViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.common.ViewMvcFactory;

public class ExerciseListItemViewMvcImpl extends BaseObservableViewMvc<ExerciseListItemViewMvc.Listener>
        implements ExerciseListItemViewMvc, SRWDialog.AddClickListener {

    private final TextView mTxtExerciseName;
    private final Button buttonAddSetRepsWeight;
    private final Button buttonDelete;

    private final LinearLayout mLinearLayoutSetRepsWeight;
    private Exercise mExercise;
    private LayoutInflater mInflater;
    private ViewGroup mViewGroup;

    public ExerciseListItemViewMvcImpl(@NotNull LayoutInflater inflater, ViewGroup parent, Activity parentActivity, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.item_exercise, parent, false));
        mInflater = inflater;
        mViewGroup = parent;
        mTxtExerciseName = findViewById(R.id.textview_exercise_movement_name);
        mLinearLayoutSetRepsWeight = findViewById(R.id.linearlayout_setrepsweight);
        buttonAddSetRepsWeight = findViewById(R.id.button_open_SRW_dialog);
        buttonDelete = findViewById(R.id.button_delete);

        ExerciseListItemViewMvcImpl listenerBinding = this;
        buttonDelete.setOnClickListener(v -> {

            //remove srw views so the don't come visible when this item is recycled.
            mLinearLayoutSetRepsWeight.removeAllViews();

            for (ExerciseListItemViewMvc.Listener listener : getListeners()) {
                listener.DeleteExercise(mExercise);
            }
        });

        buttonAddSetRepsWeight.setOnClickListener(v -> {
            SRWDialog dialog = new SRWDialog(parentActivity);
            dialog.bindListener(listenerBinding);
            dialog.show();
        });
    }

    @Override
    public void bindExerciseToListItemView(Exercise exercise) {
        mExercise = exercise;
        mTxtExerciseName.setText(exercise.getMovementName());
    }

    @Override
    public void refreshSetRepsWeightList(List<SetRepsWeight> setRepsWeightList) {
        mLinearLayoutSetRepsWeight.removeAllViews();
        for (SetRepsWeight srw : setRepsWeightList) {
            View viewSRWItem = mInflater.inflate(R.layout.srw_item, mViewGroup, false);

            TextView textViewType = viewSRWItem.findViewById(R.id.text_view_exercise_list_srw_type);
            textViewType.setText(srw.getSetType().toString());

            TextView textViewSRWInText = viewSRWItem.findViewById(R.id.text_view_exercise_list_srw_in_text);
            textViewSRWInText.setText(srw.getAsString());
            mLinearLayoutSetRepsWeight.addView(viewSRWItem);
        }
    }


    @Override
    public Void setSetRepsWeightToExercise(List<SetRepsWeight> setRepsWeightList) {
        mExercise.AddToSetRepsWeights(setRepsWeightList);
        refreshSetRepsWeightList(mExercise.getSetRepsWeights());
        return null;
    }
}
