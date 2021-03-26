package cn.edu.cqupt.pikachu.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author :DengSiYuan
 * @date :2021/3/26 20:57
 * @desc : 模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    /**
     * 数据库名称
     */
    private String database;

    /**
     * 数据表列表
     */
    private List<JsonTable> tableList;
}
