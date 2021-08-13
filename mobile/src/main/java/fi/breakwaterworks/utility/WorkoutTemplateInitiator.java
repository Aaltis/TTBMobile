package fi.breakwaterworks.utility;

import android.content.Context;

import org.json.JSONException;

import java.util.List;

import fi.breakwaterworks.networking.repository.WorkLogRepository;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.model.WorkLog;

public class WorkoutTemplateInitiator {
    private Context context;
    private StartingStrengthSaver strengthSaver;
    private TextParser textParser;
    private WorkLogRepository workLogRepository;

    public WorkoutTemplateInitiator(Context context) {
        this.context = context;
        workLogRepository = new WorkLogRepository(context);

        strengthSaver = new StartingStrengthSaver(TTBDatabase.getInstance(context), context);
        textParser = new TextParser(context);
    }

    public String SaveWorkoutsFromFiles(List<WorkLog> workLogList ) throws JSONException {
        try {

            workLogRepository.insertWorkLogTemplate(workLogList);
            return "done";

        }catch (Exception ex){
            return ex.getLocalizedMessage();
        }
    }

}

