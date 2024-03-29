package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdPlanRepository;
import cn.edu.cqupt.pikachu.ad.dao.AdUserRepository;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUser;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.IAdPlanService;
import cn.edu.cqupt.pikachu.ad.utils.CommonUtils;
import cn.edu.cqupt.pikachu.ad.utils.ConvertUtils;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author :DengSiYuan
 * @date :2021/2/6 12:56
 * @desc : 推广计划服务实现
 */
@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    @Resource
    private AdUserRepository userRepository;

    @Resource
    private AdPlanRepository planRepository;

    /**
     * 创建广告推广计划
     *
     * @param adPlanDTO 广告推广计划
     * @return 广告推广计划信息
     * @throws AdException 广告系统异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<AdPlanVO> createAdPlan(AdPlanDTO adPlanDTO) {

        try {
            if (!adPlanDTO.createValidate()) {
                return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
            }

            // 检查User是否存在
            Optional<AdUser> user = userRepository.findById(adPlanDTO.getUserId());
            if (!user.isPresent()) {
                return new Response<>(ResultStatus.USER_NOT_EXISTED);
            }

            AdPlan oldPlan = planRepository.findByUserIdAndPlanName(adPlanDTO.getUserId(), adPlanDTO.getPlanName());
            if (null != oldPlan) {
                return new Response<>(ResultStatus.PLAN_EXISTED);
            }

            AdPlan newAdPlan = planRepository.save(ConvertUtils.adPlanDTO2AdPlan(adPlanDTO));

            return new Response<>(ConvertUtils.adPlan2AdPlanVO(newAdPlan));
        } catch (Exception e) {
            log.error("ad-sponsor: ADPlanService createAdPlan -> request={}-error={}", adPlanDTO, e.getMessage());
            return new Response<>(ResultStatus.CREATE_ADPLAN_ERROR);
        }

    }

    /**
     * 批量获取推广计划列表
     *
     * @param adPlanGetDTO 广告推广计划获取请求
     * @return 推广计划列表信息
     * @throws AdException 广告系统异常和
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallBack")
    public Response<List<AdPlanVO>> getAdPlanByIds(AdPlanGetDTO adPlanGetDTO) {

        if (!adPlanGetDTO.validate()) {
            return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
        }

        // 全量查询
        if (CollectionUtils.isEmpty(adPlanGetDTO.getIds()) && StringUtils.isEmpty(adPlanGetDTO.getName())) {
            return new Response<>(ConvertUtils.adPlan2AdPlanVO(
                    planRepository.findByUserId(adPlanGetDTO.getUserId())));
        }

        // 搜索框查询
        if (CollectionUtils.isEmpty(adPlanGetDTO.getIds())) {
            return new Response<>(ConvertUtils.adPlan2AdPlanVO(
                    planRepository.findAllByIdInAndUserId(adPlanGetDTO.getIds(), adPlanGetDTO.getUserId())));
        }

        // 名称模糊查询
        if (StringUtils.isEmpty(adPlanGetDTO.getName())) {
            return new Response<>(ConvertUtils.adPlan2AdPlanVO(
                    planRepository.findByPlanNameAndUserId(adPlanGetDTO.getUserId(),
                            "%" + adPlanGetDTO.getName() + "%")));
        }

        return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);

    }

    /**
     * 更新推广计划
     *
     * @param adPlanDTO 要更新的推广计划信息
     * @return 更新后的推广计划信息
     * @throws AdException 广告系统异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<AdPlanVO> updateAdPlan(AdPlanDTO adPlanDTO) {
        try {
            if (!adPlanDTO.updateValidate()) {
                return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
            }

            AdPlan oldPlan = planRepository.findByIdAndUserId(adPlanDTO.getId(), adPlanDTO.getUserId());
            if (null == oldPlan) {
                return new Response<>(ResultStatus.PLAN_NOT_EXISTED);
            }

            AdPlan newPlan = ConvertUtils.adPlanDTO2AdPlan(adPlanDTO);
            newPlan.setCreateTime(oldPlan.getCreateTime());
            newPlan.setUpdateTime(new Date());
            newPlan.setPlanStatus(adPlanDTO.getPlanStatus());
            return new Response<>(ConvertUtils.adPlan2AdPlanVO(planRepository.save(newPlan)));
        } catch (Exception e) {
            log.error("ad-sponsor: ADPlanService updateAdPlan -> request={}-error={}", adPlanDTO, e.getMessage());
            return new Response<>(ResultStatus.UPDATE_ADPLAN_ERROR);
        }

    }

    /**
     * 删除推广计划
     *
     * @param adPlanDTO 要删除的推广计划信息
     * @return 删除成功与否
     * @throws AdException 广告系统异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdPlan(AdPlanDTO adPlanDTO) throws AdException {

        if (!adPlanDTO.deleteValidate()) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
        }

        AdPlan oldPlan = planRepository.findByIdAndUserId(adPlanDTO.getId(), adPlanDTO.getUserId());
        if (null == oldPlan) {
            throw new AdException(ResultStatus.PLAN_NOT_EXISTED);
        }

        oldPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        oldPlan.setUpdateTime(new Date());
        planRepository.save(oldPlan);
    }

    /**
     * 获取广告计划的基本信息（id + name）
     *
     * @param userId 用户Id
     * @return 广告计划基本信息列表（id + name）
     */
    @Override
    public Response<List<String>> getAllAdPlanMsg(Long userId) {
        List<AdPlan> adPlans = planRepository.findByUserId(userId);
        List<String> allAdPlanMsg = adPlans.stream()
                                            .map(adPlan -> adPlan.getId() + "." + adPlan.getPlanName())
                                            .collect(Collectors.toList());
        return new Response<>(allAdPlanMsg);
    }

    /**
     * 服务熔断的回调方法
     *
     * @param adPlanGetDTO 请求
     * @param e            异常
     * @return 熔断后的响应
     */
    public Response<List<AdPlanVO>> fallBack(AdPlanGetDTO adPlanGetDTO, Throwable e) {
        log.error("ad-sponsor: AdPlanService getAdPlanByIds -> request:{}-error:{}", JSON.toJSONString(adPlanGetDTO), e);
        return new Response<>(ResultStatus.SERVICE_FUSE);
    }
}
