<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.smissive.util.KmSmissiveConfigUtil"%>
<%@page import="com.landray.kmss.sys.number.util.NumberResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>

<ui:content title="${lfn:message('km-smissive:kmSmissiveMain.label.baseinfo')}" expand="true">
	<table class="tb_normal" width=100% id="Table_Main">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.docSubject"/>
		</td><td width=85% colspan="3">
			<xform:text property="docSubject" style="width:97%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.docAuthorId"/>
		</td><td width=35%>
		    <xform:address propertyId="docAuthorId" propertyName="docAuthorName" orgType="ORG_TYPE_PERSON"  className="inputsgl" style="width:95%" required="true" subject="${ lfn:message('km-smissive:kmSmissiveMain.docAuthorId') }"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.docCreateTime"/>
		</td><td width=35%>
		   <xform:datetime property="docCreateTime" dateTimeType="date" style="width:95%" showStatus="readOnly" className="inputsgl"></xform:datetime>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdUrgency"/>
		</td><td width=35%>
			<sunbor:enums property="fdUrgency"
						enumsType="km_smissive_urgency" elementType="select"
						bundle="km-smissive" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdSecret"/>
		</td><td width=35%>
			<sunbor:enums property="fdSecret"
						enumsType="km_smissive_secret" elementType="select"
						bundle="km-smissive" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdTemplateId"/>
		</td><td width=35%>
		    <c:out value="${kmSmissiveMainForm.fdTemplateName }"></c:out>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdFileNo"/>
		</td><td width=35%>
		<input type="hidden" name="fdFileNo" value="${kmSmissiveMainForm.fdFileNo}" />
		<c:choose>
		    <c:when test="${aaa['modifyDocNum4Draft'] == 'true'}">
				<span id="docnum"> 
				  <c:if test="${not empty kmSmissiveMainForm.fdFileNo}">
				   <c:out value="${kmSmissiveMainForm.fdFileNo}"/>
				  </c:if>
				</span>
			</c:when>
			<c:otherwise>
			     <c:if test="${not empty kmSmissiveMainForm.fdFileNo}">
				   <c:out value="${kmSmissiveMainForm.fdFileNo}"/>
				</c:if>
				 <c:if test="${empty kmSmissiveMainForm.fdFileNo}">
				  <bean:message  bundle="km-smissive" key="kmSmissiveMain.fdFileNo.describe"/>
				</c:if>
			</c:otherwise>
		 </c:choose>
		</td>
	</tr>
	<%-- 所属场所 --%>
	<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
            <c:param name="id" value="${kmSmissiveMainForm.authAreaId}"/>
       </c:import>	        
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdMainDeptId"/>
		</td><td width=35%>
		    <xform:address propertyId="fdMainDeptId" propertyName="fdMainDeptName"  orgType="ORG_TYPE_ORGORDEPT"  className="inputsgl" style="width:95%" required="true" subject="${ lfn:message('km-smissive:kmSmissiveMain.fdMainDeptId') }"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.docDeptId"/>
		</td><td width=35%>
			<html:hidden property="docDeptId"/>
			<c:out value="${kmSmissiveMainForm.docDeptName }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdSendDeptId"/>
		</td><td  width=35%>
		    <xform:address propertyId="fdSendDeptIds" propertyName="fdSendDeptNames" textarea="true" orgType="ORG_TYPE_ALL" mulSelect="true" style="width:95%;height:90px"  className="inputmul"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdCopyDeptId"/> 
		</td><td width=35%>
		    <xform:address propertyId="fdCopyDeptIds" propertyName="fdCopyDeptNames" textarea="true" orgType="ORG_TYPE_ALL" mulSelect="true" style="width:95%;height:90px"  className="inputmul"></xform:address>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdIssuerId"/>
		</td><td width=35%>
		    <xform:address propertyId="fdIssuerId" propertyName="fdIssuerName" orgType="ORG_TYPE_PERSON" className="inputsgl" style="width:95%"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.docCreatorId"/>
		</td><td width=35%>
		   <c:out value="${kmSmissiveMainForm.docCreatorName }"></c:out>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMainProperty.fdPropertyId"/>
		</td><td width=85% colspan="3">
			<xform:dialog style="width:97%;" propertyId="docPropertyIds" propertyName="docPropertyNames">
				Dialog_property(true, 'docPropertyIds','docPropertyNames', ';', ORG_TYPE_PERSON);
			</xform:dialog>
		</td>
	</tr>
	<!-- 标签机制 -->
	<c:import url="/sys/tag/import/sysTagMain_edit.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmSmissiveMainForm" />
		<c:param name="fdKey" value="smissiveDoc" /> 
		<c:param name="modelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
		<c:param name="fdQueryCondition" value="fdTemplateId" /> 
	</c:import>
	</table>
</ui:content>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		 <c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmSmissiveMainForm" />
			<c:param name="fdKey" value="smissiveDoc" />
			<c:param name="showHistoryOpers" value="true" />
			<c:param name="isExpand" value="true" />
			<c:param name="approveType" value="right" />
		</c:import>
	</c:when>
	<c:otherwise>
		<%-- 以下代码为嵌入流程模板标签的代码 --%>
	  <c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmSmissiveMainForm" />
			<c:param name="fdKey" value="smissiveDoc" />
			<c:param name="isExpand" value="true" />
	   </c:import>
	</c:otherwise>
</c:choose>
<%-- 以上代码为嵌入流程模板标签的代码 --%>
<%---发布机制 开始---%>
<c:import url="/sys/news/import/sysNewsPublishMain_edit.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmSmissiveMainForm" />
	<c:param name="fdKey" value="smissiveDoc" />
	<c:param name="isShow" value="true" /><%--是否显示--%>
</c:import>
<%---发布机制 结束---%>
<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmSmissiveMainForm" />
	<c:param name="moduleModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
</c:import>
	

