<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.sys.xform.maindata.util.MainDataInsystemEnumUtil" %>
<%@page import="net.sf.json.JSONObject" %>
<link rel="Stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/css/edit.css"/>" />
<% response.setHeader("X-UA-Compatible","IE=edge"); %>
<title>
	<c:out value="主数据显示设置"></c:out>
</title>

<html:form action="/sys/xform/maindata/main_data_show/sysFormMainDataShow.do">
<% 
	JSONObject enumJSON = MainDataInsystemEnumUtil.getAllEnum();
	String enumString = enumJSON.toString();
%>
<script>
	var _main_data_show_enumCollection = <%=enumString%>;
	Com_IncludeFile("dialog.js|data.js|doclist.js");
</script>
</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/css/xFormMainDataShow.css">
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<div id="optBarDiv">
	<c:if test="${sysFormMainDataShowForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="xform_main_data_submit('update');">
	</c:if>
	<c:if test="${sysFormMainDataShowForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="xform_main_data_submit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="xform_main_data_submit('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.main.data.show"/></p>

<center>
<table class="tb_normal" width=95%>
	<!-- 排序号、所属分类 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
		</td><td width="35%" colspan="3">
			<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="docCategoryId" style="width:90%"
					propertyName="docCategoryName" dialogJs="XForm_treeDialog()">
			</xform:dialog>
		</td>
		
	</tr>
	<!-- 标题 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.docSubject"/>
		</td>
		<td width=35% colspan="3">
			<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataShow.docSubject') }" style="width:85%" />
		</td>
		
	</tr>
	
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.fdMainDataIcon"/>
		</td>
		<td width=35%>
		
		<img style="cursor:hand;<c:if test='${sysFormMainDataShowForm.fdMainDataIcon eq null}'>display:none;</c:if>" width=57 height=58 id="moduleIcon" onclick="selectIcon('module',document.getElementsByName('fdMainDataIcon')[0],this);" src="<c:url value="${sysFormMainDataShowForm.fdMainDataIcon}" />" ></img>
			<a href="javascript:;" onclick="selectIcon('module',document.getElementsByName('fdMainDataIcon')[0],this);" style="color:#1b83d8;">选择</a>
			<input type="hidden" name="fdMainDataIcon" value="${sysFormMainDataShowForm.fdMainDataIcon}">
			<%-- 
			<div class="lui_icon_l ${sysFormMainDataShowForm.fdMainDataIcon }" style="background-color: #C78700;" onclick="SysIconDialog(this);"></div>
			<input name="fdMainDataIcon" class="inputsgl" value="${sysFormMainDataShowForm.fdMainDataIcon }" type="hidden">
			--%>
		</td>
		<td class="td_normal_title" width=15%>
			颜色
		</td>
		<td>
			<input type="hidden" id="fdColor" name="fdColor" value="${sysFormMainDataShowForm.fdColor}"/>
		 <div class="lui_show_edit_label" style="display: block; width: 100%;min-height: 10px;">
               
                 <ul class="clrfix color_ul">
                     <li class="select"><a></a></li>
                     <li class="line"></li>
                     <li class="color_1" onclick="colorClick(this)"></li>
                     <li class="color_2" onclick="colorClick(this)"></li>
                     <li class="color_3" onclick="colorClick(this)"></li>
                     <li class="color_4" onclick="colorClick(this)"></li>
                     <li class="color_5" onclick="colorClick(this)"></li>
                     <li class="color_6" onclick="colorClick(this)"></li>
                     <li class="color_7" onclick="colorClick(this)"></li>
                     <li class="color_8" onclick="colorClick(this)"></li>
                     <li class="color_9" onclick="colorClick(this)"></li>
                     <li class="color_10" onclick="colorClick(this)"></li>
                     <li class="color_11" onclick="colorClick(this)"></li>
                 </ul>
                 </div>
       </td>
	</tr>
	
	<!-- 系统数据 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.fdModleName"/>
		</td>
		<td colspan="3">
			<div style="width:20%;">
				<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormMainDataShow.fdModleName') }" propertyId="fdModelName" style="width:90%"
						propertyName="fdModelNameText" dialogJs="XForm_selectModelNameDialog();">
				</xform:dialog>
			</div>
		</td>
	</tr>
	<tr>
			<td class="td_normal_title" width=15%>
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdOrder') }
			</td>
			<td colspan="3"><xform:text property="fdOrder" validators="digits min(0)" /></td>
	</tr>
	
	<tr id="tr_fdMainDataSubjectField">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.fdMainDataSubjectField"/>
		</td>
		<td width=35% colspan="3">
			<div id='div_fdMainDataSubjectField'>
			</div>
			<input name="fdMainDataSubjectField" class="inputsgl" value="${sysFormMainDataShowForm.fdMainDataSubjectField }" type="hidden">
		</td>
		
	</tr>
	
	
	<!-- 显示字段 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.fdShowFields"/>
		</td>
		<td colspan="3">
			<table id="xform_main_data_showFieldsTable" class="tb_normal" style="float:left;width:90%;">
				<tr class="xform_main_data_tableTitle">
					<td width="50%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.attribute') }</td>
					<td width="15%">别名</td>
					<td width="20%">卡片中显示</td>
					<td width="150px"><a href="javascript:void(0);" onclick="xform_main_data_addAttrItem('xform_main_data_showFieldsTable',null,null,null);" style="color:#1b83d8;">添加</a></td>
				</tr>
			</table>
			<span class="txtstrong">*</span>
		</td>
		
		<input type="hidden" name="fdShowFields" />
	</tr>
	
	<!-- 关联设置 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataShow.sysFormMainDataShowRelates"/>
		</td>
		<td colspan="3">
		
		
		
			<table class="tb_normal" width=100% id="TABLE_DocList" >
	<tr id="detail_tr_id">
		
		<!-- 序号 -->
		<td KMSS_IsRowIndex="1" class="bgColorTd"> 
			<bean:message key="page.serial"/>
		</td>
		
		<!-- 费用类型-->
		<td class="bgColorTd" align="center">
			${lfn:message('sys-xform-maindata:sysFormJdbcDataSetCategory.fdName') }
		</td>
		<td class="bgColorTd" align="center">
			${lfn:message('sys-xform-maindata:sysFormMainDataShow.sysFormMainDataShowRelates') }
		</td>
		<td  align="center" class="bgColorTd">
			<a href="javascript:void(0);" onclick="FS_AddProjectDetailNew();">添加</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none" class="fdItem">
		
		<td KMSS_IsRowIndex="1"><span id="fdOOrder!{index}"><c:out value="!{index}"></c:out></span>
			<input type="hidden" name="order" value="">
		</td>
		<td>
			<input type="text" name="sysFormMainDataShowRelateForms[!{index}].docSubject" class="inputsgl" required="true" validate="required" /><span class="txtstrong">*</span>
			</td>
			<td>
			
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePageType') }：
			<xform:radio property="sysFormMainDataShowRelateForms[!{index}].fdRelatePageType"  onValueChange="change_relatePageType(this);" value="data">
			<xform:enumsDataSource  enumsType="mainData_show_relatePageType"/></xform:radio>
			</br>
				<div name="div_relatePageType_data">
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdModelNameText') }：
				<input type="text" name="sysFormMainDataShowRelateForms[!{index}].fdModelNameText"  class="inputsgl" readonly="readonly" style="width:200px;">
				<a href="#" onclick="editRelate();">${lfn:message('button.edit') }</a>
				</div>
				<div name="div_relatePageType_page" style="display:none;">
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePage') }：<input type="text" style="width:500px;" name="sysFormMainDataShowRelateForms[!{index}].fdRelatePage"  class="inputsgl" />
				<br/>
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePageMobile') }：<input type="text" style="width:500px;" name="sysFormMainDataShowRelateForms[!{index}].fdRelatePageMobile"  class="inputsgl"/>
				</div>
				
			
			<div id="div_relate!{index}" name="divRelate" style="height:400px; overflow:auto;display:none;">

		 	<table class="tb_normal" width="95%">
	<!-- 排序号、所属分类 -->
	<tbody>
	<%-- 
	<tr>
		<td class="td_normal_title" width="15%">
			标题
		</td>
		<td width="35%" colspan="3">
			<xform:text property="sysFormMainDataShowRelateForms[!{index}].docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataShow.docSubject') }" style="width:85%" />
		</td>
	</tr>
	--%>
	<!-- 系统数据 -->
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdModelName') }
		</td>
		<td colspan="3">
			<div style="width:40%;">
				<xform:dialog subject="${lfn:message('sys-xform-maindata:sysFormMainDataShow.fdModleName') }" propertyId="sysFormMainDataShowRelateForms[!{index}].fdModelName" style="width:90%"
						propertyName="sysFormMainDataShowRelateForms[!{index}].fdModelNameText" dialogJs="XForm_selectModelNameDialog(currentIndex);">
				</xform:dialog>
			</div>
			<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fieldArray" />
		</td>
	</tr>
	<!-- 查询条件 -->
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdWhereBlock') }
		</td>
		<td colspan="3">
			<table id="xform_main_data_whereTable_relate!{index}"  name="tb_fdWhereBlock" class="tb_normal" style="float:left;width:95%;">
				<tr class="xform_main_data_tableTitle">
					<td width="40%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.attribute') }</td>
					<td width="15%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.operator') }</td>
					<td width="35%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.value') }</td>
					<td style="width:15%;"><a href="javascript:void(0);" onclick="xform_main_data_addWhereItem(null,currentIndex);" style="color:#1b83d8;">添加</a></td>
				</tr>
			</table>
		</td>
		<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdWhereBlock">
	</tr>
	
	<!-- 显示字段 -->
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumn') }
		</td>
		<td colspan="3">
			<table id="xform_main_data_showFieldsTable_relate!{index}" name="tb_fdShowColumn" class="tb_normal" style="float:left;width:95%;">
				<tbody>
				<tr class="xform_main_data_tableTitle">
					<td width="60%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.attribute') }</td>
					<td width="15%">别名</td>
					<td style="width:25%;"><a href="javascript:void(0);" onclick="xform_main_data_addAttrItem('',null,null,currentIndex);" style="color:#1b83d8;">添加</a></td>
				</tr>
			</tbody></table>
			<span class="txtstrong">*</span>
		</td>
		<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdShowColumn">
	</tr>
	
	<!-- 数据关联设置 -->
	<tr name="tr_fdForeignField">
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelateType') }
		</td>
		<td colspan="3">
			<xform:select property="sysFormMainDataShowRelateForms[!{index}].fdRelateType" showPleaseSelect="false" value='1' onValueChange="xform_main_data_trrigleRelateType(this,currentIndex);">
				<xform:enumsDataSource enumsType="mainData_show_relateType" />
			</xform:select>
		</td>
		<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdForeignField">
		<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdMainDataRelateField">
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.viewPage') }
		</td>
		<td>
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePage') }：<input type="text" name="sysFormMainDataShowRelateForms[!{index}].fdViewPage"  class="inputsgl"  style="width:500px;"><br/>
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePageMobile') }：<input type="text" name="sysFormMainDataShowRelateForms[!{index}].fdViewPageMobile"  class="inputsgl"  style="width:500px;">
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.mobileSetting') }
		</td>
		<td>
			<table>
			<tr>
				<th>显示列</th><th>字段</th><th>别名</th>
			</tr>
			<tr name="tr_fdSubjectField">
				<td>${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdSubjectField') }</td>
				<td>
				<select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="fdId" data-type="String">${lfn:message('page.firstOption') }</option>
				</select>
				</td>
				<td><input type="text" name="alias" class="inputsgl" /></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdSubjectField" >
			</tr>
			<tr name="tr_fdShowColumnMobile1">
				<td>${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumnMobile1') }</td>
				<td><select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="" data-type="String">${lfn:message('page.firstOption') }</option>
				</select></td>
				<td><input type="text" name="alias"  class="inputsgl" /></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdShowColumnMobile1"  >
			</tr>
			<tr name="tr_fdShowColumnMobile2">
				<td>${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumnMobile2') }</td>
				<td><select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="" data-type="String">${lfn:message('page.firstOption') }</option>
				</select></td>
				<td><input type="text" name="alias"  class="inputsgl"/></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdShowColumnMobile2"  >
			</tr>
			<tr name="tr_fdShowColumnMobile3">
				<td>${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumnMobile3') }</td>
				<td><select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="" data-type="String">${lfn:message('page.firstOption') }</option>
				</select></td>
				<td><input type="text" name="alias"  class="inputsgl" /></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[!{index}].fdShowColumnMobile3"  >
			</tr>
			
			</table>
			
			
		</td>
	</tr>
</tbody></table></div>
		
		</td>
			
		<td>
		<%-- 
			<img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del" onclick="DocList_DeleteRow();" style="cursor:pointer">&nbsp;
		--%>
			<a href="javascript:void(0);" onclick="DocList_DeleteRow();">${lfn:message('button.delete') }</a>
			<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);">${lfn:message('dialog.moveUp') }</a>
			<a href="javascript:void(0);" onclick="DocList_MoveRow(1);">${lfn:message('dialog.moveDown') }</a>
		</td>
	</tr>
	<!--内容行-->
	<c:forEach items="${sysFormMainDataShowForm.sysFormMainDataShowRelateForms}"
		var="sysFormMainDataShowRelateForm" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" class="fdItem" >
			<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdId" value='${sysFormMainDataShowRelateForm.fdId}'/>
			<td  KMSS_IsRowIndex="1" onclick='DocList_addRow()'><span id="fdOOrder!{index}">${vstatus.index+1}</span></td>
			<td>
				<input type="text" name="sysFormMainDataShowRelateForms[${vstatus.index}].docSubject"  value='${sysFormMainDataShowRelateForm.docSubject}' validate="required"  class="inputsgl"><span class="txtstrong">*</span>
			</td>
			<td>
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePageType') }：
			<xform:radio property="sysFormMainDataShowRelateForms[${vstatus.index}].fdRelatePageType"  onValueChange="change_relatePageType(this);">
			<xform:enumsDataSource  enumsType="mainData_show_relatePageType"/></xform:radio>
			</br>
			<div name="div_relatePageType_data">
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdModelNameText') }：<input type="text" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdModelNameText"  value='${sysFormMainDataShowRelateForm.fdModelNameText}'  class="inputsgl" style="width:200px;">
				<a href="#" onclick="editRelate();">${lfn:message('button.edit') }</a>
				</div>
				<div name="div_relatePageType_page">
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePage') }：<input type="text" style="width:500px;" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdRelatePage"  value='${sysFormMainDataShowRelateForm.fdRelatePage}'  class="inputsgl" />
				<br/>
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePageMobile') }：<input type="text" style="width:500px;" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdRelatePageMobile"  value='${sysFormMainDataShowRelateForm.fdRelatePageMobile}'  class="inputsgl" />
				</div>
				<div id="div_relate!{index}" name="divRelate" style="height:400px; overflow:auto;display:none;">
		 	<table class="tb_normal" width="95%">
	<!-- 排序号、所属分类 -->
	<tbody>
	<!-- 系统数据 -->
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdModelName') }
		</td>
		<td colspan="3">
			<div style="width:40%;">
				<xform:dialog subject="${lfn:message('sys-xform-maindata:sysFormMainDataShow.fdModleName') }" propertyId="sysFormMainDataShowRelateForms[${vstatus.index}].fdModelName" style="width:90%"
						propertyName="sysFormMainDataShowRelateForms[${vstatus.index}].fdModelNameText" dialogJs="XForm_selectModelNameDialog(currentIndex);">
				</xform:dialog>
			</div>
			<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fieldArray" value="${sysFormMainDataShowRelateForm.fieldArray}" />
		</td>
	</tr>
	<!-- 查询条件 -->
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdWhereBlock') }
		</td>
		<td colspan="3">
			<table class="tb_normal" style="float:left;width:95%;" name="tb_fdWhereBlock" id="xform_main_data_whereTable_relate${vstatus.index}">
				<tr class="xform_main_data_tableTitle">
					<td width="40%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.attribute') }</td>
					<td width="15%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.operator') }</td>
					<td width="35%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.value') }</td>
					<td style="width:15%;"><a href="javascript:void(0);" onclick="xform_main_data_addWhereItem(null,currentIndex);" style="color:#1b83d8;">添加</a></td>
				</tr>
			</table>
		</td>
		<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdWhereBlock" value='${sysFormMainDataShowRelateForm.fdWhereBlock }'>
	</tr>
	
	<!-- 显示字段 -->
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumn') }
		</td>
		<td colspan="3">
			<table class="tb_normal" style="float:left;width:95%;" name="tb_fdShowColumn" id="xform_main_data_showFieldsTable_relate${vstatus.index}">
				<tbody><tr class="xform_main_data_tableTitle">
					<td width="60%">${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.attribute') }</td>
					<td width="15%">别名</td>
					<td style="width:25%;"><a href="javascript:void(0);" onclick="xform_main_data_addAttrItem('',null,null,currentIndex);" style="color:#1b83d8;">添加</a></td>
				</tr>
			</tbody></table>
			<span class="txtstrong">*</span>
		</td>
		<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdShowColumn" value='${sysFormMainDataShowRelateForm.fdShowColumn }'>
	</tr>
	
	<tr name="tr_fdForeignField">
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelateType') }
		</td>
		<td colspan="3">
			<xform:select property="sysFormMainDataShowRelateForms[${vstatus.index}].fdRelateType" showPleaseSelect="false" value='${sysFormMainDataShowRelateForm.fdRelateType }'
			 onValueChange="xform_main_data_trrigleRelateType(this,'${vstatus.index}');">
				<xform:enumsDataSource enumsType="mainData_show_relateType" />
			</xform:select>
		</td>
		<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdForeignField" value='${sysFormMainDataShowRelateForm.fdForeignField }'>
		<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdMainDataRelateField" value='${sysFormMainDataShowRelateForm.fdMainDataRelateField }'>
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.viewPage') }
		</td>
			<td>
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePage') }：<input type="text" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdViewPage"  value='${sysFormMainDataShowRelateForm.fdViewPage}'  class="inputsgl" style="width:500px;"><br/>
				${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdRelatePageMobile') }：<input type="text" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdViewPageMobile"  value='${sysFormMainDataShowRelateForm.fdViewPageMobile}'  class="inputsgl" style="width:500px;">
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.mobileSetting') }
		</td>
		<td>
			<table>
			<tr><th>显示列</th><th>字段</th><th>别名</th></tr>
			<tr name="tr_fdSubjectField">
				<td>
					${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdSubjectField') }
				</td>
				<td>
					<select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="fdId" data-type="String">${lfn:message('page.firstOption') }</option>
				</select>
				</td>
				<td><input type="text" name="alias"  class="inputsgl"/></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdSubjectField"  value='${sysFormMainDataShowRelateForm.fdSubjectField}' >
			</tr>
			<tr name="tr_fdShowColumnMobile1">
				<td>
					${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumnMobile1') }
				</td>
				<td>
					<select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="" data-type="String">${lfn:message('page.firstOption') }</option>
					</select>
				</td>
				<td><input type="text" name="alias"  class="inputsgl"/></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdShowColumnMobile1"  value='${sysFormMainDataShowRelateForm.fdShowColumnMobile1}' >
			</tr>
			<tr name="tr_fdShowColumnMobile2">
				<td>
					${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumnMobile2') }
				</td>
				<td>
					<select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="" data-type="String">${lfn:message('page.firstOption') }</option>
					</select>
				</td>
				<td><input type="text" name="alias"  class="inputsgl"/></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdShowColumnMobile2"  value='${sysFormMainDataShowRelateForm.fdShowColumnMobile2}' >
			</tr>
			<tr name="tr_fdShowColumnMobile3">
				<td>
					${lfn:message('sys-xform-maindata:sysFormMainDataShowRelate.fdShowColumnMobile3') }
				</td>
				<td>
					<select name="fdAttrField" onchange="xform_main_data_trrigleFieldAttr(this,true);">
					<option value="" data-type="String">${lfn:message('page.firstOption') }</option>
					</select>
				</td>
				<td><input type="text" name="alias" class="inputsgl" /></td>
				<input type="hidden" name="sysFormMainDataShowRelateForms[${vstatus.index}].fdShowColumnMobile3"  value='${sysFormMainDataShowRelateForm.fdShowColumnMobile3}' >
			</tr>
			
			</table>
			
		</td>
	</tr>
	
</tbody></table></div>

			</td>
		
			<td>
			<%-- 
				<img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del" onclick="DocList_DeleteRow();" style="cursor:pointer">&nbsp;
			--%>
				<a href="javascript:void(0);" onclick="DocList_DeleteRow();">${lfn:message('button.delete') }</a>
				<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);">${lfn:message('dialog.moveUp') }</a>
				<a href="javascript:void(0);" onclick="DocList_MoveRow(1);">${lfn:message('dialog.moveDown') }</a>
				
			</td>
		</tr>
	</c:forEach>
</table>
		</td>
		
	<tr style="display:none;">
		<td class="td_normal_title" width="15%">
			${lfn:message('sys-xform-maindata:sysFormMainDataShow.fdShowPage') }
		</td>
			<td colspan="3">
			<xform:text property="sysFormMainDataShowRelateForms.fdShowPage" style="width:800px;"></xform:text>
		</td>
	</tr>
	
	<!-- 创建者、修改者 -->
	<c:if test="${sysFormMainDataShowForm.method_GET=='edit'}">
		<tr>
			<td class="td_normal_title" width=15--%>
				<bean:message key="model.fdCreator"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataShowForm.docCreatorName }"></c:out>				
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdCreateTime"/>
			</td>
			<td width="35%">
				<c:out value="${sysFormMainDataShowForm.docCreateTime }"></c:out>
			</td>
		</tr>
		<c:if test="${not empty sysFormMainDataShowForm.docAlterorName}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.docAlteror"/>
				</td>
				<td width="35%">
					<c:out value="${sysFormMainDataShowForm.docAlterorName }"></c:out>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.fdAlterTime"/>
				</td>
				<td width="35%">
					<c:out value="${sysFormMainDataShowForm.docAlterTime }"></c:out>
				</td>
			</tr>
		</c:if>
	</c:if>
</table>
</center>

	
</tbody></table>
</div>
		 
		 
<script>
	Com_IncludeFile("xFormMainDataShow_edit_script.js",Com_Parameter.ContextPath+'sys/xform/maindata/main_data_show/','js',true);
	//用于时间选择框
	Com_IncludeFile('calendar.js');
</script>
<script>
	var html_alias = "<input type=\"text\" name=\"alias\" value=\"$value$\" class=\"inputsgl\"/>";
	var xform_main_data_show_validation = $KMSSValidation();
	
	//自定义校验方法
	xform_main_data_show_validation.addValidator('myAlphanum','${lfn:message("sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring") }',function(v, e, o){
		return this.getValidator('isEmpty').test(v) || !/\W/.test(v);
	});
	
	//属性的数据字典
	var xform_main_data_show_dictData;
	
	// 上下移功能模板HTML
	var xform_main_data_insystem_sortTempHtml = "<a href='javascript:void(0);' onclick='xform_main_data_moveTr(-1,this);' style='color:#1b83d8;'>上移</a>";
	xform_main_data_insystem_sortTempHtml += "&nbsp;<a href='javascript:void(0);' onclick='xform_main_data_moveTr(1,this);' style='color:#1b83d8;'>下移</a>";
	
	var iconParam = {};
	var index;
	window._selectIconCallVak = function(){
		var iconSrc = iconParam.obj.value;
		if(iconSrc && iconSrc.substring(0,1) == "/"){
			iconSrc = iconSrc.substring(1,iconSrc.length);
		}else if(iconSrc==null || iconSrc==''){
			iconSrc = "sys/mobile/pda/resource/images/icon/module_default.png";
		} 
		document.getElementById('moduleIcon').src = Com_Parameter.ContextPath + iconSrc;
		document.getElementById('moduleIcon').style.display = '';
	}
	//选择图标
	function selectIcon(type,obj,_this){
		var style = "width=600,height=400,top=150,left=400, status=0,scrollbars =1, resizable=1";
		iconParam.obj = obj;
		window.open(Com_Parameter.ContextPath+"sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=selectIcon", "", style);
	}
	
	// 移动 -1：上移       1：下移
	function xform_main_data_moveTr(direct,dom){
		var tb = $(dom).closest("table")[0];
		var $tr = $(dom).closest("tr");
		var curIndex = $tr.index();
		var lastIndex = tb.rows.length - 1;
		if(direct == 1){
			if(curIndex >= lastIndex){
				alert("已经到底了！");
				return;
			}
			$tr.next().after($tr);
		}else{
			if(curIndex < 2){
				alert("已经到顶了！");
				return;
			}
			$tr.prev().before($tr);			
		}
	}
	
	//添加属性行
	function xform_main_data_addAttrItem(tableId,selectedItem,callback,index,tableObj){
		//alert(selectedItem.field);
		var fieldDatas = xform_main_data_show_dictData;
		var currentModelName;
		if(index!=null && index>-1){
			//alert("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']");
			//alert($("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']"));
			try{
				var dd = $("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']").val();
				fieldDatas = $.parseJSON(dd.replace(/&quot;/g,"\""));
				currentModelName = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdModelName']").val();
			}catch(e){
				
			}
		}else{
			var $showFieldsTr = $("#xform_main_data_showFieldsTable").find("tr:not(.xform_main_data_tableTitle)");
			if($showFieldsTr.length>=12){
				alert("显示字段最多只能配置12个");
				return;
			}
		}
		if(fieldDatas == null){
			alert("请先选择模块！");
			return;
		}
		var $selectTable;
		if(tableObj){
			$selectTable = tableObj;
		}else{
			$selectTable = $("#" + tableId);
			if(tableId && tableId!=''){
				
			}else{
				var optTR = DocListFunc_GetParentByTagName("TR");
				var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
				$selectTable = $(optTB);
			}
		}
		
		var $tr = $("<tr>");
		var html = "";
		//属性
		html += "<td>";
		html += xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',selectedItem == null ? null : selectedItem.field,selectedItem,null, currentModelName);
		html += "</td>";
		var aliasValue = "";
		if(selectedItem && selectedItem.alias){
			aliasValue = selectedItem.alias;
		}
		html += "<td><input type='text' name='alias' class=\"inputsgl\" value=\""+aliasValue+"\" /></td>";
		if(index==null){
			var checked = "";
			if(selectedItem && selectedItem.card && selectedItem.card=="true"){
				checked = "checked";
			}
			html += "<td align='center'><input name='card' type='checkbox' class=\"inputsgl\" "+checked+" /></td>";
		}
		//删除
		html += "<td><a href='javascript:void(0);' onclick='xform_main_data_delTrItem(this);' style='color:#1b83d8;'>删除</a>";
		html += "&nbsp;" + xform_main_data_insystem_sortTempHtml;
		html += "</td>";
		$tr.append(html);
		$selectTable.append($tr);
		if(callback){
			callback($tr,selectedItem);
		}
	}
	
	//增加查询条件
	function xform_main_data_addWhereItem(selectedItem,index,tableObj){
		var fieldDatas = xform_main_data_show_dictData;
		var currentModelName;
		if(index!=null && index>-1){
			//alert($("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']").val());
			var dd = $("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']").val();
			fieldDatas = $.parseJSON(dd.replace(/&quot;/g,"\""));
			currentModelName = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdModelName']").val();
		}
		
		if(fieldDatas == null || index<0){
			alert("请先选择模块！");
			return;
		}
		
		var $selectTable;
		if(tableObj){
			$selectTable = tableObj;
		}else{
			var optTR = DocListFunc_GetParentByTagName("TR");
			var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
			$selectTable = $(optTB);
		}
			
		
		//var $selectTable = $("#xform_main_data_whereTable_relate"+index);
		var $tr = $("<tr>");
		var html = "";
		//html += "<tr>";
		//属性
		html += "<td>";
		html += xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField',null ,selectedItem == null ? null : selectedItem.field,selectedItem == null ? null : selectedItem,null,currentModelName);
		html += "</td>";
		//运算符
		html += "<td>";
		if(selectedItem){
			html += xform_main_data_getOperatorOptionHtml(selectedItem.fieldType,selectedItem.fieldOperator);
		}else{
			html += xform_main_data_getOperatorOptionHtml(fieldDatas);	
		}
		
		html += "</td>";
		//值
		html += "<td>";
		if(selectedItem){
			//alert("222"+selectedItem);
			html += xform_main_data_getFieldvalueOptionHtml(selectedItem.fieldType,selectedItem,fieldDatas);	
		}else{
			html += xform_main_data_getFieldvalueOptionHtml(fieldDatas);
		}
		
		html += "</td>";
		//删除
		html += "<td><a href='javascript:void(0);' onclick='xform_main_data_delTrItem(this);' style='color:#1b83d8;'>删除</a></td>";
		//html += "</tr>";
		$tr.append(html);
		html='<tr><td>1<td><td>1<td><td>1<td><td>1<td></tr>';
		$selectTable.append($tr);	
		
	}
	
	//所属分类的弹框
	function XForm_treeDialog() {
		Dialog_Tree(false, 'docCategoryId', 'docCategoryName', ',', 
				'sysFormJdbcDataSetCategoryTreeService&parentId=!{value}', 
				'所属分类', 
				null, null, null, null, null, 
				'所属分类');
	}
	
	//弹出查询预览对话框
	function XForm_queryPreviewDialog(){
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/sys/xform/maindata/main_data_show/xFormMainDataShow_edit_queryPreviewDialog.jsp";
			var height = screen.height * 0.6;
			var width = screen.width * 0.6;
			var dialog = dialog.iframe(url,"查询预览",null,{width:width,height : height});
		});
	}
	
	//弹出系统内数据对话框
	function XForm_selectModelNameDialog(index){
		if(index==undefined){
			index = '';
		}
		window.focus();
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/sys/xform/maindata/main_data_show/xFormMainDataShow_edit_dialog.jsp?index="+index;
			var height = document.documentElement.clientHeight * 0.78;
			var width = document.documentElement.clientWidth * 0.6;
			var dialog = dialog.iframe(url,"系统数据",xform_main_data_setAttr,{width:width,height : height,close:false});
		});
	}
	
	//关闭、确定对话框的回调函数
	function xform_main_data_setAttr(value){
		if(value){
			var modelNameId = 'fdModelName';
			var modelNameTextId = 'fdModelNameText';
			var index = value.index;
			var data;
			if(value.data){
				try{
					data = $.parseJSON(value.data);
				}catch(e){
					alert("列举表数据出错，详情请看后台日志！");
				}
				if(data){
					xform_main_data_setGlobal(data,index);
					//xform_main_data_detailAuthTr(xform_main_data_show_authData);
				}
			}
			//alert(index);
			if(index && index!=''){
				modelNameId = 'sysFormMainDataShowRelateForms['+index+'].fdModelName';
				modelNameTextId = 'sysFormMainDataShowRelateForms['+index+'].fdModelNameText';
				if(value.modelName){
					$("input[name='"+modelNameId+"']").val(value.modelName);	
					//$("input[name='"+modelNameTextId+"2']").val(value.modelNameText);
				}
				if(value.modelNameText){
					$("input[name='"+modelNameTextId+"']").val(value.modelNameText);	
					//$("input[name='"+modelNameTextId+"2']").val(value.modelNameText);
				}
					xform_main_data_emptyAllTr('xform_main_data_whereTable_relate'+index); 
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdWhereBlock']").val("");	
					xform_main_data_emptyAllTr('xform_main_data_showFieldsTable_relate'+index); 
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumn']").val("");	
					var fdRelateType = $("select[name='sysFormMainDataShowRelateForms["+index+"].fdRelateType']");
					fdRelateType.val("1");
					xform_main_data_trrigleRelateType(fdRelateType.get(0),index);
					
					var fieldArray = null;
					if(data){
						fieldArray = data.fieldArray;
					}
					//xform_main_data_emptyAllTr('xform_main_data_showFieldsTable'); 
					var html = xform_main_data_getFieldOptionHtml(fieldArray,'fdAttrField','true',null,null,true);
					var html_subject = xform_main_data_getFieldOptionHtml(fieldArray,'fdAttrField','true',data.displayProp,null);
					var html_alias_this = html_alias.replace('$value$',"");
					//$("#div_fdMainDataSubjectField").html(html);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdSubjectField']").val("");
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdSubjectField']").prev().prev().html(html_subject);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdSubjectField']").prev().html(html_alias_this);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile1']").val("");
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile1']").prev().prev().html(html);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile1']").prev().html(html_alias_this);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile2']").val("");
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile2']").prev().prev().html(html);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile2']").prev().html(html_alias_this);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile3']").val("");
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile3']").prev().prev().html(html);
					$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile3']").prev().html(html_alias_this);
					
			}else{
				
							if(value.modelName){
								$("input[name='"+modelNameId+"']").val(value.modelName);	
							}
							if(value.modelNameText){
								$("input[name='"+modelNameTextId+"']").val(value.modelNameText);	
								//$("input[name='"+modelNameTextId+"2']").val(value.modelNameText);
							}
							xform_main_data_show_validation.validateElement($("input[name='"+modelNameTextId+"']")[0]);
							
								xform_main_data_emptyAllTr('xform_main_data_showFieldsTable'); 
								var html = xform_main_data_getFieldOptionHtml(xform_main_data_show_dictData,'fdAttrField','true',null,null);
								$("#div_fdMainDataSubjectField").html(html);
								$('#TABLE_DocList > tbody > tr').each(function(i){
									if(i>0){
										//$(this).remove();
										DocList_DeleteRow($(this)[0]);
									}
								}
								);
								
							
					}
				}
	}
	
	//设置权限js变量的数据字典变量和权限变量
	function xform_main_data_setGlobal(data,index){
		if(index && index!=''){
			$("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']").val(JSON.stringify(data.fieldArray));
		}else{
			xform_main_data_show_dictData = data.fieldArray;
		}
	}
	
	
	
	//权限行的显示和隐藏
	function xform_main_data_detailAuthTr(authData){
		var $tr = $(".xform_main_data_table_authTr");
		if(authData.isAuth && authData.isAuth == 'true'){
			$tr.show();
		}else{
			$tr.hide();
		}
	}
	
	Com_Parameter.event["submit"].push(xform_main_data_beforeSubmitValidate);
	
	function validate_data(){
		var validateResult = true;
		
		$('#TABLE_DocList > tbody > tr').each(function(i){
			if(i>0){
				var relatePageType = $(this).find("input[name$='fdRelatePageType']:checked").val();
				var relateDocSubject = $(this).find("input[name$='docSubject']").val();
				if(relatePageType=='data'){
					var relateModelName = $(this).find("input[name$='fdModelName']").val();
					if(!relateModelName || relateModelName == ''){
						alert("关联设置【"+relateDocSubject+"】中，需要选择系统数据");
						validateResult = false;
						return false;
					}
					var $showFieldsTr = $(this).find("table[name='tb_fdShowColumn']").find("tr:not(.xform_main_data_tableTitle)");
					if($showFieldsTr.length==0){
						alert("关联设置【"+relateDocSubject+"】中，显示字段必须配置");
						validateResult = false;
						return false;
					}
					/* var $fdShowColumnTr = $(this).find("table[name='tb_fdShowColumn']").find("tr:not(.xform_main_data_tableTitle)");
					var valueArray = [];
					for(var i = 0;i < $fdShowColumnTr.length; i++){
						var tr = $fdShowColumnTr[i];
						valueArray.push(xform_main_data_detailAttrWhere(tr,'true'));
					}
					$(this).find("input[name$='fdShowColumn']").val(JSON.stringify(valueArray)); */
				}else if(relatePageType=='page'){
					var relatePage = $(this).find("input[name$='fdRelatePage']").val();
					var relatePageMoblie = $(this).find("input[name$='fdRelatePageMobile']").val();
					
					if((relatePage == '')&&(relatePageMoblie == '')){
						alert("关联设置【"+relateDocSubject+"】中，需要设置PC链接或移动链接");
						validateResult = false;
						return false;
					}
				}
			}
		}
		);
		return validateResult;
	}
	
	//提交前校验
	function xform_main_data_beforeSubmitValidate(){
			
		if(!validate_data()){
			return false;
		}
				
		var dataJSON = xform_main_data_detailWithAllData(true);
		if(dataJSON==false){
			return false;
		}
		if(!xform_main_data_validateTr(dataJSON)){
			return false;
		}
		return true;
	}
	
	function xform_main_data_validateTr(dataJSON){
		//var selectArray = dataJSON.select;
		//var searchArray = dataJSON.search;
		var showFields = dataJSON.showFields;
		
		/* if(xform_main_data_fieldIsUnique(selectArray) == false){
			alert("查询条件不能有重复的选项!");
			return false;
		}
		
		if(xform_main_data_fieldIsUnique(showFields) == false){
			alert("显示字段不能有重复的选项!");
			return false;
		} */
			
		//返回值不能为空
		if(showFields == null || showFields.length == 0){
			alert("显示字段不能为空！");
			return false;
		}
		
		return true;
	}
	
	// 判断数组里面是否有重复数据
	function xform_main_data_fieldIsUnique(array){
		if(array && array.length > 0){
			var hash = {};
			for(var i = 0;i < array.length;i++){
				if(!hash[array[i].field]){
					hash[array[i].field] = true;
				}else{
					return false;
				}
			}
		}
		return true;
	}
	
	function xform_main_data_detailWithAllData(isSetValue){
		var dataJSON = {};
		
		//处理显示字段
		var $showFieldsTr = $("#xform_main_data_showFieldsTable").find("tr:not(.xform_main_data_tableTitle)");
		var valueArray = [];
		for(var i = 0;i < $showFieldsTr.length; i++){
			var tr = $showFieldsTr[i];
			valueArray.push(xform_main_data_detailAttrWhere(tr,'true'));
		}
		dataJSON.showFields = valueArray;
		
		var $subjectFieldTr = $("#tr_fdMainDataSubjectField");
		var subjectField = xform_main_data_detailAttrWhere($subjectFieldTr,'true');
		dataJSON.subjectField = subjectField;
		
		if(isSetValue && isSetValue == true){
			//$("input[name='fdWhereBlock']").val(JSON.stringify(selectArray));
			$("input[name='fdShowFields']").val(JSON.stringify(valueArray));
			$("input[name='fdMainDataSubjectField']").val(JSON.stringify(subjectField));
			
			$('#TABLE_DocList > tbody > tr').each(function(i){
				if(i>0){
					
				//处理显示字段
					var $fdShowColumnTr = $(this).find("table[name='tb_fdShowColumn']").find("tr:not(.xform_main_data_tableTitle)");
					var valueArray = [];
					for(var i = 0;i < $fdShowColumnTr.length; i++){
						var tr = $fdShowColumnTr[i];
						valueArray.push(xform_main_data_detailAttrWhere(tr,'true'));
					}
					$(this).find("input[name$='fdShowColumn']").val(JSON.stringify(valueArray));
					
					//处理查询条件
					var $selectTr = $(this).find("table[name='tb_fdWhereBlock']").find("tr:not(.xform_main_data_tableTitle)");
					var selectArray = [];
					for(var i = 0;i < $selectTr.length; i++){
						var tr = $selectTr[i];
						selectArray.push(xform_main_data_detailSelectWhere(tr));
					}
					$(this).find("input[name$='fdWhereBlock']").val(JSON.stringify(selectArray));
					
					//处理外键字段
					var $foreignTr = $(this).find("tr[name='tr_fdForeignField']");
					var valueJSON;
					for(var i = 0;i < $foreignTr.length; i++){
						var tr = $foreignTr[i];
						var div_foreignField = $(tr).find("div[name='div_foreignField']");
						valueJSON = xform_main_data_detailAttrWhere(div_foreignField,null);
					}
					$(this).find("input[name$='fdForeignField']").val(JSON.stringify(valueJSON));
					
					//处理外键关联字段
					for(var i = 0;i < $foreignTr.length; i++){
						var tr = $foreignTr[i];
						var div_relateField = $(tr).find("div[name='div_relateField']");
						valueJSON = xform_main_data_detailAttrWhere(div_relateField,null);
					}
					$(this).find("input[name$='fdMainDataRelateField']").val(JSON.stringify(valueJSON));
					//debugger;
					var $fdSubjectField = $(this).find("tr[name='tr_fdSubjectField']");
					var sf = xform_main_data_detailAttrWhere($fdSubjectField,'true');
					$(this).find("input[name$='fdSubjectField']").val(JSON.stringify(sf));
					
					var $fdShowColumnMobile1 = $(this).find("tr[name='tr_fdShowColumnMobile1']");
					var c1 = xform_main_data_detailAttrWhere($fdShowColumnMobile1,'true');
					$(this).find("input[name$='fdShowColumnMobile1']").val(JSON.stringify(c1));
					
					var $fdShowColumnMobile2 = $(this).find("tr[name='tr_fdShowColumnMobile2']");
					var c2 = xform_main_data_detailAttrWhere($fdShowColumnMobile2,'true');
					$(this).find("input[name$='fdShowColumnMobile2']").val(JSON.stringify(c2));
					
					var $fdShowColumnMobile3 = $(this).find("tr[name='tr_fdShowColumnMobile3']");
					var c3 = xform_main_data_detailAttrWhere($fdShowColumnMobile3,'true');
					$(this).find("input[name$='fdShowColumnMobile3']").val(JSON.stringify(c3));
				 }
			}
					
		  );
			
		}
		return dataJSON;
	}
	
	//提交
	function xform_main_data_submit(method){
		Com_Submit(document.sysFormMainDataShowForm, method);
	}
	
	Com_AddEventListener(window,'load',xform_main_data_initVar);
	
	//初始化数据，主要用于edit编辑页面
	function xform_main_data_initVar(){
		var fdModelName = $("input[name='fdModelName']").val();
		var fdModelNameText = $("input[name='fdModelNameText']").val();
		if(fdModelName != "" && fdModelNameText != ""){
			//初始化数据字典变量
			var dictData = "${sysFormMainDataShowForm.modelDict}";
			dictData = dictData.replace(/&quot;/g,"\"");
			xform_main_data_setGlobal($.parseJSON(dictData));
			
			//处理显示字段
			var selectData = '${sysFormMainDataShowForm.fdShowFields}';
			if(selectData){
				var selectDataJsonArray = $.parseJSON(selectData);
				for(var i = 0;i < selectDataJsonArray.length;i++){
					if($.isEmptyObject(selectDataJsonArray[i])){
						continue;
					}
					xform_main_data_addAttrItem('xform_main_data_showFieldsTable',selectDataJsonArray[i],xform_main_data_custom_initEnum,null);
				}
			}
			// 标题列处理
			var subjectField = '${sysFormMainDataShowForm.fdMainDataSubjectField}';
			var subjectFieldValue = null;
			var subjectFieldJson = null;
			if(subjectField){
				var subjectFieldJson = $.parseJSON(subjectField);
				var subjectFieldValue = subjectFieldJson.field;
			}
			var html = xform_main_data_getFieldOptionHtml(xform_main_data_show_dictData,'fdAttrField','true',subjectFieldValue,subjectFieldJson);
			$("#div_fdMainDataSubjectField").html(html);
			
		}
		xform_main_data_custom_enumChangeEvent("xform_main_data_showFieldsTable");
		xform_main_data_custom_enumChangeEvent("xform_main_data_searchTable");
		
		$(".color_ul li.select a").css("background-color", "${sysFormMainDataShowForm.fdColor}");
		
		xform_main_data_show_initVar();
	}
	
	function xform_main_data_show_initVar(){
		
		var table = $("#TABLE_DocList");
		$('#TABLE_DocList > tbody > tr').each(function(i){
				 if(i>0){
					 
				 var index = i-1;
				 //alert("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumn']");
				 //处理关联页面模式
					var relatePageType = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdRelatePageType']:checked");
					change_relatePageType(relatePageType.get(0));
					
				 var modleNameTextObj = $(this).find("input[name='sysFormMainDataShowRelateForms["+index+"].fdModelNameText']").get(0);
				 var currentModelName = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdModelName']").val();
				 
				 /* var fdModelName = modleNameObj.val();
				 var fdModelNameText = modleNameTextObj.val();
				 if(fdModelName != "" && fdModelNameText != ""){
					 
				 } */
				 //alert($("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumn']").get(0));
				//处理显示字段
					var fdShowColumn = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumn']").val();
					if(fdShowColumn){
						var selectDataJsonArray = $.parseJSON(fdShowColumn);
						//遍历查询条件数据
						for(var i = 0;i < selectDataJsonArray.length;i++){
						
							if($.isEmptyObject(selectDataJsonArray[i])){
								continue;
							}
							var tableObj = $(this).find("table[name='tb_fdShowColumn']");
							xform_main_data_addAttrItem('',selectDataJsonArray[i],xform_main_data_custom_initEnum,index,tableObj);
						}
					}
					
					//处理查询条件
					var whereData = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdWhereBlock']").val();
					if(whereData){
						var whereDataJsonArray = $.parseJSON(whereData);
						//遍历查询条件数据
						for(var j = 0;j < whereDataJsonArray.length;j++){
							if($.isEmptyObject(whereDataJsonArray[j])){
								continue;
							}
							var tableObj = $(this).find("table[name='tb_fdWhereBlock']");
							xform_main_data_addWhereItem(whereDataJsonArray[j],index,tableObj);
						}
					}
					var dd = $("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']").val();
					if(dd){
						
					fieldDatas = $.parseJSON(dd.replace(/&quot;/g,"\""));
					
					//处理外键
					var relateType = $("select[name='sysFormMainDataShowRelateForms["+index+"].fdRelateType']").val();
					//alert("relateType:"+relateType);
					if(relateType=='2'){
						var foreignField = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdForeignField']").val();
						var relateField = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdMainDataRelateField']").val();
						//alert(foreignField);
						var foreignFieldJson = $.parseJSON(foreignField);
						var relateFieldJson;
						if(!relateField){
							relateField = '{"field":"fdId","fieldType":"String"}';
						}
						var relateFieldJson = $.parseJSON(relateField);
							if(!$.isEmptyObject(foreignFieldJson)){
								
								var mainModelName = $("input[name='fdModelName']").val();
								//var html = xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',foreignFieldJson.field,foreignFieldJson,null,currentModelName);
								var html = xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',foreignFieldJson.field,foreignFieldJson,null,currentModelName);
								var modelNameText = $("input[name='fdModelNameText']").val();
								var modelNameTextRelate = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdModelNameText']").val();
								html = "<br/>"+"<div name='div_foreignField' style='display:inline;'>"+modelNameTextRelate+html+"</div> <div style='display:inline;'>&nbsp;&nbsp;等于&nbsp;&nbsp;</div> "+"<div name='div_relateField' style='display:inline;'>"+modelNameText+xform_main_data_getFieldOptionHtml(xform_main_data_show_dictData,'fdAttrField','true',relateFieldJson.field,relateFieldJson,null,mainModelName)+"</div>";
								$("select[name='sysFormMainDataShowRelateForms["+index+"].fdRelateType']").after(html);
							}
					}
					
					//处理移动页面设置
					var subjectField = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdSubjectField']").val();
					//alert("subjectField:"+subjectField);
					//debugger;
					if(subjectField){
						var subjectFieldObj = $.parseJSON(subjectField);
						var html_subject = xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',subjectFieldObj.field,subjectFieldObj,null,currentModelName);
						$("input[name='sysFormMainDataShowRelateForms["+index+"].fdSubjectField']").prev().prev().html(html_subject+"<span class='txtstrong'>*</span>");
						if(subjectFieldObj.alias){
							$("input[name='sysFormMainDataShowRelateForms["+index+"].fdSubjectField']").prev().html(html_alias.replace('$value$',subjectFieldObj.alias));
						}
					}
					//alert(fieldDatas);
					var fdShowColumnMobile1 = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile1']").val();
					if(fdShowColumnMobile1){
						var fdShowColumnMobile1Obj = $.parseJSON(fdShowColumnMobile1);
						var html1= xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',fdShowColumnMobile1Obj.field,fdShowColumnMobile1Obj,true,currentModelName);
						//alert(currentModelName+"---"+fdShowColumnMobile1+"---"+html1);
						$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile1']").prev().prev().html(html1);
						if(fdShowColumnMobile1Obj.alias){
							$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile1']").prev().html(html_alias.replace('$value$',fdShowColumnMobile1Obj.alias));
						}
					}
					
					var fdShowColumnMobile2 = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile2']").val();
					if(fdShowColumnMobile2){
						var fdShowColumnMobile2Obj = $.parseJSON(fdShowColumnMobile2);
						var html2= xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',fdShowColumnMobile2Obj.field,fdShowColumnMobile2Obj,true,currentModelName);
						$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile2']").prev().prev().html(html2);
						if(fdShowColumnMobile2Obj.alias){
							$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile2']").prev().html(html_alias.replace('$value$',fdShowColumnMobile2Obj.alias));
						}
					}
					
					var fdShowColumnMobile3 = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile3']").val();
					if(fdShowColumnMobile3){
						var fdShowColumnMobile3Obj = $.parseJSON(fdShowColumnMobile3);
						var html3= xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',fdShowColumnMobile3Obj.field,fdShowColumnMobile3Obj,true,currentModelName);
						$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile3']").prev().prev().html(html3);
						if(fdShowColumnMobile3Obj.alias){
							$("input[name='sysFormMainDataShowRelateForms["+index+"].fdShowColumnMobile3']").prev().html(html_alias.replace('$value$',fdShowColumnMobile3Obj.alias));
						}
						} 
					}
					
				 }
		  });
		
		
		//xform_main_data_custom_enumChangeEvent("xform_main_data_showFieldsTable");
		//xform_main_data_custom_enumChangeEvent("TABLE_DocList");
	}
	
	function xform_main_data_custom_enumChangeEvent(tableId){
		$("#" + tableId).delegate("select[name='fdAttrField']","change",function(event){
			var option = $(this).find("option:selected");
			if(option.attr('data-type') == 'enum'){
				//构建选项
				var suffix = tableId.substring(tableId.lastIndexOf('_') + 1) + "_" + ++_xform_main_data_show_radioNameNum;
				$(this).after(xform_main_data_custom_buildEnumHtml(suffix));
			}
			xform_main_data_custome_stopBubble(event);
		});
	}
	
	
	function FS_AddProjectDetailNew(){
		var newrow = DocList_AddRow("TABLE_DocList");
		//var rowIndex=$(newrow)[0].rowIndex;
		//$('#fdOOrder'+rowIndex).html(rowIndex);
		//$(newrow).bind('click',customClickRow(newrow,"add",$("#TABLE_Project tr:first")));
		//DocList_DeleteRow(newrow);
	}
	
	function editRelate(index){
		//$("#calendar_view_btn").show();
		//$("#calendar_view_btn").css(getPos($("#calendar_view_btn")));
		var optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		currentIndex = getIndex(optTB.rows, optTR)-1;
		//var optTR = DocListFunc_GetParentByTagName("TR");
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			//var obj = $("#div_relate"+index);
			//obj = document.getElementById("div_relate"+index);
			var obj = $(optTR).find("div[name='divRelate']").get(0);
			//var obj = optTR.getElementsByName("divRelate")[0];
			if (obj != null) {
				if (obj.style.display == "none") {
					dialog.build( {
						config : {
							width:800,
							height:400,
							lock : true,
							cache : true,
							close: false,
							content : {
								type : "element",
								elem : obj,
								scroll : true,
								buttons : [ {
									name : "确定",
									value : true,
									focus : true,
									fn : function(value,dialogFr) {
										dialogFr.hide();
									}
								}]
							},
							title : "关联设置"
						},

						callback : function(value, dialog) {
							currentIndex = null;
							$(obj).undelegate("select[name='fdAttrField']","change");
						},
						actor : {
							type : "default"
						},
						trigger : {
							type : "default"
						}
					}).show();
					//xform_main_data_custom_enumChangeEvent("TABLE_DocList");
					$(obj).delegate("select[name='fdAttrField']","change",function(event){
						var option = $(this).find("option:selected");
						if(option.attr('data-type') == 'enum'){
							//构建选项
							var suffix = option.val() + "_" + ++_xform_main_data_show_radioNameNum;
							$(this).after(xform_main_data_custom_buildEnumHtml(suffix));
						}
						xform_main_data_custome_stopBubble(event);
					});
					$('.lui_dialog_head_right').hide();
				}
			}
		});
	}
	
	var getPos=function(showObj){
		var sWidth=showObj.width(),
			sHeight=showObj.height();
		y = ($(window).height()-sHeight)/2;
		x = ($(window).width()-sWidth)/2;
		
		return {"top":y,"left":x};
	};
	
	function getRowIndex(){
		var optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		optTR = DocListFunc_GetParentByTagName("TR",optTB);
		optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var rowIndex = getIndex(optTB.rows, optTR);
		return rowIndex-1;
	}
	
	function getRowIndex2(){
		var optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		optTR = DocListFunc_GetParentByTagName("TR",optTB);
		optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		optTR = DocListFunc_GetParentByTagName("TR",optTB);
		optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var rowIndex = getIndex(optTB.rows, optTR);
		return rowIndex-1;
	}
	
	function getIndex(arr, key){
		for(var i=0; i<arr.length; i++)
			if(arr[i]==key)
				return i;
		return -1;
	}
	
	var currentIndex = -1;
	
	function xform_main_data_trrigleRelateType(dom,index){
		var selectDom = dom;
		var type = $(selectDom).find("option:selected").val();
		
		// 如果属性是对象类型
		if(type=="2"){
			//删除当前元素后面的同级元素
			$(selectDom).nextAll().remove();
			var dd = $("input[name='sysFormMainDataShowRelateForms["+index+"].fieldArray']").val();
			if(dd==''){
				alert("请先选择模块！");
				$(selectDom).val("1");
				return;
			}
			fieldDatas = $.parseJSON(dd.replace(/&quot;/g,"\""));
			var html = xform_main_data_getFieldOptionHtml(fieldDatas,'fdAttrField','true',null);
			var modelNameText = $("input[name='fdModelNameText']").val();
			var modelNameTextRelate = $("input[name='sysFormMainDataShowRelateForms["+index+"].fdModelNameText']").val();
			html = "<br/>"+"<div name='div_foreignField' style='display:inline;'>"+modelNameTextRelate+html+"</div> <div style='display:inline;'>&nbsp;&nbsp;等于&nbsp;&nbsp;</div> "+"<div name='div_relateField' style='display:inline;'>"+modelNameText+xform_main_data_getFieldOptionHtml(xform_main_data_show_dictData,'fdAttrField','true',null)+"</div>";
			
			
			$(selectDom).after(html);
			
			/* xform_main_data_getDictAttrByModelName(type,function(data){
				if(data){
					var dataJSON = $.parseJSON(data);
					if(dataJSON.fieldArray){
						var selectHtml = xform_main_data_getFieldOptionHtml(dataJSON.fieldArray,"fdAttrField");
						$(selectDom).after(selectHtml);	
						typeChange = false;
						//$(selectHtml).change(); 
						//不能用上面这种方式触发，因为此时的$(selectHtml)并不存在于dom结构里面，会导致下面的获取不到父元素
						$(selectDom).next().change();					
					}
				}
			}); */
		}else{
			//删除当前元素后面的同级元素
			$(selectDom).nextAll().remove();
		}
	} 
	
	function SysIconDialog(dom) {
		seajs.use(['lui/dialog','lui/jquery'], function(dialog, $){
			dialog.build({
				config : {
						width : 500,
						height : 400,  
						title : "图标设置",
						close : true,
						content : {  
							type : "iframe",
							url : "/sys/ui/jsp/icon.jsp?type=l&status=false"
						}
				},
				callback : function(value, dia) {
					if(value==null){
						return ;
					}
					$(dom).removeClass().addClass("lui_icon_l").addClass(value).
									closest("td").find('[name$="fdMainDataIcon"]').val(value);
				}
			}).show(); 
		});
	}
	
	function change_relatePageType(dom){
		var type = $(dom).val();
		if(type=='data'){
			$(dom).parent().parent().find("div[name='div_relatePageType_data']").show();
			$(dom).parent().parent().find("div[name='div_relatePageType_page']").hide();
		}else if(type=='page'){
			$(dom).parent().parent().find("div[name='div_relatePageType_data']").hide();
			$(dom).parent().parent().find("div[name='div_relatePageType_page']").show();
		}
	}
	
	function colorClick(color_li){
		var color = $(color_li).css("background-color");
        $(".color_ul li.select a").css("background-color", color);
        $("#fdColor").val(color);
	}
	
	
</script>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>