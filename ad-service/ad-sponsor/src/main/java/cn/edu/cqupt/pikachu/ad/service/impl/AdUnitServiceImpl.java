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
    private CreativeUnitRepository creativeUnitRepository;

    /**
     * 创建推广单元
     *
     * @param adUnitDTO
     * @return
     * @throws AdException
     */
    @Override
    public AdUnitVO createUnit(AdUnitDTO adUnitDTO) throws AdException {

        if (!adUnitDTO.createValidate()) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
        }

        // 查询广告计划是否存在
        Optional<AdPlan> adPlan = planRepository.findById(adUnitDTO.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(ResultStatus.PLAN_NOT_EXISTED);
        }

        // 查询推广单元是否存在
        AdUnit oldAdUnit = unitRepository.findByPlanIdAndUnitName(adUnitDTO.getPlanId(), adUnitDTO.getUnitName());
        if (null != oldAdUnit) {
            throw new AdException(ResultStatus.UNIT_EXISTED);
        }

        // 插入新的推广单元数据
        AdUnit newAdUnit = unitRepository.save(ConvertUtils.adUnitDTO2AdUnit(adUnitDTO));
        return ConvertUtils.adUnit2AdUnitVO(newAdUnit);
    }

    /**
     * 创建推广单元关键字
     *
     * @param adUnitKeywordDTO 推广单元关键字请求数据
     * @return 推广单元关键字展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public AdUnitKeywordVO createUnitKeyword(AdUnitKeywordDTO adUnitKeywordDTO) throws AdException {

        List<Long> unitIds = adUnitKeywordDTO.getUnitKeywords().stream()
                .map(AdUnitKeywordDTO.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
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

        return new AdUnitKeywordVO(ids);
    }

    /**
     * 创建推广单元兴趣
     *
     * @param adUnitItDTO 推广单元兴趣请求数据
     * @return 推广单元兴趣展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public AdUnitItVO createUnitIt(AdUnitItDTO adUnitItDTO) throws AdException {

        List<Long> unitIds = adUnitItDTO.getUnitIts().stream()
                .map(AdUnitItDTO.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        adUnitItDTO.getUnitIts().forEach(unitIt -> unitIts.add(
                new AdUnitIt(unitIt.getUnitId(), unitIt.getItTag())
        ));

        List<Long> ids = unitItRepository.saveAll(unitIts)
                .stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());

        return new AdUnitItVO(ids);
    }

    /**
     * 创建推广单元区域
     *
     * @param adUnitDistrictDTO 推广单元区域请求数据
     * @return 推广单元区域展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public AdUnitDistrictVO createUnitDistrict(AdUnitDistrictDTO adUnitDistrictDTO) throws AdException {

        List<Long> unitIds = adUnitDistrictDTO.getUnitDistricts().stream()
                .map(AdUnitDistrictDTO.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        adUnitDistrictDTO.getUnitDistricts().forEach(unitDistrict -> unitDistricts.add(
                new AdUnitDistrict(unitDistrict.getUnitId(), unitDistrict.getProvince(), unitDistrict.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts)
                .stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());
        return new AdUnitDistrictVO(ids);
    }

    /**
     * 创建创意单元
     *
     * @param creativeUnitDTO 创意单元传入数据
     * @return 创意单元展示数据
     * @throws AdException 广告系统异常
     */
    @Override
    public CreativeUnitVO createCreativeUnit(CreativeUnitDTO creativeUnitDTO) throws AdException {

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
            throw new AdException(ResultStatus.REQUEST_PARAM_ERROR);
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

        return new CreativeUnitVO(ids);
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

        return creativeUnitRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }

}
