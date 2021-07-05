package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdPlanRepository;
import cn.edu.cqupt.pikachu.ad.dao.AdUnitRepository;
import cn.edu.cqupt.pikachu.ad.dao.CreativeRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitDistrictRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitItRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitKeywordRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.CreativeUnitRepository;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.*;
import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitDistrict;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitIt;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitKeyword;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.CreativeUnit;
import cn.edu.cqupt.pikachu.ad.model.vo.*;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.IAdUnitService;
import cn.edu.cqupt.pikachu.ad.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author :DengSiYuan
 * @date :2021/2/7 16:22
 * @desc : 广告系统推广单元服务接口实现
 */
@Slf4j
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    @Resource
    private AdPlanRepository planRepository;

    @Resource
    private AdUnitRepository unitRepository;

    @Resource
    private AdUnitKeywordRepository unitKeywordRepository;

    @Resource
    private AdUnitItRepository unitItRepository;

    @Resource
    private AdUnitDistrictRepository unitDistrictRepository;

    @Resource
    private CreativeRepository creativeRepository;

    @Resource
    private CreativeUnitRepository creativeUnitRepository;

    /**
     * 创建推广单元
     *
     * @param adUnitDTO
     * @return
     * @throws AdException
     */
    @Override
    public Response<AdUnitVO> createUnit(AdUnitDTO adUnitDTO) {

        if (!adUnitDTO.createValidate()) {
            return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
        }

        // 查询广告计划是否存在
        Optional<AdPlan> adPlan = planRepository.findById(adUnitDTO.getPlanId());
        if (!adPlan.isPresent()) {
            return new Response<>(ResultStatus.PLAN_NOT_EXISTED);
        }

        // 查询推广单元是否存在
        AdUnit oldAdUnit = unitRepository.findByPlanIdAndUnitName(adUnitDTO.getPlanId(), adUnitDTO.getUnitName());
        if (null != oldAdUnit) {
            return new Response<>(ResultStatus.UNIT_EXISTED);
        }

        // 插入新的推广单元数据
        AdUnit newAdUnit = unitRepository.save(ConvertUtils.adUnitDTO2AdUnit(adUnitDTO));
        return new Response<>(ConvertUtils.adUnit2AdUnitVO(newAdUnit));
    }

    /**
     * 创建推广单元关键字
     *
     * @param adUnitKeywordDTO 推广单元关键字请求数据
     * @return 推广单元关键字展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public Response<AdUnitKeywordVO> createUnitKeyword(AdUnitKeywordDTO adUnitKeywordDTO) {

        List<Long> unitIds = adUnitKeywordDTO.getUnitKeywords().stream()
                .map(AdUnitKeywordDTO.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            return new Response<>(ResultStatus.UNIT_NOT_EXIST);
        }

        List<Long> ids = Collections.emptyList();

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(adUnitKeywordDTO.getUnitKeywords())) {
            adUnitKeywordDTO.getUnitKeywords().forEach(unitKeyword ->
                    unitKeywords.add(new AdUnitKeyword(unitKeyword.getUnitId(), unitKeyword.getKeyword()))
            );
            ids = unitKeywordRepository.saveAll(unitKeywords)
                    .stream()
                    .map(AdUnitKeyword::getId)
                    .collect(Collectors.toList());
        }

        return new Response<>(new AdUnitKeywordVO(ids));
    }

    /**
     * 创建推广单元兴趣
     *
     * @param adUnitItDTO 推广单元兴趣请求数据
     * @return 推广单元兴趣展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public Response<AdUnitItVO> createUnitIt(AdUnitItDTO adUnitItDTO) {

        List<Long> unitIds = adUnitItDTO.getUnitIts().stream()
                .map(AdUnitItDTO.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        adUnitItDTO.getUnitIts().forEach(unitIt -> unitIts.add(
                new AdUnitIt(unitIt.getUnitId(), unitIt.getItTag())
        ));

        List<Long> ids = unitItRepository.saveAll(unitIts)
                .stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());

        return new Response<>(new AdUnitItVO(ids));
    }

    /**
     * 创建推广单元区域
     *
     * @param adUnitDistrictDTO 推广单元区域请求数据
     * @return 推广单元区域展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public Response<AdUnitDistrictVO> createUnitDistrict(AdUnitDistrictDTO adUnitDistrictDTO) {

        List<Long> unitIds = adUnitDistrictDTO.getUnitDistricts().stream()
                .map(AdUnitDistrictDTO.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        adUnitDistrictDTO.getUnitDistricts().forEach(unitDistrict -> unitDistricts.add(
                new AdUnitDistrict(unitDistrict.getUnitId(), unitDistrict.getProvince(), unitDistrict.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts)
                .stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());
        return new Response<>(new AdUnitDistrictVO(ids));
    }

    /**
     * 创建创意单元
     *
     * @param creativeUnitDTO 创意单元传入数据
     * @return 创意单元展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public Response<CreativeUnitVO> createCreativeUnit(CreativeUnitDTO creativeUnitDTO) {

        // 聚合推广单元Id
        List<Long> unitIds = creativeUnitDTO.getCreativeUnitItems()
                .stream()
                .map(CreativeUnitDTO.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());

        // 聚合创意Id
        List<Long> creativeIds = creativeUnitDTO.getCreativeUnitItems()
                .stream()
                .map(CreativeUnitDTO.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds) || !isRelatedCreativeExist(creativeIds)) {
            return new Response<>(ResultStatus.UNIT_OR_CREATIVE_NOT_EXIST);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        creativeUnitDTO.getCreativeUnitItems().forEach(creativeUnitItem -> creativeUnits.add(
                new CreativeUnit(creativeUnitItem.getCreativeId(), creativeUnitItem.getUnitId())
        ));

        // 存储创意单元
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits)
                .stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());

        return new Response<>(new CreativeUnitVO(ids));
    }

    /**
     * 获取所有广告单元
     *
     * @param userId 用户Id
     * @return 广告单元
     */
    @Override
    public Response<List<UserAdUnitVO>> getAllUnits(Long userId) {

        List<AdPlan> adPlans = planRepository.findByUserId(userId);
        List<Long> adPlanIds = adPlans.stream().map(AdPlan::getId).collect(Collectors.toList());
        List<AdUnit> adUnits = unitRepository.findAllByPlanIds(adPlanIds);
        return new Response<>(ConvertUtils.adUnit2UserAdUnitVO(adPlans, adUnits));
    }

    /**
     * 更新广告单元
     *
     * @param adUnitDTO 广告单元
     * @return 更新后的信息
     */
    @Override
    public Response<AdUnitVO> updateUnit(AdUnitDTO adUnitDTO) {
        try {
            if (!adUnitDTO.updateValidate()) {
                return new Response<>(ResultStatus.REQUEST_PARAM_ERROR);
            }

            Optional selectResult = unitRepository.findById(adUnitDTO.getId());

            if (!selectResult.isPresent()) {
                return new Response<>(ResultStatus.UNIT_NOT_EXIST);
            }
            AdUnit oldUnit = (AdUnit) selectResult.get();

            AdUnit adUnit = ConvertUtils.adUnitDTO2AdUnit(adUnitDTO);
            adUnit.setCreateTime(oldUnit.getCreateTime());
            adUnit.setUpdateTime(new Date());
            adUnit.setUnitStatus(adUnitDTO.getUnitStatus());
            return new Response<>(ConvertUtils.adUnit2AdUnitVO(unitRepository.save(adUnit)));
        } catch (Exception e) {
            log.error("ad-sponsor: AdUnitService updateUnit -> request={}-error={}", adUnitDTO, e.getMessage());
            return new Response<>(ResultStatus.UPDATE_ADPLAN_ERROR);
        }
    }

    /**
     * 获取广告推广单元的基本信息（id + name）
     *
     * @param userId 用户Id
     * @return 用户的所有推广单元的基本信息
     */
    @Override
    public Response<List<String>> getAllAdUnitMsg(Long userId) {
        List<AdPlan> adPlans = planRepository.findByUserId(userId);
        List<Long> adPlanIds = adPlans.stream().map(AdPlan::getId).collect(Collectors.toList());
        List<AdUnit> adUnits = unitRepository.findAllByPlanIds(adPlanIds);
        List<String> allAdPlanMsg = adUnits.stream()
                .map(adUnit -> adUnit.getId() + "." + adUnit.getUnitName())
                .collect(Collectors.toList());
        return new Response<>(allAdPlanMsg);
    }

    /**
     * 判断推广单元是否存在
     *
     * @param unitIds 推广单元Id列表
     * @return true/false
     */
    private boolean isRelatedUnitExist(List<Long> unitIds) {

        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    /**
     * 判断创意是否存在
     *
     * @param creativeIds 创意Id列表
     * @return true/false
     */
    private boolean isRelatedCreativeExist(List<Long> creativeIds) {

        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }

        return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }

}
