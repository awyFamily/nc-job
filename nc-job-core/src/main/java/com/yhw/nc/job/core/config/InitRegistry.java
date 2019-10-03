package com.yhw.nc.job.core.config;

import com.yhw.nc.job.api.dto.RegistryDTO;
import com.yhw.nc.job.api.feign.IJobAdminCallback;
import com.yhw.nc.job.core.provied.AbstractNcTask;
import com.yhw.nc.job.core.provied.NcTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * init registry
 * @author yhw
 * @date 2019-09-30
 */
@Slf4j
@Component
public class InitRegistry implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private  JobProviderPreperties jobProviderPreperties;

    @Resource
    private  IJobAdminCallback jobAdminCallback;

    @Autowired
    private ApplicationContext applicationContext;

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

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task beign");
        try{
            initCommand();
            String jobServerId = jobProviderPreperties.getJobServerId();
            RegistryDTO registryDTO = new RegistryDTO(jobServerId, NcTaskManager.getInstance().getNames());
            jobAdminCallback.registry(registryDTO);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task  success ");
        }catch (Exception e){
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task  error ",e);
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry task end");
    }
}
