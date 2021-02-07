package cn.edu.cqupt.pikachu.ad.utils;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/2/5 22:57
 * @desc : 公共工具类
 */
public class CommonUtils {

    /**
     * 时间转换表达式
     */
    private static String[] parsePatterns = {
            "yyyy-MM-dd",
            "yyyy/MM/dd",
            "yyyy.MM.dd"
    };

    public static String md5Encrypt(String value) throws AdException {
        if (null == value) {
            throw new AdException(ResultStatus.ENCRYPT_TOKEN_ERROR);
        }
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     * String类型时间转换成Date类型
     *
     * @param value 需要转换的值
     * @return Date
     * @throws AdException 广告系统异常
     */
    public static Date parseString2Date(String value) throws AdException{
        try {
            return DateUtils.parseDate(value, parsePatterns);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }

}
