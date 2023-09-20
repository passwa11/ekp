<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<div class="_readlog_container">
<ui:tabpanel id="tabAccess" layout="sys.ui.tabpanel.light">
	<ui:content title="${ __readlog__ }">
		<ui:event event="show">
			setTimeout(function() {
				document.getElementById('logContent0').src = '<c:url value="/sys/readlog/sys_read_log/sysReadLog.do" />?method=view&modelId=${sysLogForm.readLogForm.fdModelId}&modelName=${sysLogForm.readLogForm.fdModelName}';
			},100);
		</ui:event>
		
		<table width="100%" ${HtmlParam.styleValue}>
			<tr> 
				<td>
					<iframe id="logContent0" width="100%" height="1000" frameborder=0 scrolling=no></iframe></td>
				</td>
			</tr> 
		</table>
	</ui:content>
	<kmss:ifModuleExist path="/sys/print/">
		<ui:content title="${__printRecord__ }">
			<ui:event event="show">
				<%  IExtendForm form = (IExtendForm)pageContext.getAttribute("sysLogForm");
					if(form instanceof ISysPrintLogForm) {
				%>
				setTimeout(function() {
					document.getElementById('logContent1').src = '<c:url value="/sys/print/sys_print_log/sysPrintLog.do" />?method=view&modelId=${sysLogForm.sysPrintLogForm.fdModelId}&modelName=${sysLogForm.sysPrintLogForm.fdModelName}';
				},100);
				<%	} else { %>
				setTimeout(function() {
					document.getElementById('logContent1').src = '<c:url value="/sys/print/sys_print_log/sysPrintLog.do" />?method=view&modelId=${fdModelId }&modelName=${fdModelName }';
				},100);
				<%} %>
			</ui:event>
			
			<table width="100%" ${HtmlParam.styleValue}>
				<tr> 
					<iframe id="logContent1" width="100%" height="1000" frameborder=0 scrolling=yes></iframe>
				</tr> 
			</table>
		</ui:content>
	</kmss:ifModuleExist>
	<ui:content title="${__attDownloadRecord__ }">
		<ui:event event="show">
			setTimeout(function() {
				document.getElementById('logContent2').src = '<c:url value="/sys/attachment/sys_att_download_log/sysAttDownloadLog.do" />?method=view&modelId=${fdModelId }&modelName=${fdModelName }';
			},100);
		</ui:event>
		
		<table width="100%" ${HtmlParam.styleValue}>
			<tr> 
				<td>
					<iframe id="logContent2" width="100%" height="1000" frameborder=0 scrolling=yes></iframe>
				</td>
			</tr> 
		</table>
	</ui:content>
</ui:tabpanel>
</div>
<script>
	function showTab(){
		var tab = LUI('tabAccess');
		tab.on('indexChanged',function(data) {
			seajs.use(['lui/jquery'],function($) {
				//火狐浏览器下在iframe未显示出来之前获取body高度始终是0，所以这里只能需要添加延时，确保iframe已经显示出来
				setTimeout(function() {
					var $frame = $('#logContent'+data.index.after);
					var _window = $frame[0].contentWindow;
					var fHeight = $frame.height();  //iframe的高度
					var bHeight = $(_window.document.body).height();  //body的高度
					if(fHeight < bHeight) {
						if(_window.setBodyHeight)
							_window.setBodyHeight();
					}
				},100);
			});
		});
	}
</script>

