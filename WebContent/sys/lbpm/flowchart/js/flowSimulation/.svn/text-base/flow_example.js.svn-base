/**
 * 流程仿真实例对象
 */
var FlowExample = new Object();
/**
 * 节点模拟对象
 */
FlowExample.nodeSimulation = NodeSimulation;
/**
 * 根据sysOrgElement的FdId获取人员所有角色
 * @param {sysOrgElementFdId} sysOrgElementFdId
 */
FlowExample.getFdHandlerRoleInfoIds = function (sysOrgElementFdId) {
    var paramArray = new Array();
    paramArray.push("RequestType=getFdHandlerRoleInfoIds");
    paramArray.push("fdId=" + encodeURIComponent(sysOrgElementFdId));
    var vJsonArray = RequestUtil.postRequestServers(paramArray.join("&"));
    return vJsonArray;
}
/**
 * 根据起草人加载起草人身份
 */
FlowExample.loadFdHandlerRoleInfoIds = function () {
    var vSysOrgElementFdId = document.getElementById("detail_attr_value").value;
    var vJsonArray = null;
    if (vSysOrgElementFdId != "") {
        vJsonArray = this.getFdHandlerRoleInfoIds(vSysOrgElementFdId);
    }
    else {
        //起草人为空时隐藏该项
        $("#trFdHandlerRoleInfoIds").hide();
    }
    if (vJsonArray != null) {
    	$("#trFdHandlerRoleInfoIds").hide();
        if (vJsonArray[0].type == "ok") {
        	//起草人类型为部门或者机构时使用规则运算
//        	if(vJsonArray[0].orgType==2||vJsonArray[0].orgType==1){
//        		$("#trOrgSimulationRule").show();
//        	}
//        	else{  	}	
        	 $("#sFdHandlerRoleInfoIds").html("");
        	var vFdHandlerRoleInfoIds = vJsonArray[0].handlerRoleInfoIds.split(";");
            for (i in vFdHandlerRoleInfoIds) {
                var iTemp = vFdHandlerRoleInfoIds[i].split("|");
                if (iTemp == 0) {
                    $("#sFdHandlerRoleInfoIds").append("<option value='" + iTemp[0] + "' selected=\"selected\">" + iTemp[1] + "</option>");
                }
                else {
                    $("#sFdHandlerRoleInfoIds").append("<option value='" + iTemp[0] + "'>" + iTemp[1] + "</option>");
                }

            }
            $("#trFdHandlerRoleInfoIds").show();//起草人身份请求成功后显示该内容
        	
            
        }
    }
}
/**
 * 获取流程节点中所有的公式
 *nodes:流程节点集合
 */
FlowExample.obtainFormulaText = function (nodes) {
    var result = "";
    //获取流程中所有的公式
    for (n in nodes) {
        //流程节点类型为条件分支或者并行分支，且启动类型为公式计算时
        if (nodes[n].TypeCode == 3 || (nodes[n].Type == "splitNode" && nodes[n].Data.splitType == "condition")) {
            var vLineOut = nodes[n].LineOut;
            for (l in vLineOut) {
                result += vLineOut[l].Data.condition;//获取公式
            }
        }
        //流程节点为普通节点，审批人为公式计算时
        if (nodes[n].TypeCode == 0 && (nodes[n].Data.handlerSelectType == "formula" || nodes[n].Data.handlerSelectType == "matrix")) {
           
            if(nodes[n].Data.handlerSelectType != "matrix"){
            	result += nodes[n].Data.handlerIds;//获取公式
            }
            
            
            var resultTemp=nodes[n].Data.handlerIds;
            
            if(nodes[n].Data.handlerSelectType == "matrix"){
            	//矩阵要特殊处理
            	if(resultTemp){
            		try{
            			var newResult = "";
            			var content = eval('('+resultTemp+')');
            			var conditionals = content.conditionals;
            			if(conditionals){
            				for(var j=0; j<conditionals.length; j++){
            					var conditional = conditionals[j];
            					if(conditional && conditional.value){
            						newResult += conditional.value + ";";//拼装矩阵使用的公式变量，用于解析得到表单参数
            					}
            				}
            			}
            			result += newResult;
            		}catch(e){
            			//避免仿真出错
            			alert("矩阵解析出错，请重新点击流程仿真或者坚持矩阵的内容！")
            		}
            	}
            }
        }
         var businessAuthInfo = nodes[n].Data.businessAuthInfo;
         if( businessAuthInfo!=null || businessAuthInfo!= undefined ){
	        var businessAuthInfoJson= JSON.parse(businessAuthInfo);
	        var businessFormId = businessAuthInfoJson.businessFormId;
            var businessRuleId = businessAuthInfoJson.businessRuleId;
            var businessAuthId = businessAuthInfoJson.businessAuthId;
            if(businessFormId != undefined){
	            result += businessFormId;
            }
            if(businessRuleId != undefined){
	            result += businessRuleId;
            }
            if(businessAuthId != undefined){
	            result += businessAuthId;
            }

         }
    }
    return result;
}
/**
 * 获取流程公式中出现过的字段
 *fieldList:表单字段集合
 *formula:公式
 */
FlowExample.obtainFormFieldList = function (fieldList, formula) {
	 var result = new Array();
	    for(var f = 0; f< fieldList.length; f++){
	        if (formula.indexOf(fieldList[f].name) >= 0) {
	            //过滤掉明细表类型
	            if(fieldList[f].controlType!="detailsTable"){
	                //判断字段是否在公式中出现过，如果出现过则加入到集合中
	                result.push(fieldList[f]);
	            }
	        }
	    }
    return result;
}
/**
*生成标题HTML
*classTitle:类型标题
*column1:列标题1
*column2:列标题2
*/
FlowExample.getTitleHtml = function (classTitle, column1, column2) {
    var result = "<tr class=\"tr_normal_title\">\
								   <td colspan=\"2\" style=\"text-align: center\"><strong>"+ classTitle + "</strong></td>\
			                       </tr>\
			                       <tr class=\"tr_normal_title\">\
				                   <td>"+ column1 + "</td>\
				                   <td>"+ column2 + "</td>\
			                       </tr>";

    return result;
}
/**
*生成数据为空时的HTML
*isNullText:需要显示的文本
*/
FlowExample.getIsNullHtml = function (isNullText) {
    var vIsNullHtml = "<tr>\
					<td colspan=\"2\" style=\"text-align: center;\"><strong>"+ isNullText + "</strong></td>\
					</tr>";
    return vIsNullHtml;
}
/**
*将表单字段解析成HTML代码validate=\"required\"
*formField：表单字段对象
*/
FlowExample.getFieldTrHtml = function (formField) {
    var result = "";
    result += "<tr>";
    result += "<td style=\"text-align: center;\">" + formField.label + "</td>";
    result += "<td><input data-from=\"example\" data-id=\"" + formField.name + "\" data-type=\"text\" type=\"text\" class=\"inputsgl\" style=\"width:90%;\" id=\"" + formField.name + "\"></td>";//<span class=\"txtstrong\">*</span> validate=\"required\"
    result += "</tr>";
    return result;
}
/**
*解析地址本控件字段
*formField:待解析字段
*selectType：是否多选
*/
FlowExample.getDetail = function (formField, selectType) {
    var vSelectType = selectType == true ? "true" : "false";//是否多选
    var result = "";
    result += "<tr>";
    result += "<td style=\"text-align: center;\">" + formField.label + "</td>";
    result += "<td>";
    result += "<div id=\"auditshow_mouldDetail_html_" + formField.name + "\">";
    result += "<input type=\"text\" id=\"address_name_" + formField.name + "\" name=\"address_name_" + formField.name + "\" readonly=\"true\" class=\"inputsgl\" value=\"\" data-from=\"example\" data-id=\"address_name_" + formField.name + "\" data-type=\"SysOrgPerson_Name\">";
    result += "<input type=\"hidden\" id=\"address_value_" + formField.name + "\" name=\"address_value_" + formField.name + "\" value=\"\" data-from=\"example\" data-id=\"address_value_" + formField.name + "\" data-type=\"SysOrgPerson_Value\">";
    result += "<a href=\"#\" id=\"handlerSelect\" onclick=\"Dialog_Address(" + vSelectType + ", 'address_value_" + formField.name + "','address_name_" + formField.name + "', ';',ORG_TYPE_ALL);\">"+FlowSimulationLang.select+"</a>";// <span class=\"txtstrong\">*</span>
    result += "</div>";
    result += "</td>";
    result += "</tr>";
    return result;
}
/**
*将表单字段集合解析成HTML显示到table中
*fieldList：表单字段集合
*tbl：在该对象中输出显示
*/
FlowExample.addFormField = function (fieldList, tbl) {
    tbl.append(this.getTitleHtml(FlowExampleLang.formField, FlowExampleLang.fieldName, FlowExampleLang.fieldValue));
    if (fieldList.length > 0) {
        for (f in fieldList) {
            var vFieldType = fieldList[f].type;
            if (vFieldType.indexOf("com.landray.kmss.sys.organization") >= 0) {
                var vSelectType = false;//是否多选
                if (vFieldType.indexOf("[]") >= 0) {
                    vSelectType = true;
                }
                //地址本类型控件解析
                tbl.append(this.getDetail(fieldList[f], vSelectType));
            }
            else {
                tbl.append(this.getFieldTrHtml(fieldList[f]));
            }
        }
    } else {
        tbl.append(this.getIsNullHtml(FlowExampleLang.fieldError));
    }
}
/**
*获取需要解析的节点
*nodes：审批节点集合
*/
FlowExample.getStayHandleNodes = function (nodes) {
    var result = new Array();
    for (n in nodes) {
        //筛选出流程节点类型为人工决策的节点
        if (nodes[n].Type == "manualBranchNode") {
            result.push(nodes[n]);
        }
    }
    return result;
}
/**
*获取节点流出线下所有的节点
*node：节点对象
*/
FlowExample.getLineOutNode = function (node) {
    var result = new Array();
    for (l in node.LineOut) {
        result.push(node.LineOut[l].EndNode);
    }
    return result;
}
/**
*将节点对象解析成HTML
*node：节点对象
*/
FlowExample.getNodeToHtml = function (node) {
    var result = "";
    var nodeDefaultBranch = null;
    if (node.Data.defaultBranch != undefined) {
        //判断节点是否有默认审批节点
        nodeDefaultBranch = this.nodeSimulation.getEndNodeByLineId(node, node.Data.defaultBranch);
    }
    result += "<tr>";
    result += "<td style=\"text-align: center;\">" + node.Data.id + "." + node.Data.name + "</td>";
    result += "<td>";
    result += "<select id=\"" + node.Data.id + "\" style=\"width:90%;\" data-from=\"example\" data-id=\"" + node.Data.id + "\" data-type=\"select\">";// 验证代码：validate=\"required\"
    if (nodeDefaultBranch != null) {
        result += "<option value=\"\">" + FlowExampleLang.select + "</option>";
    }
    else {
        result += "<option value=\"\" selected=\"selected\">" + FlowExampleLang.select + "</option>";
    }
    for (n in node.LineOut) {
        result += "<option value=\"" + node.LineOut[n].EndNode.Data.id + "\"";
        if (nodeDefaultBranch != null) {
            //设置了默认流向时，生成时选中默认流向的选项
            if (node.LineOut[n].EndNode.Data.id == nodeDefaultBranch.Data.id) {
                result += " selected=\"selected\" ";
            }
        }
        result += ">";
        result += node.LineOut[n].EndNode.Data.id + "." + node.LineOut[n].EndNode.Data.name;
        result += "</option>";
    }
    result += "</select>";//</select><span class=\"txtstrong\">*</span>
    result += "</td>";
    result += "</tr>";
    return result;

}
/**
*将需要在流程设置中进行配置的节点解析成HTML并输出
*nodes:节点集合
*tbl：在该对象中输出显示
*/
FlowExample.getNodesToHtml = function (nodes, tbl) {
    var nodeTemp = new Array();
    tbl.append(this.getTitleHtml(FlowExampleLang.nodeSeting, FlowExampleLang.nodeName, FlowExampleLang.nodeAction));
    nodeTemp = this.getStayHandleNodes(nodes);//获取需要处理的节点
    if (nodeTemp.length > 0) {
        for (n in nodeTemp) {
            tbl.append(this.getNodeToHtml(nodeTemp[n]));
        }
    } else {
        tbl.append(this.getIsNullHtml(FlowExampleLang.nodeMessage));
    }
}
/**
*设置流程实例表格内容，将解析的节点内容显示在表格中
*/
FlowExample.setProcessSimulation = function () {
    var vProcessSimulation = $("#tdProcessSimulation");//document.getElementById("tdProcessSimulation");
    this.addFormField(vFieldList, vProcessSimulation);//解析并加载表单字段
    this.getNodesToHtml(vNodes, vProcessSimulation);//解析并加载需要显示的节点设置
}
function savecheck(){
	var result=false;
	var vDetail_attr_name=$("#detail_attr_name").val();
	var draftmanType=$("#draftmanType").val();
	var sDraftmanRule=$("#sDraftmanRule").val();
	var address_name_scope_draftman=$("#address_name_scope_draftman").val();
	var sysOrgElement_draftman_num=$("#sysOrgElement_draftman_num").val();
	if(draftmanType=="0"&&vDetail_attr_name!=""){
		result=true;
	}
	else if(draftmanType=="1"&&sDraftmanRule!=""&&address_name_scope_draftman!=""){
		result=true;
	}
	else{
		result=false
	}
	return result;
	
}

/**
 * 保存实例
 */
FlowExample.save = function () {
	var model = {};
	model.fdModelId = Com_GetUrlParameter(window.location.href, "fdId");
	model.fdModelName = flowChartObject.ModelName;
	model.fdTemplateModelName = flowChartObject.TemplateModelName
	model.fdDraftmanType=$("#draftmanType").val();
	var vDetail_attr_name=$("#detail_attr_name").val();
	//判断是否选择了起草人
	if(savecheck()==true&&model.fdModelId!=""){
		//打开实例标题页面，实例标题填写后进入数据的保存逻辑
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			dialog.iframe("/sys/lbpm/flowchart/page/simulation_Example_Title.jsp",FlowSimulationLang.title,function(value){
				model.fdTitle=value;
				if(model.fdTitle!=undefined&&model.fdTitle!=""){
					
				    var example =getAttributeAll();//获取页面中所有实例的参数数据
				    model.fdExampleData = JSON.stringify(example);
				    var data={"data":JSON.stringify(model)};
				    var request=reqService(data,"saveExample");//发送保存请求
				    if(request.type=="ok"){
				    	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				    		dialog.success(FlowSimulationLang.message_f,"","","","",{autoCloseTimeout:2,topWin:window});
				    		});
				    	//数据保存成功后刷新实例列表
				    	FlowExample.loadExampleList(Com_GetUrlParameter(window.location.href,"fdId"),$("#dataList_body"));
				    }
				    else{
				    	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				    		dialog.failure(FlowSimulationLang.message_g,"","","","",{autoCloseTimeout:2,topWin:window});
				    		});
				    }
				}
			},{width:330,height:130,params:"hello"});//,topWin:window
		});  
	}
	else{
		
		if(model.fdModelId==""){
			alert(FlowSimulationLang.message_i);
		}else{
			alert("请填写带*号的必填项");
		}		
		
	}
	
}
/**
 * 加载实例列表
 */
FlowExample.loadExampleList=function(modelId,tab){
	var data=queryExampleList(modelId);
	if(data.type=="ok"){
		var dataList=data.data;
		if(dataList!=undefined&&dataList!=null&&dataList!=""&&dataList.length>0){
			tab.html("");
			for(i in dataList){
				tab.append(getExampleRow(dataList[i]));
				//把数据放到数据行的隐藏域中
				$("#"+dataList[i].fdId).val(JSON.stringify(dataList[i]));
			}
		}
		else{
			tab.html("<tr><td colspan=\"3\">"+FlowSimulationLang.list_noData+"</td></tr>");
		}
		
	}	
}
/**
 * 根据实例ID显示流程实例
 */
FlowExample.showExample=function(id){
	this.dataBindById(id);
	//跳转到流程仿真页签
	LUI("tabpanel").setSelectedIndex(0);
}
/**
 * 通过实例ID绑定实例数到页面中
 */
FlowExample.dataBindById=function (id){
	var model=eval('(' + $("#"+id).val() + ')');
	var dataList=eval('(' + model.fdExampleData + ')');
	var draftmanType=$("#draftmanType");
	if(model.fdDraftmanType&&model.fdDraftmanType!=""){
		draftmanType.val(model.fdDraftmanType);
	}
	else{
		draftmanType.val(0);
	}
	draftmanType.change();
	FlowExample.dataBind(dataList.exampleData);
	
}
/**
 * 将实例数据绑定到页面中
 */
FlowExample.dataBind=function(dataList){
	var vError=0;
	for(var i=0;i<dataList.length;i++){
		var vControl=$("#"+dataList[i].dataId);
		if(vControl!=undefined&&vControl!=null){
			if(dataList[i].dataType=="select"){
				if(dataList[i].dataId=="sFdHandlerRoleInfoIds"){
					//当前为加载起草人身份时，加载起草人身份数据和界面
					FlowExample.loadFdHandlerRoleInfoIds();
				}
				vControl.val(dataList[i].dataValue);
			}
			else{
				vControl.val(dataList[i].dataValue);
			}
		}
		else{
			vError+=1;
		}
	}
	if(vError>0){
		alert(FlowSimulationLang.message_h);
	}	
}
/**
 * 删除选中实例
 */
FlowExample.deleteExample=function(){
	var vCheckExample=$("[name='checkExample']:checked");
	var ids="";
	if(vCheckExample.length>0){
		for(var i=0;i<vCheckExample.length;i++){
			if(ids==""){
				ids+=vCheckExample[i].value;
			}
			else{
				ids+=","+vCheckExample[i].value;
			}
		}
	}
	else{
		alert(FlowSimulationLang.message_e);
	}
	if(ids!=""){
		var data={"data":JSON.stringify({"fdIds":ids})};
		var result=reqService(data,"deleteExample");
		if(result.type=="ok"){
			alert("删除成功！");
			//数据删除成功后刷新实例列表
	    	FlowExample.loadExampleList(Com_GetUrlParameter(window.location.href,"fdId"),$("#dataList_body"));
		}
		else{
			alert("删除失败！");
		}
	}
}
/**
 * 构建数据行
 * @param data
 * @returns
 */
function getExampleRow(data){
	var result="";
	result+="<tr>";
	result+="<td style=\"text-align: left;\">";
	var checkboxType="";
	if(data.fdDraftmanType=="1"){
		//checkboxType="disabled=\"disabled\"";
	}
	result+="<input name=\"checkExample\" data-fddraftmanType=\""+data.fdDraftmanType+"\" type=\"checkbox\" value=\""+data.fdId+"\" "+checkboxType+">";
	result+="</td>";
	result+="<td style=\"text-align: center;\"><a href=\"javascript:void(0);\" onclick=\"FlowExample.showExample('"+data.fdId+"');\">"+data.fdTitle+"</a><input type=\"hidden\" id=\""+data.fdId+"\" value=\"\"></td>";
	//result+="<td style=\"padding:0px;\"><div class=\"divTag\" name=\"divTestType\" id=\""+data.fdId+"_type\"></div></td>";
	result+="</tr>";
	return result;
}
/**
 * 查询实例数据
 * @param modelId 模板id
 * @returns 返回json数据
 */
function queryExampleList(modelId){
	var data={"modelId":modelId};
	var result=	reqService(data,"queryExampleList");
	return result;
}
/**
 * ajax请求实例处理服务
 * @param data 数据
 * @param method 服务名
 * @returns 返回json数据结果
 */
function reqService(data,method){
	var result=RequestUtil.postRequest(data,"sys/lbpmservice/support/lbpm_simulation_exampleService/lbpmSimulationExampleService.do?method="+method);
	return result;
}
/**
 * 获取实例面板中所有的设置参数
 * @returns
 */
function getAttributeAll(){
	var ExampleData = new Array();
    var exampleDom = $("[data-from='example']");
    for (var e = 0; e < exampleDom.length; e++) {
        var domData = {};
        domData.dataId = $(exampleDom[e]).attr("data-id");
        domData.dataType = $(exampleDom[e]).attr("data-type");
        if(domData.dataType=="select"){
        	domData.dataText = $(exampleDom[e]).find("option:selected").text();
            domData.dataValue = $(exampleDom[e]).val();
        }
        else{
        	domData.dataText = $(exampleDom[e]).val();
            domData.dataValue = $(exampleDom[e]).val();
        }        
        ExampleData.push(domData);
    }
    var example = { "exampleData": ExampleData };
    return example;   
}

FlowExample.draftmanTypeChange=function(obj){
	var selectValue=$(obj).val();
	//选择指定起草人时显示指定起草人相关的控件
	if(selectValue==="0"){
		$("[data-draftmanType='1']").hide();
		$("[data-draftmanType='0']").show();
		//起草人为空时，隐藏身份控件
		if($("#detail_attr_name").val()==""){
			$("#trFdHandlerRoleInfoIds").hide();
		}				
	}
	if(selectValue=="1"){
		$("[data-draftmanType='0']").hide();
		$("[data-draftmanType='1']").show();
	}
	
}