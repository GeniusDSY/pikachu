package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/10 11:15
 * @desc : 广告推广单元战术数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictVO {

    /**
     * 推广单元区域Id
     */
    private List<Long> ids;

}
