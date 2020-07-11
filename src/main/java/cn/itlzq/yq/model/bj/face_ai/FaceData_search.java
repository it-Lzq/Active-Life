package cn.itlzq.yq.model.bj.face_ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/5/1 13:48
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceData_search {
    private String error_msg;
    private int error_code;
    private Result result;
}
