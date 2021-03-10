package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.annotation.IgnoreResponseAdvice;
import cn.edu.cqupt.pikachu.ad.client.SponsorClient;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/8 12:18
 * @desc : 广告检索Controller
 */
@Slf4j
@RestController
public class SearchController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SponsorClient sponsorClient;

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("getAdPlansByRibbon")
    public Response<List<AdPlanVO>> getAdPlansByRebbion(AdPlanGetDTO adPlanGetDTO) {
        log.info("ad-search: SearchController getAdPlansByRebbion -> {}", JSON.toJSONString(adPlanGetDTO));
        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/adPlan/get",
                adPlanGetDTO,
                Response.class).getBody();
    }

    @IgnoreResponseAdvice
    @PostMapping("getAdPlans")
    public Response<List<AdPlanVO>> getAdPlans(AdPlanGetDTO adPlanGetDTO) {
        log.info("ad-search: SearchController getAdPlans -> {}", JSON.toJSONString(adPlanGetDTO));
        return sponsorClient.getAdPlans(adPlanGetDTO);
    }

}
