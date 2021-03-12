package cn.edu.cqupt.pikachu.ad.index.adplan;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 17:00
 * @desc : 广告推广计划索引实现
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    /**
     * 类缓存存储Map
     */
    private static Map<Long, AdPlanObject> objectMap;

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
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    /**
     * 添加索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("AdPlanIndex add -> key:{}, value:{}", key, value, "indexMap:{}", objectMap);
        objectMap.put(key, value);
    }

    /**
     * 更新索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("AdPlanIndex update -> key:{}, value:{}", key, value, "indexMap:{}", objectMap);
        AdPlanObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("AdPlanIndex update -> key:{}, value:{}", key, value, "indexMap:{}", objectMap);
    }

    /**
     * 删除索引
     *
     * @param key   索引的键
     * @param value
     */
    @Override
    public void delete(Long key, AdPlanObject value) {

        log.info("AdPlanIndex delete -> key:{}, value:{}", key, value, "indexMap:{}", objectMap);
        objectMap.remove(key);
    }
}
