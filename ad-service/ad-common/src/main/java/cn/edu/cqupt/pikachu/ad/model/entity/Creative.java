package cn.edu.cqupt.pikachu.ad.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/2/3 22:53
 * @desc : 广告创意
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_creative")
public class Creative {

    /**
     * 创意Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 创意名称
     */
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 创意的主类型（文字、图片、视频）
     */
    @Basic
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 物料的类型（图片：jpg、bmp；视频：MP4）
     */
    @Basic
    @Column(name = "material_type", nullable = false)
    private Integer materialType;

    /**
     * 物料高度
     */
    @Basic
    @Column(name = "height", nullable = false)
    private Integer height;

    /**
     * 物料宽度
     */
    @Basic
    @Column(name = "width", nullable = false)
    private Integer width;

    /**
     * 物料大小
     */
    @Basic
    @Column(name = "size", nullable = false)
    private Long size;

    /**
     * 持续时长（视频）
     */
    @Basic
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /**
     * 审核状态
     */
    @Basic
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;

    /**
     * 上传用户Id
     */
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 物料地址信息
     */
    @Basic
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * 创意创建时间
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 创意更新时间
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

}
