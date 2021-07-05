package cn.edu.cqupt.pikachu.ad.index;

import cn.edu.cqupt.pikachu.ad.DumpDataService;
import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import cn.edu.cqupt.pikachu.ad.dao.AdPlanRepository;
import cn.edu.cqupt.pikachu.ad.dao.AdUnitRepository;
import cn.edu.cqupt.pikachu.ad.dao.CreativeRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitDistrictRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitItRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.AdUnitKeywordRepository;
import cn.edu.cqupt.pikachu.ad.dao.unit_condition.CreativeUnitRepository;
import cn.edu.cqupt.pikachu.ad.dump.table.*;
import cn.edu.cqupt.pikachu.ad.handler.AdLevelDataHandler;
import cn.edu.cqupt.pikachu.ad.model.entity.AdPlan;
import cn.edu.cqupt.pikachu.ad.model.entity.AdUnit;
import cn.edu.cqupt.pikachu.ad.model.entity.Creative;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitDistrict;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitIt;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.AdUnitKeyword;
import cn.edu.cqupt.pikachu.ad.model.entity.unit_condition.CreativeUnit;
import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/23 14:19
 * @desc : 索引文件加载
 */
@Slf4j
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @Resource
    private DumpDataService dumpDataService;

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
     * 初始化加载数据索引
     */
    @PostConstruct
    public void init() {

        // 首先dump索引数据
        dumpDataService.dumpAdTableData();

        // 广告计划序列化数据加载
        List<AdPlanTable> planTables = loadAdPlanTableData();
        if (CollectionUtils.isNotEmpty(planTables)) {
            planTables.forEach(p -> AdLevelDataHandler.handleLevel2(p, OpType.ADD));
        }

        // 广告单元序列化数据加载
        List<AdUnitTable> unitTables = loadAdUnitTableData();
        if (CollectionUtils.isNotEmpty(unitTables)) {
            unitTables.forEach(u -> AdLevelDataHandler.handleLevel3(u, OpType.ADD));
        }

        // 广告创意序列化数据加载
        List<AdCreativeTable> creativeTables = loadAdCreativeTableData();
        if (CollectionUtils.isNotEmpty(creativeTables)) {
            creativeTables.forEach(c -> AdLevelDataHandler.handleLevel2(c, OpType.ADD));
        }

        // 广告创意单元序列化数据加载
        List<AdCreativeUnitTable> creativeUnitTables = loadAdCreativeUnitTableData();
        if (CollectionUtils.isNotEmpty(creativeUnitTables)) {
            creativeUnitTables.forEach(cu -> AdLevelDataHandler.handleLevel3(cu, OpType.ADD));
        }

        // 广告单元区域影响因素序列化数据加载
        List<AdUnitDistrictTable> unitDistrictTables = loadAdUnitDistrictTableData();
        if (CollectionUtils.isNotEmpty(unitDistrictTables)) {
            unitDistrictTables.forEach(ud -> AdLevelDataHandler.handleLevel4(ud, OpType.ADD));
        }

        // 广告单元兴趣爱好影响因素序列化数据加载
        List<AdUnitItTable> unitItTables = loadAdUnitItTableData();
        if (CollectionUtils.isNotEmpty(unitItTables)) {
            unitItTables.forEach(ui -> AdLevelDataHandler.handleLevel4(ui, OpType.ADD));
        }

        // 广告单元关键字影响因素序列化数据加载
        List<AdUnitKeywordTable> unitKeywordTables = loadAdUnitKeywordTableData();
        if (CollectionUtils.isNotEmpty(unitKeywordTables)) {
            unitKeywordTables.forEach(uk -> AdLevelDataHandler.handleLevel4(uk, OpType.ADD));
        }
    }

    /**
     * 加载广告计划数据表数据
     *
     * @return 广告计划数据表数据列表
     */
    private List<AdPlanTable> loadAdPlanTableData() {

        List<AdPlan> adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlans)) {
            return Collections.emptyList();
        }

        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(p -> planTables.add(
                new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate())
        ));
        return planTables;
    }

    /**
     * 加载广告单元数据表数据
     *
     * @return 广告单元数据表数据列表
     */
    private List<AdUnitTable> loadAdUnitTableData() {

        List<AdUnit> adUnits = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (org.springframework.util.CollectionUtils.isEmpty(adUnits)) {
            return Collections.emptyList();
        }

        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(
                new AdUnitTable(u.getId(), u.getUnitStatus(), u.getPositionType(), u.getPlanId())
        ));
        return unitTables;
    }

    /**
     * 加载广告创意数据表数据
     *
     * @return 广告创意数据表数据列表
     */
    private List<AdCreativeTable> loadAdCreativeTableData() {

        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }

        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(),
                        c.getHeight(), c.getWidth(), c.getAuditStatus(), c.getAdContents(), c.getUrl())
        ));
        return creativeTables;
    }

    /**
     * 加载广告创意广告单元关联数据表数据
     *
     * @return 广告创意广告单元关联数据表数据列表
     */
    private List<AdCreativeUnitTable> loadAdCreativeUnitTableData() {

        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return Collections.emptyList();
        }

        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new AdCreativeUnitTable(
                        c.getCreativeId(),
                        c.getUnitId()
                )
        ));
        return creativeUnitTables;
    }

    /**
     * 加载广告单元地域影响因素数据表数据
     *
     * @return 广告单元地域影响因素数据表数据列表
     */
    private List<AdUnitDistrictTable> loadAdUnitDistrictTableData() {

        List<AdUnitDistrict> unitDistricts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return Collections.emptyList();
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        d.getUnitId(),
                        d.getProvince(),
                        d.getCity()
                )
        ));
        return unitDistrictTables;
    }

    /**
     * 加载广告单元兴趣爱好影响因素数据表数据
     *
     * @return 广告单元兴趣爱好影响因素数据表数据列表
     */
    private List<AdUnitItTable> loadAdUnitItTableData() {

        List<AdUnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return Collections.emptyList();
        }

        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(i -> unitItTables.add(
                new AdUnitItTable(
                        i.getUnitId(),
                        i.getItTag()
                )
        ));
        return unitItTables;
    }

    /**
     * 加载广告单元关键字影响因素数据表数据
     *
     * @return 广告单元关键字影响因素数据表数据列表
     */
    private List<AdUnitKeywordTable> loadAdUnitKeywordTableData() {

        List<AdUnitKeyword> unitKeywords = keywordRepository.findAll();
        if (org.springframework.util.CollectionUtils.isEmpty(unitKeywords)) {
            return Collections.emptyList();
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(k -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        k.getUnitId(),
                        k.getKeyword()
                )
        ));
        return unitKeywordTables;
    }
    /*
     *//**
     * 加载dump的数据
     *
     * @param fileName 文件名称
     * @return 数据
     *//*
    private List<String> loadDumpData(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(fileName)
        )) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            log.error("ad-search:IndexFileLoader loadDumpData -> load error:{}", e.getMessage());
            return Collections.emptyList();
        }
    }*/
}
