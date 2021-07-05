package cn.edu.cqupt.pikachu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/18 10:54
 * @desc : 广告创意数据表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeTable {

    /**
     * 广告Id
     */
    private Long adId;

    /**
     * 创意名称
     */
    private String name;

    /**
     * 创意类型
     */
    private Integer type;

    /**
     * 创意素材类型
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
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 广告具体信息
     */
    private String adContents;

    /**
     * 广告链接地址
     */
    private String adUrl;
}
