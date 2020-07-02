package com.yhw.nc.job.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhw.nc.job.admin.core.model.NcJobLog;
import com.yhw.nc.job.admin.core.model.dto.NcJobLogQueryDTO;
import com.yhw.nc.job.admin.core.model.vo.NcJobLogVO;
import com.yhw.nc.job.api.dto.CallbackDTO;

import java.util.List;

public interface INcJobLogService extends IService<NcJobLog> {


    /**
     * 更新调度日志
     * @param dto 更新模型
     */
    void updateByCallback(CallbackDTO dto);

    /**
     * 分页查询
     * @param  dto 查询模型
     * @return 分页模型
     */
    IPage<NcJobLogVO> getAll(NcJobLogQueryDTO dto);


    /**
     * 批量删除
     * @param ids id
     * @return 布尔值
     */
    boolean removeInfos(List<Integer> ids);
}
