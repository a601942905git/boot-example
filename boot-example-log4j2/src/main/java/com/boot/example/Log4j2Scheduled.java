package com.boot.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.boot.example.Log4j2Scheduled
 *
 * @author lipeng
 * @date 2019/1/9 下午4:29
 */
@Component
public class Log4j2Scheduled {

    private Logger logger = LoggerFactory.getLogger(Log4j2Scheduled.class);

    /**
     * 2秒钟执行1次
     */
    @Scheduled(fixedRate = 20)
    public void logging(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        logger.info(simpleDateFormat.format(now));
        logger.debug("-------DEBUG---------");
        logger.warn("-------WARN---------");
        logger.error(String.valueOf(now.getTime()));
    }
}
