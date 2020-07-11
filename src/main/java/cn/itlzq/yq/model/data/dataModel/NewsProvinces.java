package cn.itlzq.yq.model.data.dataModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/1 21:25
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsProvinces {
    //时间
    private String ctime;
    //标题
    private String title;
    //图片地址
    private String picUrl;
    //地址
    private String url;
    //来源
    private String source;
}
