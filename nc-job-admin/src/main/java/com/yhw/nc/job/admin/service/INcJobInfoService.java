package com.yhw.nc.job.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhw.nc.job.admin.core.model.NcJobInfo;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoDTO;
import com.yhw.nc.job.api.dto.CallbackDTO;

import java.util.List;

public interface INcJobInfoService extends IService<NcJobInfo> {

    Integer addInfo(NcJobInfoDTO dto);

    boolean updateInfo(NcJobInfoDTO dto);

    boolean updateCallback(CallbackDTO callbackDTO);

    /**
     * 立即执行任务
     * @param id
     * @return
     */
    boolean running(Integer id);

    boolean deletes(List<Integer> ids);
}
