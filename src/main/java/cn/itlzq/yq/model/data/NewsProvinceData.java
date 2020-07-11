package cn.itlzq.yq.model.data;

import cn.itlzq.yq.model.data.dataModel.NewsProvinces;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/1 21:29
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsProvinceData {
    private int code;
    private String msg;
    private List<NewsProvinces> newslist;
}
