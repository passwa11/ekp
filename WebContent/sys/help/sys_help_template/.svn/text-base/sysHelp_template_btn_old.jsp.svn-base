<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<kmss:authShow roles="ROLE_SYSHELP_DEFAULT">
<c:if test="${ sysHelpIsLoad != 'true' }">

	<div id="sysHelpIsLoad" style="display: none">
		<input type="button" value="<bean:message bundle='sys-help' key='sysHelpConfig.template.text' />"
					onclick="toHelpPage()" />
	</div>
	<script>
		function addBtn(){
			if(OptBar_BarList)
				OptBar_BarList.push('sysHelpIsLoad');
		}
	</script>
	<script>
		$(function(){
			$.ajax({
				url: '${LUI_ContextPath}/sys/help/sys_help_module/sysHelpModule.do?method=checkIsShowBtn',
				type: 'post',
				dataType: 'json',
				async: true,
				data: {
					'url': window.location.pathname + window.location.search
				},
				success: function(data) {
					if(data){
						addBtn();
					}
				}
			});
		});
	</script>
	
	<script>
		function toHelpPage(){
			var fdUrl = window.location.pathname + window.location.search;
			var url = '${LUI_ContextPath}/sys/help/sys_help_config/sysHelpConfig.do?method=findConfigByUrl';
			$.ajax({
				url: url,
				type: 'post',
				dataType: 'json',
				data: {
					'fdUrl': fdUrl
				},
				success: function(data) {
					if(data.message == 'noData'){
						alert('<bean:message bundle="sys-help" key="sysHelpConfig.template.nodata" />');
					}else if(data.message == 'more'){
						alert('<bean:message bundle="sys-help" key="sysHelpConfig.template.more" />');
					}else{
						Com_OpenWindow('${LUI_ContextPath}/sys/help/sys_help_main/sysHelpMain.do?method=findHTMLById&fdId='+data.mainId+'&configId='+data.configId,'_blank')
					}
				}
			});
		}
	</script>
</c:if>
</kmss:authShow>