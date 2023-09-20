<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<link href="<%=request.getContextPath() %>/third/ctrip/xform/resource/mobile/thirdCtripXformMobile.css" type="text/css" rel="stylesheet" />

<style>
	#third_ctrip_${param.controlId}_view .mblScrollableViewContainer{
		height:100%;
	}
</style>
<% 
	//form处理 start
	String formBeanName = request.getParameter("formName");
	IExtendForm mainForm = (IExtendForm)request.getAttribute(formBeanName);
	//获取modelname
	String thirdCtripModelName = mainForm.getModelClass().getName();
	String mainModelStatus = TagUtils.getDocStatus(request);
	//System.out.println(mainModelStatus);
%>
<xform:editShow>
	<div style="display:inline;" data-dojo-type="third/ctrip/xform/resource/mobile/js/Ctrip" 
		data-dojo-props="showStatus:'edit',controlId:'${param.controlId}',mainDocStatus:'<%=mainModelStatus.toString()%>',ticketType:'hotel|plane',bookTypeControlId:'${param.bookTypeControlId }',modelName:'<%=thirdCtripModelName.toString()%>',docId:'${param.fdId}',curViewWgt:'${param.backTo}',ctripViewDom:'third_ctrip_${param.controlId}_view',location:location.href"></div>
	<div  class="thirdCtripView" data-dojo-type="mui/view/DocScrollableView" id="third_ctrip_${param.controlId}_view">
		<div id="third_ctrip_${param.controlId}_loading" style="display:none;height:100%;width:100%;filter:alpha(opacity=30); background-color:white;opacity: 0.3;-moz-opacity: 0.3; ">
			<div class="third_ctrip_loading" title="数据加载中"></div>
		</div>
		<div class="third_ctrip_iframe" style="height:100%;width:100%;">
		
		</div>
	</div>
</xform:editShow>

<xform:viewShow>
	<div style="display:inline;" data-dojo-type="third/ctrip/xform/resource/mobile/js/Ctrip"
		data-dojo-props="showStatus:'view',controlId:'${param.controlId}',mainDocStatus:'<%=mainModelStatus.toString()%>',ticketType:'hotel|plane',bookTypeControlId:'${param.bookTypeControlId }',modelName:'<%=thirdCtripModelName.toString()%>',docId:'${param.fdId}',curViewWgt:'${param.backTo}',ctripViewDom:'third_ctrip_${param.controlId}_view'"></div>
</xform:viewShow>