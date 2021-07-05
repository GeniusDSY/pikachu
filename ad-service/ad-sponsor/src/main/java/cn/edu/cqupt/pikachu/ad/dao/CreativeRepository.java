package cn.edu.cqupt.pikachu.ad.dao;

import cn.edu.cqupt.pikachu.ad.model.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 21:21
 * @desc : 创意数据接口
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {

    /**
     * 通过UserId查询该用户的所有创意
     *
     * @param userId 用户id
     * @return 该用户的所有创意
     */
    List<Creative> findAllByUserId(Long userId);
}
