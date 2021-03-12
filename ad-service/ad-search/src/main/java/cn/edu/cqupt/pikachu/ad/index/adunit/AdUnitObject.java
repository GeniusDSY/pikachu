package cn.edu.cqupt.pikachu.ad.index.adunit;

import cn.edu.cqupt.pikachu.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 21:05
 * @desc :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    /**
     * 广告单元Id
     */
    private Long unitId;

    /**
     * 广告单元状态
     */
    private Integer unitStatus;

    /**
     * 广告位类型(开屏广告、贴片广告、中贴广告...)
     */
    private Integer positionType;

    /**
     * 推广计划Id
     */
    private Long planId;

    /**
     * 广告计划实体数据
     */
    private AdPlanObject adPlanObject;

    /**
     * 更新广告单元数据索引
     *
     * @param newObject
     */
    public void update(AdUnitObject newObject) {

        if (null != newObject.getUnitId()) {
            this.unitId = newObject.getUnitId();
        }

        if (null != newObject.getUnitStatus()) {
            this.unitStatus = newObject.getUnitStatus();
        }

        if (null != newObject.getPositionType()) {
            this.positionType = newObject.getPositionType();
        }

        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }

        if (null != newObject.getAdPlanObject()) {
            this.adPlanObject = newObject.getAdPlanObject();
        }

    }

}
