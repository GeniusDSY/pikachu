package cn.edu.cqupt.pikachu.ad.search.vo;

import cn.edu.cqupt.pikachu.ad.search.vo.feature.DistrictFeature;
import cn.edu.cqupt.pikachu.ad.search.vo.feature.FeatureRelation;
import cn.edu.cqupt.pikachu.ad.search.vo.feature.ItFeature;
import cn.edu.cqupt.pikachu.ad.search.vo.feature.KeywordFeature;
import cn.edu.cqupt.pikachu.ad.search.vo.media.AdSlot;
import cn.edu.cqupt.pikachu.ad.search.vo.media.App;
import cn.edu.cqupt.pikachu.ad.search.vo.media.Device;
import cn.edu.cqupt.pikachu.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 17:58
 * @desc : 检索请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    /**
     * 媒体方的请求标识
     */
    private String mediaId;

    /**
     * 请求信息
     */
    private RequestInfo requestInfo;

    /**
     * 匹配信息
     */
    private FeatureInfo featureInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RequestInfo{

        /**
         * 请求Id
         */
        private String requestId;

        /**
         * 广告位列表
         */
        private List<AdSlot> adSlots;

        /**
         * 应用信息
         */
        private App app;

        /**
         * 地理信息
         */
        private Geo geo;

        /**
         * 设备信息
         */
        private Device device;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {

        /**
         * 关键字匹配信息
         */
        private KeywordFeature keywordFeature;

        /**
         * 区域匹配信息
         */
        private DistrictFeature districtFeature;

        /**
         * 兴趣爱好匹配信息
         */
        private ItFeature itFeature;

        /**
         * 匹配关系（默认为AND）
         */
        private FeatureRelation relation = FeatureRelation.AND;
    }
}
