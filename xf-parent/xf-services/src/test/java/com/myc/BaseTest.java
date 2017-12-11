package com.myc;

import com.ardsec.framework.mybatis.id.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 */
public  abstract class BaseTest {

    @Autowired
    protected IdGenerator idGenerator;
}
