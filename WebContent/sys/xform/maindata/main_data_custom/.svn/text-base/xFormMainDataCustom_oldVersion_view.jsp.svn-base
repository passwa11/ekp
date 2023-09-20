<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<title>
	<c:out value="${ lfn:message('sys-xform-maindata:tree.relation.jdbc.root') } - ${ lfn:message('sys-xform-maindata:tree.relation.jdbc.custom') }"></c:out>
</title>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>

<div id="optBarDiv">

	<input type="button"
		value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysFormMainDataCustom.do?method=edit&fdId=${JsParam.fdId}','_self');">


	<input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysFormMainDataCustom.do?method=delete&fdId=${JsParam.fdId}','_self');">
	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.custom"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
		</td><td width="35%">
			<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="docCategoryId" style="width:90%"
					propertyName="docCategoryName" dialogJs="XForm_treeDialog()">
			</xform:dialog>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdOrder"/>
		</td><td width="35%">
			<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
			<xform:text property="fdNewOrder" style="width:85%;" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
		</td>
		<td width=35%>
			<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
		</td>
		<td width=35%>
			<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:15%" validators="alphanum"/>&nbsp;
		</td>
	</tr>
	<!-- 是否级联 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade"/>
		</td>
		<td colspan="3">
			<div class="xform_main_data_custom_cascade">
				<xform:radio property="isCascade" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.isCascade')}" onValueChange="xform_main_data_custom_showCascadeSelect(this);">
					<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade.yes"/></xform:simpleDataSource>
					<xform:simpleDataSource value="false"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade.no"/></xform:simpleDataSource>
				</xform:radio>
				<c:if test="${sysFormMainDataCustomForm.cascadeCustomId ne null}">
					<a href="javascript:void(0);" onclick="Com_OpenWindow('sysFormMainDataCustom.do?method=view&fdId=${sysFormMainDataCustomForm.cascadeCustomId}','_target');" style="color:#1b83d8;"><c:out value="${sysFormMainDataCustomForm.cascadeCustomSubject}"></c:out></a>
				</c:if>
				
			</div>
		</td>
	</tr>
	<!-- 自定义数据 -->
	<tr>
		<td colspan="4">
			<div class="xform_main_data_custom_tableWrap">
				<!-- 有上级 -->
				<table id="xform_main_data_custom_extendDataTable_hasSuper" width="100%">
					<tr class="tr_normal_title">
						<td width="5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.index"/></td>
						<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.super"/></td>
						<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.showValue"/></td>
						<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.realValue"/></td>
						<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.order"/></td>
					</tr>
				</table>
				<!-- 无上级 -->
				<table id="xform_main_data_custom_extendDataTable" width="100%">
					<tr class="tr_normal_title">
						<td width="5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.index"/></td>
						<td width="30%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.showValue"/></td>
						<td width="30%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.realValue"/></td>
						<td width="30%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.order"/></td>
					</tr>
				</table>
			</div>	
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdCreator"/>
		</td>
		<td width="35%">
			<c:out value="${sysFormMainDataCustomForm.docCreatorName }"></c:out>				
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message key="model.fdCreateTime"/>
		</td>
		<td width="35%">
			<c:out value="${sysFormMainDataCustomForm.docCreateTime }"></c:out>
		</td>
	</tr>
	<c:if test="${not empty sysFormMainDataCustomForm.docAlterorName}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.docAlteror"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataCustomForm.docAlterorName }"></c:out>
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdAlterTime"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataCustomForm.docAlterTime }"></c:out>
			</td>
		</tr>
	</c:if>
	
</table>
<script>
	
	// 是否多语言
	var _xform_main_data_custom_isLangEnabled = <%=SysLangUtil.isLangEnabled()%>;
	// 官方语言
	var _xform_main_data_custom_officialLang = "<%=ResourceUtil.getKmssConfigString("kmss.lang.official")%>";
	// 当前语言
	var _xform_main_data_custom_currentLang = Com_Parameter.Lang.toUpperCase ();

	function xform_main_data_custom_getCascadeValueText(value,cascadeExtendData){
		if(cascadeExtendData){
			for(var i = 0;i < cascadeExtendData.length;i++){
				var extendData = cascadeExtendData[i];
				if(extendData.value == value){
					return xform_main_data_custom_getLangValueText(extendData.valueText);
				}
			}	
		}
	}
	
	//获取多语言信息
	function xform_main_data_custom_getLangValueText(valueText){
		var val = '';
		if(_xform_main_data_custom_isLangEnabled){
			if(valueText.hasOwnProperty(_xform_main_data_custom_currentLang)){
				val = valueText[_xform_main_data_custom_currentLang];
			}else if(_xform_main_data_custom_officialLang && _xform_main_data_custom_officialLang != 'null'){
				//否则选择官方语言
				var officialArray = _xform_main_data_custom_officialLang.trim().split("|");
				if(officialArray.length == 2){
					var official = officialArray[1].toUpperCase();
					val = valueText[official];	
				}
			}
		}else if(_xform_main_data_custom_officialLang && _xform_main_data_custom_officialLang != 'null'){
			//否则选择官方语言
			var officialArray = _xform_main_data_custom_officialLang.trim().split("|");
			if(officialArray.length == 2){
				var official = officialArray[1].toUpperCase();
				val = valueText[official];	
			}
		}else if(valueText.hasOwnProperty(_xform_main_data_custom_currentLang)){
				val = valueText[_xform_main_data_custom_currentLang];
		}
		return val;
	}

	//页面初始化数据
	function xform_main_data_custom_init(){
		var iscascade = '${sysFormMainDataCustomForm.isCascade}';
		var $table; 
		var html = "";
		var fdExtendData = $.parseJSON('${sysFormMainDataCustomForm.fdExtendData}');
		if(iscascade == 'true'){
			$table = $("#xform_main_data_custom_extendDataTable_hasSuper");
			$table.show();
			$("#xform_main_data_custom_extendDataTable").hide();
			var cascadeExtendData = $.parseJSON('${sysFormMainDataCustomForm.cascadeCustomExtendData}'); 
			for(var i = 0;i < fdExtendData.length;i++){
				var extendData = fdExtendData[i];
				html += "<tr>";
				html += "<td>"+ (i+1) +"</td>";
				html += "<td>"+ xform_main_data_custom_getCascadeValueText(extendData.cascade,cascadeExtendData) +"</td>";
				html += "<td>"+ xform_main_data_custom_getLangValueText(extendData.valueText) +"</td>";
				html += "<td>"+ extendData.value +"</td>";
				html += "<td>"+ extendData.order +"</td>";
				html += "</tr>";
			}
		}else if(iscascade == 'false'){
			$table = $("#xform_main_data_custom_extendDataTable"); 
			$("#xform_main_data_custom_extendDataTable_hasSuper").hide();
			$table.show();
			for(var i = 0;i < fdExtendData.length;i++){
				var extendData = fdExtendData[i];
				html += "<tr>";
				html += "<td>"+ (i+1) +"</td>";
				html += "<td>"+ xform_main_data_custom_getLangValueText(extendData.valueText) +"</td>";
				html += "<td>"+ extendData.value +"</td>";
				html += "<td>"+ extendData.order +"</td>";
				html += "</tr>";
			}
		}
		$table.append(html);
	}
	Com_AddEventListener(window,'load',xform_main_data_custom_init);
</script>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>