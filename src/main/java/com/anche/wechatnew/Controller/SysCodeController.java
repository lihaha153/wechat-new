package com.anche.wechatnew.Controller;

import com.anche.wechatnew.service.SysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SysCode")
public class SysCodeController {

    @Autowired
    private SysCodeService sysCodeService;


}
