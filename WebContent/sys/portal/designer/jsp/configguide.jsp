<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.simple">
	<template:replace name="title">引导页属性设置</template:replace>
	<template:replace name="body">
	<ui:toolbar layout="sys.ui.toolbar.float" count="10" var-navwidth="100%">
	    <ui:button onclick="onClear()" text="${lfn:message('button.delete')}"></ui:button>
		<ui:button onclick="onEnter()" text="${lfn:message('button.ok')}"></ui:button>
	</ui:toolbar>
	<script>
		seajs.use(['theme!form']);
		</script>
<script>

	function onReady(){
		if(window.$dialog == null){
			window.setTimeout(onReady, 100);
			return
		}
		window.$ = LUI.$;
		var dp = window.$dialog.dialogParameter;
		$('[name="fdGuide"]').val(dp.fdGuide);
		if(dp.fdGuideCfg){
			var cfg = LUI.toJSON(unescape(dp.fdGuideCfg));
			$('[name="fdGuideName"]').val($.trim(cfg.name));
			$('[name="fdGuideShowName"]').val($.trim(cfg.showName));
			if(cfg.close == 'true'){
				 $('[name="fdGuideClose"]').prop('checked',true);
			}else{
				$('[name="fdGuideClose"]').prop('checked',false);
			}
		}
	}

	LUI.ready(onReady);
	function onEnter(){
		var data = {},
			cfg = {
				name : $('[name="fdGuideName"]').val(),
				showName : $('[name="fdGuideShowName"]').val(),
				close : $('[name="fdGuideClose"]').prop('checked') + ''
			};
		data.fdGuide = $('[name="fdGuide"]').val();
		data.fdGuideCfg = escape(LUI.stringify(cfg));
		window.$dialog.hide(data);
	}
	function onClear(){
		var data = {},
		cfg = {
			name : '',
			showName :'',
			close : $('[name="fdGuideClose"]').prop('checked') + ''
		};
	data.fdGuide ='';
	data.fdGuideCfg = escape(LUI.stringify(cfg));
	window.$dialog.hide(data);
	}
 
</script>
<br>
<br>
<table class="tb_normal" style="width: 400px;">
	<tbody>
		<tr>
			<td width="80px;" valign="top">
				<bean:message bundle="sys-portal" key="sysPortalGuide.guide"/>:
			</td>
			<td colspan="3">
				<div class="inputselectsgl" onclick="selectGuide()" style="width:90%">
					<input name="fdGuide" type="hidden" />
					<div class="input">
						<input subject="${ lfn:message('sys-portal:sysPortalPage.msg.selectGuide') }" name="fdGuideName" type="text">
					</div>
					<div class="selectitem"></div>
				</div>
			</td>
		</tr>
		<tr>
			<td width="80px;" valign="top">
				<bean:message bundle="sys-portal" key="sysPortalGuide.fdShowName" />:
			</td>
			<td colspan="3">
				<input class="inputsgl" subject="${ lfn:message('sys-portal:sysPortalGuide.fdShowName') }" name="fdGuideShowName" type="text" style="width:90%">
			</td>
		</tr>
		<tr>
			<td width="80px;" valign="top">
			<bean:message bundle="sys-portal" key="sysPortalGuide.operation" />:
			</td>
			<td colspan="3">
				<input type="checkbox" name="fdGuideClose" checked="checked"/>
				<bean:message bundle="sys-portal" key="sysPortalGuide.showCloseButton" />
			</td>
		</tr>	
	</tbody>
</table> 
<script type="text/javascript">
function selectGuide(){
	seajs.use(['lui/dialog'],function(dialog){
		dialog.iframe("/sys/portal/designer/jsp/selectguide.jsp","${ lfn:message('sys-portal:sysPortalPage.msg.selectGuide') }",function(value, dia){
			if(value==null){
				return ;
			}
			$("[name='fdGuide']").val(value.ref);
			$("[name='fdGuideName']").val(value.name);
			$("[name='fdGuideShowName']").val(value.name);
		},{"width":700,"height":500});
	});
}
</script>	
	</template:replace>
</template:include>