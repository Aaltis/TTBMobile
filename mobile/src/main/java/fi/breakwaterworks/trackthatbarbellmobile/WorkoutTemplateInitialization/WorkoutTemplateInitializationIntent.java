package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;



import java.util.List;

import androidx.annotation.Nullable;
import fi.breakwaterworks.model.Movement;


interface WorkoutTemplateInitializationIntent {
    class InitialIntent implements WorkoutTemplateInitializationIntent {
        private long worklogId;

        @Nullable
        public InitialIntent create(long worklogId) {
            this.worklogId = worklogId;
            return this;
        }

        public long getWorklogId() {
            return worklogId;
        }
    }

    class CreateProgram implements WorkoutTemplateInitializationIntent {
        private List<Movement> movements;
        private long worklogId;
        @Nullable
        public CreateProgram create(List<Movement> movements,Long worklogId) {
            this.movements = movements;
            this.worklogId = worklogId;
            return this;
        }

        public List<Movement> getMovements() {
            return movements;
        }

        public long getWorklogId() {
            return worklogId;
        }
    }

    class Continue implements WorkoutTemplateInitializationIntent {
        @Nullable
        public Continue create() {
            return this;
        }
    }
}
