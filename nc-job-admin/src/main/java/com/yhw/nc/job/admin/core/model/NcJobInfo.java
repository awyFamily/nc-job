package com.yhw.nc.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NcJobInfo {

    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 执行器主键ID
     */
    private int jobGroupId;

    /**
     * 任务执行CRON表达式
     */
    private String jobCron;

    private String jobDesc;

    /**
     * 报警邮件
     */
    private String alarmEmail;

    /**
     * 执行器路由策略
     */
    private Integer routeStrategy;

    /**
     * 执行器，任务参数
     */
    private String executorParam;

    /**
     * 失败重试次数
     */
    private int executorFailRetryCount;

    /**
     * 调度状态：0-停止，1-运行
     */
    private int triggerStatus;


    /**
     * 上次调度时间
     */
    private long triggerLastTime;
    /**
     * 下次调度时间
     */
    private long triggerNextTime;


    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String remark;

    @TableLogic
    private Integer hasDelete;

}
