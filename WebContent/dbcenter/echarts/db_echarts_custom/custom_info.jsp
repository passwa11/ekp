<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 看板 Starts -->
<div class="lui-chartData-info-board">
  <div class="board-txt" id="borad-txt"></div>
  <div class="board-btn-group">
    <button type="button" class="lui-chartMode-btn lui-chartMode-btn-plain-primary" onclick="dbEchartsCustom_Preview();">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.preview') }</button>
  </div>
</div>
<!-- 看板 Ends -->
<div class="lui-chartData-info-body">
  <div class="lui-chartData-info-list-box">
  </div>
</div>
<div class="lui-chartData-info-tip">
	<span class="info-tip">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.textareaDesrc') }</span>
</div>
<div class="lui-chartData-rtf">
	<xform:rtf property="fdCustomText" height="220" required="false" width="100%" needFilter="true" toolbarSet="Default"></xform:rtf>
</div>