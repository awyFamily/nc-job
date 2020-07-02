package com.yhw.nc.job.admin.core.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 数组DTO
 * @author yhw
 */
@ApiModel(value = "单个列表通用模型")
@Data
public class ListDTO<T> implements Serializable {

    @ApiModelProperty(name = "ids", value = "主键ID列表")
    @NotNull(message = "ids不能为空")
    private List<T> ids;

}
