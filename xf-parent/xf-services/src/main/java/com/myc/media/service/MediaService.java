package com.myc.media.service;

import com.ardsec.framework.mybatis.plgins.Paging;
import com.ardsec.framework.paging.PagingResult;
import com.ardsec.framework.utils.StringUtil;
import com.myc.common.XfBaseService;
import com.myc.file.utils.HandleFileUtil;
import com.myc.media.dao.MediaDao;
import com.myc.media.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 */
@Service
public class MediaService extends XfBaseService {

    /** 视频 **/
    @Autowired
    private MediaDao mediaDao;


    /**
     * 文件保存
     * @param path
     * @param fileName
     * @param md5
     * @return
     */
    public int saveMediaFile(String path, String fileName, String md5){
        File file = new File(path);
        if (file.exists()) {
            Media media = new Media();
            media.setId(idGenerator.generateId());
            media.setFileName(fileName);
            media.setFileMd5(md5);
            media.setMediaStatus(Media.STATUS_NEW);
            media.setMediaPath(path);
            media.setMediaSize(file.length());
            return saveMedia(media);
        }
        return 0;
    }

    /**
     * 获取分页数据
     * @param pageNumber 页面
     * @param pageSize 条数
     * @return
     */
    public PagingResult<Media> getAllList(int pageNumber, int pageSize, Map<String,String> map){
        Paging<Media> paging = mediaDao.getMediaListByPaging(pageNumber, pageSize, map);
        return new PagingResult<>(paging);
    }

    /**
     * 根据主键查询
     * @param mediaId
     * @return
     */
    public Media findById(String mediaId) {
        if (StringUtil.isEmpty(mediaId)) {
            return null;
        }
        return mediaDao.findById(mediaId);
    }

    /**
     * 保存视频
     * @param media com.myc.media.model.Media
     * @return 成功返回 1
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveMedia(Media media) {
        return mediaDao.insert(media);
    }

    /**
     * 更新视频信息
     * @param media com.myc.media.model.Media
     * @return 成功：大于等于0
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateMedia(Media media) {
        return mediaDao.update(media);
    }

    /**
     * 删除视频信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteMedia(String id) {
        if (StringUtil.isEmpty(id)) {
            return 0;
        }
        Media media = mediaDao.findById(id);

        if (media != null) {

            if (media.getMediaStatus() == Media.STATUS_AUDITED
                    || media.getMediaStatus() == Media.STATUS_USED){
                return -1;
            }

            if (StringUtil.isNotEmpty(media.getMediaPath())) {
                HandleFileUtil.delFile(media.getMediaPath());
            }
        }
        return mediaDao.deleteMedia(id);

    }

    /**
     * 根据MD5查询
     * @param md5
     * @return
     */
    public boolean findByMd5(String md5) {
        if (StringUtil.isEmpty(md5)) {
            return false;
        }
        int counter =  mediaDao.findByMd5(md5);
        if (counter > 0) {
            return true;
        }
        return false;
    }
}
