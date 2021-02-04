package cn.edu.cqupt.pikachu.ad.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author :DengSiYuan
 * @date :2021/1/30 22:40
 * @desc :
 */
@Getter
@AllArgsConstructor
public enum CommonStatus {

    /**
     * 无效状态
     */
    INVALID(0,"无效状态"),

    /**
     * 有效状态
     */
    VALID(1, "有效状态"),
    ;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String  desc;

}
