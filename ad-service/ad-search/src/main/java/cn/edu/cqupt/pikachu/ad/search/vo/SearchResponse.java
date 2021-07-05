package cn.edu.cqupt.pikachu.ad.search.vo;

import cn.edu.cqupt.pikachu.ad.index.creative.CreativeObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

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
         * 广告名称
         */
        private String adName;

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
         * 广告具体描述信息
         */
        private List<AdContent> adContents;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdContent {

        /**
         * 素材类型
         */
        private Integer materialType;

        /**
         * 素材展示链接
         */
        private String showUrl;

        /**
         * 素材跳转链接
         */
        private String clickUrl;

        /**
         * 素材描述
         */
        private String adDesc;
    }

    public static Creative convert(CreativeObject object) {

        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdName(object.getName());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setMaterialType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        creative.setAdContents(JSON.parseArray(object.getAdContents(), AdContent.class));
        return creative;
    }
}
