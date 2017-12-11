package com.myc.media.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ardsec.framework.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 *
 *
 * 视频实体
 */
@Data
public class Media extends BaseEntity {

    //0:新上传,1:审核通过,2:审核不通过,3:已使用
    public final static int  STATUS_NEW = 0;

    public final static int  STATUS_AUDITED = 1;

    public final static int  STATUS_UNAUDITED = 2;

    public final static int  STATUS_USED = 3;

    /** 文件名称 **/
    @JSONField(name = "file_name")
    private String fileName;

    /** 文件路径 **/
    private String mediaPath;

    /** 文件大小 **/
    @JSONField(name = "media_size")
    private long mediaSize;

    /** 文件状态 **/
    @JSONField(name = "media_status")
    private int mediaStatus;

    /** 上传者 **/
    private String uploaderId;

    /** 审核人Id **/
    private String auditorId;

    /** 审核时间 **/
    private Date auditTime;

    /** 文件MD5 **/
    private String fileMd5;


}
