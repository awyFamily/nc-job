package com.yhw.nc.job.admin.core.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yhw
 */
@NoArgsConstructor
@Data
public class UpdateGroupHasAvailableDTO {

    private List<Integer> ids;

    /**
     * handler是否可用
     */
    private Boolean hasAvailable;


    private LocalDateTime updateTime;

    public UpdateGroupHasAvailableDTO(List<Integer> ids,Boolean hasAvailable){
        this.ids = ids;
        this.hasAvailable = hasAvailable;
        this.updateTime = LocalDateTime.now();
    }
}
