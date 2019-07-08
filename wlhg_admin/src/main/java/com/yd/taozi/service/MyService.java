package com.yd.taozi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xiaotaozi on 2019/7/6.
 */
@FeignClient(value = "provider")
public interface MyService {
    @RequestMapping(value = "/dengLu",method = RequestMethod.POST)
    public String denglu();
}
