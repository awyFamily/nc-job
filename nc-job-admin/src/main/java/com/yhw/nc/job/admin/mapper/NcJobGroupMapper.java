package com.yhw.nc.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhw.nc.job.admin.core.model.NcJobGroup;
import com.yhw.nc.job.admin.core.model.dto.UpdateGroupHasAvailableDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yhw
 * @date 2019-10-11
 */
public interface NcJobGroupMapper extends BaseMapper<NcJobGroup> {

    /**
     * 更新可用状态
     * @param dto model
     * @return 受影响行数
     */
    Integer updateBatchHasAvailable(@Param("dto") UpdateGroupHasAvailableDTO dto);


}
