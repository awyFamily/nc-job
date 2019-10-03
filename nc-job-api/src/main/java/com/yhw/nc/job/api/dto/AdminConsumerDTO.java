package com.yhw.nc.job.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * admin : 消费任务模型
 * @author yhw
 */
@NoArgsConstructor
@Data
public class AdminConsumerDTO {
    /**
     * 任务唯一名称
     */
    private String handlerName;
    /**
     * 任务主键ID
     */
    private Integer jobId;
    /**
     * 任务执行参数
     */
    private String parameter;

    public AdminConsumerDTO(String handlerName,Integer jobId,String parameter){
        this.handlerName = handlerName;
        this.jobId = jobId;
        this.parameter = parameter;
    }
}
