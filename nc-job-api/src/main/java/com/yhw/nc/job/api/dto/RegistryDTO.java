package com.yhw.nc.job.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 执行器 任务注册模型
 * @author yhw
 */
@NoArgsConstructor
@Data
public class RegistryDTO {

    /**
     * 服务实例id
     */
    private String jobInstanceId;


    /**
     * 任务列表
     */
    private List<String> tasks;

    public RegistryDTO(String jobInstanceId, List<String> tasks){
        this.jobInstanceId = jobInstanceId;
        this.tasks = tasks;
    }
}

