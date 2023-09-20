<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%
	Object pop = session.getAttribute("__CAN_GET_POPS_DATA__");
	if(pop == null || ((String)pop).equals("false")) {
		session.setAttribute("__CAN_GET_POPS_DATA__", "true");
		request.setAttribute("canGetPopsData", "true");
	}
	
	String userId = UserUtil.getUser(request).getFdId();
	out.println("<script>window.__POPS_TARGET__=\"" + userId + "\"</script>");	
%>

<c:if test="${canGetPopsData == 'true'}">
	<link rel="stylesheet" href="${LUI_ContextPath }/sys/portal/pop/import/css/view.css"/>
	<ui:dataview>
		<ui:source type="AjaxJson" cfg-commitType="get">
	         {url:'/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=getOwnPopList'}
		</ui:source>
		
		<ui:var name="showNoDataTip" value="false"></ui:var>
	
		<ui:render type="Javascript">
			<c:import url="/sys/portal/pop/import/js/render.js" charEncoding="UTF-8">
			</c:import>
		</ui:render>
		<ui:event event="load">
			seajs.use("lui/topic",function(topic){
				topic.publish('lui.page.quick.resize')
			})
		</ui:event>
	</ui:dataview>
</c:if>
<script type="text/javascript">
seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
	window.setPersonDefaultPortal = function(fdPortalId,fdPortalName,obj){
		var $statusTarget = $(obj).find('.default-icon');
		var flag;
		if($statusTarget.hasClass('default-icon-solid')){
			flag = false;
		}else{
			flag = true;
		}
		var url = '${LUI_ContextPath }/sys/portal/sys_portal_person_default/sysPortalPersonDefault.do?method=setDefault';
		$.ajax({
			type : "POST",
			url :url,
			data : {fdPortalId:fdPortalId,fdPortalName:fdPortalName,flag:flag},
			dataType : 'json',
			success : function(result) {
				if($statusTarget.hasClass('default-icon-solid')){
					$statusTarget.removeClass('default-icon-solid');
					$statusTarget.addClass('default-icon-line');
					dialog.success("取消成功！");
				}else{
					$(".lui_dataview_treemenu_flat").find('.default-icon').each(function(){
						if($(this).hasClass('default-icon-solid')){
							$(this).removeClass('default-icon-solid');
							$(this).addClass('default-icon-line');
						}
					});
					$statusTarget.removeClass('default-icon-line');
					$statusTarget.addClass('default-icon-solid');
					dialog.success("设置成功！");
				}
			},
			error : function(result) {
			}
		});
	};
});
</script>