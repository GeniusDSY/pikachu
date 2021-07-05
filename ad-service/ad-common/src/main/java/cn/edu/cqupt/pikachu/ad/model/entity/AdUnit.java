package cn.edu.cqupt.pikachu.ad.model.entity;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/2/3 21:02
 * @desc : 广告单元
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit")
public class AdUnit {

    /**
     * 广告单元Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 所属计划Id
     */
    @Basic
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    /**
     * 广告单元名称
     */
    @Basic
    @Column(name = "unit_name", nullable = false)
    private String unitName;

    /**
     * 广告单元状态
     */
    @Basic
    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;

    /**
     * 广告位类型(开屏广告、贴片广告、中贴广告...)
     */
    @Basic
    @Column(name = "position_type", nullable = false)
    private Integer positionType;

    /**
     * 广告单元预算
     */
    @Basic
    @Column(name = "budget", nullable = false)
    private Long budget;

    /**
     * 广告单元创建时间
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 广告单元更新时间
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUnit(Long id, Long planId, String unitName, Integer positionType, Long budget) {
        this.id = id;
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALID.getStatus();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

}
