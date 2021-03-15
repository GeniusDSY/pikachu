package cn.edu.cqupt.pikachu.ad.index.keyword;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import cn.edu.cqupt.pikachu.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 21:33
 * @desc : 广告单元关键字索引
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

    /**
     * 关键字单元类缓存存储Map（正向索引）
     */
    private static Map<String, Set<Long>> keywordUnitMap;

    /**
     * 关键字单元类缓存存储Map（反向索引）
     */
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取索引
     *
     * @param key 索引的键
     * @return 索引值
     */
    @Override
    public Set<Long> get(String key) {

        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = keywordUnitMap.get(key);
        if (null == result) {
            return Collections.emptySet();
        }

        return result;
    }

    /**
     * 添加索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void add(String key, Set<Long> value) {

        Set<Long> unitIdSet = CommonUtils.getorCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }

        log.info("UnitKeywordIndex add -> key:{}, value:{}, keywordUnitMap:{}, unitKeywordMap:{}",
                key, value, keywordUnitMap, unitKeywordMap);
    }

    /**
     * 更新索引(通过先删除后add的方式进行操作)
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitKeywordIndex update -> {}",
                "unitKeyword index cannot support update,You can do this by deleting it first and adding it later");
    }

    /**
     * 删除索引
     *
     * @param key   索引的键
     * @param value
     */
    @Override
    public void delete(String key, Set<Long> value) {

        Set<Long> unitIds = CommonUtils.getorCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getorCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }

        log.info("UnitKeywordIndex delete -> key:{}, value:{}, keywordUnitMap:{}, unitKeywordMap:{}",
                key, value, keywordUnitMap, unitKeywordMap);
    }

    /**
     * 匹配广告单元是否存在与关键字集合中
     *
     * @param unitId 广告单元Id
     * @param keywords 关键字
     * @return 匹配结果
     */
    public boolean match(Long unitId, List<String> keywords) {
        if (unitKeywordMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {

            Set<String> unitKeywords = unitKeywordMap.get(unitId);

            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }

        return false;
    }

}
