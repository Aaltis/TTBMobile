package fi.breakwaterworks.model;

public enum ExerciseType {
    STRAIGHT_SET("Straight set",false),
    DROP_SET("Drop set",true),
    AMRAP("Amrap",false),
    MTOR("MTOR",true),
    MYO("Myo",true);

    private String typeName;
    private boolean multiset;

    ExerciseType(String name,boolean multiset) {
        typeName = name;
        this.multiset=multiset;
    }

    @Override
    public String toString() {
        return typeName;
    }

    public boolean isMultiset(){
        return multiset;
    }
}
