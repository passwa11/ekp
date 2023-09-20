<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
</script>
<html:form action="/sys/time/sys_time_area/sysTimeArea.do" onsubmit="return validateSysTimeAreaForm(this);">
<div id="optBarDiv">
	<c:if test="${sysTimeAreaForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysTimeAreaForm, 'update');">
	</c:if>
	<c:if test="${sysTimeAreaForm.method_GET=='add' ||sysTimeAreaForm.method_GET=='clone'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysTimeAreaForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysTimeAreaForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="top.close();">
</div>

<p class="txttitle"><bean:message  bundle="sys-time" key="table.sysTimeArea"/></p>

<center>
<table class="tb_normal" width=95%>
	<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeArea.fdName"/>
		</td>
		<td width=85% colspan=3>
			<xform:text property="fdName" style="width:85%" required="true"/>
		</td>
	</tr>
	<%-- 所属场所 --%>
    <%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
		<tr>
			<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
                <c:param name="id" value="${sysTimeAreaForm.authAreaId}"/>
            </c:import>
		</tr>
	<%} %>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeArea.fdHoliday"/>
		</td>
		<td width=85% colspan=3>
			<html:hidden property="fdHolidayId" />
			<html:text property="fdHolidayName" style="width:85%;" readonly="true" styleClass="inputsgl"/>
			<a href="javascript:void(0);" class="add-btn" onclick="selHoliday();">
				<bean:message key="button.select"/>
			</a>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeArea.scope"/>
		</td>
		<td width=85% colspan=3>
			<xform:address textarea="true" mulSelect="true" propertyId="areaMemberIds" propertyName="areaMemberNames" orgType="ORG_TYPE_ORG | ORG_TYPE_DEPT | ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"/>
			<br>
			<c:if test="${sysTimeAreaForm.method_GET=='edit'}">
				<xform:checkbox property="fdIsBatchSchedule" showStatus="noShow" >
					<xform:simpleDataSource value="true">
						<bean:message bundle="sys-time" key="sysTimeArea.isBatch"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysTimeAreaForm.method_GET=='add' ||sysTimeAreaForm.method_GET=='clone'}">
				<xform:checkbox property="fdIsBatchSchedule" showStatus="noShow">
					<xform:simpleDataSource value="true">
						<bean:message bundle="sys-time" key="sysTimeArea.isBatch"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeArea.timeAdmin"/>
		</td>
		<td width=85% colspan=3>
			<xform:address textarea="true" mulSelect="true" propertyId="areaAdminIds" propertyName="areaAdminNames" orgType="ORG_TYPE_PERSON" style="width:97%;height:90px;" required="true"/>
		</td>
	</tr>
	<c:if test="${sysTimeAreaForm.method_GET=='edit'}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeArea.docCreatorId"/>
		</td><td width=35%>
			${sysTimeAreaForm.docCreatorName}
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-time" key="sysTimeArea.docCreateTime"/>
		</td><td width=35%>
			${sysTimeAreaForm.docCreateTime}
		</td>
	</tr>
	</c:if>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<script language="JavaScript">Com_IncludeFile("calendar.js");</script>
<script>
	$KMSSValidation();
</script>
<script>
function selHoliday(){
	Dialog_List(false, "fdHolidayId", "fdHolidayName", ';',"sysTimeHolidayService",null,"sysTimeHolidayService&search=!{keyword}", false, false,'<bean:message  bundle="sys-time" key="table.sysTimeHoliday"/>');
}
</script>
<html:javascript formName="sysTimeAreaForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>