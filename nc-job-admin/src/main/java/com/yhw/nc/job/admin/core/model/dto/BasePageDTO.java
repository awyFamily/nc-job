package com.yhw.nc.job.admin.core.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页查询通用DTO
 * @author yhw
 */
@Data
public class BasePageDTO {

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数，默认 10")
    private long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页,默认 1")
    private long current = 1;

    /**
     * 需要进行排序的字段
     */
    @ApiModelProperty(value = "需要进行排序的字段")
    private List<String> columns;

    /**
     * 是否正序排列，默认 true
     */
    @ApiModelProperty(value = "是否正序排列，默认 true")
    private boolean asc = true;


}
