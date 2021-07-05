package cn.edu.cqupt.pikachu.ad.dao;

import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 21:12
 * @desc : 广告推广单元数据接口
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    /**
     * 通过广告计划Id和推广单元名称查询广告推广单元信息
     *
     * @param planId   广告计划Id
     * @param unitName 推广单元名称
     * @return 广告推广单元信息
     */
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * 通过推广单元状态查询广告推广单元信息
     *
     * @param unitStatus 推广单元状态
     * @return 广告推广单元列表
     */
    List<AdUnit> findAllByUnitStatus(Integer unitStatus);

    /**
     * 通过推广计划Id查询所有的推广单元
     *
     * @param planIds 推广计划Id
     * @return 广告推广单元列表
     */
    @Query(value = "select * from ad_unit where plan_id in (?1)", nativeQuery = true)
    List<AdUnit> findAllByPlanIds(List<Long> planIds);

}
