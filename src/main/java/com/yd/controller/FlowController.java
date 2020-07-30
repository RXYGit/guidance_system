package com.yd.controller;

import com.yd.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对流程的的控制管理，以及流程轨迹的管理等与流程相关的点
 */
@RestController
public class FlowController {
    @Autowired
    private FlowService flowService;

    /**
     * 轨迹信息的查询
     * 疑问怎么记录整个的历史的轨迹路线，不计算回溯的操作，只记录树形结构从场景开始到结束操作的过程
     * @param param
     * @return
     */
    @RequestMapping(value = "/flowTraceQry",method = {RequestMethod.POST})
    public Map<String,Object> flowTraceQry(@RequestBody Map<String,Object> param){
        Map<String, Object> rspMap = new HashMap<>();
        param.get("prt_subs_num");//受理的主叫号码
        List<Map<String,Object>> flowTraceList =  flowService.flowTraceQry(param);
        for (Map<String, Object> map : flowTraceList) {
            map.get("fl_last_node_id");//上一级的轨迹id

        }
        rspMap.put("flowTraceMsg",flowTraceList);//轨迹信息
        return rspMap;
    }

}
