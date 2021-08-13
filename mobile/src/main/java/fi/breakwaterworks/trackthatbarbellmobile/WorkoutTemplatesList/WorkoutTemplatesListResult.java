package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import fi.breakwaterworks.model.WorkLog;
import fi.breakwaterworks.utility.Status;

public interface WorkoutTemplatesListResult {

    class Load implements WorkoutTemplatesListResult {
        @NonNull
        private Status status;

        @Nullable
        private Throwable error;
        private List<WorkLog> result;

        public Load(Status status, List<WorkLog> result, Throwable error) {
            this.status = status;
            this.result = result;
            this.error = error;
        }

        @NonNull
        static Load success(@NonNull List<WorkLog> result) {
            return new Load(Status.SUCCESS, result, null);
        }


        @NonNull
        static Load failure(Throwable error) {
            return new Load(Status.FAILURE, null, error);
        }

        @NonNull
        static Load running() {
            return new Load(Status.RUNNING, null, null);
        }

        @NonNull
        public Status getStatus() {
            return status;
        }

        @Nullable
        public Throwable getError() {
            return error;
        }

        public void setError(Throwable error) {
            this.error = error;
        }

        public List<WorkLog> getResult() {
            return result;
        }

        public void setResult(List<WorkLog> result) {
            this.result = result;
        }
    }
}
