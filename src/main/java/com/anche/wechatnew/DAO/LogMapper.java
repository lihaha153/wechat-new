package com.anche.wechatnew.DAO;

import com.anche.wechatnew.model.admin.SysLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    void insertLog(SysLog sysLog);
}
