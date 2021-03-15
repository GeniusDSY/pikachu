package cn.edu.cqupt.pikachu.ad.index.adunit;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import cn.edu.cqupt.pikachu.ad.index.adplan.AdPlanObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
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
        log.info("AdUnitIndex add -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
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
        log.info("AdUnitIndex before update -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
        AdUnitObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("AdUnitIndex after update -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
    }

    /**
     * 删除索引
     *
     * @param key   索引的键
     * @param value
     */
    @Override
    public void delete(Long key, AdUnitObject value) {

        log.info("AdUnitIndex delete -> key:{}, value:{}, indexMap:{}", key, value, objectMap);
        objectMap.remove(key);
    }
}
