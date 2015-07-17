package me.danielpan.youtubelike.util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by it-od-m-2572 on 15/6/3.
 */
public final class ColorGenerator {
    public static final int THROSHOLD = 256;

    public final static int getRandomColor() {
        final Random random = new Random();
        int r = random.nextInt(THROSHOLD);
        int g = random.nextInt(THROSHOLD);
        int b = random.nextInt(THROSHOLD);
        return Color.rgb(r, g, b);
    }
}
