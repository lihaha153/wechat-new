package com.anche.wechatnew.DAO;

import com.anche.wechatnew.model.admin.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BussinessMapper {
    List<Map<String,String>> getYwyyListByYwlx(@Param("ywlx") String ywlx);

    List<SysOrganization> getAllStation();
}
