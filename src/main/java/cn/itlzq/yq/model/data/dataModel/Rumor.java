package cn.itlzq.yq.model.data.dataModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/2 12:53
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rumor {
    //时间
    private String date;
    //标题
    private String title;
    //图片
    private String imgsrc;
    //内容
    private String desc;
    //作者
    private String author;
}
