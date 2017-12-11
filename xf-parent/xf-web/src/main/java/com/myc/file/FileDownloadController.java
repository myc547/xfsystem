package com.myc.file;

import com.ardsec.framework.controller.BaseController;
import com.ardsec.framework.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author myc
 * @version 1.0, 2017/12/10
 */
@Slf4j
@Controller
@RequestMapping("${xf.web.mappings.file.uploader:fileDownload}")
public class FileDownloadController extends BaseController {


    /**
     * 写文件
     * @param request
     * @param response
     */
    @GetMapping("/videoPlay")
    public void videoDownload(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileId = request.getParameter("fileId");
            if (StringUtil.isEmpty(fileId)) {
                return;
            }
            File file = new File(fileId);
            downRangeFile(file, response , request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 视频文件下载
     * @param downloadFile
     * @param response
     * @param request
     * @throws Exception
     */
    public void downRangeFile(File downloadFile, HttpServletResponse response, HttpServletRequest request) throws Exception {

        if (!downloadFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        long fileLength = downloadFile.length();// 记录文件大小
        long pastLength = 0;// 记录已下载文件大小
        int rangeSwitch = 0;// 0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）
        long toLength = 0;// 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
        long contentLength = 0;// 客户端请求的字节总量
        String rangeBytes = "";// 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
        RandomAccessFile raf = null;// 负责读取数据
        OutputStream os = null;// 写出数据
        OutputStream out = null;// 缓冲
        int bsize = 1024;// 缓冲区大小
        byte b[] = new byte[bsize];// 暂存容器
        String range = request.getHeader("Range");
        int responseStatus = 206;
        if (range != null && range.trim().length() > 0 && !"null".equals(range)) {// 客户端请求的下载的文件块的开始字节
            responseStatus = HttpServletResponse.SC_PARTIAL_CONTENT;
            rangeBytes = range.replaceAll("bytes=", "");
            if (rangeBytes.endsWith("-")) {
                rangeSwitch = 1;
                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                pastLength = Long.parseLong(rangeBytes.trim());
                contentLength = fileLength - pastLength;// 客户端请求的是
            } else {
                rangeSwitch = 2;
                String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                String temp2 = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
                pastLength = Long.parseLong(temp0.trim());
                toLength = Long.parseLong(temp2);// bytes=1275856879-1275877358，到第
                contentLength = toLength - pastLength + 1;// 客户端请求的是
            }
        } else {
            contentLength = fileLength;
        }

        /**
         * 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。 响应的格式是:
         * Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
         * ServletActionContext.getResponse().setHeader("Content-Length", new
         * Long(file.length() - p).toString());
         */
        response.reset();
        response.setHeader("Accept-Ranges", "bytes");
        if (rangeSwitch != 0) {
            response.setStatus(responseStatus);
            switch (rangeSwitch) {
                case 1: {
                    String contentRange = new StringBuffer("bytes ")
                            .append(new Long(pastLength).toString()).append("-")
                            .append(new Long(fileLength - 1).toString())
                            .append("/").append(new Long(fileLength).toString())
                            .toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                case 2: {// 针对 bytes=27000-39000 的请求
                    String contentRange = range.replace("=", " ") + "/"
                            + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
            }
        } else {
            String contentRange = new StringBuffer("bytes ").append("0-")
                    .append(fileLength - 1).append("/").append(fileLength)
                    .toString();
            response.setHeader("Content-Range", contentRange);
        }

        try {
            String contentType = null;
            if (contentType != null) {
                response.setContentType(contentType);
            } else {
                response.setContentType("audio/mpeg");
            }
            response.setHeader("Content-Length", String.valueOf(contentLength));
            os = response.getOutputStream();
            out = new BufferedOutputStream(os);
            raf = new RandomAccessFile(downloadFile, "r");
            try {
                long outLength = 0;
                switch (rangeSwitch) {
                    case 1: {
                        raf.seek(pastLength);// 形如 bytes=969998336- 的客户端请求，跳过
                        int n = 0;
                        while ((n = raf.read(b)) != -1) {
                            out.write(b, 0, n);
                            outLength += n;
                        }
                        break;
                    }
                    case 2: {
                        raf.seek(pastLength);
                        int n = 0;
                        long readLength = 0;// 记录已读字节数
                        while (readLength <= contentLength - bsize) {// 大部分字节在这里读取
                            n = raf.read(b);
                            readLength += n;
                            out.write(b, 0, n);
                            outLength += n;
                        }
                        if (readLength <= contentLength) {// 余下的不足 1024 个字节在这里读取
                            n = raf.read(b, 0, (int) (contentLength - readLength));
                            out.write(b, 0, n);
                            outLength += n;
                        }
                        break;
                    }
                }
                log.info("Content-Length为：" + contentLength + "；实际输出字节数：" + outLength);
                out.flush();
            } catch (IOException ie) {
                /**
                 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
                 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。
                 * 尤其是对于迅雷这种吸血的客户端软件， 明明已经有一个线程在读取 bytes=1275856879-1275877358，
                 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
                 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
                 * 所以，我们忽略这种异常
                 */
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

    }
}
