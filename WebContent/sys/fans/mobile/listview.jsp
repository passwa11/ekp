<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String fdId = UserUtil.getUser().getFdId();
	request.setAttribute("fdId", fdId);
%>
<div id="scroll" data-dojo-type="mui/list/StoreScrollableView">
    <ul id="_filterDataList"
    	data-dojo-type="mui/list/JsonStoreList" 
    	data-dojo-mixins="sys/fans/mobile/list/attentionItemListMixin"
    	data-dojo-props="url:'/sys/fans/sys_fans_main/sysFansMain.do?method=dataFollow&fdId=${fdId}&orderby=fdFollowTime&ordertype=down&attentModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo&fansModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo&q.type=mobileAttention', 
								lazy:false">
	</ul>
</div>
