package cn.edu.cqupt.pikachu.ad.model.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author :DengSiYuan
 * @date :2021/2/3 23:20
 * @desc : 创意单元
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creative_unit")
public class CreativeUnit {

    /**
     * 创意单元Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 创意Id
     */
    @Basic
    @Column(name = "creative_id", nullable = false)
    private Long creativeId;

    /**
     * 推广单元Id
     */
    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    public CreativeUnit(Long creativeId, Long unitId) {
        this.creativeId = creativeId;
        this.unitId = unitId;
    }
}
