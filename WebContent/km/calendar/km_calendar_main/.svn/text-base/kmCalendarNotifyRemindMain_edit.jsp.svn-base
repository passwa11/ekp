<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysNotifyRemindMainContextForm" value="${mainModelForm.sysNotifyRemindMainContextForm}" scope="request" />
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<style type="text/css">
	.sys_notify_add{color: #1354ca;}
	.sys_notify_add:HOVER {text-decoration: underline;}
	td>span>input{width: 50px;}
</style>
<!-- 全天 -->
<table  class="tb_simple" width="100%" id="${HtmlParam.fdPrefix}_${HtmlParam.fdKey}_true">
<script type="text/javascript">DocList_Info.push("${HtmlParam.fdPrefix}_${HtmlParam.fdKey}_true");</script>
 	<%--基准行 --%>
	<tr KMSS_IsReferRow="1" style="display:none">
		 <td>
		 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdModelName" value="${HtmlParam.fdModelName}"  />
	       	<xform:select property="" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='daySelect_!{index}'" onValueChange="allDayChangeDay(this.id,this.options[this.options.selectedIndex].value);">
	       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.custom" /></xform:simpleDataSource>
	       	</xform:select>
	       	&nbsp;
	       	<select id="hourSelect_!{index}" onchange="allDayChangeHour(this.id,this.options[this.options.selectedIndex].value);">
	       		<c:forEach begin="0" end="23" varStatus="vs">
	       			<c:choose>
	       				<c:when test="${vs.count ne 10}">
	       					<option value="${vs.count-1}">${vs.count-1}</option>
	       				</c:when>
	       				<c:otherwise>
	       					<option value="${vs.count-1}" selected="selected">${vs.count-1}</option>
	       				</c:otherwise>
	       			</c:choose>
	       		</c:forEach>
	       	</select>
	       	&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.hour" />&nbsp;
	       	<select id="timeSelect_!{index}" onchange="allDayChangeTime(this.id,this.options[this.options.selectedIndex].value);">
	       		<c:forEach begin="0" end="59" varStatus="vs">
	       			<option value="${vs.count-1}">${vs.count-1}</option>
	       		</c:forEach>
	       	</select>
	       	<input type="hidden" subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}"
	       		name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdBeforeTime" value="-540" />
			<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdTimeUnit" value="minute" />
			&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.time" />&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.by" />&nbsp;
			<kmss:editNotifyType value="todo" property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdNotifyType" multi="false" ></kmss:editNotifyType>
			&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.notify" />&nbsp;
			<span>
		    	<a href="javascript:void(0);" onclick="sysNotifyRemind_Delete(this);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_ContextPath}sys/notify/images/delete_btn.png" border="0"  title="<bean:message key="button.delete"/>"/></a>
		     </span>
	    </td>
	</tr>
	<%--内容行--%>
    <c:if test="${kmCalendarMainForm.fdIsAlldayevent eq 'true' }">
    <c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1">
			 <td>
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdId" value="${sysNotifyRemindMainFormListItem.fdId}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelId" value="${sysNotifyRemindMainFormListItem.fdModelId}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdKey" value="${sysNotifyRemindMainFormListItem.fdKey}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelName" value="${HtmlParam.fdModelName}" />
		       	<c:choose>
		       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime le 0 }">
		       			<xform:select value="0" property="" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='daySelect_${vstatus.index}'" onValueChange="allDayChangeDay(this.id,this.options[this.options.selectedIndex].value);">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:when>
		       		<c:when test="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 1 or lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 3 or lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 5 or lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440) eq 7}">
		       			<xform:select value="${lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime/1440)}" property="" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='daySelect_${vstatus.index}'" onValueChange="allDayChangeDay(this.id,this.options[this.options.selectedIndex].value);">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:when>
		       		<c:otherwise>
		       			<xform:select value="custom" property="" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='daySelect_${vstatus.index}'" onValueChange="allDayChangeDay(this.id,this.options[this.options.selectedIndex].value);">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.1" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="3"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.3" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="7"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.7" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:otherwise>
		       	</c:choose>
		       	&nbsp;
		       	<c:choose>
		       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime lt 0 }">
		       			<select id="hourSelect_${vstatus.index}" onchange="allDayChangeHour(this.id,this.options[this.options.selectedIndex].value);">
				       		<c:forEach begin="0" end="23" varStatus="vs">
				       			<c:choose>
				       				<c:when test="${vs.count-1 eq lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60)}">
				       					<option value="${vs.count-1}" selected="selected">${vs.count-1}</option>
				       				</c:when>
				       				<c:otherwise>
				       					<option value="${vs.count-1}">${vs.count-1}</option>
				       				</c:otherwise>
				       			</c:choose>
				       		</c:forEach>
				       	</select>
				       	&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.hour" />&nbsp;
				       	<select id="timeSelect_${vstatus.index}" onchange="allDayChangeTime(this.id,this.options[this.options.selectedIndex].value);">
				       		<c:forEach begin="0" end="59" varStatus="vs">
				       			<c:choose>
				       				<c:when test="${vs.count-1 eq lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60)}">
				       					<option value="${vs.count-1}" selected="selected">${vs.count-1}</option>
				       				</c:when>
				       				<c:otherwise>
				       					<option value="${vs.count-1}">${vs.count-1}</option>
				       				</c:otherwise>
				       			</c:choose>
				       		</c:forEach>
				       	</select>
		       		</c:when>
		       		<c:otherwise>
			       		<select id="hourSelect_${vstatus.index}" onchange="allDayChangeHour(this.id,this.options[this.options.selectedIndex].value);">
				       		<c:forEach begin="0" end="23" varStatus="vs">
				       			<c:choose>
				       				<c:when test="${vs.count-1 eq 24-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%1440/60)}">
				       					<option value="${vs.count-1}" selected="selected">${vs.count-1}</option>
				       				</c:when>
				       				<c:otherwise>
				       					<option value="${vs.count-1}">${vs.count-1}</option>
				       				</c:otherwise>
				       			</c:choose>
				       		</c:forEach>
				       	</select>
				       	&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.hour" />&nbsp;
				       	<select id="timeSelect_${vstatus.index}" onchange="allDayChangeTime(this.id,this.options[this.options.selectedIndex].value);">
				       		<c:forEach begin="0" end="59" varStatus="vs">
				       			<c:choose>
				       				<c:when test="${vs.count-1 eq 60-lfn:ceil(sysNotifyRemindMainFormListItem.fdBeforeTime%60)}">
				       					<option value="${vs.count-1}" selected="selected">${vs.count-1}</option>
				       				</c:when>
				       				<c:otherwise>
				       					<option value="${vs.count-1}">${vs.count-1}</option>
				       				</c:otherwise>
				       			</c:choose>
				       		</c:forEach>
				       	</select>
		       		</c:otherwise>
		       	</c:choose>
		       	<input type="hidden" subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}"
		       		name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdBeforeTime" value="${sysNotifyRemindMainFormListItem.fdBeforeTime}" />
				<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" value="${sysNotifyRemindMainFormListItem.fdTimeUnit}" />
				&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.time" />&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.by" />&nbsp;
				<kmss:editNotifyType property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType" multi="false" value="${sysNotifyRemindMainFormListItem.fdNotifyType }"></kmss:editNotifyType>
				&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.notify" />&nbsp;
				<span>
				 	<a href="javascript:void(0);" onclick="sysNotifyRemind_Delete(this);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_ContextPath}sys/notify/images/delete_btn.png" border="0"  title="<bean:message key="button.delete"/>"/></a>
				</span>
		    </td>
		</tr>
   </c:forEach> 
	</c:if>
	<tr>
	  	<td align="left">
	    	<div style="width:100px;" >
			    <a href="javascript:void(0);"  onclick="DocList_AddRow('${HtmlParam.fdPrefix}_${HtmlParam.fdKey}_true');" class="sys_notify_add"><bean:message bundle="sys-notify" key="sysNotify.remind.add"/></a>
	     	</div>
	  	</td>
	</tr>
</table>
<!-- 非全天 -->
 <table  class="tb_simple" width="100%" id="${HtmlParam.fdPrefix}_${HtmlParam.fdKey}_false">
 	<script type="text/javascript">DocList_Info.push("${HtmlParam.fdPrefix}_${HtmlParam.fdKey}_false");</script>
 	<%--基准行 --%>
	<tr KMSS_IsReferRow="1" style="display:none">
		 <td>
		 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdModelName" value="${HtmlParam.fdModelName}"  />
	       	<xform:select property="" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='earlyTimeSelect_!{index}'" onValueChange="notAllDayChangeBeforeTime(this.id,this.options[this.options.selectedIndex].value);">
	       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.5" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="15"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.15" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="30"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.30" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="60"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.60" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="120"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.120" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="1440"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.1440" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="2880"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.2880" /></xform:simpleDataSource>
	       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.custom" /></xform:simpleDataSource>
	       	</xform:select>
	       	<input type="hidden" subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}"
	       		name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdBeforeTime" value="0" />
			<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdTimeUnit" value="minute" />
			&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.by" />&nbsp;
			<kmss:editNotifyType value="todo" property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdNotifyType" multi="false" ></kmss:editNotifyType>
			&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.notify" />&nbsp;
			<span>
		    	<a href="javascript:void(0);" onclick="sysNotifyRemind_Delete(this);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_ContextPath}sys/notify/images/delete_btn.png" border="0"  title="<bean:message key="button.delete"/>"/></a>
		     </span>
	    </td>
	</tr>
	<%--内容行--%>
    <c:if test="${kmCalendarMainForm.fdIsAlldayevent eq 'false' }">
    <c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1">
			 <td>
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdId" value="${sysNotifyRemindMainFormListItem.fdId}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelId" value="${sysNotifyRemindMainFormListItem.fdModelId}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdKey" value="${sysNotifyRemindMainFormListItem.fdKey}" />
			 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelName" value="${HtmlParam.fdModelName}" />
		       	<c:choose>
		       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime eq 0 or sysNotifyRemindMainFormListItem.fdBeforeTime eq 5 or sysNotifyRemindMainFormListItem.fdBeforeTime eq 15 or sysNotifyRemindMainFormListItem.fdBeforeTime eq 30 }">
		       			<xform:select property="" value="${sysNotifyRemindMainFormListItem.fdBeforeTime }" htmlElementProperties="id='earlyTimeSelect_${vstatus.index}'" onValueChange="notAllDayChangeBeforeTime(this.id,this.options[this.options.selectedIndex].value);" showPleaseSelect="false">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="15"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.15" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="30"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.30" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="60"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.60" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="120"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.120" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1440"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.1440" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="2880"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.2880" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:when>
		       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime eq 1 and sysNotifyRemindMainFormListItem.fdTimeUnit eq 'hour'}">
		       			<xform:select property="" value="60" htmlElementProperties="id='earlyTimeSelect_${vstatus.index}'" onValueChange="notAllDayChangeBeforeTime(this.id,this.options[this.options.selectedIndex].value);" showPleaseSelect="false">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="15"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.15" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="30"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.30" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="60"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.60" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="120"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.120" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1440"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.1440" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="2880"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.2880" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:when>
		       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime eq 2 and sysNotifyRemindMainFormListItem.fdTimeUnit eq 'hour'}">
		       			<xform:select property="" value="120" htmlElementProperties="id='earlyTimeSelect_${vstatus.index}'" onValueChange="notAllDayChangeBeforeTime(this.id,this.options[this.options.selectedIndex].value);" showPleaseSelect="false">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="15"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.15" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="30"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.30" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="60"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.60" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="120"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.120" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1440"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.1440" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="2880"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.2880" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:when>
		       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime eq 1 and sysNotifyRemindMainFormListItem.fdTimeUnit eq 'day'}">
		       			<xform:select property="" value="1440" htmlElementProperties="id='earlyTimeSelect_${vstatus.index}'" onValueChange="notAllDayChangeBeforeTime(this.id,this.options[this.options.selectedIndex].value);" showPleaseSelect="false">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="15"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.15" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="30"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.30" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="60"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.60" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="120"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.120" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1440"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.1440" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="2880"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.2880" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:when>
		       		<c:when test="${sysNotifyRemindMainFormListItem.fdBeforeTime eq 2 and sysNotifyRemindMainFormListItem.fdTimeUnit eq 'day'}">
		       			<xform:select property="" value="2880" htmlElementProperties="id='earlyTimeSelect_${vstatus.index}'" onValueChange="notAllDayChangeBeforeTime(this.id,this.options[this.options.selectedIndex].value);" showPleaseSelect="false">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="15"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.15" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="30"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.30" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="60"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.60" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="120"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.120" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1440"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.1440" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="2880"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.2880" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.custom" /></xform:simpleDataSource>
				       	</xform:select>
		       		</c:when>
		       		<c:otherwise>
			       		<xform:select property="" value="custom" htmlElementProperties="id='earlyTimeSelect_${vstatus.index}'" onValueChange="notAllDayChangeBeforeTime(this.id,this.options[this.options.selectedIndex].value);" showPleaseSelect="false">
				       		<xform:simpleDataSource value="0"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="5"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.5" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="15"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.15" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="30"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.30" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="60"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.60" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="120"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.120" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="1440"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.1440" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="2880"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.2880" /></xform:simpleDataSource>
				       		<xform:simpleDataSource value="custom"><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.custom" /></xform:simpleDataSource>
				       	</xform:select>
				       	<span id="customSpan_${vstatus.index}">
				       		&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.early" />&nbsp;
				       		<input class="inputsgl" validate='digits min(0)' id="beforeTimeInput_${vstatus.index}" type="text" onchange="notAllDaySetBeforeTime(this.id)" value="${sysNotifyRemindMainFormListItem.fdBeforeTime }" />
				       		&nbsp;
				       		<xform:select property="" htmlElementProperties="id='timeUnit_${vstatus.index}'" onValueChange="notAllDaySetTimeUnit(this.id);" value="${sysNotifyRemindMainFormListItem.fdTimeUnit}" showPleaseSelect="false">
				       			<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
				       		</xform:select>
				       	</span>
		       		</c:otherwise>
		       	</c:choose>
		       	<input type="hidden" subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}"
		       		name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdBeforeTime" value="${sysNotifyRemindMainFormListItem.fdBeforeTime}" />
				<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" value="${sysNotifyRemindMainFormListItem.fdTimeUnit}" />
				&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.by" />&nbsp;
				<kmss:editNotifyType property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType" multi="false" value="${sysNotifyRemindMainFormListItem.fdNotifyType }"></kmss:editNotifyType>
				&nbsp;<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.notify" />&nbsp;
				<span>
				 	<a href="javascript:void(0);" onclick="sysNotifyRemind_Delete(this);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_ContextPath}sys/notify/images/delete_btn.png" border="0"  title="<bean:message key="button.delete"/>"/></a>
				</span>
		    </td>
		</tr>
   </c:forEach> 
	</c:if>
	<tr>
	  	<td align="left">
	    	<div style="width:100px;" >
			    <a href="javascript:void(0);"  onclick="DocList_AddRow('${HtmlParam.fdPrefix}_${HtmlParam.fdKey}_false');" class="sys_notify_add"><bean:message bundle="sys-notify" key="sysNotify.remind.add"/></a>
	     	</div>
	  	</td>
	</tr>
</table>
<script type="text/javascript">
	Com_IncludeFile('calendar.js');
</script>
<script type="text/javascript">

	function allDayChangeTime(id,value){
		var index = id.split("_")[1];
		var daySelect = document.getElementById("daySelect_"+index);
		var hourSelect = document.getElementById("hourSelect_"+index);
		var di = daySelect.selectedIndex;
		var hi = hourSelect.selectedIndex;
		var val = 0;
		if(daySelect.options[di].value != 'custom'){
			val = daySelect.options[di].value;
		}else{
			val = document.getElementById("allDaybeforeTimeInput_"+index).value;
		}
		val *= 1440;
		val -= hourSelect.options[hi].value * 60;
		var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
		beforeTimeInput.value = val - value;
	}

	function allDayChangeHour(id,value){
		var index = id.split("_")[1];
		var daySelect = document.getElementById("daySelect_"+index);
		var timeSelect = document.getElementById("timeSelect_"+index);
		var di = daySelect.selectedIndex;
		var ti = timeSelect.selectedIndex;
		var val = 0;
		if(daySelect.options[di].value != 'custom'){
			val = daySelect.options[di].value;
		}else{
			val = document.getElementById("allDaybeforeTimeInput_"+index).value;
		}
		val *= 1440;
		val -= timeSelect.options[ti].value;
		var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
		beforeTimeInput.value = val - value * 60;
	}
	
	function allDaySetBeforeTime(id){
		var index = id.split("_")[1];
		var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
		var value = document.getElementById(id).value;
		var hourSelect = document.getElementById("hourSelect_"+index);
		var timeSelect = document.getElementById("timeSelect_"+index);
		var hi = hourSelect.selectedIndex;
		var ti = timeSelect.selectedIndex;
		var hourVal = hourSelect.options[hi].value * 60;
		var timeVal = timeSelect.options[ti].value;
		beforeTimeInput.value = value * 24 * 60 - hourVal - timeVal;
	}

	function allDayChangeDay(id,value){
		var index = id.split("_")[1];
		$("#allDayCustomSpan_"+index).remove();
		var hourSelect = document.getElementById("hourSelect_"+index);
		var timeSelect = document.getElementById("timeSelect_"+index);
		var hi = hourSelect.selectedIndex;
		var ti = timeSelect.selectedIndex;
		var hourVal = hourSelect.options[hi].value * 60;
		var timeVal = timeSelect.options[ti].value;
		var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
		if(value != 'custom'){
			var val = 0;
			if(value == 0){
				val -= hi*60-ti;
			}else{
				val = value * 24 * 60 - hourVal - timeVal;
			}
			beforeTimeInput.value = val;
		}else{
			var select = $("#daySelect_"+index);
			select.after("<span id='allDayCustomSpan_"+index+"'>&nbsp;<input value='0' class='inputsgl' validate='number min(0)' id='allDaybeforeTimeInput_"+index+"' type='text' onchange='allDaySetBeforeTime(this.id)' />&nbsp;<bean:message bundle='km-calendar' key='kmCalendarNotifyRemaindMain.earlyDay.one' />&nbsp;</span>");
			beforeTimeInput.value = 0 - hourVal - timeVal;
		}
	}

	function notAllDaySetTimeUnit(id){
		var index = id.split("_")[1];
		var timeUnitInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdTimeUnit")[0];
		var value = document.getElementById(id).value;
		timeUnitInput.value = value;
	}

	function notAllDaySetBeforeTime(id){
		var index = id.split("_")[1];
		var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
		var value = document.getElementById(id).value;
		beforeTimeInput.value = value;
	}

	function notAllDayChangeBeforeTime(id,value){
		var index = id.split("_")[1];
		$("#customSpan_"+index).remove();
		if(value != 'custom'){
			var timeUnit = 'minute';
			if(value == 60 || value == 1440){
				timeUnit = value == 60 ? "hour" : "day";
				value = 1;
			}
			if(value == 120 || value == 2880){
				timeUnit = value == 120 ? "hour" : "day";
				value = 2;
			}
			var beforeTimeInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0];
			beforeTimeInput.value = value;
			var timeUnitInput = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdTimeUnit")[0];
			timeUnitInput.value = timeUnit;
		}else{
			var select = $("#earlyTimeSelect_"+index);
			select.after("<span id='customSpan_"+index+"'>&nbsp;<bean:message bundle='km-calendar' key='kmCalendarNotifyRemaindMain.early' />&nbsp;<input value='0' class='inputsgl' validate='digits min(0)' id='beforeTimeInput_"+index+"' type='text' onchange='notAllDaySetBeforeTime(this.id)' />&nbsp;<select id='timeUnit_"+index+"' onchange='notAllDaySetTimeUnit(this.id)'><option value='minute'><bean:message bundle='sys-notify' key='sysNotify.fdTimeUnit.minute' /></option><option value='hour'><bean:message bundle='sys-notify' key='sysNotify.fdTimeUnit.hour' /></option><option value='day'><bean:message bundle='sys-notify' key='sysNotify.fdTimeUnit.day' /></option><option value='week'><bean:message bundle='sys-notify' key='sysNotify.fdTimeUnit.week' /></option></select></span>");
		}
	}

	function sysNotifyRemind_alert(msg){
		if (typeof(seajs) != 'undefined'){
			seajs.use('lui/dialog', function(dialog) {
				dialog.alert(msg);
			});
		}else{
			alert(msg);
		}
	}
	
	function sysNotifyRemind_validate(notifyDate,isAllDay){
		var fdTimeUnitMap = {
			'minute' : 60 * 1000,
			'hour' : 3600 * 1000,
			'day' : 24 * 3600 * 1000,
			'week' : 7 * 24 * 3600 * 1000
		};
		if(typeof(notifyDate) === 'string'){
			notifyDate = formatDate(notifyDate);
		}
		var dynRowObj = $('#${JsParam.fdPrefix}_${JsParam.fdKey}_'+isAllDay).find("tr");
		for(var i = 0;i < dynRowObj.length;i++){
			var row = dynRowObj.eq(i);
			var fdModelName = $(row).find(':input[name$="fdModelName"]');
			if(!fdModelName.val()){
				continue;
			}
			var fdBeforeTime = $(row).find(':input[name$="fdBeforeTime"]');
			var fdTimeUnit = $(row).find(':input[name$="fdTimeUnit"]');
			if(fdBeforeTime.val() && fdTimeUnit.val()){
				var tmpDate = notifyDate.getTime() - fdTimeUnitMap[fdTimeUnit.val()] * parseInt(fdBeforeTime.val());
				if(tmpDate <= new Date().getTime()){
					var msg="";
					if(isAllDay){
						var beforeTime = fdBeforeTime.val();
						var timeUnitText = "";
						var minuteUnitText = "";
						msg = "%beforeTime%&nbsp;<bean:message bundle='km-calendar' key='kmCalendarNotifyRemaindMain.earlyDay.one' />&nbsp;%timeUnitText%&nbsp;<bean:message key='date.interval.hour' />&nbsp;%minuteUnitText%&nbsp;<bean:message key='date.interval.minute' />&nbsp;<bean:message bundle='km-calendar' key='kmCalendarNotifyRemaindMain.earlyDay.Notify.one' />";
						if(beforeTime<=0){
							timeUnitText = Math.abs(Math.ceil(beforeTime%1440/60));
							minuteUnitText = Math.abs(Math.ceil(beforeTime%60));
							beforeTime = "<bean:message bundle='km-calendar' key='kmCalendarNotifyRemaindMain.earlyDay.0' />";
						}else{
							timeUnitText = 24 - Math.ceil(beforeTime%1440/60);
							minuteUnitText =Math.ceil(beforeTime%60)!=0?60 - Math.ceil(beforeTime%60):0;
							beforeTime = Math.ceil(beforeTime/1440);
						}
						msg = msg.replace('%beforeTime%',beforeTime).replace('%timeUnitText%',timeUnitText).replace('%minuteUnitText%',minuteUnitText);
					}else{
						var fdTimeUnitText = {'minute':'<bean:message key="date.interval.minute"/>','hour':'<bean:message key="date.interval.hour"/>','day':'<bean:message key="date.interval.day"/>','week':'<bean:message key="calendar.type.week"/>'};
						msg = '${lfn:message("sys-notify:sysNotifyRemindCommon.check.msg")}'
							.replace('%beforeTime%',fdBeforeTime.val())
							.replace('%timeUnitText%',fdTimeUnitText[fdTimeUnit.val()]);
					}
					sysNotifyRemind_alert(msg);
					return false;
				}
			}
		}
		return true;
	}
	
	//删除消息列
	function sysNotifyRemind_Delete(self){
		var tr=$(self).parents("tr").eq(0);
		tr.find("[name^='sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList']").val("");
		tr.find("[name$='fdModelName']").val("");
		tr.find("[name$='fdBeforeTime']").attr("validate",'');
		tr.hide();
	}
</script>