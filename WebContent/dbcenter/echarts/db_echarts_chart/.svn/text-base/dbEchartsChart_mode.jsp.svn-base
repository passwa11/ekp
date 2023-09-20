<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.constant.DbcenterConstants"%>
<html>
	<script>
		Com_IncludeFile('dbEchartsChart_mode.css',Com_Parameter.ContextPath+'dbcenter/echarts/db_echarts_chart/css/','css',true);
	</script>
	<body>
		<!-- 模式选择列表 Starts -->
      <ul class="lui-chartMode-list">
        <li>
          <div class="lui-chartMode-pane">
            <div class="pane-heading"><img src="images/img-chartMode-01@2x.png"></div>
            <div class="pane-body">
              <dl class="lui-chartMode-dl">
                <dt>${ lfn:message('dbcenter-echarts:chart.mode.configure') }</dt>
                <dd>${ lfn:message('dbcenter-echarts:chart.mode.configure.desrc1') }</dd>
                <dd>${ lfn:message('dbcenter-echarts:chart.mode.configure.desrc2') }</dd>
                <dd>${ lfn:message('dbcenter-echarts:chart.mode.configure.desrc3') }</dd>
              </dl>
            </div>
            <div class="pane-footer">
              <button type="button" data-mode="<%=DbcenterConstants.DBCENTER_CHART_MODE_CONFIGURE %>" class="lui-chartMode-btn lui-chartMode-btn-primary" onclick="addDoc(this);">
              	${ lfn:message('dbcenter-echarts:chart.mode.choose') }
              </button>
            </div>
          </div>
        </li>
        <li>
          <div class="lui-chartMode-pane">
            <div class="pane-heading"><img src="images/img-chartMode-02@2x.png"></div>
            <div class="pane-body">
              <dl class="lui-chartMode-dl">
                <dt>${ lfn:message('dbcenter-echarts:chart.mode.programming') }</dt>
                <dd>${ lfn:message('dbcenter-echarts:chart.mode.programming.desrc1') }</dd>
                <dd>${ lfn:message('dbcenter-echarts:chart.mode.programming.desrc2') }</dd>
              </dl>
              <div class="lui-chartMode-form">
                <div class="form-item">
                  <label class="lui-chartMode-radio-inline">
                    <input type="radio" name="chartModeProgram" value="<%=DbcenterConstants.DBCENTER_CHART_MODE_PROGRAM_NORMAL %>">${ lfn:message('dbcenter-echarts:chart.mode.programming.simple') }
                  </label>
                  <p class="form-txt">${ lfn:message('dbcenter-echarts:chart.mode.programming.sql') }<BR/>${ lfn:message('dbcenter-echarts:chart.mode.programming.style') }</p>
                </div>
                <div class="form-item">
                  <label class="lui-chartMode-radio-inline">
                    <input type="radio" name="chartModeProgram" value="<%=DbcenterConstants.DBCENTER_CHART_MODE_PROGRAM_ADVANCE%>">${ lfn:message('dbcenter-echarts:chart.mode.programming.higeLevel') }
                  </label>
                  <p class="form-txt">${ lfn:message('dbcenter-echarts:chart.mode.programming.sql') }<BR/>${ lfn:message('dbcenter-echarts:chart.mode.programming.echart') }</p>
                </div>
              </div>
            </div>
            <div class="pane-footer">
              <button type="button" data-mode="<%=DbcenterConstants.DBCENTER_CHART_MODE_PROGRAM %>" class="lui-chartMode-btn lui-chartMode-btn-primary" onclick="addDoc(this);">
              	${ lfn:message('dbcenter-echarts:chart.mode.choose') }
              </button>
            </div>
          </div>
        </li>
      </ul>
      <!-- 模式选择列表 Ends -->
		<script>
			function changeMode(dom){
				var msg = "";
				var mode = $(dom).data("mode");
				var className = ".content_mdeDesc_" + mode; 
				if(mode == '<%=DbcenterConstants.DBCENTER_CHART_MODE_CONFIGURE %>'){
					$(dom).addClass("contend_mode_chosen");
					$(dom).siblings().removeClass("contend_mode_chosen");
					$(className).show();
					$(className).siblings().hide();
				}else if(mode == '<%=DbcenterConstants.DBCENTER_CHART_MODE_PROGRAM %>'){
					$(dom).addClass("contend_mode_chosen");
					$(dom).siblings().removeClass("contend_mode_chosen");
					$(className).show();
					$(className).siblings().hide();
				}
			}
			
			function cancelDialogOpera(){
				$dialog.hide(null);
			}
			
			function addDoc(dom){
				var type = $(dom).data("mode");
				if(type == '<%=DbcenterConstants.DBCENTER_CHART_MODE_PROGRAM%>'){
					var radio = $("[name='chartModeProgram']:checked");
					if(radio.length > 0){
						type = radio.val();
					}else{
						alert("${ lfn:message('dbcenter-echarts:chart.mode.programming.plzChooseMode') }");
						return;
					}
				}
				var url = '${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=add&fdKey=${param.fdKey}&fdModelName=${param.fdModelName}&fdType=' + type;
				if("${param.fdTemplateId}" != null && "${param.fdTemplateId}" != ""){
					url += "&fdTemplateId=${param.fdTemplateId}"
				}
				Com_OpenWindow(url);	
				$dialog.hide(null);
			}
		</script>
	</body>
</html>

