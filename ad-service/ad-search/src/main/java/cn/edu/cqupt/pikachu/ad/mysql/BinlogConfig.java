package cn.edu.cqupt.pikachu.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author :DengSiYuan
 * @date :2021/3/28 18:56
 * @desc : binlog 配置信息
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {

    /**
     * host
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * binlog名称
     */
    private String binlogName;

    /**
     * 位置
     */
    private Long position;
}
