package com.yhw.nc.job.admin.core.trigger;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * trigger type enum
 *
 * @author xuxueli 2018-09-16 04:56:41
 */
@Getter
@AllArgsConstructor
public enum TriggerTypeEnum {

    MANUAL(0,"手动触发"),
    CRON(1,"Cron触发"),
    RETRY(2,"失败重试触发"),
    API(3,"API触发");

    private Integer type;
    private String title;

    private static Map<Integer,TriggerTypeEnum> repository;

    static {
        repository = new HashMap<>();
        for (TriggerTypeEnum typeEnum : TriggerTypeEnum.values()) {
            repository.put(typeEnum.getType(),typeEnum);
        }
    }

    public static TriggerTypeEnum getType(Integer code){
        return repository.get(code);
    }
}
