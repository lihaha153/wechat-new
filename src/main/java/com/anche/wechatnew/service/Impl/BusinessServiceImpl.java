package com.anche.wechatnew.service.Impl;

import com.anche.wechatnew.DAO.BussinessMapper;
import com.anche.wechatnew.model.admin.SysOrganization;
import com.anche.wechatnew.service.BusinessService;
import com.anche.wechatnew.util.location.LocationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BussinessMapper bussinessMapper;


    @Override
    public List<Map<String, String>> getYwyyListByYwlx(String ywlx) {
        List<Map<String, String>> mapList = bussinessMapper.getYwyyListByYwlx(ywlx);
        return mapList;
    }

    @Override
    public List<SysOrganization> getStationListByLocation(String lat, String lng) {
        List<SysOrganization> sysOrganList = bussinessMapper.getAllStation();
        for (SysOrganization sysOrganization : sysOrganList) {
            //根据当前经纬度计算与站点之间的直线距离
            Double distance = LocationUtils.getDistance(Double.valueOf(lat), Double.valueOf(lng), Double.valueOf(sysOrganization.getLat()), Double.valueOf(sysOrganization.getLng()));
            sysOrganization.setDistance(distance);
        }
        sysOrganList = sortListData(sysOrganList);
        return sysOrganList;
    }

    /**
     * 根据实体类中distance（距离）字段进行升序排序
     * @param list
     * @return
     */
    public static List<SysOrganization> sortListData(List<SysOrganization> list) {
        Collections.sort(list, (o1, o2) -> {
            if (o2.getDistance() < o1.getDistance()) {
                return 1;
            }
            return -1;
        });
        return list;
    }
}
