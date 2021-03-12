package cn.edu.cqupt.pikachu.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 21:31
 * @desc : 广告单元关键字实体数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordObject {

    /**
     * 广告推广单元Id
     */
    private Long unitId;

    /**
     * 搜索关键字
     */
    private String keyword;
}
