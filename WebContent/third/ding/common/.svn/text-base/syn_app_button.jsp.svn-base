<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<% 
if(DingConfig.newInstance().getDingAppEnabled().equals("true") && DingConfig.newInstance().getDingEnabled().equals("true")){
%>
	<c:if test="${param.buttonType == '1' }">
		<ui:button id="synAppTodingAll" text="批量同步到钉钉" onclick="synAppTodingAll()" order="5" ></ui:button>
	</c:if>
	
	<c:if test="${param.buttonType == '2' }">
		<a class="btn_txt" href="javascript:synAppTodingAll('${param.fdId}')">同步到钉钉</a>
	</c:if>
<%}%>

<script type="text/javascript">

seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		
	window.synAppTodingAll = function(id){
		var values = [];
		if(id) {
			values.push(id);
 		} else {
			$("input[name='List_Selected']:checked").each(function(){
				values.push($(this).val());
			});
 		}
		if(values.length==0){
			dialog.alert('<bean:message key="page.noSelect"/>');
			return;
		}
		window.del_load = dialog.loading();
		var url = "${KMSS_Parameter_ContextPath}third/ding/third_ding_microapp/thirdingMicroApp.do?method=createMicroApp";
		$.post(url,$.param({"List_Selected":values},true),function(data){
			if(window.del_load!=null)
				window.del_load.hide();
			if(data!=null && data.status=='true'){
				topic.publish("list.refresh");
				dialog.success('<bean:message key="return.optSuccess" />');
			}else{
				dialog.failure(data.msg);
			}
		},'json');
	};
	
	
});
	
	
</script>
