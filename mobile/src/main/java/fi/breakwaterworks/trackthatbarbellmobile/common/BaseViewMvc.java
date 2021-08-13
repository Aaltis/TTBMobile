package fi.breakwaterworks.trackthatbarbellmobile.common;

import android.content.Context;
import android.view.View;

import fi.breakwaterworks.mvibase.MviView;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MainActivityViewState;


public abstract class BaseViewMvc implements ViewMvc {

    private View mRootView;

    @Override
    public View getRootView() {
        return mRootView;
    }

    protected void setRootView(View rootView) {
        mRootView = rootView;
    }

    protected <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    protected Context getContext() {
        return getRootView().getContext();
    }
}
