<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html>

<script type="text/javascript">
<%
	String KMSS_Parameter_Style = request.getParameter("s_css");
	if(KMSS_Parameter_Style==null || KMSS_Parameter_Style.equals("")){
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0)
			for (int i = 0; i < cookies.length; i++)
				if ("KMSS_Style".equals(cookies[i].getName())) {
					KMSS_Parameter_Style = cookies[i].getValue();
					break;
				}
	}
	if(KMSS_Parameter_Style==null || KMSS_Parameter_Style.equals(""))
		KMSS_Parameter_Style="default";
	pageContext.setAttribute("KMSS_Parameter_Style", KMSS_Parameter_Style);
	String KMSS_Parameter_ContextPath = request.getContextPath()+"/";
	pageContext.setAttribute("KMSS_Parameter_ContextPath", KMSS_Parameter_ContextPath);
	String KMSS_Parameter_ResPath = KMSS_Parameter_ContextPath+"resource/";
	pageContext.setAttribute("KMSS_Parameter_ResPath", KMSS_Parameter_ResPath);
	String KMSS_Parameter_StylePath = KMSS_Parameter_ResPath + "style/"+KMSS_Parameter_Style+"/";
	pageContext.setAttribute("KMSS_Parameter_StylePath", KMSS_Parameter_StylePath);
%>
var lbpm = new Object();
lbpm.globals = new Object();
var Com_Parameter = {
	ContextPath:"${KMSS_Parameter_ContextPath}",
	ResPath:"${KMSS_Parameter_ResPath}",
	Style:"${KMSS_Parameter_Style}",
	JsFileList:new Array,
	StylePath:"${KMSS_Parameter_StylePath}",
	CurrentUserId:"${KMSS_Parameter_CurrentUserId}"
}; 
</script>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js"></script>
<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script>
Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">

	
	function WorkFlow_PassValue(){
		if(nodeId != null && nodeId != ''){
			document.getElementsByName("nodeId")[0].value=nodeId;
		}
		if(handlerIdentity != null && handlerIdentity != ''){
			document.getElementsByName("handlerIdentity")[0].value=handlerIdentity;
		}
	}
	
	$(function(){
		var initDialog = setInterval(function(){
			if(typeof $dialog != "undefined"){//最多执行15次
				clearInterval(initDialog);
				dialogObject = $dialog;
				win = dialogObject.___params.win ;
				lbpm = win.lbpm;
				lbpm.globals.setFurtureHandlerInfoes1 = WorkFlow_SetFurtureHandlerInfoes1;
				nodeId = '${JsParam.nodeId}';
				handlerIdentity = '${JsParam.handlerIdentity}';
				switchFlag = '${JsParam.switchFlag}';
				WorkFlow_InitialWindow(switchFlag);
			}
		}, 100);
	});
	
	// 刷新当前值 limh 2011年3月31日
	function WorkFlow_SetFurtureHandlerInfoes1(rtv,isFormula,isMatrix,isRule){
		var id = _now_nodeId;
		var handlerIdsObj = document.getElementsByName("handlerIds_" + id)[0];
		var handlerNamesObj = document.getElementsByName("handlerNames_" + id)[0];
		if(rtv){
			if(rtv.GetHashMapArray()){
			  handlerIdsObj.setAttribute("isFormula",isFormula);
			  handlerIdsObj.setAttribute("isMatrix",(isMatrix==null?"false":isMatrix));
			  handlerIdsObj.setAttribute("isRule",(isRule==null?"false":isRule));
			  var ids="";
			  var names="";
			  for(var i=0;i<rtv.GetHashMapArray().length;i++){
				  ids+=rtv.GetHashMapArray()[i].id;
				  names+=rtv.GetHashMapArray()[i].name;
				  if(i!=rtv.GetHashMapArray().length-1){
					  ids+=";";
					  names+=";";
				  }
			  }
			  handlerIdsObj.value = ids;
			  handlerNamesObj.value = names;
			}
			_now_nodeId = null;
		}
	}

	function WorkFlow_InitialWindow(switchFlag){
		var throughtNodes = dialogObject.___params.throughtNodes;
		//流程经过的节点id串 如“N1,N2,N5” @作者：曹映辉 @日期：2011年10月28日 
			
			var throughNodesStr = lbpm.globals.getIdsByNodes(throughtNodes);
			
			if(switchFlag=="all"){
				throughNodesStr = lbpm.globals.getIdsByNodes(WorkFlow_GetAllNodeArray(win));
			}
			var workflowNodeTB = document.getElementById("workflowNodeTB");
			$(workflowNodeTB).find("tr:gt(0)").empty();
			var currentNode = lbpm.globals.getNodeObj(nodeId);
			var mustNodes="";
			if(currentNode.mustModifyHandlerNodeIds){
				mustNodes=currentNode.mustModifyHandlerNodeIds;
			}
			var nextNodeArray = WorkFlow_GetAllNodeArray(win);
			for(var i = 0; i < nextNodeArray.length; i++){
				
				if(switchFlag!="all"){
					if(!(lbpm.globals.judgeIsNecessaryAlert(nextNodeArray[i]))) continue ;
				}

				var nextNodeId = nextNodeArray[i].id;
				if(lbpm_checkModifyNodeAuthorization(currentNode, nextNodeArray[i].id,throughNodesStr) && nextNodeArray[i].XMLNODENAME!="embeddedSubFlowNode"){
					var handlerIdentity = (function() {
						if (nextNodeArray[i].optHandlerCalType == null || nextNodeArray[i].optHandlerCalType == '2') {
							var rolesSelectObj = win.document.getElementsByName("rolesSelectObj")[0];
						 	if(rolesSelectObj != null && rolesSelectObj.selectedIndex > -1){
						 		return rolesSelectObj.options[rolesSelectObj.selectedIndex].value;
						 	}
							return win.document.getElementsByName("sysWfBusinessForm.fdIdentityId")[0].value;
						}
						var handlerIdentityIdsObj = win.document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
				 		var rolesIdsArray = handlerIdentityIdsObj.value.split(";");
				 		return rolesIdsArray[0];
					})();
					var tr = document.createElement("tr");
					var td1 = document.createElement("td");
					var langNodeName = WorkFlow_getLangLabel(nextNodeArray[i].name,nextNodeArray[i]["langs"],"nodeName");
					//必填节点增加*号标识 作者 曹映辉 #日期 2017年5月9日
					if((mustNodes+";").indexOf(nextNodeArray[i].id+";")>=0){
						//td1.innerHTML = "<center><span class='txtstrong'>*</span>&nbsp;&nbsp;" + nextNodeArray[i].id + "." + langNodeName + "</center>";
						td1.innerHTML = "<span class='txtstrong'>*</span>&nbsp;&nbsp;" + nextNodeArray[i].id + "." + langNodeName + "";
					}
					else{
						//td1.innerHTML = "<center>" + nextNodeArray[i].id + "." + langNodeName + "</center>"; //j
						td1.innerHTML = "" + nextNodeArray[i].id + "." + langNodeName + "";
					}
					//td1.style.cssText="td_normal_title"
					tr.appendChild(td1);
					var handlerIds, handlerNames, isFormulaType = (nextNodeArray[i].handlerSelectType == 'formula'), isMatrixType = (nextNodeArray[i].handlerSelectType == 'matrix'), isRuleType = (nextNodeArray[i].handlerSelectType == 'rule');
					handlerIds = (nextNodeArray[i].handlerIds == null) ? "" : nextNodeArray[i].handlerIds;
					handlerNames = (nextNodeArray[i].handlerNames == null) ? "" : nextNodeArray[i].handlerNames;
					
					// 是矩阵组织时特殊处理处理人以及id
					if(isMatrixType){
					      var dataNextNodeHandler;
					      var nextNodeHandlerNames4View="";
					      var nextNodeHandlerIds4View="";
					      dataNextNodeHandler=lbpm.globals.matrixNextNodeHandlerNameAndId(handlerIds,true,lbpm.globals.checkNodeType(lbpm.constant.NODETYPE_SEND,nextNodeArray[i]),null,nextNodeArray[i].id);
					      for(var j=0;j<dataNextNodeHandler.length;j++){
					          if(nextNodeHandlerNames4View==""){
					            nextNodeHandlerNames4View=dataNextNodeHandler[j].name;
					          }else{
					            nextNodeHandlerNames4View+=";"+dataNextNodeHandler[j].name;
					          }
					          if(nextNodeHandlerIds4View==""){
					        	  nextNodeHandlerIds4View=dataNextNodeHandler[j].id;
						          }else{
						        	  nextNodeHandlerIds4View+=";"+dataNextNodeHandler[j].id;
						          }
					        }
						handlerNames = (nextNodeHandlerNames4View == "") ? "" : nextNodeHandlerNames4View.replace(/;/g, ';');
						handlerIds =   (nextNodeHandlerIds4View == "") ? "" : nextNodeHandlerIds4View;
					}
					var html = '<input flowcontent="true" type="hidden" name="handlerIds_' + nextNodeId + '" value="' + Com_HtmlEscape(handlerIds) + '" ' + (isFormulaType ? 'isFormula="true"' : 'isFormula="false" ') + (isMatrixType ? 'isMatrix="true"' : 'isMatrix="false"') + (isRuleType ? 'isRule="true"' : 'isRule="false"') + '>';
					//增加处理人必填校验
					if ((mustNodes+";").indexOf(nextNodeArray[i].id+";")>=0){
						html += '<input flowcontent="true" subject=' + nextNodeArray[i].id + '.' + nextNodeArray[i].name + ' type="text" name="handlerNames_' + nextNodeId + '" value="' + Com_HtmlEscape(handlerNames) + '" readonly class="inputSgl" style="width:95%;" validate="required"><br>';
					}else{
						html += '<input flowcontent="true" type="text" name="handlerNames_' + nextNodeId + '" value="' + Com_HtmlEscape(handlerNames) + '" readonly class="inputSgl" style="width:95%;"><br>';
					}
					var optHandlerIds = nextNodeArray[i].optHandlerIds==null?"":nextNodeArray[i].optHandlerIds;
					var optHandlerSelectType = nextNodeArray[i].optHandlerSelectType == null?"org":nextNodeArray[i].optHandlerSelectType;
					var defaultOptionBean = "lbpmOptHandlerTreeService&optHandlerIds=" + encodeURIComponent(optHandlerIds) 
						+ "&currentIds=" + ((isFormulaType || isMatrixType || isRuleType) ? "" : encodeURIComponent(handlerIds)) 
						+ "&handlerIdentity=" + handlerIdentity
						+ "&optHandlerSelectType=" + optHandlerSelectType
						+ "&fdModelName=" + win.lbpm.modelName
						+ "&fdModelId=" + win.lbpm.modelId
						+ "&nodeId=" + nextNodeId;
					//增加搜索条 add by limh 2010年11月4日
					var searchBean = defaultOptionBean + "&keyword=!{keyword}";
					var hrefObj = "<a class=\"com_btn_link\" style=\"margin-left: 8px;\" href=\"javascript:void(0);\"";
					if (win.lbpm.constant.NODETYPE_SEND,nextNodeArray[i].nodeDescType=="shareReviewNodeDesc") {
						// 微审批节点处理人选择控制
						hrefObj+=" onclick=\"WorkFlow_ClearValueForFurtureHandler('"+nextNodeId+"','false');Dialog_AddressList(false, 'handlerIds_" + nextNodeId + "','handlerNames_" + nextNodeId + "', ';', '"+defaultOptionBean+"', function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,'false','false');}, '"+searchBean+"', null, null, '<bean:message key='lbpmNode.processingNode.changeProcessor.select' bundle='sys-lbpmservice'/>');\"";
					} else {
						hrefObj+=" onclick=\"WorkFlow_ClearValueForFurtureHandler('"+nextNodeId+"','false');Dialog_AddressList(true, 'handlerIds_" + nextNodeId + "','handlerNames_" + nextNodeId + "', ';', '"+defaultOptionBean+"', function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,'false','false');}, '"+searchBean+"', null, null, '<bean:message key='lbpmNode.processingNode.changeProcessor.select' bundle='sys-lbpmservice'/>');\"";
					}
					
					//如果选择的是本部门或者本机构,进行hrefObj的重构
					if(optHandlerSelectType == 'dept' || optHandlerSelectType == 'mechanism'){
						hrefObj = "<a class=\"com_btn_link\" style=\"margin-left: 8px;\" href=\"javascript:void(0);\"";
						var deptLimit;
						if(optHandlerSelectType == 'dept'){
							deptLimit = 'myDept';
						}else{
							deptLimit = 'myOrg';
						}
						var currentOrgIdsObj = win.document.getElementById("sysWfBusinessForm.fdHandlerRoleInfoIds");
						var reg = /;/g;
						var ids = currentOrgIdsObj ? currentOrgIdsObj.value.split(reg) : [];
						var exceptValue="";
						for(var j=0; j<ids.length; j++){
							if(j == ids.length-1){
								exceptValue += "'" + ids[j] + "'";
							}else{
								exceptValue += "'" + ids[j] + "',";
							}
						}
						var selectType = ORG_TYPE_POSTORPERSON;
						if (win.lbpm.constant.NODETYPE_SEND,nextNodeArray[i].nodeDescType=="shareReviewNodeDesc") {
							// 微审批节点处理人选择控制
							hrefObj+=" onclick=\"WorkFlow_ClearValueForFurtureHandler('"+nextNodeId+"','false');Dialog_Address(false,'handlerIds_" + nextNodeId + "','handlerNames_" + nextNodeId + "', ';', "+selectType+",function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,'false');}, null, null, true, null, null, ["+exceptValue+"], '"+deptLimit+"');\"";
						} else {
							hrefObj+=" onclick=\"WorkFlow_ClearValueForFurtureHandler('"+nextNodeId+"','false');Dialog_Address(true,'handlerIds_" + nextNodeId + "','handlerNames_" + nextNodeId + "', ';', "+selectType+",function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,'false');}, null, null, true, null, null, ["+exceptValue+"], '"+deptLimit+"');\"";
						}
					}
					
					hrefObj+=">" + '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.select.alternative" /></a>';
					if(nextNodeArray[i].useOptHandlerOnly == "true"){
						html += hrefObj;
					}else{ 
						var selectType = lbpm.constant.ADDRESS_SELECT_POSTPERSONROLE;
						if (lbpm.globals.checkNodeType(win.lbpm.constant.NODETYPE_SEND,nextNodeArray[i])) {
							selectType = lbpm.constant.ADDRESS_SELECT_ALLROLE;
						}
						if (lbpm.constant.NODETYPE_SEND,nextNodeArray[i].nodeDescType=="shareReviewNodeDesc") {
							// 微审批节点处理人选择控制
							selectType = "ORG_TYPE_PERSON";
							html += '<a class="com_btn_link" style="margin-left: 8px;" href="#" onclick="WorkFlow_ClearValueForFurtureHandler(\''+nextNodeId+'\',\'false\');Dialog_Address(false, \'handlerIds_' + nextNodeId + '\',\'handlerNames_' + nextNodeId + '\', \';\', '+selectType;
							html += ',function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,\'false\',\'false\');});"><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.select.organization" /></a>';
							var hrefObj_formula = "";
							if ((nextNodeArray[i].optHandlerIds && nextNodeArray[i].optHandlerIds.replace(/^\s+|\s+$/g, '') != "")  || optHandlerSelectType == 'dept' || optHandlerSelectType == 'mechanism'){
								html += hrefObj;
							}
							/* var hrefObj_formula = "<a class=\"com_btn_link\" style=\"margin-left: 8px;\" href=\"javascript:void(0)\"";
							hrefObj_formula +=" onclick=\"WorkFlow_ClearValueForFurtureHandler('"+nextNodeId+"','true');lbpm.globals.setHandlerFormulaDialog_('handlerIds_" + nextNodeId + "', 'handlerNames_" + nextNodeId + "', '${JsParam.fdModelName}',function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,'true','false');},true);\"";
							hrefObj_formula+=">" + "<bean:message bundle='sys-lbpmservice' key='lbpmSupport.selectFormList'/></a>"; */
							html += hrefObj_formula;
						} else {
							html += '<a class="com_btn_link" style="margin-left: 8px;" href="#" onclick="WorkFlow_ClearValueForFurtureHandler(\''+nextNodeId+'\',\'false\');Dialog_Address(true, \'handlerIds_' + nextNodeId + '\',\'handlerNames_' + nextNodeId + '\', \';\', '+selectType;
							html += ',function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,\'false\',\'false\');});"><bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.select.organization" /></a>';
							if ((nextNodeArray[i].optHandlerIds && nextNodeArray[i].optHandlerIds.replace(/^\s+|\s+$/g, '') != "") || optHandlerSelectType == 'dept' || optHandlerSelectType == 'mechanism'){
								html += hrefObj;
							}
							var hrefObj_formula = "";
							/* var hrefObj_formula = "<a class=\"com_btn_link\" style=\"margin-left: 8px;\" href=\"javascript:void(0)\"";
							hrefObj_formula +=" onclick=\"WorkFlow_ClearValueForFurtureHandler('"+nextNodeId+"','true');lbpm.globals.setHandlerFormulaDialog_('handlerIds_" + nextNodeId + "', 'handlerNames_" + nextNodeId + "', '${JsParam.fdModelName}',function(rtv){lbpm.globals.setFurtureHandlerInfoes1(rtv,'true','false');});\"";
							hrefObj_formula+=">" + "<bean:message bundle='sys-lbpmservice' key='lbpmSupport.selectFormList'/></a>"; */
							html += hrefObj_formula;
						}
						
					}
					
					var td2 = document.createElement("td");
					td2.innerHTML = html;
					tr.appendChild(td2);
					var td3 = document.createElement("td");
					var langNodeDesc = WorkFlow_getLangLabel(nextNodeArray[i].description,nextNodeArray[i]["langs"],"nodeDesc");

					var nodeDesc=langNodeDesc == null?"":langNodeDesc;
					nodeDesc=nodeDesc.replace(/(<pre>)|(<\/pre>)/ig,"");
					td3.innerHTML =nodeDesc;
					tr.appendChild(td3);
					workflowNodeTB.appendChild(tr);
				}
			} 

	}
	

	function lbpm_checkModifyNodeAuthorization(nodeObj, allowModifyNodeId,throughNodesStr){
		var index, nodeIds;
		throughNodesStr+=",";
		//如果要修改的节点不在当前计算后应该出现的节点中（及自动决策将流向的分支）这不出现在待修改列表中
		if(throughNodesStr.indexOf(allowModifyNodeId+",") == -1){
			
			return false;
		}
		if(nodeObj.canModifyHandlerNodeIds != null && nodeObj.canModifyHandlerNodeIds != ""){
			nodeIds = nodeObj.canModifyHandlerNodeIds + ";";
			index = nodeIds.indexOf(allowModifyNodeId + ";");
			if(index != -1){
				return true;
			}
		}
		if(nodeObj.mustModifyHandlerNodeIds != null && nodeObj.mustModifyHandlerNodeIds != ""){
			nodeIds = nodeObj.mustModifyHandlerNodeIds + ";";
			index = nodeIds.indexOf(allowModifyNodeId + ";");
			if(index != -1){
				return true;
			}
		}
	
		return false;
	}
	
	function WorkFlow_GetAllNodeArray(win){
		var rtnNodeArray = new Array();
		(win.$).each(win.lbpm.nodes, function(index, node) {
			if(!win.lbpm.globals.checkNodeType(win.lbpm.constant.NODETYPE_START,node) && !win.lbpm.globals.checkNodeType(win.lbpm.constant.NODETYPE_END,node) && !win.lbpm.globals.checkNodeType(win.lbpm.constant.NODETYPE_MANUALBRANCH,node)){
				rtnNodeArray.push(node);
			}
		});
		rtnNodeArray.sort(win.lbpm.globals.getNodeSorter());
		return rtnNodeArray;
	};

	function _switchLoadFrame(switchFlag){
		if (switchFlag == "all") {
			LUI("switchOpBtn1").element.hide();
			LUI("switchOpBtn0").element.show();
		} else {
			LUI("switchOpBtn1").element.show();
			LUI("switchOpBtn0").element.hide();
		}
		WorkFlow_PassValue();
		WorkFlow_InitialWindow(switchFlag);
		
	}

</script>
	<c:import url="/sys/lbpmservice/workitem/dingSuit/sysLbpmMain_changeProcessor_edit.jsp" charEncoding="UTF-8">
	</c:import>
</html>

