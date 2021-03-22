package cn.edu.cqupt.pikachu.ad.handler;

import cn.edu.cqupt.pikachu.ad.dump.table.*;
import cn.edu.cqupt.pikachu.ad.index.DataTable;
import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import cn.edu.cqupt.pikachu.ad.index.adplan.AdPlanIndex;
import cn.edu.cqupt.pikachu.ad.index.adplan.AdPlanObject;
import cn.edu.cqupt.pikachu.ad.index.adunit.AdUnitIndex;
import cn.edu.cqupt.pikachu.ad.index.adunit.AdUnitObject;
import cn.edu.cqupt.pikachu.ad.index.creative.CreativeIndex;
import cn.edu.cqupt.pikachu.ad.index.creative.CreativeObject;
import cn.edu.cqupt.pikachu.ad.index.creativeunit.CreativeUnitIndex;
import cn.edu.cqupt.pikachu.ad.index.creativeunit.CreativeUnitObject;
import cn.edu.cqupt.pikachu.ad.index.district.UnitDistrictIndex;
import cn.edu.cqupt.pikachu.ad.index.interest.UnitItIndex;
import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import cn.edu.cqupt.pikachu.ad.utils.CommonUtils;
import cn.edu.cqupt.pikachu.ad.utils.ConvertUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author :DengSiYuan
 * @date :2021/3/22 13:52
 * @desc : 广告数据处理器（1、索引之间存在着层级的划分，也就是依赖关系的划分；
 * 2、加载全量索引其实是增量索引”添加“的一种特殊实现）
 */
@Slf4j
public class AdLevelDataHandler {

    /**
     * 广告计划数据第二层级索引
     *
     * @param adPlanTable 广告计划数据表
     * @param type        操作类型
     */
    public static void handleLevel2(AdPlanTable adPlanTable, OpType type) {

        AdPlanObject planObject = ConvertUtils.adPlanTable2AdPlanObject(adPlanTable);
        handleBinLogEvent(
                DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    /**
     * 广告创意数据第二层级索引
     *
     * @param creativeTable 创意数据表
     * @param type          操作类型
     */
    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {

        CreativeObject creativeObject = ConvertUtils.creativeTable2CreativeObject(creativeTable);
        handleBinLogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

    /**
     * 广告单元第三层级索引
     *
     * @param unitTable 广告单元数据表
     * @param type      操作类型
     */
    public static void handleLevel3(AdUnitTable unitTable, OpType type) {

        AdPlanObject adPlanObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (null == adPlanObject) {
            log.error("AdLevelDataHandler handleLevel3 found AdUnitTable index error -> planId={}",
                    unitTable.getPlanId());
            return;
        }

        AdUnitObject unitObject = ConvertUtils.adUnitTable2AdUnitObject(unitTable, adPlanObject);
        handleBinLogEvent(
                DataTable.of(AdUnitIndex.class),
                unitObject.getPlanId(),
                unitObject,
                type
        );
    }

    /**
     * 广告创意单元关系第三层级索引
     *
     * @param creativeUnitTable 创意单元数据表
     * @param type              操作类型
     */
    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {

        // 不支持更新，更新会打出更新日志
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());

        if (null == unitObject || null == creativeObject) {
            log.error("AdLevelDataHandler handleLevel3 found AdCreativeUnitTable index error -> {}",
                    JSON.toJSONString(creativeUnitTable));
            return;
        }

        CreativeUnitObject creativeUnitObject = ConvertUtils.creativeTable2CreativeUnitObject(creativeUnitTable);
        handleBinLogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(String.valueOf(creativeUnitObject.getAdId()),
                        String.valueOf(creativeUnitObject.getUnitId())),
                creativeUnitObject,
                type
        );
    }

    /**
     * 地域维度第四层级索引
     *
     * @param districtTable 地域数据表
     * @param type          操作类型
     */
    public static void handlevel4(AdUnitDistrictTable districtTable, OpType type) {

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(districtTable.getUnitId());
        if (null == unitObject) {
            log.error("AdLevelDataHandler handleLevel4 found AdUnitDistrictTable index error -> unitId={}",
                    districtTable.getUnitId());
        }

        String key = CommonUtils.stringConcat(districtTable.getProvince(), districtTable.getCity());
        Set<Long> value = new HashSet<>(Collections.singleton(districtTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    /**
     * 兴趣爱好第四层级索引
     *
     * @param itTable 兴趣爱好数据表
     * @param type    操作类型
     */
    public static void handleLevel4(AdUnitItTable itTable, OpType type) {

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(itTable.getUnitId());
        if (null == unitObject) {
            log.error("AdLevelDataHandler handleLevel4 found AdUnitItTable index error -> unitId={}",
                    itTable.getUnitId());
        }

        Set<Long> value = new HashSet<>(Collections.singleton(itTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitItIndex.class), itTable.getItTag(), value, type);
    }

    /**
     * 关键字第四层级索引
     *
     * @param keywordTable 关键字数据表
     * @param type         操作类型
     */
    public static void handleLevel4(AdKeywordTable keywordTable, OpType type) {

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (null == unitObject) {
            log.error("AdLevelDataHandler handleLevel4 found AdKeywordTable index error -> unitId={}",
                    keywordTable.getUnitId());
        }

        Set<Long> value = new HashSet<>(Collections.singleton(keywordTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitItIndex.class), keywordTable.getKeyword(), value, type);
    }

    /**
     * 处理全量索引、增量索引的处理过程
     *
     * @param index 索引
     * @param key   键
     * @param value 值
     * @param type  操作类型
     * @param <K>   键类型
     * @param <V>   值类型
     */
    private static <K, V> void handleBinLogEvent(IndexAware<K, V> index, K key, V value, OpType type) {

        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;

        }
    }
}
