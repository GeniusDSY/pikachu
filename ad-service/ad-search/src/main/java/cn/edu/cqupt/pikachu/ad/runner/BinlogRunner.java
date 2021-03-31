package cn.edu.cqupt.pikachu.ad.runner;

import cn.edu.cqupt.pikachu.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author :DengSiYuan
 * @date :2021/3/28 19:45
 * @desc :
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    @Resource
    private BinlogClient client;

    @Override
    public void run(String... args) throws Exception {

        log.info("ad-search:BinlogRunner run -> Coming in BinlogRunner...");
        client.connect();
    }
}
