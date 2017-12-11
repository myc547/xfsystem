package com.myc.utils;

import com.myc.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author myc
 * @version 1.0, 2017/12/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RSAUtilTest extends BaseTest {

    private String publicKey = null;
    private String privateKey = null;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSAUtil.initKey();
        publicKey = RSAUtil.getPublicKey(keyMap);
        privateKey = RSAUtil.getPrivateKey(keyMap);

        log.info("公钥 -> " + publicKey);
        log.info("私钥 -> " + privateKey);
    }

    /**
     * 公钥加密，私钥解密
     * @throws Exception
     */
    @Test
    public void publicKeyEncryTest() throws Exception {
        String sourceString = "hi, RSA";

        byte[] encodedData = RSAUtil.encrypt(sourceString.getBytes(), publicKey, true);
        byte[] decodedData = RSAUtil.decrypt(encodedData, privateKey, false);

        String targetString = new String(decodedData);
        log.info("加密前: " + sourceString + "，解密后: " + targetString);
        assertEquals(sourceString, targetString);
    }

    /**
     * 私钥签名，公钥验证签名
     * @throws Exception
     */
    @Test
    public void privateKeyVerifyTest() throws Exception {
        String sourceString = "hello, RSA sign";
        byte[] data = sourceString.getBytes();

        // 产生签名
        String sign = RSAUtil.sign(data, privateKey);
        log.info("签名 -> " + sign);

        // 验证签名
        boolean status = RSAUtil.verify(data, publicKey, sign);
        log.info("状态 -> " + status);
        assertTrue(status);
    }

    /**
     * 私钥加密，公钥解密测试
     * @throws Exception
     */
    @Test
    public void privateKeyEncryTest() throws Exception {
        String sourceString = "hello, reRSA";
        byte[] data = sourceString.getBytes();

        byte[] encodedData = RSAUtil.encrypt(data, privateKey, false);
        byte[] decodedData = RSAUtil.decrypt(encodedData, publicKey, true);

        String targetString = new String(decodedData);
        log.info("加密前: " + sourceString + "，解密后: " + targetString);
        assertEquals(sourceString, targetString);
    }

}
