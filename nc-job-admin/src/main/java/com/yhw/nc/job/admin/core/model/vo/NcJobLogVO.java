package com.yhw.nc.job.admin.core.model.vo;

import com.yhw.nc.job.admin.core.model.NcJobLog;
import com.yhw.nc.job.admin.core.model.enums.ExecutorStatusEnum;
import com.yhw.nc.job.admin.core.trigger.TriggerTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yhw
 */
@NoArgsConstructor
@ApiModel(value = "任务日志模型")
@Data
public class NcJobLogVO{

    @ApiModelProperty(value = "日志主键id")
    private Integer id;

    /**
     * 任务主键ID
     */
    @ApiModelProperty(value = "任务主键id")
    private Integer jobInfoId;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    /**
     * 触发类型
     */
    @ApiModelProperty(value = "触发类型")
    private int triggerType;

    @ApiModelProperty(value = "触发类型名称")
    public String triggerTypeName(){
        TriggerTypeEnum type = TriggerTypeEnum.getType(this.triggerType);
        if(type != null){
            return type.getTitle();
        }
        return "";
    }

    /**
     * 执行状态
     * 执行中  0
     * 调度失败 1
     * 任务执行失败 2
     * 任务执行成功 3
     */
    @ApiModelProperty(value = "执行状态")
    private Integer executorStatus;

    @ApiModelProperty(value = "执行状态名称")
    public String getExecutorStatusName(){
        if(this.executorStatus != null){
            ExecutorStatusEnum type = ExecutorStatusEnum.getType(this.executorStatus);
            if(type != null){
                return type.getDescription();
            }
        }
        return "";
    }

    /**
     * 已重试次数
     */
    @ApiModelProperty(value = "已重试次数")
    private Integer retryNumber;

    /**
     * 日志信息详情
     */
    @ApiModelProperty(value = "日志信息详情")
    private String logDesc;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


    public NcJobLogVO(NcJobLog jobLog, String jobName){
        this.id = jobLog.getId();
        this.jobInfoId = jobLog.getJobInfoId();
        this.triggerType = jobLog.getTriggerType();
        this.executorStatus = jobLog.getExecutorStatus();
        this.retryNumber = jobLog.getRetryNumber();
        this.logDesc = jobLog.getLogDesc();
        this.createTime = jobLog.getCreateTime();
        this.updateTime = jobLog.getUpdateTime();

        this.jobName = jobName;
    }

}
