package cn.edu.cqupt.pikachu.ad.client;

import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 14:14
 * @desc : 投放客户端
 */
@FeignClient(value = "ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    /**
     * 获取广告投放系统获取广告投放计划
     *
     * @param adPlanGetDTO 广告计划获取请求数据
     * @return 广告投放计划信息列表
     */
    @PostMapping("/ad-sponsor/adPlan/get")
    Response<List<AdPlanVO>> getAdPlans(@RequestBody AdPlanGetDTO adPlanGetDTO);

}
