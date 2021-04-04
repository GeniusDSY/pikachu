package cn.edu.cqupt.pikachu.ad.mockdata;

import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.AdPlanGetDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.AdUnitDTO;
import cn.edu.cqupt.pikachu.ad.model.dto.AdUnitKeywordDTO;
import com.alibaba.fastjson.JSON;

/**
 * @author :DengSiYuan
 * @date :2021/4/2 19:09
 * @desc : 单测mock数据
 */
public class MockData {

    /**
     * AdPlanGetDTO
     */
    public static final AdPlanGetDTO AD_PLAN_GET_DTO = JSON.parseObject("{\"userId\":15,\"ids\":[10]}", AdPlanGetDTO.class);

    /**
     * AdPlanDTO
     */
    public static final AdPlanDTO AD_PLAN_DTO = JSON.parseObject("{\"id\":10,\"userId\":15,\"planName\":\"测试计划\",\"startDate\":\"2018-11-28 00:00:00\",\"endDate\":\"2019-11-20 00:00:00\"}", AdPlanDTO.class);

    /**
     * AdUnitDTO
     */
    public static final AdUnitDTO AD_UNIT_DTO = JSON.parseObject("{\"planId\":10,\"unitName\":\"测试单元\",\"positionType\":1,\"budget\":\"10000000\"}", AdUnitDTO.class);

    /**
     * AdUnitKeywordDTO
     */
    public static final AdUnitKeywordDTO AD_UNIT_KEYWORD_DTO = JSON.parseObject("{\"unitKeywords\":[\"宝马\",\"奥迪\"]}", AdUnitKeywordDTO.class);
}
