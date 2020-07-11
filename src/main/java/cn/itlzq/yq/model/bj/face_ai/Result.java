package cn.itlzq.yq.model.bj.face_ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/1 17:57
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String face_token;
    private List<User_list> user_list;
}
