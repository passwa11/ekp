<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="enableFlow" value="${enableFlow }"/>
<%
	String formName = (String)request.getAttribute("formName");
	if(StringUtil.isNotNull(formName)){
		pageContext.setAttribute("formName", formName);
	}

	if("false".equals(request.getAttribute("enableFlow"))){
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain");
	}else{
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
	}
%>
<script>
	Com_IncludeFile("print.css","${KMSS_Parameter_ContextPath}sys/modeling/base/print/css/",'css',true);
</script>
<style>
	#printTemplate{
		overflow-x: scroll !important;
	}
	.subform_panel_normal{
	    width: 80%;
	    margin: 2px 5px;
	    padding: 1px 5px;
	}
	.subform_panel_mouseover{
	    width: 80%;
	    margin: 2px 5px;
	    padding: 1px 5px;
	    cursor: pointer;
	    border: 1px solid #ccc;
	}
	<c:if test="${_isHide eq 'true' }">
		#SubForm_Print_table > .tb_normal {
		    border: 0;
		}
		#SubForm_Print_table > .tb_normal > tbody > tr {
		    border: 0;
		}
		#SubForm_Print_table > .tb_normal > tbody > tr > td {
		    border: 0;
		}
	</c:if>
</style>
<div class="print">
	<c:if test="${_isHide eq 'true' }">
		<div class="print-head">
			<div class="print-head-btn">
				<div class="head-desc">打印说明：表单中新增控件、删除控件或控件配置发生变化时，打印模板中对应内容需重新配置</div>
			</div>
		</div>
	</c:if>
	<div>
		<c:import url="/sys/print/include/multi_template/sysPrintTemplate_edit_content.jsp">
			<c:param name="formName" value="${formName }"></c:param>
			<c:param name="fdKey" value="modelingApp"></c:param>
			<c:param name="modelName" value="${modelName }"></c:param>
			<c:param name="templateModelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppModel"></c:param>
			<c:param name="_isHide" value="${_isHide }"></c:param>
			<c:param name="_isHidePrintDesc" value="${_isHidePrintDesc }"></c:param>
			<c:param name="_isHideDefaultSetting" value="${_isHideDefaultSetting }"></c:param>
			<c:param name="_isShowName" value="${_isShowName }"></c:param>
			<c:param name="_isHidePrintMode" value="${_isHidePrintMode }"></c:param>
			<c:param name="_isXForm" value="true"></c:param>
			<c:param name="enableFlow" value='${enableFlow }'></c:param>
		</c:import>
	</div>
</div>
