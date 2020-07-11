package cn.itlzq.yq.db;


import cn.itlzq.yq.model.bj.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:04
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
public interface UserDao extends JpaRepository<User, Long> {

//    @Query("select u from User u where u.phone = ?1 and u.password = ?2")
    User findByPhoneAndPassword(String phone, String password);
}
