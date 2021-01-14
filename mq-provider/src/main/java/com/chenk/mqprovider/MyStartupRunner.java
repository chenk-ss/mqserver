package com.chenk.mqprovider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import util.MyClient;

/**
 * @Author chenk
 * @create 2020/12/23 22:36
 */
@Slf4j
@Component
public class MyStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("---loading---");
        new MyClient();
        log.info("---loading success---");
    }
}
