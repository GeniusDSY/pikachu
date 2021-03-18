package cn.edu.cqupt.pikachu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/18 10:53
 * @desc : 广告单元区域数据表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictTable {

    /**
     * 推广单元Id
     */
    private Long unitId;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;
}
