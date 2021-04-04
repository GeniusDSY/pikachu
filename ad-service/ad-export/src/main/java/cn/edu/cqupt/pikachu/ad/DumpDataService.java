package cn.edu.cqupt.pikachu.ad;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdPlanRepository;
import cn.edu.cqupt.pikachu.ad.dao.AdUnitRepository;
import cn.edu.cqupt.pikachu.ad.dao.CreativeRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitDistrictRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitItRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitKeywordRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.CreativeUnitRepository;
import cn.edu.cqupt.pikachu.ad.dump.DConstant;
import cn.edu.cqupt.pikachu.ad.dump.table.*;
import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import cn.edu.cqupt.pikachu.ad.model.entity.Creative;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitDistrict;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitIt;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitKeyword;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.CreativeUnit;
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
     * dump数据表信息
     */
    public void dumpAdTableData() {

        dumpAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT));
        dumpAdCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_CREATIVE));
        dumpAdCreativeUnitTable(String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_CREATIVE_UNIT));
        dumpAdUnitDistrictTable(String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT_DISTRICT));
        dumpAdUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT_IT));
        dumpAdUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_STR, DConstant.AD_UNIT_KEYWORD));
    }

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

    /**
     * dump广告创意和广告单元关联表
     *
     * @param fileName 文件名称
     */
    private void dumpAdCreativeUnitTable(String fileName) {

        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }

        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new AdCreativeUnitTable(
                        c.getCreativeId(),
                        c.getUnitId()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
        } catch (IOException ex) {
            log.error("ad-export:DumpDataService dumpAdCreativeUnitTable error -> {}\", e.fillInStackTrace()");
        }
    }

    /**
     * dump广告单元区域影响因素表
     *
     * @param fileName 文件名称
     */
    private void dumpAdUnitDistrictTable(String fileName) {

        List<AdUnitDistrict> unitDistricts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        d.getUnitId(),
                        d.getProvince(),
                        d.getCity()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
        } catch (IOException ex) {
            log.error("ad-export:DumpDataService dumpAdUnitDistrictTable error -> {}\", e.fillInStackTrace()");
        }
    }

    /**
     * dump广告单元兴趣爱好影响因素表
     *
     * @param fileName 文件名称
     */
    private void dumpAdUnitItTable(String fileName) {

        List<AdUnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }

        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(i -> unitItTables.add(
                new AdUnitItTable(
                        i.getUnitId(),
                        i.getItTag()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
        } catch (IOException ex) {
            log.error("ad-export:DumpDataService dumpAdUnitItTable error -> {}\", e.fillInStackTrace()");
        }
    }

    /**
     * dump广告单元关键字影响因素表
     *
     * @param fileName 文件名称
     */
    private void dumpAdUnitKeywordTable(String fileName) {

        List<AdUnitKeyword> unitKeywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(k -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        k.getUnitId(),
                        k.getKeyword()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
        } catch (IOException ex) {
            log.error("ad-export:DumpDataService dumpAdUnitKeywordTable error -> {}\", e.fillInStackTrace()");
        }
    }

}
