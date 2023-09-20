<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
	<script type="text/javascript">
	Com_IncludeFile("dialog.css","${LUI_ContextPath}/sys/lbpmservice/support/lbpm_summary_approval/css/","css",true);
	Com_IncludeFile("listview.css","${LUI_ContextPath}/sys/ui/extend/theme/default/style/","css",true);
	</script>
	<script type="text/javascript">
		//替换样式
		var parentDom = window.parent.document;
		$(parentDom).find("div.lui_dialog_frame").css({
			"border":"1px solid #ffffff",
			"border-radius": "4px"
		});
		$(parentDom).find("div.lui_dialog_container").css({
			"border":"none"
		})
	</script>
	<c:if test="${data.emptyData == true}">
		<div id='container'>
			<div class="panel-tab-content">
				<div class="panel-tab-header">
					<div class="closeWin" onclick="Com_CloseWindow()"></div>
				</div>
				<c:import url="/resource/jsp/listview_norecord.jsp" charEncoding="UTF-8"></c:import>
			</div>
		</div>
	</c:if>
	<c:if test="${data.emptyData == false }">
		<div data-lui-type="${Com_Parameter.ContextPath }sys/lbpmservice/support/lbpm_summary_approval/container!Container" id="container">
			<script type="text/config">
 			{
				storeData : ${data},
				headerClass : 'panel-tab-header',
				mainClass : 'panel-tab-main',
				params : {'modelName':'${JsParam.modelName}'},
				optionButtom : ${optionButtom}
			}
 		</script>
			<div class="panel-tab-content">
				<div class="panel-tab-header"></div>
				<div class="panel-tab-main"></div>
			</div>
		</div>
	</c:if>
<%@ include file="/resource/jsp/edit_down.jsp"%>