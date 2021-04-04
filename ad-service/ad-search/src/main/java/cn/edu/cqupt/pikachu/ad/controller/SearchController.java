package cn.edu.cqupt.pikachu.ad.controller;

import cn.edu.cqupt.pikachu.ad.annotation.IgnoreResponseAdvice;
import cn.edu.cqupt.pikachu.ad.client.SponsorClient;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.search.ISearch;
import cn.edu.cqupt.pikachu.ad.search.vo.SearchRequest;
import cn.edu.cqupt.pikachu.ad.search.vo.SearchResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final SponsorClient sponsorClient;

    private final ISearch search;

    @Autowired
    public SearchController(ISearch search, SponsorClient sponsorClient) {
        this.search = search;
        this.sponsorClient = sponsorClient;
    }

    @SuppressWarnings("all")
    @PostMapping("getAdPlansByRibbon")
    public Response<List<AdPlanVO>> getAdPlansByRebbion(@RequestBody AdPlanGetDTO adPlanGetDTO) {
        log.info("ad-search: SearchController getAdPlansByRebbion -> {}", JSON.toJSONString(adPlanGetDTO));
        return restTemplate.postForEntity(
                "http://ad-sponsor/ad-sponsor/adPlan/get",
                adPlanGetDTO,
                Response.class).getBody();
    }

    @PostMapping("getAdPlans")
    public Response<List<AdPlanVO>> getAdPlans(@RequestBody AdPlanGetDTO adPlanGetDTO) {
        log.info("ad-search: SearchController getAdPlans -> {}", JSON.toJSONString(adPlanGetDTO));
        return sponsorClient.getAdPlans(adPlanGetDTO);
    }

    @PostMapping("fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("ad-search: SearchController fetchAds -> {}", JSON.toJSONString(request));
        return search.fetchAds(request);

    }

}
