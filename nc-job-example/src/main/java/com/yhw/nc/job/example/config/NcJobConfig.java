package com.yhw.nc.job.example.config;

import com.yhw.nc.job.api.constants.JobCommonConstant;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yhw
 */
@EnableFeignClients(JobCommonConstant.ADMIN_FEIGN_REMOTE_PATH)
@ComponentScan(JobCommonConstant.JOB_SCAN_PACKAGE_PATH)
@Configuration
public class NcJobConfig {

}
