package cn.edu.cqupt.pikachu.ad.service;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdPlanRepository;
import cn.edu.cqupt.pikachu.ad.dao.AdUnitRepository;
import cn.edu.cqupt.pikachu.ad.dao.CreativeRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitDistrictRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitItRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitKeywordRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.CreativeUnitRepository;
import cn.edu.cqupt.pikachu.ad.dump.table.AdCreativeTable;
import cn.edu.cqupt.pikachu.ad.dump.table.AdPlanTable;
import cn.edu.cqupt.pikachu.ad.dump.table.AdUnitTable;
import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import cn.edu.cqupt.pikachu.ad.model.entity.Creative;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/20 16:20
 * @desc : Dump数据服务
 */
@Slf4j
@Service
public class DumpDataService {

    @Resource
    private AdPlanRepository planRepository;

    @Resource
    private AdUnitRepository unitRepository;

    @Resource
    private CreativeRepository creativeRepository;

    @Resource
    private CreativeUnitRepository creativeUnitRepository;

    @Resource
    private AdUnitDistrictRepository districtRepository;

    @Resource
    private AdUnitItRepository itRepository;

    @Resource
    private AdUnitKeywordRepository keywordRepository;

    /**
     * dump广告计划数据表
     *
     * @param fileName 文件名称
     */
    private void dumpAdPlanTable(String fileName) {

        List<AdPlan> adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }

        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(p -> planTables.add(
                new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("ad-exprot:DumpDataService dumpAdPlanTable error -> {}", e.fillInStackTrace());
        }
    }

    /**
     * dump广告单元数据表
     *
     * @param fileName 文件名称
     */
    private void dumpAdUnitTable(String fileName) {

        List<AdUnit> adUnits = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }

        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(
                new AdUnitTable(u.getId(), u.getUnitStatus(), u.getPositionType(), u.getPlanId())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("ad-export:DumpDataService dumpAdUnitTable error -> {}", e.fillInStackTrace());
        }
    }

    /**
     * dump广告创意表
     *
     * @param fileName 文件名称
     */
    private void dumpAdCreativeTable(String fileName) {

        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(),
                        c.getHeight(), c.getWidth(), c.getAuditStatus(), c.getUrl())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("ad-export:DumpDataService dumpAdCreativeTable error -> {}", e.fillInStackTrace());
        }
    }

}
