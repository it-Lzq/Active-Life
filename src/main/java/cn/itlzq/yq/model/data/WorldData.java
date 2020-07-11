package cn.itlzq.yq.model.data;

import cn.itlzq.yq.model.Country;
import cn.itlzq.yq.model.Provinces;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/27 0:44
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorldData {
    private String code;
    private String msg;
    private List<Country> newslist;
}
