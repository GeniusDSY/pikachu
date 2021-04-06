package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/6 11:43
 * @desc : 广告计划服务
 */
public interface IAdPlanService {

    /**
     * 创建广告推广计划
     *
     * @param adPlanDTO 广告推广计划
     * @return 广告推广计划信息
     * @throws AdException 广告系统异常
     */
    Response<AdPlanVO> createAdPlan(AdPlanDTO adPlanDTO);

    /**
     * 批量获取推广计划列表
     *
     * @param adPlanGetDTO 广告推广计划获取请求
     * @return 推广计划列表信息
     * @throws AdException 广告系统异常和
     */
    Response<List<AdPlanVO>> getAdPlanByIds(AdPlanGetDTO adPlanGetDTO);

    /**
     * 更新推广计划
     *
     * @param adPlanDTO 要更新的推广计划信息
     * @return 更新后的推广计划信息
     * @throws AdException 广告系统异常
     */
    Response<AdPlanVO> updateAdPlan(AdPlanDTO adPlanDTO);

    /**
     * 删除推广计划
     *
     * @param adPlanDTO 要删除的推广计划信息
     * @throws AdException 广告系统异常
     */
    void deleteAdPlan(AdPlanDTO adPlanDTO) throws AdException;

}
