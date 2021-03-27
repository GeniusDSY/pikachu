package cn.edu.cqupt.pikachu.ad.mysql;

import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import cn.edu.cqupt.pikachu.ad.mysql.dto.ParseTemplate;
import cn.edu.cqupt.pikachu.ad.mysql.dto.TableTemplate;
import cn.edu.cqupt.pikachu.ad.mysql.dto.Template;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author :DengSiYuan
 * @date :2021/3/27 20:45
 * @desc : 模板解析服务，实现映射关系
 */
@Slf4j
@Component
public class TemplateHolder {

    /**
     * 解析模板
     */
    private ParseTemplate template;

    /**
     * JDBC模板
     */
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * SQL格式
     */
    private static final String SQL_SCHEMA = "select table_schema, table_name, column_name, ordinal_position " +
            "from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    /**
     * 获取数据表模板
     *
     * @param tableName 数据表名称
     * @return 数据表模板信息
     */
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    /**
     * 加载Json文件到模板文件
     *
     * @param path 加载路径
     */
    private void loadJson(String path) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        try {

            Template template = JSON.parseObject(inputStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error("ad-search: TemplateHolder loadJson -> {}", e.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }

    /**
     * 加载MySQL数据
     */
    private void loadMeta() {

        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {

            TableTemplate table = entry.getValue();

            // 更新log
            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            // 插入log
            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            // 删除log
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                    template.getDatabase(), table.getTableName()
            }, (rs, i) -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String columnName = rs.getString("COLUMN_NAME");

                boolean conditions = (null != updateFields && updateFields.contains(columnName))
                        || (null != insertFields && insertFields.contains(columnName))
                        || (null != deleteFields && deleteFields.contains(columnName));
                if (conditions) {
                    table.getPosMap().put(pos - 1, columnName);
                }
                return null;
            });
        }
    }
}
