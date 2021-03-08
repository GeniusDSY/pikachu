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
import cn.edu.cqupt.pikachu.ad.service.IAdPlanService;
import cn.edu.cqupt.pikachu.ad.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public AdPlanVO createAdPlan(AdPlanDTO adPlanDTO) throws AdException {

        if (!adPlanDTO.createValidate()) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
        }

        // 检查User是否存在
        Optional<AdUser> user = userRepository.findById(adPlanDTO.getUserId());
        if (!user.isPresent()) {
            throw new AdException(ResultStatus.USER_NOT_EXISTED);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(adPlanDTO.getUserId(), adPlanDTO.getPlanName());
        if (null != oldPlan) {
            throw new AdException(ResultStatus.PLAN_EXISTED);
        }

        AdPlan newAdPlan = planRepository.save(ConvertUtils.adPlanDTO2AdPlan(adPlanDTO));

        return ConvertUtils.adPlan2AdPlanVO(newAdPlan);
    }

    /**
     * 批量获取推广计划列表
     *
     * @param adPlanGetDTO 广告推广计划获取请求
     * @return 推广计划列表信息
     * @throws AdException 广告系统异常和
     */
    @Override
    public List<AdPlanVO> getAdPlanByIds(AdPlanGetDTO adPlanGetDTO) throws AdException {

        if (!adPlanGetDTO.validate()) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
        }

        return ConvertUtils.adPlan2AdPlanVO(planRepository
                .findAllByIdInAndUserId(adPlanGetDTO.getIds(), adPlanGetDTO.getUserId()));
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
    public AdPlanVO updateAdPlan(AdPlanDTO adPlanDTO) throws AdException {

        if (!adPlanDTO.updateValidate()) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
        }

        AdPlan oldPlan = planRepository.findByIdAndUserId(adPlanDTO.getId(), adPlanDTO.getUserId());
        if (null == oldPlan) {
            throw new AdException(ResultStatus.PLAN_NOT_EXISTED);
        }

        oldPlan = ConvertUtils.adPlanDTO2AdPlan(adPlanDTO);
        oldPlan.setUpdateTime(new Date());
        return ConvertUtils.adPlan2AdPlanVO(planRepository.save(oldPlan));
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
}
