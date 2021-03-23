package cn.edu.cqupt.pikachu.ad.dao;

import cn.edu.cqupt.pikachu.ad.model.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 21:21
 * @desc : 创意数据接口
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {
}
