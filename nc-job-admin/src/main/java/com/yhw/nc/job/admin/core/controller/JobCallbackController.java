package com.yhw.nc.job.admin.core.controller;

import com.yhw.nc.job.admin.service.INcJobGroupService;
import com.yhw.nc.job.admin.service.INcJobInfoService;
import com.yhw.nc.job.admin.service.INcJobLogService;
import com.yhw.nc.job.api.dto.CallbackDTO;
import com.yhw.nc.job.api.dto.RegistryDTO;
import com.yhw.nc.job.api.feign.IJobAdminCallback;
import com.yhw.nc.job.api.model.ApiResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yhw
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("/jobCallback")
@RestController
public class JobCallbackController implements IJobAdminCallback {

    private final INcJobGroupService jobGroupService;

    private final INcJobInfoService jobInfoService;

    private  final INcJobLogService jobLogService;



    @PostMapping("/callback")
    @Override
    public ApiResult callback(@RequestBody CallbackDTO callbackDTO) {
        try{
            //记录任务执行是否成功 状态信息
            jobInfoService.updateCallback(callbackDTO);
            jobLogService.updateByCallback(callbackDTO);
            return ApiResult.ok();
        }catch (Exception e){
            log.error("任务回调异常：",e);
            return ApiResult.error(e);
        }
    }

    @PostMapping("/registry")
    @Override
    public ApiResult registry(@RequestBody RegistryDTO registryDTO) {
        try {
            return ApiResult.ok(jobGroupService.insertOrUpdate(registryDTO));
        }catch (Exception e){
            log.error("任务注册异常：",e);
            return ApiResult.error(e);
        }
    }

}
