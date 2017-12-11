package com.myc.utils;

import com.myc.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author myc
 * @version 1.0, 2017/12/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SimpleEncodeUtilTest extends BaseTest {

    @Test
    public void encodeTest() {
        String source = "/usr/local/2121.mm";
        String encrySource = SimpleEnCodeUtil.encode(source);
        log.info("加密后的字符串,{}",encrySource);
    }
}
