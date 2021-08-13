package fi.breakwaterworks.UseCases;

import android.content.Context;

import java.util.List;

import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.trackthatbarbellmobile.TTBDatabase;
import fi.breakwaterworks.trackthatbarbellmobile.common.BaseObservable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchMovementUseCase extends BaseObservable<SearchMovementUseCase.Listener> {

    TTBDatabase database;

    public interface Listener {
        void onMovementsLoaded(List<Movement> movements);
    }

    public SearchMovementUseCase(Context context) {
        this.database = TTBDatabase.getInstance(context);
    }

    public void getMovementsWithNameLike(String movementName) {
        database.movementDAO().getMovementsWithNameLike(movementName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Movement>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Movement> movements) {
                        for (Listener listener : getListeners()) {
                            listener.onMovementsLoaded(movements);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
