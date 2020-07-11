package cn.itlzq.yq.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/3 1:55
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
public class DataUtils {


    public static String getResult(String httpUrl){
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }

            reader.close();
            result = sbf.toString();
            //chinaData = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
