<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<c:if test="${kmImissiveUnitForm.fdNature ne '2'}">
			<kmss:auth requestURL="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				 <ui:button text="${ lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('kmImissiveUnit.do?method=edit&fdId=${param.fdId}','_self');">
				 </ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				 <ui:button text="${ lfn:message('button.delete') }" order="2" onclick="Delete();">
				 </ui:button>
			</kmss:auth>
		</c:if>
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		 </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<script>
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.dialog=dialog;
});
function Delete(){
	Com_Delete_Get('<c:url value="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=delete&fdId=${param.fdId}"/>', 'com.landray.kmss.km.imissive.model.KmImissiveUnit');
};
	
</script>
<p class="txttitle"><bean:message  bundle="sys-unit" key="table.kmImissiveUnit"/></p>
<center>
<table class="tb_normal" width=100%>
		<html:hidden name="kmImissiveUnitForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnit.fdCategoryId"/>
		</td>
		<td colspan="3">
			<c:out value="${kmImissiveUnitForm.fdCategoryName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdNature"/>
		</td>
		<td colspan="3">
			<sunbor:enumsShow value="${kmImissiveUnitForm.fdNature}" enumsType="kmImissiveUnit.fdNature4show" />
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdName"/>
		</td>
		<td width=85% colspan="3">
			<c:out value="${kmImissiveUnitForm.fdName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdShortName"/>
		</td><td width=35%>
			<c:out value="${kmImissiveUnitForm.fdShortName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdSocialCreditCode"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitForm.fdSocialCreditCode}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdCode"/>
		</td>
		<td width=35%>
			<xform:text property="fdCode" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="sysUnit.fdPronounsCode"/><br>
		</td>
		<td width=35%>
			<xform:text property="fdPronounsCode" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdUnitLeader"/>
		</td>
		<td width=35%>
			<xform:address  subject="${ lfn:message('sys-unit:kmImissiveUnit.fdLeader')}" propertyName="fdUnitLeaderName" propertyId="fdUnitLeaderId" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%" ></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<c:if test="${kmImissiveUnitForm.fdNature!='0'}">
			<bean:message bundle="sys-unit" key="kmImissiveUnit.fdSecretaryId"/>
			</c:if>
		</td>
		<td width=35%>
			<c:if test="${kmImissiveUnitForm.fdNature!='0'}">
			<c:out value="${kmImissiveUnitForm.fdSecretaryNames}" />
			<input type="checkbox" name="CanReturn" disabled="disabled"  <c:if test="${kmImissiveUnitForm.fdCanReturn=='true'}" >checked</c:if>><bean:message  bundle="sys-unit" key="kmImissiveUnit.fdCanReturn"/>
			</c:if>
		</td>
	</tr>
	<c:if test="${not empty listSecretary}">
		<tr>
			<td colspan="4">
				<table class="tb_normal" width=99% >
					<tr><td colspan="4" style="font-size: 110%;font-weight: bold;">文书信息</td></tr>
					<tr>
						<td width="5%" class="td_normal_title"><bean:message key="page.serial" /></td>
						<td width="15%" class="td_normal_title">名字</td>
						<td width="15%" class="td_normal_title">所在部门</td>
						<td class="td_normal_title">负责单位</td>
					</tr>
					<c:forEach items="${listSecretary}" var="sysUnitSecretary" varStatus="vstatus">
						<tr>
							<td>${vstatus.index+1}</td>
							<td><c:out value="${sysUnitSecretary.fdPerson.fdName}" /></td>
							<td><c:out value="${sysUnitSecretary.fdBelongUnit.fdName}" /></td>
							<td>
								<c:forEach items="${sysUnitSecretary.fdUnits}" var="fdUnit" varStatus="vstatus">
									<c:out value="${fdUnit.fdName}" />;
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty listSecretary}">
		<tr>
			<td colspan="4">
				<table class="tb_normal" width=99% >
					<tr><td colspan="4" style="font-size: 110%;font-weight: bold;">联络员信息</td></tr>
					<tr>
						<td width="5%" class="td_normal_title"><bean:message key="page.serial" /></td>
						<td width="15%" class="td_normal_title">名字</td>
						<td width="15%" class="td_normal_title">所在部门</td>
						<td class="td_normal_title">负责单位</td>
					</tr>
					<c:forEach items="${listSecretary}" var="sysUnitSecretary" varStatus="vstatus">
						<tr>
							<td>${vstatus.index+1}</td>
							<td><c:out value="${sysUnitSecretary.fdSupervisePerson.fdName}" /></td>
							<td><c:out value="${sysUnitSecretary.fdBelongUnit.fdName}" /></td>
							<td>
								<c:forEach items="${sysUnitSecretary.fdUnits}" var="fdUnit" varStatus="vstatus">
									<c:out value="${fdUnit.fdName}" />;
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width=15%>
			分管领导
		</td>
		<td width=85% colspan="3">
			<c:out value="${kmImissiveUnitForm.fdBrunchLeaderNames}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdMeetingLiaison"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitForm.fdMeetingLiaisonNames}" />
		</td>
		<td class="td_normal_title" width=15%>
		  <bean:message  bundle="sys-unit" key="kmImissiveUnit.fdSuperViseLiaison"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitForm.fdSuperViseLiaisonNames}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdProposalLiaison"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitForm.fdProposalLiaisonNames}" />
		</td>
		<td class="td_normal_title" width=15%>
		  <bean:message  bundle="sys-unit" key="kmImissiveUnit.fdAdviseLiaison"/>
		</td>
		<td width=35%>
			<c:out value="${kmImissiveUnitForm.fdAdviseLiaisonNames}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdOrder"/>
		</td><td width=35%>
			<c:out value="${kmImissiveUnitForm.fdOrder}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdIsAvailable"/>
		</td><td width=35%>
			<sunbor:enumsShow value="${kmImissiveUnitForm.fdIsAvailable}" enumsType="common_yesno" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdContent"/>
		</td><td width=35% colspan='3'>
			<kmss:showText value="${kmImissiveUnitForm.fdContent}"/>
		</td>
	</tr>
	<tr>
			<td class="td_normal_title" width="15%">
			  <bean:message bundle="sys-unit" key="kmImissiveUnit.areader.distribute"/>
			</td>
			<td width="85%" colspan='3'>
			 ${kmImissiveUnitForm.authReaderNamesDistribute}
			</td>
	</tr>
	<tr>
			<td class="td_normal_title" width="15%">
			  <bean:message bundle="sys-unit" key="kmImissiveUnit.areader.report"/>
			</td>
			<td width="85%" colspan='3'>
			   ${kmImissiveUnitForm.authReaderNamesReport}
			</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnit.docCreateId"/>
		</td><td width=35%>
			<c:out value="${kmImissiveUnitForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.docCreateTime"/>
		</td><td width=35%>
			<c:out value="${kmImissiveUnitForm.docCreateTime}" />
		</td>
	</tr>
</table>
</center>
</template:replace>
</template:include>