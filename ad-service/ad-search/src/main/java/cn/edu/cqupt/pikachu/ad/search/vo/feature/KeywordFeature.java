package cn.edu.cqupt.pikachu.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/29 19:49
 * @desc : 关键词匹配信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordFeature {

    /**
     * 关键词信息
     */
    private List<String> keywords;
}
