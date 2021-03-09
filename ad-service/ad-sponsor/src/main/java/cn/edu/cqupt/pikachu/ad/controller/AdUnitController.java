package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.*;
import cn.edu.cqupt.pikachu.ad.model.vo.*;
import cn.edu.cqupt.pikachu.ad.service.IAdUnitService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :DengSiYuan
 * @date :2021/2/24 18:08
 * @desc : 推广单元Controller
 */
@Slf4j
@RestController
@RequestMapping("adUnit")
public class AdUnitController {

    @Resource
    private IAdUnitService adUnitService;

    @PostMapping("create/unit")
    public AdUnitVO createUnit(AdUnitDTO adUnitDTO) throws AdException{
        log.info("ad-sponsor: AdUnitController createUnit -> {}", JSON.toJSONString(adUnitDTO));
        return adUnitService.createUnit(adUnitDTO);
    }

    @PostMapping("create/unitKeyword")
    public AdUnitKeywordVO createUnitKeyword(AdUnitKeywordDTO adUnitKeywordDTO) throws AdException {
        log.info("ad-sponsor: AdUnitController createUnitKeyword -> {}", JSON.toJSONString(adUnitKeywordDTO));
        return adUnitService.createUnitKeyword(adUnitKeywordDTO);
    }

    @PostMapping("create/unitIt")
    public AdUnitItVO createUnitIt(AdUnitItDTO adUnitItDTO) throws AdException {
        log.info("ad-sponsor: AdUnitController createUnitIt -> {}", JSON.toJSONString(adUnitItDTO));
        return adUnitService.createUnitIt(adUnitItDTO);
    }

    @PostMapping("create/unitDistrict")
    public AdUnitDistrictVO createUnitDistrict(AdUnitDistrictDTO adUnitDistrictDTO) throws AdException {
        log.info("ad-sponsor: AdUnitController createUnitDistrict -> {}", JSON.toJSONString(adUnitDistrictDTO));
        return adUnitService.createUnitDistrict(adUnitDistrictDTO);
    }

    @PostMapping("create/creativeUnit")
    public CreativeUnitVO createCreativeUnit(CreativeUnitDTO creativeUnitDTO) throws AdException {
        log.info("ad-sponsor: AdUnitController createCreativeUnit -> {}", JSON.toJSONString(creativeUnitDTO));
        return adUnitService.createCreativeUnit(creativeUnitDTO);
    }

}
