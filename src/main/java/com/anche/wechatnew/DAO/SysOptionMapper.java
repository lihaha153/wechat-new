package com.anche.wechatnew.DAO;

import com.anche.wechatnew.model.admin.SysOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysOptionMapper {
    String getCodeByKindAndName(@Param("option_kind") String option_kind, @Param("option_name") String option_name);

    void updateCodeByKindAndName(SysOption sysOption);
}
