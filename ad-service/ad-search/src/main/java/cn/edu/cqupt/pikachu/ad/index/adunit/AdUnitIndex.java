package cn.edu.cqupt.pikachu.ad.index.adunit;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import cn.edu.cqupt.pikachu.ad.index.adplan.AdPlanObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 21:22
 * @desc : 广告单元索引
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {
    /**
     * 类缓存存储Map
     */
    private static Map<Long, AdUnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取索引
     *
     * @param key 索引的键
     * @return 索引值
     */
    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    /**
     * 添加索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("ad-search:AdUnitIndex add -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
        objectMap.put(key, value);
    }

    /**
     * 更新索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("ad-search:AdUnitIndex before update -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
        AdUnitObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("ad-search:AdUnitIndex after update -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
    }

    /**
     * 删除索引
     *
     * @param key   索引的键
     * @param value
     */
    @Override
    public void delete(Long key, AdUnitObject value) {

        log.info("ad-search:AdUnitIndex delete -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
        objectMap.remove(key);
    }


    /**
     * 匹配符合流量类型的广告单元Ids
     *
     * @param positionType 流量类型
     * @return 匹配结果
     */
    public Set<Long> match(Integer positionType) {

        Set<Long> adUnitIds = new HashSet<>();

        objectMap.forEach((k, v) -> {
            if (AdUnitObject.isAdSlotTypeOk(positionType, v.getPositionType())) {
                adUnitIds.add(k);
            }
        });

        return adUnitIds;
    }

    /**
     * 获取广告单元对象列表
     *
     * @param adUnitIds 广告单元Ids
     * @return 广告单元对象列表
     */
    public List<AdUnitObject> fetch(Collection<Long> adUnitIds) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptyList();
        }

        List<AdUnitObject> result = new ArrayList<>();

        adUnitIds.forEach(u -> {
            AdUnitObject o = get(u);
            if (null == o) {
                log.error("ad-search:AdUnitIndex fetch -> AdUnitObject not found: {}", u);
                return;
            }
            result.add(o);
        });

        return result;
    }
}
