package com.yhw.nc.job.admin.controller;

import com.yhw.nc.job.admin.core.model.dto.NcJobInfoDTO;
import com.yhw.nc.job.admin.service.INcJobInfoService;
import com.yhw.nc.job.api.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yhw
 */
@Api(value = "任务管理")
@AllArgsConstructor
@Slf4j
@RequestMapping("/jobInfo")
@RestController
public class NcJobInfoController {

    private final INcJobInfoService jobInfoService;

    @ApiOperation(value = "新增任务")
    @PostMapping("/add")
    public ApiResult<Integer> add(@Valid @RequestBody NcJobInfoDTO dto){
        try{
            return ApiResult.ok(jobInfoService.addInfo(dto));
        }catch (Exception e){
            log.error("add job error:",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "更新任务")
    @PostMapping("/update")
    public ApiResult updateInfo(@RequestBody NcJobInfoDTO dto){
        try{
            return ApiResult.ok(jobInfoService.updateInfo(dto));
        }catch (Exception e){
            log.error("update job error:",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "任务立即执行")
    @GetMapping("/running/{id}")
    public ApiResult running(@PathVariable("id") Integer id){
        try{
            return ApiResult.ok(jobInfoService.running(id));
        }catch (Exception e){
            log.error("running error:",e);
            return ApiResult.error(e);
        }
    }

}
