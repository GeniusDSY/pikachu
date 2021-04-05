package cn.edu.cqupt.pikachu.ad.client;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.vo.AdPlanVO;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 14:37
 * @desc : 投放客户端断路器
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public Response<List<AdPlanVO>> getAdPlans(AdPlanGetDTO adPlanGetDTO) {
        return new Response<>(ResultStatus.SPONSOR_ERROR);
    }
}
