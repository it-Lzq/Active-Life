package cn.itlzq.yq.model.bj.face_ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/1 13:53
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_list{
    private Double score;
    private String group_id;
    private String user_id;
    private String user_info;
}

