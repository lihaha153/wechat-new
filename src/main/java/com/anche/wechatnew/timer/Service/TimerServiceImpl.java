package com.anche.wechatnew.timer.Service;

import com.anche.wechatnew.DAO.SysOptionMapper;
import com.anche.wechatnew.model.admin.SysOption;
import com.anche.wechatnew.model.common.Result;
import com.anche.wechatnew.model.wechat.ErrorCode;
import com.anche.wechatnew.model.wechat.Token;
import com.anche.wechatnew.util.exception.ExceptionUtil;
import com.anche.wechatnew.util.log.LogService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    private LogService logService;

    @Autowired
    private SysOptionMapper sysOptionMapper;

    @Override
    public void getAccessToken() {
        String appid = sysOptionMapper.getCodeByKindAndName("微信配置", "appId");
        String secret = sysOptionMapper.getCodeByKindAndName("微信配置", "secret");
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String json = new String(jsonBytes, "UTF-8");
            System.out.println("JSON字符串：" + json);
            is.close();
            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(json, Map.class);
            if (map.get("access_token") != null && !map.get("access_token").equals("")) {
                String access_token = map.get("access_token").toString();
                String expir = map.get("expires_in").toString();
                SysOption sysOption = new SysOption();
                sysOption.setOption_kind("微信配置");
                sysOption.setOption_name("access_token");
                sysOption.setOption_code(access_token);
                sysOptionMapper.updateCodeByKindAndName(sysOption);
                logService.insertLog("1", "定时器刷新access_token成功，值为" + access_token + "，有效期为：" + expir + "秒", "1");
            } else if (map.get("errcode") != null && !map.get("errcode").equals("")) {
                logService.insertLog("0", "定时器刷新access_token失败，微信公众平台返回错误码：" + map.get("errcode") + "，错误描述：" + map.get("errmsg"), "1");
            } else {
                logService.insertLog("0", "定时器刷新access_token失败，未知的微信公众平台返回结果：" + json, "1");
            }
        } catch (Exception e) {
            String msg = ExceptionUtil.getStackTrace(e).length() > 2000 ? ExceptionUtil.getStackTrace(e).substring(0, 2000) : ExceptionUtil.getStackTrace(e);
            logService.insertLog("0", "定时器刷新access_token失败，方法内异常：" + msg, "1");
        }
    }

    @Override
    public void getJsApi_ticket() {
        String access_token = sysOptionMapper.getCodeByKindAndName("微信配置", "access_token");
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String json = new String(jsonBytes, "UTF-8");
            is.close();
            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(json, Map.class);
            if ((double) map.get("errcode") == 0) {
                String jsapi_ticket = map.get("ticket").toString();
                String expir = map.get("expires_in").toString();
                SysOption sysOption = new SysOption();
                sysOption.setOption_kind("微信配置");
                sysOption.setOption_name("jsapi_ticket");
                sysOption.setOption_code(jsapi_ticket);
                sysOptionMapper.updateCodeByKindAndName(sysOption);
                logService.insertLog("1", "获取ticket成功，：ticket：" + jsapi_ticket + "，有效期：" + expir + "秒", "2");
            } else {
                logService.insertLog("0", "获取ticket失败，微信公众平台返回错误码：" + map.get("errcode") + "，错误描述：" + map.get("errmsg"), "2");
            }
        } catch (Exception e) {
            String msg = ExceptionUtil.getStackTrace(e).length() > 2000 ? ExceptionUtil.getStackTrace(e).substring(0, 2000) : ExceptionUtil.getStackTrace(e);
            logService.insertLog("0", "获取ticket失败，方法内异常：" + msg, "2");
        }
    }
}
