package com.yhw.nc.job.admin;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yhw.nc.common.discovery.client.util.RestTemplateUtil;
import com.yhw.nc.job.admin.core.model.dto.NcJobInfoDTO;
import org.junit.Test;

public class JobInfoTest extends BaseTest {

    static {
        url = url.concat("/jobInfo");
    }

    @Test
    public void  add(){
        url = url.concat("/add");

        NcJobInfoDTO dto = new NcJobInfoDTO();
        //添加测试
        /*dto.setAlarmEmail("aaa");
        dto.setExecutorFailRetryCount(3);
        dto.setJobDesc("ces");
        dto.setRouteStrategy(0);
        dto.setJobGroupId(2);*/

        //添加测试1
        dto.setAlarmEmail("aaa");
        dto.setExecutorFailRetryCount(3);
        dto.setJobCron("0 0/1 * * * ? ");
        dto.setJobDesc("没分钟执行一次");
        dto.setRouteStrategy(0);
        dto.setJobGroupId(1);

        parameters = JSONUtil.parseObj(dto);
        responseResult = HttpRequest.post(url).body(parameters)
                .execute().body();
    }

    @Test
    public void  running(){
        url = url.concat("/running").concat("/1");


        responseResult = HttpRequest.get(url)
                .execute().body();

    }


}
