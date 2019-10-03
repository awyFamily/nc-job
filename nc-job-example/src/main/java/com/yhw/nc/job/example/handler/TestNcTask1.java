package com.yhw.nc.job.example.handler;

import cn.hutool.core.date.DatePattern;
import com.yhw.nc.job.core.provied.AbstractNcTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class TestNcTask1 extends AbstractNcTask {

    @Override
    protected void run(Integer jobId, String parameter) {
        log.info(getName()+"开始运行了:".concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN))));
    }

    @Override
    protected String getName() {
        return "测试任务1";
    }
}
