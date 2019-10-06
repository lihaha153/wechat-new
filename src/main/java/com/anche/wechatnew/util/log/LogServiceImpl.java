package com.anche.wechatnew.util.log;

import com.anche.wechatnew.DAO.LogMapper;
import com.anche.wechatnew.model.admin.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class LogServiceImpl implements LogService{

    @Autowired
    private LogMapper logMapper;

    @Override
    public void insertLog(String code, String msg, String module) {
        SysLog sysLog = new SysLog();
        sysLog.setId(UUID.randomUUID().toString().replaceAll("-",""));
        sysLog.setCode(code);
        sysLog.setMsg(msg);
        sysLog.setModule(module);
        sysLog.setCreatedate(new Date());
        logMapper.insertLog(sysLog);
    }
}
