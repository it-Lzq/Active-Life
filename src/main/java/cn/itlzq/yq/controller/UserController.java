package cn.itlzq.yq.controller;


import cn.itlzq.yq.model.ResultMessage;
import cn.itlzq.yq.model.bj.User;
import cn.itlzq.yq.service.UserService;
import cn.itlzq.yq.util.SendSms;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:14
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 跳转登录页面
     * @return 登录页面
     */
    @GetMapping("/bj")
    public String toLogin(){
      return "login";
    }

    /**
     * 登录验证
     * @param phone 用户名
     * @param password 密码
     * @return 登录页 或者控制台主页
     */
    @PostMapping("/bj/login")
    public String login(@RequestParam String phone,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        System.out.println(phone+password);
        User user = userService.checkUser(phone, password);
        System.out.println(user);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:../bj/notes";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:../bj";
        }
    }
    @GetMapping("/bj/index")
    public String index(HttpSession session){
        if(session.getAttribute("user") == null){
            return "login";
        }else{
            return "redirect:/bj/notes";
        }

    }

    @GetMapping("/bj/exit")
    public String exit(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @RequestMapping("/toReg")
    public String toReg(){
        return "reg";
    }
    @ResponseBody
    @RequestMapping("/reg")
    public ResultMessage reg(User user){
        System.out.println(user);
        if(user.getPhone() == null && user.getPhone().equals("")){
            return new ResultMessage(400,"注册失败，请重试",null);
        }
        User u = userService.saveUser(user);
        if(u != null){
            return new ResultMessage(200,"注册成功",null);
        }
        return new ResultMessage(400,"注册失败，请重试",null);
    }

    @ResponseBody
    @RequestMapping("/sms")
    public JSONObject sendSms(String phone){
        System.out.println(phone);
        int code = SendSms.random();
        //boolean flag = true;
        boolean flag = SendSms.send(phone, code);
        JSONObject json = new JSONObject();
        if(flag){
            json.put("status",200);
            json.put("msg","验证码发送成功");
            json.put("realCode",code);
            json.put("realPhone",phone);
        }else{
            json.put("status",-1);
            json.put("msg","验证码发送失败，请检查您的手机号");
        }
        return json;
    }
}
