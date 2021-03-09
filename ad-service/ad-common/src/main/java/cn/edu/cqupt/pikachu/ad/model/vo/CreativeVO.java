package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/2/17 9:50
 * @desc : 创意展示数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeVO {

    /**
     * 创意Id
     */
    private Long id;

    /**
     * 创意名称
     */
    private String name;

}
