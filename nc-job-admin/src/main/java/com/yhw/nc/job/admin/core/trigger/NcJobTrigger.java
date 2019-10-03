package com.yhw.nc.job.admin.core.trigger;


import cn.hutool.core.lang.Assert;
import com.yhw.nc.common.discovery.client.route.RouterEum;
import com.yhw.nc.common.discovery.client.util.RestTemplateUtil;
import com.yhw.nc.common.discovery.client.util.ServiceInstanceUtil;
import com.yhw.nc.job.admin.core.config.NcJobAdminConfig;
import com.yhw.nc.job.admin.core.model.NcJobGroup;
import com.yhw.nc.job.admin.core.model.NcJobInfo;
import com.yhw.nc.job.admin.core.model.NcJobLog;
import com.yhw.nc.job.api.constants.JobCommonConstant;
import com.yhw.nc.job.api.dto.AdminConsumerDTO;
import com.yhw.nc.job.api.model.ApiResult;
import org.springframework.cloud.client.ServiceInstance;

/**
 * @author yhw
 */
public class NcJobTrigger {


    public static void trigger(int jobId, TriggerTypeEnum triggerType,int routeType) {
        processTrigger(getJobInfo(jobId),triggerType, RouterEum.getType(routeType));
    }

    public static void trigger(int jobId, TriggerTypeEnum triggerType) {
        NcJobInfo cloudJobInfo = getJobInfo(jobId);
        processTrigger(cloudJobInfo,triggerType,RouterEum.getType(cloudJobInfo.getRouteStrategy()));
    }

    private static NcJobInfo getJobInfo(int jobId){
        NcJobInfo cloudJobInfo = NcJobAdminConfig.getAdminConfig().getJobInfoMapper().selectById(jobId);
        Assert.isFalse(cloudJobInfo==null,">>>>>>>>>>>>>>>>>>>>> current job {} . not exists",jobId);
        return cloudJobInfo;
    }


    private static void processTrigger(NcJobInfo jobInfo, TriggerTypeEnum triggerType, RouterEum routerEum){
        ApiResult<String> apiResult;
        NcJobLog jobLog = new NcJobLog(jobInfo.getId(),triggerType.getType());
        try{
            NcJobGroup jobGroup = NcJobAdminConfig.getAdminConfig().getJobGroupService().getById(jobInfo.getJobGroupId());
            Assert.isFalse(jobGroup == null , "group not exists ");
            AdminConsumerDTO dto = new AdminConsumerDTO(jobGroup.getName(),jobInfo.getId(),jobInfo.getExecutorParam());
            apiResult = runExecutor(jobGroup.getServerId(), dto, routerEum);
            //如果调用成功，则记录调用成功
            if(apiResult == null){
                jobLog.setExecutorStatus(1);
                jobInfo.setTriggerStatus(1);
            }else if(!apiResult.isSuccess()){
                jobInfo.setTriggerStatus(0);
                jobLog.setExecutorStatus(1);
                jobLog.setLogDesc(apiResult.getMessage());
            }
        }catch (Exception e){
            jobLog.setExecutorStatus(1);
            jobInfo.setTriggerStatus(0);
            jobLog.setLogDesc("服务调度异常：".concat(e.getMessage()).concat(" <br/> "));
        }finally {
            //保存日志信息
            NcJobAdminConfig.getAdminConfig().getJobLogService().save(jobLog);
            NcJobAdminConfig.getAdminConfig().getJobInfoMapper().updateById(jobInfo);
        }
    }

    /**
     * run executor
     */
    private static ApiResult<String> runExecutor(String jobInstanceId, AdminConsumerDTO dto, RouterEum routerEum){
        NcJobAdminConfig adminConfig = NcJobAdminConfig.getAdminConfig();
        ServiceInstance serviceInstance = ServiceInstanceUtil.getServiceInstance(adminConfig.getDiscoveryClient(),jobInstanceId,routerEum);
        Assert.isFalse(serviceInstance==null,"server instance not exists");
        String path = serviceInstance.getUri().toString().concat(JobCommonConstant.PROVIDER_CONTROLLER_API_PATH);
        return RestTemplateUtil.postForObjBody(adminConfig.getRestTemplate(),path,dto,ApiResult.class);
    }

}

