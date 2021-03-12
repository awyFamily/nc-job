package com.yhw.nc.job.core.config;

import com.yhw.nc.job.api.constants.CommonConstant;
import com.yhw.nc.job.api.constants.JobCommonConstant;
import com.yhw.nc.job.api.feign.IJobAdminCallback;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yhw
 * @date 2019-09-30
 */
@Configuration
@ComponentScan(basePackages = CommonConstant.GROUP_ID)
@EnableFeignClients(basePackages = {JobCommonConstant.ADMIN_FEIGN_REMOTE_PATH} )
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
