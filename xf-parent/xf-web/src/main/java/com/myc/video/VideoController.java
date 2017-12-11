package com.myc.video;

import com.ardsec.framework.controller.BaseController;
import com.ardsec.framework.message.Response;
import com.ardsec.framework.param.PagingParam;
import com.ardsec.framework.utils.StringUtil;
import com.myc.common.MessageConstant;
import com.myc.media.model.Media;
import com.myc.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author myc
 * @version 1.0, 2017/12/8
 */
@Controller
@RequestMapping("${xf.web.mappings.video:video}")
public class VideoController extends BaseController {

    /** 视频service **/
    @Autowired
    private MediaService mediaService;

    /**
     * 视频列表
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "video/list";
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        return "video/add";
    }

    /**
     * 审核
     * @return
     */
    @RequestMapping("/edit")
    public String edit(String param, Model model) {
        Media media = mediaService.findById(param);
        if (media != null) {
            if (media.getMediaStatus() == Media.STATUS_AUDITED) {
                throw new RuntimeException("已经审核");
            }
            addAttributes(model, media);
            model.addAttribute("opera", "edit");
        }
        return "video/edit";
    }

    /**
     * 查看
     * @param param
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String view(String param, Model model) {
        Media media = mediaService.findById(param);
        if (media != null) {
            addAttributes(model, media);
            model.addAttribute("opera", "view");
        }
        return "video/edit";
    }



    /**
     * 审核
     * @param params
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Response update(@RequestBody Map<String,String> params) {
        String status = params.get("status");
        String mediaId = params.get("mediaId");
        Response res = new Response();
        if (StringUtil.isEmpty(status) || StringUtil.isEmpty(mediaId)) {
            return res.failure();
        }
        int mediaStatus = Integer.parseInt(status);
        Media media = new Media();
        media.setId(mediaId);
        media.setAuditTime(new Date());
        media.setMediaStatus(mediaStatus);
        int result = mediaService.updateMedia(media);
        if (result > 0) {
            return res.success();
        }
        return res.failure();
    }

    /**
     * 获取视频list
     * @param pagingParam
     * @return
     */
    @RequestMapping("/listData")
    @ResponseBody
    public Response listData(PagingParam pagingParam) {
        int pageNumber = pagingParam.getPagingNumber();
        int pageSize = pagingParam.getPageSize();
        String mediaName = pagingParam.getSearch();
        Map<String, String> map = new HashMap<>();
        map.put("mediaName", mediaName);
        map.put("order", pagingParam.getOrderBy());
        return new Response().success(mediaService.getAllList(pageNumber, pageSize, map));
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/del")
    @ResponseBody
    public Response deleteMedia(@RequestBody Map<String,String> params){
        String mediaId = params.get("mediaId");
        Response response = new Response();
        if (StringUtil.isEmpty(mediaId)) {
            return response.failure();
        }
        int result = mediaService.deleteMedia(mediaId);
        if (result == -1) {
            return response.failure(MessageConstant.MEDIA_STATUS_UNDELETED);
        }
        return response.success();

    }

    /**
     * 状态检测
     * @param params
     * @return
     */
    @PostMapping("/checkStatus")
    @ResponseBody
    public Response checkStatus(@RequestBody Map<String,String> params){
        String mediaId = params.get("mediaId");
        Response response = new Response();
        if (StringUtil.isEmpty(mediaId)) {
            return response.failure();
        }
        Media media = mediaService.findById(mediaId);
        if (media != null) {
            return response.success(media.getMediaStatus());
        }
        return response.failure();

    }

    /**
     * 添加属性
     * @param model
     * @param media
     */
    private void addAttributes(Model model, Media media) {
        model.addAttribute("status", media.getMediaStatus());
        model.addAttribute("mediaId", media.getId());
        model.addAttribute("fileId", media.getMediaPath());
    }
}
