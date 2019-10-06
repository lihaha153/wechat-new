package com.anche.wechatnew.service.Impl;

import com.anche.wechatnew.DAO.SysOptionMapper;
import com.anche.wechatnew.model.wechat.WechatConfig;
import com.anche.wechatnew.service.WechatService;
import com.anche.wechatnew.util.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private SysOptionMapper sysOptionMapper;

    @Override
    public WechatConfig getConfigPara(String url) {
        String ticket = sysOptionMapper.getCodeByKindAndName("微信配置", "jsapi_ticket");
        if (ticket != null) {
            String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
            //将参数排序并拼接字符串
            String str = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            //将字符串进行sha1加密
            String signature = SHA1(str);
            WechatConfig wechatConfig = new WechatConfig();
            wechatConfig.setSignature(signature);
            wechatConfig.setTimestamp(timestamp);
            wechatConfig.setNonceStr(noncestr);
            String appId = sysOptionMapper.getCodeByKindAndName("微信配置", "appId");
            wechatConfig.setAppId(appId);
            return wechatConfig;
        }
        return null;
    }

    private String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
