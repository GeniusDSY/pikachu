package cn.edu.cqupt.pikachu.ad.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2020/9/28 21:04
 * @desc : 响应码
 */
@Getter
@AllArgsConstructor
public enum ResultStatus {

    /**
     * 响应状态是成功
     */
    SUCCESS("SUCCESS", "成功"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    ;

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String description;

}
