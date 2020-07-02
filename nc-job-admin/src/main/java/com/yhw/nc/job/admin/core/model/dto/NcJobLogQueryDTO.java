package com.yhw.nc.job.admin.core.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author yhw
 */
@ApiModel(value = "任务日志分页查询模型")
@Data
public class NcJobLogQueryDTO extends BasePageDTO {

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "执行状态 [0-执行中] [1-调度失败] [2-任务执行失败] [3-任务执行成功]")
    private Integer executorStatus;


}
