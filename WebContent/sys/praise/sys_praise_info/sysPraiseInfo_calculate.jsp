<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.praise.service.ISysPraiseInfoService"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
		<% 
		ISysPraiseInfoService sysPraiseInfoService = (ISysPraiseInfoService)SpringBeanUtil.getBean("sysPraiseInfoService");
		Date lastTime = sysPraiseInfoService.getLastExecuteTime();
		if(lastTime!=null){
			request.setAttribute("lastTime", DateUtil.convertDateToString(lastTime, DateUtil.PATTERN_DATETIME));
		}else{
			request.setAttribute("lastTime", null);
		}
		%>
	</template:replace>
	<template:replace name="content">
		<list:criteria id="criteria1" expand="true" multi="false">
				<list:cri-criterion title="${ lfn:message('sys-praise:sysPraiseInfo.calculate.time')}" key="fdTimeType"> 
				<list:box-select>
					<list:item-select cfg-defaultValue="week" cfg-required = "true">
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.week')}', value:'week'},
							{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.month')}',value:'month'},
							{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.year')}',value:'year'},
							{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.total')}',value:'total'}]
						</ui:source>
						<ui:event event="selectedChanged" args="evt">
							var vals = evt.values;
							var val = vals[0].value;
							document.getElementById("timeValue").setAttribute("value",val);
						</ui:event>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfoPersonal" 
			property="fdPerson"/>
		</list:criteria>
		<c:if test="${not empty lastTime }">
			<p class="calculateTitle">${lfn:message('sys-praise:sysPraiseInfo.calculate.limitedTime')}：${lastTime}</p>
		</c:if>
		<div class="lui_list_operation" style="padding: 1px 0px 1px 0;">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn" style="float:left;margin-top: 0.8rem;margin-left:10px;">
				<list:selectall></list:selectall>
			</div>
			<input type="hidden" id="timeValue" />
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
					     <list:sort property="fdPraiseNum" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdPraiseNum') }" group="sort.list"></list:sort>
						 <list:sort property="fdPraisedNum" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdPraisedNum') }" group="sort.list"></list:sort>
						 <list:sort property="fdOpposeNum" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdOpposeNum') }" group="sort.list"></list:sort>
						 <list:sort property="fdOpposedNum" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdOpposedNum') }" group="sort.list"></list:sort>
						 <list:sort property="fdPayNum" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdPayNum') }" group="sort.list"></list:sort>
						 <list:sort property="fdRichPay" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdRichPay') }" group="sort.list"></list:sort>
						 <list:sort property="fdReceiveNum" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdReceiveNum') }" group="sort.list"></list:sort>
						 <list:sort property="fdRichGet" text="${lfn:message('sys-praise:sysPraiseInfoDetailBase.fdRichGet') }" group="sort.list"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div> 
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar">
							<kmss:authShow roles="ROLE_SYSPRAISEINFO_MAINTAINER">
							<ui:button text="${lfn:message('button.export')}" onclick="exportInfo()" order="1" ></ui:button>
							</kmss:authShow>
					</ui:toolbar>
				</div>
			</div> 
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id = "listview">
			<ui:source type="AjaxJson">
				{url:'/sys/praise/sys_praise_info_personal/sysPraiseInfoPersonal.do?method=data'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdPerson,fdPraiseNum,fdPraisedNum,fdOpposeNum,fdOpposedNum,fdPayNum,fdRichPay,fdReceiveNum,fdRichGet"></list:col-auto>
			</list:colTable>
		</list:listview>
		<list:paging></list:paging>
		<script>
			seajs.use(["lui/jquery", "lui/dialog", "lui/topic"], function($, dialog, topic) {
				window.exportInfo = function(){
					var values = [];
					var fdShowInfo ;
					$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});

					if(values.length>0){
						fdShowInfo = "${lfn:message('sys-praise:sysPraiseInfo.downloadSelected.warning')}";
					}else{
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm(fdShowInfo,
							function(flag, d) {
								if (flag) {
									var dataview = LUI("listview");
									var fdUrl = dataview.source.url;
									var fdExportUrl = "${ LUI_ContextPath}"+fdUrl.replace("method=data","method=downExcel");
									if(values.length>0){
										$.ajax({
											url : "<c:out value='${LUI_ContextPath}/sys/praise/sys_praise_info_personal/sysPraiseInfoPersonal.do?method=buildIdsInfo'/>",
											type: "post",
											async:"false",
											data: {
												ids : values.join(";")
											},
											success : function(data) {
												Com_OpenWindow(fdExportUrl, '_self');
											}
										})
									}else{
										Com_OpenWindow(fdExportUrl, '_self');
									}
								}
							})

				}
			});



			function showItemDetail(fdOrgId,fdTimeType,fdPraiseType,fdPersonType){
				var fdUrl = "/sys/praise/sys_praise_info/sysPraiseInfo.do?method=listDetail&q.fdType="+fdPraiseType+"&fdTimeType="+fdTimeType;
				if(fdOrgId!=""){
					if(fdPersonType=="target"){
						fdUrl +="&q.fdTargetPerson="+fdOrgId
					}else{
						fdUrl +="&q.fdPraisePerson="+fdOrgId
					}
				}
				seajs.use(
						[ 'lui/dialog' ],
						function(dialog) {
							dialog.iframe(
									fdUrl,
									"${lfn:message('sys-praise:sysPraiseInfo.calculate.detail')}",
									function() {
									}, {
										"width" : 800,
										"height" : 450
									});
						});

			}
		</script>
		<style>
		.praiseItemBtn{
		cursor: pointer;
		text-decoration: underline;
		}
		</style>
	</template:replace>
</template:include>