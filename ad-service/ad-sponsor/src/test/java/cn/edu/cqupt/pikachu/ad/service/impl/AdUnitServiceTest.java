package cn.edu.cqupt.pikachu.ad.service.impl;

import cn.edu.cqupt.pikachu.ad.SponsorApplication;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.mockdata.MockData;
import cn.edu.cqupt.pikachu.ad.model.dto.AdUnitKeywordDTO;
import cn.edu.cqupt.pikachu.ad.service.IAdUnitService;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author :DengSiYuan
 * @date :2021/4/2 20:13
 * @desc : 广告单元服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SponsorApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdUnitServiceTest {

    @Resource
    private IAdUnitService unitService;

    @Test
    @Transactional
    public void testCreateUnit() throws AdException {
        Assert.assertNotNull(unitService.createUnit(MockData.AD_UNIT_DTO));
    }

    @Test
    @Transactional
    public void createUnitKeyword() throws AdException {
        //Assert.assertNotNull(unitService.createUnitKeyword(MockData.AD_UNIT_KEYWORD_DTO));
    }

    @Test
    @Transactional
    public void createUnitIt() {
    }

    @Test
    @Transactional
    public void createUnitDistrict() {
    }

    @Test
    @Transactional
    public void createCreativeUnit() {
    }
}