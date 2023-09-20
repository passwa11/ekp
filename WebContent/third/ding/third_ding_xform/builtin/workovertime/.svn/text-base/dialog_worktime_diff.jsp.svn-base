<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/ding/third_ding_xform/resource/css/template.css"/>
	</template:replace>
	<template:replace name="content">
		<div>
			<div class="lui_dialog_head">
				<div class="lui_dialog_head_left" data-lui-mark = "dialog.nav.title" id = "hearderId"></div>
				<div class="lui_dialog_head_right" data-lui-mark="dialog.nav.close" onclick="continueSubmits()"></div>
				<div class="clr"></div>
			</div>
			<div class="d_lui_mix_pop_content">
				<p style="margin-top: 45px;font-size: large;">只能选择班次、加班规则相同的员工</p>
			</div>
			<div class="d_lui_mix_pop_footer clearfix">
				<div onclick="showInfo()" class="d_lui_mix_pop_footer_btn">查看详情</div>
				<div onclick="continueSubmits()" class="d_lui_mix_pop_footer_btn primary">继续提交</div>
			</div>
		</div>
		<script>
			Com_AddEventListener(window, "load", init);
			
			function init() {
				let interval = setInterval(function(){
					if($dialog) {
						clearInterval(interval);
						Ding_Init($dialog.content.params);
					}
				}, 50);
			}
	
			function Ding_Init(params){
				$("#hearderId").text(params.headerStr); 
			}
			function showInfo(){
				//查看详情没页面，先隐藏，此处预留
				 seajs.use(['lui/dialog'], function (dialog) {
					var data={result:"showInfo"};
					$dialog.hide(data);
				 });
			}
			function continueSubmits(){
				seajs.use(['lui/dialog'], function (dialog) {
					var data={result:"continueSubmit"};
					$dialog.hide(data);
				});
			}
		</script>
	</template:replace>
</template:include>