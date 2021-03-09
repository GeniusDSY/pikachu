package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/17 10:48
 * @desc : 创意单元传入数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitDTO {

    /**
     * 创意单元项目列表
     */
    private List<CreativeUnitItem> creativeUnitItems;

    /**
     * 创意单元项目
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreativeUnitItem {

        /**
         * 创意Id
         */
        private Long creativeId;

        /**
         * 推广单元Id
         */
        private Long unitId;
    }

}
