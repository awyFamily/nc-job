package com.yhw.nc.job.api.feign;

import com.yhw.nc.job.api.dto.CallbackDTO;
import com.yhw.nc.job.api.dto.RegistryDTO;
import com.yhw.nc.job.api.model.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yhw
 */
@FeignClient(value = "nc-job-admin", path = "/jobCallback")
public interface IJobAdminCallback {

    /**
     * 任务回调
     * @param callbackDTO 参数模型
     * @return common result
     */
    @PostMapping("/callback")
    ApiResult callback(@RequestBody CallbackDTO callbackDTO);

    /**
     * 任务注册
     * @param registryDTO 参数模型
     * @return common result
     */
    @PostMapping("/registry")
    ApiResult registry(@RequestBody RegistryDTO registryDTO);
}
