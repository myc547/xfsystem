package com.myc.media.dao.impl;

import com.ardsec.framework.dao.impl.BaseDaoImpl;
import com.ardsec.framework.mybatis.plgins.Paging;
import com.myc.media.dao.MediaDao;
import com.myc.media.model.Media;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 *
 * 视频管理service
 *
 */
@Repository
public class MediaDaoImpl extends BaseDaoImpl implements MediaDao {


    /**
     * 分页查询视频列表
     *
     * @param pageNumber 页面
     * @param pageSize   条数
     * @return
     */
    @Override
    public Paging<Media> getMediaListByPaging(int pageNumber, int pageSize, Map<String,String> map) {
        return super.selectPaging("selectMediaPaging", map, pageNumber, pageSize);
    }

    /**
     * 根据主键查询
     * @param mediaId 主键
     * @return 正确返回Media对象,否则返回null
     */
    @Override
    public Media findById(String mediaId) {
        return super.selectOne("findById", mediaId);
    }

    /**
     * 根据md5查询
     * @param md5
     * @return
     */
    @Override
    public int findByMd5(String md5) {
        return super.selectOne("findByMd5", md5);
    }

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    @Override
    public int deleteMedia(String id) {
        return super.delete("delById", id);
    }
}
