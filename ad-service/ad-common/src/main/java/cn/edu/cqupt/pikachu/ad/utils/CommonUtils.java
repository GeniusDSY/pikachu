package cn.edu.cqupt.pikachu.ad.utils;

import cn.edu.cqupt.pikachu.ad.constants.CodeEnum;
import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author :DengSiYuan
 * @date :2021/2/5 22:57
 * @desc : 公共工具类
 */
@Slf4j
public class CommonUtils {

    /**
     * 时间转换表达式
     */
    private static String[] parsePatterns = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy.MM.dd HH:mm:ss"
    };

    /**
     * Md5加密处理
     *
     * @param value 需要加密的数据
     * @return 加密结果
     * @throws AdException 广告系统异常
     */
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
    public static Date parseString2Date(String value) throws AdException {
        try {
            return DateUtils.parseDate(value, parsePatterns);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }

    /**
     * String类型时间转换成Date类型
     *
     * @param date 需要转换的值
     * @return str String类型的日期
     * @throws AdException 广告系统异常
     */
    public static String parseDate2String(Date date) {
        SimpleDateFormat sdf=new SimpleDateFormat(parsePatterns[0]);
        return sdf.format(date);
    }

    /**
     * 如果要获取的Key不存在，则通过工厂生成一个新的对象
     *
     * @param key     键
     * @param map     KV map
     * @param factory 对象工厂
     * @param <K>     键
     * @param <V>     值
     * @return
     */
    public static <K, V> V getorCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    /**
     * 字符串拼接（”-“）
     *
     * @param args 参数
     * @return 拼接结果
     */
    public static String stringConcat(String... args) {

        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append("-");
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }

    /**
     * 根据枚举类的Code获取枚举类的值
     *
     * @param enumClass 枚举类
     * @param code      枚举Code
     * @param <T>       枚举类型
     * @return 枚举值
     */
    public static <T extends CodeEnum> String getByCode(Class<T> enumClass, Integer code) {
        for (T each : enumClass.getEnumConstants()) {
            if (each.getCode().equals(code)) {
                return each.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据枚举类的值获取枚举类的Code
     *
     * @param enumClass 枚举类
     * @param value      枚举Code
     * @param <T>       枚举类型
     * @return 枚举Code
     */
    public static <T extends CodeEnum> Integer getByDesc(Class<T> enumClass, String value) {
        for (T each : enumClass.getEnumConstants()) {
            if (each.getDesc().equals(value)) {
                return each.getCode();
            }
        }
        return -1;
    }

}
