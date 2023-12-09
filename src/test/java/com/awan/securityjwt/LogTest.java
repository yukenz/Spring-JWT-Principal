package com.awan.securityjwt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class LogTest {

    @Test
    void logVerb() {
        log.info("info");
        log.warn("warn");
        log.error("error",new RuntimeException());
        log.trace("trace");
        log.debug("debug");
    }

    @Test
    void name() {
    }
}
