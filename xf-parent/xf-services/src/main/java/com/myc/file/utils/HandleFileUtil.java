package com.myc.file.utils;

import com.ardsec.framework.utils.StringUtil;

import java.io.File;

/**
 * @author myc
 * @version 1.0, 2017/12/10
 */
public class HandleFileUtil {

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public static boolean delFile(String fileName){
        if (StringUtil.isEmpty(fileName)) {
            return false;
        }
        File file = new File(fileName);
        if (file != null && file.exists()) {
            return file.delete();
        }
        return true;
    }
}
