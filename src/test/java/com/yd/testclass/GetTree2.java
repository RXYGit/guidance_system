package com.yd.testclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yd.commom.ObjectMapperJson;
import com.yd.commom.DBUtils;
import org.junit.jupiter.api.Test;

/**
 * 导引系统根据节点获取树的结构，返回json
 */
public class GetTree2 {

	@Test
	public  void treeData() {
		String groupid = "2";
		List<Map<String, Object>> groupList = DBUtils.qry("select a.`id`,a.`grp_type`,a.`grp_name`,a.`grp_creator`,a.`grp_is_effective` from `zyzx_sd_dy_group` a where a.`id`='"+groupid+"' and a.`grp_is_effective`='0';");
		Map<String, Object> grpMap = groupList.get(0);

		Map<String, Object> map = new HashMap<>();
		System.out.println(grpMap.get("id")+"");
		map.put("nodeGrpId", grpMap.get("id")+"");
		//获取节点
		Map<String, Object> root = getRoot(map);
		//获得节点上的id,在标签表中的字tag_node_id段中符合id
		root.put("tagList", getTag(root.get("id")+""));
		//查询整树
		getnodelist(root);
		//JSONObject json = new JSONObject(root);
		//System.out.println(JSONObject.toJSONString(root,{SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty}));

		String s = ObjectMapperJson.toJson(root);
		System.out.println(s);
//		SerializerFeature[] features = new SerializerFeature[] {
//				SerializerFeature.WriteNullStringAsEmpty
//		};
//		System.out.println(JSONObject.toJSONString(root,features));
	}
	/**
	 * 查全树
	 * @param map
	 * @return
	 */
	public static Map<String, Object> getnodelist(Map<String,Object> map){
		String sql = "SELECT t.`node_grp_id`,t.id,t.`node_parent_id`,t.`node_style`,t.`node_circulate`,t.`node_layout`,t.`node_quest`,t.`node_word_cal`,t.`node_title`,t.`node_is_effective` FROM zyzx_sd_dy_node t WHERE t.`node_grp_id`='" + map.get("node_grp_id") + "' AND t.`node_parent_id`='" + map.get("id") + "'";
		List<Map<String, Object>> list = DBUtils.qry(sql);
		map.put("nodeList", list);
		for(int i = 0; i < list.size(); i++){
			Map<String,Object> map1 = list.get(i);
			map1.put("tagList", getTag(map1.get("id")+""));
			getnodelist(map1);
		}
		return null;
	}

	/**
	 * 查根节点
	 * @param map
	 */
	public static Map<String, Object> getRoot(Map<String,Object> map){
		String sql = "select a.`id`,a.`node_parent_id`,a.`node_grp_id`,a.`node_word_cal`,a.`node_title`,a.`node_quest`,a.`node_style`,a.`node_circulate`,a.`node_layout`,a.`node_is_effective`,a.`ext1`,a.`ext2`,a.`ext3`,a.`ext4` from `zyzx_sd_dy_node` a where a.`node_is_effective`='0' and a.`node_grp_id`='" + map.get("nodeGrpId") + "' ";
		System.out.println(sql);
		List<Map<String, Object>> list = DBUtils.qry(sql);
		return list.get(0);
	}

	/**
	 * 查标签集合
	 * @param nodeid
	 */
	public static List<Map<String, Object>> getTag(String nodeid){
		String sql = "select b.`id`, b.`tag_node_id`, b.`tag_attr_id`, b.`tag_name`, b.`tag_value`, b.`tag_class`, b.`tag_access_way`, b.`tag_js_function`, b.`tag_sort`, b.`tag_type`, b.`tag_html`, b.`tag_is_effective`,b.`tag_is_circulation`,b.`tag_text`, b.`ext1`, b.`ext2`, b.`ext3`, b.`ext4` from `zyzx_sd_dy_tag` b WHERE b.`tag_is_effective`='0' and b.`tag_is_circulation` ='0' AND b.`tag_node_id`='" + nodeid + "' ORDER BY b.`tag_sort`";
		List<Map<String, Object>> list = DBUtils.qry(sql);
		List<Map<String, Object>> list1 = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			Map<String,Object> map = list.get(i);
			map.put("tagAttrList", getTagAttr(map.get("id")+""));
			list1.add(map);
		}
		return list1;
	}
	/**********************************************************************************************************************************************************/
	/**
	 * 查标签的属性集合
	 * @param tagid
	 */
	public static List<Map<String, Object>> getTagAttr(String tagid){
		String sql = "select c.`id`, c.`attr_tag_id`, c.`attr_name`, c.`attr_value`, c.`attr_is_effective`,c.`attr_is_effective`, c.`ext1`, c.`ext2`, c.`ext3`, c.`ext4` from `zyzx_sd_dy_tag_attribute` c WHERE c.`attr_is_effective`='0' AND c.`attr_tag_id`='"+tagid+"'";
		return DBUtils.qry(sql);
	}

	/**
	 * 单个节点的更新删除
	 * @param nodemap
	 * @return
	 */
	public static Map<String, Object> updateNode(Map<String, String> nodemap) {
		String sql = "UPDATE `zyzx_sd_dy_node` a SET a.`node_parent_id`='12' where a.`node_parent_id`='"+nodemap.get("nodeParentId")+"' and a.`id`='" + nodemap.get("nodeId") + "' ";
		return DBUtils.updateNode(sql);
	}


	/**
	 * 单个节点的新增操作，存在批量新增
	 * @param nodemap
	 * @return
	 */
	public static Map<String, Object>  nodeInsert(Map<String, String> nodemap) {
		String sql = "insert into zyzx_sd_dy_node(id,node_parent_id,node_grp_id) values ('"+nodemap.get("nodeid")+"','"+nodemap.get("nodeParentId")+"','"+nodemap.get("nodeGrpId")+"');";
		return DBUtils.updateNode(sql);
	}

	/**
	 * 查询节点选项的结合(每个节点是否包含标签和属性)
	 * @param nodemap
	 * @return
	 */
	public static List<Map<String,Object>> nodeListSelect(Map<String, String> nodemap) {
		String sql = "select a.`id`,a.`node_parent_id`,a.`node_grp_id`,a.`node_word_cal`,a.`node_title`,a.`node_quest`,a.`node_style`,a.`node_circulate`,a.`node_layout`,a.`node_is_effective`,a.`ext1`,a.`ext2`,a.`ext3`,a.`ext4` from `zyzx_sd_dy_node` a where a.`node_is_effective`='0' and a.`node_grp_id`='" + nodemap.get("nodeGrpId") + "'  ";
		return DBUtils.qry(sql);

	}

	/**
	 *查询节点的标签的集合
	 * @param tagNodeid
	 * @return
	 */
	public static List<Map<String,Object>> tagNodeList(String tagNodeid) {
		String sql = "select b.`id`, b.`tag_node_id`, b.`tag_attr_id`, b.`tag_name`, b.`tag_value`, b.`tag_class`, b.`tag_access_way`, b.`tag_js_function`, b.`tag_sort`, b.`tag_type`, b.`tag_html`, b.`tag_is_effective`,b.`tag_is_circulation`,b.`tag_text`, b.`ext1`, b.`ext2`, b.`ext3`, b.`ext4` from `zyzx_sd_dy_tag` b WHERE b.`tag_is_effective`='0' and b.`tag_is_circulation` ='0' AND b.`tag_node_id`='" + tagNodeid + "' ORDER BY b.`tag_sort`";
		return DBUtils.qry(sql);
	}

	/**
	 * 查询下一级节点的集合
	 * @param nodeid
	 * @return
	 */
	public static List<Map<String,Object>> nodeNestList(String nodeid) {
		String sql = "select a.`id`,a.`node_parent_id`,a.`node_grp_id`,a.`node_word_cal`,a.`node_title`,a.`node_quest`,a.`node_style`,a.`node_circulate`,a.`node_layout`,a.`ext1`,a.`ext2`,a.`ext3`,a.`ext4` from `zyzx_sd_dy_node` a where a.`node_grp_id`='1'  and a.`node_is_effective`='0' and a.`node_parent_id`='"+nodeid+"';";
		return DBUtils.qry(sql);
	}

	/**
	 * 流转到下一节点同时返回下一节点信息(不是很理解，是否包含当前和下一节点的节点（包括标签属性）)
	 * @param nodeid
	 * @return
	 */
	public static List<Map<String, Object>> nextNodeMsg(String nodeid) {
		String sql = "select a.`id`,a.`node_parent_id`,a.`node_grp_id`,a.`node_word_cal`,a.`node_title`,a.`node_quest`,a.`node_style`,a.`node_circulate`,a.`node_layout`,a.`ext1`,a.`ext2`,a.`ext3`,a.`ext4` from `zyzx_sd_dy_node` a where a.`node_grp_id`='1'  and a.`node_is_effective`='0' and a.`node_parent_id`='"+nodeid+"';";
		List<Map<String, Object>> qry = DBUtils.qry(sql);

		List<Map<String, Object>> nodeid1 = getTag(String.valueOf(qry.get(0).get("id")));

		return nodeid1;
	}

	/***********************************************************************************************************************/

	/**
	 * 标签的查询，包含标签属性的查询
	 * @param tagId
	 * @return
	 */
	public static List<Map<String,Object>> selectTag(String tagId) {
		String sql = "select b.`id`, b.`tag_node_id`, b.`tag_attr_id`, b.`tag_name`, b.`tag_value`, b.`tag_class`, b.`tag_access_way`, b.`tag_js_function`, b.`tag_sort`, b.`tag_type`, b.`tag_html`, b.`tag_is_effective`,b.`tag_is_circulation`,b.`tag_text`, b.`ext1`, b.`ext2`, b.`ext3`, b.`ext4` from `zyzx_sd_dy_tag` b WHERE b.`tag_is_effective`='0' and b.`tag_is_circulation` ='0' AND b.`id`='" + tagId + "' ORDER BY b.`tag_sort`";
		List<Map<String, Object>> list = DBUtils.qry(sql);
		List<Map<String, Object>> tagList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			Map<String,Object> map = list.get(i);
			map.put("tagAttrList", getTagAttr(map.get("id")+""));
			tagList.add(map);
		}
		return tagList;
	}


	/**
	 * 标签的更新操作
	 * @param tagId
	 * @return
	 */
	public static Map<String,Object> updateTag(String tagId) {
		String sql = "Update `zyzx_sd_dy_tag` a SET a.`tag_node_id`='1', a.`tag_attr_id`='idSimple2', a.`tag_name`='块级', a.`tag_value`='lab', a.`tag_class`='话术', a.`tag_access_way`='方式', a.`tag_js_function`='demo', a.`tag_sort`='222', a.`tag_type`='demo', a.`tag_is_effective`='0', a.`tag_is_circulation`='0', a.`tag_html`='html', a.`tag_text`='标签内容', a.`ext1`='扩展11', a.`ext2`='扩展21',a.`ext3`='扩展31', a.`ext4`='扩展21' where a.`id`='"+tagId+"'";
		return DBUtils.updateNode(sql);
	}

	/**
	 * 标签的新增操作，存在批量新增
	 * @param tagMap
	 * @return
	 */
	public static Map<String,Object> insertTag(Map<String,Object> tagMap) {
		String sql = "insert into `zyzx_sd_dy_tag`  (`id`, `tag_node_id`, `tag_attr_id`,`tag_is_effective`, `tag_text`) values ("+tagMap.get("tagId")+",'"+tagMap.get("tagNodeId")+"' ,'"+tagMap.get("tagAttrId")+"','"+tagMap.get("tagEffective")+"','"+tagMap.get("tagText")+"')";
		return DBUtils.updateNode(sql);

	}

	/******************************************************************************************************************************/

	/**
	 * 组表的查询
	 * @param grpId
	 * @return
	 */
	public static List<Map<String,Object>> grpSelect(String grpId) {
		String sql = "select a.`id`,a.`grp_name`,a.`grp_type`,a.`grp_is_effective`,a.`grp_creator`,a.`grp_create_time`,a.`ext1`,a.`ext2`,a.`ext3`,a.`ext4` from `zyzx_sd_dy_group` a where a.`id`='"+grpId+"' and a.`grp_is_effective`='0';";
		return DBUtils.qry(sql);
	}

	/**
	 *组的更新，删除
	 * @param grpId
	 * @return
	 */
	public static Map<String,Object> grpUpdate(String grpId) {
		String sql = "Update `zyzx_sd_dy_group` b SET b.`grp_is_effective`='1' where b.`id`='"+grpId+"';";
		return DBUtils.updateNode(sql);
	}

	/**
	 * 组的新增
	 * @param grpMap
	 * @return
	 */
	public static Map<String,Object> grpInsert(Map<String, Object> grpMap) {
		String sql = "INSERT INTO `zyzx_sd_dy_group` (`id`,`grp_name`) values ('"+grpMap.get("grpId")+"','"+grpMap.get("grpName")+"');";
		return DBUtils.updateNode(sql);
	}

	/******************************************************************************************************************************/

	/**
	 * 规则的查询
	 * @param ruleId
	 * @return
	 */
	public static List<Map<String,Object>> ruleSelect(String ruleId) {
		String sql = "select a.`id`,a.`rule_start_node`,a.`rule_target_node`,a.`rule_name`,a.`rule_type`,a.`rule_is_effective`,a.`rule_priority`,a.`ext1`,a.`ext2`,a.`ext3`,a.`ext4` from `zyzx_sd_dy_rule` a where a.`id`='"+ruleId+"' and a.`rule_is_effective`='0';";
		return DBUtils.qry(sql);
	}

	/**
	 * 规则的更新，删除
	 * @param ruleId
	 * @return
	 */
	public static Map<String,Object> ruleUpdate(String ruleId) {
		String sql = "update `zyzx_sd_dy_rule` b SET b.`rule_is_effective`='1' where b.`id`='"+ruleId+"';";
		return DBUtils.updateNode(sql);
	}

	/**
	 * 规则的新增
	 * @param ruleMap
	 * @return
	 */
	public static Map<String,Object> ruleInsert(Map<String, Object> ruleMap) {
		String sql = "insert into `zyzx_sd_dy_rule` (`id`,`rule_name`) values ('"+ruleMap.get("ruleId")+"','"+ruleMap.get("ruleName")+"');";
		return DBUtils.updateNode(sql);
	}

	/****************************************************************************************************************/


	/**
	 * 规则的要素的查询
	 * @param eleId
	 * @return
	 */
	public static List<Map<String,Object>> eleSelect(String eleId) {
		//暂时修改为re_rule_id作为条件
		String sql = "select a.`id`,a.`re_rule_id`,a.`re_param`,a.`re_accord`,a.`re_range`,a.`re_result`,a.`re_is_effective`,a.`ext1`,a.`ext2`,a.`ext3`,a.`ext4` from `zyzx_sd_dy_rule_element` a where a.`re_rule_id`='"+eleId+"' and a.`re_is_effective`='0';";
		return DBUtils.qry(sql);
	}

	/**
	 * 规则要素的修改，删除
	 * @param eleId
	 * @return
	 */
	public static Map<String,Object> eleUpdate(String eleId) {
		String sql = "update `zyzx_sd_dy_rule_element` a set a.`re_is_effective`='2' where a.`id`='"+eleId+"';";
		return DBUtils.updateNode(sql);
	}


	public static Map<String,Object> eleInsert(String eleID) {
		String sql ="insert into `zyzx_sd_dy_rule_element` (`id`,`re_param`,`re_is_effective`) values ('"+eleID+"','新增测试参数2','0');";
		return DBUtils.updateNode(sql);
	}

	/***************************************************************************************************************************************************************/

	public static List<Map<String,Object>> attrSelect(String attrId) {
		String sql = "";
		return DBUtils.qry(sql);
	}


}
