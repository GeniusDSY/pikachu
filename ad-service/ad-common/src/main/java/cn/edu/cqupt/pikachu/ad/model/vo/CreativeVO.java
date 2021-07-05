package cn.edu.cqupt.pikachu.ad.model.vo;

import cn.edu.cqupt.pikachu.ad.constants.enums.CreativeMaterialType;
import cn.edu.cqupt.pikachu.ad.constants.enums.CreativeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/2/17 9:50
 * @desc : 创意展示数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeVO {

    /**
     * 创意Id
     */
    private Long id;

    /**
     * 创意名称
     */
    private String name;

    /**
     * 创意类型
     * @see CreativeType
     */
    private String type;

    /**
     * 素材类型
     * @see CreativeMaterialType
     */
    private String materialType;

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
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 广告具体内容
     */
    private String adContents;

    /**
     * 创意地址
     */
    private String url;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;

}
