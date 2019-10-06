package com.anche.wechatnew.Controller;

import com.anche.wechatnew.model.wechat.WechatConfig;
import com.anche.wechatnew.service.WechatService;
import com.anche.wechatnew.util.exception.ExceptionUtil;
import com.anche.wechatnew.util.log.LogService;
import com.anche.wechatnew.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jsApi")
public class WechatController {

    @Autowired
    private LogService logService;

    @Autowired
    private WechatService wechatService;

    @GetMapping("/configPara")
    public Object getConfigPara(@RequestParam("url") String url) {
        try {
            WechatConfig wechatConfig = wechatService.getConfigPara(url);
            if (wechatConfig != null) {
                logService.insertLog("1", "/jsApi/configPara返回成功，：" + wechatConfig.toString(), "2");
                return ResultUtil.success(wechatConfig);
            } else {
                logService.insertLog("0", "/jsApi/configPara返回错误，wechatConfig为null", "2");
                return ResultUtil.error(0, "wechatConfig获取为空");
            }
        } catch (Exception e) {
            String msg = ExceptionUtil.getStackTrace(e).length() > 2000 ? ExceptionUtil.getStackTrace(e).substring(0, 2000) : ExceptionUtil.getStackTrace(e);
            logService.insertLog("0", "/jsApi/configPara接口异常，：" + msg, "2");
            return ResultUtil.error(0, "后台接口异常");
        }
    }

}
