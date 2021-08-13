package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.model.Movement;

public class WorkoutTemplateInitializationListAdapter extends ArrayAdapter<Movement> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public WorkoutTemplateInitializationListAdapter(Context context, List<Movement> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_workout_template_initialization, parent, false);

            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movement item = getItem(position);
        if (item!= null) {
            TextView nameTextView = convertView.findViewById(R.id.item_workout_template_movement_name_text_view);
            nameTextView.setText(item.getName());
        }

        return convertView;
    }


}
