package cn.itlzq.yq.controller;

import cn.itlzq.yq.model.Country;
import cn.itlzq.yq.model.Provinces;
import cn.itlzq.yq.model.data.*;
import cn.itlzq.yq.model.data.dataModel.Rumor;
import cn.itlzq.yq.util.DataUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/25 15:54
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@SuppressWarnings("all")
@Controller
public class YQController {

    /**
     * 主页
     */
    @RequestMapping("/")
    public String index(Model model){
        //获取国内疫情json
        //最新动态json
        ChinaData chinaDt =getChinaData();
       // System.out.println(newsData.getNewslist().get(0));
        NewsData newsData = getNews();
        model.addAttribute("chinaData",chinaDt.getNewslist());
        model.addAttribute("newsList",newsData.getNews());
        model.addAttribute("tipData",newsData.getDesc().get(0));
       // System.out.println(newsData1.getDesc());
        return "yiqing/index";
    }


    /**
     * 跳转到省份页面
     * @param name 传入省份名
     */
    @RequestMapping("/yq/{name}")
    public String ToProvince(@PathVariable String name,Model model){
        System.out.println(name);
        List<Provinces> chinaData =getChinaData().getNewslist();
        NewsData newsData = getNews();
        Provinces provinces = new Provinces();
        NewsProvinceData newsProvinceData = getProvinceData(name, 1);
        System.out.println(newsProvinceData);
        if(newsProvinceData.getNewslist() != null){
            model.addAttribute("proNewsList",newsProvinceData.getNewslist().subList(0,6));
        }
        for (int i = 0; i < chinaData.size(); i++) {
            if(chinaData.get(i).getProvinceShortName().equals(name)){
                provinces = chinaData.get(i);
                break;
            }
        }
        if(name.equals("云南")){
            model.addAttribute("proNewsList",null);
        }
        model.addAttribute("provinces",provinces);
        System.out.println(provinces);
        model.addAttribute("newsList",newsData.getNews());
        model.addAttribute("tipData",newsData.getDesc().get(0));
        model.addAttribute("page",1);
        //System.out.println(chinaData.get(0).getCities().toString());
        return "yiqing/province";
    }

    /**
     *  搜索省份页面
     */
    @RequestMapping("/province")
    public String searchProvince(String name,int page,Model model){
        List<Provinces> chinaData =getChinaData().getNewslist();
        NewsData newsData = getNews();//获取注意事项
        System.out.println(page);
        NewsProvinceData newsProvinceData = getProvinceData(name, page);
        if(newsProvinceData.getCode() == 200){
            Provinces provinces = new Provinces();
            for (int i = 0; i < chinaData.size(); i++) {
                if(chinaData.get(i).getProvinceShortName().equals(name)){
                    provinces = chinaData.get(i);
                    break;
                }
            }
            model.addAttribute("provinces",provinces);
            model.addAttribute("proNewsList",newsProvinceData.getNewslist().subList(0,6));
        }

        System.out.println(newsProvinceData.getNewslist());

        model.addAttribute("newsList",newsData.getNews());
        model.addAttribute("tipData",newsData.getDesc().get(0));
        model.addAttribute("page",page);
        //System.out.println(chinaData.get(0).getCities().toString());
        return "yiqing/province";
    }

    /**
     * 跳转到谣言界面
     */
    @RequestMapping("/toRumor")
    public String toRumor(int page,Model model){
        RumorData rumorData = getRumorData(page);
        List<Rumor> rumorList = rumorData.getNewslist();
        model.addAttribute("rumorList",rumorList);
        model.addAttribute("page",page);
        return "yiqing/rumor";
    }

    /**
     * 跳转到世界疫情界面
     */
    @RequestMapping("/world")
    public String toWorld(Model model){
        WorldData worldData = getWorldData();
        List<Country> countryList = worldData.getNewslist();
        for (int i = 0; i < countryList.size(); i++) {
            Country country = countryList.get(i);
            if(country.getCurrentConfirmedCount() < 10000){
                countryList.remove(country);
                i--;
            }
        }
        //System.out.println(countryList);
        model.addAttribute("countryList",countryList);
        return "yiqing/world";
    }

    /**
     *  跳转到国家页面
     */
    @RequestMapping("/country")
    public String toCountry(String name,Model model){
        WorldData worldData = getWorldData();
        Country country = null;
        List<Country> countryList = worldData.getNewslist();
        for (Country c : countryList) {
            if(c.getProvinceName().equals(name)){
                country = c;
            }
        }
        if(country == null) return "error/500";
        model.addAttribute("country",country);
        return "yiqing/country";
    }

    /**
     *  获取新闻资讯
     * @return
     */
    public NewsData getNews(){
        String httpUrl = "http://api.tianapi.com/txapi/ncov/index?key=f2955898741fd3ada950dbc138a6405b ";
        String result = DataUtils.getResult(httpUrl);
        BaseData newsData = JSONObject.parseObject(result, BaseData.class);
        System.out.println(newsData);
        NewsData newsData1 = JSONObject.parseObject(newsData.getNewslist().get(0).toString(), NewsData.class);
        return newsData1;
    }

    /**
     * 获取世界疫情数据
     * @return
     */
    public WorldData getWorldData(){
        String httpUrl = "http://api.tianapi.com/txapi/ncovabroad/index?key=f2955898741fd3ada950dbc138a6405b";
        String result = DataUtils.getResult(httpUrl);
        WorldData worldData = JSONObject.parseObject(result, WorldData.class);
        return worldData;
    }

    /**
     * 获取中国疫情数据
     * @return
     */
    public ChinaData getChinaData(){
        String httpUrl = "http://api.tianapi.com/txapi/ncovcity/index?key=f2955898741fd3ada950dbc138a6405b ";
        String result = DataUtils.getResult(httpUrl);
        ChinaData chinaDt = JSONObject.parseObject(result, ChinaData.class);
        return chinaDt;
    }

    /**
     * 获取地区最新资讯
     * @param areaname
     * @param page
     * @return
     */
    public NewsProvinceData getProvinceData(String areaname,int page){
        String httpUrl = "http://api.tianapi.com/areanews/index?key=f2955898741fd3ada950dbc138a6405b&areaname="+areaname+"&page="+page;
        String result = DataUtils.getResult(httpUrl);
        NewsProvinceData provinceData = JSONObject.parseObject(result, NewsProvinceData.class);
        return provinceData;
    }

    /**
     * 获取最新谣言
     * @param page
     * @return
     */
    public RumorData getRumorData(int page){
        String httpUrl = "http://api.tianapi.com/txapi/rumour/index?key=f2955898741fd3ada950dbc138a6405b"+"&page="+page;
        String result = DataUtils.getResult(httpUrl);
        RumorData rumorData = JSONObject.parseObject(result, RumorData.class);
        return rumorData;
    }

}
