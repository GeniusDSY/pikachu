package cn.edu.cqupt.pikachu.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 19:15
 * @desc : 地理位置信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    /**
     * 纬度
     */
    private Float latitude;

    /**
     * 经度
     */
    private Float longitude;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;
}
