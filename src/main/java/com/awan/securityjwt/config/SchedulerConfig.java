package com.awan.securityjwt.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {

    @Scheduled(fixedRate = 1000L * 5L)
    public void doEvery10Second() {
        String methodName = "doEvery10Second";
        log.info("{}() was executed", getClass().getName() + methodName);
    }

}
