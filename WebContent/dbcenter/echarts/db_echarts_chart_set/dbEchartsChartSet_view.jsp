<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<c:if test="${'0'!=param.showButton}">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:if test='${isAttention=="1"}'><!--已关注-->
	<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}">
		<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.nofollowing.title')}" order="1" onclick="deleteMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
	</kmss:auth>
</c:if>
<c:if test='${isAttention=="0"}'><!--未关注-->
	<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}">
		<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.following.title')}" order="1" onclick="createMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
	</kmss:auth>
</c:if>

			<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('dbEchartsChartSet.do?method=edit&fdId=${param.fdId}','_self');"></ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${ lfn:message('button.copy') }" onclick="Com_OpenWindow('dbEchartsChartSet.do?method=clone&cloneModelId=${param.fdId}','_blank');">
					</ui:button>
			</kmss:auth>
			
			<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=delete&fdId=${param.fdId}">
				<ui:button text="${lfn:message('button.delete')}" order="4" onclick="deleteDoc('${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=delete&fdId=${param.fdId}');"></ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	</c:if>
	<template:replace name="content">
<c:if test="${config.hideTitle!='true'}">
	<p class="txttitle"><c:out value="${(empty config.title)?(model.docSubject):(config.title)}"/></p><br>
</c:if>
<center>
<div style="${'1'==param.scroll?'width:100%;overflow-x:auto;':''}">
<table class="tb_noborder" width="${empty config.tbWidth ? '800' : config.tbWidth}" id="mainTable">
	<c:set var="rowCount" value="0" />
	<c:set var="cellCount" value="0" />
	<c:forEach items="${model.fdChartList}" var="chartModel" varStatus="vstatus">
		<%-- 第一列 --%>
		<c:if test="${cellCount==0}">
			<c:if test="${rowCount>0}">
	</tr>
	<tr height="${(empty config.paddingHeight)?'1px':(config.paddingHeight)}"><td colspan="${config.columns*2-1}"></td></tr>
			</c:if>
			<c:set var="rowCount" value="${rowCount+1}"/>
	<tr>
		</c:if>
		<c:if test="${cellCount>0}">
		<td width="${(empty config.paddingWidth)?'1px':(config.paddingWidth)}"></td>
		</c:if>
		<td>
			<ui:chart var-themeName="${empty chartTheme ? '' : chartTheme }" width="100%" height="${config.tdHeight}">
				<ui:source type="AjaxJson">
					{"url":"/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=chartData&isFromSet=true&fdId=${chartModel.fdId}${chartParams}"}
				</ui:source>
			</ui:chart>
		</td>
		<c:set var="cellCount" value="${cellCount+1}"/>
		<c:if test="${cellCount==config.columns}">
			<c:set var="cellCount" value="0"/>
		</c:if>
	</c:forEach>
	</tr> 
</table>
<c:if test="${('0'!=param.showButton) || ('1'==param.showTheme)}">
<div style="margin-top:40px;">
	<label><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.choose.theme.tip"/></label>
	<select id="themeChoose">
		<option value="default" ${'default' eq chartTheme ? 'selected':'' }><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.theme.default"/></option>
		<c:forEach var="theme" items="${themes }">
			<option value="${theme }" ${theme eq chartTheme ? 'selected':'' }>${theme }</option>
		</c:forEach>
	</select>
</div>
</c:if>
</div>
<script type="text/javascript">
	Com_IncludeFile("echartschart.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
	seajs.use(['lui/jquery'], function($) {
		$(document).ready(function() {
			//绑定切换主题事件
			$("#themeChoose").bind('change',function() {
				var theme = $(this).val();
				var url = window.location.href;
				var re = new RegExp();
				re.compile("([\\?&]theme=)[^&]*", "i");
				theme = encodeURIComponent(theme);
				//已经有theme参数
				if(re.test(url)){
					url = url.replace(re, "$1"+theme);
				}else{
					//没有theme参数，必须在前面添加theme
					//不放前面如果有筛选项参数会传不过去
					url = url.replace("&fdId=","&theme="+theme+"&fdId=");
				}
				window.location.href = url;
			});
		});
		var tb = document.getElementById("mainTable");
		if(tb.rows.length==0 || tb.rows[0].cells.length<2){
			return;
		}
		var width = $(tb).width();
		var padding = $(tb.rows[0].cells[1]).width();
		var col = (tb.rows[0].cells.length+1)/2;
		width = (width - (col-1) * padding)/col;
		if(width<10){
			width = 10;
		}
		for(var i=0; i<tb.rows.length; i+=2){
			var row = tb.rows[i];
			for(var j=0; j<row.cells.length; j+=2){
				$(row.cells[j]).css("width",width+"px");
			}
		}
	});
	seajs.use(["lui/dialog"],function(dialog){
		window.deleteDoc = function(delUrl){
			dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
				if(isOk){
					Com_OpenWindow(delUrl,'_self');
				}	
			});
			return;
		};
		
		//取消关注方法方法
		window.deleteMyAttentionEcharts = function(confirmUrl){
			dialog.confirm('${lfn:message('dbcenter-echarts:module.echarts.my.following.noConfirm') }',function(isOk){
				if(isOk){
					Com_OpenWindow(confirmUrl,'_self');
				}
			});
			return;
		};

		//关注方法
		window.createMyAttentionEcharts = function(confirmUrl){
			dialog.confirm('${lfn:message('dbcenter-echarts:module.echarts.my.following.confirm') }',function(isOk){
				if(isOk){
					Com_OpenWindow(confirmUrl,'_self');
				}
			});
			return;
		};
		
	});
	domain.autoResize();
</script>
<c:if test="${'0'!=param.showButton}">
	<br><br>${ lfn:message('dbcenter-echarts:chartset_hint_1') }:<br>
	<script>document.write(location.protocol+'//'+location.host+location.pathname+'?method=view&fdId=${model.fdId}&showButton=0&scroll=1&LUIID=!{lui.element.id}')</script>
	<br><br>
</c:if>
</center>
	<c:if test="${'0'!=param.showButton}">
		<ui:tabpage expand="false" collapsed="true">
			<!--权限机制 -->
			<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="dbEchartsChartSetForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.dbcenter.echarts.model.DbEchartsChartSet" />
			</c:import>
		</ui:tabpage>
	</c:if>
	</template:replace>
</template:include>