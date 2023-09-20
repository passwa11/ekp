<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>

<%@ page
		import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<c:set var="kmsKnowledgeCategoryFdName" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdName') }"></c:set>
<c:set var="kmsKnowledgeCategoryFdParent" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdParent') }"></c:set>
<c:set var="kmsKnowledgeCategoryTip" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.categroy.template.parent.tip') }"></c:set>
<c:set var="tempReader" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.description.main.tempReader.allUse') }"></c:set>
<c:set var="kmsKnowledgeCategoryToDoc" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.apply.to.doc') }"></c:set>
<c:set var="kmsKnowledgeCategorySubSet" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.sub.set') }"></c:set>
<c:set var="kmsKnowledgeCategorySubSetDoc" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.sub.set.doc') }"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig3 = new KmsCategoryConfig();
		String kmsCategoryEnabled3 = (String) kmsCategoryConfig3.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled3)) {
	%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>
	<c:set var="kmsKnowledgeCategoryFdName" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdName.categoryTrue') }"></c:set>
	<c:set var="kmsKnowledgeCategoryFdParent" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdParent.categoryTrue') }"></c:set>
	<c:set var="kmsKnowledgeCategoryTip" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.categroy.template.parent.tip.categoryTrue') }"></c:set>
	<c:set var="kmsKnowledgeCategoryToDoc" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.apply.to.doc.categoryTrue') }"></c:set>
	<c:set var="tempReader" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.description.main.tempReader.allUse.categoryTrue') }"></c:set>
	<c:set var="kmsKnowledgeCategorySubSet" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.sub.set.categoryTrue') }"></c:set>
	<c:set var="kmsKnowledgeCategorySubSetDoc" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.sub.set.doc.categoryTrue') }"></c:set>

	<%
		}
	%>
</c:if>
<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
<% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
<!-- （为空则本组织人员可使用） -->
<c:set var="tempReader" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.description.main.tempReader1.organizationUse') }"></c:set>
<% } else { %>
<!-- （为空则所有内部人员可使用） -->
<c:set var="tempReader" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.description.main.tempReader.allUse.categoryTrue') }"></c:set>
<% } %>
<% } else { %>
<!-- （为空则所有人可使用） -->
<c:set var="tempReader" value="${lfn:message('kms-knowledge:kmsKnowledgeCategory.description.main.tempReader2.nonOrganizationAllUse') }"></c:set>
<% } %>
<%
	String fdModelName = request.getParameter("fdModelName");
	Set propertyNameSet =  SysDataDict.getInstance().getModel(fdModelName).getPropertyMap().keySet();
%>
<style>
	input::-ms-clear, input::-ms-reveal{
		display: none;
	}
</style>
<c:set var="sysSimpleCategoryMain" value="${requestScope[param.formName]}" />
<c:set var="selectEmpty" value="true" />
<kmss:auth
		requestURL="${JsParam.requestURL}"
		requestMethod="Get">
	<c:set var="selectEmpty" value="false" />
</kmss:auth>
<tr>
	<td class="td_normal_title" width=15%>${kmsKnowledgeCategoryFdParent }</td>
	<td colspan="3"><html:hidden property="fdParentId" />
		<input type="text" value="<c:out value='${sysSimpleCategoryMain.fdParentName}'/>"
			   name="fdParentName" readonly="readonly" style="width:90%" class="inputsgl" unselectable="on">
		<c:if test="${empty JsParam.fdCopyId ||  param.fdModelName != 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory'}">
			<a href="#"
			   onclick="Dialog_SimpleCategory_Bak('${JsParam.fdModelName}','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}','${JsParam.titleKey}');Cate_getParentMaintainer();"
			>
				<bean:message key="dialog.selectOther" /> </a>
		</c:if>
	</td>
</tr>
<tr>
	<td class="td_normal_title" width=15%>${kmsKnowledgeCategoryFdName }</td>
	<td colspan="3"><xform:text property="fdName" required="true" validators="maxLength(200)" style="width:90%" /></td>
</tr>
<tr>
	<td class="td_normal_title" width=15%>${lfn:message('kms-knowledge:kmsKnowledgeCategory.fdDescription') }</td>
	<td colspan="3"><xform:textarea property="fdDesc" required="false" validators="maxLength(1500)" style="width:90%" /></td>
</tr>
<c:if test="${kms_professional}">
	<c:import url="/kms/common/resource/ui/sysPropertyTemplate_select.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="${JsParam.formName}" />
		<c:param name="mainModelName" value="${JsParam.mainModelName}" />
	</c:import>
</c:if>
<%-- <tr>
    <td
        class="td_normal_title"
        width="15%"><bean:message
        bundle="kms-knowledge"
        key="kmsKnowledgeCategory.fdNumberPrefix" /></td>
    <td colspan=3>
        <xform:text property="fdNumberPrefix" style="width:90%"  validators="maxLength(10)"/>
    </td>
 </tr> --%>
<% if(propertyNameSet.contains("fdTemplateType")){ %>
<tr>
	<td class="td_normal_title" width=15%><bean:message
			bundle="kms-knowledge" key="kmsKnowledgeCategory.fdTemplateType" /></td>
	<td colspan="3">
		<xform:checkbox property="fdTemplateType" onValueChange="settingTemplates" validators="checkType">
			<xform:customizeDataSource
					className="com.landray.kmss.kms.knowledge.service.spring.KmsKnowledgeTemplateService">
			</xform:customizeDataSource>
		</xform:checkbox>
		<c:if test="${param.fdModelName == 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory'}">
			<span style="padding-left:8px;">(${kmsKnowledgeCategoryTip})</span>
		</c:if>
	</td>
</tr>
<%} %>
<% if(propertyNameSet.contains("docProperties")){ %>
<tr>
	<td class="td_normal_title" width=15%><bean:message
			bundle="sys-category" key="menu.sysCategory.property" /></td>
	<td colspan="3"><html:hidden property="docPropertyIds" />
		<html:text
				property="docPropertyNames"
				readonly="true"
				styleClass="inputsgl"
				style="width:90%" />
		<a href="#"
		   onclick="Dialog_property(true, 'docPropertyIds','docPropertyNames', ';',ORG_TYPE_PERSON);">
			<bean:message key="dialog.selectOther" /></a>
	</td>
</tr>
<%} %>

<% if(propertyNameSet.contains("authArea") && ISysAuthConstant.IS_AREA_ENABLED){ %>
<%-- 所属场所 --%>
<td class="td_normal_title" width="15%">
	<bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
</td>
<td colspan="3">
	<html:hidden property="authAreaId" />
	<c:out value="${sysSimpleCategoryMain.authAreaName}" />
</td>
<%} %>

<tr>
	<td class="td_normal_title" width=15%><bean:message
			key="model.fdOrder" /></td>
	<td colspan="3"><html:text property="fdOrder" onchange="checkIsNum()"/></td>
</tr>

<tr>
	<td class="td_normal_title" width=15%><bean:message bundle="sys-simplecategory"
														key="sysSimpleCategory.parentMaintainer" /></td>
	<td colspan="3" id="parentMaintainerId">${parentMaintainer}</td>
</tr>

<tr>
	<td class="td_normal_title" width=15%><bean:message
			key="model.tempEditorName" /></td>
	<td colspan="3"><html:hidden  property="authEditorIds"/>
		<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:97%;height:90px;" ></xform:address>
		<div class="description_txt">
			${tempEditor }
		</div>
	</td>
</tr>

<tr>
	<td class="td_normal_title" width=15%><bean:message
			key="model.tempReaderName" /></td>
	<td colspan="3">
		<input type="checkbox" name="authNotReaderFlag" value="${sysSimpleCategoryMain.authNotReaderFlag}" onclick="Cate_CheckNotReaderFlag(this);"
			   <c:if test="${sysSimpleCategoryMain.authNotReaderFlag eq 'true'}">checked</c:if>>
		<bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
		<div id="Cate_AllUserId">
			<html:hidden  property="authReaderIds"/>
			<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:97%;height:90px;" ></xform:address>
		</div>
		<div id="Cate_AllUserNote">
			${tempReader }
		</div>
	</td>
</tr>
<tr style="display:none">
	<td class="td_normal_title" width=15%><bean:message
			bundle="sys-simplecategory" key="sysSimpleCategory.fdIsinheritMaintainer" /></td>
	<td width=35%>
		<sunbor:enums property="fdIsinheritMaintainer" enumsType="common_yesno" elementType="radio" />
	</td>
	<td class="td_normal_title" width=15%><bean:message
			bundle="sys-simplecategory" key="sysSimpleCategory.fdIsinheritUser" /></td>
	<td width=35%>
		<sunbor:enums property="fdIsinheritUser" enumsType="common_yesno" elementType="radio" />
	</td>
</tr>
<c:if test="${sysSimpleCategoryMain.method_GET!='add'}">
	<tr>
		<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreator" /></td>
		<td width=35%><bean:write name="sysSimpleCategoryMain" property="docCreatorName"/></td>
		<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreateTime" /></td>
		<td width=35%><bean:write name="sysSimpleCategoryMain" property="docCreateTime"/></td>
	</tr>
	<c:if test="${sysSimpleCategoryMain.docAlterorName!=''}">
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					key="model.docAlteror" /></td>
			<td width=35%><bean:write name="sysSimpleCategoryMain" property="docAlterorName"/></td>
			<td class="td_normal_title" width=15%><bean:message
					key="model.fdAlterTime" /></td>
			<td width=35%><bean:write name="sysSimpleCategoryMain" property="docAlterTime"/></td>
		</tr>
	</c:if>
</c:if>

<c:if test="${sysSimpleCategoryMain.method_GET!='add' }">
	<tr>
		<td
				class="td_normal_title"
				width="15%"> <bean:message bundle="kms-knowledge" key="kmsKnowledgeCategory.apply.to" /></td>
		<td colspan=3>
			<input type='checkbox' name="appToMyDoc"  value='appToMyDoc'/> ${kmsKnowledgeCategoryToDoc }
			<input type='checkbox' name="appToChildren"  value='appToChildren'/> ${kmsKnowledgeCategorySubSet }
			<input type='checkbox' name="appToChildrenDoc"  value='appToChildrenDoc'/> ${kmsKnowledgeCategorySubSetDoc }
		</td>
	</tr>
</c:if>

<script>
	function Cate_CheckNotReaderFlag(el){
		document.getElementById("Cate_AllUserId").style.display=el.checked?"none":"";
		document.getElementById("Cate_AllUserNote").style.display=el.checked?"none":"";
		el.value=el.checked;
	}

	function Cate_Win_Onload(){
		Cate_CheckNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
	}

	Com_AddEventListener(window, "load", Cate_Win_Onload);

	Com_IncludeFile("dialog.js");
	function checkParentId(){
		var formObj = document.${JsParam.formName};
		if(formObj.fdParentId.value!="" && formObj.fdParentId.value==formObj.fdId.value){
			alert("<bean:message bundle="sys-simplecategory" key="error.illegalSelected" />");
			return false;
		}else
			return true;
	}

	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = checkParentId;

	Com_IncludeFile("jquery.js", null, "js");

	function Cate_getParentMaintainer(){
		var requestURL = "${JsParam.requestURL}";

		requestURL = requestURL.substring(0,requestURL.indexOf("?"));
		var url = Com_Parameter.ContextPath + requestURL + "?method=getParentMaintainer";
		jQuery.ajax({
			url: url,
			type: 'get',
			dataType: 'html',
			data: {
				'parentId': document.getElementsByName("fdParentId")[0].value
			},
			success: function(data, textStatus, xhr) {
				$(document.getElementById("parentMaintainerId")).text(xhr.responseText);
			},
			error: function(xhr, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
	}

	function checkIsNum() {
		var fdvalue=document.getElementsByName("fdOrder")[0].value;
		var fdShowWorning=false;
		if(fdvalue!="") {
			if(isNaN(fdvalue)||Number(fdvalue)<0||parseInt(fdvalue, 10)!=fdvalue) {
				fdShowWorning=true;
			}
		}
		if(fdShowWorning) {
			alert("<bean:message bundle='kms-common' key='category.orderValidate'/>");
		}
	}

	function addCategory(){
		if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
			seajs.use(['lui/dialog'],function(dialog){
				dialog.simpleCategory('${JsParam.fdModelName}','fdParentId','fdParentName',false,null,null,true,null,false);
			})
		}else{
			Dialog_SimpleCategory_Bak('${JsParam.fdModelName}','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}','${JsParam.titleKey}');
		}
	}

</script>
