package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.listitem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseObservableViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.R;

public class MovementsListItemViewMvcImpl extends BaseObservableViewMvc<MovementsListItemViewMvc.Listener>
        implements MovementsListItemViewMvc {

    private final TextView mTextMovementName;
    private final TextView mTextMovementType;
    private final TextView mTextMovementStance;
    private final TextView mTextMovementGrip;
    private Movement mMovement;

    public MovementsListItemViewMvcImpl(@NotNull LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_pick_movement, parent, false));
        mTextMovementName = findViewById(R.id.textview_movement_name);
        mTextMovementType = findViewById(R.id.textview_movement_type);
        mTextMovementStance = findViewById(R.id.textview_movement_stance);
        mTextMovementGrip = findViewById(R.id.textview_movement_grip);
        getRootView().setOnClickListener(view -> {
            for (Listener listener : getListeners()) {
                listener.onMovementClicked(mMovement);
            }
        });
    }

    @Override
    public void bindMovement(Movement movement) {
        mMovement = movement;
        mTextMovementName.setText(movement.getName());
        mTextMovementType.setText(movement.getType());
        mTextMovementStance.setText(movement.getStance());
        mTextMovementGrip.setText(movement.getGrip());

    }
}