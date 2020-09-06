package pl.polishstation.polishstationbackend.utils;

public class Indexator {
    public static int getStartIndexOfPath(int v) {
        return Math.max(getEndIndexOfPart(v - 1), 0);
    }

    public static int getEndIndexOfPart(int v) {
        return v * 5;
    }
}
