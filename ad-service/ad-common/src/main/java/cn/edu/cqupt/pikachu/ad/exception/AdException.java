package cn.edu.cqupt.pikachu.ad.exception;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;

/**
 * @author :DengSiYuan
 * @date :2021/1/30 11:38
 * @desc : 广告系统的自定义异常类
 */
public class AdException extends Exception {

    public AdException(ResultStatus resultStatus) {
        super(resultStatus.getDescription());
    }

    public AdException(String message) {
        super(message);
    }


}
