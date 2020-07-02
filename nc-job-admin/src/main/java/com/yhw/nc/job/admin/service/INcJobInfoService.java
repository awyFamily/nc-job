package com.yhw.nc.job.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhw.nc.job.admin.core.model.NcJobInfo;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoDTO;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoQueryDTO;
import com.yhw.nc.job.api.dto.CallbackDTO;

import java.util.List;

/**
 * @author yhw
 */
public interface INcJobInfoService extends IService<NcJobInfo> {

    /**
     * 添加单个任务
     * @param dto 模型
     * @return 主键id
     */
    Integer addInfo(NcJobInfoDTO dto);

    /**
     * 修改单个任务
     * @param dto 模型
     * @return 布尔值
     */
    boolean updateInfo(NcJobInfoDTO dto);

    /**
     * 分页查询
     * @param dto 查询模型
     * @return 分页模型
     */
    IPage<NcJobInfo> getAll(NcJobInfoQueryDTO dto);

    /**
     * 回调更新
     * @param callbackDTO 模型
     * @return 布尔值
     */
    boolean updateCallback(CallbackDTO callbackDTO);

    /**
     * 立即执行任务
     * @param id 主键id
     * @return 布尔值
     */
    boolean running(Integer id);

    /**
     * 批量删除任务
     * @param ids 主键id列表
     * @return 布尔值
     */
    boolean deletes(List<Integer> ids);
}
