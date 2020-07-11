package cn.itlzq.yq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/27 0:39
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private Long locationId;
    //名字
    private String provinceName;

    private String countryShortName;
    //已确认数量
    private int confirmedCount;
    //治愈数量
    private int curedCount;
    //现任数量
    private int currentConfirmedCount;
    //死亡数量
    private int deadCount;
    //死亡率
    private String deadRate;
    //死亡等级
    private int deadRateRank;
    //可疑数量
    private int suspectedCount;
}
