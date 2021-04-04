package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.SponsorApplication;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.mockdata.MockData;
import cn.edu.cqupt.pikachu.ad.service.IAdPlanService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author :DengSiYuan
 * @date :2021/4/2 18:59
 * @desc : 广告单元服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SponsorApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Resource
    private IAdPlanService planService;

    @Test
    @Transactional
    public void testCreateAdPlan() throws AdException {
        Assert.assertNotNull(planService.createAdPlan(MockData.AD_PLAN_DTO));
    }

    @Test
    public void testGetAdPlanByIds() throws AdException {
        Assert.assertNotNull(planService.getAdPlanByIds(MockData.AD_PLAN_GET_DTO));
    }

    @Test
    @Transactional
    public void testUpdateAdPlan() throws AdException {
        Assert.assertNotNull(planService.updateAdPlan(MockData.AD_PLAN_DTO));
    }

    @Test
    @Transactional
    public void testDeleteAdPlan() throws AdException {
        planService.deleteAdPlan(MockData.AD_PLAN_DTO);
    }
}