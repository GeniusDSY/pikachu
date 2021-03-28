package cn.edu.cqupt.pikachu.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author :DengSiYuan
 * @date :2021/3/22 14:40
 * @desc : 操作类型
 */
public enum OpType {

    /**
     * Insert操作
     */
    ADD,
    /**
     * 更新操作
     */
    UPDATE,
    /**
     * 删除操作
     */
    DELETE,
    /**
     * 其他操作
     */
    OTHER,
    ;

    /**
     * 事件操作类型转换
     *
     * @param eventType 事件类型
     * @return 操作类型
     */
    public static OpType to(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }

}
