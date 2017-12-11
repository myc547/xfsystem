package com.myc.file.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author myc
 * @version 1.0, 2017/12/9
 *
 * 文件的操作
 */
public class StreamUtil {

    /**
     * 输入流保存
     * @param inputStream
     * @param filePath
     * @throws Exception
     */
    public static void saveStreamToFile(final InputStream inputStream,
                                        final String filePath)
            throws Exception {
         /*创建输出流，写入数据，合并分块*/
        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    /**
     * 删除文件
     * @param folderPath
     * @return
     */
    public static boolean deleteFolder(final String folderPath) {
        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        if(files!=null){
            for (File file : files) {
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dir.delete();
    }
}
