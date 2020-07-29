package com.yd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelListPOJO implements Serializable {
    private static final long serialVersionUID = -6943567832823222426L;
    private String id;
    private String labNodeId;
    private String labWordCal;
    private String labName;
    private String labValue;
    private String labClass;
    private String labAccessWay;
    private String labJsFunction;
    private String labSort;
    private String labType;
    private String labIsEffective;
    private String labIsCirculation;
    private String labHtml;
    private String labText;
    private String cmosModifyTime;
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;

}
