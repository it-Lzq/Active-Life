package cn.itlzq.yq.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 15:59
 * @email 邮箱:905866484@qq.com
 * @description 描述：配置类
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // List<String> excludeUrl = new ArrayList<>();
//        excludeUrl.add("/admin");
//        excludeUrl.add("/admin/login");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/bj/**")
                .excludePathPatterns("/bj/login")
                .excludePathPatterns("/bj");


    }
}
