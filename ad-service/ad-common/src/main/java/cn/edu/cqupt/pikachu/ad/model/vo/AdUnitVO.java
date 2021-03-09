package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/2/7 16:05
 * @desc : 广告推广单元展示数据模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitVO {

    /**
     * 推广单元Id
     */
    private Long id;

    /**
     * 推广单元名称
     */
    private String unitName;

}
