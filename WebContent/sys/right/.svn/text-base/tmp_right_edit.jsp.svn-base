<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.sys.right.interfaces.IExtendAuthTmpForm" %>
<%@ page import="com.landray.kmss.sys.right.interfaces.IBaseAuthTmpForm" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
	String moduleModelName = request.getParameter("moduleModelName");
	Set propertyNameSet =  SysDataDict.getInstance().getModel(moduleModelName).getPropertyMap().keySet();
	if(moduleModelName.indexOf("KmsTrainPlanCategory")>-1){ //培训活动模块暂不需要默认可编辑者
		if(propertyNameSet.contains("authTmpEditors")){
			propertyNameSet.remove("authTmpEditors");
		}
	}
	//可指定地址本类型（#127639）
	String orgType = request.getParameter("orgType");
	if (StringUtil.isNull(orgType)){
		orgType = "ORG_TYPE_ALL|ORG_TYPE_ROLE";
	}
	
%>
<c:set var="tmpRightForm" value="${requestScope[param.formName]}" />
	<% if(propertyNameSet.contains("authTmpReaders")){ %>
		<tr>
			<td class="td_normal_title" width="15%"><bean:message
				bundle="sys-right" key="right.read.authTmpReaders" /></td>
			<td width="85%">
		<% if(propertyNameSet.contains("authChangeReaderFlag")){ %>				
				<html:checkbox property="authChangeReaderFlag" value="1"/><bean:message bundle="sys-right" key="right.read.authChangeReaderFlag" />					
			<% } %>	
			<% if(propertyNameSet.contains("authRBPFlag")){ %>	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<bean:message bundle="sys-right" key="right.effect.time" />	
					<sunbor:enums property="authRBPFlag" enumsType="sys_rbp_flag" elementType="radio" bundle="sys-right"/>		
			<% } %>					
			<html:hidden property="authTmpReaderIds" />
			<xform:address textarea="true" mulSelect="true" propertyId="authTmpReaderIds" propertyName="authTmpReaderNames" orgType="<%=orgType %>" style="width:97%;height:90px;" ></xform:address>
				<br>
				<c:if test="${tmpRightForm.authReaderNoteFlag=='1'}">
				<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				         <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
					        <!-- （为空则本组织人员可使用） -->
					        <bean:message bundle="sys-right" key="right.read.authReaders.organizationNote" arg0="${ecoName}" />
					    <% } else { %>
					        <!-- （为空则所有内部人员可使用） -->
							<c:choose>
								<c:when test="${param.modelingFlag eq true}">
									<bean:message
											bundle="sys-right" key="right.read.authReaders.modeling.note" />
								</c:when>
								<c:otherwise>
									<bean:message
											bundle="sys-right" key="right.read.authReaders.note" />
								</c:otherwise>
							</c:choose>
					    <% } %>
				
					 <% } else { %>
					<!-- （为空则所有内部人员可使用） -->
						<c:choose>
							<c:when test="${param.modelingFlag eq true}">
								<bean:message
										bundle="sys-right" key="right.read.authReaders.modeling.note" />
							</c:when>
							<c:otherwise>
								<bean:message bundle="sys-right" key="right.read.authReaders.nonOrganizationNote" />
							</c:otherwise>
						</c:choose>
					<% } %>
				</c:if>
				<c:if test="${tmpRightForm.authReaderNoteFlag=='2'}">
				<bean:message
				bundle="sys-right" key="right.read.authReaders.note1" />
				</c:if><br>
						
			</td>
		</tr>
		<%} %>
		
		<% if(propertyNameSet.contains("authTmpEditors")){ %>
		<tr>
			<td class="td_normal_title"><bean:message bundle="sys-right"
				key="right.edit.authTmpEditors" /></td>
			<td>
			<% if(propertyNameSet.contains("authChangeEditorFlag")){ %>				
				<html:checkbox property="authChangeEditorFlag" value="1"/><bean:message bundle="sys-right" key="right.edit.authChangeEditorFlag" />						
			<% } %>
			<br>
			<html:hidden property="authTmpEditorIds" />
			<xform:address textarea="true" mulSelect="true" propertyId="authTmpEditorIds" propertyName="authTmpEditorNames" orgType="<%=orgType %>" style="width:97%;height:90px;" ></xform:address>
				<br>
				<bean:message
				bundle="sys-right" key="right.read.authEditors.note" />				
			</td>
		</tr>
		<%} %>
		
		<% if(propertyNameSet.contains("authTmpAttCopys")
				|| propertyNameSet.contains("authTmpAttDownloads")
				|| propertyNameSet.contains("authTmpAttPrints")){ %>
		<tr>
			<td class="td_normal_title" width="15%"><bean:message
				bundle="sys-right" key="right.att.label" /></td>
			<td width="85%">
			<% if(propertyNameSet.contains("authChangeAtt")){ %>				
				<html:checkbox property="authChangeAtt" value="1"/><bean:message bundle="sys-right" key="right.att.authChangeAttFlag" /><br>					
			<% } %>
			<% if(propertyNameSet.contains("authTmpAttCopys")){ 
			boolean isJGEnabled = com.landray.kmss.sys.attachment.util.JgWebOffice
			.isJGEnabled(); 
			boolean isJGPDFEnabled = com.landray.kmss.sys.attachment.util.JgWebOffice.isJGPDFEnabled();
			if(isJGEnabled){
			    if(isJGPDFEnabled){%>
				<bean:message bundle="sys-right" key="right.att.authAttCopys.jg.pdf" />	
				<%}
			    else{%>
			    <bean:message bundle="sys-right" key="right.att.authAttCopys.jg" />		
			   <%}}
			 else{
				 if(isJGPDFEnabled){%>
				 <bean:message bundle="sys-right" key="right.att.authAttCopys.pdf" />
				 <%}
				 else{%>
				 <bean:message bundle="sys-right" key="right.att.authAttCopys" />
			<%}}%>		
			<html:checkbox property="authTmpAttNocopy" value="1" onclick="refreshDisplay(this,'copyDiv')"/>
						<bean:message key="right.att.authAttNocopy" bundle="sys-right"/>
			<br>
			<div id="copyDiv" <c:if test="${tmpRightForm.authTmpAttNocopy == 'true'}">style="display:none"</c:if> > 						
			<html:hidden property="authTmpAttCopyIds" /> 
			<xform:address textarea="true" mulSelect="true" propertyId="authTmpAttCopyIds" propertyName="authTmpAttCopyNames" orgType="<%=orgType %>" style="width:97%;height:90px;" ></xform:address>
				<br>
				 <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				 
				          <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
					        <!-- （为空则本组织人员可使用） -->
					        <bean:message bundle="sys-right" key="right.att.authAttCopys.organizationNote" arg0="${ecoName}" />
					    <% } else { %>
					        <!-- （为空则所有内部人员可使用） -->
					          <bean:message key="right.att.authAttCopys.note" bundle="sys-right"/>
					    <% } %>
          
				 <% } else { %>
			           <bean:message key="right.att.authAttCopys.nonOrganizationNote" bundle="sys-right"/>
				<% } %>
			</div>
			<%} %>
			
			<% if(propertyNameSet.contains("authTmpAttDownloads")){ %>
				<%if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGPDFEnabled()){%>
				    <bean:message bundle="sys-right" key="right.att.authAttDownloads.pdf" />
				<%}else{ %>
					<bean:message bundle="sys-right" key="right.att.authAttDownloads" />
				<%}%>
			<html:checkbox property="authTmpAttNodownload" value="1" onclick="refreshDisplay(this,'downloadDiv')"/>
						<bean:message key="right.att.authAttNodownload" bundle="sys-right"/>
			<br>
			<div id="downloadDiv" <c:if test="${tmpRightForm.authTmpAttNodownload == 'true'}">style="display:none"</c:if> > 						
			<html:hidden property="authTmpAttDownloadIds" />
			<xform:address textarea="true" mulSelect="true" propertyId="authTmpAttDownloadIds" propertyName="authTmpAttDownloadNames" orgType="<%=orgType %>" style="width:97%;height:90px;" ></xform:address>
				<br>
				
				  <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				  
				        <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
					        <!-- （为空则本组织人员可使用） -->
					         <bean:message bundle="sys-right" key="right.att.authAttDownloads.organizationNote" arg0="${ecoName}" />
					    <% } else { %>
					        <!-- （为空则所有内部人员可使用） -->
					          <bean:message key="right.att.authAttDownloads.note" bundle="sys-right"/>
					    <% } %>
                        
				 <% } else { %>
			             <bean:message key="right.att.authAttDownloads.nonOrganizationNote" bundle="sys-right"/>
				<% } %>
			</div>
			<%} %>
			
			<% if(propertyNameSet.contains("authTmpAttPrints")){ %>
				<%if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGPDFEnabled()){%>
				    <bean:message bundle="sys-right" key="right.att.authAttPrints.pdf" />
				<%}else{ %>
					<bean:message bundle="sys-right" key="right.att.authAttPrints" />
				<%}%>
			<html:checkbox property="authTmpAttNoprint" value="1" onclick="refreshDisplay(this,'printDiv')"/>
						<bean:message key="right.att.authAttNoprint" bundle="sys-right"/>
			<br>
			<div id="printDiv" <c:if test="${tmpRightForm.authTmpAttNoprint == 'true'}">style="display:none"</c:if> > 						
			<html:hidden property="authTmpAttPrintIds" />
			<xform:address textarea="true" mulSelect="true" propertyId="authTmpAttPrintIds" propertyName="authTmpAttPrintNames" orgType="<%=orgType %>" style="width:97%;height:90px;" ></xform:address>
				<br>
				 <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				 
				         <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
					        <!-- （为空则本组织人员可使用） -->
					       <bean:message key="right.att.authAttPrints.organizationNote" bundle="sys-right" arg0="${ecoName}"/>
					    <% } else { %>
					        <!-- （为空则所有内部人员可使用） -->
					         <bean:message key="right.att.authAttPrints.note" bundle="sys-right"/>
					    <% } %>
				 <% } else { %>
			             <bean:message key="right.att.authAttPrints.nonOrganizationNote" bundle="sys-right"/>
				<% } %>
			</div>
			<%} %>
			
			</td>
		</tr>	
	<%} %>
			
<script>
function refreshDisplay(obj,divName){
	var divObj = document.getElementById(divName);
	divObj.style.display=(obj.checked?"none":"");
}
</script>
