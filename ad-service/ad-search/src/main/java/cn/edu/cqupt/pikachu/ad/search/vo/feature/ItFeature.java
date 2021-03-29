package cn.edu.cqupt.pikachu.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 19:50
 * @desc : 兴趣匹配信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItFeature {

    /**
     * 兴趣爱好信息
     */
    private List<String> its;
}
