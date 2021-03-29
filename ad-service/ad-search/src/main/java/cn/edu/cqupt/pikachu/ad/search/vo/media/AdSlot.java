package cn.edu.cqupt.pikachu.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 18:02
 * @desc : 广告位
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    /**
     * 广告位编码
     */
    private String adSlotCode;

    /**
     * 流量类型
     */
    private Integer positionType;

    /**
     * 广告位的宽
     */
    private Integer width;

    /**
     * 广告位的高
     */
    private Integer height;

    /**
     * 广告物料类型（图片、视频等）
     */
    private List<Integer> type;

    /**
     * 广告的最低出价
     */
    private Integer minCpm;
}
