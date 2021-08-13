package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.listitem.ExerciseListItemViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.common.ViewMvcFactory;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.MyViewHolder>
        implements ExerciseListItemViewMvc.Listener {

    List<Exercise> mExercises;
    Activity mParentActivity;

    @Override
    public void addReps() {

    }

    public void bindExercises(List<Exercise> exercises) {
        mExercises.clear();
        mExercises.addAll(exercises);
        notifyDataSetChanged();
    }

    public interface Listener {
        void onExerciseClicked(Movement movement);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ExerciseListItemViewMvc mViewMvc;

        public MyViewHolder(ExerciseListItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }
    }

    private final ExerciseRecyclerAdapter.Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;

    public ExerciseRecyclerAdapter(Listener listener, Activity parentActivity, ViewMvcFactory viewMvcFactory) {
        mListener = listener;
        mViewMvcFactory = viewMvcFactory;
        mExercises = new ArrayList<>();
        mParentActivity = parentActivity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseListItemViewMvc viewMvc = mViewMvcFactory.getExerciseListItemViewMvc(parent, mParentActivity);
        viewMvc.registerListener(this);
        return new MyViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mViewMvc.bindExercise(mExercises.get(position));
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

}


