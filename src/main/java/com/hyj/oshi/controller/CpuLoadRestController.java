package com.hyj.oshi.controller;
import com.hyj.oshi.common.R;
import com.hyj.oshi.service.CpuLoadMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping("/metrics")
public class CpuLoadRestController {
    @Autowired
    CpuLoadMetricsService cpuLoadMetricsService;
    /**
     * {
     *    code:200,
     *    msg:"ok",
     *    data:null;
     * }
     */
    @GetMapping("/cpuload")
    public R getCpuLoad() {
        double[] cpuLoad = cpuLoadMetricsService.getCpuLoad();
        return R.ok(cpuLoad);
    }
}
