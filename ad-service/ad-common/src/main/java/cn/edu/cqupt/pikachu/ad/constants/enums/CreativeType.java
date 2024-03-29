package cn.edu.cqupt.pikachu.ad.constants.enums;

import cn.edu.cqupt.pikachu.ad.constants.CodeEnum;
import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/2/3 23:23
 * @desc : 创意类型
 */
@Getter
public enum CreativeType implements CodeEnum {

    /**
     * 图片类型
     */
    IMAGE(1, "图片"),

    /**
     * 视频类型
     */
    VIDEO(2, "视频"),

    /**
     * 文本类型
     */
    TEXT(3, "文本")
    ;

    /**
     * 类型
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    CreativeType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
