package com.yd.testclass;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yd.commom.JsonUtil;
import com.yd.commom.ObjectMapperJson;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 规则的管理以及规则要素的管理
 */
public class RuleAndEleManage {

    /**
     * 规则的的查询
     */
    @Test
    public void ruleSelect(){
        String ruleId = "1";
        System.out.println(JsonUtil.to(GetTree2.ruleSelect(ruleId)));
    }
    /**
     * 规则的的更新删除
     */
    @Test
    public void ruleUpdate(){

        String ruleId = "1";
        System.out.println(JsonUtil.to(GetTree2.ruleUpdate(ruleId)));
    }
    /**
     * 规则的新增
     */
    @Test
    public void ruleInsert(){
        Map<String, Object> ruleMap = new HashMap<>();
        ruleMap.put("ruleId",3);
        ruleMap.put("ruleName","新增测试名称");
        System.out.println(JsonUtil.to(GetTree2.ruleInsert(ruleMap)));
    }

    /******************************************************************************************************/

    /**
     * 规则要素的查询
     */
    @Test
    public void ruleEleSelect(){
        String eleId = "";
        System.out.println(JsonUtil.to(GetTree2.eleSelect(eleId)));
    }
    /**
     * 规则要素的更新
     */
    @Test
    public void ruleEleUpdate(){
        //1.2 入参测试
       Map<String, String> param = new HashMap<>();
       param.put("id","1");
        param.put("re_rule_id","1");
        param.put("re_param","参数");
        param.put("re_accord","规则");
        String s = ObjectMapperJson.toJson(param);
        System.out.println(s);

        //利用了阿里的fastJson，实现json到map
        Map<String, String> jsonToMap = JSONObject.parseObject(s, new TypeReference<Map<String, String>>() {
        });
        System.out.println("fastjson:"+jsonToMap);

        //利用jackson，实现json到map
        Map map = ObjectMapperJson.toString(s, Map.class);
        System.out.println("jackson:"+map);

        //1.1 固定值
        String eleId="1";
        System.out.println(JsonUtil.to(GetTree2.eleUpdate(param.get("id"))));
    }

    /**
     * 规则要素的新增
     */
    @Test
    public void ruleEleInsert(){
        String eleID = "3";
        System.out.println(JsonUtil.to(GetTree2.eleInsert(eleID)));
    }



}

