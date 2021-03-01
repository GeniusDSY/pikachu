package cn.edu.cqupt.pikachu.ad.model.entity;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/1/31 17:51
 * @desc : 推广计划实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_plan")
public class AdPlan {

    /**
     * 推广计划Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 用户Id
     */
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 推广计划名称
     */
    @Basic
    @Column(name = "plan_name", nullable = false)
    private String planName;

    /**
     * 推广计划状态
     */
    @Basic
    @Column(name = "plan_status", nullable = false)
    private Integer planStatus;

    /**
     * 推广计划开始时间
     */
    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    /**
     * 推广计划结束时间
     */
    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    /**
     * 推广计划创建时间
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 推广计划更新时间
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdPlan(Long userId, String planName, Date startDate, Date endDate) {
        this.userId = userId;
        this.planName = planName;
        this.planStatus = CommonStatus.VALID.getStatus();
        this.startDate = startDate;
        this.endDate = endDate;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }


}
