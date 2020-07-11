package cn.itlzq.yq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/25 20:02
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private Long locationId;
    private String cityName;
    //已确认数量
    private int confirmedCount;
    //治愈数量
    private int curedCount;
    //现任数量
    private int currentConfirmedCount;
    //死亡数量
    private int deadCount;
    //可疑数量
    private int suspectedCount;
    private long paientId;
}
