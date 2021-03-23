package cn.edu.cqupt.pikachu.ad.dao;

import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 20:34
 * @desc : 广告计划数据接口
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * 通过广告计划Id和用户Id查询广告计划
     *
     * @param id     广告计划Id
     * @param userId 用户Id
     * @return 广告计划记录
     */
    AdPlan findByIdAndUserId(Long id, Long userId);

    /**
     * 通过广告计划Id列表和用户Id查询广告计划列表
     *
     * @param ids    广告计划Id列表
     * @param userId 用户Id
     * @return 广告计划列表
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * 通过用户Id和广告计划名称查询广告计划
     *
     * @param userId   用户Id
     * @param planName 广告计划名称
     * @return 广告计划
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 通过广告计划状态查询广告计划
     *
     * @param status 广告状态
     * @return 广告计划列表
     */
    List<AdPlan> findAllByPlanStatus(Integer status);

}
