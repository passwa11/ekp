<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="stat_key" value="dept.stat"></c:set>
<c:if test="${HtmlParam['stat_key']!=null && HtmlParam['stat_key']!=''}">
	<c:set var="stat_key" value="${HtmlParam['stat_key']}"></c:set>
</c:if>
<template:include ref="default.list" spa="true">
	<template:replace name="head">
		<script type="text/javascript">
		var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary";
		
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/data/source','lui/menu','lui/toolbar'], 
				function($, dialog , topic, source , menu , toolbar) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.addDoc = function(addUrl) {
					Com_OpenWindow('${LUI_ContextPath}' + addUrl,'_blank');	
				};
				
				//删除
				window.delDoc = function(delUrl){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							var del_load = dialog.loading();
							$.post('${LUI_ContextPath}' + delUrl,
									$.param({"List_Selected":values},true),function(data){
										if(del_load!=null)
											del_load.hide();
										if(data!=null && data.status==true){
											topic.publish("list.refresh");
											dialog.success('<bean:message key="return.optSuccess" />');
										}else{
											dialog.failure('<bean:message key="return.optFailure" />');
										}
									},'json');
						}
					});
				};
			});
		</script>
	</template:replace>
	

	<%-- 左侧导航栏 --%>
	<template:replace name="nav">
				<ui:combin ref="menu.nav.title">
			<ui:varParam name="operation">
				<ui:source type="Static">
				[
					{
						"text": "我参加的",
						"href": "${LUI_ContextPath }/km/imeeting/import/kmImeeting_attend.jsp",
						"icon": "&#xe765;",
						"target": "_rIframe"
					},
					{
						"text": "我主持的",
						"href": "${LUI_ContextPath }/km/imeeting/import/kmImeeting_manage.jsp",
						"icon": "&#xe76a;",
						"target": "_rIframe"
					},
					{
						"text": "我发起的",
						"href": "${LUI_ContextPath }/km/imeeting/import/kmImeeting_create.jsp?mymeeting=myCreate&except=docStatus:00",
						"icon": "&#xe768;",
						"target": "_rIframe"
					},
					{
						"text": "待我审的",
						"href": "${LUI_ContextPath }/km/imeeting/import/kmImeeting_create.jsp?mymeeting=myApproval&except=docStatus:10_00_30",
						"icon": "&#xe6cd;",
						"target": "_rIframe"
					},
					{
						"text": "我已审的",
						"href": "${LUI_ContextPath }/km/imeeting/import/kmImeeting_create.jsp?mymeeting=myApproved&except=docStatus:10_00",
						"icon": "&#xe769;",
						"target": "_rIframe"
					}
					
				]
				</ui:source>
			</ui:varParam>				
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/km/imeeting/import/nav.jsp?panel=kmImeetingStatPanel" charEncoding="UTF-8">
					<c:param name="key" value="calendar"></c:param>
				   	<c:param name="criteria" value="res"></c:param>
				</c:import>
			<ui:content title="${ lfn:message('list.otherOpt') }" expand="false">
				<ul class='lui_list_nav_list'>
					<!-- <li><a id="kmImissiveSend_allflow" href="javascript:void(0)" onclick="moduleAPI.kmImissive.switchMenuItem(this, 'kmImissive',{ cri :{'cri.q' : 'docStatus:00' },title : '废弃箱'});">废弃箱</a></li> -->
					<kmss:authShow roles="ROLE_KMIMEETING_BACKSTAGE_MANAGER">
					<li><a href="${LUI_ContextPath }/sys/profile/index.jsp#app/ekp/km/imeeting" target="_blank">${ lfn:message('list.manager') }</a></li>
					</kmss:authShow>
				</ul>
			</ui:content>	
			</ui:accordionpanel>
		</div>
	
	</template:replace>
	
	<%-- 右侧列表 --%>
	<template:replace name="content">
		<ui:tabpanel id="kmImeetingStatPanel">
		 <ui:content title="${lfn:message('km-imeeting:kmImeetingStat.dept.stat')}">
		 	  <ui:iframe id="deptStat" src="${LUI_ContextPath }/km/imeeting/km_imeeting_stat/KmImeetingStat_index.jsp?stat_key=${JsParam.stat_key}"></ui:iframe>
		  </ui:content>
	  </ui:tabpanel>
		
	</template:replace>


</template:include>