package cn.itlzq.yq.service;


import cn.itlzq.yq.model.bj.User;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:00
 * @email 邮箱:905866484@qq.com
 * @description 描述：用户Service
 */
public interface UserService {

    /**
     * 检查用户登录验证
     * @param phone 手机号
     * @param password 密码
     * @return 真实用户
     */
    User checkUser(String phone, String password);

    User saveUser(User user);

    /**
     * 更新user
     * @param user 用户
     * @return 修改之后
     */
    User updateUser(User user);

    User getOne(Long id);
}
