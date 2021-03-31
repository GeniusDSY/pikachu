package cn.edu.cqupt.pikachu.ad.index.creativeunit;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import cn.edu.cqupt.pikachu.ad.index.adunit.AdUnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author :DengSiYuan
 * @date :2021/3/12 13:05
 * @desc : 创意推广单元索引
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    /**
     * 创意单元对象缓存{adId-unitId, CreativeUnitObject}
     */
    private static Map<String, CreativeUnitObject> creativeUnitObjectMap;

    /**
     * 创意单元Id缓存{adId, Set<unitId>}
     */
    private static Map<Long, Set<Long>> creativeUnitIdsMap;

    /**
     * 单元创意Id缓存{unitId, Set<adId>}
     */
    private static Map<Long, Set<Long>> unitCreativeIdsMap;

    static {
        creativeUnitObjectMap = new ConcurrentHashMap<>();
        creativeUnitIdsMap = new ConcurrentHashMap<>();
        unitCreativeIdsMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取索引
     *
     * @param key 索引的键
     * @return 索引值
     */
    @Override
    public CreativeUnitObject get(String key) {
        return creativeUnitObjectMap.get(key);
    }

    /**
     * 添加索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void add(String key, CreativeUnitObject value) {

        creativeUnitObjectMap.put(key, value);

        Set<Long> unitSet = creativeUnitIdsMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitIdsMap.put(value.getAdId(), unitSet);
        }
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet = unitCreativeIdsMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeIdsMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getAdId());

        log.info("ad-search:CreativeUnitIndex add -> key:{}, value:{}, creativeUnitObjectMap:{}", key, value, creativeUnitIdsMap);

    }

    /**
     * 更新索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("ad-search:CreativeUnitIndex update -> {}",
                "creativeUnit index cannot support update,You can do this by deleting it first and adding it later");
    }

    /**
     * 删除索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void delete(String key, CreativeUnitObject value) {

        creativeUnitObjectMap.remove(key);

        Set<Long> unitSet = creativeUnitIdsMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitSet)) {
            unitSet.remove(value.getUnitId());
        }

        Set<Long> creativeSet = unitCreativeIdsMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getAdId());
        }

        log.info("ad-search:CreativeUnitIndex delete -> key:{}, value:{}, creativeUnitObjectMap:{}", creativeUnitObjectMap);

    }

    /**
     * 选择广告
     *
     * @param unitObjects 广告单元数据对象
     * @return 符合创意的广告单元id列表
     */
    public List<Long> selectAds(List<AdUnitObject> unitObjects) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return Collections.emptyList();
        }

        List<Long> result = new ArrayList<>();

        for (AdUnitObject unitObject : unitObjects) {

            Set<Long> adIds = unitCreativeIdsMap.get(unitObject.getUnitId());
            if (CollectionUtils.isNotEmpty(adIds)) {

                result.addAll(adIds);
            }
        }

        return result;
    }
}
