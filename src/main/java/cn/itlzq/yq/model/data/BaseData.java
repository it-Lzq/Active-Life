package cn.itlzq.yq.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/25 20:01
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseData {
    private String code;
    private String msg;
    private List<Object> newslist;
}
