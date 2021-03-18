package cn.edu.cqupt.pikachu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/18 10:52
 * @desc : 广告单元兴趣数据表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItTable {

    /**
     * 广告单元Id
     */
    private Long unitId;

    /**
     * 兴趣标签
     */
    private String itTag;
}
