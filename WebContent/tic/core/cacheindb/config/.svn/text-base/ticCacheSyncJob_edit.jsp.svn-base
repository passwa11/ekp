<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/core/cacheindb/config/ticCacheSyncJob.do">
<div id="optBarDiv">
	
		<input type=button value="<bean:message key="button.update"/>"
			onclick="submitForm();">

			<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCacheSyncJob.do?method=delete&fdId=${ticCacheSyncJobForm.fdId}','_self');">

		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<%@ include file="/tic/core/cacheindb/config/ticCacheSyncJob_script.jsp"%>
<p class="txttitle"><bean:message bundle="tic-core-cacheindb" key="table.ticCacheSyncJob"/></p>

<center>
<table class="tb_normal" width=95% style="table-layout: fixed;">
	<tr>
		<td class="td_normal_title">
				<bean:message bundle="tic-core-cacheindb" key="ticCacheSyncJob.fdUseCache"/>
		</td><td colspan="3">
				<sunbor:enums property="fdUseCache" enumsType="common_yesno" htmlElementProperties="onclick=showUseCache()" elementType="radio" />
		</td>
	</tr>
	</tr>
</table>
<table class="tb_normal" width=95% style="table-layout: fixed;" id="TB_MainTable">
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.fdSubject"/>
		</td><td colspan="3">
			<xform:text property="fdSubject" style="width:85%" required="true" subject="${lfn:message('sys-quartz:sysQuartzJob.fdSubject') }"  />
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.fdCronExpression"/>
		</td><td width=35%>
			<c:import url="/sys/quartz/sys_quartz_job/sysQuartzJob_showCronExpression.jsp" charEncoding="UTF-8">
				<c:param name="value" value="${ticCacheSyncJobForm.fdTempCronExpression}" />
			</c:import>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.nextTime"/>
		</td><td width=35%>
			<c:out value="${ticCacheSyncJobForm.fdRunTime}"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.fdRunType"/>
		</td><td>
			<xform:select property="fdRunType" onValueChange="onRunTypeChange" showStatus="edit">
				<xform:enumsDataSource enumsType="sysQuartzJob_fdRunType" />
			</xform:select>
			<xform:select property="fdRunServer" style="${ticCacheSyncJobForm.fdRunType=='2' ? '' : 'display:none;' }" showStatus="edit" showPleaseSelect="false">
				<xform:customizeDataSource className="com.landray.kmss.sys.cluster.interfaces.ServerDataSource" />
			</xform:select>
		</td>
		<td class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.inputType"/>
		</td>
		<td>
			<label><input type="radio" name="fdInputType" value="0" checked onclick="changeInputType(value);"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.inputType.normal"/></label>
			<label><input type="radio" name="fdInputType" value="1" onclick="changeInputType(value);"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.inputType.cronExpression"/></label>
		</td>
	</tr>
	<tr kmss_inputtype="0">
		<td class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency"/>
		</td>
		<td colspan="3">
			<select name="fdFrequency" onchange="refreshInpuType0();">
				<option value=""><bean:message key="page.firstOption" /></option>
				<option value="once"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency.once"/></option>
				<option value="year"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency.year"/></option>
				<option value="month"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency.month"/></option>
				<option value="week"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency.week"/></option>
				<option value="day"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency.day"/></option>
				<option value="hour"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency.hour"/></option>
				<option value="every"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.frequency.every"/></option>
			</select>
		</td>
	</tr>
	<tr kmss_inputtype="0" id="TR_FrequencySetting" style="display:none">
		<td class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.timeSetting"/>
		</td>
		<td colspan="3">
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.year1"/><input name="fdYear" size="4" class="inputSgl"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.year2"/></span>
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.month1"/><input name="fdMonth" size="2" class="inputSgl"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.month2"/></span>
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.day1"/><input name="fdDay" size="2" class="inputSgl"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.day2"/></span>
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.week1"/><select name="fdWeek">
				<c:forEach begin="0" end="6" var="i">
					<option value="${i+1}"><bean:message key="date.weekDay${i}" /></option>
				</c:forEach>
			</select><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.week2"/></span>
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.hour1"/><input name="fdHour" size="2" class="inputSgl"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.hour2"/></span>
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.minute1"/><input name="fdMinute" size="2" class="inputSgl"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.minute2"/></span>
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.second1"/><input name="fdSecond" size="2" class="inputSgl"><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.second2"/></span>
			<span><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.every1"/><select name="fdEvery">
				<option value="5">5</option>
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
				<option value="30">30</option>
			</select><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.field.every2"/></span>
		</td>
	</tr>
	<tr kmss_inputtype="1" style="display:none">
		<td class="td_normal_title">
			<bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.inputType.cronExpression"/>
		</td>
		<td colspan="3">
			<html:text property="fdCronExpression" style="width:80%"/>
			<a href="#" onclick="isShowHelp=!isShowHelp; refreshInpuType1();">
				<bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.help"/>
			</a>
		</td>
	</tr>
	<tr kmss_inputtype="1" id="TR_CronExpressionHelp" style="display:none">
		<td colspan="4">
			<%@ include file="/sys/quartz/sys_quartz_job/sysQuartzJob_cronExpressionHelp.jsp"%>
			<br><bean:message bundle="sys-quartz" key="sysQuartzJob.cronExpression.helpLink"/>
			<a href="http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html" target="_blank">
				http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html
			</a>
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId"/>
<html:hidden property="fdRelationId" value="${param.funcId}"/>
<html:hidden property="fdKey" value="${param.fdKey}" />
<html:hidden property="fdTempCronExpression"/>
<html:hidden property="method_GET"/>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>