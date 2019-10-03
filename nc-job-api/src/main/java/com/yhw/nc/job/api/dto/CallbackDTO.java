package com.yhw.nc.job.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 执行器：任务回调模型
 */
@NoArgsConstructor
@Data
public class CallbackDTO {

    /**
     * 是否执行成功
     */
    private Boolean isSuccess;

    /**
     * 任务执行描述
     */
    private String jobRemark;

    /**
     * 任务主键id
     */
    private Integer jobId;

    public CallbackDTO(Boolean isSuccess,String jobRemark,Integer jobId){
        this.isSuccess = isSuccess;
        this.jobRemark = jobRemark;
        this.jobId = jobId;
    }
}
