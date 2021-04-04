package cn.edu.cqupt.pikachu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/18 10:51
 * @desc : 广告关键词数据表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordTable {

    /**
     * 推广单元Id
     */
    private Long unitId;

    /**
     * 关键词
     */
    private String keyword;
}
