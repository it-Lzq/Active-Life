package cn.itlzq.yq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/28 15:14
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {
    private int status;
    private String msg;
    private Object object;
}
