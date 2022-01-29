package fi.breakwaterworks.trackthatbarbellmobile.MainView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import fi.breakwaterworks.trackthatbarbellmobile.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);

    }
}