package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.model.WorkLog;

public class WorkoutTemplatesListAdapter extends ArrayAdapter<WorkLog> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public WorkoutTemplatesListAdapter(Context context, List<WorkLog> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_workout_layouts_list, parent, false);

            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WorkLog item = getItem(position);
        if (item!= null) {
            TextView nameTextView = convertView.findViewById(R.id.item_workout_layouts_list_textview);

            nameTextView.setText(item.getName());
        }

        return convertView;
    }
}
