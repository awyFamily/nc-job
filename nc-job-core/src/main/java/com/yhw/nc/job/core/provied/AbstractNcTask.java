package com.yhw.nc.job.core.provied;

import cn.hutool.core.date.DatePattern;
import com.yhw.nc.job.api.constants.JobCommonConstant;
import com.yhw.nc.job.api.dto.CallbackDTO;
import com.yhw.nc.job.core.config.JobConfig;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 任务实现基类类
 * @author yhw
 * @date 2019-09-30
 */
@Slf4j
public abstract class AbstractNcTask {

    /**
     * 任务运行方法
     * @param jobId 任务主键Id
     * @param parameter 任务执行参数
     */
    protected abstract void run(Integer jobId, String parameter);

    /**
     * 获取任务唯一名称
     * @return 任务唯一名称
     */
    protected abstract String getName();


    public void callBackAdmin(Integer jobId, String parameter){
        try {
            log.info("开始执行：".concat(getName()).concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN))));
            run(jobId,parameter);
            //response executor success
            callbackRequest(true, JobCommonConstant.TASK_EXECUTOR_REMARK,jobId);
        } catch (Exception e) {
            //response executor error
            callbackRequest(false,e.getMessage(),jobId);
        }
    }

    private void callbackRequest(Boolean isSuccess,String jobRemark,Integer jobId){
        JobConfig.getJobConfig().getJobAdminCallback().callback(new CallbackDTO(isSuccess,jobRemark,jobId));
    }
}
