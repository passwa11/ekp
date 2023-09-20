<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="dbcenter_${param.fdControlId }" data-dojo-type="dbcenter/echarts/application/common/mobile/CustomText" style="word-break:break-all;"></div>

<script>
	require(["dojo/ready","dijit/registry","dbcenter/echarts/application/common/mobile/DbChart"],function(ready,registry,DbChart) {
		ready(function() {
			setTimeout(function(){
				var executor = registry.byId("dbcenter_${param.fdControlId}");
				var config = {};
				config.name = "${param.fdControlId }";
				config.categoryId = "${param.categoryId }";
				config.showstatus = "${param.showstatus }";
				config.inputs = "${param.inputs }";
				var dbcenterChart = new DbChart(config);
				dbcenterChart.executor = executor;
				dbcenterChart.origUrl = "/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=getCustomTextByFdId&fdId=${param.categoryId}";
				dbcenterChart.load();
			},50);
		});
	});
</script>
