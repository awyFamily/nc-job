package com.yhw.nc.job.admin.core.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "新增任务模型")
@Data
public class NcJobInfoDTO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    /**
     * 执行器主键ID
     */
    @NotNull
    @ApiModelProperty(value = "执行器主键ID")
    private Integer jobGroupId;

    /**
     * 任务执行CRON表达式
     */
    @ApiModelProperty(value = "任务执行CRON表达式")
    private String jobCron;

    @ApiModelProperty(value = "任务说明")
    private String jobDesc;

    /**
     * 报警邮件
     */
    @ApiModelProperty(value = "报警邮件")
    private String alarmEmail;

    /**
     * 执行器路由策略
     */
    @ApiModelProperty(value = "执行器路由策略")
    private Integer routeStrategy;

    /**
     * 执行器，任务参数
     */
    @ApiModelProperty(value = "执行任务参数")
    private String executorParam;

    /**
     * 失败重试次数
     */
    @ApiModelProperty(value = "失败重试次数")
    private int executorFailRetryCount;
}
