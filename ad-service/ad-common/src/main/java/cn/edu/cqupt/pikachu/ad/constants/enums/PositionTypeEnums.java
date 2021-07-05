package cn.edu.cqupt.pikachu.ad.constants.enums;

import cn.edu.cqupt.pikachu.ad.constants.CodeEnum;
import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/5/1 19:09
 * @desc : 广告单元未知类型
 */
@Getter
public enum PositionTypeEnums implements CodeEnum {

    KAI_PING(1, "开屏广告"),
    TIE_PIAN(2, "贴片广告"),
    TIE_PIAN_MIDDLE(4, "中部贴片广告"),
    TIE_PIAN_PAUSE(8, "暂停贴片广告"),
    TIE_PIAN_POST(16, "后部贴片广告")
    ;

    /**
     * 类型
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    PositionTypeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
