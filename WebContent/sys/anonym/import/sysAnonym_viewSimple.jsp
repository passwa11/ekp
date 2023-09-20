<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<style>

</style>
<div id="_anonym_container">
	<ui:tabpanel id="tabAccessAnonym" layout="sys.ui.tabpanel.light">
		<ui:content
			title="${lfn:message('sys-anonym:button.publish.pub.record') }">
			<ui:event event="show">
				setTimeout(function() {
					document.getElementById('logAnonymContent0').src = '<c:url value="/sys/anonym/sys_anonym_main/sysAnonymMain.do" />?method=pubRecord&fdModelName=${fdModelName}&fdModelId=${fdModelId}&fdKey=${anonymFdKey}';
				},100);
			</ui:event>
		
			<table width="100%" ${HtmlParam.styleValue}>
				<tr> 
					<td>
						<iframe id="logAnonymContent0" width="100%" height="1000" frameborder=0 scrolling=yes></iframe>
					</td>
				</tr> 
			</table>
		</ui:content>
		<ui:content
			title="${lfn:message('sys-anonym:button.publish.opt.record') }">
			<ui:event event="show">
				setTimeout(function() {
					document.getElementById('logAnonymContent1').src = '<c:url value="/sys/anonym/sys_anonym_main/sysAnonymMain.do" />?method=optRecord&fdModelName=${fdModelName}&fdModelId=${fdModelId}&fdKey=${anonymFdKey}';
				},100);
			</ui:event>
		
			<table width="100%" ${HtmlParam.styleValue}>
				<tr> 
					<td>
						<iframe id="logAnonymContent1" width="100%" height="1000" frameborder=0 scrolling=yes></iframe>
					</td>
				</tr> 
			</table>
		</ui:content>
	</ui:tabpanel>
</div>
<script>
	function showAnonymTab(){
		var tab = LUI('tabAccessAnonym');
		tab.on('indexChanged',function(data) {
			console.log(data)
			seajs.use(['lui/jquery'],function($) {
				//火狐浏览器下在iframe未显示出来之前获取body高度始终是0，所以这里只能需要添加延时，确保iframe已经显示出来
				setTimeout(function() {
					var $frame = $('#logAnonymContent'+data.index.after);
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