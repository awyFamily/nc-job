package com.yhw.nc.job.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhw.nc.job.admin.core.model.NcJobLog;
import com.yhw.nc.job.api.dto.CallbackDTO;

public interface INcJobLogService extends IService<NcJobLog> {


    //更新调度日志
    void updateByCallback(CallbackDTO dto);

    //分页查询

    //查询明细
}
