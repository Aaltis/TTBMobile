package fi.breakwaterworks.trackthatbarbellmobile.MainView.Processors;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import fi.breakwaterworks.model.Config;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.networking.local.repository.WorkLogRepository;
import fi.breakwaterworks.networking.server.MovementsService;
import fi.breakwaterworks.networking.server.RetrofitClientInstance;
import fi.breakwaterworks.networking.server.response.MovementResponse;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import io.reactivex.Observable;

public class RemoteUpdateProcess {
    TTBDatabase ttbDatabase;
    Context context;
    WorkLogRepository workLogRepository;
    MovementsService movementsService;

    public RemoteUpdateProcess(TTBDatabase ttbDatabase, Context context, WorkLogRepository workLogRepository) {
        this.ttbDatabase = ttbDatabase;
        this.context = context;
        this.workLogRepository = workLogRepository;
    }

    public Observable<Boolean> Load() {
        return ttbDatabase.ConfigDAO().loadOneConfigObservable()
                .flatMap(this::UpdateMovementsFromServer);
    }


    public Observable<String> getTimeMovementsUpdatedAtServer(Config config) {
        movementsService = RetrofitClientInstance.getRetrofitInstance(config.getServerUrl()).create(MovementsService.class);
        return movementsService.getTimeMovementsUpdated().flatMapObservable(
                v -> {
                    return Observable.just(v);
                }
        );
    }

    /**
     * Do server call and ask last time movements have been updated.
     * If time is different in client and server get alla movements from local and remote and compare + save.
     *
     * @param config
     * @return list<Boolean>
     */
    public Observable<Boolean> UpdateMovementsFromServer(Config config) throws Exception {
        if (config.getServerUrl() == null || config.getServerUrl() == "") {
            throw new Exception("no server url");
        }

        String x = config.getServerUrl();
        movementsService = RetrofitClientInstance.getRetrofitInstance(config.getServerUrl()).create(MovementsService.class);


        return getRemoteMovements()
                .flatMap(this::getLocalMovements)
                .flatMap(this::compareLocalAndRemoteMovementsAndSaveToDatabase);


    }

    public Observable<List<Movement>> getRemoteMovements() {
        return movementsService.getAllMovements().flatMapObservable(
                remoteMovements -> {
                    List<Movement> movements = new ArrayList<>();
                    for (MovementResponse movementResponse : remoteMovements) {
                        movements.add(new Movement(movementResponse));
                    }
                    return Observable.just(movements);
                });
    }

    public Observable<Pair<List<Movement>, List<Movement>>> getLocalMovements(List<Movement> remoteMovements) {
        return ttbDatabase.movementDAO().getAllMovements().flatMapObservable(
                localMovements -> Observable.just(new Pair<>(remoteMovements, localMovements))
        );
    }

    private Observable<Boolean> compareLocalAndRemoteMovementsAndSaveToDatabase(Pair<List<Movement>, List<Movement>> movements) {
        List<Movement> removeMovements = movements.first;
        List<Movement> localMovements = movements.second;

        List<Movement> updateMovements = new ArrayList<>();
        List<Movement> newMovements = new ArrayList<>();

        //todo should every movement be refreshed or should we have timestamp on every movement?
        for (Movement remoteMovement : removeMovements) {
            Movement found = null;

            //try find with id
            found = localMovements.stream()
                    .filter(movement -> remoteMovement.getServerId().equals(movement.getServerId()))
                    .findAny()
                    .orElse(null);
            //try find with name
            if (found == null) {
                found = localMovements.stream()
                        .filter(movement -> remoteMovement.getName().equals(movement.getName()))
                        .findAny()
                        .orElse(null);

                // local version is most likely from first time initialization txt.
                // update everything but name.
                if (found != null) {
                    Movement copy = found;
                    copy.setServerId(remoteMovement.getServerId());
                    copy.setGrip(remoteMovement.getGrip());
                    copy.setStance(remoteMovement.getStance());
                    copy.setType(remoteMovement.getType());
                    updateMovements.add(copy);
                    continue;
                }
            }
            if (found == null) {
                newMovements.add(remoteMovement);
            } else {
                Movement copy = found;
                copy.setName(remoteMovement.getName());
                copy.setServerId(remoteMovement.getServerId());
                copy.setGrip(remoteMovement.getGrip());
                copy.setStance(remoteMovement.getStance());
                copy.setType(remoteMovement.getType());
                updateMovements.add(copy);

            }
        }
        Movement[] updateMovementArray = new Movement[updateMovements.size()];
        updateMovements.toArray(updateMovementArray);
        ttbDatabase.movementDAO().UpdateMovements(updateMovementArray);
        Movement[] newMovementsArray = new Movement[newMovements.size()];
        newMovements.toArray(newMovementsArray);
        ttbDatabase.movementDAO().insertAll(new Movement[newMovements.size()]);

        return Observable.just(true);
    }
}
