package com.yhw.nc.job.admin.config;

import com.yhw.nc.job.api.constants.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author yhw
 */
@ComponentScan(CommonConstant.GROUP_ID)
@Configuration
public class AdminJobConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
