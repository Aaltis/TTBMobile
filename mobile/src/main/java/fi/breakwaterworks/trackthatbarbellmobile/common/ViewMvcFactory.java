package fi.breakwaterworks.trackthatbarbellmobile.common;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.listitem.ExerciseListItemViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.listitem.ExerciseListItemViewMvcImpl;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view.ExerciseListViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.view.ExerciseListViewMvcImpl;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.listitem.MovementsListItemViewMvcImpl;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.listitem.MovementsListItemViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view.MovementsListViewMvc;
import fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.PickExerciseFragment.view.MovementsListViewMvcImpl;


public class ViewMvcFactory {
    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }


    public MovementsListItemViewMvc getMovementsListItemViewMvc(ViewGroup parent) {
        return new MovementsListItemViewMvcImpl(mLayoutInflater, parent);
    }

    public MovementsListViewMvc getMovementsListViewMvc(ViewGroup parent) {
        return new MovementsListViewMvcImpl(mLayoutInflater, parent,this);
    }

    public ExerciseListViewMvc getExerciseListViewMvc(ViewGroup parent, Activity parentActivity) {
        return new ExerciseListViewMvcImpl(mLayoutInflater, parent,parentActivity,this);

    }

    public ExerciseListItemViewMvc getExerciseListItemViewMvc(ViewGroup parent,Activity parentActivity) {
        return (ExerciseListItemViewMvc) new ExerciseListItemViewMvcImpl(mLayoutInflater, parent,parentActivity,this);

    }
}
