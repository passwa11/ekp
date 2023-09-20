<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	//根据默认值来过滤按钮
	boolean isShow = false;
	LbpmSettingDefault settingDefault = new LbpmSettingDefault();
	String ids = settingDefault.getSummaryApprovalIds();
	String[] idArr = StringUtil.isNotNull(ids) ? ids.split(";") : null;
	if(idArr != null && idArr.length > 0 && UserUtil.checkUserIds(ArrayUtil.asList(idArr))){
		isShow = true;
	}
	if(isShow){
%>
		<ui:button id="summaryApproval"
				text="${ lfn:message('sys-xform-base:button.summaryApproval')}"
				order="2" onclick="listProcess()" cfg-if="param['j_path']!='/listFiling' && param['j_path']!='/listDiscard' && param['j_path']!='/listFeedback'">
		</ui:button>
		<script>
			//请求带有表单摘要的流程数据
			function listProcess(){
				
				var url = '/sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=getData&modelName=${JsParam.modelName}';
				seajs.use( [ 'lui/dialog','lui/topic' ], function(dialog,topic) {
					dialog.iframe(url,"", function(value) {
					}, {
						"width" : 800,
						"height" : 500
					});
					
					//替换样式
					var dom = window.document;
					$(dom).find("div.lui_dialog_mask").css({
						"opacity": "0.5",
						"background-color": "#000000"
					})
				});
			}
		</script>
<%
	}
%>

