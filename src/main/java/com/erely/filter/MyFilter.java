package com.erely.filter;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class MyFilter extends Filter {

    public int decide(LoggingEvent event) {
        System.out.println("拦截消息");
        return Filter.NEUTRAL;
    }
}
