package cn.itlzq.yq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/29 19:38
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
public class ReportController {


    @RequestMapping("/study")
    public String tostudy(){
        return "life/tostudy";
    }




}
