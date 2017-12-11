package com.myc.file.utils;

import java.util.Random;

/**
 * @author myc
 * @version 1.0, 2017/12/9
 */
public class RandomUtils {

    public static String getRandomTime(){
        return System.currentTimeMillis() + new Random(20).nextInt(10) + "";
    }
}
