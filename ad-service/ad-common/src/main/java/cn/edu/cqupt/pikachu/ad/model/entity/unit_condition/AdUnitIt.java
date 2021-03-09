package cn.edu.cqupt.pikachu.ad.model.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author :DengSiYuan
 * @date :2021/2/3 22:40
 * @desc : 广告兴趣限制
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_it")
public class AdUnitIt {

    /**
     * 广告单元兴趣限制Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 所属的广告单元Id
     */
    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    /**
     * 兴趣标签
     */
    @Basic
    @Column(name = "itTag", nullable = false)
    private String itTag;

    public AdUnitIt(Long unitId, String itTag) {
        this.unitId = unitId;
        this.itTag = itTag;
    }

}
