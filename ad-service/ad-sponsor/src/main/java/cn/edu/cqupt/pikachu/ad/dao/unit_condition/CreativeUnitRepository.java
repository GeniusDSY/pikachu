package cn.edu.cqupt.pikachu.ad.dao.unit_condition;

import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.CreativeUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 22:15
 * @desc : 创意单元数据接口
 */
public interface CreativeUnitRepository extends JpaRepository<CreativeUnit, Long> {
}
