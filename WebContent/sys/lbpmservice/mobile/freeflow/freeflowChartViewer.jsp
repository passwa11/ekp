<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<div data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartView"  data-dojo-props="showType:'${param.showType }',backTo:'${param.backTo }'" 
	class="lbpmView" id="freeflowChartView" style="background-color: #F5F6FB">
	<div id="freeflowChartArea" class="actionArea">
		<div class="actionView">
			<div class="freeflowChartRow">
				<div class="titleNode" id="freeflowRowTitle">
					<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" />
				</div>
				<div class="detailNode">
					<div>
						<div data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowChartPanel"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
		<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnDefault freeflowChartBack freeflowChartBackButton"
			data-dojo-props=''>
			<bean:message  key="button.back" />
		</li>
	</div>
</div>