package cn.edu.cqupt.pikachu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/18 10:57
 * @desc : 广告创意和广告单元关联表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeUnitTable {

    /**
     * 广告Id
     */
    private Long adId;

    /**
     * 广告单元Id
     */
    private Long unitId;

}
