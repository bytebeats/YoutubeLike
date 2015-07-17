package me.danielpan.youtubelike.util;

/**
 * Created by it-od-m-2572 on 15/6/9.
 */
public final class FloatComparator {
    public static final double THRESHOLD = 0.00001D;
    public static final int GREATER = 1;
    public static final int EQUAL = 0;
    public static final int SMALLER = -1;

    public static final int compare(double arg1, double arg2) {
        double result = arg1 - arg2;
        if (result > THRESHOLD) {
            return GREATER;
        } else if (result < -THRESHOLD) {
            return SMALLER;
        } else {
            return EQUAL;
        }
    }
}
