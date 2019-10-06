package com.anche.wechatnew.timer.Service;


import com.anche.wechatnew.model.common.Result;

public interface TimerService {
    /**
     * 使用AppID和AppSecret调用本接口来获取access_token。AppID和AppSecret可在“微信公众平台-开发-基本配置”页中获得。
     * @return
     */
    void getAccessToken();

    void getJsApi_ticket();
}
