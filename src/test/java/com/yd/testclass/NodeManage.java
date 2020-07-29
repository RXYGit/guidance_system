package com.yd.testclass;

import com.yd.commom.ObjectMapperJson;
import com.yd.mapper.TreeStructureMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导引系统节点管理
 */
//@SpringBootTest
public class NodeManage {

    /**
     * 查询单个节点的信息
     *  nodeid
     */
    @Test
    public void nodeSelect(){
        String nodeid ="1";
        Map<String, Object> nodemap = new HashMap<>();
        nodemap.put("nodeGrpId",nodeid);
        Map<String, Object> root = GetTree2.getRoot(nodemap);
        System.out.println(ObjectMapperJson.toJson(root));
    }

    /**
     * 更新,删除单个节点的信息,都用采用更新语句，删除只是改变是否生效的状态（0或1）
     * nodeid nodeParentId 依据条件节点的id和节点的父级节点id
     */
    @Test
    public void nodeUpdate(){
        String nodeid ="1";
        String nodeParentId = "11";
        Map<String, String> nodemap = new HashMap<>();
        nodemap.put("nodeId",nodeid);
        nodemap.put("nodeParentId",nodeParentId);
        System.out.println(ObjectMapperJson.toJson(GetTree2.updateNode(nodemap)));
    }

    /**
     * 节点的插入
     */
    @Test
    public void nodeInsert(){
        String nodeid ="7";
        String nodeParentId = "1";
        String nodeGrpId = "11";
        Map<String, String> nodemap = new HashMap<>();
        nodemap.put("nodeid",nodeid);
        nodemap.put("nodeParentId",nodeParentId);
        nodemap.put("nodeGrpId",nodeGrpId);
        System.out.println(ObjectMapperJson.toJson(GetTree2.nodeInsert(nodemap)));
    }

    /**
     * 查询节点选项的集合，这个选项有点疑惑，不是很明白，所以这个有问题
     */
    @Test
    public void nodeListSelect(){
        String nodeid ="7";
        String nodeGrpId = "1";
        Map<String, String> nodemap = new HashMap<>();
        nodemap.put("nodeid",nodeid);
        nodemap.put("nodeGrpId",nodeGrpId);
        System.out.println(ObjectMapperJson.toJson(GetTree2.nodeListSelect(nodemap)));
    }

    /**
     * 查询节点的标签的集合，
     */
    @Test
    public void nodeTagList(){
        String tagNodeid ="2";

        System.out.println(ObjectMapperJson.toJson(GetTree2.tagNodeList(tagNodeid)));
    }

    /**
     * 查询下一级节点的集合
     */
    @Test
    public void nodeNextList(){
        String nodeid = "2";//当前节点的的id，下一级的parentId=id时
        System.out.println(ObjectMapperJson.toJson(GetTree2.nodeNestList(nodeid)));
    }

    /**
     * 流转到下一节点同时返回下一节点信息(不是很理解，是否包含当前和下一节点的节点（包括标签属性）)
     */
    @Test
    public void nextNodeMsg(){
        String nodeid="1";
        System.out.println(ObjectMapperJson.toJson(GetTree2.nextNodeMsg(nodeid)));
    }

    /****************************批量执行，入参Map,出参Map，where动态条件2020-07-22***************************************************************/


    private TreeStructureMapper nodeMapMapper;


    @Test
    public void nodeSelectMap(){
        Map<String, String> nodeMap1 = new HashMap<>();
        nodeMap1.put("id","1");
        Map<String, String> nodeMap2 = new HashMap<>();
        nodeMap2.put("id","2");
        Map<String, String> nodeMap3 = new HashMap<>();
        nodeMap3.put("id","3");
        List<Map<String, String>>  nodeReqList = new ArrayList<>();
//        List<Map<String, String>>  nodeReqList = (List<Map<String, String>>) param.get("idList");
        nodeReqList.add(nodeMap1);
        nodeReqList.add(nodeMap2);
        nodeReqList.add(nodeMap3);
        List<Map<String, String>> rspList = nodeMapMapper.nodeSelectList(nodeReqList);
        System.out.println(rspList);
    }

}
