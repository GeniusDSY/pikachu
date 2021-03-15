package cn.edu.cqupt.pikachu.ad.index.creative;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
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
        log.info("CreativeIndex add -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);

    }

    /**
     * 更新索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void update(Long key, CreativeObject value) {

        log.info("CreativeIndex before update -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);
        CreativeObject oldObject = creativeObjectMap.get(key);
        if (null == oldObject) {
            creativeObjectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("CreativeIndex after update -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);

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
        log.info("CreativeIndex remove -> key:{}, value:{}, creativeObjectMap:{}", key, value, creativeObjectMap);
    }
}
