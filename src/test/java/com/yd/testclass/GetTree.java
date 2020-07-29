package com.yd.testclass;

import com.yd.commom.ObjectMapperJson;
import com.yd.commom.DBUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTree {


    @Test
    public void getTree() {
        //查询组表，
        List<Map<String, Object>> grouplist = DBUtils.qry("select a.id,a.grp_type,a.grp_name,a.grp_creator from zyzx_sd_dy_group a where id=1 and a.grp_is_effective='0';");
        //
        //for (int i = 0; i < grouplist.size(); i++) {
         //   Map groupmap = grouplist.get(i);

            Map<String, Object> map = new HashMap<>();
            map.put("GROUPID",  grouplist.get(0).get("id") + "");//查询节点表，条件字段为组表的id
            List<Map<String, Object>> root = getNode(map);
//            for (int j = 0; j < root.size(); j++) {
//                map.put("lablist", getTag(root.get(j).get("id") + ""));//查寻标签的集合，条件节点的id
//                root.add(map);
//            }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("rs",root);
        String s = ObjectMapperJson.toJson(root);
            System.out.println(s);
      //}
    }

    /**
     * 查询节点，属于组id的所有节点的集合
     * @param map
     * @return
     */
    private List<Map<String, Object>> getNode(Map<String, Object> map) {
        String sql = "select a.id,a.node_parent_id,a.node_grp_id,a.node_word_cal,a.node_title,a.node_quest,a.node_style,a.node_circulate,a.node_layout,a.ext1,a.ext2,a.ext3,a.ext4 from zyzx_sd_dy_node a where a.node_is_effective='0' and a.`node_grp_id`='" + map.get("GROUPID") + "' ";
        System.out.println(sql);
        List<Map<String, Object>> list2 = DBUtils.qry(sql);
        Map<String,Object> tagMap = new HashMap<>();
        //List<Map<String, Object>> list = new ArrayList<>();
        //继续查询标签上属于节点上id的所有标签的集合
        for (int i = 0 ;i<list2.size();i++){
            tagMap.put("tagList",getTag(String.valueOf(list2.get(i).get("id"))));//获取其中节点上的id
            //list.add(tagMap);
        }
        list2.add(tagMap);
        return list2;
    }

    private  List<Map<String, Object>> getTag(String id) {
        String sql = "select b.id, b.tag_node_id, b.tag_attr_id, b.tag_name, b.tag_value, b.tag_class, b.tag_access_way, b.tag_js_function, b.tag_sort, b.tag_type, b.tag_html, b.tag_text, b.ext1, b.ext2, b.ext3, b.ext3, b.ext4 from zyzx_sd_dy_tag b WHERE b.tag_is_effective='0' and b.tag_is_circulation ='0' AND b.`tag_node_id`='" + id + "'";
        List<Map<String, Object>> list = DBUtils.qry(sql);
        List<Map<String, Object>> list1 = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Map<String,Object> map = list.get(i);
            map.put("tagattrlist", getTagAttr(map.get("id")+""));
            list1.add(map);
        }
        return list1;
    }

    private List<Map<String, Object>> getTagAttr(String id) {
        String sql = "select c.id, c.attr_tag_id, c.attr_name, c.attr_value, c.attr_is_effective, c.ext1, c.ext2, c.ext3, c.ext4 from zyzx_sd_dy_tag_attribute c WHERE c.attr_is_effective='0' AND c.`attr_tag_id`='"+id+"'";
        return DBUtils.qry(sql);
    }



}