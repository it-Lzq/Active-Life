package cn.itlzq.yq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/27 19:43
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private int id;
    private String type_name;
    private String cp_name;
    private String zuofa;
    private String texing;
    private String tishi;
    private String tiaoliao;
    private String yuanliao;

    public List<String> getZF(){
        List<String> zfList = new ArrayList<>();
        int start = 0;
        int end = 0;
        if(zuofa.charAt(0) == '('){
            for (int i = 1; i < 20; i++) {
                boolean flag = zuofa.contains("("+i+")");
                start = zuofa.indexOf("("+i+")");
                if(flag){
                    if(zuofa.contains("("+(i+1)+")")){
                        end = zuofa.indexOf("("+(i+1)+")");
                        zfList.add(zuofa.substring(start,end));
                    }else{
                        zfList.add(zuofa.substring(start));

                        return zfList;
                    }
                }
            }
        }
        String c = zuofa.charAt(1)+"";
        for (int i = 1; i < 20; i++) {
            boolean flag = zuofa.contains(i+c);
            start = zuofa.indexOf(i+c);
            if(flag){
                if(zuofa.contains(i+1+c)){
                    end = zuofa.indexOf(i+1+c);
                    zfList.add(zuofa.substring(start,end));
                }else{
                    zfList.add(zuofa.substring(start));

                    return zfList;
                }
            }
        }
        if(zuofa.charAt(0) == '['){
            zuofa = zuofa.substring(1,zuofa.length()-1);
            c = zuofa.charAt(1)+"";
            for (int i = 1; i < 20; i++) {
                boolean flag = zuofa.contains(i+c);
                start = zuofa.indexOf(i+c);
                if(flag){
                    if(zuofa.contains(i+1+c)){
                        end = zuofa.indexOf(i+1+c);
                        zfList.add(zuofa.substring(start,end));
                    }else{
                        zfList.add(zuofa.substring(start));
                        System.out.println(zfList);
                        return zfList;
                    }
                }
            }
        }

        zfList.add(zuofa);
        return zfList;
    }
}
