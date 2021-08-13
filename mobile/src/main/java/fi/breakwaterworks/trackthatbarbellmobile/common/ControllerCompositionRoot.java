package fi.breakwaterworks.trackthatbarbellmobile.common;

import android.app.Activity;
import android.view.LayoutInflater;

public class ControllerCompositionRoot {
    private final Activity mActivity;

    public ControllerCompositionRoot(Activity activity) {
        mActivity = activity;
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater());
    }
}
