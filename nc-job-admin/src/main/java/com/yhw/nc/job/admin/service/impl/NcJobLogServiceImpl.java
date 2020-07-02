package com.yhw.nc.job.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhw.nc.job.admin.core.model.NcJobInfo;
import com.yhw.nc.job.admin.core.model.NcJobLog;
import com.yhw.nc.job.admin.core.model.dto.NcJobLogQueryDTO;
import com.yhw.nc.job.admin.core.model.vo.NcJobLogVO;
import com.yhw.nc.job.admin.mapper.NcJobInfoMapper;
import com.yhw.nc.job.admin.mapper.NcJobLogMapper;
import com.yhw.nc.job.admin.service.INcJobLogService;
import com.yhw.nc.job.api.dto.CallbackDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yhw
 */
@AllArgsConstructor
@Service
public class NcJobLogServiceImpl extends ServiceImpl<NcJobLogMapper, NcJobLog> implements INcJobLogService {

    private final NcJobInfoMapper jobInfoMapper;

    @Override
    public void updateByCallback(CallbackDTO dto) {
        IPage<NcJobLog> page = this.page(new Page<>(), Wrappers.<NcJobLog>lambdaQuery().eq(NcJobLog::getJobInfoId, dto.getJobId())
                .orderByDesc(NcJobLog::getCreateTime));

        List<NcJobLog> records = page.getRecords();
        if(CollectionUtil.isNotEmpty(records)){
            NcJobLog jobLog = records.get(0);
            jobLog.setExecutorStatus(dto.getIsSuccess() ? 3 : 2);
            if(StrUtil.isNotEmpty(dto.getJobRemark())){
                jobLog.setLogDesc(dto.getJobRemark());
            }
            this.updateById(jobLog);
        }
    }

    @Override
    public IPage<NcJobLogVO> getAll(NcJobLogQueryDTO dto) {
        LambdaQueryWrapper<NcJobLog> wrapper = Wrappers.lambdaQuery();
        if(dto.getExecutorStatus() != null){
            wrapper.eq(NcJobLog::getExecutorStatus,dto.getExecutorStatus());
        }
        List<NcJobInfo> ncJobInfos = new ArrayList<>();
        if(StrUtil.isNotEmpty(dto.getJobName())){
            ncJobInfos = jobInfoMapper.selectList(Wrappers.<NcJobInfo>lambdaQuery().like(NcJobInfo::getJobName, dto.getJobName()));
            //if current user not find return empty
            if(ncJobInfos == null || ncJobInfos.isEmpty()){
                return new Page<>();
            }
            ncJobInfos.stream().map(NcJobInfo::getId).collect(Collectors.toList());
            wrapper.in(NcJobLog::getJobInfoId, ncJobInfos.stream().map(NcJobInfo::getId).collect(Collectors.toList()));
        }


        wrapper.orderByDesc(NcJobLog::getId);

        IPage page = this.page(new Page<>(dto.getCurrent(), dto.getSize()), wrapper);
        if(page == null || page.getRecords().isEmpty()){
            return new Page<>();
        }

        List<NcJobLog> jobLogs = page.getRecords();
        if(ncJobInfos.isEmpty()){
            Set<Integer> jobInfoIds = jobLogs.stream().map(NcJobLog::getJobInfoId).collect(Collectors.toSet());
            ncJobInfos = jobInfoMapper.selectBatchIds(jobInfoIds);
        }

        Map<Integer, List<NcJobInfo>> maps = new HashMap<>();
        if(!ncJobInfos.isEmpty()){
            maps = ncJobInfos.stream().collect(Collectors.groupingBy(NcJobInfo::getId));
        }


        //packaging result info
        List<NcJobLogVO> logVOS =  new ArrayList<>();
        NcJobLogVO vo;
        for (NcJobLog jobLog : jobLogs) {
            vo = new NcJobLogVO(jobLog,getJobName(maps,jobLog.getJobInfoId()));
            logVOS.add(vo);
        }

        page.setRecords(logVOS);

        return page;
    }

    private String getJobName(Map<Integer, List<NcJobInfo>> map,Integer jobInfoId){
        if(map == null || map.isEmpty()){
            return "";
        }
        List<NcJobInfo> ncJobInfos = map.get(jobInfoId);
        if(ncJobInfos == null || ncJobInfos.isEmpty()){
            return "";
        }
        return ncJobInfos.get(0).getJobName();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean removeInfos(List<Integer> ids) {
        this.removeByIds(ids);
        return false;
    }
}
