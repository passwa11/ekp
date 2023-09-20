<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.km.calendar.service.IKmCalendarPersonGroupService,com.landray.kmss.util.UserUtil,com.landray.kmss.km.calendar.model.KmCalendarPersonGroup"%>
<%@page import="java.util.List,com.landray.kmss.util.SpringBeanUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<% 
	//唯一标示Id,防止页面出现多个portlet时Id冲突
	String luiId=request.getParameter("LUIID").replace("-","");
	request.setAttribute("pageId","calendar_" + luiId);
	IKmCalendarPersonGroupService groupervice = (IKmCalendarPersonGroupService) SpringBeanUtil
			.getBean("kmCalendarPersonGroupService");
	List<KmCalendarPersonGroup> list = groupervice.getUserPersonGroup(UserUtil.getUser().getFdId());
	String groupId = request.getParameter("groupId");
	int groupSize = 0;
	for(KmCalendarPersonGroup group: list){
		if(group.getFdId().equals(groupId)){
			groupSize = list.size();
			break;
		}
	}
	//判断当前用户是否有指定群组的查看权限
	request.setAttribute("groupSize",groupSize);
%>
<div id="${lfn:escapeHtml(pageId)}" class=""></div>
<script type="text/javascript">
seajs.use( [ 'km/calendar/resource/css/calendar_portlet_group.css']);
seajs.use(['lui/jquery','km/calendar/resource/js/calendar_portlet_group'],function($,PortletCalendar){
	$(function(){
		//参数设置
		var config = {
			id : '${lfn:escapeJs(pageId)}',
			//根节点,JQuery表达式写法 *
			element : $('#${lfn:escapeJs(pageId)}'),
			rowsize : '${JsParam.rowsize}',
			groupId : '${JsParam.groupId}',
			groupSize:'${groupSize}'
		};
		var __portlet = new PortletCalendar.CalendarPortlet(config);
		__portlet.draw();			
	});
});
</script>