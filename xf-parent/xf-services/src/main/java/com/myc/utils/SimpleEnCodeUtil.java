package com.myc.utils;

import java.nio.charset.Charset;

/**
 * @author myc
 * @version 1.0, 2017/12/10
 */
public class SimpleEnCodeUtil {

    private static final String key0 = "FEWSCR32423**<MNCXZPKL";
    private static final Charset charset = Charset.forName("UTF-8");
    private static byte[] keyBytes = key0.getBytes(charset);

    /**
     * 加密
     * @param enc
     * @return
     */
    public static String encode(String enc){
        byte[] b = enc.getBytes(charset);
        for(int i=0,size=b.length;i<size;i++){
            for(byte keyBytes0:keyBytes){
                b[i] = (byte) (b[i]^keyBytes0);
            }
        }
        return new String(b);
    }

    /**
     * 解密
     * @param dec
     * @return
     */
    public static String decode(String dec){
        byte[] e = dec.getBytes(charset);
        byte[] dee = e;
        for(int i=0,size=e.length;i<size;i++){
            for(byte keyBytes0:keyBytes){
                e[i] = (byte) (dee[i]^keyBytes0);
            }
        }
        return new String(e);
    }

}
