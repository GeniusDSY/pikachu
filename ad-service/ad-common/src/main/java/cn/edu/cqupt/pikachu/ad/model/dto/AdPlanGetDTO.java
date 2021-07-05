package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/6 12:44
 * @desc : 批量获取推广计划的数据请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetDTO {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 推广计划Id列表
     */
    private List<Long> ids;

    /**
     * 推广计划名称
     */
    private String name;

    public boolean validate() {
        return  null != userId;
    }

}
