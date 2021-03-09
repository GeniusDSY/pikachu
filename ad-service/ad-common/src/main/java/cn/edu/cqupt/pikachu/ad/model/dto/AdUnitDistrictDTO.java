package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/10 11:18
 * @desc : 推广单元区域请求数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictDTO {

    private List<UnitDistrict> unitDistricts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitDistrict {

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

}
