package cn.edu.cqupt.pikachu.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author :DengSiYuan
 * @date :2021/3/27 21:51
 * @desc : Binlog行数据映射对象
 */
@Data
public class BinlogRowData {

    /**
     * 数据表模板数据
     */
    private TableTemplate table;

    /**
     * 事件操作类型
     */
    private EventType eventType;

    /**
     * 事件操作之后
     */
    private List<Map<String, String>> after;

    /**
     * 事件操作之前
     */
    private List<Map<String, String>> before;
}
