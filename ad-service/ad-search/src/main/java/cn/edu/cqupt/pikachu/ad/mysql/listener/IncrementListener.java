package cn.edu.cqupt.pikachu.ad.mysql.listener;

import cn.edu.cqupt.pikachu.ad.mysql.constant.Constant;
import cn.edu.cqupt.pikachu.ad.mysql.constant.OpType;
import cn.edu.cqupt.pikachu.ad.mysql.dto.BinlogRowData;
import cn.edu.cqupt.pikachu.ad.mysql.dto.MySqlRowData;
import cn.edu.cqupt.pikachu.ad.mysql.dto.TableTemplate;
import cn.edu.cqupt.pikachu.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :DengSiYuan
 * @date :2021/3/28 18:22
 * @desc : 增量数据监听处理器
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    @Resource
    private ISender sender;

    @Resource
    private AggregationListener aggregationListener;

    /**
     * 监听器注册
     */
    @Override
    @PostConstruct
    public void register() {

        log.info("IncrementListener register -> IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    /**
     * 事件触发
     *
     * @param eventData 事件数据
     */
    @Override
    public void onEvent(BinlogRowData eventData) {

        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();

        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("IncrementListener onEvent -> {} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>(16);
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();

                _afterMap.put(colName, colValue);
            }

            rowData.getFieldValueMap().add(_afterMap);
        }

        sender.sender(rowData);
    }
}
