package cn.itlzq.yq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/4/25 15:44
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Provinces {

    private Long locationId;
    private String provinceName;

    private String provinceShortName;
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
    //最新评论
    private String comment;
    private List<City> cities;
}
