package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.*;
import cn.edu.cqupt.pikachu.ad.model.vo.*;

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
    AdUnitVO createUnit(AdUnitDTO adUnitDTO) throws AdException;

    /**
     * 创建推广单元关键字
     *
     * @param adUnitKeywordDTO 推广单元关键字请求数据
     * @return 推广单元关键字展示数据
     * @throws AdException 广告系统异常
     */
    AdUnitKeywordVO createUnitKeyword(AdUnitKeywordDTO adUnitKeywordDTO) throws AdException;

    /**
     * 创建推广单元兴趣
     *
     * @param adUnitItDTO 推广单元兴趣请求数据
     * @return 推广单元兴趣展示数据
     * @throws AdException 广告系统异常
     */
    AdUnitItVO createUnitIt(AdUnitItDTO adUnitItDTO) throws AdException;

    /**
     * 创建推广单元区域
     *
     * @param adUnitDistrictDTO 推广单元区域请求数据
     * @return 推广单元区域展示数据
     * @throws AdException 广告系统异常
     */
    AdUnitDistrictVO createUnitDistrict(AdUnitDistrictDTO adUnitDistrictDTO) throws AdException;

    /**
     * 创建创意单元
     *
     * @param creativeUnitDTO 创意单元传入数据
     * @return 创意单元展示数据
     * @throws AdException 广告系统异常
     */
    CreativeUnitVO createCreativeUnit(CreativeUnitDTO creativeUnitDTO) throws AdException;

}
