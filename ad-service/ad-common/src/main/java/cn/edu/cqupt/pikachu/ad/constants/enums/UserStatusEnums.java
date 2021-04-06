package cn.edu.cqupt.pikachu.ad.constants.enums;

import cn.edu.cqupt.pikachu.ad.constants.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/4/6 15:50
 * @desc : 用户状态枚举类
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnums implements CodeEnum {

    UNKNOWN(-1, "不详"),
    WORKING(0, "工作中"),
    SLEEPING(1, "睡觉中"),
    RESTING(2, "休假中"),
    MEETING(3, "会议中"),
    GAMING(4, "游戏中")
    ;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String desc;
}
