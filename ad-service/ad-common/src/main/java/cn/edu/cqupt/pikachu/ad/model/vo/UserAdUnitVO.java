package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/5/1 18:13
 * @desc : 用户广告单元展示数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdUnitVO {

    /**
     * 广告单元Id
     */
    private Long id;

    /**
     * 所属广告计划信息（id+name）
     */
    private String planMsg;

    /**
     * 广告单元名称
     */
    private String unitName;

    /**
     * 广告单元位置信息
     */
    private String positionType;

    /**
     * 广告单元状态
     */
    private Integer unitStatus;

    /**
     * 广告单元预算
     */
    private Long budget;

    /**
     * 广告单元创建时间
     */
    private String createTime;

    /**
     * 广告单元更新时间
     */
    private String updateTime;

}
