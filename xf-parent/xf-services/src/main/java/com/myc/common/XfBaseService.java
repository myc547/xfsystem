package com.myc.common;

import com.ardsec.framework.mybatis.id.IdGenerator;
import com.ardsec.framework.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 */
public abstract class XfBaseService extends BaseService {

    /** 主键生成器 **/
    @Autowired
    protected IdGenerator idGenerator;
}
