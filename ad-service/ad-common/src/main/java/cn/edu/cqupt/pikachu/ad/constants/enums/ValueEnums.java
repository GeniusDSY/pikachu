package cn.edu.cqupt.pikachu.ad.constants.enums;

import cn.edu.cqupt.pikachu.ad.constants.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/4/6 15:43
 * @desc :
 */
@Getter
@AllArgsConstructor
public enum  ValueEnums implements CodeEnum {

    UNKNOWN(-1, "不详"),
    MAN(0, "男"),
    WOMAN(1, "女"),
    ;

    /**
     * 数值
     */
   private Integer code;

    /**
     * 含义
     */
   private String desc;
}
