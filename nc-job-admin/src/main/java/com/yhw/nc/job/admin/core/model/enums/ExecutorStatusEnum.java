package com.yhw.nc.job.admin.core.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行状态枚举
 * @author yhw
 */
@Getter
@AllArgsConstructor
public enum ExecutorStatusEnum {

    executor_run(0,"执行中"),
    target_error(1,"调度失败"),
    executor_error(2,"任务执行失败"),
    executor_success(3,"任务执行成功"),
    ;

    private Integer type;

    private String description;

    private static Map<Integer,ExecutorStatusEnum> repository;

    static {
        repository = new HashMap<>();
        for (ExecutorStatusEnum typeEnum : ExecutorStatusEnum.values()) {
            repository.put(typeEnum.getType(),typeEnum);
        }
    }

    public static ExecutorStatusEnum getType(Integer code){
        return repository.get(code);
    }

}
