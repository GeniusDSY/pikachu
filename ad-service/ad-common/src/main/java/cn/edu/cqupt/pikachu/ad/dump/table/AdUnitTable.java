package cn.edu.cqupt.pikachu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/18 10:38
 * @desc :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitTable {

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
     * 关联的广告计划Id
     */
    private Long planId;
}
