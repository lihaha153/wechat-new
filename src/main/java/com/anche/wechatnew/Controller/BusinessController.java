package com.anche.wechatnew.Controller;

import com.anche.wechatnew.model.admin.SysOrganization;
import com.anche.wechatnew.service.BusinessService;
import com.anche.wechatnew.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/YwyyList")
    public Object getYwyyList(@RequestParam("ywlx") String ywlx) {
        try {
            List<Map<String, String>> mapList = businessService.getYwyyListByYwlx(ywlx);
            return ResultUtil.success(mapList);
        } catch (Exception e) {
            return ResultUtil.error(0, e.getMessage());
        }
    }

    @GetMapping("/StationList")
    public Object getStationList(@RequestParam("lat") String lat, @RequestParam("lng") String lng) {
        try {
            List<SysOrganization> sysOrganizationList = businessService.getStationListByLocation(lat,lng);
            return ResultUtil.success(sysOrganizationList);
        }catch (Exception e){
            return ResultUtil.error(0, e.getMessage());
        }
    }
}
