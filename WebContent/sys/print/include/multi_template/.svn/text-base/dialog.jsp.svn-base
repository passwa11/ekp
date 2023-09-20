<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">选择打印模板</template:replace>
	<template:replace name="head">
		<template:super/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/print/resource/css/printdialog.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="body">
		<div class="app_print">
			<!-- <div class="print_head">
				<div class="app_print_search">
					<div class="app_print_searchbox">
						<input type="text" class="app_print_searchinput" onkeyup='' placeholder="输入关键字查找模板"/>
						<a href="javascript:void(0);" onclick="" class="app_print_searchbtn"></a>
					</div>
				</div>
			</div> -->
			<div class="print_content">
				<ui:dataview id="printDataView">
					<ui:source type="AjaxJson">
						{url : "/sys/print/sys_print_template/sysPrintTemplate.do?method=printData&fdModelId=${JsParam.fdModelId}&fdModelName=${JsParam.fdModelName}"}
					</ui:source>
					<ui:render type="Javascript">
						<c:import url="/sys/print/resource/js/printRender.js" charEncoding="UTF-8"></c:import>
					</ui:render>
				</ui:dataview>
			</div>
			<div class="print_bottom">
				<div class="print_bottom_buttons">
					<ui:button text="${lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" width="80" onclick="cancel();" order="2" ></ui:button>
					<ui:button text="${lfn:message('button.ok')}" width="80" onclick="ok();" order="1" ></ui:button>
				</div>
			</div>
		</div>
		<script>
			var selectId;
			function ok(){
				$dialog.hide({type: 'ok',printId:selectId});
			}
			
			function cancel(){
				$dialog.hide({type: 'cancel'});
			}
		</script>
	</template:replace>
</template:include>
