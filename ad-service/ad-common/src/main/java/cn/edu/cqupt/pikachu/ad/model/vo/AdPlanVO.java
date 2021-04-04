package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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


    /**
     * 推广计划状态
     */
    private Integer planStatus;

    /**
     * 推广计划开始时间
     */
    private Date startDate;

    /**
     * 推广计划结束时间
     */
    private Date endDate;

    /**
     * 推广计划创建时间
     */
    private Date createTime;

    /**
     * 推广计划更新时间
     */
    private Date updateTime;
}
