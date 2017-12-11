package com.myc.file;

import com.ardsec.framework.message.Response;
import com.ardsec.framework.utils.FileUtil;
import com.ardsec.framework.utils.StringUtil;
import com.myc.common.FileCommonController;
import com.myc.file.utils.RandomUtils;
import com.myc.file.utils.mergeFileUtils;
import com.myc.media.service.MediaService;
import com.myc.utils.AesUtil;
import com.myc.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author myc
 * @version 1.0, 2017/12/9
 */
@Slf4j
@Controller
@RequestMapping("${xf.web.mappings.file.uploader:fileUploader}")
public class FileUploadController extends FileCommonController {

    /** 获取文件路径 **/
    @Value("${system.file.base.path}")
    private String baseFilePath;

    /** 视频文件service **/
    @Autowired
    private MediaService mediaService;

    /**
     * 大文件上传
     * @param guid
     * @param md5value
     * @param chunks
     * @param chunk
     * @param name
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/bigFile")
    @ResponseBody
    public Response bigFile(String guid,
                            String md5value,
                            String chunks,
                            String chunk,
                            String name,
                            MultipartFile file) throws  Exception{
        String basePath  = FileUtil.systemFilePath(baseFilePath)+"/";
        String ext = name.substring(name.lastIndexOf("."));
        String videoPath = null;
        if (StringUtil.isNotEmpty(chunks) && StringUtil.isNotEmpty(chunk)) {
            String mergePath = basePath + guid + "/";
            int index = Integer.parseInt(chunk);
            String fileName = String.valueOf(index) + ext;
            saveFile(mergePath, fileName, file);
            videoPath = mergeFileUtils.Uploaded(md5value, guid, chunk, chunks, basePath, fileName, ext);
        } else {
            String fileName = RandomUtils.getRandomTime() + ext;
            saveFile(basePath, fileName, file);
            videoPath = basePath + fileName;
        }
        if (StringUtil.isNotEmpty(videoPath)) {
            log.info("filePath:{}", videoPath);
            videoPath = videoPath.replaceAll("\\\\", "/");
            mediaService.saveMediaFile(videoPath, name, md5value);
        }
        return new Response().success();
    }

    /**
     * 判断文件的md5是否存在
     * @param fileMd5
     * @return
     */
    @PostMapping("/isMd5Exist")
    @ResponseBody
    public Response md5Exsit(String fileMd5) {
        boolean flag = mediaService.findByMd5(fileMd5);
        return new Response().success(flag);
    }
}

