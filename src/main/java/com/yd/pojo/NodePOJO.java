package com.yd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodePOJO implements Serializable {

    private static final long serialVersionUID = -1292517795310787298L;
    //节点的表和标签集合
    private String id;
    private String nodeLastId;
    private String nodeTree;
    private String nodeTitle;
    private String nodeQuest;
    private String nodeStyle;
    private String nodeIsEffective;
    private String nodeCirculate;
    private String nodeLayout;
    private String cmosModifyTime;
    private String ext2;
    private String ext3;
    private String ext4;


}
