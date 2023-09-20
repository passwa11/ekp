<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysNotifyRemindMainContextForm" value="${mainModelForm.sysNotifyRemindMainContextForm}" scope="request" />
<link rel="stylesheet" href="${LUI_ContextPath}/sys/notify/mobile/import/edit.css" />
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<!-- 全天  -->
<div id="allDay">
	<table class="muiAgendaNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_${param.fdPrefix}_${param.fdKey}_true" >
		<script type="text/javascript">DocList_Info.push("TABLE_${param.fdPrefix}_${param.fdKey}_true");</script>
		<%--基准行--%>
		<tr data-dojo-type="mui/form/Template" class="muiAgendaNormalTr" data-celltr="true" KMSS_IsReferRow="1" style="display:none;" border='0'>
			<td>
				<input  type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdModelName" value="${param.fdModelName}"  />
				<input type="hidden" subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdBeforeTime" value="-540" />
				<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdTimeUnit" value="minute" />
				<table class="muiSimple">
					<tr>
						<td class="muiDetailTableNo"  style="padding-left: 1rem;">
							<span class="muiDetailTableNoTxt"><bean:message key="sysNotifyTodo.muiDetailTableNo" bundle="sys-notify"/>!{index}</span>
							<div class="muiDetailTableDel" onclick="_${param.fdPrefix}_${param.fdKey}_del(this,true);"><bean:message key="button.delete"/></div>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 请选择提前提醒天数 --%>
							<xform:select value="0" property="daySelect_!{index}" title="请选择提前提醒天数" showStatus="edit" showPleaseSelect="false" mobile="true" htmlElementProperties="id='daySelect_!{index}'" onValueChange="allDayChangeDay(!{index},this.value);">
								<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
								<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
       							<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
       							<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
       							<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
							</xform:select>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 提醒时间 --%>
							<xform:datetime value="09:00" dateTimeType="time" property="" subject="提醒时间" showStatus="edit" mobile="true" htmlElementProperties="id='timeSelect_!{index}'" onValueChange="allDayChangeTime(!{index},this.value);"></xform:datetime>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 通知方式 --%>
			 				<kmss:editNotifyType 
			 					multi="false" value="todo" mobile="true" 
			 					property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdNotifyType" 
			 					title="${lfn:message('sys-notify:mui.sysNotify.notify.type')}">
			 				</kmss:editNotifyType>
						</td>
					</tr>
				</table>
			</td>	
		</tr>
		<%--内容行--%>
		<c:if test="${kmCalendarMainForm.fdIsAlldayevent eq 'true' }">
		<c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" data-celltr="true" class="muiAgendaNormalTr">
				<td>
					<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdId" value="${sysNotifyRemindMainFormListItem.fdId}" />
				 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelId" value="${sysNotifyRemindMainFormListItem.fdModelId}" />
				 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdKey" value="${sysNotifyRemindMainFormListItem.fdKey}" />
				 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelName" value="${param.fdModelName}" />
					<input type="hidden" subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdBeforeTime" value="${sysNotifyRemindMainFormListItem.fdBeforeTime}" />
					<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" value="${sysNotifyRemindMainFormListItem.fdTimeUnit}" />
					<table class="muiSimple">
						<tr>
							<td class="muiDetailTableNo">
								<span class="muiDetailTableNoTxt"  style="padding-left: 1rem;"><bean:message key="sysNotifyTodo.muiDetailTableNo" bundle="sys-notify"/>${vstatus.index+1}</span>
								<div class="muiDetailTableDel" onclick="_${param.fdPrefix}_${param.fdKey}_del(this,true);"><bean:message key="button.delete"/></div>
							</td>
						</tr>
						<tr>
							<td>
								<!-- 请选择提前提醒天数 -->
								<c:choose>
						       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime lt 0 }">
						       			<xform:select value="0" property="daySelect_${vstatus.index}" title="请选择提前提醒天数" showStatus="edit" showPleaseSelect="false" mobile="true" htmlElementProperties="id='daySelect_${vstatus.index}'" onValueChange="allDayChangeDay(${vstatus.index},this.value);">
								       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
								       		<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
								       		<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
								       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
								       		<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
								       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.custom" /></xform:simpleDataSource>
								       	</xform:select>
						       		</c:when>
						       		<c:when test="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 1 or lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 3 or lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 5 or lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 7}">
						       			<xform:select value="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440)}" property="daySelect_${vstatus.index}" title="请选择提前提醒天数" showStatus="edit" showPleaseSelect="false" mobile="true" htmlElementProperties="id='daySelect_${vstatus.index}'" onValueChange="allDayChangeDay(${vstatus.index},this.value);">
											<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
											<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
			       							<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
			       							<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
			       							<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
										</xform:select>
						       		</c:when>
						       		<c:otherwise>
						       			<xform:select value="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440)}" property="daySelect_${vstatus.index}" title="请选择提前提醒天数" showStatus="edit" showPleaseSelect="false" mobile="true" htmlElementProperties="id='daySelect_${vstatus.index}'" onValueChange="allDayChangeDay(${vstatus.index},this.value);">
											<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
											<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
			       							<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
			       							<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
			       							<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
			       							<xform:simpleDataSource value="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440)}">${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440)}天前</xform:simpleDataSource>
										</xform:select>
						       		</c:otherwise>
						       	</c:choose>
							</td>
						</tr>
						<tr>
							<td>
								<!-- 提醒时间 -->
								<c:choose>
									<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime le 0 }">
										<c:choose>
											<c:when test="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60) lt 10}">
												<c:set var="hour" value="0${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60)}" />
											</c:when>
											<c:otherwise>
												<c:set var="hour" value="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60)}" />
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60) eq 0}">
												<c:set var="time" value="00" />
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60) lt 10}">
														<c:set var="time" value="0${60-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60)}" />
													</c:when>
													<c:otherwise>
														<c:set var="time" value="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60)}" />
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${24-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60) lt 10}">
												<c:set var="hour" value="0${24-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60)}" />
											</c:when>
											<c:otherwise>
												<c:set var="hour" value="${24-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60)}" />
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${60-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60) eq 60}">
												<c:set var="time" value="00" />
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${60-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60) lt 10}">
														<c:set var="time" value="0${60-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60)}" />
													</c:when>
													<c:otherwise>
														<c:set var="time" value="${60-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60)}" />
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<xform:datetime value="${hour}:${time}" dateTimeType="time" property="" subject="提醒时间" showStatus="edit" mobile="true" htmlElementProperties="id='timeSelect_${vstatus.index}'" onValueChange="allDayChangeTime(${vstatus.index},this.value);"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td>
								<!-- 通知方式 -->
								<kmss:editNotifyType 
									multi="false" mobile="true"
									property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType"
									title="${lfn:message('sys-notify:mui.sysNotify.notify.type')}">
								</kmss:editNotifyType>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:forEach>
		</c:if>
	</table>
	<div class="muiDetailTableAdd"  onclick="_${param.fdPrefix}_${param.fdKey}_add(true);">
		<div class="mblTabBarButtonIconArea">
			<div class="mblTabBarButtonIconParent">
				<i class="mui mui-plus"></i>
			</div>
		</div>
		<div class="mblTabBarButtonLabel" ><bean:message key="sysNotifyTodo.mblTabBarButtonLabel" bundle="sys-notify"/></div>
	</div>
</div>
<!-- 非全天  -->
<div id="notAllDay">
	<table class="muiAgendaNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_${param.fdPrefix}_${param.fdKey}_false" >
		<script type="text/javascript">DocList_Info.push("TABLE_${param.fdPrefix}_${param.fdKey}_false");</script>
		<%--基准行--%>
		<tr data-dojo-type="mui/form/Template" class="muiAgendaNormalTr" data-celltr="true" KMSS_IsReferRow="1" style="display:none;" border='0'>
			<td>
				<input  type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdModelName" value="${param.fdModelName}"  />
				<table class="muiSimple">
					<tr>
						<td class="muiDetailTableNo"  style="padding-left: 1rem;">
							<span class="muiDetailTableNoTxt"><bean:message key="sysNotifyTodo.muiDetailTableNo" bundle="sys-notify"/>!{index}</span>
							<div class="muiDetailTableDel" onclick="_${param.fdPrefix}_${param.fdKey}_del(this,false);"><bean:message key="button.delete"/></div>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 提醒提前时间 --%>
							<div data-dojo-type="mui/form/Input" 
								data-dojo-props="name:'sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdBeforeTime',value:'30',showStatus:'edit',subject:'${lfn:message('km-calendar:kmCalendar.notify.fdBeforeTime')}',validate:'digits required',required:true,orient:'vertical'">
						</td>
					</tr>
					<tr>
						<td>
							<%-- 时间单位 --%>
							<xform:select property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdTimeUnit"
					 			value="minute" showStatus="edit" showPleaseSelect="false" mobile="true" required="true" 
					 			title="${lfn:message('km-calendar:kmCalendar.notify.fdTimeUnit')}">
								<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
							</xform:select>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 通知方式 --%>
			 				<kmss:editNotifyType 
			 					multi="false" value="todo" mobile="true" 
			 					property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdNotifyType" 
			 					title="${lfn:message('sys-notify:mui.sysNotify.notify.type')}">
			 				</kmss:editNotifyType>
						</td>
					</tr>
				</table>
			</td>	
		</tr>
		<%--内容行--%>
		<c:if test="${kmCalendarMainForm.fdIsAlldayevent eq 'false' }">
		<c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" data-celltr="true" class="muiAgendaNormalTr">
				<td>
					<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdId" value="${sysNotifyRemindMainFormListItem.fdId}" />
				 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelId" value="${sysNotifyRemindMainFormListItem.fdModelId}" />
				 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdKey" value="${sysNotifyRemindMainFormListItem.fdKey}" />
				 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelName" value="${param.fdModelName}" />
					<table class="muiSimple">
						<tr>
							<td class="muiDetailTableNo">
								<span class="muiDetailTableNoTxt"  style="padding-left: 1rem;"><bean:message key="sysNotifyTodo.muiDetailTableNo" bundle="sys-notify"/>${vstatus.index+1}</span>
								<div class="muiDetailTableDel" onclick="_${param.fdPrefix}_${param.fdKey}_del(this,false);"><bean:message key="button.delete"/></div>
							</td>
						</tr>
						<tr>
							<td>
								<%-- 提醒提前时间 --%>
								<div data-dojo-type="mui/form/Input" 
									data-dojo-props="name:'sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdBeforeTime',
										value:'${sysNotifyRemindMainFormListItem.fdBeforeTime}',
										showStatus:'edit',subject:'${lfn:message('km-calendar:kmCalendar.notify.fdBeforeTime')}',validate:'digits required',required:true,opt:false,orient:'vertical'">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<%-- 时间单位 --%>
								<xform:select 
									property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" 
									value="${sysNotifyRemindMainFormListItem.fdTimeUnit}"  
									title="${lfn:message('km-calendar:kmCalendar.notify.fdTimeUnit')}"
									showStatus="edit" showPleaseSelect="false" mobile="true">
									<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
								</xform:select>
							</td>
						</tr>
						<tr>
							<td>
								<%-- 通知方式 --%>
								<kmss:editNotifyType 
									multi="false" mobile="true"
									property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType"
									title="${lfn:message('sys-notify:mui.sysNotify.notify.type')}">
								</kmss:editNotifyType>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:forEach>
		</c:if>
	</table>
	<div class="muiDetailTableAdd"  onclick="_${param.fdPrefix}_${param.fdKey}_add(false);">
		<div class="mblTabBarButtonIconArea">
			<div class="mblTabBarButtonIconParent">
				<i class="mui mui-plus"></i>
			</div>
		</div>
		<div class="mblTabBarButtonLabel" ><bean:message key="sysNotifyTodo.mblTabBarButtonLabel" bundle="sys-notify"/></div>
	</div>
</div>
<br/>
<script type="text/javascript">
require(["dojo/parser", "dojo/dom", "dojo/dom-construct","dojo/dom-style","dojo/dom-geometry","dojo/dom-attr" ,"dijit/registry","dojo/ready","dojo/query","dojo/topic"],
		function(parser, dom, domConstruct,domStyle,domGeometry,domAttr ,registry,ready,query,topic){
	
	window.allDayChangeTime = function(index,value){
		var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
		var daySelect = registry.byId('daySelect_'+index).value;
		var time = value.split(":");
		var hourVal = time[0] * 60;
		var timeVal = time[1];
		beforeTimeInput.value = daySelect * 24 * 60 - hourVal - timeVal;
	}
	
	window.allDayChangeDay = function(index,value){
		var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
		var timeSelect = registry.byId('timeSelect_'+index).value;
		var time = timeSelect.split(":");
		var hourVal = time[0] * 60;
		var timeVal = time[1];
		var val = 0;
		if(value == 0){
			val -= hourVal-timeVal;
		}else{
			val = value * 24 * 60 - hourVal - timeVal;
		}
		beforeTimeInput.value = val;
	}
	
	//添加行
	window._${param.fdPrefix}_${param.fdKey}_add = function(flag) {
		event = event || window.event;
		if (event.stopPropagation)
			event.stopPropagation();
		else
			event.cancelBubble = true;
		var newRow = DocList_AddRow("TABLE_${param.fdPrefix}_${param.fdKey}_"+flag);
		parser.parse(newRow);
		resize(flag);
		window['_${param.fdPrefix}_${param.fdKey}_fixNo'](flag);
	};
	//删除行
	window._${param.fdPrefix}_${param.fdKey}_del = function(domNode,flag) {
		var TR = query(domNode).parents('.muiAgendaNormalTr')[0];
		var modelNameField = query('[name^="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList"]',TR);
		modelNameField.forEach(function(INPUT,index){
			domAttr.set(INPUT,'value','');
		});
		domStyle.set(TR,'display','none');
		resize(flag);
		window['_${param.fdPrefix}_${param.fdKey}_fixNo'](flag);
	};
	
	//调整行号
	window._${param.fdPrefix}_${param.fdKey}_fixNo = function(flag){
		var tb;
		if(flag){
			tb = query('#TABLE_${param.fdPrefix}_${param.fdKey}_true')[0];
		}else{
			tb = query('#TABLE_${param.fdPrefix}_${param.fdKey}_false')[0];
		}
		var nodes = query('.muiDetailTableNoTxt',tb);
		var visibleIndex = 0;
		nodes.forEach(function(dom){
			var TR = query(dom).parents('.muiAgendaNormalTr')[0];
			var visible = (domStyle.get(TR,'display') != 'none');
			if(visible){
				dom.innerHTML = '${lfn:message("sys-notify:sysNotifyTodo.muiDetailTableNo")}' + (visibleIndex + 1);
				visibleIndex++;
			}
		});
	};
	
	ready(function(){
		if(DocList_TableInfo['TABLE_${param.fdPrefix}_${param.fdKey}_true']==null || DocList_TableInfo['TABLE_${param.fdPrefix}_${param.fdKey}_false']==null){
			DocListFunc_Init();
		}
	});
	
	//重新调整位置
	function resize(flag){
		var table;
		if(flag){
			table=query('#TABLE_${param.fdPrefix}_${param.fdKey}_true')[0];
		}else{
			table=query('#TABLE_${param.fdPrefix}_${param.fdKey}_false')[0];
		}
		var	tableOffsetTop=_getDomOffsetTop(table),
			sliceHeight= tableOffsetTop+table.offsetHeight;
		topic.publish("/mui/list/toTop",null,{y: 0 - sliceHeight  });
	}
	
	function _getDomOffsetTop(node){
		 var offsetParent = node;
		 var nTp = 0;
		 while (offsetParent!=null && offsetParent!=document.body) {
			 nTp += offsetParent.offsetTop; 
			 offsetParent = offsetParent.offsetParent; 
		 }
		 return nTp;
	};
	
	
    topic.subscribe('mui/form/checkbox/valueChange', function(widget, args) {
        if (args.name.indexOf('__notify_type') > -1 && document.getElementById(args.name) ) {
            var fields = document.getElementsByName(args.name);
            var values = '';
            for (var i = 0; i < fields.length; i++)
                if (fields[i].checked)
                    values += ';' + fields[i].value;
            if (values != '')
                document.getElementById(args.name).value = values.substring(1);
            else
                document.getElementById(args.name).value = '';
        }
    });
	
});
	
</script>


