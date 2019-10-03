package com.yhw.nc.job.core.endpoint;

import com.yhw.nc.job.api.constants.JobCommonConstant;
import com.yhw.nc.job.api.dto.AdminConsumerDTO;
import com.yhw.nc.job.api.model.ApiResult;
import com.yhw.nc.job.core.provied.AbstractNcTask;
import com.yhw.nc.job.core.provied.NcTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * task executor endpoint
 * @author yhw
 * @date 2019-09-30
 */
@Slf4j
@RestController
public class NcTaskEndpoint {

    @PostMapping(JobCommonConstant.PROVIDER_CONTROLLER_API_PATH)
    public ApiResult runTask(@RequestBody AdminConsumerDTO dto){
        try {
            log.info("begin executor task ......................");
            AbstractNcTask cmdProcess = NcTaskManager.getInstance().getCmdProcess(dto.getHandlerName());
            if(cmdProcess == null){
                return ApiResult.error("task " + dto.getHandlerName() + " not exists");
            }
            cmdProcess.callBackAdmin(dto.getJobId(),dto.getParameter());
            return ApiResult.ok("success");
        } catch (Exception e) {
            log.error("task executor error ",e);
            return ApiResult.error(e);
        }
    }
}
