package com.yhw.nc.job.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhw.nc.job.admin.core.model.NcJobGroup;
import com.yhw.nc.job.api.dto.RegistryDTO;

import java.util.List;

public interface INcJobGroupService extends IService<NcJobGroup> {

    boolean insertOrUpdate(RegistryDTO dto);

    boolean deletes(List<Integer> ids);
}
