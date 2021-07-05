package cn.edu.cqupt.pikachu.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :DengSiYuan
 * @date :2021/3/12 12:28
 * @desc : 创意索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeObject {

    /**
     * 广告创意Id
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
     * 素材类型
     */
    private Integer materialType;

    /**
     * 创意高度
     */
    private Integer height;

    /**
     * 创意宽度
     */
    private Integer width;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 广告具体内容
     */
    private String adContents;

    /**
     * 广告地址
     */
    private String adUrl;

    /**
     * 创意索引更新处理
     *
     * @param newObject 更新数据
     */
    public void update(CreativeObject newObject) {

        if (null != newObject.getAdId()) {
            this.adId = newObject.getAdId();
        }

        if (null != newObject.getName()) {
            this.name = newObject.getName();
        }

        if (null != newObject.getType()) {
            this.type = newObject.getType();
        }

        if (null != newObject.getMaterialType()) {
            this.materialType = newObject.getMaterialType();
        }

        if (null != newObject.getHeight()) {
            this.height = newObject.getHeight();
        }

        if (null != newObject.getWidth()) {
            this.width = newObject.getWidth();
        }

        if (null != newObject.getAuditStatus()) {
            this.auditStatus = newObject.getAuditStatus();
        }

        if (null != newObject.getAdContents()) {
            this.adContents = newObject.getAdContents();
        }

        if (null != newObject.getAdUrl()) {
            this.adUrl = newObject.getAdUrl();
        }
    }

}
