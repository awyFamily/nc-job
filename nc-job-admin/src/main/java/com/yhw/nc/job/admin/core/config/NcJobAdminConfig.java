package com.yhw.nc.job.admin.core.config;

import com.yhw.nc.job.admin.mapper.NcJobInfoMapper;
import com.yhw.nc.job.admin.service.INcJobGroupService;
import com.yhw.nc.job.admin.service.INcJobLogService;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Component
public class NcJobAdminConfig implements InitializingBean{
    private static NcJobAdminConfig adminConfig = null;
    public static NcJobAdminConfig getAdminConfig() {
        return adminConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        adminConfig = this;
    }

    @Getter
    @Resource
    private DiscoveryClient discoveryClient;

    @Getter
    @Resource
    private RestTemplate restTemplate;

    @Getter
    @Resource
    private DataSource dataSource;
    @Getter
    @Resource
    private NcJobInfoMapper jobInfoMapper;
    @Getter
    @Resource
    private INcJobGroupService jobGroupService;

    @Getter
    @Resource
    private INcJobLogService jobLogService;

}
