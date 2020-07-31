package com.yd.serviceImpl;

import com.yd.mapper.TreeStructureMapper;
import com.yd.service.TreeStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TreeStructureServiceImpl implements TreeStructureService {

    @Autowired
    private TreeStructureMapper treeTreeStructureMapper;
    //查询组
    @Override
    public List<Map<String, Object>> grpQryList(String grpId) {
        System.out.println("组的id:"+grpId);
        List<Map<String, Object>> list = treeTreeStructureMapper.grpQryList(grpId);
        return list;
    }
    //查询节点
    @Override
    public List<Map<String, Object>> nodeQryReqMap(Map<String, Object> map) {
        return treeTreeStructureMapper.nodeQryReqMap(map);
    }
    //查询标签集合
    @Override
    public List<Map<String, Object>> tagQryList(Object id) {
        List<Map<String, Object>> tagList = treeTreeStructureMapper.tagQryList(id);
        List<Map<String, Object>> attrTemplist = new ArrayList<>();
        for (int i = 0 ;i<tagList.size();i++){
            Map<String,Object> map = tagList.get(i);
            //获取标签的id，作为属性的条件
            List<Map<String, Object>> attrRspList = treeTreeStructureMapper.attrQryList(map.get("id"));
            map.put("tagAttrList",attrRspList);
            attrTemplist.add(map);
        }
        return attrTemplist;
    }
    //查询一个节点的整个树形结构
    @Override
    public Map<String, Object> allTreeListQry(Map<String, Object> nodeMap) {
        List<Map<String, Object>> list = treeTreeStructureMapper.allTreeListQry(nodeMap);
        nodeMap.put("nodeList", list);
        for(int i = 0; i < list.size(); i++){
            Map<String,Object> map1 = list.get(i);
            map1.put("tagList", tagQryList(map1.get("id")+""));
            allTreeListQry(map1);
        }
        return null;
    }




    /***********************************批量执行，入参Map,出参Map，where动态条件 2020-07-22***************************************************/
    //Junit测试
    ////节点查询入参是Map包含List
    @Override
    public List<Map<String, String>> nodeSelectList(List<Map<String, String>> nodeReqList) {
        return treeTreeStructureMapper.nodeSelectList(nodeReqList);
    }
    //节点查询入参只是Map
    @Override
    public List<Map<String, String>> nodeSelectMap(Map<String, Object> param) {
        return treeTreeStructureMapper.nodeSelectMap(param);
    }
    //节点的更新（删除），b包含入参Map和批量更新的的操作
    @Override
    public int nodeUpateMap(Map<String, Object> param) {
        return treeTreeStructureMapper.nodeUpdateMap(param);
    }
    //节点新增的操作
    @Override
    public int nodeInserteMap(Map<String, Object> param) {
        return treeTreeStructureMapper.nodeInserteMap(param);
    }
    /*************************************************************************************************************************/
    //流转到下一节点同时返回下一节点信息(返回当前节点上的所有信息，并且同时返回下一个节点的上的所有信息)
    @Override
    public Map<String, Object> nodeAndNextNodeMsg(Map<String, Object> param) {
        Map<String, Object> nodeCurrentMap = treeTreeStructureMapper.nodeAndNextNodeMsg(param);//返回的是当前的节点（不包含标签和属性）
        //查询属于此节点的标签和属性
        nodeCurrentMap.put("tagList",tagQryList(nodeCurrentMap.get("id")));
        //获取当前节点所在的下一级的节点集合（不包含标签和属性）
        List<Map<String, Object>> nextNodeMapList = treeTreeStructureMapper.nextNodeList(nodeCurrentMap);
        //然后获取当前节点所在的下一级节点每一个节点上的所有标签和属性
        for (int i = 0 ;i<nextNodeMapList.size();i++){
            Map<String, Object> map = nextNodeMapList.get(i);
            List<Map<String, Object>> list = tagQryList(map.get("id"));//复用标签的方法
            map.put("tagList",list);
        }
        nodeCurrentMap.put("nextNodeList",nextNodeMapList);
        return nodeCurrentMap;
    }
    /*************************************************************************************************************************/

    //获取当前节点的规则的信息，内部包含规则的要素   //判断节点选项是否符合规则
    @Override
    public Map<String, Object> nodeRuleMsg(Map<String, Object> param) {
        //查询出当前节点的Map数据
        Map<String, Object> map = treeTreeStructureMapper.nodeRuleMsg(param);
        //获取当前节点中的规则要素
        map.put("nodeRulEle",treeTreeStructureMapper.ruleStartEndEleMsg(map));
        return map;
    }
    /*************************************************************************************************************************/

}
