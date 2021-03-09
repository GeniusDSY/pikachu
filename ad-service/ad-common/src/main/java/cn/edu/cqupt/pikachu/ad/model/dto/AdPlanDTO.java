package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author :DengSiYuan
 * @date :2021/2/6 11:47
 * @desc : 广告计划请求数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanDTO {

    /**
     * 广告推广计划Id
     */
    private Long id;

    /**
     * 推广计划用户Id
     */
    private Long userId;

    /**
     * 推广计划名称
     */
    private String planName;

    /**
     * 推广计划开始时间
     */
    private String startDate;

    /**
     * 推广计划结束时间
     */
    private String endDate;

    /**
     * 检验创建参数有效性
     *
     * @return true/false
     */
    public boolean createValidate() {
        return null != userId
                && StringUtils.isNotEmpty(planName)
                && StringUtils.isNotEmpty(startDate)
                && StringUtils.isNotEmpty(endDate);
    }

    public boolean updateValidate() {
        return null != id && null != userId;
    }

    public boolean deleteValidate() {
        return  null != id && null != userId;
    }

}
