package com.yd.service;

import java.util.List;
import java.util.Map;

/**
 * 流程相关的的操作
 */
public interface FlowService {
    //轨迹信息的查询
    List<Map<String, Object>> flowTraceQry(Map<String, Object> param);
}
