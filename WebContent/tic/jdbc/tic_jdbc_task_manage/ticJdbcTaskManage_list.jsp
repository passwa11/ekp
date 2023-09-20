<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page import="
	java.util.Date,
	com.landray.kmss.util.DateUtil,
	com.landray.kmss.util.ResourceUtil" %>
<%@page import="com.landray.kmss.tic.jdbc.model.TicJdbcTaskManage"%>
<%@page import="com.landray.kmss.sys.quartz.scheduler.CronExpression"%>

	
<%@ include file="/resource/jsp/list_top.jsp"%>

<html:form action="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do" />?method=add&fdtemplatId=${param.categoryId}');">
		</kmss:auth>
		
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do?method=chgenabled">
			<input type="button" value="<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.button.enable"/>"
				onclick="if(confirmEnable('<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.dialog.comfirmEnable"/>')){
				document.getElementsByName('fdIsEnabled')[0].value=true;
				Com_Submit(document.ticJdbcTaskManageForm, 'chgenabled')
			}"/>
		</kmss:auth>
		
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do?method=chgenabled">
			<input type="button" value="<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.button.disable"/>"
				onclick="if(confirmEnable('<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.dialog.comfirmDisable"/>')){
				document.getElementsByName('fdIsEnabled')[0].value=false;
				Com_Submit(document.ticJdbcTaskManageForm, 'chgenabled')
			}"/>
		</kmss:auth>
		
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcTaskManageForm, 'deleteall');">
		</kmss:auth>
	</div>
	
<input type="hidden" name="fdIsEnabled">
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				
				<c:if test="${param.sysJob=='0'}">
					<sunbor:column property="ticJdbcTaskManage.fdSubject">
							<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.fdSubject"/>
					</sunbor:column>
				</c:if>
				<c:if test="${param.sysJob!='0'}">
					<td>
						<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.fdSubject"/>
					</td>
				</c:if>
				
				<td>
					<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.fdCronExpression"/>
				</td>
				
				<td>
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.nextTime"/>
				</td>
				
				 <td>
					<bean:message bundle="sys-quartz" key="sysQuartzJob.fdRunType"/>
				</td>
				
				<td>
					<bean:message bundle="tic-jdbc" key="ticJdbcTaskManage.button.enable"/>
				</td>
			</sunbor:columnHead>
		</tr>
		
		<logic:iterate id="ticJdbcTaskManage" name="queryPage" property="list" indexId="index" >
			<tr 
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do?method=view&fdId=${ticJdbcTaskManage.fdId}"/>">
				<td>
					<input type="checkbox" name="List_Selected" value="<bean:write name="ticJdbcTaskManage" property="fdId"/>">
				</td>
				
				<td>
					<%
					TicJdbcTaskManage jobModel = (TicJdbcTaskManage)ticJdbcTaskManage;
						if(jobModel.getFdIsSysJob().booleanValue()){
						    System.out.println("ssss");
							out.print(ResourceUtil.getString(jobModel.getFdSubject(), request.getLocale()));
						}else{
					%>
						<bean:write name="ticJdbcTaskManage" property="fdSubject"/> 
					<%
						}
					%>
				</td>
				
				<!-- 触发时间-->
				<td>
					<c:import url="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage_showCronExpression.jsp" charEncoding="UTF-8">
						<c:param name="value" value="${ticJdbcTaskManage.fdCronExpression}" />
					</c:import>
				</td>
				
				<!-- 下次运行时间 -->
				<td>
					<%
						if(jobModel.getFdIsEnabled().booleanValue()){
							CronExpression expression = new CronExpression(jobModel.getFdCronExpression());
							Date nxtTime = expression.getNextValidTimeAfter(new Date());
							if(nxtTime!=null)
								out.write(DateUtil.convertDateToString(nxtTime,DateUtil.TYPE_DATETIME, request.getLocale()));
						}
					%>
				</td>
				
				<!-- 运行类型 -->
				<td>
					<sunbor:enumsShow value="${ticJdbcTaskManage.fdRunType}" enumsType="sysQuartzJob_fdRunType" />
				</td>
				
				<!--是否启用 -->
				<td>
				      <sunbor:enumsShow value="${ticJdbcTaskManage.fdIsEnabled}" enumsType="common_yesno" />
				</td>
				
				
			</tr>
		</logic:iterate>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<script>
function confirmEnable(info){
	return List_CheckSelect() && confirm(info);
}
</script>
<%@ include file="/resource/jsp/list_down.jsp"%>