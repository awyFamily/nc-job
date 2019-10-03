package com.yhw.nc.job.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhw.nc.job.admin.core.model.NcJobGroup;
import com.yhw.nc.job.admin.mapper.NcJobGroupMapper;
import com.yhw.nc.job.admin.service.INcJobGroupService;
import com.yhw.nc.job.api.dto.RegistryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NcJobGroupServiceImpl extends ServiceImpl<NcJobGroupMapper, NcJobGroup> implements INcJobGroupService {


    @Override
    public boolean insertOrUpdate(RegistryDTO dto) {
        dto.getJobInstanceId();
        List<NcJobGroup> saveList = new ArrayList<>();
        dto.getTasks().stream().forEach(name ->{
            List<NcJobGroup> exists = this.list(Wrappers.<NcJobGroup>lambdaQuery().eq(NcJobGroup::getName, name)
                    .eq(NcJobGroup::getServerId, dto.getJobInstanceId()));
            if(CollectionUtil.isEmpty(exists)){
                saveList.add(new NcJobGroup(dto.getJobInstanceId(),name));
            }
        });
        if(CollectionUtil.isNotEmpty(saveList)){
            this.saveBatch(saveList);
        }
        return true;
    }

    @Override
    public boolean deletes(List<Integer> ids) {
        return false;
    }
}
