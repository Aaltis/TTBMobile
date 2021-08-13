package fi.breakwaterworks.utility;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import fi.breakwaterworks.trackthatbarbellmobile.R;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;

public class StartingStrengthSaver {
    private TTBDatabase ttbDatabase;

    Context context;

    public StartingStrengthSaver(TTBDatabase ttbDatabase, Context context) {
        this.ttbDatabase = ttbDatabase;
        this.context = context;
    }

    public void saveSS() {
        try {
           // workLogRepository.insertWorkLogTemplate(CreateStartingStrenghTemplate(context, LoadIdsForSSMovements(ttbDatabase)));
        }catch (Exception ex){
            String message=ex.getMessage();
        }
    }

    private Map<String, Long> LoadIdsForSSMovements(TTBDatabase database) {
        Map<String, Long> movementIdList = new HashMap<>();

        movementIdList.put(context.getResources().getString(R.string.squat),
                database.movementDAO().getMovementWithName(context.getResources().getString(R.string.squat)).getMovementId());
        movementIdList.put(context.getResources().getString(R.string.overheadpress),
                database.movementDAO().getMovementWithName(context.getResources().getString(R.string.overheadpress)).getMovementId());
        movementIdList.put(context.getResources().getString(R.string.deadlift),
                database.movementDAO().getMovementWithName(context.getResources().getString(R.string.deadlift)).getMovementId());
        movementIdList.put(context.getResources().getString(R.string.bench),
                database.movementDAO().getMovementWithName(context.getResources().getString(R.string.bench)).getMovementId());
        return movementIdList;

    }

}
