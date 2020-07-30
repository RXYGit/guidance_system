package com.yd.controller;

import com.yd.commom.ObjectMapperJson;
import com.yd.commom.URLCode;
import com.yd.service.TreeStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 导引助手的树形结构、节点、规则的测试参考类
 */
@RestController
public class TreeStructureController {
    @Autowired
    private TreeStructureService treeStructureService;

    //获取树形结构  也包含了另外的功能 查询单个节点的信息（包含标签和属性）
    @RequestMapping("/throwNodeGetTree")
    public Map<String, Object> selectTree(@RequestBody Map<String,String> param) throws NoSuchAlgorithmException {

        String grpId = param.get("id");
        //查询组,从而得到组的id
        List<Map<String, Object>> groupList = treeStructureService.grpQryList(grpId);
        //查询节点,通过获取组的id来得到属于同一个组的所有节点
        Map<String, Object> map = new HashMap<>();
        map.put("nodeGrpId", groupList.get(0).get("id"));
        List<Map<String, Object>> nodeList = treeStructureService.nodeQryReqMap(map);//此处会异常IndexOutOfBoundsException: Index: 0, Size: 0
        //查询标签集合   在标签内部的实现类里查询 标 签属性集合
        Map<String, Object> nodeMap = nodeList.get(0);//获取一个节点，为了查询这个节点上的标签和标签属性
        nodeMap.put("tagList", treeStructureService.tagQryList(nodeMap.get("id")));

        //然后查询一个节点的整个树形结构
        treeStructureService.allTreeListQry(nodeMap);
        //随机数生成方式的几种区别
        Random random = new Random(1000);
        System.out.println("Random随机Boolean值:"+random.nextBoolean());
        System.out.println("Random随机Double值:"+random.nextDouble());

        ThreadLocalRandom threadLocalRandom =  ThreadLocalRandom.current();
        System.out.println("ThreadLocalRandom随机Boolean："+threadLocalRandom.nextBoolean());
        System.out.println("ThreadLocalRandom随机数："+threadLocalRandom.nextInt(5,10));

        //SecureRandom既指定了算法名称又指定了包提供程序: 系统将确定环境中是否有所请求的算法实现，是否有多个，是否有首选实现。
        SecureRandom secureRandom= SecureRandom.getInstance("SHA1PRNG");//
        int randNum = secureRandom.nextInt(100);
        System.out.println("SecureRandom随机数字"+randNum);
        System.out.println("SecureRandom随机的Boolean值"+secureRandom.nextBoolean());

        //需返回的整个map
        return nodeMap;
    }

    /**
     * 节点查询入参是Map内部包含List，在List中取出入参数据
     * @param param
     * @return
     */
    @RequestMapping(value = "/nodeSelectList",method = {RequestMethod.POST})
    public Map<String, Object> nodeSelectList(@RequestBody Map<String,Object> param){
        Map<String, Object> nodeMap = new HashMap<>();
//        nodeMap1.put("id","1");
//        Map<String, String> nodeMap2 = new HashMap<>();
//        nodeMap2.put("id","2");
//        Map<String, String> nodeMap3 = new HashMap<>();
//        nodeMap3.put("id","3");
        //List<Map<String, String>>  nodeReqList = new ArrayList<>();

        List<Map<String, String>>  nodeReqList = (List<Map<String, String>>) param.get("idList");
//        nodeReqList.add(nodeMap1);
//        nodeReqList.add(nodeMap2);
//        nodeReqList.add(nodeMap3);
        List<Map<String, String>> nodeRspList = treeStructureService.nodeSelectList(nodeReqList);
        nodeReqList.get(0).get("node_style");
        nodeMap.put("result",nodeRspList);
        return nodeMap;
    }

    /**
     * 节点查询入参只是Map
     * 并且对数据的解码 URLDecode(指定的Key)
     * @param param
     * @return
     */
    @RequestMapping(value = "/nodeSelectMap",method = {RequestMethod.POST})
    public Map<String, Object> nodeQryMap(@RequestBody Map<String,Object> param) {
        Map<String, Object> nodeMap = new HashMap<>();
        List<Map<String, String>> nodeRspList = treeStructureService.nodeSelectMap(param);
        //查询
        System.out.println("未解码之前的数据： "+ nodeRspList);
        /*
         * 解码，把特殊字符解码，比如双引号
         *     入参数据：{"id":"17","node_is_effective":0,"node_parent_id":"\"测试URLCode特殊字符\""}
         *   下面的方式解码存在两个问题
         * 1.未解码之前的数据： [{nodeCirculate=null, nodeIsEffective=0, nodeWordCal=null, nodeLayout=null, nodeGrpId=null, nodeParentId=%22测试URLCode特殊字符%22, nodeTitle=null, nodeStyle=null, id=17, ext4=null, ext3=null, nodeQuest=null, ext2=null, ext1=null}]
         *    这是数据库得到的数据，有null值，有URLCode编码
         * 2.解码后数据：[{"nodeCirculate":"null","nodeIsEffective":"0","nodeWordCal":"null","nodeLayout":"null","nodeGrpId":"null","nodeParentId":"\"测试URLCode特殊字符\"","nodeTitle":"null","nodeStyle":"null","id":"17","ext4":"null","ext3":"null","nodeQuest":"null","ext2":"null","ext1":"null"}]
         *   null值变成了空串，不正确，
         *   解码后,返回json时会把转义符加上
         */
//        for (int i = 0 ; i < nodeRspList.size();i++){
//            //获取查询结果集中的Map集合
//            Map<String, String> map = nodeRspList.get(i);
//            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//            while (iterator.hasNext()){
//                Map.Entry<String, String> next = iterator.next();
//                //String s = String.valueOf(next.getValue());
//                String s1 = URLCode.deCode(next.getValue());
//                //System.out.println("value解码后数据："+s1);
//                map.put(next.getKey(),s1);
//            }
//        }


        /*
         * 解码的第二种方案：
         *  可以有效解决 第一种方案的null值，变成“null”字符串 ，但是应对所有的字段，代码写的冗余
         *  针对性一个key较强，但是应防止key重复
         */
        for (int i = 0 ; i < nodeRspList.size();i++){
            Map<String, String> map = nodeRspList.get(i);
            String nodeParentId = map.get("nodeParentId");
            String s = URLCode.deCode(nodeParentId);
            System.out.println("解码后：" + s);
            //未解码：[{nodeCirculate=null, nodeIsEffective=0, nodeWordCal=null, nodeLayout=null, nodeGrpId=null, nodeParentId="测试URLCode特殊字符", nodeTitle=null, nodeStyle=null, id=13, ext4=null, ext3=null, nodeQuest=null, ext2=null, ext1=null}]
            //解码后：[{"nodeCirculate":null,"nodeIsEffective":0,"nodeWordCal":null,"nodeLayout":null,"nodeGrpId":null,"nodeParentId":"\"测试URLCode特殊字符\"","nodeTitle":null,"nodeStyle":null,"id":13,"ext4":null,"ext3":null,"nodeQuest":null,"ext2":null,"ext1":null}]
            map.put("nodeParentId",s);
            System.out.println("测试的字符串1："+map.get("nodeWordCal"));//测试的字符串1：话术测试
            System.out.println("测试的字符串2："+map.get("nodeParentId"));//测试的字符串2："测试URLCode特殊字符"
        }

        System.out.println(ObjectMapperJson.toJson(nodeRspList));
        System.out.println("测试转成json后字符串:");

        nodeMap.put("result",nodeRspList);
        return nodeMap;
    }

    /**
     * 节点的更新（删除），b包含入参Map和批量更新的的操作
     * @param param
     * @return
     */
    @RequestMapping(value = "/nodeUpdateMap",method = {RequestMethod.POST})
    public Map<String,Object> nodeUpdateMap(@RequestBody Map<String,Object> param){
        Map<String, Object> nodeMap = new HashMap<>();
        int nodeRspCount = treeStructureService.nodeUpateMap(param);
        if (nodeRspCount>=1) {
            nodeMap.put("rspCode", "0");
            nodeMap.put("rspDesc", "更新成功");
            System.out.println(ObjectMapperJson.toJson(nodeMap));
        }else {
            nodeMap.put("rspCode", "-1");
            nodeMap.put("rspDesc", "更新失败");
        }
        return nodeMap;
    }


    /**
     * 节点新增的操作
     * 包含了特殊字符的新增，如双引号
     * @param param
     * @return
     */
    @RequestMapping(value = "/nodeInserteMap",method = {RequestMethod.POST})
    public Map<String,Object> nodeInserteMap(@RequestBody Map<String,Object> param){
        Map<String, Object> nodeMap = new HashMap<>();
        //把特殊字符编码，入库
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("paramMap");
        Map<String, Object> map = list.get(0);
        //遍历接收的Map
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            String value = String.valueOf(next.getValue());

            System.out.println("获取的key: "+key);
            System.out.println("获取的value: "+value);
            String rs = value.replaceAll("\"",URLCode.enCode("\""));
            map.put(key,rs);//重新覆盖掉原有的key的value值
            System.out.println("编码后："+ rs);
        }


        System.out.println(param);

        int nodeRspCount = treeStructureService.nodeInserteMap(param);
        if (nodeRspCount>=1) {
            nodeMap.put("rspCode", "0");
            nodeMap.put("rspDesc", "更新成功");
            System.out.println(ObjectMapperJson.toJson(nodeMap));
        }else {
            nodeMap.put("rspCode", "-1");
            nodeMap.put("rspDesc", "更新失败");
        }
        return nodeMap;
    }


    /**
     * 流转到下一节点同时返回下一节点信息(返回当前节点上的所有信息(包括标签和属性)，并且同时返回下一个节点的上的所有信息)
     * 这个内部也实现了----查询节点选项的集合的功能点(在实现类里TreeTreeStructureMapper.nextNodeList()方法上可以实现)
     * ------------------查询节点选项的集合的功能点，有疑惑（是否包含标签和属性的集合）
     * @param param
     * @return
     */
    @RequestMapping(value = "/nodeAndNextNodeMsg",method = {RequestMethod.POST})
    public Map<String,Object> nodeAndNextNodeMsg(@RequestBody Map<String,Object> param){
        Map<String, Object> nodeCurrentMapList = treeStructureService.nodeAndNextNodeMsg(param);
        //System.out.println(ObjectMapperJson.toJson(nodeCurrentMapList));
        return nodeCurrentMapList;
    }

    /**
     * 规则的查询,包含规则的要素,
     * 测试URLEncode编码和URLDecode解码
     * @param param
     * @return
     */
    @RequestMapping(value = "/nodeQryRuleAndEle",method = {RequestMethod.POST})
    public Map<String,Object> nodeQryRuleAndEle(@RequestBody Map<String,Object> param){
        //1.1 测试URLEncode编码和URLDecode解码
        String enCode = URLCode.enCode(param.get("URLCODE"));
        System.out.println("URLCODE的参数:"+param.get("URLCODE"));
        System.out.println("URLEncode编码:"+enCode);

        String deCode = URLCode.deCode(enCode);
        System.out.println("URLDecod解码:"+deCode);
        //URLEncode编码:%5B%7Burlcode%3Dvalue%27%27%E4%BD%A0%E5%A5%BD%7D%5D
        //URLDecod解码:[{urlcode=value''你好}]

        String deCodeToJson = ObjectMapperJson.toJson(deCode);
        System.out.println("解码后转成Json:"+deCodeToJson);

        //2.1 测试URLEncode编码和URLDecode解码 ,只转义特殊的字符串
        String urlcode = String.valueOf(param.get("URLCODE"));
        // 遍历这个串
        for (int i = 0 ;i < urlcode.length(); i++ ){
            char c = urlcode.charAt(i);
            System.out.print(" "+c);
        }
        String s = urlcode.replaceAll("\"", URLCode.enCode("\""));
        System.out.println("特殊字符编码后"+s);
        String ss = urlcode.replaceAll("\"", URLCode.deCode("\""));
        System.out.println("特殊字符解码后"+ss);



        //获取当前节点的规则的信息，内部包含规则的要素
        Map<String, Object> map = treeStructureService.nodeRuleMsg(param);
        map.put("解码的数据",deCode);//如果要求编码的数据,内部是一个list包含Map,把这个List解码后会成为类似于  [{urlcode=value''你好}] 形式
        return  map;
    }

    /**
     * 判断节点选项是否符合规则,用于筛选不符合的数据
     * @param param
     * @return
     */
    @RequestMapping(value = "/nodeConformRule",method = {RequestMethod.POST})
    public Map<String,Object> nodeConformRule(@RequestBody Map<String,Object> param){
        //1.获取到规则的信息以及此规则的要素的信息
        Map<String, Object> ruleMapMsg = treeStructureService.nodeRuleMsg(param);
        //2.对节点是否符合规则的处理
        System.out.println("规则的信息："+ruleMapMsg);
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> rulEleList = (List<Map<String, Object>>) ruleMapMsg.get("nodeRulEle");
        for (Map<String, Object> map : rulEleList) {
            Object re_result = map.get("re_result");
            Object re_range = map.get("re_range");
            Object re_accord = map.get("re_accord");
            Object re_param = map.get("re_param");
            System.out.println("获取的要素的结果：" + re_result);
            System.out.println("获取的要素的范围：" + re_range);
            System.out.println("获取的要素的运算：" + re_accord);
            System.out.println("获取的要素的参数：" + re_param);
            //3.对要素的处理
            String strRange = String.valueOf(re_range);
            //3.1 判断字符串是否是数字
            for (int i = strRange.length() ; --i >= 0; ){
                if(Character.isDigit(strRange.charAt(i))){//判断是否是数值
                    System.out.println(strRange.charAt(i));
                    System.out.println("数字");
                }
            }

            Integer integer = Integer.valueOf(strRange);
            System.out.println("范围转换成数值："+integer);
            int i = Integer.parseInt(strRange);
            System.out.println("范围转换成数值："+i);
            Integer integer1 = Integer.valueOf(String.valueOf(re_param));
            if (integer1<integer){
                System.out.println("运算正确");
            }
            //4.1 判断规则是否符合规则，用于筛选数据
            //4.2 获取到的数据
            String accord = String.valueOf(re_accord);
            String params = String.valueOf(re_param);
            String result = String.valueOf(re_result);
            //4.3 与当前节点的校验
        }
        return ruleMapMsg;
    }
}
