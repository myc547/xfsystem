package com.myc.course;

import com.ardsec.framework.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author myc
 * @version 1.0, 2017/12/11
 *
 * 课程ontroller
 */
@Controller
@RequestMapping("${xf.web.mappings.course:course}")
public class CourseController extends BaseController {

    /**
     * 课程管理
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "course/list";
    }
}
