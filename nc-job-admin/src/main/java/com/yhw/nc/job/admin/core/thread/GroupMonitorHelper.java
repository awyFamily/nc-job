package com.yhw.nc.job.admin.core.thread;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yhw.nc.job.admin.core.model.NcJobGroup;
import com.yhw.nc.job.admin.core.model.dto.UpdateGroupHasAvailableDTO;
import com.yhw.nc.job.admin.mapper.NcJobGroupMapper;
import com.yhw.nc.job.api.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 监听 handler 是否可用
 * @author yhw
 * @date 2019-10-11
 */
@Slf4j
@Component
public class GroupMonitorHelper implements InitializingBean, DisposableBean {

    private Thread monitorThread;

    @Resource
    private NcJobGroupMapper jobGroupMapper;

    private volatile Boolean monitorThreadStop = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        int defaultMonitorTime = CommonConstant.DEFAULT_ADMIN_MONITOR_TIME;
        monitorThread = new Thread(() -> {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> group monitor task begin  ");
            while (!monitorThreadStop){

                try{
                    LocalDateTime now = LocalDateTime.now().withMinute(defaultMonitorTime);
                    List<NcJobGroup> result = jobGroupMapper.selectList(Wrappers.<NcJobGroup>lambdaQuery().eq(NcJobGroup::getHasAvailable,true).lt(NcJobGroup::getUpdateTime,now));

                    if(CollectionUtil.isNotEmpty(result)){
                        List<Integer> ids = result.stream().map(NcJobGroup::getId).collect(Collectors.toList());
                        jobGroupMapper.updateBatchHasAvailable(new UpdateGroupHasAvailableDTO(ids,false));
                    }
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> group monitor executor success  ");
                }catch (Exception e){
                    log.error(e.getMessage(), e);
                }

                sleepTime(TimeUnit.MINUTES,defaultMonitorTime);
            }
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> group monitor task end  ");
        },"GroupMonitor");

        monitorThread.setDaemon(true);
        monitorThread.start();

    }

    private void sleepTime(TimeUnit timeUnit,long time){
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public void destroy() throws Exception {
        this.monitorThreadStop = true;

        sleepTime(TimeUnit.SECONDS,1);

        if (monitorThread.getState() != Thread.State.TERMINATED){
            // interrupt and wait
            monitorThread.interrupt();
            try {
                monitorThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
