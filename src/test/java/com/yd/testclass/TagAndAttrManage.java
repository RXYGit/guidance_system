package com.yd.testclass;

import com.yd.commom.JsonUtil;
import com.yd.commom.ObjectMapperJson;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签的管理以及标签的属性的管理
 */
public class TagAndAttrManage {
    /**
     * 标签的查询,标签内部包含了标签的属性的集合
     */
    @Test
    public void selectTag(){
        String tagId = "1";
        System.out.println(ObjectMapperJson.toJson(GetTree2.selectTag(tagId)));

    }

    /**
     * 标签的更新，包含标签的删除操作，更改标签的是否生效的状态
     */
    @Test
    public void updateTag(){
        String tagId = "1";
        System.out.println(ObjectMapperJson.toJson(GetTree2.updateTag(tagId)));
    }

    @Test
    public void insertTag(){
        Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("tagId",7);
        tagMap.put("tagNodeId","3");
        tagMap.put("tagAttrId","demo2");
        tagMap.put("tagEffective","0");
        tagMap.put("tagText","新增测试2");
        System.out.println(ObjectMapperJson.toJson(GetTree2.insertTag(tagMap)));
    }

    /*******************************************************************************************************************************/

    @Test
    public void tagAttrSelect(){
        String attrId = "1";
        System.out.println(JsonUtil.to(GetTree2.attrSelect(attrId)));
    }
}
