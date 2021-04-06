package cn.edu.cqupt.pikachu.ad.model.entity;

import cn.edu.cqupt.pikachu.ad.constants.enums.CommonStatus;
import cn.edu.cqupt.pikachu.ad.constants.enums.UserStatusEnums;
import cn.edu.cqupt.pikachu.ad.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/1/30 22:13
 * @desc : 广告用户数据PO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser {

    /**
     * 用户Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 用户名称
     */
    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 用户年龄
     */
    @Basic
    @Column(name = "age", nullable = false, insertable = false)
    private Integer age;

    /**
     * 用户性别
     */
    @Basic
    @Column(name = "gender", nullable = false, insertable = false)
    private Integer gender;

    /**
     * 用户Token
     */
    @Basic
    @Column(name = "token", nullable = false)
    private String token;

    /**
     * 用户状态
     */
    @Basic
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    /**
     * 用户账户创建时间
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 用户账户更新时间
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUser(Long userId, String username, Integer age, Integer gender, Integer userStatus, String token) {
        this.id = userId;
        this.username = username;
        this.age = (null == age ? -1 : age);
        this.gender = (null == gender ? -1 : gender);
        this.token = token;
        this.userStatus = (null == userStatus ? -1 : userStatus);
        this.createTime = new Date();
        this.updateTime = createTime;
    }

}
