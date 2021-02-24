package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/9 22:12
 * @desc : 广告单元兴趣请求数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItDTO {

    /**
     * 推广单元兴趣列表
     */
    private List<UnitIt> unitIts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitIt {

        /**
         * 推广单元Id
         */
        private Long unitId;

        /**
         * 兴趣标签
         */
        private String itTag;

    }

}
