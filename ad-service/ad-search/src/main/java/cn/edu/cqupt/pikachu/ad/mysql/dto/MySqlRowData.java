package cn.edu.cqupt.pikachu.ad.mysql.dto;

import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author :DengSiYuan
 * @date :2021/3/28 11:45
 * @desc : MySQL数据行映射对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    /**
     * 数据表名称
     */
    private String tableName;

    /**
     * 数据表层级
     */
    private String level;

    /**
     * 操作数据类型
     */
    private OpType opType;

    /**
     * 数据集合
     */
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}
