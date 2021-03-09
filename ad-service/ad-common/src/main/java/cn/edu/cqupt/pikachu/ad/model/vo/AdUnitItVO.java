package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/9 22:16
 * @desc : 广告推广单元兴趣展示数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItVO {

    /**
     * 推广单元兴趣Id列表
     */
    private List<Long> ids;

}
