package com.myc.file.utils;

import com.myc.file.model.UploadInfo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.myc.file.utils.StreamUtil.deleteFolder;
import static com.myc.file.utils.StreamUtil.saveStreamToFile;

/**
 * 分片上传的工具类
 * @author myc
 * @version 1.0, 2017/12/9
 */
public class mergeFileUtils {

    /** 分片文件集合 **/
    private final static List<UploadInfo> uploadInfoList = new ArrayList<>();

    /**
     * 判断文件是否上传完成
     * @param md5
     * @param chunks
     * @return
     */
    private static boolean isAllUploaded(final String md5,
                                        final String chunks) {
        int size = uploadInfoList.stream()
                .filter(item -> item.getMd5().equals(md5))
                .distinct()
                .collect(Collectors.toList())
                .size();
        boolean bool = (size == Integer.parseInt(chunks));
        if (bool) {
            synchronized (uploadInfoList) {
                uploadInfoList.removeIf(item -> Objects.equals(item.getMd5(), md5));
            }
        }
        return bool;
    }

    /**
     * 文件分片上传
     * @param md5
     * @param guid
     * @param chunk
     * @param chunks
     * @param uploadFolderPath
     * @param fileName
     * @param ext
     * @return
     * @throws Exception
     */
    public static String  Uploaded( final String md5,
                                    final String guid,
                                    final String chunk,
                                    final String chunks,
                                    final String uploadFolderPath,
                                    final String fileName,
                                    final String ext)
            {
        synchronized (uploadInfoList) {
            uploadInfoList.add(new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext));
        }
        boolean allUploaded = isAllUploaded(md5, chunks);
        int chunksNumber = Integer.parseInt(chunks);
        String filePath = null;
        if (allUploaded) {
            try {
                filePath = mergeFile(chunksNumber, ext, guid, uploadFolderPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    /**
     * 分片文件合并
     * @param chunksNumber
     * @param ext
     * @param guid
     * @param uploadFolderPath
     * @throws Exception
     */
    private static String mergeFile(final int chunksNumber,
                                 final String ext,
                                 final String guid,
                                 final String uploadFolderPath)
            throws Exception {
        /*合并输入流*/
        String mergePath = uploadFolderPath + guid + "/";
        SequenceInputStream s ;
        InputStream s1 = new FileInputStream(mergePath + 0 + ext);
        InputStream s2 = new FileInputStream(mergePath + 1 + ext);
        s = new SequenceInputStream(s1, s2);
        for (int i = 2; i < chunksNumber; i++) {
            InputStream s3 = new FileInputStream(mergePath + i + ext);
            s = new SequenceInputStream(s, s3);
        }
        String filePath = uploadFolderPath + RandomUtils.getRandomTime() + ext;
        saveStreamToFile(s, filePath);
        deleteFolder(mergePath);
        return filePath;
    }
}
