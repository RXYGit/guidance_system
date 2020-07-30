package com.yd.serviceImpl;

import com.yd.mapper.FlowMapper;
import com.yd.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流程、轨迹相关的功能
 */
@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    private FlowMapper flowMapper;

    ////轨迹信息的查询
    @Override
    public List<Map<String, Object>> flowTraceQry(Map<String, Object> param) {
        return flowMapper.flowTraceQry(param);
    }
}
