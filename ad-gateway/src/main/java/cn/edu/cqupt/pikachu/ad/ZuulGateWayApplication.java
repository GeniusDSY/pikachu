package cn.edu.cqupt.pikachu.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author :DengSiYuan
 * @date :2021/1/7 20:30
 * @desc : zuul 网关启动类
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGateWayApplication.class, args);
    }

}
