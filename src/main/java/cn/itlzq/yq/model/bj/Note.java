package cn.itlzq.yq.model.bj;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 0:14
 * @email 邮箱:905866484@qq.com
 * @description 描述：博客类
 */
@Data
@Entity
@Table(name = "al_note")
public class Note {
    //博客id
    @Id @GeneratedValue
    private Long id;
    //笔记标题
    private String title;
    //笔记内容
    private String content;
    //笔记标记
    private String flag;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;//类别
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();//标签
    @ManyToOne
    private User user;

    @Transient
    private String tagIds;

    private String description;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

}
