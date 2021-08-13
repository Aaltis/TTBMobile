package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.listitem.MovementsListItemViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.common.ViewMvcFactory;


public class MovementsRecyclerAdapter extends RecyclerView.Adapter<MovementsRecyclerAdapter.MyViewHolder>
        implements MovementsListItemViewMvc.Listener {

    @Override
    public void onMovementClicked(Movement movement) {
        mListener.onMovementClicked(movement);
    }

    public void bindMovements(List<Movement> movements) {
        mMovements = new ArrayList<>(movements);
        mMovements = movements;
        notifyDataSetChanged();
    }

    public interface Listener {
        void onMovementClicked(Movement movement);
    }

    private final Listener mListener;
    private List<Movement> mMovements = new ArrayList<>();
    private final ViewMvcFactory mViewMvcFactory;

    public MovementsRecyclerAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        mListener = listener;
        mViewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovementsListItemViewMvc viewMvc = mViewMvcFactory.getMovementsListItemViewMvc(parent);
        viewMvc.registerListener(this);
        return new MyViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mViewMvc.bindMovement(mMovements.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovements.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final MovementsListItemViewMvc mViewMvc;

        public MyViewHolder(MovementsListItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }

    }

}
