package cn.itlzq.yq.model.bj;



import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 0:37
 * @email 邮箱:905866484@qq.com
 * @description 描述：用户
 */

@Entity @Data @Table(name="al_user")
public class User {
    @Id @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String phone;

    private boolean face;

    private String qq;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @OneToMany
    private List<Note> notes = new ArrayList<>();


}
