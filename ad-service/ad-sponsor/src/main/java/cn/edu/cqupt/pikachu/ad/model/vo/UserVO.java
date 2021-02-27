package cn.edu.cqupt.pikachu.ad.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author :DengSiYuan
 * @date :2021/2/5 15:24
 * @desc : 用户展示信息数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户Token
     */
    private String token;

    /**
     * 用户账户创建时间
     */
    private Date createTime;

    /**
     * 用户账户更新时间
     */
    private Date updateTime;

}
