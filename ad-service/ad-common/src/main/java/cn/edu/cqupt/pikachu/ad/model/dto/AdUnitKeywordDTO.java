package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/9 21:37
 * @desc : 广告单晕啊关键字请求数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordDTO {

    /**
     * 推广单元关键字列表
     */
    private List<UnitKeyword> unitKeywords;

    /**
     * 广告单元关键字内部类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeyword {

        /**
         * 推广单元Id
         */
        private Long unitId;

        /**
         * 关键字
         */
        private String keyword;

    }

}
