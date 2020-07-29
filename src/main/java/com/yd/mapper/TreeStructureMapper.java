package com.yd.mapper;

import java.util.List;
import java.util.Map;

public interface TreeStructureMapper {
    //查询组
    List<Map<String, Object>> grpQryList(String grpId);
    //查询节点集合
    List<Map<String, Object>> nodeQryReqMap(Map<String, Object> map);
    //查询标签集合
    List<Map<String, Object>> tagQryList(Object id);
    //查询标签属性集合
    List<Map<String, Object>> attrQryList(Object id);
    //查询一个节点的整个树形结构
    List<Map<String, Object>> allTreeListQry(Map<String, Object> nodeMap);


    /***********************************批量执行，入参Map,出参Map，where动态条件 2020-07-22***************************************************/
    //Junit测试
    //查询节点的集合,入参是Map,包含List
    List<Map<String, String>> nodeSelectList(List<Map<String, String>> nodeMap);
    //查询节点的集合,入参只是Map
    List<Map<String, String>> nodeSelectMap(Map<String, Object> param);
    //节点的更新（删除），b包含入参Map和批量更新的的操作
    int nodeUpdateMap(Map<String, Object> param);
    //节点新增的操作
    int nodeInserteMap(Map<String, Object> param);
    /**************************************************************************************************************************************/
    //流转到下一节点同时返回下一节点信息(返回当前节点上的所有信息，并且同时返回下一个节点的上的所有信息------------ begin)
    Map<String, Object> nodeAndNextNodeMsg(Map<String, Object> param);
    List<Map<String, Object>> nextNodeList(Map<String, Object> nodeCurrentMap);//获取当前节点所在的下一级的节点集合（不包含标签和属性）
    //-------------------------end
    /**************************************************************************************************************************************/
    //获取当前节点的规则的信息，内部包含规则的要素    //判断节点选项是否符合规则
    Map<String,Object> nodeRuleMsg(Map<String, Object> param);
    //获取规则的要素
    List<Map<String, Object>> ruleStartEndEleMsg(Map<String, Object> map);
    /**************************************************************************************************************************************/



}
