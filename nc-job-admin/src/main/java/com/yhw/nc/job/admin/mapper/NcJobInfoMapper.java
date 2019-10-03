package com.yhw.nc.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhw.nc.job.admin.core.model.NcJobInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NcJobInfoMapper extends BaseMapper<NcJobInfo> {

    boolean scheduleUpdate(NcJobInfo info);

    List<NcJobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime);
}
