package cn.edu.cqupt.pikachu.ad.util;

import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;

/**
 * @author :DengSiYuan
 * @date :2021/1/28 21:27
 * @desc : 组建响应工具类
 */
public class ResponseUtils {

    /**
     * 组装异常响应工具方法
     *
     * @param resultStatus 结果信息枚举类
     * @return 响应结果
     * @throws Exception 传入组装参数错误
     */
    public static Response<Object> fail(ResultStatus resultStatus) {
        return new Response<>(resultStatus.getCode(), resultStatus.getDescription());
    }

    /**
     * 组装异常响应工具方法
     *
     * @param resultStatus 结果信息枚举类
     * @return 响应结果
     * @throws Exception 传入组装参数错误
     */
    public static Response<Object> fail(ResultStatus resultStatus, Object data) {
        return new Response<>(resultStatus.getCode(), resultStatus.getDescription(), data);
    }

    /**
     * 组装成功响应且有返回数据的工具方法
     *
     * @param resultStatus 结果信息枚举类
     * @param data           响应数据
     * @return 响应结果
     */
    public static Response<Object> success(ResultStatus resultStatus, Object data) {
        return new Response<>(resultStatus.getCode(), resultStatus.getDescription(), data);
    }

    /**
     * 组装成功响应无返回数据的工具方法
     *
     * @param resultStatus 结果信息枚举类
     * @return 响应结果
     */
    public static Response<Object> success(ResultStatus resultStatus) {
        return new Response<>(resultStatus.getCode(), resultStatus.getDescription());
    }


}
