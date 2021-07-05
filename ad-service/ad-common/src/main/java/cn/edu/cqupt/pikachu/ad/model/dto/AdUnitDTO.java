package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author :DengSiYuan
 * @date :2021/2/7 15:44
 * @desc : 广告推广单元请求数据模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDTO {

    /**
     * 广告单元Id
     */
    private Long id;

    /**
     * 推广计划Id
     */
    private Long planId;

    /**
     * 推广单元名称
     */
    private String unitName;

    /**
     * 广告类型
     */
    private Integer positionType;

    /**
     * 推广预算
     */
    private Long budget;

    /**
     * 广告单元状态
     */
    private Integer unitStatus;

    /**
     * 创建广告推广单元的有效性验证
     *
     * @return 有效与否
     */
    public boolean createValidate() {
        return null != planId && StringUtils.isNotEmpty(unitName)
                && null != positionType && null != budget;
    }

    public boolean updateValidate() {
        return null != id && StringUtils.isNotEmpty(unitName) && null != positionType && null != budget;
    }
}
