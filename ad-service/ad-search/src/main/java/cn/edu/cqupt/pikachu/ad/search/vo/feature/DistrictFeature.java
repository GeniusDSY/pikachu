package cn.edu.cqupt.pikachu.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 19:51
 * @desc : 区域匹配信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictFeature {

    /**
     * 区域信息（省份、城市）
     */
    private List<ProvinceAndCity> districts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvinceAndCity {

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
