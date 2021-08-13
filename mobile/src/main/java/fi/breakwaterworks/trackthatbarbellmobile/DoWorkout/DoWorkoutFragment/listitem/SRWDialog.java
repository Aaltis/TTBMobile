package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.listitem;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fi.breakwaterworks.trackthatbarbellmobile.R;

public class SRWDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public Button btnAdd;
    public EditText editText_set;
    public EditText editText_reps;
    public EditText editText_weight;
    public TextView textView_error;

    public AddClickListener mListener;

    public SRWDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.srw_dialog);
        editText_set = findViewById(R.id.srw_dialog_edittext_set);
        editText_reps = findViewById(R.id.srw_dialog_edittext_reps);
        editText_weight = findViewById(R.id.srw_dialog_edittext_weight);
        textView_error = findViewById(R.id.srw_dialog_text_view_error);

        btnAdd = findViewById(R.id.srw_dialog_add_btn);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (editText_reps.getText().toString().isEmpty() || editText_weight.getText().toString().isEmpty()) {
            textView_error.setText("Write atleast reps and weight.");
            return;
        }

        mListener.getValues(editText_set.getText().toString(),
                editText_reps.getText().toString(),
                editText_weight.getText().toString());

        dismiss();
    }

    public void bindListener(ExerciseListItemViewMvcImpl receiver) {
        mListener = receiver;

    }

    public interface AddClickListener {
        Void getValues(String set, String reps, String weight);
    }

}