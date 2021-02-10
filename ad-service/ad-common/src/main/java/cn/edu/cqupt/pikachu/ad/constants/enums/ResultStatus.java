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

    /**
     * 请求参数错误
     */
    REQUEST_PARAM_ERROR("REQUEST_PARAM_ERROR", "请求参数错误"),

    /**
     * 同名用户已存在
     */
    USER_EXISTED("USER_EXISTED", "同名用户已存在"),

    /**
     * 加密Token异常
     */
    ENCRYPT_TOKEN_ERROR("ENCRYPT_TOKEN_ERROR", "加密Token异常"),

    /**
     * 用户不存在
     */
    USER_NOT_EXISTED("USER_NOT_EXISTED", "用户不存在"),
    /**
     * 推广计划已存在
     */
    PLAN_EXISTED("PLAN_EXISTED", "推广计划已存在"),

    /**
     * 推广计划不存在
     */
    PLAN_NOT_EXISTED("PLAN_NOT_EXISTED", "推广计划不存在"),

    /**
     * 推广单元已存在
     */
    UNIT_EXISTED("UNIT_EXISTED", "推广单元已存在");

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String description;

}
