package fi.breakwaterworks.trackthatbarbellmobile.WorkoutTemplatesList;

interface WorkoutTemplatesListAction {
    class InitialTask implements WorkoutTemplatesListAction {
        public InitialTask create() {
            return this;
        }
    }
}
