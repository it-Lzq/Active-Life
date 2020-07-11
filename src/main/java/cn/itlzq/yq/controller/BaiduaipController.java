package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.ResultMessage;
import cn.itlzq.yq.model.bj.User;
import cn.itlzq.yq.model.bj.face_ai.FaceData_search;
import cn.itlzq.yq.model.bj.face_ai.Result;
import cn.itlzq.yq.model.bj.face_ai.User_list;
import cn.itlzq.yq.service.UserService;
import cn.itlzq.yq.util.BaiduAiUtils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/30 17:19
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
public class BaiduaipController {

    @Resource
    private UserService service;



    @ResponseBody
    @RequestMapping("/searchFace")
    public ResultMessage searchFace(String img, HttpSession session) {
        System.out.println(img);
        String s = BaiduAiUtils.searchFace(img, "note", null);
        System.out.println(s);
        FaceData_search search = JSONObject.parseObject(s, FaceData_search.class);
        if(search.getError_code() == 0){
            User_list user_list = search.getResult().getUser_list().get(0);
            if(user_list.getScore() < 80){
                return new ResultMessage(400,"请对准屏幕",null);
            }
            String userId = user_list.getUser_id();
            User one = service.getOne(Long.parseLong(userId));
            System.out.println(one);
            if(one != null){
                session.setAttribute("user",one);
                return new ResultMessage(200,"登录成功",null);
            }else{
                return new ResultMessage(400,"登陆失败，请重试",null);
            }
        }else{
            return new ResultMessage(400,"请对准屏幕",null);
        }


    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

}
