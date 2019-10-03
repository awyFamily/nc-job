package com.yhw.nc.job.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhw.nc.job.admin.core.model.NcJobLog;
import com.yhw.nc.job.admin.mapper.NcJobLogMapper;
import com.yhw.nc.job.admin.service.INcJobLogService;
import com.yhw.nc.job.api.dto.CallbackDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NcJobLogServiceImpl extends ServiceImpl<NcJobLogMapper, NcJobLog> implements INcJobLogService {


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
}
