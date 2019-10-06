package com.anche.wechatnew.service;

import com.anche.wechatnew.model.admin.SysOrganization;

import java.util.List;
import java.util.Map;

public interface BusinessService {
    List<Map<String,String>> getYwyyListByYwlx(String ywlx);

    List<SysOrganization> getStationListByLocation(String lat, String lng);
}
