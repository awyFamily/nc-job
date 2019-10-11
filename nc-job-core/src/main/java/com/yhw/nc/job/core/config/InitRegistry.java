package com.yhw.nc.job.core.config;

import com.yhw.nc.job.api.constants.CommonConstant;
import com.yhw.nc.job.api.dto.RegistryDTO;
import com.yhw.nc.job.api.feign.IJobAdminCallback;
import com.yhw.nc.job.core.provied.AbstractNcTask;
import com.yhw.nc.job.core.provied.NcTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * init registry
 * @author yhw
 * @date 2019-09-30
 */
@Slf4j
@Component
public class InitRegistry implements ApplicationListener<ApplicationReadyEvent>, DisposableBean {

    @Resource
    private  JobProviderPreperties jobProviderPreperties;

    @Resource
    private  IJobAdminCallback jobAdminCallback;

    @Autowired
    private ApplicationContext applicationContext;

    private Thread heartbeatThread;

    private volatile Boolean heartbeatThreadStop = false;

    private void initCommand(){
        try{
            List<AbstractNcTask> list = new ArrayList<>();
            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
            Stream.of(beanDefinitionNames).forEach(beanName -> {
                Object bean = applicationContext.getBean(beanName);
                if(bean instanceof AbstractNcTask){
                    list.add((AbstractNcTask)bean);
                }
            });

            NcTaskManager.getInstance().addNcTasks(list);
            log.info("init nc task repository success ! count [" + list.size() + "]");
        }catch (Exception e){
            log.error("nit nc task repository error",e);
        }
    }

    private void registry(String jobServerId){
        RegistryDTO registryDTO = new RegistryDTO(jobServerId, NcTaskManager.getInstance().getNames());
        jobAdminCallback.registry(registryDTO);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task begin");
        try{
            initCommand();
            String jobServerId = jobProviderPreperties.getJobServerId();
            if(jobServerId == null || jobServerId.isEmpty()){
                throw new RuntimeException("nc.job.provider.jobServerId  can not be empty");
            }

            registry(jobServerId);

            startHeartbeat();

            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task  success ");


        }catch (Exception e){
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task  error ",e);
            System.exit(0);
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task end");
    }

    private void startHeartbeat(){

        heartbeatThread = new Thread(() -> {
            while (!heartbeatThreadStop){
                sleepTime(TimeUnit.MINUTES, CommonConstant.DEFAULT_EXECUTOR_HEARTBEAT_TIME);
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> heartbeat task end");

                String jobServerId = jobProviderPreperties.getJobServerId();

                try{
                    registry(jobServerId);
                }catch (Exception e){
                    log.info("heartbeat task error ",e);
                }

                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> heartbeat task end");
            }
        },"heart-pack");

        heartbeatThread.setDaemon(true);
        heartbeatThread.start();

    }


    @Override
    public void destroy() throws Exception {
        this.heartbeatThreadStop = true;

        sleepTime(TimeUnit.SECONDS,1);

        if (heartbeatThread.getState() != Thread.State.TERMINATED){
            // interrupt and wait
            heartbeatThread.interrupt();
            try {
                heartbeatThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void sleepTime(TimeUnit timeUnit,long time){
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
