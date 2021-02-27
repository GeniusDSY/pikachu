package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/9 22:09
 * @desc : 广告单元关键字展示数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordVO {

    /**
     * 关键字Id
     */
    private List<Long> ids;

}
