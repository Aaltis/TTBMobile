package fi.breakwaterworks.networking.local.repository;

import android.content.Context;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import io.reactivex.Single;

public class MovementRepository {
    TTBDatabase database;

    public MovementRepository(Context context) {
        this.database = TTBDatabase.getInstance(context);
    }

    public Single<List<Movement>> GetAllMovements() {
        return database.movementDAO().getAllMovements();
    }

}
