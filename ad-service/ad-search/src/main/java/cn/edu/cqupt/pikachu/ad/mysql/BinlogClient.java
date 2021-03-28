package cn.edu.cqupt.pikachu.ad.mysql;

import cn.edu.cqupt.pikachu.ad.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author :DengSiYuan
 * @date :2021/3/28 19:06
 * @desc : Binlog客户端
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;

    @Resource
    private BinlogConfig config;

    @Resource
    private AggregationListener listener;

    /**
     * 连接MySQL
     */
    public void connect() {

        new Thread(() -> {
            client = new BinaryLogClient(
                   config.getHost(), config.getPort(), config.getUsername(), config.getPassword()
            );

            if (StringUtils.isNotEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            client.registerEventListener(listener);

            try {
                log.info("BinlogClient connect -> connecting to mysql start");
                client.connect();
                log.info("BinlogClient connect -> connecting to mysql done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
