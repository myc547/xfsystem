package com.myc.common;

import com.ardsec.framework.controller.BaseController;
import com.ardsec.framework.utils.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author myc
 * @version 1.0, 2017/12/9
 */
public class FileCommonController extends BaseController {

    /**
     * 保存文件
     * @param savePath
     * @param fileFullName
     * @param file
     * @return
     */
    public static boolean saveFile(final String savePath,final String fileFullName,
                                   final MultipartFile file) {
        FileUtil.createDir(savePath);
        File uploadFile = new File(savePath + fileFullName);
        try (FileOutputStream outStream = new FileOutputStream(uploadFile)) {//写入数据
            byte[] data = readInputStream(file.getInputStream());
            outStream.write(data);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadFile.exists();
    }

    /**
     * 读取输入流
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


}
