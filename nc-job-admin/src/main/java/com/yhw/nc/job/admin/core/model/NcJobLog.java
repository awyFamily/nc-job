package com.yhw.nc.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class NcJobLog extends Model<NcJobLog> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 任务主键ID
     */
    private Integer jobInfoId;

    /**
     * 触发类型
     */
    private int triggerType;

    /**
     * 执行状态
     * 执行中  0
     * 调度失败 1
     * 任务执行失败 2
     * 任务执行成功 3
     */
    private Integer executorStatus;

    /**
     * 已重试次数
     */
    private Integer retryNumber;

    /**
     * 日志信息详情
     */
    private String logDesc;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    public NcJobLog(Integer jobInfoId,Integer triggerType){
        this.jobInfoId = jobInfoId;
        this.triggerType = triggerType;
        this.retryNumber = 0;
        this.executorStatus = 0;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

}
