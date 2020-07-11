package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.ResultMessage;
import cn.itlzq.yq.model.bj.face_ai.FaceData;
import cn.itlzq.yq.model.bj.User;
import cn.itlzq.yq.service.UserService;
import cn.itlzq.yq.util.BaiduAiUtils;
import cn.itlzq.yq.util.MD5;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/1 3:07
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
@SuppressWarnings("all")
public class UpdateUserController {
    @Resource
    private UserService service;

    @RequestMapping("/bj/grzx")
    public String grzx(){
        return "study/grzx";
    }

    @ResponseBody
    @RequestMapping("/bj/updateNickname")
    public ResultMessage nickname(String nickname,Long id, HttpSession session){
        System.out.println(nickname+id);
        User user = service.getOne(id);
        System.out.println(user);
        user.setNickname(nickname);
        User u = service.updateUser(user);
        session.setAttribute("user",u);
        return new ResultMessage(200,"修改成功",u);
    }

    @ResponseBody
    @RequestMapping("/bj/updatePassword")
    public ResultMessage password(String oldPass,String newPass,Long id, HttpSession session){
        System.out.println(newPass+id);
        User u = service.getOne(id);
        if(!MD5.getPassword(oldPass).equals(u.getPassword())){
            return  new ResultMessage(400,"密码输入错误",null);
        }
        System.out.println(u);
        u.setPassword(MD5.getPassword(newPass));
        User user = service.updateUser(u);
        session.setAttribute("user",user);
        return new ResultMessage(200,"修改成功",user);
    }

    @ResponseBody
    @RequestMapping("/bj/updateFace")
    public ResultMessage updateFace(String img,Long id, HttpSession session){
        String msg = "";
        User u = service.getOne(id);
        String note = BaiduAiUtils.addUser(img, id.toString(), "note");
        System.out.println(note);
        FaceData faceData = JSONObject.parseObject(note, FaceData.class);
        if(faceData.getError_code() == 0){
            msg = "人脸登录开启成功";
            u.setFace(true);
            User user = service.updateUser(u);
            session.setAttribute("user",user);
            return new ResultMessage(200,msg,null);
        }else{
            msg = "图片不合法";
            return new ResultMessage(400,msg,null);
        }

       /* FaceData detectRS = JSONObject.parseObject(BaiduAiUtils.detectFace(img, "1"), FaceData.class);
        if(detectRS.getError_code() != 0){
            return  new ResultMessage(400,"没有检测到人脸",null);
        }
        //人脸搜索
        String s = BaiduAiUtils.searchFace(img, "note", null);
        System.out.println(s);
        //解析搜索数据
        FaceData faceData = JSONObject.parseObject(s, FaceData.class);


        User user = null;
        if(faceData.getError_code() == 0){
            User_list user_list = faceData.getUser_list().get(0);
            if(user_list.getScore() > 75){
                //检测成功 修改
                String note = BaiduAiUtils.addUser(img, id.toString(), "note");
                System.out.println(note);
                msg = "修改成功";
            }
        }else{
            //进行注册
            u.setFace(true);
            user = service.updateUser(u);
            session.setAttribute("user",user);
            BaiduAiUtils.addUser(img,id.toString(),"note");
            msg= "人脸登录已开启";
        }*/

    }
}
