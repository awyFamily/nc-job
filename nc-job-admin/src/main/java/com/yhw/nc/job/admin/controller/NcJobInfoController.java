package com.yhw.nc.job.admin.controller;

import com.yhw.nc.job.admin.core.model.dto.NcJobInfoDTO;
import com.yhw.nc.job.admin.service.INcJobInfoService;
import com.yhw.nc.job.api.model.ApiResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RequestMapping("/jobInfo")
@RestController
public class NcJobInfoController {

    private final INcJobInfoService jobInfoService;

    @PostMapping("/add")
    public ApiResult<Integer> add(@RequestBody NcJobInfoDTO dto){
        try{
            return ApiResult.ok(jobInfoService.addInfo(dto));
        }catch (Exception e){
            log.error("add job error:",e);
            return ApiResult.error(e);
        }
    }

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
