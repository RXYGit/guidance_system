package com.yd.testclass;


import com.yd.commom.ObjectMapperJson;
import com.yd.commom.DBUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class TreeGet {


	/**
	 * 根据组中节点id,查询树形结构
	 */
	@Test
	public void tree() {
		// TODO Auto-generated method stub

		List<Map<String, Object>> grouplist = DBUtils.qry("select a.id,a.grp_type,a.grp_name,a.grp_creator from zyzx_sd_dy_group a where id=1 and a.grp_is_effective='0';");
		List<Map<String,Object>> returnList = new ArrayList<>();
		for (int i=0;i<grouplist.size();i++) {
			Map groupmap = grouplist.get(i);

			Map<String, Object> map = new HashMap<>();
			map.put("GROUPID", groupmap.get("id") + "");//查询节点表，条件字段为组表的id
			Map<String, Object> root = getRoot(map);
				 map.put("taglist", getTag(root.get("id") + ""));//查寻标签的集合，条件节点的id

			//查询整树
			//getnodelist(root);
			String s = ObjectMapperJson.toJson(map);
			System.out.println(s);
		}
	}

	/**
	 * 查全树
	 */
	public static Map<String, Object> getnodelist(Map map){
		List<Map<String, Object>> list = DBUtils.qry("SELECT concat(t.id,'') as \"grouprelationid\",t.`groupid`,t.`childid`,t.`parentid`,t.`childtype`,t.`orderno` , t1.`bgstyle`,t1.`flowtype`,concat(t1.`id`,'') as nodeid,t1.`layout`,t1.`question`,t1.`tips`,t1.`title` FROM t_dy_grouprelation t INNER JOIN t_dy_node t1 ON (t.`childid`=t1.`id`) AND t.`groupid`='" + map.get("GROUPID") + "' AND t.`parentid`='" + map.get("CHILDID") + "'");
		map.put("nodelist", list);
		for(int i = 0; i < list.size(); i++){
			Map map1 = list.get(i);
			map1.put("lablist", getTag(map1.get("NODEID")+""));
			
			//System.out.println("sql:"+"SELECT t.`id` grouprelationid,t.`groupid`,t.`childid`,t.`parentid`,t.`childtype`,t.`orderno` , t1.`bgstyle`,t1.`flowtype`,t1.`id` nodeid,t1.`layout`,t1.`question`,t1.`tips`,t1.`title` FROM t_dy_grouprelation t INNER JOIN t_dy_node t1 ON (t.`childid`=t1.`id`) AND t.`groupid`='" + map.get("GROUPID") + "' AND t.`parentid`='" + map.get("CHILDID") + "'");
			getnodelist(map1);
		}
		
		
		return null;
	}
	
	/**
	 * 查根节点
	 */
	public static Map<String, Object> getRoot(Map map){
		String sql = "select a.id,a.node_parent_id,a.node_grp_id,a.node_word_cal,a.node_title,a.node_quest,a.node_style,a.node_circulate,a.node_layout,a.ext1,a.ext2,a.ext3,a.ext4 from zyzx_sd_dy_node a where a.node_is_effective='0' and a.`node_grp_id`='" + map.get("GROUPID") + "' ";
		System.out.println(sql);
		List<Map<String, Object>> list = DBUtils.qry(sql);
		return list.get(0);

	}
	
	/**
	 * 查标签集合
	 */
	public static List<Map<String, Object>> getTag(String nodeid){
		String sql = "select b.id, b.tag_node_id, b.tag_attr_id, b.tag_name, b.tag_value, b.tag_class, b.tag_access_way, b.tag_js_function, b.tag_sort, b.tag_type, b.tag_html, b.tag_text, b.ext1, b.ext2, b.ext3, b.ext3, b.ext4 from zyzx_sd_dy_tag b WHERE b.tag_is_effective='0' and b.tag_is_circulation ='0' AND b.`tag_node_id`='" + nodeid + "'";
		List<Map<String, Object>> list = DBUtils.qry(sql);
		List<Map<String, Object>> list1 = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			Map map = list.get(i);
			map.put("tagattrlist", getTagAttr(map.get("id")+""));
			list1.add(map);
		}
		return list1;	
	}
	
	/**
	 * 查标签的属性集合
	 */
	public static List<Map<String, Object>> getTagAttr(String tagid){
		String sql = "select c.id, c.attr_tag_id, c.attr_name, c.attr_value, c.attr_is_effective, c.ext1, c.ext2, c.ext3, c.ext4 from zyzx_sd_dy_tag_attribute c WHERE c.attr_is_effective='0' AND c.`attr_tag_id`='"+tagid+"'";
		List<Map<String, Object>> list = DBUtils.qry(sql);
		//System.out.println("sql:"+sql);
		return list;
	}
	

}
