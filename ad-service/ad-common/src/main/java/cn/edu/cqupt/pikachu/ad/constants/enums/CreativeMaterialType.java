package cn.edu.cqupt.pikachu.ad.constants.enums;

import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/2/4 19:43
 * @desc : 创意物料类型
 */
@Getter
public enum CreativeMaterialType {

    /**
     * 图片物料格式
     */
    JPG(1, "jpg"),
    BMP(2, "bmp"),

    /**
     * 视频物料格式
     */
    MP4(3, "mp4"),
    AVI(4, "avi"),

    /**
     * 文本物料格式
     */
    TXT(5, "txt"),
    ;

    /**
     * 创意物料类型
     */
    private Integer type;

    /**
     * 物料类型描述
     */
    private String desc;

    CreativeMaterialType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
