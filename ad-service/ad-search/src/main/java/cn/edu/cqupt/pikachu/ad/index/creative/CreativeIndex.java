package cn.edu.cqupt.pikachu.ad.index.creative;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author :DengSiYuan
 * @date :2021/3/12 12:38
 * @desc : 创意索引
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    /**
     * 创意对象类缓存
     */
    private static Map<Long, CreativeObject> creativeObjectMap;

    static {
        creativeObjectMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取广告Id所关联的创意对象
     *
     * @param adIds 广告Id列表
     * @return 创意对象列表
     */
    public List<CreativeObject> fetch(Collection<Long> adIds) {

        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }

        List<CreativeObject> result = new ArrayList<>();

        adIds.forEach(u -> {
            CreativeObject object = get(u);
            if (null == object) {
                log.error("ad-search:CreativeIndex fetch -> creativeObject not found: {}", u);
                return;
            }

            result.add(object);
        });

        return result;
    }

    /**
     * 获取索引
     *
     * @param key 索引的键
     * @return 索引值
     */
    @Override
    public CreativeObject get(Long key) {
        return creativeObjectMap.get(key);
    }

    /**
     * 添加索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void add(Long key, CreativeObject value) {

        creativeObjectMap.put(key, value);
        log.info("ad-search:CreativeIndex add -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);

    }

    /**
     * 更新索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void update(Long key, CreativeObject value) {

        log.info("ad-search:CreativeIndex before update -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);
        CreativeObject oldObject = creativeObjectMap.get(key);
        if (null == oldObject) {
            creativeObjectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("ad-search:CreativeIndex after update -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);

    }

    /**
     * 删除索引
     *
     * @param key   索引的键
     * @param value
     */
    @Override
    public void delete(Long key, CreativeObject value) {
        creativeObjectMap.remove(key);
        log.info("ad-search:CreativeIndex remove -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);
    }
}
