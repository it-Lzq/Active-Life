package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.Menu;
import cn.itlzq.yq.model.data.BaseData;
import cn.itlzq.yq.model.data.MenuData;
import cn.itlzq.yq.util.DataUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/27 10:59
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@SuppressWarnings("all")
@Controller
public class LifeController {
    private static final String LOVE_WORDS = "http://api.tianapi.com/txapi/saylove/index?key=f2955898741fd3ada950dbc138a6405b";
    private static final String LIFE_COMMON = "http://api.tianapi.com/txapi/qiaomen/index?key=f2955898741fd3ada950dbc138a6405b";
    private static final String MENU_PLAY = "http://api.tianapi.com/txapi/caipu/index?key=f2955898741fd3ada950dbc138a6405b";

    /**
     *  跳转到食谱菜单页面
     */
    @RequestMapping("/life")
    public String toLife(Model model,int page){
        String name = randomName();
        String url = MENU_PLAY + "&word="+ name +"&num=" + 5 + "&page="+page;
        String loveWords = getData(LOVE_WORDS);
        String lifeCommon = getData(LIFE_COMMON);
        List<Menu> menuList = getMenuData(url);
        System.out.println(menuList);
        model.addAttribute("loveWords",loveWords);
        model.addAttribute("lifeCommon",lifeCommon);
        model.addAttribute("menuList", menuList);
        model.addAttribute("page",page);
        return "life/life";
    }

    /**
     *  获取生活小常识
     */
    @ResponseBody
    @RequestMapping("/getWords")
    public String getWords(HttpServletResponse response){
        String loveWords = getData(LOVE_WORDS);
       return loveWords;
    }

    /**
     *  搜索菜单页面
     */
    @RequestMapping("/menu")
    public String toMenu(String name,String page,Model model){
        System.out.println(name);
        if(name==null || name.equals("")){
            name = randomName();
        }
        String url = MENU_PLAY + "&word="+ name +"&num=" + 8 + "&page="+page;
        List<Menu> menuList = getMenuData(url);
        model.addAttribute("menuList", menuList);
        model.addAttribute("word",name);
        model.addAttribute("page",page);
        return "life/menu";
    }

    /**
     * 获取菜单数据
     * @param httpUrl
     * @return
     */
    private List<Menu> getMenuData(String httpUrl){
        System.out.println(httpUrl);
        String result = DataUtils.getResult(httpUrl);
        System.out.println(result);
        MenuData menuData = JSONObject.parseObject(result, MenuData.class);
        return menuData.getNewslist();
    }

    /**
     * 获取随机名字
     * @return
     */
    public String randomName(){
        String[] menu = new String[]{"鸡","鸭","鱼","牛","羊","豆","菜"};
        Random r = new Random();

        int i = r.nextInt(menu.length);
        return menu[i];
    }

    /**
     * 获取土味情话
     */
    private String getData(String httpUrl){
        BufferedReader reader = null;
        String result = DataUtils.getResult(httpUrl);
        BaseData baseData = JSONObject.parseObject(result, BaseData.class);
        String str = baseData.getNewslist().get(0).toString();
        String[] split = str.split(":");
        str = split[1].substring(0,split[1].length()-1);
        return str;
    }


}
