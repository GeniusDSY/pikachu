package cn.edu.cqupt.pikachu.ad.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/12 13:04
 * @desc : 创意单元索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {

    /**
     * 广告创意Id
     */
    private Long adId;

    /**
     * 广告推广单元Id
     */
    private Long unitId;

}
