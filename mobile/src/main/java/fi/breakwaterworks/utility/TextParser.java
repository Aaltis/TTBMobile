package fi.breakwaterworks.utility;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Exercise;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.SetRepsWeight;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.model.Workout;
import fi.breakwaterworks.networking.local.repository.WorkLogRepository;
import fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization.WorkoutTemplateInitializationActivity;

public class TextParser {
    WorkLogRepository workLogRepository;
    static String templatesFolder = "templates";

    public TextParser(Context context) {
        workLogRepository = new WorkLogRepository(context);
    }

    public String loadSingleFileFromAssetsAsSingleString(Context context, String file) {
        String text = null;
        try {

            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return text;
    }

    public List<String> loadSingleFileFromAssetsAsListOfStrings(Context context, String file) {
        List<String> stringList = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(file)))) {
            while (reader.ready()) {
                String line = reader.readLine();
                stringList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stringList;

    }

    public List<WorkLog> loadWorkoutTemplates(Context context) {
        String fileList[] = new String[0];
        List<String> jsonList = new ArrayList<>();
        List<WorkLog> workLogs = new ArrayList<>();
        try {
            fileList = context.getAssets().list(templatesFolder);
        } catch (Exception ex) {
            Log.e(TextParser.class.getName(), "failed to load fileList", ex);
        }
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                try {
                    jsonList.add(loadSingleFileFromAssetsAsSingleString(context, templatesFolder + "/" + fileList[i]));
                } catch (Exception ex) {
                    Log.e(TextParser.class.getName(), "loadSingleFileFromAssets:", ex);
                }
            }
            for (String workLogTemplateString : jsonList) {
                try {
                    workLogs.add(jsonToWorklog(workLogTemplateString, context));
                } catch (Exception ex) {
                    Log.e(TextParser.class.getName(), "failed to create json to worklog.:", ex);
                }
            }
        }

        return workLogs;
    }

    private WorkLog jsonToWorklog(String templateJsonString, Context context) throws JSONException {
        JSONObject workLogJsonObject = new JSONObject(templateJsonString);
        WorkLog workLog = new WorkLog(workLogJsonObject.getString("name"), jsonWorkoutArrayToObjectList(workLogJsonObject.getJSONArray("workouts")), true);
        return workLog;
    }

    private ArrayList<Workout> jsonWorkoutArrayToObjectList(JSONArray workoutArray) throws JSONException {
        ArrayList<Workout> workoutArrayList = new ArrayList<>();
        for (int w = 0; w < workoutArray.length(); w++) {
            workoutArrayList.add(new Workout(workoutArray.getJSONObject(w).getString("name"),
                    jsonExerciseArrayToObjectList(workoutArray.getJSONObject(w).getJSONArray("exercises")),
                    true));
        }
        return workoutArrayList;
    }

    private List<Exercise> jsonExerciseArrayToObjectList(JSONArray exercicesArray) throws JSONException {
        List<Exercise> exerciseList = new ArrayList<>();
        for (int e = 0; e < exercicesArray.length(); e++) {
            exerciseList.add(new Exercise(exercicesArray.getJSONObject(e).getInt("orderNumber"),
                    exercicesArray.getJSONObject(e).getString("movementName"),
                    SetTypeEnum.valueOf(exercicesArray.getJSONObject(e).getString("setType")),
                    jsonSetRepsWeightsArrayToObjectList(exercicesArray.getJSONObject(e).getJSONArray("setRepsWeights"))));
        }
        return exerciseList;
    }

    private List<SetRepsWeight> jsonSetRepsWeightsArrayToObjectList(JSONArray setRepsWeightsArray) throws JSONException {
        List<SetRepsWeight> srwList = new ArrayList<>();
        for (int s = 0; s < setRepsWeightsArray.length(); s++) {
            srwList.add(new SetRepsWeight(setRepsWeightsArray.getJSONObject(s).getInt("set"),
                    setRepsWeightsArray.getJSONObject(s).getInt("reps")));
        }
        return srwList;
    }


    public Movement[] LoadMovements(Context context) {

        List<String> movementLines = loadSingleFileFromAssetsAsListOfStrings(context, "movements.txt");
        List<Movement> movements = new ArrayList<>();
        for (String line : movementLines) {
            try {

                String name = "";
                String details = "";
                String type = "";

                String[] movement = line.split(",");

                if (movement[0].contains(" - ")) {

                    String[] nameAndDetails = movement[0].split(" - ");
                    name = nameAndDetails[0];
                    details = nameAndDetails[1];
                } else {
                    name = movement[0];
                }
                if (movement.length > 1) {
                    type = movement[1];
                }
                movements.add(new Movement(name, details, type));

            } catch (Exception e) {
                Log.e(WorkoutTemplateInitializationActivity.class.getName(), "LoadMovements: ", e);
            }
        }
        Log.d("TextParser", String.format("LoadMovements: found %s movements", movements.size()));
        Movement[] itemsArray = new Movement[movements.size()];
        return movements.toArray(itemsArray);
    }

}
