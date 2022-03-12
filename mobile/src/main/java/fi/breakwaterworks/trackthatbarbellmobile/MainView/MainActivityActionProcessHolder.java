package fi.breakwaterworks.trackthatbarbellmobile.MainView;

import androidx.annotation.NonNull;

import fi.breakwaterworks.mvibase.MviAction;
import fi.breakwaterworks.mvibase.MviResult;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityAction;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.MVVM.MainActivityResult;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.Processors.LocalInitializationProcessor;
import fi.breakwaterworks.trackthatbarbellmobile.MainView.Processors.RemoteUpdateProcess;
import fi.breakwaterworks.trackthatbarbellmobile.Schedulers.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class MainActivityActionProcessHolder {

    @NonNull
    private LocalInitializationProcessor initializationProcessor;
    private BaseSchedulerProvider mSchedulerProvider;
    private RemoteUpdateProcess remoteUpdateProcess;
    public MainActivityActionProcessHolder(BaseSchedulerProvider baseSchedulerProvider,
                                           LocalInitializationProcessor initializationProcessor,
                                            RemoteUpdateProcess remoteUpdateProcess) {
        this.initializationProcessor = initializationProcessor;
        this.remoteUpdateProcess = remoteUpdateProcess;
        mSchedulerProvider = baseSchedulerProvider;
    }

    private ObservableTransformer<MainActivityAction.Initialization, MainActivityResult.Initialization>
            initialize =
            actions -> actions.flatMap(action ->
                    initializationProcessor.Initialize()
                            .map(MainActivityResult.Initialization::success)
                            .onErrorReturn(MainActivityResult.Initialization::failure)
                            .subscribeOn(mSchedulerProvider.io())
                            .observeOn(mSchedulerProvider.ui())
                            .startWith(MainActivityResult.Initialization.running()));

    private ObservableTransformer<MainActivityAction.UpdateFromServer, MainActivityResult.RemoteUpdateResult>
            RemoteUpdate =
            actions -> actions.flatMap(action ->
                    remoteUpdateProcess.Load()
                            .map(MainActivityResult.RemoteUpdateResult::success)
                            .onErrorReturn(MainActivityResult.RemoteUpdateResult::failure)
                            .subscribeOn(mSchedulerProvider.io())
                            .observeOn(mSchedulerProvider.ui())
                            .startWith(MainActivityResult.RemoteUpdateResult.running()));


    private ObservableTransformer<MainActivityAction.OpenDoWorkoutActivity, MainActivityResult.OpenWorkoutTemplatesResult>
            OpenDoWorkoutActivity =
            actions -> actions.map(action -> MainActivityResult.OpenWorkoutTemplatesResult.success(true));
    /**
     * Splits the {@link Observable< MviAction >} to match each type of {@link MviAction} to
     * its corresponding business logic processor. Each processor takes a defined {@link MviAction},
     * returns a defined {@link MviResult}
     * The global actionProcessor then merges all {@link Observable<MviResult>} back to
     * one unique {@link Observable<MviResult>}.
     * <p>
     * The splitting is done using  which allows almost anything
     * on the passed {@link Observable} as long as one and only one {@link Observable} is returned.
     * <p>
     * An security layer is also added for unhandled {@link MviAction} to allow early crash
     * at runtime to easy the maintenance.
     */

    public ObservableTransformer<MainActivityAction, MainActivityResult> actionProcessor =
            actions -> actions.publish(shared -> Observable.merge(
                    shared.ofType(MainActivityAction.Initialization.class).compose(initialize),
                    shared.ofType(MainActivityAction.OpenDoWorkoutActivity.class).compose(OpenDoWorkoutActivity),
                    shared.ofType(MainActivityAction.UpdateFromServer.class).compose(RemoteUpdate)
            ));

}
