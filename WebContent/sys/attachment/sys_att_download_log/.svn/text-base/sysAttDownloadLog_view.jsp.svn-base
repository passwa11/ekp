<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script type="text/javascript">	
			var criteriaHeight;
			LUI.ready(function() {
				var criteria = LUI('criteria1');
				//筛选器加载完毕后给一些元素添加事件
				criteria.on('load',function() {
					seajs.use(['lui/jquery'],function($) {
						//日期选择框
						var $input = $('span.lui_criteria_date_span input');
						$input.focus(function() {
							$("body").css('min-height',(criteriaHeight+310)+'px');
							setBodyHeight(100);
						}).blur(function() {
							$("body").css('min-height','');
							setBodyHeight(100);
						});
						//展开（折叠）按钮
						var $expandA = $('.criteria-extra-more');
						$expandA.bind('click',function() {
							setBodyHeight(500);
						});
					});
					setBodyHeight();
				});
				var creator = criteria.criterions[1];
				var v1 = creator.children[1];
				v1.on('load',function() {
					criteriaHeight = document.getElementById('criteria1').offsetHeight;
					setBodyHeight(200);
				});
			});
			function setBodyHeight(delay) {
				seajs.use(['lui/jquery'],function($){
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						if(delay) {
							setTimeout(function() {
								window.frameElement.style.height =  $(document.body).height() +140+ "px";
							},delay);
						}else {
							window.frameElement.style.height =  $(document.body).height() +140+ "px";
						}
					}
				});
			}
		</script>
		<list:criteria id="criteria1" expand="false">
			<list:cri-ref key="fdFileName" ref="criterion.sys.docSubject">
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('sys-attachment:sysAttDownloadLog.docCreatorName') }" />
			<list:cri-auto modelName="com.landray.kmss.sys.attachment.model.SysAttDownloadLog" property="docCreateTime"/>
		</list:criteria>
		<div>
		 <list:listview>
			<ui:source type="AjaxJson">
				{"url":"/sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=list&rowsize=10&modelName=${param['modelName']}&modelId=${param['modelId']}"}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.listtable" cfg-norecodeLayout="simple">
				<list:col-auto props=""></list:col-auto>
			</list:colTable>						
			<ui:event topic="list.loaded">  
			   setBodyHeight();
			</ui:event>	
		</list:listview>
		<div style="height: 15px;"></div>
		<list:paging layout="sys.ui.paging.simple"></list:paging>
		</div>
	</template:replace>
</template:include>