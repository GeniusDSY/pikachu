package cn.edu.cqupt.pikachu.ad.utils;

import cn.edu.cqupt.pikachu.ad.dump.table.AdCreativeTable;
import cn.edu.cqupt.pikachu.ad.dump.table.AdCreativeUnitTable;
import cn.edu.cqupt.pikachu.ad.dump.table.AdPlanTable;
import cn.edu.cqupt.pikachu.ad.dump.table.AdUnitTable;
import cn.edu.cqupt.pikachu.ad.index.adplan.AdPlanObject;
import cn.edu.cqupt.pikachu.ad.index.adunit.AdUnitObject;
import cn.edu.cqupt.pikachu.ad.index.creative.CreativeObject;
import cn.edu.cqupt.pikachu.ad.index.creativeunit.CreativeUnitObject;

/**
 * @author :DengSiYuan
 * @date :2021/3/22 19:27
 * @desc : 转换工具类
 */
public class ConvertUtils {

    /**
     * AdPlanTable 转化为 AdPlanObject
     *
     * @param adPlanTable 广告计划数据表
     * @return AdPlanObject
     */
    public static AdPlanObject adPlanTable2AdPlanObject(AdPlanTable adPlanTable) {

        return new AdPlanObject(adPlanTable.getId(),
                adPlanTable.getUserId(),
                adPlanTable.getPlanStatus(),
                adPlanTable.getStartDate(),
                adPlanTable.getEndDate());
    }

    /**
     * CreativeTable 转化为 CreativeObject
     *
     * @param creativeTable 创意数据表
     * @return CreativeObject
     */
    public static CreativeObject creativeTable2CreativeObject(AdCreativeTable creativeTable) {

        return new CreativeObject(creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdContents(),
                creativeTable.getAdUrl());
    }

    /**
     * AdUnitTable 转化为 AdUnitObject
     *
     * @param unitTable  广告单元数据表
     * @param planObject 广告计划数据对象
     * @return AdUnitObject
     */
    public static AdUnitObject adUnitTable2AdUnitObject(AdUnitTable unitTable, AdPlanObject planObject) {

        return new AdUnitObject(unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                planObject);

    }

    /**
     * AdCreativeUnitTable 转化为 CreativeUnitObject
     *
     * @param creativeUnitTable 创意单元数据表
     * @return CreativeUnitObject
     */
    public static CreativeUnitObject creativeTable2CreativeUnitObject(AdCreativeUnitTable creativeUnitTable) {

        return new CreativeUnitObject(creativeUnitTable.getAdId(), creativeUnitTable.getUnitId());
    }
}
