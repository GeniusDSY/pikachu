package cn.edu.cqupt.pikachu.ad.search.vo;

import cn.edu.cqupt.pikachu.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 20:33
 * @desc : 检索响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        /**
         * 广告Id
         */
        private Long adId;

        /**
         * 广告地址
         */
        private String adUrl;

        /**
         * 广告宽度
         */
        private Integer width;

        /**
         * 广告高度
         */
        private Integer height;

        /**
         * 素材类型
         */
        private Integer materialType;

        /**
         * 展示检测Url
         */
        private List<String> showMonitorUrl = Arrays.asList("www.baidu.com", "www.baidu.com");

        /**
         * 点击检测Url
         */
        private List<String> clickMonitorUrl = Arrays.asList("www.baidu.com", "www.baidu.com");
    }

    public static Creative convert(CreativeObject object) {

        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setMaterialType(object.getType());
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }
}
