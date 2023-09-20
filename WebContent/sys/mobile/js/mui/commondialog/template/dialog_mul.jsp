<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String modelName = request.getParameter("modelName");
	String displayProp = SysDataDict.getInstance().getModel(modelName).getDisplayProperty();
	pageContext.setAttribute("_displayProp", displayProp);
%>
<div data-dojo-type="mui/commondialog/template/DialogHeader"
	data-dojo-props="key:'{categroy.key}',height:'3.8rem'"></div>
<div
	data-dojo-type="mui/commondialog/template/DialogSearchBar" 
	data-dojo-props="modelName:'{categroy.modelName}',needPrompt:false,height:'3.8rem',searchUrl:'{categroy.dataUrl}',
	key:'{categroy.key}'">
</div>
<div id='_dialog_sgl_view_{categroy.key}'
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}'">
	
	<ul data-dojo-type="mui/commondialog/template/DialogList"
		data-dojo-mixins="mui/commondialog/template/DialogItemListMixin"
		data-dojo-props="isMul:{categroy.isMul},key:'{categroy.key}',dataUrl:'{categroy.dataUrl}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',
			selType:{categroy.type},modelName:'{categroy.modelName}',displayProp:'${_displayProp}'">
	</ul>
</div>
<div data-dojo-type="mui/commondialog/template/DialogSelection" 
	data-dojo-props="key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',modelName:'{categroy.modelName}'" fixed="bottom">
</div>
