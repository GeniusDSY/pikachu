package cn.edu.cqupt.pikachu.ad.model.vo.response;

import cn.edu.cqupt.pikachu.ad.constants.ResponseConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :DengSiYuan
 * @date :2020/9/28 20:54
 * @desc : 响应数据模型
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -2117304893907240721L;

    /**
     * 结果数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误码
     */
    private String code;

    /**
     * 异常响应构造函数
     *
     * @param code
     * @param message
     */
    public Response(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    /**
     * 成功响应构造函数
     *
     * @param code
     * @param message
     * @param data
     */
    public Response(String code, String message, T data) {
        this.code = code;
        this.message = ResponseConstants.EMPTY;
        this.data = data;
    }

}
