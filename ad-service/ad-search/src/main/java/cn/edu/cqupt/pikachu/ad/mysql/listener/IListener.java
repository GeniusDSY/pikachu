package cn.edu.cqupt.pikachu.ad.mysql.listener;

import cn.edu.cqupt.pikachu.ad.mysql.dto.BinlogRowData;

/**
 * @author :DengSiYuan
 * @date :2021/3/27 21:55
 * @desc : Binlog监听
 */
public interface IListener {

    /**
     * 监听器注册
     */
    void register();

    /**
     * 事件触发
     *
     * @param eventData 事件数据
     */
    void onEvent(BinlogRowData eventData);
}
