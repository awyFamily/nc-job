package com.yhw.nc.job.admin.controller;

import com.yhw.nc.job.admin.core.model.dto.ListDTO;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoDTO;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoQueryDTO;
import com.yhw.nc.job.admin.service.INcJobInfoService;
import com.yhw.nc.job.api.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yhw
 */
@Api(value = "任务管理服务" , tags = "任务管理服务")
@AllArgsConstructor
@Slf4j
@RequestMapping("/jobInfo")
@RestController
public class NcJobInfoController {

    private final INcJobInfoService jobInfoService;

    @ApiOperation(value = "添加任务", notes = "添加单个任务")
    @PostMapping("/add")
    public ApiResult<Integer> add(@Valid @RequestBody NcJobInfoDTO dto){
        try{
            return ApiResult.ok(jobInfoService.addInfo(dto));
        }catch (Exception e){
            log.error("add job error:",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "修改任务", notes = "修改单个任务")
    @PostMapping("/update")
    public ApiResult<Integer> updateInfo(@Valid @RequestBody NcJobInfoDTO dto){
        try{
            return ApiResult.ok(jobInfoService.updateInfo(dto));
        }catch (Exception e){
            log.error("update job error:",e);
            return ApiResult.error(e);
        }
    }


    @ApiOperation(value = "删除任务", notes = "删除单个任务")
    @PostMapping("/remove/{id}")
    public ApiResult removeInfo(@PathVariable("id") Integer id){
        try{
            List<Integer> ids = new ArrayList<>();
            ids.add(id);
            return ApiResult.ok(jobInfoService.deletes(ids));
        }catch (Exception e){
            log.error("remove job error:",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "批量删除任务", notes = "批量删除任务")
    @PostMapping("/removes")
    public ApiResult removeInfos(@Valid @RequestBody ListDTO<Integer> dto){
        try{
            return ApiResult.ok(jobInfoService.deletes(dto.getIds()));
        }catch (Exception e){
            log.error("batch remove job error:",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "立即执行任务")
    @GetMapping("/running/{id}")
    public ApiResult running(@PathVariable("id") Integer id){
        try{
            return ApiResult.ok(jobInfoService.running(id));
        }catch (Exception e){
            log.error("running error:",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "任务分页查询")
    @PostMapping("/page")
    public ApiResult getPage(@RequestBody NcJobInfoQueryDTO dto){
        try{
            return ApiResult.ok(jobInfoService.getAll(dto));
        }catch (Exception e){
            log.error("page query error:",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "更新任务定时表达式(暂时未实现)")
    @PostMapping("/updateCron")
    public ApiResult updateCron(){
        return ApiResult.ok();
    }

}
