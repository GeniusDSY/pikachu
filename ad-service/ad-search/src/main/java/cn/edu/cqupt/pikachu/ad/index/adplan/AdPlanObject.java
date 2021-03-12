package cn.edu.cqupt.pikachu.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 16:52
 * @desc : 广告计划索引
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {

    /**
     * 投放计划Id
     */
    private Long planId;

    /**
     * 用户Id
     */
    private Long userId;

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

    public void update(AdPlanObject newObject) {

        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }

        if (null != newObject.getUserId()) {
            this.userId = newObject.userId;
        }

        if (null != newObject.getPlanStatus()) {
            this.planStatus = newObject.getPlanStatus();
        }

        if (null != newObject.getStartDate()) {
            this.startDate = newObject.getStartDate();
        }

        if (null != newObject.getEndDate()) {
            this.endDate = newObject.endDate;
        }
    }

}
