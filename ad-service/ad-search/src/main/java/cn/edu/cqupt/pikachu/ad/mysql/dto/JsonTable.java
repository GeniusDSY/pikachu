package cn.edu.cqupt.pikachu.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/26 20:47
 * @desc : 模板文件表的数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    /**
     * 数据表名称
     */
    private String tableName;

    /**
     * 索引等级
     */
    private Integer level;

    /**
     * 插入数据列数据
     */
    private List<Column> insert;

    /**
     * 更新数据列数据
     */
    private List<Column> update;

    /**
     * 删除数据列数据
     */
    private List<Column> delete;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {

        /**
         * 列名
         */
        private String column;
    }
}
