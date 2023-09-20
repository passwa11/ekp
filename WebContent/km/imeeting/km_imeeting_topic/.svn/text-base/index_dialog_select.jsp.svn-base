<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/listview.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="body">
		<style>
			.km_imeeting_topic_select{
				text-align: center;
				border-top: solid 1px #797874;
				position: fixed;
				left: 0;
				right: 0;
				bottom: 0;
				padding: 10px 0px;
				background-color: #fff
			}
		</style>
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
	  <list:criteria id="sendCriteria">
	        <list:cri-ref key="docSubject" ref="criterion.sys.docSubject"> 
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.simpleCategory" key="fdTopicCategory" multi="false" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdTopicCategory') }" expand="false">
			  <list:varParams modelName="com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory"/>
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingTopic" property="fdIsAccept" />
		</list:criteria>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview_send">
			<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=list&isDialog=0'}
			</ui:source>
			<list:colTable  isDefault="false" layout="sys.ui.listview.columntable" onRowClick="selectStock('!{fdId}')"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview> 
	 	<list:paging></list:paging>	 
	 	
	 	<div style="height:50px"></div>
	 	<div class="km_imeeting_topic_select">
	 		<ui:button text="${lfn:message('button.ok')}" onclick="getReturnValue();" order="1"></ui:button>	
			<ui:button text="${lfn:message('button.cancel')}" onclick="$dialog.hide();" order="1"></ui:button>
	 	</div>
	 	<script type="text/javascript">
	 	seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/toolbar'], function($, strutil, dialog , topic ,toolbar) {
		 	window.selectStock = function(fdId){
		 		if($('[name="List_Selected"][value="'+fdId+'"]').prop('checked') ){
					$('[name="List_Selected"][value="'+fdId+'"]').prop('checked',false);
				}else{
					$('[name="List_Selected"][value="'+fdId+'"]').prop('checked',true);
				}
			};
			window.getReturnValue = function(){
				 var checks=document.getElementsByName("List_Selected");
				 var checksValue="";
				 for(var i=0;i<checks.length;i++)
				 {
					 if(checks[i].checked)
					 {
						checksValue+=checks[i].value+";";
					}
				 }
				 if(checksValue==""){
					seajs.use(['lui/dialog'], function(dialog) {
						dialog.alert('请选择议题!');
					});
					return;
				 }
				 $dialog.hide(checksValue);
			}
	 	});
		</script>
	</template:replace>	 
</template:include>