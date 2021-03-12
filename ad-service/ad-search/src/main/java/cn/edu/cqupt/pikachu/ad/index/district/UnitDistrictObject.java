package cn.edu.cqupt.pikachu.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/10 20:02
 * @desc : 推广单元地域索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictObject {

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
