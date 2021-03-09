package cn.edu.cqupt.pikachu.ad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author :DengSiYuan
 * @date :2021/2/5 15:18
 * @desc : 用户信息传入实体数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * 用户名称
     */
    private String username;

    public boolean validate() {
        return StringUtils.isNotEmpty(username);
    }


}
