package cn.edu.cqupt.pikachu.ad.index.adunit;

import cn.edu.cqupt.pikachu.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 21:05
 * @desc : 广告单元对象
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

    /**
     * 是否开屏
     *
     * @param positionType 流量类型
     * @return true/false
     */
    private static boolean isKaiPing(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.KAIPING) > 0;
    }

    /**
     * 是否贴片
     *
     * @param positionType 流量类型
     * @return true/false
     */
    private static boolean isTiePian(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN) > 0;
    }

    /**
     * 是否中部贴片
     *
     * @param positionType 流量类型
     * @return true/false
     */
    private static boolean isTiePianMiddle(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE) > 0;
    }

    /**
     * 是否暂停贴片
     *
     * @param positionType 流量类型
     * @return true/false
     */
    private static boolean isTiePianPause(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE) > 0;
    }

    /**
     * 是否结尾贴片
     *
     * @param positionType 流量类型
     * @return true/false
     */
    private static boolean isTiePianPost(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_POST) > 0;
    }

    /**
     * 判单广告位种类是否喝流量类型匹配
     *
     * @param adSlotType   广告位种类
     * @param positionType 流量信息
     * @return 是否匹配
     */
    public static boolean isAdSlotTypeOk(int adSlotType, int positionType) {

        switch (adSlotType) {
            case AdUnitConstants.POSITION_TYPE.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN:
                return isTiePian(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }

}
