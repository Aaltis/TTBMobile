package fi.breakwaterworks.utility;

public class OneRepCalculator {

    private static long calculateRepMaxFromOneRepMax(int reps, long weight) {
        switch (reps) {
            case 1:
                return weight;
            case 2:
                return (weight / 95);
            case 3:
                return (weight / 93);
            case 4:
                return  (weight / 90);
            case 5:
                return  (weight / 87);
            case 6:
                return  (weight / 85);
            case 7:
                return  (weight / 83);
            case 8:
                return  (weight / 80);
            case 9:
                return  (weight / 77);
            case 10:
                return  (weight / 75);
            case 11:
                return  (weight / 73);
            case 12:
                return  (weight / 70);
        }
        return 0;
    }

    public static long calculateOneRepMax(int reps, long weight) {
        switch (reps) {
            case 1:
                return weight;
            case 2:
                return (long) (weight * 1.05);
            case 3:
                return (long) (weight * 1.07);
            case 4:
                return (long) (weight * 1.1);
            case 5:
                return (long) (weight * 1.13);
            case 6:
                return (long) (weight * 1.15);
            case 7:
                return (long) (weight * 1.17);
            case 8:
                return (long) (weight * 1.2);
            case 9:
                return (long) (weight * 1.23);
            case 10:
                return (long) (weight * 1.25);
            case 11:
                return (long) (weight * 1.27);
            case 12:
                return (long) (weight * 1.3);
        }
        return 0;
    }

    public static long calculateRepMax(int userDoneReps, long userDoneWeight, int wantedReps){
        return calculateRepMaxFromOneRepMax(wantedReps ,calculateOneRepMax(userDoneReps,userDoneWeight));
    }
}
