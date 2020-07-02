package com.yhw.nc.job.api.constants;

public interface CommonConstant {

    String GROUP_ID = "com.yhw.nc";

    /**
     * 响应成功
     */
    Integer RESPONSE_SUCCESS = 200;

    /**
     * 响应失败
     */
    Integer RESPONSE_ERROR = 500;

    /**
     * 响应超时
     */
    Integer RESPONSE_TIMEOUT = 502;

    /**
     * admin 监听时间 分钟
     */
    Integer DEFAULT_ADMIN_MONITOR_TIME = 5;

    /**
     * 执行器心态请求时间 分钟
     */
    Integer DEFAULT_EXECUTOR_HEARTBEAT_TIME = 3;

}
