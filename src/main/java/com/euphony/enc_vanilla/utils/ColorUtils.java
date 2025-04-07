package com.euphony.enc_vanilla.utils;

public class ColorUtils {
    public static int getPingTextColor(int latency) {
        if(latency < 0) {
            return 6953882;
        } else if (latency < 150) {
            return 5025616;
        } else if (latency < 300) {
            return 16771899;
        } else if(latency < 600) {
            return 16088064;
        } else {
            return 12000284;
        }
    }
}
