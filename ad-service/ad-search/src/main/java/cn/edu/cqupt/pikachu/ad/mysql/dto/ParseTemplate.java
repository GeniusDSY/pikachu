package cn.edu.cqupt.pikachu.ad.mysql.dto;

import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author :DengSiYuan
 * @date :2021/3/26 21:11
 * @desc : 解析模板
 */
@Data
public class ParseTemplate {

    /**
     * 数据库
     */
    private String database;

    /**
     * 数据表模板映射
     */
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    /**
     * 解析模板数据
     *
     * @param _template 模板
     * @return 解析后数据
     */
    public static ParseTemplate parse(Template _template) {

        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());

        for (JsonTable table : _template.getTableList()) {

            String name = table.getTableName();
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(name, tableTemplate);

            // 遍历操作类型对应的列
            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(OpType.DELETE, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
        }
        return template;
    }

    /**
     * 获取，若不存在则创建
     *
     * @param key     操作类型，Map的key
     * @param map     操作类型的映射表
     * @param factory 若不存在该操作类型，则创建
     * @param <T>     操作类型枚举类
     * @param <R>     操作类型所对应的列的数据类型
     * @return 当前操作类型的数据
     */
    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
