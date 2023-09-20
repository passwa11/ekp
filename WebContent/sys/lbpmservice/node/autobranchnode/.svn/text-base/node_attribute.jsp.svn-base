<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td colspan="2">
						<table class="tb_normal" id="lineTable">
							<tr class="tr_normal_title">
								<td style="width:94px"><kmss:message key="FlowChartObject.Lang.Line.nextNode" bundle="sys-lbpm-engine" /></td>
								<td style="width:135px"><kmss:message key="FlowChartObject.Lang.Line.name" bundle="sys-lbpm-engine" /></td>
								<td style="width:330px"><kmss:message key="FlowChartObject.Lang.Line.condition" bundle="sys-lbpm-engine" /></td>
							</tr>
							<tr KMSS_IsReferRow="1" style="display:none">
								<td class="autoWrap"></td>
								<td>
									<%-- <input name="lineName" class="inputsgl">
									<xlang:lbpmlang property="lineName" style="width:100%" langs=""/> --%>
									<c:if test="${!isLangSuportEnabled }">
										<input name="lineName" class="inputsgl">
									</c:if>
									<c:if test="${isLangSuportEnabled }">
										<xlang:lbpmlangNew property="lineName" style="width:100%" className="inputsgl" langs=""/>
									</c:if>
									<input name="nextNodeName" type="hidden">
								</td>
								<td>
									<div class="originalMode">
										<input type="hidden" name="lineCondition">
										<input name="lineDisCondition" class="inputsgl" readonly style="width:100%"><br>
										<a href="#" onclick="openExpressionEditor();"><kmss:message key="FlowChartObject.Lang.Line.formula" bundle="sys-lbpm-engine" /></a>
										<span style="width:180px"></span>
										<a href="#" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
										<a href="#" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
									</div>
									<div class="newMode">
										<label><input type="radio" value="rule" name="lineMode[!{index}]" onclick="selectMode(this,'rule','lineCondition')"><kmss:message key="FlowChartObject.Lang.Node.selectRule" bundle="sys-lbpmservice" /></label>
										<label><input type="radio" value="formula" name="lineMode[!{index}]" checked="true" onclick="selectMode(this,'formula','lineCondition')">公式定义器</label>
										<%-- <a href="#" onclick="openExpressionEditor();"><kmss:message key="FlowChartObject.Lang.Line.formula" bundle="sys-lbpm-engine" /></a> --%>
										<span style="width:180px"></span>
										<a href="#" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
										<a href="#" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a><br/>
										<input type="hidden" name="lineCondition">
										<input name="lineDisCondition" class="inputsgl condition" readonly style="width:80%">
										<a class="formula" href="#" onclick="openExpressionEditor();">选择</a>
										<c:import url="/sys/rule/sys_ruleset_quote/sysRuleQuote.jsp" charEncoding="UTF-8">
											<c:param name="type" value="autobranchnode"></c:param>
											<c:param name="returnType" value="Boolean"></c:param>
											<c:param name="mode" value="rule"></c:param>
											<c:param name="key" value="lineCondition"></c:param>
										</c:import>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Line.tip" bundle="sys-lbpm-engine" /></td>
					<td width="490px">
						<kmss:message key="FlowChartObject.Lang.Line.note" bundle="sys-lbpm-engine" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Event" bundle="sys-lbpm-engine" />">
		<td>
		<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
</table>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/common.js"/>'></script>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/rule_quote.js"/>'></script>
<script>
DocList_Info.push("lineTable");

function _appendLangsValue(obj,data){
	var langs = data["langs"];
	var lineName =  data["name"]||"";
	if(typeof langs !="undefined"){
		var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
		if(isEdit){
			lineName = _getLangLabel(lineName,langs,langJson["official"]["value"]);
		}else{
			lineName = _getLangLabel(lineName,langs,userLang);
		}
	}
	obj["name"]=lineName||"";
	for(var j=0;j<langJson["support"].length;j++){
		var name ="lineName_"+langJson["support"][j]["value"];
		var val = _getLangLabel("",langs,langJson["support"][j]["value"]);
		obj[name]=val||"";
	}
}

AttributeObject.Init.AllModeFuns.push(function() {
	var NodeData = AttributeObject.NodeData;
	var LineOut = AttributeObject.NodeObject.LineOut;
	NodeData.XMLNODENAME = "autoBranchNode";
	function getSortData(){
		var o1=[],o2=[];
		if(LineOut!=null && LineOut.length>0){
			LineOut = LineOut.sort(FlowChartObject.LinesSort);
			for(var i=0;i<LineOut.length;i++){
				var nn = LineOut[i].EndNode.Data.id + "." + LineOut[i].EndNode.Data.name;
				var o = {nextNodeName:nn, name : LineOut[i].Data["name"]||"",
						condition:LineOut[i].Data["condition"]||"",disCondition:LineOut[i].Data["disCondition"]||"",
						priority:LineOut[i].Data["priority"]||""
					};
					//补多语言
					_appendLangsValue(o,LineOut[i].Data);
				if(o["priority"]==""){
					o2[o2.length] = o;
				}else{
					o1[o["priority"]] = o;
				}
			}
		}
		o1 = o1.concat(o2);
		return o1;
	}

	var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
	var a = getSortData();
	if(a!=null && a.length>0){
		var arrIndex = -1;
		for(var i=0;i<a.length;i++){
			if(!a[i]){
				continue;
			}else{
				arrIndex++;
			}
			var rd = {lineName : a[i]["name"]||"",nextNodeName : a[i]["nextNodeName"],
				lineCondition : a[i]["condition"]||"", lineDisCondition : a[i]["disCondition"]||""};
			
			//补多语言
			for(var j=0;j<langJson["support"].length;j++){
				var name ="lineName_"+langJson["support"][j]["value"];
				rd[name]=a[i][name]||"";
			}

			var row = DocList_AddRow("lineTable",[a[i]["nextNodeName"]],rd);
			row.style.verticalAlign="top";
			$(row).find("td.autoWrap").css({
				"word-wrap":"break-word",
				"max-width":"100px"
			});
			var isRule = false;
			try{
				var condition = eval('('+a[i].condition+')');
				if(condition.type && condition.type == 'rule'){
					isRule = true;
				}
			}catch(e){
			}
			var isShow = false;
			if(FlowChartObject.SysRuleTemplate || isRule){
				isShow = true;
			}else{
				if(FlowChartObject && FlowChartObject.ModelId){
					var modelId = FlowChartObject.ModelId;
					//请求后台来确认是否显示（主要解决流程文档页面节点看不到选项的问题）
					$.ajax({
						  url: Com_Parameter.ContextPath+"sys/lbpm/engine/jsonp.jsp?s_bean=lbpmRuleHandlerService",
						  type:'GET',
						  async:false,//同步请求
						  data:{modelId: modelId},
						  success: function(json){
							  var data = eval('('+json+')');
							  if(data.isShow){
								 isShow = true;
							  }	
						  }
					});
				}
			}
			if(isShow){//初始化规则机制对象
				if(!window.sysRuleQuote){
					window.sysRuleQuote = window.SysRuleQuote("lineCondition","lineDisCondition","lineCondition",FlowChartObject.SysRuleTemplate,FlowChartObject.LbpmTemplateKey);
				}
				$(".originalMode").remove();
				if(isRule){
					var index;
					if(isEdit){
						index = window.sysRuleQuote.initRuleQuote(a[i].condition,arrIndex,'lineCondition','lineDisCondition','edit');
					}else{
						index = window.sysRuleQuote.initRuleQuote(a[i].condition,arrIndex,'lineCondition','lineDisCondition','view');
					}
					if(index != -1){
						$(".formula").eq(arrIndex).hide();
						$(".condition").eq(arrIndex).hide();
						$(".rule.lineCondition").eq(arrIndex).show();
						$("input[name='lineMode["+arrIndex+"]'][value='rule']").attr("checked",true);
					}
				}
			}else{
				//不存在机制内容，隐藏入口
				$(".newMode").remove();
			}
		}
	}

	//var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
	if(!isEdit){
		if(a!=null && a.length>0){
			for(var i=0;i<a.length;i++){
				if(!a[i]){
					continue;
				}
				if(document.getElementsByName("lineName_span")[i]){
					document.getElementsByName("lineName_span")[i].style.display="none";
				}
			}
		}
	}
});

function openExpressionEditor() {
	var index = getElementIndex();
	Formula_Dialog(document.getElementsByName("lineCondition")[index-1],
			document.getElementsByName("lineDisCondition")[index-1],
			FlowChartObject.FormFieldList,
			"Boolean",
			null,
			"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
			FlowChartObject.ModelName);
}

function getElementIndex(){
	var row = DocListFunc_GetParentByTagName("TR");
	var table = DocListFunc_GetParentByTagName("TABLE", row);
	for(var i=1; i<table.rows.length; i++){
		if(table.rows[i]==row){
			return i;
		}
	}
	return -1;
}

function nodeDataCheck(data){
	var hasEmpty = false;
	var LineOut = AttributeObject.NodeObject.LineOut;
	if(LineOut!=null && LineOut.length>0){
		for(var i=0;i<LineOut.length;i++){
			var nn = LineOut[i].EndNode.Data.id + "." + LineOut[i].EndNode.Data.name;
			//var nextNodeName = LineOut[i].EndNode.Data.name;
			var o = getLineValue(nn);
			if(o==null){
				return true;
			}
			LineOut[i].Data["name"] = o["name"];
			LineOut[i].Data["condition"] = o["condition"];
			LineOut[i].Data["disCondition"] = o["disCondition"];
			LineOut[i].Data["priority"] = o["priority"];
			LineOut[i].Data["langs"] = o["langs"];
			//添加引用规则的内容
			//LineOut[i].Data["ruleMapping"] = o["ruleMapping"];
			
			if (o["condition"] == null || Com_Trim(o["condition"]) == '') {
				hasEmpty = true;
			}
		}
	}
	if (hasEmpty) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkConditionHasEmpty" bundle="sys-lbpmservice-node-autobranchnode" />');
		return false;
	}

	return true;
}

AttributeObject.CheckDataFuns.push(nodeDataCheck);

function getLineValue(nextNodeName){
	var lineName = document.getElementsByName("lineName");
	var lineCondition = document.getElementsByName("lineCondition");
	var lineDisCondition = document.getElementsByName("lineDisCondition");
	var nextNodeNames = document.getElementsByName("nextNodeName");
	//添加保存规则的属性
	//var mappingContent = document.getElementsByName("mappingContent");
	for(var i=0;i<nextNodeNames.length;i++){
		if(nextNodeNames[i].value==nextNodeName){
			//var o = {name:lineName[i].value,condition:lineCondition[i].value,disCondition:lineDisCondition[i].value,priority:""+i,ruleMapping:mappingContent[i].value};
			var o = {name:lineName[i].value,condition:lineCondition[i].value,disCondition:lineDisCondition[i].value,priority:""+i};
			var langs = _getLangByElName("lineName",i,"");
			o["langs"] =  JSON.stringify(langs);
			return o;
		}
	}
	return null;
}

AttributeObject.SubmitFuns.push(AttributeObject.Utils.refreshLineOut);
/*规则引擎*/
//选择模式
function selectMode(obj,label){
	var index = getIndex()-1;
	if(label == 'formula'){
		$(".formula").eq(index).show();
		$(".condition").eq(index).show();
		$(".rule.lineCondition").eq(index).hide();
		//记录需要该引用id
		window.sysRuleQuote.recordDelMapContentIds(index);
		//清空规则信息
		$(".rule.lineCondition").eq(index).find("[name='ruleId']").eq(0).val("");
		$(".rule.lineCondition").eq(index).find("[name='ruleName']").eq(0).val("");
		
		$(".rule.lineCondition").eq(index).find("[name='mapContent']").eq(0).val("");
		$(".rule.lineCondition").eq(index).find("[name='alreadyMapId']").eq(0).val("");
		$(".rule.lineCondition").eq(index).find("[name='alreadyMapName']").eq(0).val("");
	}else{
		$(".formula").eq(index).hide();
		$(".condition").eq(index).hide();
		$(".rule.lineCondition").eq(index).show();
		
		$(".rule.lineCondition").eq(index).find(".alreadyMapType").eq(0).hide();
		$(".rule.lineCondition").eq(index).find(".mapArea").eq(0).hide();
		
	}
	$("[name='lineCondition']").eq(index).val("");
	$("[name='lineDisCondition']").eq(index).val("");
}
//校验
function checkRuleData(data){
	if(window.sysRuleQuote){
		return window.sysRuleQuote.checkData();
	}
	return true;
}
AttributeObject.CheckDataFuns.push(checkRuleData);

function writeRuleMapData(){
	if(window.sysRuleQuote){
		window.sysRuleQuote.writeData("lineMode");
	}
}
AttributeObject.SubmitFuns.push(writeRuleMapData);
//选择
function selectRule(returnType,mode,key){
	if(key == 'lineCondition'){
		window.sysRuleQuote.selectRule(returnType,mode);
	}
}
//更新映射
function updateMapContent(value, obj,key){
	if(key == 'lineCondition'){
		window.sysRuleQuote.updateMapContent(value, obj);
	}
}
/*规则引擎*/
</script>