package cn.edu.cqupt.pikachu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/3/18 10:34
 * @desc : 广告计划数据表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {

    /**
     * 广告计划Id
     */
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 广告计划状态
     */
    private Integer planStatus;

    /**
     * 广告计划开始时间
     */
    private Date startDate;

    /**
     * 广告计划结束时间
     */
    private Date endDate;
}
