package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/2/6 11:44
 * @desc : 推广计划展示数据信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanVO {

    /**
     * 推广计划Id
     */
    private Long id;

    /**
     * 推广计划名称
     */
    private String planName;
}
