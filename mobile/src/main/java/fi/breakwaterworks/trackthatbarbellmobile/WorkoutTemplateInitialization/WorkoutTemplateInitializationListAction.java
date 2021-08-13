package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplateInitialization;

import java.util.List;

import androidx.annotation.Nullable;
import fi.breakwaterworks.model.Movement;
import fi.breakwaterworks.mvibase.MviAction;

interface WorkoutTemplateInitializationListAction extends MviAction {

    class InitialTask implements WorkoutTemplateInitializationListAction {
        private long templateId;

        @Nullable
        public InitialTask create(long worklogId) {
            this.templateId = worklogId;
            return this;
        }

        public long getTemplateId() {
            return templateId;
        }

    }

    class CreateTemplate implements WorkoutTemplateInitializationListAction {
        private long worklogTemplateId;
        private List<Movement> movements;

        @Nullable
        public CreateTemplate create(List<Movement> movements, long worklogTemplateId) {
            this.worklogTemplateId = worklogTemplateId;
            this.movements = movements;
            return this;
        }

        public List<Movement> getMovements() {
            return movements;
        }

        public long getWorklogTemplateId() {
            return worklogTemplateId;
        }
    }

}
