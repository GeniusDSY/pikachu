package cn.edu.cqupt.pikachu.ad.mysql.dto;

import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :DengSiYuan
 * @date :2021/3/26 21:05
 * @desc : 数据表模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    /**
     * 数据表名称
     */
    private String tableName;

    /**
     * 等级
     */
    private String level;

    /**
     * 操作类型
     */
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引 -> 字段名
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
