package com.yhw.nc.job.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author yhw
 * @date 2019-09-30
 */
@ConfigurationProperties(prefix = "nc.job.provider")
@Component
@Data
public class JobProviderPreperties {


    /**
     * current server instance
     */
    private String jobServerId;

    /**
     * job class package path
     */
//    private String taskPackagePath;

    /**
     * 任务提供者  task调用路径(如果做了服务认证需要放行)
     * 例如：/jobControl/start
     */
//    private String providerUrl;



    //需要注册当前job服务的所有任务

}
