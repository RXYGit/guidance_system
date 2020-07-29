package com.yd.testclass;

import com.yd.commom.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 组管理和组关系管理
 */
public class GroupManage {

    @Test
    public void grpSelect(){
        String grpId = "1";
        System.out.println(JsonUtil.to(GetTree2.grpSelect(grpId)));
    }

    /**
     * 组的更新和删除，删除查看组的直接
     */
    @Test
    public void grpUpdate(){
        String grpId = "1";
        System.out.println(JsonUtil.to(GetTree2.grpUpdate(grpId)));
    }

    /**
     * 组的新增操作
     */
    @Test
    public void grpInsert(){
        Map<String, Object> grpMap = new HashMap<>();
        grpMap.put("grpId",4);
        grpMap.put("grpName","新增组的测试1");
        System.out.println(JsonUtil.to(GetTree2.grpInsert(grpMap)));
    }
}
