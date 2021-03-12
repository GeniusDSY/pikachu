package cn.edu.cqupt.pikachu.ad.index.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/10 19:37
 * @desc : 兴趣爱好索引数据模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitItObject {

    /**
     * 推广单元Id
     */
    private Long unitId;

    /**
     * 兴趣标签
     */
    private String itTag;

}
