<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.constant.DbcenterConstants"%>
<html>
	<script>
		Com_IncludeFile('dbEchartsTable_mode.css',Com_Parameter.ContextPath+'dbcenter/echarts/db_echarts_table/css/','css',true);
	</script>
	<body>
		<!-- 模式选择列表 Starts -->
	      <ul class="lui-chartMode-list">
	        <li>
	          <div class="lui-chartMode-pane">
	            <div class="pane-heading"><img src="images/img-chartMode-01@2x.png"></div>
	            <div class="pane-body">
	              <dl class="lui-chartMode-dl">
	                <dt>${ lfn:message('dbcenter-echarts:table.model.configure') }</dt>
	                <dd>${ lfn:message('dbcenter-echarts:table.model.configure.desrc1') }</dd>
	                <dd>${ lfn:message('dbcenter-echarts:table.model.configure.desrc2') }</dd>
	              </dl>
	            </div>
	            <div class="pane-footer">
	              <button type="button" data-mode="<%=DbcenterConstants.DBCENTER_TABLE_MODE_CONFIGURE %>" class="lui-chartMode-btn lui-chartMode-btn-primary" onclick="addDoc(this);">${ lfn:message('dbcenter-echarts:table.model.choose') }</button>
	            </div>
	          </div>
	        </li>
	        <li>
	          <div class="lui-chartMode-pane">
	            <div class="pane-heading"><img src="images/img-chartMode-02@2x.png"></div>
	            <div class="pane-body">
	              <dl class="lui-chartMode-dl">
	                <dt>${ lfn:message('dbcenter-echarts:table.model.programming') }</dt>
	                <dd>${ lfn:message('dbcenter-echarts:table.model.programming.desrc1') }</dd>
	                <dd>${ lfn:message('dbcenter-echarts:table.model.programming.desrc2') }</dd>
	              </dl>
	            </div>
	            <div class="pane-footer">
	              <button type="button" class="lui-chartMode-btn lui-chartMode-btn-primary" data-mode="<%=DbcenterConstants.DBCENTER_TABLE_MODE_PROGRAM %>" onclick="addDoc(this);">${ lfn:message('dbcenter-echarts:table.model.choose') }</button>
	            </div>
	          </div>
	        </li>
	      </ul>
	      <!-- 模式选择列表 Ends -->
		
		<script>
			
			function addDoc(dom){
				var mode = $(dom).data("mode");
				var url = '${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=add&fdKey=${JsParam.fdKey}&fdModelName=${JsParam.fdModelName}&mode=' + mode;
				if("${param.fdTemplateId}" != null && "${param.fdTemplateId}" != ""){
					url += "&fdTemplateId=${param.fdTemplateId}"
				}
				Com_OpenWindow(url);	
				$dialog.hide(null);
			}
		</script>
	</body>
</html>

