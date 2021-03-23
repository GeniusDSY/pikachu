package cn.edu.cqupt.pikachu.ad.dao;

import cn.edu.cqupt.pikachu.ad.model.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 20:24
 * @desc : 广告系统用户数据接口
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    AdUser findByUsername(String username);

}
