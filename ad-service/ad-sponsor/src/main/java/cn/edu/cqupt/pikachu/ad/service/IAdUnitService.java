package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.*;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import cn.edu.cqupt.pikachu.ad.model.vo.*;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/7 15:43
 * @desc : 广告推广单元服务接口
 */
public interface IAdUnitService {

    /**
     * 创建推广单元
     *
     * @param adUnitDTO
     * @return
     * @throws AdException
     */
    Response<AdUnitVO> createUnit(AdUnitDTO adUnitDTO);

    /**
     * 创建推广单元关键字
     *
     * @param adUnitKeywordDTO 推广单元关键字请求数据
     * @return 推广单元关键字展示数据
     * @throws AdException 广告系统异常
     */
    Response<AdUnitKeywordVO> createUnitKeyword(AdUnitKeywordDTO adUnitKeywordDTO);

    /**
     * 创建推广单元兴趣
     *
     * @param adUnitItDTO 推广单元兴趣请求数据
     * @return 推广单元兴趣展示数据
     * @throws AdException 广告系统异常
     */
    Response<AdUnitItVO> createUnitIt(AdUnitItDTO adUnitItDTO);

    /**
     * 创建推广单元区域
     *
     * @param adUnitDistrictDTO 推广单元区域请求数据
     * @return 推广单元区域展示数据
     * @throws AdException 广告系统异常
     */
    Response<AdUnitDistrictVO> createUnitDistrict(AdUnitDistrictDTO adUnitDistrictDTO);

    /**
     * 创建创意单元
     *
     * @param creativeUnitDTO 创意单元传入数据
     * @return 创意单元展示数据
     * @throws AdException 广告系统异常
     */
    Response<CreativeUnitVO> createCreativeUnit(CreativeUnitDTO creativeUnitDTO);

    /**
     * 获取所有广告单元
     *
     * @param userId 用户Id
     * @return 广告单元
     */
    Response<List<UserAdUnitVO>> getAllUnits(Long userId);

    /**
     * 更新广告单元
     *
     * @param adUnitDTO 广告单元
     * @return 更新后的信息
     */
    Response<AdUnitVO> updateUnit(AdUnitDTO adUnitDTO);

    /**
     * 获取广告推广单元的基本信息（id + name）
     *
     * @param userId
     * @return
     */
    Response<List<String>> getAllAdUnitMsg(Long userId);
}
