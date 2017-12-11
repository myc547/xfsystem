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
public class AesUtilTest extends BaseTest {

    @Test
    public void aesEncryptTest() {
        String source = "/usr/local/21211.ccc";
        String key = "DD334ddwRR45*&KM";
        String encrypt = AesUtil.aesEncrypt(source, key);
        String decrypt = AesUtil.aesDecrypt(encrypt, key);
        log.info("加密后的->{}, 解密后的->{}", encrypt, decrypt);
    }

    @Test
    public void aesDencryptTest() {
        String encrypt = "vRCRjSG55shVjX3PBtPxZ+fXKmWSOm+pVA2VSLqvSZyQ4bRVzOxP2gmyfsTjL2aZ\n";
        String key = "DD334ddwRR45*&KM";
        String decrypt = AesUtil.aesDecrypt(encrypt, key);
        log.info("解密后的代码：{}", decrypt);
    }
}
