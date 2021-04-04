package cn.edu.cqupt.pikachu.ad.sender;

import cn.edu.cqupt.pikachu.ad.mysql.dto.MySqlRowData;

/**
 * @author :DengSiYuan
 * @date :2021/3/28 18:16
 * @desc : 增量数据投递接口
 */
public interface ISender {

    /**
     * 增量数据投递
     *
     * @param rowData 投递数据
     */
    void sender(MySqlRowData rowData);
}
