<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.online" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script src="${LUI_ContextPath}/sys/profile/maintenance/behavior/js/echarts.min.js"></script>
		<script type="text/javascript">
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=onlineReport');
			source.bindRender({
				draw: function(data) {
					var option = {
						    "tooltip": {
						        "trigger": "axis",
						        "textStyle": {
						            "align": "left"
						        }
						    },
						    "grid": {
						        "bottom": 90
						    },
						    "legend": {
						        "x": "center",
						        "y": "bottom",
						        "orient": "horizontal",
						        "data": [
						            "<bean:message key="sys.profile.behavior.online.number" bundle="sys-profile-behavior" />"
						        ]
						    },
						    "toolbox": {
						        "show": true,
						        "y": "center",
						        "orient": "vertical",
						        "feature": {
						            "restore": {
						                "show": true
						            },
						            "saveAsImage": {
						                "show": true
						            },
						            "dataView": {
						                "show": false
						            }
						        }
						    },
						    "calculable": "true",
						    "yAxis": [
						        {
						            "type": "value",
						            "name": "<bean:message key="sys.profile.behavior.online.num" bundle="sys-profile-behavior" />",
						            "max": data.yAxisMax
						        }
						    ],
						    "xAxis": [
						        {
						            "name": "<bean:message key="sys.profile.behavior.online.time" bundle="sys-profile-behavior" />",
						            "type": "category",
						            "axisLabel": {
						                "interval": "0",
						                "rotate": "70"
						            },
						            "data": data.xAxis
						        }
						    ],
						    "series": [
						        {
						            "name": "<bean:message key="sys.profile.behavior.online.number" bundle="sys-profile-behavior" />",
						            "type": "line",
						            "yAxisIndex": "0",
						            "data": data.datas,
						            "areaStyle": {
						                "normal": {}
						            },
						        }
						    ],
						    "dataZoom": [
						        {
						            "show": true,
						            "realtime": true,
						            "start": data.dataZoomStart,
						            "end": data.dataZoomEnd
						        }
						    ],
						    "itemStyle": {
						        "normal": {
						            "color": '#5BADF6',
						            "lineStyle": {
						                "color": '#5BADF6'
						            }
						        }
						    }
						};
					echarts.init($('#chartmain')[0]).setOption(option);
				}
			});
	
			$(function(){
				source.load();
				menu_focus("0__online");
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-4">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.online.current" bundle="sys-profile-behavior" /></h4>
              <!-- 
              <div class="beh-card-heading-right">
                <p class="beh-card-heading-right-tip"><span class="red">20</span>秒之后更新数据</p>
                <span class="fontmui mui-shuaxinhuanyipi"></span>
              </div>
               -->
            </div>
            <div class="beh-card-body">
				<div style="height:150px; text-align: center; padding: 50px 0;">
					<div style="color: #999; font-size: 14px">
						<%=com.landray.kmss.util.DateUtil.convertDateToString(new java.util.Date(), "yyyy-MM-dd HH:mm:ss")%>
					</div>
					<div style="font-size: 60px; line-height: 1; margin-top: 15px; margin-bottom: 10px;">
						<%=com.landray.kmss.sys.log.util.SysLogOnlineUtil.getOnlineUserNum()%>
					</div>
					<div style="font-size: 16px">
						<bean:message key="sys.profile.behavior.online.number1" bundle="sys-profile-behavior" />
					</div>
				</div>
            </div>
          </div>
        </div>
        <div class="beh-card-wrap beh-col-8">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.online.history" bundle="sys-profile-behavior" /></h4>
              <!-- 
              <div class="beh-card-heading-right">
                <p class="beh-card-heading-right-tip"><span class="red">20</span>秒之后更新数据</p>
                <span class="fontmui mui-shuaxinhuanyipi"></span>
              </div>
               -->
            </div>
            <div class="beh-card-body">
				<div id="chartmain" style="width:100%; height:250px;"></div>
            </div>
          </div>
        </div>
        
        <div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.online.list" bundle="sys-profile-behavior" /></h4>
              <!-- 
              <div class="beh-card-heading-right">
                <p class="beh-card-heading-right-tip"><span class="red">20</span>秒之后更新数据</p>
                <span class="fontmui mui-shuaxinhuanyipi"></span>
              </div>
               -->
            </div>
            <div class="beh-card-body">
				<div style="margin: 5px auto;">
					<!-- 内容列表 -->
					<list:listview>
						<ui:source type="AjaxJson">
							{url:'/sys/log/sys_log_online/sysLogOnline.do?method=list&q.fdType=online'}
						</ui:source>
						<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
							rowHref="/sys/log/sys_log_online/sysLogOnline.do?method=view&fdId=!{fdId}">
							<list:col-serial></list:col-serial>
							<list:col-auto props="fdPerson,fdDept,isOnline,fdOnlineTime,fdLoginTime,fdLoginIp,fdLastLoginTime,fdLastLoginIp,fdLoginNum,docCreateTime"></list:col-auto>
						</list:colTable>
					</list:listview>
					<br>
					<!-- 分页 -->
				 	<list:paging/>
				</div>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>