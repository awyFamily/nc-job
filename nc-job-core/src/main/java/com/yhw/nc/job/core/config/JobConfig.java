package com.yhw.nc.job.core.config;

import com.yhw.nc.job.api.feign.IJobAdminCallback;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yhw
 * @date 2019-09-30
 */
@Component
public class JobConfig implements InitializingBean {

    private static JobConfig jobConfig = null;

    public static JobConfig getJobConfig() {
        return jobConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception{
        jobConfig = this;
    }

    @Getter
    @Resource
    private IJobAdminCallback jobAdminCallback;
}
