package cn.itlzq.yq.model.data.dataModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/25 21:12
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private String sourceUrl;
    private String title;
    private String summary;
    private String pubDateStr;
    private String infoSource;
}
