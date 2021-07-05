package cn.edu.cqupt.pikachu.ad.constants.enums;

import cn.edu.cqupt.pikachu.ad.constants.CodeEnum;
import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 19:43
 * @desc : 创意物料类型
 */
@Getter
public enum CreativeMaterialType implements CodeEnum {

    /**
     * 图片物料格式
     */
    JPG(1, "JPG"),
    BMP(2, "BMP"),

    /**
     * 视频物料格式
     */
    MP4(3, "MP4"),
    AVI(4, "AVI"),

    /**
     * 文本物料格式
     */
    TXT(5, "TXT"),
    ;

    /**
     * 创意物料类型
     */
    private Integer code;

    /**
     * 物料类型描述
     */
    private String desc;

    CreativeMaterialType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
