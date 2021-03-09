package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/2/17 11:02
 * @desc : 创意单元展示数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitVO {

    /**
     * 创意单元Id列表
     */
    private List<Long> ids;

}
