package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.model.dto.*;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import cn.edu.cqupt.pikachu.ad.model.vo.*;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.IAdUnitService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public Response<AdUnitVO> createUnit(@RequestBody AdUnitDTO adUnitDTO) {
        log.info("ad-sponsor: AdUnitController createUnit -> {}", JSON.toJSONString(adUnitDTO));
        return adUnitService.createUnit(adUnitDTO);
    }

    @PutMapping("update/unit")
    public Response<AdUnitVO> updateUnit(@RequestBody AdUnitDTO adUnitDTO) {
        log.info("ad-sponsor: AdUnitController updateUnit -> {}", JSON.toJSONString(adUnitDTO));
        return adUnitService.updateUnit(adUnitDTO);
    }

    @GetMapping("get/units")
    public Response<List<UserAdUnitVO>> getAllUnits(Long userId) {
        log.info("ad-sponsor: AdUnitController getAllUnits -> {}", userId);
        return adUnitService.getAllUnits(userId);
    }

    @PostMapping("create/unitKeyword")
    public Response<AdUnitKeywordVO> createUnitKeyword(@RequestBody AdUnitKeywordDTO adUnitKeywordDTO) {
        log.info("ad-sponsor: AdUnitController createUnitKeyword -> {}", JSON.toJSONString(adUnitKeywordDTO));
        return adUnitService.createUnitKeyword(adUnitKeywordDTO);
    }

    @PostMapping("create/unitIt")
    public Response<AdUnitItVO> createUnitIt(@RequestBody AdUnitItDTO adUnitItDTO) {
        log.info("ad-sponsor: AdUnitController createUnitIt -> {}", JSON.toJSONString(adUnitItDTO));
        return adUnitService.createUnitIt(adUnitItDTO);
    }

    @PostMapping("create/unitDistrict")
    public Response<AdUnitDistrictVO> createUnitDistrict(@RequestBody AdUnitDistrictDTO adUnitDistrictDTO) {
        log.info("ad-sponsor: AdUnitController createUnitDistrict -> {}", JSON.toJSONString(adUnitDistrictDTO));
        return adUnitService.createUnitDistrict(adUnitDistrictDTO);
    }

    @PostMapping("create/creativeUnit")
    public Response<CreativeUnitVO> createCreativeUnit(@RequestBody CreativeUnitDTO creativeUnitDTO) {
        log.info("ad-sponsor: AdUnitController createCreativeUnit -> {}", JSON.toJSONString(creativeUnitDTO));
        return adUnitService.createCreativeUnit(creativeUnitDTO);
    }

    @GetMapping("getAdUnitMsg")
    public Response<List<String>> getAllAdUnitMsg(Long userId) {
        log.info("ad-sponsor: AdUnitController getAllAdUnitMsg -> {}", userId);
        return adUnitService.getAllAdUnitMsg(userId);
    }

}
