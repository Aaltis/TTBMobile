package fi.breakwaterworks.trackthatbarbellmobile.DoWorkout.DoWorkoutFragment.listitem;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.SetType;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.databinding.SrwDialogBinding;

public class SRWDialog extends Dialog {

    public Activity activity;

    private List<SetRepsWeight> setRepsWeightList;
    public AddClickListener mListener;
    public int spinnerPosition;

    public SRWDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        setRepsWeightList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.srw_dialog);
        SrwDialogBinding binding = SrwDialogBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());

        Button btnMultiple = findViewById(R.id.button_add_multiple);
        btnMultiple.setOnClickListener(v -> {
            binding.srwDialogPlusButton.setVisibility(View.VISIBLE);
            binding.buttonAddMultiple.setVisibility(View.INVISIBLE);
        });


        binding.spinnerType.setAdapter(new ArrayAdapter<SetType>(getContext(), android.R.layout.simple_spinner_item, SetType.values()));
        binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                spinnerPosition = position;
                SetType selected_val = (SetType) binding.spinnerType.getItemAtPosition(position);
                if (selected_val.isMultiset()) {
                    binding.srwDialogPlusButton.setVisibility(View.VISIBLE);
                    binding.buttonAddMultiple.setVisibility(View.INVISIBLE);
                } else {
                    binding.srwDialogPlusButton.setVisibility(View.INVISIBLE);
                    binding.buttonAddMultiple.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        binding.srwDialogPlusButton.setOnClickListener(v -> {
            if (binding.srwDialogEdittextReps.getText().toString().isEmpty() || binding.srwDialogEdittextWeight.getText().toString().isEmpty()) {
                binding.srwDialogTextViewError.setText("Write atleast reps and weight.");
                return;
            }

            setRepsWeightList.add(new SetRepsWeight(Integer.parseInt(binding.srwDialogEdittextSet.getText().toString()),
                    Integer.parseInt(binding.srwDialogEdittextReps.getText().toString()),
                    Double.parseDouble(binding.srwDialogEdittextWeight.getText().toString()),
                    (SetType) binding.spinnerType.getItemAtPosition(spinnerPosition)));

            refreshList(binding);

        });

        binding.srwDialogButtonSave.setOnClickListener(v -> {
            SetType setType = (SetType) binding.spinnerType.getItemAtPosition(spinnerPosition);
            if (setType != null && setType.isMultiset()) {
                mListener.setSetRepsWeightToExercise(setRepsWeightList);
            }

            if (binding.srwDialogEdittextReps.getText().toString().isEmpty() ) {
                binding.srwDialogTextViewError.setText("Write atleast reps.");
                return;
            }

            int sets=0;
            int reps=0;
            double weigh=0;

            if(!binding.srwDialogEdittextSet.getText().toString().isEmpty()){
                sets=Integer.parseInt(binding.srwDialogEdittextSet.getText().toString());
            }
            if(!binding.srwDialogEdittextReps.getText().toString().isEmpty()){
                reps=Integer.parseInt(binding.srwDialogEdittextReps.getText().toString());
            }
            if(!binding.srwDialogEdittextWeight.getText().toString().isEmpty()){
                weigh=Double.parseDouble(binding.srwDialogEdittextWeight.getText().toString());
            }
            setRepsWeightList.add(new SetRepsWeight(sets, reps, weigh, setType));
            mListener.setSetRepsWeightToExercise(setRepsWeightList);

            dismiss();
        });
    }

    private void refreshList(SrwDialogBinding binding) {
        binding.linearLayoutSrwList.removeAllViews();

        for (SetRepsWeight setRepsWeight : setRepsWeightList) {
            final LinearLayout newSRWItemLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.srw_dialog_multiple_item, null);
            newSRWItemLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            final TextView srwTextView = newSRWItemLayout.findViewById(R.id.srw_dialog_multiple_item_textview);
            Button remove = newSRWItemLayout.findViewById(R.id.srw_dialog_remove_list_item_button);
            remove.setOnClickListener(v1 -> {
                        setRepsWeightList.remove(setRepsWeight);
                        refreshList(binding);
                    }
            );

            srwTextView.setGravity(Gravity.CENTER);
            srwTextView.setText(setRepsWeight.getAsString());
            binding.linearLayoutSrwList.addView(newSRWItemLayout);

        }
    }

    public void bindListener(ExerciseListItemViewMvcImpl receiver) {
        mListener = receiver;

    }

    public interface AddClickListener {
        Void setSetRepsWeightToExercise(List<SetRepsWeight> setRepsWeightList);
    }

}