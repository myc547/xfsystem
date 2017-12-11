package com.myc.media;

import com.ardsec.framework.paging.PagingResult;
import com.myc.BaseTest;
import com.myc.media.model.Media;
import com.myc.media.service.MediaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaServiceTest extends BaseTest {

    /** MediaService **/
    @Autowired
    private MediaService mediaService;

    /**
     * 测试保存
     */
    @Test
    public void saveTest(){
        Media media = new Media();
        media.setId(idGenerator.generateId());
        media.setMediaPath("/usr/local/attachements/vedio/" + System.currentTimeMillis() +".mp4");
        media.setFileName("中文视频测试");
        media.setMediaSize(12L);
        media.setMediaStatus(0);
        media.setUploaderId(System.currentTimeMillis()+ "");
        media.setCreator(System.currentTimeMillis()+ "");
        int result = mediaService.saveMedia(media);
        Assert.assertTrue(result > 0);
    }

    /**
     * 根据之间查询测试
     */
    @Test
    public void findByIdTest() {
        Media media = mediaService.findById(1512630846774L +"");
        Assert.assertTrue(media != null);
    }

    /**
     * 更新测试
     */
    @Test
    public void updateTest() {
        Media media = mediaService.findById(1512630846774L +"");
        media.setEditor(System.currentTimeMillis() + "");
        media.setAuditorId(System.currentTimeMillis() + "");
        media.setAuditTime(new Date());
        mediaService.updateMedia(media);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void  pagingTest(){
        PagingResult result = mediaService.getAllList(1, 10, new HashMap<>());
        Assert.assertTrue(result != null);
        Assert.assertEquals(2, result.getTotal());
    }
}
