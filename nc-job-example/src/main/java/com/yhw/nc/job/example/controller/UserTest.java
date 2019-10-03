package com.yhw.nc.job.example.controller;

import com.yhw.nc.job.api.dto.RegistryDTO;
import com.yhw.nc.job.api.feign.IJobAdminCallback;
import com.yhw.nc.job.api.model.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/user")
@RestController
public class UserTest {

    @Resource
    private IJobAdminCallback jobAdminCallback;


    @GetMapping("/getUser")
    public ApiResult getUser(){
        jobAdminCallback.registry(new RegistryDTO());
        return ApiResult.ok("haha");
    }
}
