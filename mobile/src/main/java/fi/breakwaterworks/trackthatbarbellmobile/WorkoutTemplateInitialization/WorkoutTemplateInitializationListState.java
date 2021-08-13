package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.mvibase.MviViewState;
import fi.breakwaterworks.utility.Status;

public interface WorkoutTemplateInitializationListState extends MviViewState {


    /**
     * A valid search result. Contains a list of items that have matched the searching criteria.
     */
    class Load implements WorkoutTemplateInitializationListState {

        @NonNull
        private final Status status;
        @Nullable
        private final Throwable error;
        private final List<Movement> result;
        public List<Movement> getResult() {
            return result;
        }

        public Load(Status status, List<Movement> result,Throwable error) {
            this.status=status;
            this.result = result;
            this.error=error;
        }

        @NonNull
        static Load success(@NonNull List<Movement> result) {
            return new Load(Status.SUCCESS, result, null);
        }


        @NonNull
        static Load failure(Throwable error) {
            return new Load(Status.FAILURE,null, error);
        }

        @NonNull
        static Load running() {
            return new Load(Status.RUNNING,null, null);
        }

        @NonNull
        public Status getStatus() {
            return status;
        }

        @Nullable
        public Throwable getError() {
            return error;
        }
    }


    /**
     * A valid search result. Contains a list of items that have matched the searching criteria.
     */
    class CreateTemplate implements WorkoutTemplateInitializationListState {

        @NonNull
        private final Status status;
        @Nullable
        private final Throwable error;
        private final WorkLog result;
        public WorkLog getResult() {
            return result;
        }

        public CreateTemplate(Status status, WorkLog result, Throwable error) {
            this.status=status;
            this.result = result;
            this.error=error;
        }

        @NonNull
        static CreateTemplate success(@NonNull WorkLog result) {
            return new CreateTemplate(Status.SUCCESS, result, null);
        }


        @NonNull
        static CreateTemplate failure(Throwable error) {
            return new CreateTemplate(Status.FAILURE,null, error);
        }

        @NonNull
        static CreateTemplate running() {
            return new CreateTemplate(Status.RUNNING,null, null);
        }

        @NonNull
        public Status getStatus() {
            return status;
        }

        @Nullable
        public Throwable getError() {
            return error;
        }
    }
}
