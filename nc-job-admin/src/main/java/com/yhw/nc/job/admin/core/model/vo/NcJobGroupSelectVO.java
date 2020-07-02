package com.yhw.nc.job.admin.core.model.vo;

import com.yhw.nc.job.admin.core.model.NcJobGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务组下拉选项VO
 * @author yhw
 */
@ApiModel(value = "任务组下拉选项模型")
@NoArgsConstructor
@Data
public class NcJobGroupSelectVO {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "handler唯一名称")
    private String name;

    public NcJobGroupSelectVO(NcJobGroup jobGroup){
        this.id = jobGroup.getId();
        this.name = jobGroup.getName();
    }
}
