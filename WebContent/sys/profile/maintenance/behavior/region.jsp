<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.region" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.min.js"></script>
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/china.js"></script>
		<script type="text/javascript">
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=regionDistribution');
			source.bindRender(new sui.MonthRender('#month_select'));
			source.bindRender({
				draw: function(data) {
					var option = {
						    "title": {
						        "text": "<bean:message key="sys.profile.behavior.region" bundle="sys-profile-behavior" />",
						        "left": "center"
						    },
						    "tooltip": {
						        "trigger": "item"
						    },
						    "visualMap": {
						        "min": 0,
						        "max": data.max,
						        "top": "bottom",
						        "text": [
						            "<bean:message key="sys.profile.behavior.region.high" bundle="sys-profile-behavior" />",
						            "<bean:message key="sys.profile.behavior.region.low" bundle="sys-profile-behavior" />"
						        ],
						        "calculable": true,
						        "inRange": {
						            "color": [
						                "#ECF5FF",
						                "#197EFA"
						            ]
						        }
						    },
						    "series": [
						        {
						            "name": "<bean:message key="sys.profile.behavior.region" bundle="sys-profile-behavior" />",
						            "type": "map",
						            "mapType": "china",
						            "label": {
						                "emphasis": {
						                    "show": true
						                }
						            },
						            "data": data.datas
						        }
						    ]
						};
					
					echarts.init($('#chartmain')[0]).setOption(option);
				}
			});
			source.bindRender({
				draw:function(data){
					var items = data.datas;
					var total = data.totalCount;
					var tb = $('#list_table')[0];
					for(var i = tb.rows.length - 1; i>0; i--){
						tb.deleteRow(i);
					}
					
					for(var i=0; i<items.length; i++) {
						insertRow(tb, items[i].name, items[i].value, total);
					}
				}
			});
			
			function insertRow(tb, name, count, total) {
				var row = tb.insertRow(-1);
				var cell = $(row.insertCell(-1));
				cell.text(name);
				cell.css({'text-align':'center'});
				cell = $(row.insertCell(-1));
				cell.text(count);
				cell.css({'text-align':'center'});
				cell = $(row.insertCell(-1));
				cell.text(rate(count, total)+'%');
				cell.css({'text-align':'center'});
			}
			function rate(value, total){
				return Math.round(value * 10000.0 / total)/100.0;
			}
	
			$(function(){
				source.load();
				menu_focus("0__region");
			});
		</script>
	</template:replace>
	<template:replace name="filter">
		<div class="beh-container-heading">
			<div class="criteria_frame">
				<span class="criteria_title"><bean:message key="sys.profile.behavior.time.slot" bundle="sys-profile-behavior" /></span>
				<span id="month_select"></span>
			</div>
		</div>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-8">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.region" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
				<div style="width:100%;margin: 5px auto;">
					<div id="chartmain" style="width:100%; height:450px;"></div>
				</div>
            </div>
          </div>
        </div>
        <div class="beh-card-wrap beh-col-4">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.region.list" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
				<div style="width:100%;margin: 5px auto;">
					<table class="tb_normal" id="list_table" style="margin-top: 5px; width:100%;">
						<tr class="tr_normal_title">
							<td width="30%"><bean:message key="sys.profile.behavior.region.name" bundle="sys-profile-behavior" /></td>
							<td width="30%"><bean:message key="sys.profile.behavior.region.count" bundle="sys-profile-behavior" /></td>
							<td width="40%"><bean:message key="sys.profile.behavior.region.proportion" bundle="sys-profile-behavior" /></td>
						</tr>
					</table>
				</div>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>