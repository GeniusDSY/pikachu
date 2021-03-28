package cn.edu.cqupt.pikachu.ad.mysql.listener;

import cn.edu.cqupt.pikachu.ad.mysql.TemplateHolder;
import cn.edu.cqupt.pikachu.ad.mysql.dto.BinlogRowData;
import cn.edu.cqupt.pikachu.ad.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author :DengSiYuan
 * @date :2021/3/27 21:57
 * @desc : 监听器集合
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据表名称
     */
    private String tableName;

    /**
     * 监听器存储器
     */
    private Map<String, IListener> listenerMap = new HashMap<>();

    @Resource
    private TemplateHolder templateHolder;

    /**
     * 生成Key的方法
     *
     * @param dbName    数据库名称
     * @param tableName 数据表名称
     * @return key
     */
    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    /**
     * 监听器注册
     *
     * @param dbName    数据库名称
     * @param tableName 数据表名称
     * @param iListener 监听器
     */
    public void register(String dbName, String tableName, IListener iListener) {
        log.info("ad-search: AggregationListener register -> {}-{}", dbName, tableName);
        this.listenerMap.put(genKey(dbName, tableName), iListener);
    }

    @Override
    public void onEvent(Event event) {

        EventType type = event.getHeader().getEventType();
        log.info("ad-search: Aggregation onEvent -> eventType :{}", type);

        // 记录当前操作的数据库和数据表的名称
        if (EventType.TABLE_MAP == type) {
            TableMapEventData data = event.getData();
            this.dbName = data.getDatabase();
            this.tableName = data.getTable();
            return;
        }

        // 若操作类型不属于增删改，则直接返回
        if (EventType.EXT_UPDATE_ROWS != type && EventType.EXT_WRITE_ROWS != type && EventType.EXT_DELETE_ROWS != type) {
            return;
        }

        // 判断表名和库名是否已经完成填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("ad-search: AggregationListener onEvent -> no meta data event");
            return;
        }

        // 找去对应表有兴趣的监听器
        String key = genKey(this.dbName, this.tableName);
        IListener listener = this.listenerMap.get(key);
        if (null == listener) {
            log.info("ad-search: AggregationListener onEvent -> skip key:{}", key);
            return;
        }
        log.info("ad-search: AggregationListener onEvent -> trigger event:{}", type.name());

        try {

            BinlogRowData rowData = buildRowData(event.getData());
            if (null == rowData) {
                return;
            }

            rowData.setEventType(type);
            listener.onEvent(rowData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ad-search: AggregationListener onEvent -> error msg:{}", e.getMessage());
        } finally {
            this.dbName = StringUtils.EMPTY;
            this.tableName = StringUtils.EMPTY;
        }
    }

    /**
     * 获取操作后数据
     *
     * @param eventData 时间数据
     * @return 序列化数据行
     */
    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * 解析构建BinlogRowData
     *
     * @param eventData 事件处理数据
     * @return binlogRowData
     */
    private BinlogRowData buildRowData(EventData eventData) {

        TableTemplate table = templateHolder.getTable(tableName);

        if (null == table) {
            log.warn("ad-search: AggregationListener buildRowData -> table:{} not found", tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();

        for (Serializable[] after : getAfterValues(eventData)) {

            Map<String, String> afterMap = new HashMap<>(16);

            int colLength = after.length;

            for (int i = 0; i < colLength; ++i) {

                // 取出当前位置的列名
                String colName = table.getPosMap().get(i);

                //如果没有则不关心这个列
                if (null == colName) {
                    log.info("ad-search: AggregationListener buildRowData -> ignore position:{}", i);
                    continue;
                }

                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }

            afterMapList.add(afterMap);
        }

        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
