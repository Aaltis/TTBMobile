package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import fi.breakwaterworks.trackthatbarbellmobile.R;

public class LeaveWorkoutDialog extends Dialog {

    public LeaveWorkoutDialogListener mListener;

    public interface LeaveWorkoutDialogListener {
        void saveWorkoutAndExit(boolean save);

        void cancel();
    }

    public LeaveWorkoutDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.leave_workout_dialog);
        Button btnSave = findViewById(R.id.buttonYesSave);
        Button btnNoSave = findViewById(R.id.btnNoSave);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(v -> mListener.saveWorkoutAndExit(true));
        btnNoSave.setOnClickListener(v -> mListener.saveWorkoutAndExit(false));
        btnCancel.setOnClickListener(v -> mListener.cancel());

    }

}