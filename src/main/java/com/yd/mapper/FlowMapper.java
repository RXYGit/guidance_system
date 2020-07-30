package com.yd.mapper;

import java.util.List;
import java.util.Map;

public interface FlowMapper {
    //轨迹信息的查询
    List<Map<String, Object>> flowTraceQry(Map<String, Object> param);
}
