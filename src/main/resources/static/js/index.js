var treejson = '{"ID":"1","NODE_CIRCULATE":"","NODE_GRP_ID":"1","NODE_LAYOUT":"","NODE_PARENT_ID":"0","NODE_QUEST":"请提供一下您的宽带账号","NODE_STYLE":"","NODE_TITLE":"输入宽带账号","NODE_WORD_CAL":"","nodelist":[{"ID":"2","NODE_CIRCULATE":"auto","NODE_GRP_ID":"1","NODE_LAYOUT":"","NODE_PARENT_ID":"1","NODE_QUEST":"查看宽带账号是否欠费","NODE_STYLE":"background-color:#33FF66;width:300px;height:300px;border:2px solid;","NODE_TITLE":"查看宽带账号是否欠费","NODE_WORD_CAL":"","nodelist":[{"ID":"3","NODE_CIRCULATE":"auto","NODE_GRP_ID":"1","NODE_LAYOUT":"","NODE_PARENT_ID":"2","NODE_QUEST":"您的号码已欠费，建议您充值之后使用","NODE_STYLE":"background-color:#33FF66;width:300px;height:300px;border:2px solid;","NODE_TITLE":"您的号码已欠费，建议您充值之后使用","NODE_WORD_CAL":"","nodelist":[{"ID":"4","NODE_CIRCULATE":"","NODE_GRP_ID":"1","NODE_LAYOUT":"","NODE_PARENT_ID":"3","NODE_QUEST":"挂机","NODE_STYLE":"background-color:#33FF66;width:300px;height:300px;border:2px solid;","NODE_TITLE":"挂机","NODE_WORD_CAL":"","nodelist":[],"taglist":[{"ID":"7","TAG_ACCESS_WAY":"","TAG_ATTR_ID":"tag_1_7","TAG_CLASS":"span","TAG_HTML":"","TAG_IS_CIRCULATION":"","TAG_IS_EFFECTIVE":"1","TAG_NAME":"tag_1_7","TAG_NODE_ID":"4","TAG_TEXT":"测试","TAG_VALUE":"","tagattrlist":[{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"style","ATTR_TAG_ID":"7","ATTR_VALUE":"width:200px;height:100px;border:1px solid;","ID":"8"}]}]}],"taglist":[{"ID":"6","TAG_ACCESS_WAY":"","TAG_ATTR_ID":"tag_1_6","TAG_CLASS":"span","TAG_HTML":"","TAG_IS_CIRCULATION":"","TAG_IS_EFFECTIVE":"1","TAG_NAME":"tag_1_6","TAG_NODE_ID":"3","TAG_TEXT":"测试","TAG_VALUE":"","tagattrlist":[{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"style","ATTR_TAG_ID":"6","ATTR_VALUE":"width:200px;height:100px;border:1px solid;","ID":"7"}]}]}],"taglist":[{"ID":"5","TAG_ACCESS_WAY":"","TAG_ATTR_ID":"tag_1_5","TAG_CLASS":"span","TAG_HTML":"","TAG_IS_CIRCULATION":"","TAG_IS_EFFECTIVE":"1","TAG_NAME":"tag_1_5","TAG_NODE_ID":"2","TAG_TEXT":"测试","TAG_VALUE":"","tagattrlist":[{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"style","ATTR_TAG_ID":"5","ATTR_VALUE":"width:200px;height:100px;border:1px solid;","ID":"6"}]}]}],"taglist":[{"ID":"1","TAG_ACCESS_WAY":"","TAG_ATTR_ID":"tag_1_1","TAG_CLASS":"span","TAG_HTML":"","TAG_IS_CIRCULATION":"","TAG_IS_EFFECTIVE":"1","TAG_NAME":"tag_1_1","TAG_NODE_ID":"1","TAG_TEXT":"请提供一下您的宽带账号","TAG_VALUE":"","tagattrlist":[{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"style","ATTR_TAG_ID":"1","ATTR_VALUE":"width:200px;height:30px;","ID":"1"}]},{"ID":"4","TAG_ACCESS_WAY":"","TAG_ATTR_ID":"tag_1_4","TAG_CLASS":"span","TAG_HTML":"","TAG_IS_CIRCULATION":"","TAG_IS_EFFECTIVE":"1","TAG_NAME":"tag_1_4","TAG_NODE_ID":"1","TAG_TEXT":"询问客户宽带账号","TAG_VALUE":"","tagattrlist":[{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"style","ATTR_TAG_ID":"4","ATTR_VALUE":"width:200px;height:100px;border:1px solid;","ID":"4"}]},{"ID":"2","TAG_ACCESS_WAY":"","TAG_ATTR_ID":"tag_1_2","TAG_CLASS":"span","TAG_HTML":"","TAG_IS_CIRCULATION":"","TAG_IS_EFFECTIVE":"1","TAG_NAME":"tag_1_2","TAG_NODE_ID":"1","TAG_TEXT":"宽带账号：","TAG_VALUE":"","tagattrlist":[{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"style","ATTR_TAG_ID":"2","ATTR_VALUE":"width:80px;height:18px;float:left;","ID":"2"}]},{"ID":"3","TAG_ACCESS_WAY":"","TAG_ATTR_ID":"tag_1_3","TAG_CLASS":"input","TAG_HTML":"","TAG_IS_CIRCULATION":"","TAG_IS_EFFECTIVE":"1","TAG_NAME":"tag_1_3","TAG_NODE_ID":"1","TAG_TEXT":"","TAG_VALUE":"","tagattrlist":[{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"type","ATTR_TAG_ID":"3","ATTR_VALUE":"text","ID":"3"},{"ATTR_IS_EFFECTIVE":"1","ATTR_NAME":"style","ATTR_TAG_ID":"3","ATTR_VALUE":"width:80px;height:18px;float:left;","ID":"5"}]}]}';

$(function(){//页面一加载完就自动执行该方法

    var treeobj = eval ("(" + treejson + ")");
    //遍历节点
    parseTree(treeobj);
});


var parseTree = function(node){
    createNodeTable(node);


    //下面的节点
    for(var i = 0; i < node.nodelist.length; i++){
        parseTree(node.nodelist[i]);
    }

}


//创建一个自定义节点
var createNodeTable = function(node){
    var trFlowNode = $('<tr></tr>');
    trFlowNode.appendTo(document.getElementById("tbFlowTree"));
    //创建导航
    var tdNav = $('<td class="cssTdNav" align="center"></td>');
    tdNav.attr('id','nav_'+node.ID);
    tdNav.appendTo(trFlowNode);
    //导航数字 球
    var divCircle0 = $('<div style="margin-top:5px;"></div>');
    divCircle0.appendTo(tdNav);

    //var imgCircle1 = $('<img style=" top:0px; left:0px;width:16px; height:16px;position:absolute;" id="u1482_img" src="正在处理_u798.png" ></img>');
    //imgCircle1.attr('id','imgCircle_'+node.ID);
    //imgCircle1.appendTo(divCircle0);

    var divCircle1 = $('<div style="background-image:url(" 正在处理_u798.png");"></div>');
    divCircle1.attr('id','navCircle_'+node.ID);
    divCircle1.css("width","16px");
    divCircle1.css("height","16px");
    divCircle1.appendTo(divCircle0);
    ;

    var spanCircle3 = $('<span class="cssSpanCircle"   ></span>');
    //var spanCircle3 = $('<span style="width:100px;height:100px "  ></span>');
    spanCircle3.attr('id','navText_'+node.ID);
    spanCircle3.appendTo(divCircle1);
    spanCircle3.append('2');


    var line = $('<div style="background-color:#797979; width:2px; height:100px;"></div>');
    line.appendTo(tdNav);


    //创建节点
    var node1 = $('<td class="cssTdNode"></td>');
    node1.attr('id','node_'+node.ID);
    node1.appendTo(trFlowNode);

    //解析标签
    var arrtag = node.taglist;
    for(var i = 0; i < arrtag.length; i++){
        var tag = arrtag[i];
        var tag1;
        var div1;
        if(tag.TAG_CLASS.toUpperCase() == 'SPAN'){
            tag1 = $('<span></span>');
            tag1.attr('id','tag_'+tag.TAG_NODE_ID+"_"+tag.ID);
            tag1.attr('name','tag_'+tag.TAG_NODE_ID+"_"+tag.ID);
            tag1.attr('HASINFO',tag.TAG_IS_CIRCULATION);
            tag1.append(tag.TAG_TEXT);

            div1 = $('<div class="cssTag"></div>');


            tag1.appendTo(div1);
            div1.appendTo(node1);


        }else if(tag.TAG_CLASS.toUpperCase() == 'INPUT'){
            tag1 = $('<input></input>');
            tag1.attr('id','tag_'+tag.TAG_NODE_ID+"_"+tag.ID);
            tag1.attr('name','tag_'+tag.TAG_NODE_ID+"_"+tag.ID);
            tag1.attr('HASINFO',tag.TAG_IS_CIRCULATION);

            div1 = $('<div class="cssTag"></div>');

            tag1.appendTo(div1);
            div1.appendTo(node1);
        }else{

        }
        //标签属性
        var arrtagattr = tag.tagattrlist;
        for(var j = 0; j < arrtagattr.length; j++){
            tagattr = arrtagattr[j];
            //如果是样式
            if(tagattr.ATTR_NAME.toUpperCase() == 'STYLE'){
                var arrtagcss = tagattr.ATTR_VALUE.split(';');
                for(var k = 0; k < arrtagcss.length; k++){
                    if(arrtagcss[k].length > 0){
                        tag1.css(arrtagcss[k].split(':')[0],arrtagcss[k].split(':')[1]);
                    }
                }
            }else{
                tag1.attr(tagattr.ATTR_NAME,tagattr.ATTR_VALUE);
            }
        }

    }

}



