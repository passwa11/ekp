<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/ding/third_ding_xform/resource/css/template.css"/>
	</template:replace>
	<template:replace name="content">

		<div>
			<div class="d_lui_mix_pop_header">
				<p>请选择补卡班次</p>
				<span class="d_lui_mix_pop_header_close" onclick="dialogClose()">✕</span>
			</div>
			<div class="d_lui_mix_pop_content">
				<ul class="d_lui_mix_RadioBoxList">
				</ul>
			</div>
			<div class="d_lui_mix_pop_footer clearfix">
				<div onclick="OK()" class="d_lui_mix_pop_footer_btn primary">确定</div>
				<div onclick="dialogClose()" class="d_lui_mix_pop_footer_btn">取消</div>
			</div>
		</div>

		<script>
			function init() {
				let interval = setInterval(function(){
					if($dialog) {
						clearInterval(interval);
						initSchedule($dialog.content.params);
					}
				}, 50);
			}

			function initSchedule(params){
				if(!params || !params.data || !params.data.schedules) {
					return;
				}
				let schedules = params.data.schedules;
				let currentSchedule = params.data.currentSchedule;
				for(let i in schedules) {
					let schedule = schedules[i];
					let checked = (schedule.punchId == currentSchedule || Object.keys(schedules).length === 1) ? "checked=\"checked\"" : "";
					let li = $('<li/>');
					let label = $('<label/>');
					label.addClass('d_lui_mix_RadioBox');
					label.append("<input type=\"radio\" name=\"schedule\" class=\"d_lui_mix_Radio\" value=\"" + i + "\" " + checked + ">");
					label.append("<label for=\"radio_1\"></label>");
					label.append("<em>" + schedule.text + "</em>");
					li.append(label);
					$('.d_lui_mix_RadioBoxList').append(li);
				}
			}

			Com_AddEventListener(window, "load", init);

			function dialogClose(){
				$dialog.hide();
			}

			function OK(){
				let resultJson = {};
				let $input = $("input[name='schedule']:checked");
				resultJson.value = $input.val();
				$dialog.hide(resultJson);
			}

			/**
			 * ajax请求失败
			 */
			function loadFailure(data) {
				seajs.use(['lui/dialog'], function (dialog) {
					dialog.failure('网络异常，请稍后再试');
				});
			}

		</script>
	</template:replace>
</template:include>