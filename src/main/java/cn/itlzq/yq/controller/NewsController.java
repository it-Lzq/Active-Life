package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.data.NewsProvinceData;
import cn.itlzq.yq.model.data.dataModel.NewsProvinces;
import cn.itlzq.yq.util.DataUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/3 1:52
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
@SuppressWarnings("all")
public class NewsController {
    private static final String YL_URL = "http://api.tianapi.com/huabian/index?key=f2955898741fd3ada950dbc138a6405b&num=8";
    private static final String DM_URL = "http://api.tianapi.com/dongman/index?key=f2955898741fd3ada950dbc138a6405b&num=8";
    private static final String YS_URL = "http://api.tianapi.com/film/index?key=f2955898741fd3ada950dbc138a6405b&num=8";
    private static final String GJ_URL = "http://api.tianapi.com/world/index?key=f2955898741fd3ada950dbc138a6405b&num=8";
    private static final String LY_URL = "http://api.tianapi.com/travel/index?key=f2955898741fd3ada950dbc138a6405b&num=10";
    /**
     * 跳转到娱乐新闻页面
     */
    @RequestMapping("/toyl")
    public String toylNews(Model model,String word,int page){
        if(word==null) word = "";
        System.out.println(word);
        List<NewsProvinces> ylNews = getNews(YL_URL,page, word);
        System.out.println(ylNews);
        model.addAttribute("ylNews",ylNews);
        model.addAttribute("page",page);
        model.addAttribute("word",word);
        return "life/yuleNews";
    }
    /**
     * 跳转到动漫新闻页面
     */
    @RequestMapping("/todm")
    public String todmNews(Model model,String word,int page){
        if(word==null) word = "";
        List<NewsProvinces> dmNews = getNews(DM_URL,page, word);
        model.addAttribute("dmNews",dmNews);
        model.addAttribute("page",page);
        model.addAttribute("word",word);
        return "life/dmNews";
    }
    /**
     * 跳转到影视新闻页面
     */
    @RequestMapping("/toys")
    public String toysNews(Model model,String word,int page){
        if(word==null) word = "";
        List<NewsProvinces> ysNews = getNews(YS_URL,page, word);
        model.addAttribute("ysNews",ysNews);
        model.addAttribute("page",page);
        model.addAttribute("word",word);
        return "life/ysNews";
    }


    /**
     * 跳转到国际新闻页面
     */
    @RequestMapping("/togj")
    public String togjNews(Model model,String word,int page){
        if(word==null) word = "";
        List<NewsProvinces> gjNews = getNews(GJ_URL,page, word);
        model.addAttribute("gjNews",gjNews);
        model.addAttribute("page",page);
        model.addAttribute("word",word);
        return "life/gjNews";
    }

    /**
     * 跳转到旅游新闻页面
     */
    @RequestMapping("/toly")
    public String tolyNews(Model model,String word,int page){
        if(word==null) word = "";
        List<NewsProvinces> lyNews = getNews(LY_URL,page, word);
        model.addAttribute("lyNews",lyNews);
        model.addAttribute("page",page);
        model.addAttribute("word",word);
        return "life/lyNews";
    }

    public List<NewsProvinces> getNews(String url,int page,String word){
        String httpUrl = url + "&page="+page;
        if(!word.equals("")){
            httpUrl += "&word="+word;
        }
        String result = DataUtils.getResult(httpUrl);
        NewsProvinceData data = JSONObject.parseObject(result, NewsProvinceData.class);
        return data.getNewslist();
    }


}
