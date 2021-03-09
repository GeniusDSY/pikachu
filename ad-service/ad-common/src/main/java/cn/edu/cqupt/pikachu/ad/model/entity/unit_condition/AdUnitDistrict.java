package cn.edu.cqupt.pikachu.ad.model.entity.unit_condition;

/**
 * @author :DengSiYuan
 * @date :2021/2/3 22:42
 * @desc : 广告地域限制
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_district")
public class AdUnitDistrict {

    /**
     * 广告单元地域限制Id
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
     * 限制省份
     */
    @Basic
    @Column(name = "province", nullable = false)
    private String province;

    /**
     * 限制城市
     */
    @Basic
    @Column(name = "city", nullable = false)
    private String city;

    public AdUnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }

}
