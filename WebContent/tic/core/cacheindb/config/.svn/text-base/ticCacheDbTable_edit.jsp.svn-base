<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/core/cacheindb/config/ticCacheDbTable.do">
<div id="optBarDiv">
	
		<input type=button value="<bean:message key="button.update"/>"
			onclick="if(!validateForm())return;Com_Submit(document.ticCacheDbTableForm, 'update');">

			<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCacheDbTable.do?method=delete&fdId=${ticCacheDbTableForm.fdId}','_self');">

		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<script>
    Com_IncludeFile("dialog.js|doclist.js");
</script>
<%@ include file="/tic/core/cacheindb/config/ticCacheDbTable_script.jsp"%>
<p class="txttitle"><bean:message bundle="tic-core-cacheindb" key="ticCacheindb.config.cache"/></p>

<center>
<table class="tb_normal" width=95% style="table-layout: fixed;">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-cacheindb" key="ticCacheindb.config.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdName') }" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-cacheindb" key="ticCacheindb.config.fdTableName"/>
		</td><td width="35%">
			<xform:text property="fdTable" style="width:85%"  validators="uniqueTableName" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdTableName') }"  />
		</td>
	</tr>

	<tr > 
		<td width="90%" colspan="4">

			<script language="JavaScript">
			if(DocList_Info == null){
				DocList_Info = new Array("TABLE_DocList");
			}else{
				DocList_Info.push("TABLE_DocList");
			} 
			</script>
			<table class="tb_normal" width=100% id="TABLE_DocList">	
				<tr>
					<td class="td_normal_title" align="center" width=30%>
						<bean:message key="ticCacheindb.config.fdColumnName" bundle="tic-core-cacheindb"/>
					</td>
					<td class="td_normal_title" align="center" width=20%>
						<bean:message key="ticCacheindb.config.fdColumn" bundle="tic-core-cacheindb" />
					</td>
					<td class="td_normal_title" align="center" width=20%>
						<bean:message key="ticCacheindb.config.fdType" bundle="tic-core-cacheindb"/>
					</td>
					<td class="td_normal_title" align="center" width=20%>
						<bean:message key="ticCacheindb.config.fdLength" bundle="tic-core-cacheindb"/>
					</td>

					<td class="td_normal_title" align="center" width=10%>
						<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/add.gif"/>" 
							 title="<bean:message key="button.create" />" 
							 onclick="AddRow_Init();"> 
					</td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td align="center">
						<input type="hidden" name="fdColumns[!{index}].fdId">
						<xform:text property="fdColumns[!{index}].fdName" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdColumnName') }" style="width:85%"/>
					</td>
					<td align="center">
						<xform:text property="fdColumns[!{index}].fdColumn" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdColumn') }" style="width:85%"/>
					</td>
					<td align="center">
						<xform:select property="fdColumns[!{index}].fdType" showPleaseSelect="true" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdType') }" onValueChange="selectType">
							<xform:enumsDataSource enumsType="fdColumns_fdType" />
						</xform:select>
					</td>
					<td align="center">
						<xform:text property="fdColumns[!{index}].fdLength" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdLength') }" style="width:85%"/>
					</td>

					<td align="center">
						<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/delete.gif"/>" 
							title="<bean:message key="button.delete" />"
							onclick="DocList_DeleteRow();">
						<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/up.gif"/>" 
							title="<bean:message key="button.moveup" />"
							onclick="DocList_MoveRow(-1);"> 
						<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/down.gif"/>" 
							title="<bean:message key="button.movedown" />"
							onclick="DocList_MoveRow(1);"> 
					</td>
				</tr>
				<c:forEach items="${ticCacheDbTableForm.fdColumns}" var="ticCacheDbColumnForm" varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td align="center">
						<input type="hidden" name="fdColumns[${vstatus.index}].fdId" value="${ticCacheDbColumnForm.fdId}">
						<xform:text property="fdColumns[${vstatus.index}].fdName" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdColumnName') }" style="width:85%" value="${ticCacheDbColumnForm.fdName}"/>
					</td>
					<td align="center">	
						<xform:text property="fdColumns[${vstatus.index}].fdColumn" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdColumn') }" style="width:85%"/>
					</td>
					<td align="center">
						<xform:select property="fdColumns[${vstatus.index}].fdType" showPleaseSelect="true" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdType') }" onValueChange="selectType">
							<xform:enumsDataSource enumsType="fdColumns_fdType" />
						</xform:select>
					</td>
					<td align="center">
						<xform:text property="fdColumns[${vstatus.index}].fdLength" required="true" subject="${lfn:message('tic-core-cacheindb:ticCacheindb.config.fdLength') }" style="width:85%"/>
					</td>

					<td align="center">
						<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/delete.gif"/>" 
							title="<bean:message key="button.delete" />"
							onclick="DocList_DeleteRow();">
						<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/up.gif"/>" 
							title="<bean:message key="button.moveup" />"
							onclick="DocList_MoveRow(-1);"> 
						<img style="cursor:pointer" class=optStyle src="<c:url value="/resource/style/default/icons/down.gif"/>" 
							title="<bean:message key="button.movedown" />"
							onclick="DocList_MoveRow(1);">
					</td>
				</tr>	
				</c:forEach>
			</table>

		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="fdRelationId" value="${param.funcId}"/>
<html:hidden property="fdKey" value="${param.fdKey}" />
<html:hidden property="method_GET" />
<script>
	var _validation =$KMSSValidation();

	var NameValidators = {
			'uniqueTableName' : {
				error : "<bean:message key='ticCacheindb.fdTableName.unique' bundle='tic-core-cacheindb' />",
				test : function (value) {
						var fdId = document.getElementsByName("fdId")[0].value;
						var result = checkNameUnique("ticCacheDbTableService",value,fdId);
						if (!result) 
							return false;
						return true;
				      }
			}
 	};
	_validation.addValidators(NameValidators);

	//校验名称是否唯一
	function checkNameUnique(bean, fdTable,fdId) {
		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
				+ bean + "&fdTable=" + fdTable+"&fdId="+fdId+"&date="+new Date());
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}

	function confirmDelete(msg) {
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}

</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>