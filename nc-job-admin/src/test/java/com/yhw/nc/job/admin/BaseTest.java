package com.yhw.nc.job.admin;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.After;
import org.junit.Before;

/**
 * 测试基类
 * @author yhw
 */
public class BaseTest {

    /**
     * 服务地址
     */
    public static String url = "localhost:9999";


    /**
     * 接口入参
     */
    public static JSONObject parameters = new JSONObject();

    /**
     * 响应结果
     */
    public static String responseResult;

    @Before
    public void before(){
    }

    @After
    public void After(){
        System.out.println("request >>>>>>>> url:  \n\t" + url);
        System.out.println("\nrequest >>>>>>>> parameter:");
        System.out.println(JSONUtil.formatJsonStr(parameters.toString()));
        System.out.println("\nrequest >>>>>>> Result:");
        System.out.println(JSONUtil.formatJsonStr(responseResult));
    }

}
