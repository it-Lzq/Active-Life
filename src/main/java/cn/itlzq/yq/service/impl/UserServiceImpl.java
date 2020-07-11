package cn.itlzq.yq.service.impl;


import cn.itlzq.yq.db.UserDao;
import cn.itlzq.yq.model.bj.User;
import cn.itlzq.yq.service.UserService;
import cn.itlzq.yq.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:02
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao dao;

    /**
     * 检查用户登录验证
     * @param phone 用户名
     * @param password 密码
     * @return 真实用户
     */
    @Override
    public User checkUser(String phone, String password) {
        System.out.println(dao);

        User user = dao.findByPhoneAndPassword(phone, MD5.getPassword(password));
        System.out.println(user);
        return user;
    }

    @Override
    public User saveUser(User user) {
        user.setCreateTime(new Date());
        String password = user.getPassword();
        user.setPassword(MD5.getPassword(password));
        System.out.println(user);
        return dao.save(user);
    }

    /**
     * 更新user
     *
     * @param user 用户
     * @return 修改之后
     */
    @Override
    public User updateUser(User user) {
        return dao.save(user);
    }

    @Override
    public User getOne(Long id) {
        return dao.getOne(id);
    }
}
