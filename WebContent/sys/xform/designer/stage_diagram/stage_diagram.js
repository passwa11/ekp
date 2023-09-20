/**********************************************************
 功能：阶段图
 使用：

 作者：admin
 创建时间：2019-02-25
 **********************************************************/
Designer_Config.operations['stageDiagram'] = {
    lab : "2",
    imgIndex : 71,
    title :Designer_Lang.stage_diagram_name,
    run : function(designer) {
        designer.toolBar.selectButton('stageDiagram');
    },
    type : 'cmd',
    order: 81,
    shortcut : '',
    isAdvanced: true,
    select : true,
    cursorImg : 'style/cursor/stageDiagram.cur'
};

Designer_Config.buttons.form.push("stageDiagram");
Designer_Menus.form.menu['stageDiagram'] = Designer_Config.operations['stageDiagram'];

Designer_Config.controls.stageDiagram = {
    type : "stageDiagram",
    storeType : 'none',
    inherit : 'base',
    container : false,
    onDraw : _Designer_Control_StageDiagram_OnDraw,
    drawXML : _Designer_Control_StageDiagram_DrawXML,
    drawMobile : _Designer_Control_StageDiagram_DrawMobile,
    implementDetailsTable : false,
    attrs : {
        stage:{
            text : "<span style='cursor:pointer' onclick='addStage();' title='" + Designer_Lang.stage_diagram_stage + "'>" +
                "<label>" + Designer_Lang.stage_diagram_stage + "</label>" +
                "<img style='margin-bottom:-4px;' src='relation_rule/style/icons/add.gif'></img>" +
                "</span>",//阶段
            value: '',
            show : true,
            type : 'self',
            draw : _Designer_Control_Attr_Stage_Diagram_Self_Stage_Draw,
            checkout: _Stage_Diagram_Not_Null_CheckOut,
            validator :[_Stage_Diagram_Not_Null_Validator,
                _Stage_Diagram_AllNode_Must_Select]
        },
        displayItem:{
            text : Designer_Lang.stage_diagram_displayItem,
            value : '',
            type : 'checkGroup',
            opts : [
                {name:"startEndNode",text: Designer_Lang.stage_diagram_startEndNode, value: 'true'}, //开始/结束节点
                {name:"approvalTime",text: Designer_Lang.stage_diagram_finishTime, value: 'true'} //审批时间
            ],
            show: true
        }
    },
    info : {
        name : Designer_Lang.stage_diagram_name
    },
    defaultStageSize : 3,
    stageId : "stage_diagram_stage",
    resizeMode : 'no'
};

function _Designer_Control_Attr_Stage_Diagram_Self_Stage_Draw(name, attr,
                                                              value, form, attrs, values, control) {
    var html = "<div id='" + Designer_Config.controls.stageDiagram.stageId + "'>";
    var val = value || "[]";
    val = Designer.HtmlUnEscape(val);
    var stageValues = _Designer_Control_Attr_Stage_Diagram_parseStr(val);
    var i = 0;
    html += "<input type='hidden' name='stage' value='"+ value +"'/>";
    for(var uid in stageValues){
        var val = stageValues[uid];
        html += _createStage(val,i==0);
        i++;
    }
    if(val == "[]"){
        for (var i = 0; i < Designer_Config.controls.stageDiagram.defaultStageSize; i++){
            html += _createStage({uid:'uid'+Designer.generateID()},i == 0);
        }
    }

    html += "</div>";
    return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//解析string
function _Designer_Control_Attr_Stage_Diagram_parseStr(str){
    var val;
    try{
        if (!str){
            return val;
        }
        str = Designer.HtmlUnEscape(str);
        val = JSON.parse(str);
    }catch(e){
        console.log(str + " 转换为json时出错！");
    }
    return val;
}

function addStage(){
    var stageIdSelector = "#" + Designer_Config.controls.stageDiagram.stageId;
    var isFist = $(stageIdSelector).html() == '';
    $(stageIdSelector).append(_createStage({uid:'uid'+Designer.generateID()},isFist));
}

function removeStage(uid,obj){
    var stageValObjs = _Stage_Diagram_Get_Stage_Val();
    var stageIdSelector = "#" + Designer_Config.controls.stageDiagram.stageId;
    //校验至少保留一个动作
    if($(stageIdSelector).children("div").length == 1){
        alert(Designer_Lang.stage_diagram_requiredOne + Designer_Lang.stage_diagram_stage);
        return ;
    }
    var hasIn=false;
    for(var stageV in stageValObjs){
        if(uid == stageValObjs[stageV].uid){
            //从数据组中移除掉这个元素
            stageValObjs.splice(stageV, 1);
            _Stage_Diagram_Set_Stage_Val(stageValObjs);
            break;
        }
    }
    $(obj).parent().remove();
    //显示按钮面板
    Relation_ShowButton();
}

function _createStage(field,isFirst) {
    //显示按钮面板
    Relation_ShowButton();
    var html = [];

    var uid = field.uid;
    var stageName = field.stageName ? field.stageName : '';
    var stageNodeId = field.stageNodeId ? field.stageNodeId : '';
    var stageNodeName = field.stageNodeName ? field.stageNodeName : '';

    html.push("<div id=''>");
    //第一个元素不需要分隔符
    if(!isFirst){
        html.push("<hr />");
    }
    html.push("<label>" + Designer_Lang.stage_name + "</label>" + "<br/>" +
        "<input style='width:114px;vertical-align:middle;' required='true' name='stageName_" + uid + "' value='" + stageName + "' onchange=\"_Stage_Diagram_Name_Change('"+uid+"',this);\"/>" +
        "<span class=txtstrong>*</span>" );
    if (!isFirst){
        html.push("<img src='relation_rule/style/icons/delete.gif' style='cursor:pointer;margin-bottom:-5px;' onclick=\"removeStage('"+uid+"',this);\"></img>");
    }
    html.push("<br/>");
    html.push("<label>" + Designer_Lang.stage_belong_node + "</label>" +
        "<input type='hidden' value='" + stageNodeId +"' name='stageNodeId_" + uid + "' />" + "<br/>" +
        "<input name='stageNodeName_" + uid + "' readOnly=true  style='width:114px;vertical-align:middle;' class='inputsgl' value='" + stageNodeName + "'/>" +
        "<a href='javascript:void(0)' onclick =\"Stage_Diagram_Dialog_List_ShowNode('" + uid + "');\">" + Designer_Lang.stage_choose + "</a>"
    );
    html.push("<span class=txtstrong>*</span></br>");
    html.push("</div>");
    return html.join("");
}

function Stage_Diagram_Convert_HTML_ForJSon(str){
    return Designer.HtmlEscape(str);
}

function Stage_Diagram_Dialog_List_ShowNode(uid){
    var stageValObjs = _Stage_Diagram_Get_Stage_Val();
    var stageVal={"uid":uid};
    var idField = "stageNodeId_" + uid;
    var nameField = "stageNodeName_" + uid;
    var hasIn = false;
    var otherStageNodeId = [];
    var currentStageNodeId = [];
    for(var stageV in stageValObjs){
        var nodeIdStr = stageValObjs[stageV].stageNodeId || "";
        if(uid == stageValObjs[stageV].uid){
            stageVal = stageValObjs[stageV];
            hasIn = true;
            currentStageNodeId = nodeIdStr.split(";");
        }else{
            var otherNodeId = nodeIdStr.split(";");
            otherStageNodeId = otherStageNodeId.concat(otherNodeId);
        }
    }
    var splitStr = ";"
    var dialog = new KMSSDialog(true, true);
    dialog.BindingField(idField, nameField, splitStr, "");

    var action = function(rtn){
        if(!rtn) return;
        var idArr = [],nameArr = [];
        //保存条件
        for (var i = 0,len = rtn.data.length; i < len; i++){
            idArr.push(rtn.data[i].id);
            nameArr.push(rtn.data[i].name);
        }
        stageVal.stageNodeId = Stage_Diagram_Convert_HTML_ForJSon(idArr.join(splitStr));
        stageVal.stageNodeName = Stage_Diagram_Convert_HTML_ForJSon(nameArr.join(splitStr));
        if(!hasIn){
            stageValObjs.push(stageVal);
        }
        _Stage_Diagram_Set_Stage_Val(stageValObjs);
    };

    dialog.SetAfterShow(action);

    //获取流程中所有节点
    var wfNodes=(window.XForm_GetWfAuditNodes == null)?[]:XForm_GetWfAuditNodes();
    var data = new KMSSData();
    var ary = new Array();
    for(var i=0;i<wfNodes.length;i++){
        if('signNode'==wfNodes[i].type || 'reviewNode'==wfNodes[i].type
            || 'sendNode'==wfNodes[i].type || 'draftNode'==wfNodes[i].type
            || 'voteNode' == wfNodes[i].type){
            //设置选中节点
            var temp = new Array();
            temp["id"]= wfNodes[i].value;
            temp["name"]= wfNodes[i].name;
            //其它阶段不包含该节点才添加
            if (otherStageNodeId.indexOf(wfNodes[i].value) < 0){
                ary.push(temp);
            }
        }
    }

    data.AddHashMapArray(ary);
    dialog.optionData.AddKMSSData(data);
    dialog.Show(window.screen.width*520/1366,window.screen.height*400/768);
}

function _Designer_Control_StageDiagram_OnDraw(parentNode, childNode) {
    var values = this.options.values;
    if (values.id == null){
        values.id = "fd_" + Designer.generateID();
    }
    var domElement = _CreateDesignElement('div', this, parentNode, childNode);
    //再次编辑时,stage的val值没有被转义, 如果只改了开始/结束节点或者显示时间的值,保存提交后台会报错
    if (values.stage){
        values.stage = Designer.HtmlUnEscape(values.stage);
        values.stage = Stage_Diagram_Convert_HTML_ForJSon(values.stage);
    }
    var stage = values.stage || "[]";
    $(domElement).css("width","100%");
    $(domElement).css("min-width","400px");
    if (values.stage){
        stage = _Designer_Control_Attr_Stage_Diagram_parseStr(stage);
    }else{
        stage = null
    }
    var stageDiagramHtml = _Stage_Diagram_Draw_Diagram(values, stage, domElement);
    $(domElement).append(stageDiagramHtml);
}

function _Stage_Diagram_Draw_Diagram(values, stage, domElement){
    var length = stage ? stage.length : Designer_Config.controls.stageDiagram.defaultStageSize;
    var stageVals = stage || [];
    if (values.startEndNode === "true"){
        length += 2;
    }
    var width = $(domElement).parent().width();
    var html = [];
    //125为一个阶段的宽度,width/125计算出阶段图控件所在的父节点能存放的阶段个数,也就是一个ul里面包含的li个数
    var lineLiNum = parseInt(width/125) || 4;
    //计算需要多少行,也就是需要多少个ul
    var lineNum = Math.ceil(length/lineLiNum);
    //圆环流程图表 Starts
    html.push("<div class='lui-flow-rotundity'>");
    for (var i = 0; i < lineNum; i++){
        html.push("<ul class='lui-flow-rotundity-list'>");
        for(var j = 0; j < lineLiNum; j++){
            if ((i * lineLiNum + j) == length){
                break;
            }
            var stageName = "";
            var index = i * lineLiNum + j;
            if(values.startEndNode === "true"){
                //开始节点
                if (i == 0 && j == 0){
                    stageName = Designer_Lang.stage_diagram_start_node;
                }else if(i == lineNum - 1 && j == (length - (i * lineLiNum + 1))){//结束节点
                    stageName = Designer_Lang.stage_diagram_end_node;
                }else{//阶段节点
                    var stageObj = stageVals[index - 1];
                    if (stageObj){
                        stageName = stageObj.stageName;
                    }
                }
            }else{//不显示开始/结束节点
                var stageObj = stageVals[index];
                if (stageObj){
                    stageName = stageObj.stageName;
                }
            }
            //i != lineNum -1 说明多行非最后一行,j == lineLiNum - 1说明当前行的最后一个,就是除了最后一行,其它行的最后一个阶段都要添加一个last-child样式
            if (i != lineNum - 1 && j == lineLiNum - 1){
                html.push("<li class='last-child'>");
            }else{
                html.push("<li>");
            }
            html.push("<span class='spot'></span>");
            html.push("<p class='title'>" + stageName + "</p>");
            html.push("</li>")
        }
        html.push("</ul>");
    }
    html.push("</div>");
    //圆环流程图表 Ends
    return html.join("");
}

function _Stage_Diagram_Name_Change(uid,obj){
    var stageValObjs = _Stage_Diagram_Get_Stage_Val();
    var stageVal = {"uid":uid};
    var hasIn = false;
    for(var stageV in stageValObjs){
        if(uid == stageValObjs[stageV].uid){
            stageVal = stageValObjs[stageV];
            hasIn = true;
            break;
        }
    }
    stageVal.stageName = $(obj).val();
    if(!hasIn){
        stageValObjs.push(stageVal);
    }
    _Stage_Diagram_Set_Stage_Val(stageValObjs);
}

function _Stage_Diagram_Set_Stage_Val(stageValObjs){
    stageValObjs = stageValObjs || [];
    $("input[type='hidden'][name='stage']").val(Designer.HtmlEscape(JSON.stringify(
        stageValObjs)));
}

function _Stage_Diagram_Get_Stage_Val(){
    var stageVals = $("input[type='hidden'][name='stage']").val();
    stageVals = stageVals || "[]";
    stageVals = Designer.HtmlUnEscape(stageVals);
    var stageValObjs = _Designer_Control_Attr_Stage_Diagram_parseStr(stageVals) || [];
    return stageValObjs;
}

function _Stage_Diagram_Not_Null_Validator(elem, name, attr, value, values) {
    var $stageAttrObj = $("#" + Designer_Config.controls.stageDiagram.stageId);
    var $stageNameObj = $("input[name*='stageName_']",$stageAttrObj);
    var $stageNodeIdObj = $("input[name*='stageNodeId_']",$stageAttrObj);
    var isPass = true;
    var callback = function(i,dom){
        var val = $(dom).val() || "";
        val = val.replace(/\s+/g,"");
        if (val == ""){
            isPass = false;
            return isPass;
        }
    }
    $stageNameObj.each(callback);
    if (isPass){
        $stageNodeIdObj.each(callback);
    }
    if (!isPass){
        alert(Designer_Lang.stage_diagram_stage_not_null);
    }
    return isPass;
}

function _Stage_Diagram_AllNode_Must_Select(elem, name, attr, value, values){
    //获取流程中所有节点
    var wfNodes=(window.XForm_GetWfAuditNodes == null)?[]:XForm_GetWfAuditNodes();
    var allNodes = new Array();
    for(var i=0;i<wfNodes.length;i++){
        if('signNode'==wfNodes[i].type || 'reviewNode'==wfNodes[i].type
            || 'sendNode'==wfNodes[i].type || 'draftNode'==wfNodes[i].type
            || 'voteNode' == wfNodes[i].type){
            allNodes.push(wfNodes[i]);
        }
    }
    var stageValObjs = _Stage_Diagram_Get_Stage_Val();
    var selectedNodes = [];
    for (var i = 0; i < stageValObjs.length; i++){
        var stageObj = stageValObjs[i];
        var nodeIds = stageObj.stageNodeId.split(";");
        selectedNodes = selectedNodes.concat(nodeIds);
    }
    var isPass = true;
    if (allNodes.length != selectedNodes.length){
        alert(Designer_Lang.stage_diagram_allNode_must_selected);
        isPass = false;
    }
    return isPass;
}

function _Stage_Diagram_Not_Null_CheckOut(msg, name, attr, value, values, control){
    if (!value || value == "[]"){
        msg.push(Designer_Lang.stage_diagram_name+""+values.id,","+Designer_Lang.stage_diagram_stage+""+Designer_Lang.stage_diagram_notNull);
        return false;
    }
    return true;
}

function _Designer_Control_StageDiagram_DrawXML() {

}