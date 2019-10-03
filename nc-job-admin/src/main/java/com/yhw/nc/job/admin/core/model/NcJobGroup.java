package com.yhw.nc.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class NcJobGroup {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String serverId;

    /**
     * handler名称
     */
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public NcJobGroup(String serverId,String name){
        this.serverId = serverId;
        this.name = name;
    }
}
