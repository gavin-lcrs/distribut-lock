package com.gavin.distributlock.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @Author jiwen.cao
 * @Date 2021/6/18
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    private Integer num = 0;

    @GetMapping("/unlock")
    public String testUnLock(){
        String s = Thread.currentThread().getName();
        String result = "";
        if (num < 20) {
            log.info("{}=>排号成功，号码是{}", s, num);
            result = String.format("排号成功，号码是%s", num.toString());
            num++;
        } else {
            log.info("{}=>排号失败，号码已抢光", s);
            result = String.format("排号失败");
        }
        return result;
    }

    @GetMapping("/lock")
    public void tetLock() throws InterruptedException {
        Lock lock = redisLockRegistry.obtain("lock");
        boolean isLock = lock.tryLock(5, TimeUnit.SECONDS);
        String s = Thread.currentThread().getName();
        if (num < 100 && isLock) {
            log.info("{}=>排号成功，号码是{}", s, num);
            num++;
            Thread.sleep(500L);
            lock.unlock();
        } else {
            log.info("{}=>本轮排号已结束", s);
        }
    }
}
