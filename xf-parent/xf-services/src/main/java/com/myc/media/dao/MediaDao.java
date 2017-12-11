package com.myc.media.dao;

import com.ardsec.framework.dao.BaseDao;
import com.ardsec.framework.mybatis.plgins.Paging;
import com.myc.media.model.Media;

import java.util.Map;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 */
public interface MediaDao  extends BaseDao{

    /**
     * 分页查询视频列表
     * @param pageNumber 页面
     * @param pageSize 条数
     * @param map 查询参数
     * @return
     */
    Paging<Media> getMediaListByPaging(int pageNumber, int pageSize, Map<String, String> map);

    /**
     * 根据主键查询
     * @param mediaId 主键
     * @return
     */
    Media findById(String mediaId);

    /**
     * 根据md5查询
     * @param md5
     * @return 数量
     */
    int findByMd5(String md5);

    /**
     * 删除文件
     * @param id
     * @return
     */
    int deleteMedia(String id);
}
