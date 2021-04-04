package cn.edu.cqupt.pikachu.ad.model.vo.response;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :DengSiYuan
 * @date :2020/9/28 20:54
 * @desc : 响应数据模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -2117304893907240721L;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

    /**
     * 异常响应构造函数
     *
     * @param resultStatus 结果状态码
     */
    public Response(ResultStatus resultStatus) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getDescription();
        this.data = null;
    }

    /**
     * 成功响应构造函数
     *
     * @param data 响应数据
     */
    public Response(T data) {
        this.code = ResultStatus.SUCCESS.getCode();
        this.message = ResultStatus.SUCCESS.getDescription();
        this.data = data;
    }

    /**
     * 成功响应构造函数
     *
     * @param resultStatus 结果状态码
     * @param data
     */
    public Response(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getDescription();
        this.data = data;
    }

}
