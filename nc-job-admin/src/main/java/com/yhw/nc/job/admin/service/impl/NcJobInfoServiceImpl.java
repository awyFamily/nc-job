package com.yhw.nc.job.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhw.nc.job.admin.core.cron.CronExpression;
import com.yhw.nc.job.admin.core.model.NcJobInfo;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoDTO;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoQueryDTO;
import com.yhw.nc.job.admin.core.thread.JobTriggerPoolHelper;
import com.yhw.nc.job.admin.core.trigger.TriggerTypeEnum;
import com.yhw.nc.job.admin.mapper.NcJobInfoMapper;
import com.yhw.nc.job.admin.service.INcJobInfoService;
import com.yhw.nc.job.api.dto.CallbackDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yhw
 */
@Service
public class NcJobInfoServiceImpl extends ServiceImpl<NcJobInfoMapper, NcJobInfo> implements INcJobInfoService {

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer addInfo(NcJobInfoDTO dto) {
        NcJobInfo ncJobInfo = new NcJobInfo();
        BeanUtil.copyProperties(dto,ncJobInfo);
        if(StrUtil.isNotEmpty(dto.getJobCron())){
            try{
                long triggerNextTime   = new CronExpression(dto.getJobCron())
                        .getNextValidTimeAfter(new Date())
                        .getTime();
                ncJobInfo.setTriggerNextTime(triggerNextTime);
                ncJobInfo.setTriggerStatus(0);
            }catch (Exception e){
                throw new RuntimeException("cron 表达式错误!");
            }
        }else {
            ncJobInfo.setTriggerStatus(2);
        }
        this.save(ncJobInfo);
        return ncJobInfo.getId();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean updateInfo(NcJobInfoDTO dto) {
        NcJobInfo jobInfo = this.getById(dto.getId());
        Assert.isFalse(jobInfo == null,"job not exists");

        String oldCron = jobInfo.getJobCron();
        BeanUtil.copyProperties(dto,jobInfo);

        if(StrUtil.isNotEmpty(dto.getJobCron()) && !dto.getJobCron().equals(oldCron)){
            try{
                long triggerNextTime   = new CronExpression(dto.getJobCron())
                        .getNextValidTimeAfter(new Date())
                        .getTime();
                jobInfo.setTriggerNextTime(triggerNextTime);
                jobInfo.setTriggerStatus(0);
            }catch (Exception e){
                throw new RuntimeException("cron 表达式错误!");
            }
        }
        if(StrUtil.isEmpty(dto.getJobCron())){
            jobInfo.setTriggerStatus(2);
        }
        this.updateById(jobInfo);
        return true;
    }

    @Override
    public IPage<NcJobInfo> getAll(NcJobInfoQueryDTO dto) {
        return this.page(new Page<>(dto.getCurrent(),dto.getSize()));
    }

    @Override
    public boolean updateCallback(CallbackDTO callbackDTO) {
        NcJobInfo jobInfo = this.getById(callbackDTO.getJobId());
        Assert.isFalse(jobInfo == null,"job not exists");
        if(jobInfo.getTriggerStatus() != 2 ){
            jobInfo.setTriggerStatus(0);
        }
        this.updateById(jobInfo);
        return true;
    }


    @Override
    public boolean running(Integer id) {
        NcJobInfo jobInfo = this.getById(id);
        Assert.isFalse(jobInfo == null,"job not exists");
        JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL);
        return true;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean deletes(List<Integer> ids) {
        this.removeByIds(ids);
        return true;
    }
}
