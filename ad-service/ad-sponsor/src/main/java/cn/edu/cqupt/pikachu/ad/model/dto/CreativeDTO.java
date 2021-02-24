package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author :DengSiYuan
 * @date :2021/2/14 15:53
 * @desc : 创意传入数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeDTO {

    /**
     * 创意的名称
     */
    private String name;

    /**
     * 创意的类型
     */
    private Integer type;

    /**
     * 素材类型
     */
    private Integer materialType;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 大小
     */
    private Long size;

    /**
     * 持续时长
     */
    private Integer duration;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 创建参数有效性检验
     *
     * @return true/false
     */
    public boolean createValidate() {
        return null != userId && StringUtils.isNotEmpty(name)
                && null != type && null != materialType
                && null != height && null != width
                && null != size && null != duration
                && null != url;
    }

}
