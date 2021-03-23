package cn.edu.cqupt.pikachu.ad.dao.unit_condition;

import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 22:13
 * @desc : 广告单元地域限制数据接口
 */
public interface AdUnitDistrictRepository extends JpaRepository<AdUnitDistrict, Long> {
}
