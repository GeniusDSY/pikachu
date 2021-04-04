package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.service.IAdPlanService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/17 20:36
 * @desc : 广告计划Controller
 */
@Slf4j
@RestController
@RequestMapping("adPlan")
public class AdPlanController {

    @Resource
    private IAdPlanService adPlanService;

    @PostMapping("create")
    public Response<AdPlanVO> createAdPlan(@RequestBody AdPlanDTO adPlanDTO) throws AdException {
        log.info("ad-sponsor: AdPlanController createAdPlan -> {}", JSON.toJSONString(adPlanDTO));
        return adPlanService.createAdPlan(adPlanDTO);
    }

    @PostMapping("get")
    public Response<List<AdPlanVO>> getAdPlanByIds(@RequestBody AdPlanGetDTO adPlanGetDTO) {
        log.info("ad-sponsor: AdPlanController getAdPlanByIds -> {}", JSON.toJSONString(adPlanGetDTO));
        return adPlanService.getAdPlanByIds(adPlanGetDTO);
    }

    @PutMapping("update")
    public Response<AdPlanVO> updateAdPlan(@RequestBody AdPlanDTO adPlanDTO) throws AdException {
        log.info("ad-sponsor: AdPlanController updateAdPlan -> {}", JSON.toJSONString(adPlanDTO));
        return adPlanService.updateAdPlan(adPlanDTO);
    }

    @DeleteMapping("delete")
    public void deleteAdPlan(@RequestBody AdPlanDTO adPlanDTO) throws AdException {
        log.info("ad-sponsor: AdPlanController deleteAdPlan -> {}", JSON.toJSONString(adPlanDTO));
        adPlanService.deleteAdPlan(adPlanDTO);
    }

}
