<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysWebserviceRestConfigForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysWebserviceRestConfigForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysWebserviceRestConfigForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysWebserviceRestConfigForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysWebserviceRestConfigForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">

<script>
	Com_IncludeFile("calendar.js|dialog.js|doclist.js|jquery.js|json2.js");
</script>	
<html:form action="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do">
 
<p class="txttitle"><bean:message bundle="sys-webservice2" key="table.sysWebserviceRestConfig"/></p>

<center>
<table class="tb_normal" id="Label_Tabel" width=95%>
	<tr LKS_LabelName='${ lfn:message("config.baseinfo") }'>
		<td>
			<table class="tb_normal" width=100%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" />
			<a onclick="selectModule();return false;" href=""><bean:message key="dialog.selectOther" /></a>			
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdOrder"/>
		</td><td width="35%">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdPrefix"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdPrefix" style="width:35%" showStatus="readOnly"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdDes"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdDes" style="width:85%" />
		</td>
	</tr>
	<c:if test="${not empty sysWebserviceRestConfigForm.docCreatorName}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>

	</tr>
	</c:if>
	<c:if test="${not empty sysWebserviceRestConfigForm.docAlterorName}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docAlteror"/>
		</td><td width="35%">
			<xform:address propertyId="docAlterorId" propertyName="docAlterorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docAlterTime"/>
		</td><td width="35%">
			<xform:datetime property="docAlterTime" showStatus="view" />
		</td>		
	</tr>
	</c:if>
	<%-- 文档页签列表设置 --%>
	<tr id="tr_docLabelArea">
		<td colspan="4" width=100%>
			<bean:message bundle="sys-webservice2" key="table.sysWebserviceDictConfig"/><br/>
			<table class="tb_normal" width="100%" id="TABLE_DocList_Next">
				<tr>
					<td width="20%" class="td_normal_title"><bean:message key="page.serial" /></td>
					<td class="td_normal_title"><bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdName"/></td>
					<td class="td_normal_title" width="120px;"  align="center">
						<img src="${KMSS_Parameter_StylePath}icons/add.gif"
							style="cursor:pointer" onclick="addDocDictRow('TABLE_DocList_Next');"  title="<bean:message key="button.insert"/>">
					</td>
				</tr>
				<!--基准行-->
				<tr KMSS_IsReferRow="1" style="display:none">
					<td KMSS_IsRowIndex="1"></td>
					<td>
						<input type="hidden" name="fdDictItems[!{index}].fdId" />
						<input type="hidden" name="fdDictItems[!{index}].fdOrder" />
						<input type="hidden" name="fdDictItems[!{index}].fdMainDisplay"/>
						<input type="hidden" name="fdDictItems[!{index}].fdListDisplay"/>
						<input type="hidden" name="fdDictItems[!{index}].fdPrefix" />
						<input type="hidden" name="fdDictItems[!{index}].fdModelName"/>
						<input type="hidden" name="fdDictItems[!{index}].fdModuleId" value="${sysWebserviceRestConfigForm.fdId}"/>
						<input name="fdDictItems[!{index}].fdName" class="inputsgl" style="width: 90%" />
					</td>
					<td align="center">
						<input type="hidden" name="!{index}"> 
						<a href="javascript:void(0);" onclick="editDocDictRow(this,'TABLE_DocList_Next');" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/edit.gif" border="0" border="0" title="<bean:message key="button.edit"/>" /></a>
						<a href="javascript:void(0);" onclick="deleteRow();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0" border="0" title="<bean:message key="button.delete"/>" /></a>
						<a href="javascript:void(0);" onclick="moveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0" title="<bean:message key="button.moveup"/>" /></a>
						<a href="javascript:void(0);" onclick="moveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0" title="<bean:message key="button.movedown"/>" /></a>
					</td>
				</tr>
				<!--内容行-->
				<c:forEach items="${sysWebserviceRestConfigForm.fdDictItems}" varStatus="vstatus" var="sysWebserviceDictConfigForm">
					<tr KMSS_IsContentRow="1">
						<td KMSS_IsRowIndex="1">${vstatus.index+1}</td>
						<td>
							<input type="hidden" name="fdDictItems[${vstatus.index}].fdId" value="${sysWebserviceDictConfigForm.fdId}"/>
							<input type="hidden" name="fdDictItems[${vstatus.index}].fdOrder" value="${sysWebserviceDictConfigForm.fdOrder}"/>				
							<input type="hidden" name="fdDictItems[${vstatus.index}].fdMainDisplay" value='${sysWebserviceDictConfigForm.fdMainDisplay}'/>
							<input type="hidden" name="fdDictItems[${vstatus.index}].fdListDisplay" value='${sysWebserviceDictConfigForm.fdListDisplay}'/>							
							<input type="hidden" name="fdDictItems[${vstatus.index}].fdPrefix" value="${sysWebserviceDictConfigForm.fdPrefix}" />
							<input type="hidden" name="fdDictItems[${vstatus.index}].fdModelName" value="${sysWebserviceDictConfigForm.fdModelName}" />
							<input type="hidden" name="fdDictItems[${vstatus.index}].fdModuleId" value="${sysWebserviceRestConfigForm.fdId}" />
							<input name="fdDictItems[${vstatus.index}].fdName" class="inputsgl" style="width: 90%" value="${sysWebserviceDictConfigForm.fdName}" />
						</td>
						<td align="center">
							<input type="hidden" name="${vstatus.index}"> 
							<a href="javascript:void(0);" onclick="editDocDictRow(this,'TABLE_DocList_Next');" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/edit.gif" border="0" title="<bean:message key="button.edit"/>" /></a>
							<a href="javascript:void(0);" onclick="deleteRow();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0" title="<bean:message key="button.delete"/>" /></a>
							<a href="javascript:void(0);" onclick="moveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0" title="<bean:message key="button.moveup"/>" /></a>
							<a href="javascript:void(0);" onclick="moveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0" title="<bean:message key="button.movedown"/>" /></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>	
	
			</table>
		</td>
	</tr>
</table> 
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
	var openObj;
	var index;
	var modelArr;
	var actionType;
	var pageTableId;
	$(document).ready(function (){
		//初始化动态表格
		DocList_Info.push("TABLE_DocList_Next");
	});	
	//选择模块
	function selectModule(){
		Dialog_List(false, "fdPrefix", "fdName", null, "restModuleSelectDialog",afterModuleSelect,"restModuleSelectDialog&keyword=!{keyword}",null,null,
				"<bean:message bundle='sys-webservice2' key='sysWebserviceRestConfig.moduleSelectDilog'/>");
	}
	
	function afterModuleSelect(dataObj){
		if(dataObj==null)
			return ;
		var rtnData = dataObj.GetHashMapArray();
		if(rtnData[0]==null)
			return;
		for(var key in rtnData[0]){
			if(key.indexOf('dynamicMap_') > -1){
				var name = key.replace('_','(').replace('_',')'),
					element = $('[name="'+ name +'"]');
				if(element && element.length > 0){
					element.val( rtnData[0][key]);
				}
			}
		}
	}
	
	//添加一个文档配置行
	function addDocDictRow(tableId){
		this.actionType = 'add';
		this.pageTableId = tableId;
		var fdPrefix = $('input[name="fdPrefix"]').val();
		if(fdPrefix == "" || fdPrefix == null){
			alert('<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.validate.notNull.fdName"/>');
			return;
		}
		
		this.modelArr =  new Array();
		
		var tbInfo = DocList_TableInfo[tableId];
		
		this.index = tbInfo.lastIndex - tbInfo.firstIndex;
		for(var i=0;i<index;i++){
			if($('input[name="fdDictItems['+i+'].fdModelName"]').val()!=null){
				modelArr.push($('input[name="fdDictItems['+i+'].fdModelName"]').val());
			}
		}
		
		$('input[name="fdDictItems['+index+'].fdModuleId"]').next().html();
		var obj = {'fdId':$('input[name="fdDictItems['+index+'].fdId"]').val(),
				   'fdName':$('input[name="fdDictItems['+index+'].fdName"]').val(),
				   'fdOrder':$('input[name="fdDictItems['+index+'].fdOrder"]').val(),
				   'fdMainDisplay':$('input[name="fdDictItems['+index+'].fdMainDisplay"]').val(),
				   'fdListDisplay':$('input[name="fdDictItems['+index+'].fdListDisplay"]').val(),				   
				   'fdPrefix':fdPrefix,
				   'fdModuleId':$('input[name="fdDictItems['+index+'].fdModuleId"]').val(),
				   'fdModelName':$('input[name="fdDictItems['+index+'].fdModelName"]').val()
				};
		var style = "dialogWidth:600px; dialogHeight:450px; status:0;scroll:1; help:0; resizable:1";
		
		if(window.showModalDialog == undefined){
			openObj = obj;
			var iWidth = 600;
			var iHeight = 450;
			var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
			var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
			var chromeStyle = "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes";
			var rtnVal = window.open(Com_Parameter.ContextPath+"sys/webservice2/sys_webservice_dict_config/sysWebserviceDictConfig_edit.jsp", "弹出窗口", chromeStyle);
	    }else{
		
			var rtnVal = window.showModalDialog(Com_Parameter.ContextPath+"sys/webservice2/sys_webservice_dict_config/sysWebserviceDictConfig_edit.jsp", obj, style);
			if(obj != null){
				if(obj.fdName && obj.fdName != ""){
					if(modelArr.indexOf(obj.fdModelName)==-1){
						DocList_AddRow(tableId);
						afterOpenModalDialog(obj,index);
					}else
						alert('<bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdSelectedNotice"/>');
				}
			}
	    }
	}
	
	//编辑一个文档配置
	function editDocDictRow(_this,tableId){
		this.actionType = 'edit';
		var fdPrefix = $('input[name="fdPrefix"]').val();
		if(fdPrefix == "" || fdPrefix == null){
			alert('<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.validate.notNull.fdName"/>');
			return;
		}
		this.index = $(_this).prev().attr('name');
		
		var origModelName = $('input[name="fdDictItems['+index+'].fdModelName"]').val();
		
		this.modelArr =  new Array();
		
		var tbInfo = DocList_TableInfo[tableId];
		
		var rows = tbInfo.lastIndex - tbInfo.firstIndex;
		for(var i=0;i<rows;i++){
			if($('input[name="fdDictItems['+i+'].fdModelName"]').val()!=null){
				modelArr.push($('input[name="fdDictItems['+i+'].fdModelName"]').val());
			}
		}

		var fdMainDisplay = null;
		if($('input[name="fdDictItems['+index+'].fdMainDisplay"]').val() != null && $('input[name="fdDictItems['+index+'].fdMainDisplay"]').val() != "")
			fdMainDisplay = JSON.parse($('input[name="fdDictItems['+index+'].fdMainDisplay"]').val());
		
		var fdListDisplay = null;
		if($('input[name="fdDictItems['+index+'].fdListDisplay"]').val() != null && $('input[name="fdDictItems['+index+'].fdListDisplay"]').val() != "")
			fdListDisplay = JSON.parse($('input[name="fdDictItems['+index+'].fdListDisplay"]').val());

		var obj = {'fdId':$('input[name="fdDictItems['+index+'].fdId"]').val(),
				   'fdName':$('input[name="fdDictItems['+index+'].fdName"]').val(),
				   'fdOrder':$('input[name="fdDictItems['+index+'].fdOrder"]').val(),
				   'fdMainDisplay':fdMainDisplay,
				   'fdListDisplay':fdListDisplay,
				   'fdPrefix':fdPrefix,
				   'fdModuleId':$('input[name="fdDictItems['+index+'].fdModuleId"]').val(),
				   'fdModelName':$('input[name="fdDictItems['+index+'].fdModelName"]').val()
				};
		var style = "dialogWidth:600px; dialogHeight:450px; status:0;scroll:1; help:0; resizable:1";
		
		if(window.showModalDialog == undefined){
			openObj = obj;

			var iWidth = 600;
			var iHeight = 450;
			var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
			var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
			var chromeStyle = "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes";
			var rtnVal = window.open(Com_Parameter.ContextPath+"sys/webservice2/sys_webservice_dict_config/sysWebserviceDictConfig_edit.jsp", "弹出窗口", chromeStyle);
	    }else{
			var rtnVal = window.showModalDialog(Com_Parameter.ContextPath+"sys/webservice2/sys_webservice_dict_config/sysWebserviceDictConfig_edit.jsp", obj, style);

			if(obj != null){
				if(origModelName==obj.fdModelName)
					afterOpenModalDialog(obj,index);
				else if(modelArr.indexOf(obj.fdModelName)==-1)
					afterOpenModalDialog(obj,index);
				else
					alert('<bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdSelectedNotice"/>');
			}
	    }
	}

	function afterOpenModalDialog(obj,index,actionType,pageTableId){
		if(actionType=='add'){
			DocList_AddRow(pageTableId);
		}
		
		$('input[name="fdDictItems['+index+'].fdId"]').val(obj.fdId);
		$('input[name="fdDictItems['+index+'].fdName"]').val(obj.fdName);
		$('input[name="fdDictItems['+index+'].fdOrder"]').val(obj.fdOrder);
		$('input[name="fdDictItems['+index+'].fdPrefix"]').val(obj.fdPrefix);
		$('input[name="fdDictItems['+index+'].fdMainDisplay"]').val(JSON.stringify(obj.fdMainDisplay));
		$('input[name="fdDictItems['+index+'].fdListDisplay"]').val(JSON.stringify(obj.fdListDisplay));		
		$('input[name="fdDictItems['+index+'].fdModuleId"]').val(obj.fdModuleId);
		$('input[name="fdDictItems['+index+'].fdModelName"]').val(obj.fdModelName);
	}
	
	function moveRow(direct, optTR){
		if(optTR==null)
			optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var tbInfo = DocList_TableInfo[optTB.id];
		var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
		var tagIndex = rowIndex + direct;
		if(direct==1){
			if(tagIndex>=tbInfo.lastIndex)
				return;
			optTB.rows[rowIndex].parentNode.insertBefore(optTB.rows[tagIndex], optTB.rows[rowIndex]);
		}else{
			if(tagIndex<tbInfo.firstIndex)
				return;
			optTB.rows[rowIndex].parentNode.insertBefore(optTB.rows[rowIndex], optTB.rows[tagIndex]);
		}
		refreshIndex(tbInfo, rowIndex);
		refreshIndex(tbInfo, tagIndex);
	}
	function deleteRow(optTR){
		if(optTR==null)
			optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var tbInfo = DocList_TableInfo[optTB.id];
		var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
		optTB.deleteRow(rowIndex);
		tbInfo.lastIndex--;
		for(var i = rowIndex; i<tbInfo.lastIndex; i++)
			refreshIndex(tbInfo, i);
	}
	function refreshIndex(tbInfo, index){
		for (var n = 0; n < tbInfo.cells.length; n ++) {
			if (tbInfo.cells[n].isIndex) {
				tbInfo.DOMElement.rows[index].cells[n].innerHTML = index;
			}
		}
		refreshFieldIndex(tbInfo, index, "INPUT");
		refreshFieldIndex(tbInfo, index, "TEXTAREA");
		refreshFieldIndex(tbInfo, index, "SELECT");
	}
	function refreshFieldIndex(tbInfo, index, tagName){
		var optTR = tbInfo.DOMElement.rows[index];
		var fields = optTR.getElementsByTagName(tagName);
		for(var i=0; i<fields.length; i++){
			var fieldName = fields[i].name.replace(/\d/, index-tbInfo.firstIndex);
			if(document.getElementById(fields[i].name)!=null){
				Com_SetOuterHTML(fields[i],fields[i].outerHTML.replace(/\d/, index-tbInfo.firstIndex));
			}else{
				fields[i].setAttribute("name",fieldName);
			}
		}
	}

</script>
</html:form>

	</template:replace>
</template:include>