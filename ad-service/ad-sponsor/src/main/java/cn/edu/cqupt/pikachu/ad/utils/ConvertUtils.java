package cn.edu.cqupt.pikachu.ad.utils;

import cn.edu.cqupt.pikachu.ad.constants.enums.*;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.AdUnitDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.CreativeDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.UserDTO;
import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUser;
import cn.edu.cqupt.pikachu.ad.model.entity.Creative;
import cn.edu.cqupt.pikachu.ad.model.vo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author :DengSiYuan
 * @date :2021/2/5 22:39
 * @desc : 转换工具类
 */
public class ConvertUtils {

    /**
     * UserDTO转换成Entity AdUser
     *
     * @param userDTO 用户传入信息
     * @return AdUser实体数据信息
     * @throws AdException Ad自定义异常
     */
    public static AdUser userDTO2AdUser(UserDTO userDTO) throws AdException {
        return new AdUser(userDTO.getUserId(),
                userDTO.getUsername(),
                CommonUtils.md5Encrypt(userDTO.getPassword()),
                userDTO.getAge(),
                CommonUtils.getByDesc(ValueEnums.class, userDTO.getGender()),
                CommonUtils.getByDesc(UserStatusEnums.class, userDTO.getStatus()),
                CommonUtils.md5Encrypt(userDTO.getUsername()));
    }

    /**
     * Entity AdUser转换成UserVO
     *
     * @param adUser 用户数据库数据
     * @return 用户响应展示数据
     */
    public static UserVO adUser2UserVO(AdUser adUser) {
        UserVO userVO = new UserVO();
        userVO.setUserId(adUser.getId());
        userVO.setUsername(adUser.getUsername());
        userVO.setAge(ValueEnums.UNKNOWN.getCode().equals(adUser.getAge()) ?
                ValueEnums.UNKNOWN.getDesc() : String.valueOf(adUser.getAge()));
        userVO.setGender(CommonUtils.getByCode(ValueEnums.class, adUser.getGender()));
        userVO.setToken(adUser.getToken());
        userVO.setStatus(CommonUtils.getByCode(UserStatusEnums.class, adUser.getUserStatus()));
        userVO.setCreateTime(CommonUtils.parseDate2String(adUser.getCreateTime()));
        userVO.setUpdateTime(CommonUtils.parseDate2String(adUser.getUpdateTime()));
        return userVO;
    }

    /**
     * AdPlanDTO 转换成Entity AdPlan
     *
     * @param adPlanDTO 请求入参AdPlanDTO
     * @return Entity AdPlan
     * @throws AdException 广告系统异常
     */
    public static AdPlan adPlanDTO2AdPlan(AdPlanDTO adPlanDTO) throws AdException {
        return new AdPlan(adPlanDTO.getId(),
                adPlanDTO.getUserId(),
                adPlanDTO.getPlanName(),
                CommonUtils.parseString2Date(adPlanDTO.getStartDate()),
                CommonUtils.parseString2Date(adPlanDTO.getEndDate()));
    }

    /**
     * Entity AdPlan 转换成 AdPlanVO
     *
     * @param adPlan Entity AdPlan
     * @return AdPlan展示数据
     */
    public static AdPlanVO adPlan2AdPlanVO(AdPlan adPlan) {
        return new AdPlanVO(adPlan.getId(),
                adPlan.getPlanName(),
                adPlan.getPlanStatus(),
                CommonUtils.parseDate2String(adPlan.getStartDate()),
                CommonUtils.parseDate2String(adPlan.getEndDate()),
                CommonUtils.parseDate2String(adPlan.getCreateTime()),
                CommonUtils.parseDate2String(adPlan.getUpdateTime()));
    }

    /**
     * Entity AdPlan 批量转换成 AdPlanVO
     *
     * @param adPlans Entity AdPlan
     * @return AdPlan展示数据
     */
    public static List<AdPlanVO> adPlan2AdPlanVO(List<AdPlan> adPlans) {

        return adPlans.stream().map(adPlan ->
                new AdPlanVO(adPlan.getId(),
                        adPlan.getPlanName(),
                        adPlan.getPlanStatus(),
                        CommonUtils.parseDate2String(adPlan.getStartDate()),
                        CommonUtils.parseDate2String(adPlan.getEndDate()),
                        CommonUtils.parseDate2String(adPlan.getCreateTime()),
                        CommonUtils.parseDate2String(adPlan.getUpdateTime())))
                .collect(Collectors.toList());
    }


    /**
     * AdUnitDTO 转换成 AdUnit
     *
     * @param adUnitDTO 请求传入数据
     * @return Entity AdUnit
     */
    public static AdUnit adUnitDTO2AdUnit(AdUnitDTO adUnitDTO) {

        return new AdUnit(adUnitDTO.getId(),
                adUnitDTO.getPlanId(),
                adUnitDTO.getUnitName(),
                adUnitDTO.getPositionType(),
                adUnitDTO.getBudget());
    }

    /**
     * Entity AdUnit 转换成 AdUnitVO
     *
     * @param adUnit 广告单元Entity数据
     * @return 广告单元展示数据AdUnitVO
     */
    public static AdUnitVO adUnit2AdUnitVO(AdUnit adUnit) {

        return new AdUnitVO(adUnit.getId(), adUnit.getUnitName());
    }

    /**
     * 传入创意 CreativeDTO 转换成 Entity Creative
     *
     * @param creativeDTO 创意传入数据
     * @return Entity 创意数据
     */
    public static Creative creativeDTO2Creative(CreativeDTO creativeDTO) {
        Creative creative = new Creative();
        creative.setId(creative.getId());
        creative.setName(creativeDTO.getName());
        creative.setType(creativeDTO.getType());
        creative.setMaterialType(creativeDTO.getMaterialType());
        creative.setHeight(creativeDTO.getHeight());
        creative.setWidth(creativeDTO.getWidth());
        creative.setSize(creativeDTO.getSize());
        creative.setDuration(creativeDTO.getDuration());
        creative.setAuditStatus(creativeDTO.getAuditStatus());
        creative.setUserId(creativeDTO.getUserId());
        creative.setAdContents(creativeDTO.getAdContents());
        creative.setUrl(creativeDTO.getUrl());
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }

    /**
     * 广告推广单元转换成用户广告推广单元
     *
     * @param adPlans 广告推广计划
     * @param adUnits 广告推广单元
     * @return 用户广告推广单元列表
     */
    public static List<UserAdUnitVO> adUnit2UserAdUnitVO(List<AdPlan> adPlans, List<AdUnit> adUnits) {

        List<UserAdUnitVO> userAdUnitVOS = new ArrayList<>();
        for (AdUnit adUnit : adUnits) {
            UserAdUnitVO userAdUnitVO = new UserAdUnitVO();
            userAdUnitVO.setId(adUnit.getId());
            userAdUnitVO.setUnitName(adUnit.getUnitName());
            Optional optional = adPlans.stream().filter(adPlan -> adPlan.getId().equals(adUnit.getPlanId())).findFirst();
            if (optional.isPresent()) {
                AdPlan adPlan = (AdPlan) optional.get();
                userAdUnitVO.setPlanMsg(adPlan.getId() + "." + adPlan.getPlanName());
            } else {
                userAdUnitVO.setPlanMsg("未知广告推广计划");
            }
            userAdUnitVO.setBudget(adUnit.getBudget());
            userAdUnitVO.setUnitStatus(adUnit.getUnitStatus());
            userAdUnitVO.setPositionType(CommonUtils.getByCode(PositionTypeEnums.class, adUnit.getPositionType()));
            userAdUnitVO.setCreateTime(CommonUtils.parseDate2String(adUnit.getCreateTime()));
            userAdUnitVO.setUpdateTime(CommonUtils.parseDate2String(adUnit.getUpdateTime()));
            userAdUnitVOS.add(userAdUnitVO);
        }
        return userAdUnitVOS;
    }

    /**
     * Creative to CreativeVO
     *
     * @param creative 创意实体数据
     * @return 创意展示数据
     */
    public static CreativeVO creative2CreativeVO(Creative creative) {
        CreativeVO creativeVO = new CreativeVO();
        creativeVO.setId(creative.getId());
        creativeVO.setName(creative.getName());
        creativeVO.setType(CommonUtils.getByCode(CreativeType.class, creative.getType()));
        creativeVO.setMaterialType(CommonUtils.getByCode(CreativeMaterialType.class, creative.getMaterialType()));
        creativeVO.setHeight(creative.getHeight());
        creativeVO.setWidth(creative.getWidth());
        creativeVO.setSize(creative.getSize());
        creativeVO.setDuration(creative.getDuration());
        creativeVO.setAuditStatus(creative.getAuditStatus());
        creativeVO.setUserId(creative.getUserId());
        creativeVO.setAdContents(creative.getAdContents());
        creativeVO.setUrl(creative.getUrl());
        creativeVO.setCreateTime(CommonUtils.parseDate2String(creative.getCreateTime()));
        creativeVO.setUpdateTime(CommonUtils.parseDate2String(creative.getUpdateTime()));
        return creativeVO;
    }
}
