package cn.edu.cqupt.pikachu.ad.index.district;

import cn.edu.cqupt.pikachu.ad.index.IndexAware;
import cn.edu.cqupt.pikachu.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author :DengSiYuan
 * @date :2021/3/10 20:08
 * @desc : 推广单元地域索引
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    /**
     * {province-city, unitId}
     */
    private static Map<String, Set<Long>> districtUnitMap;

    /**
     * {unitId, province-city}
     */
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取索引
     *
     * @param key 索引的键
     * @return 索引值
     */
    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    /**
     * 添加索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void add(String key, Set<Long> value) {
        Set<Long> unitIds = CommonUtils.getorCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districts.add(key);
        }

        log.info("UnitDistrictIndex add -> key:{}, value:{}", key, value,
                "districtUnitMap:{}", districtUnitMap,
                "unitDistrictMap:{}", unitDistrictMap);
    }

    /**
     * 更新索引
     *
     * @param key   索引的键
     * @param value 索引的值
     */
    @Override
    public void update(String key, Set<Long> value) {

        log.error("UnitDistrictIndex update -> {}",
                "keyword index cannot support update,You can do this by deleting it first and adding it later");
    }

    /**
     * 删除索引
     *
     * @param key   索引的键
     * @param value
     */
    @Override
    public void delete(String key, Set<Long> value) {

        Set<Long> unitIds = CommonUtils.getorCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districts.remove(key);
        }

        log.info("UnitDistrictIndex delete -> key:{}, value:{}", key, value,
                "districtUnitMap:{}", districtUnitMap,
                "unitDistrictMap:{}", unitDistrictMap);
    }
}
