package com.yhw.nc.job.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yhw.nc.job.admin.core.model.NcJobGroup;
import com.yhw.nc.job.admin.core.model.dto.ListDTO;
import com.yhw.nc.job.admin.core.model.dto.NcJobLogQueryDTO;
import com.yhw.nc.job.admin.core.model.vo.NcJobGroupSelectVO;
import com.yhw.nc.job.admin.core.model.vo.NcJobLogVO;
import com.yhw.nc.job.admin.mapper.NcJobGroupMapper;
import com.yhw.nc.job.admin.service.INcJobLogService;
import com.yhw.nc.job.api.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yhw
 */
@Slf4j
@Api(value = "任务日志", tags = "任务日志服务")
@AllArgsConstructor
@RequestMapping("/jobLog")
@RestController
public class NcJobLogController {

    private final INcJobLogService jobLogService;

    @ApiOperation(value = "任务日志分页查询")
    @PostMapping("/page")
    public ApiResult<IPage<NcJobLogVO>> getPage(@RequestBody NcJobLogQueryDTO dto){
        try {
            return ApiResult.ok(jobLogService.getAll(dto));
        } catch (Exception e) {
            log.error("任务日志分页查询异常",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "根据id删除日志")
    @GetMapping("/remove/{id}")
    public ApiResult removeInfo(@PathVariable("id") Integer id){
        try {
            List<Integer> ids = new ArrayList<>();
            ids.add(id);
            return ApiResult.ok(jobLogService.removeInfos(ids));
        } catch (Exception e) {
            log.error("根据id删除日志异常",e);
            return ApiResult.error(e);
        }
    }

    @ApiOperation(value = "根据id批量删除")
    @PostMapping("/removes")
    public ApiResult removeInfos(@RequestBody ListDTO<Integer> dto){
        try {
            return ApiResult.ok(jobLogService.removeInfos(dto.getIds()));
        } catch (Exception e) {
            log.error("根据id批量删除异常",e);
            return ApiResult.error(e);
        }
    }

    private final NcJobGroupMapper jobGroupMapper;

    @ApiOperation(value = "获取所有的任务组【下拉框】")
    @PostMapping("/getGroups")
    public ApiResult getGroups(){
        try {
            List<NcJobGroup> ncJobGroups = jobGroupMapper.selectList(Wrappers.<NcJobGroup>lambdaQuery().eq(NcJobGroup::getHasAvailable, true));
            if(ncJobGroups == null || ncJobGroups.isEmpty()){
                return ApiResult.ok(new ArrayList<>());
            }
            List<NcJobGroupSelectVO> results = ncJobGroups.stream().map(obj -> {
                return new NcJobGroupSelectVO(obj);
            }).collect(Collectors.toList());

            return ApiResult.ok(results);
        } catch (Exception e) {
            log.error("获取任务组列表异常",e);
            return ApiResult.error(e);
        }
    }

}
