package fi.breakwaterworks.trackthatbarbellmobile;

import android.os.Bundle;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.support.wearable.activity.WearableActivity;

public class MainActivity extends WearableActivity {

    /*https://www.grokkingandroid.com/first-glance-androids-recyclerview/*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IconData[] data = new IconData[] {
                new IconData("5x5", android.R.drawable.ic_delete),


        };

        WearableRecyclerView recyclerView = findViewById(R.id.recyclerView);
        IconAdapter adapter = new IconAdapter(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEdgeItemsCenteringEnabled(true);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        // Handle our Wearable List's click events


    }
}
