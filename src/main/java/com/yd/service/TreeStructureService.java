package com.yd.service;


import com.yd.pojo.LabelListPOJO;
import com.yd.pojo.NodePOJO;
import com.yd.pojo.labelAttrListPOJO;

import java.util.List;
import java.util.Map;

public interface TreeStructureService {
    //查询组
    List<Map<String, Object>> grpQryList(String grpId);
    //查询节点
    List<Map<String, Object>> nodeQryReqMap(Map<String, Object> map);
    //查询标签
    List<Map<String, Object>> tagQryList(Object id);
    //查询一个节点的整个树形结构
    Map<String, Object> allTreeListQry(Map<String, Object> nodeMap);

    /***********************************批量执行，入参Map,出参Map，where动态条件 2020-07-22***************************************************/
    //Junit测试
    //节点查询入参Map包含List
    List<Map<String, String>> nodeSelectList(List<Map<String, String>> nodeReqList);
    //节点查询入参只是Map
    List<Map<String, String>> nodeSelectMap(Map<String, Object> param);
    //节点的更新（删除），b包含入参Map和批量更新的的操作
    int nodeUpateMap(Map<String, Object> param);
    //节点新增的操作
    int nodeInserteMap(Map<String, Object> param);



    //流转到下一节点同时返回下一节点信息(返回当前节点上的所有信息，并且同时返回下一个节点的上的所有信息)
    Map<String, Object> nodeAndNextNodeMsg(Map<String, Object> param);



    //获取当前节点的规则的信息，内部包含规则的要素   //判断节点选项是否符合规则
    Map<String, Object> nodeRuleMsg(Map<String, Object> param);




}
