<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" rwd="true">
	<template:replace name="body">
		<list:criteria id="criteria1" expand="true">
			<list:cri-ref key="fdPersonName" ref="criterion.sys.docSubject" title="参会人名字"/>
			<list:cri-criterion title="命令类型" key="fdPushType" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_02') }', value:'02'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_03') }', value:'03'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_05') }', value:'05'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_06') }', value:'06'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_07') }', value:'07'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_10') }', value:'10'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_11') }', value:'11'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_12') }', value:'12'}
							,{text:'${ lfn:message('km-imeeting:km_imeeting_push_type_13') }', value:'13'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		</list:criteria>
		
		<div>
			<list:listview id="listview">
				<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=getControlResult&fdMainId=${JsParam.fdId}&fdType=2'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" name="columntable">
					<list:col-auto props="userName;msgType;sendTime;sendStatus;receiveTime;responseTime;responseStatus;imMsg"></list:col-auto>
				</list:colTable>
				<ui:event topic="list.loaded">  
			   seajs.use(['lui/jquery'],function($){
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						if($(document.body).height() > 0){
							window.frameElement.style.height =  $(document.body).height() +10+ "px";
						}
					}
				});
			</ui:event>
			</list:listview>
			<div style="height: 15px;"></div>
			<list:paging layout="sys.ui.paging.simple"></list:paging>
		</div>
	</template:replace>
</template:include>