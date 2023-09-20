<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<script type="text/javascript">
			seajs.use(['theme!profile']);
			seajs.use(['theme!iconfont']);
			Com_IncludeFile("formList.css","${LUI_ContextPath}/sys/xform/sys_form_common_template/resource/css/","css",true);
		</script>
	 </template:replace>
	 <template:replace name="content">
	 	<script type="text/javascript">
	 		var modelInfo = {
	 				fdModelName : '${JsParam.fdModelName}',
	 				fdMainModelName : '${JsParam.fdMainModelName}',
	 				fdKey : '${JsParam.fdKey}'
	 		}
	 	</script>
		<!-- 通用表单列表 -->
	  	<div id="modeList" data-lui-type="sys/xform/sys_form_common_template/resource/js/formListDataView!FormListDataView" class="lui_main_content cm-hot-template">
			<ui:source type="AjaxJson">
				{
					url : "/sys/xform/sys_form_common_template_new/sysFormCommonTemplate.do?method=findForm&type=0&fdModelName=${JsParam.fdModelName}"
				}
			</ui:source>
			<ui:render type="Javascript">
				<c:import url="/sys/xform/sys_form_common_template/resource/js/commonFormRender.js" charEncoding="UTF-8"></c:import>
			</ui:render>
		</div>
	</template:replace>
</template:include>