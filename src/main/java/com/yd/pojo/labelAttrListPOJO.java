package com.yd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class labelAttrListPOJO implements Serializable {
    private static final long serialVersionUID = -6561993258322520348L;
    private String id;
    private String attrLabelId;
    private String attrName;
    private String attrValue;
    private String attrIsEffective;
    private String cmosModifyTime;
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;



}
