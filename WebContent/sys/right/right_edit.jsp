<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.Set,java.util.List" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.sys.right.interfaces.IExtendAuthForm" %>
<%@ page import="com.landray.kmss.sys.right.interfaces.IBaseAuthForm" %>
<%@ page import="com.landray.kmss.sys.attachment.util.JgWebOffice" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.builder.NodeInstance" %>
<%
	String moduleModelName = request.getParameter("moduleModelName");
	String formName = request.getParameter("formName");
	Set propertyNameSet =  SysDataDict.getInstance().getModel(moduleModelName).getPropertyMap().keySet();
%>
<c:set var="rightForm" value="${requestScope[param.formName]}" />
    <%-- 主模型中含有默认读者设置时显示逻辑--%>
	<% if(propertyNameSet.contains("authReaders")){ %>
		<tr>
			<td class="td_normal_title" width="15%">
			   <bean:message bundle="sys-right" key="right.read.authReaders" />
			</td>
			<td width="85%">
				<% if(propertyNameSet.contains("authRBPFlag")){ %>
			    <html:hidden property="authRBPFlag"  value="${rightForm.authRBPFlag}"/>
			    <% } %>
			    <% if(propertyNameSet.contains("authChangeReaderFlag")){ %>
			    <html:hidden property="authChangeReaderFlag"  value="${rightForm.authChangeReaderFlag}"/>
			    <% } %>
				<%--是否允许起草人更改默认阅读者--%>
				<%
					boolean authChangeReader = true;
					if (propertyNameSet.contains("authChangeReaderFlag")
							&& ((IBaseAuthForm) pageContext.getAttribute("rightForm")).getAuthChangeReaderFlag() != null
							&& ((IBaseAuthForm) pageContext.getAttribute("rightForm")).getAuthChangeReaderFlag() == true) {
						try {
							// 获取当前流程节点信息
							ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil.getBean("lbpmProcessExecuteServiceTarget");
							ProcessInstanceInfo processInstanceInfo = processExecuteService.load(((IBaseAuthForm) pageContext.getAttribute("rightForm")).getFdId());
							// 如果不是当前处理人，不允许修改阅读者
							if (processInstanceInfo != null && processInstanceInfo.isHandler()) {
								List<NodeInstance> currentNodes = processInstanceInfo.getCurrentNodes();
								if (currentNodes != null && currentNodes.size() > 0) {
									// 是否起草节点
									boolean isDraft = false;
									for (NodeInstance node : currentNodes) {
										if ("N2".equalsIgnoreCase(node.getFdFactNodeId())) {
											isDraft = true;
											break;
										}
									}
									// 起草节点不允许修改阅读者
									if (isDraft) {
										authChangeReader = false;
									}
								}
							} else {
								authChangeReader = false;
							}
						} catch (Exception e) {}
					}
				%>
				<% if(!authChangeReader){ %>
					<c:if test="${empty rightForm.authReaderNames}">
						<c:if test="${rightForm.authReaderNoteFlag=='1'}">
							<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
							    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
							        <bean:message bundle="sys-right" key="right.all.person.outter" arg0="${ecoName}" />
							    <% } else { %>
							        <bean:message bundle="sys-right" key="right.all.person.inner" />
							    <% } %>
							<% } else { %>
							    <bean:message bundle="sys-right" key="right.all.person" />
							<% } %>
						</c:if>
						<c:if test="${rightForm.authReaderNoteFlag=='2'}">
							<bean:message bundle="sys-right" key="right.other.person" />
						</c:if>
					</c:if>
					<c:if test="${not empty rightForm.authReaderNames}">
						 <html:hidden property="authReaderIds"/>
						${rightForm.authReaderNames}
					</c:if>
				<% }else{ %>
					<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:97%;height:90px;" ></xform:address>
					<br>
					<c:if test="${rightForm.authReaderNoteFlag=='1'}">
						<span class="com_help">
							<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
							    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
							        <!-- （为空则本组织人员可使用） -->
							        <bean:message  bundle="sys-right" key="right.read.authReaders.organizationNote" arg0="${ecoName}" />
							    <% } else { %>
							        <!-- （为空则所有内部人员可使用） -->
							        <bean:message bundle="sys-right" key="right.read.authReaders.note" />
							    <% } %>
							<% } else { %>
							    <!-- （为空则所有人可使用） -->
							    <bean:message  bundle="sys-right" key="right.read.authReaders.nonOrganizationNote" />
							<% } %>
						</span>
					</c:if>
					<c:if test="${rightForm.authReaderNoteFlag=='2'}">
						<span class="com_help"><bean:message bundle="sys-right" key="right.read.authReaders.note1" /></span>
					</c:if>
				<% }%>
			</td>
		</tr>
		<% } %>
		<%-- 主模型中含有默认编辑者设置时显示逻辑--%>
		<% if(propertyNameSet.contains("authEditors")){ %>
		<tr>
			<td class="td_normal_title">
				<bean:message bundle="sys-right" key="right.edit.authEditors" />
			</td>
			<td>
			   <% if(propertyNameSet.contains("authChangeEditorFlag")){ %>
			    <html:hidden property="authChangeEditorFlag"  value="${rightForm.authChangeEditorFlag}"/>
			    <% } %>
				<%--是否允许起草人更改默认编辑者--%>	
				<% if(propertyNameSet.contains("authChangeEditorFlag") && ((IBaseAuthForm)pageContext.getAttribute("rightForm")).getAuthChangeEditorFlag()!=null&& ((IBaseAuthForm)pageContext.getAttribute("rightForm")).getAuthChangeEditorFlag() == true ){ %>
					<c:if test="${empty rightForm.authEditorNames}">
						<bean:message bundle="sys-right" key="right.other.person.edit" />
					</c:if>
					<c:if test="${not empty rightForm.authEditorNames}">
						 <html:hidden property="authEditorIds"/>
						${rightForm.authEditorNames}
					</c:if>
				<% }else{ %>
					<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:97%;height:90px;" ></xform:address>
					<br>
					<span class="com_help">
						<c:if test = "${empty param.isModeling}">
							<bean:message bundle="sys-right" key="right.read.authEditors.note" />
						</c:if>
						<c:if test="${not empty param.isModeling}">
							<bean:message bundle="sys-right" key="right.read.authEditors.modelingNote" />
						</c:if>
					</span>		
				<% } %>		
			</td>
		</tr>
		<%} %>
		<%-- 主模型中含有附件权限设置时显示逻辑--%>
		<% if(propertyNameSet.contains("authAttCopys")
				|| propertyNameSet.contains("authAttDownloads")
				|| propertyNameSet.contains("authAttPrints")){ %>
		<tr>
			<td class="td_normal_title" width="15%">
				<bean:message bundle="sys-right" key="right.att.label" />
			</td>
			<td width="85%">
			    <% if(propertyNameSet.contains("authChangeAtt")){ %>
			    <html:hidden property="authChangeAtt"  value="${rightForm.authChangeAtt}"/>
			    <% } %>
				<%--是否允许起草人更改默认的附件权限设置--%>	
				<% if(propertyNameSet.contains("authChangeAtt")&& ((IExtendAuthForm)pageContext.getAttribute("rightForm")).getAuthChangeAtt()!=null && ((IExtendAuthForm)pageContext.getAttribute("rightForm")).getAuthChangeAtt().booleanValue() == true ){ %>
					<%--附件拷贝--%>	
					<% if(propertyNameSet.contains("authAttCopys")){ 
						boolean isJGEnabled = com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled(); 
						if(isJGEnabled){%>
							<bean:message bundle="sys-right" key="right.att.authAttCopys.jg" />								
						<%}else{ %>
							<bean:message bundle="sys-right" key="right.att.authAttCopys" />	
						<%} %>	
						<html:hidden property="authAttCopyIds" />
						<html:hidden property="authAttNocopy" />
						<c:if test="${rightForm.authAttNocopy == 'true'}">
							<bean:message key="right.att.authAttNocopy" bundle="sys-right"/>
						</c:if>
						<c:if test="${rightForm.authAttNocopy != 'true'}">
							<c:if test="${empty rightForm.authAttCopyNames}">
								<!-- <bean:message bundle="sys-right" key="right.no.restr" /> -->
								<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
										<% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
										     <!-- （为空则本组织人员可使用） -->
										     <bean:message  bundle="sys-right" key="right.no.restr.outter" arg0="${ecoName}" />
										 <% } else { %>
										     <!-- （为空则所有内部人员可使用） -->
										     <bean:message  bundle="sys-right" key="right.no.restr.inner" />
										 <% } %>
										<% } else { %>
										 <!-- （为空则所有人可使用） -->
										 <bean:message  bundle="sys-right" key="right.no.restr" />
										<% } %>
								
							</c:if>
							<c:if test="${not empty rightForm.authAttCopyNames}">
								${rightForm.authAttCopyNames}
							</c:if>			
						</c:if>
						<br>
					<%} %>
					<%--附件下载--%>	
					<% if(propertyNameSet.contains("authAttDownloads")){ %>
						<bean:message bundle="sys-right" key="right.att.authAttDownloads" />
						<html:hidden property="authAttDownloadIds" />
						<html:hidden property="authAttNodownload" />
						<c:if test="${rightForm.authAttNodownload == 'true'}">
							<bean:message key="right.att.authAttNodownload" bundle="sys-right"/>
						</c:if>
						<c:if test="${rightForm.authAttNodownload != 'true'}">
							<c:if test="${empty rightForm.authAttDownloadNames}">
								<!-- <bean:message bundle="sys-right" key="right.no.restr" /> -->
								<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
											<% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
											     <!-- （为空则本组织人员可使用） -->
											     <bean:message  bundle="sys-right" key="right.no.restr.outter" arg0="${ecoName}" />
											 <% } else { %>
											     <!-- （为空则所有内部人员可使用） -->
											     <bean:message  bundle="sys-right" key="right.no.restr.inner" />
											 <% } %>
											<% } else { %>
											 <!-- （为空则所有人可使用） -->
											 <bean:message  bundle="sys-right" key="right.no.restr" />
											<% } %>								
							</c:if>
							<c:if test="${not empty rightForm.authAttDownloadNames}">
								${rightForm.authAttDownloadNames}
							</c:if>			
						</c:if>
						<br>
					<%}%>
					<%--附件打印--%>	
					<% if(propertyNameSet.contains("authAttPrints")){ %>
						<bean:message bundle="sys-right" key="right.att.authAttPrints" />
						<html:hidden property="authAttPrintIds" />
						<html:hidden property="authAttNoprint" />
						<c:if test="${rightForm.authAttNoprint == 'true'}">
							<bean:message key="right.att.authAttNoprint" bundle="sys-right"/>
						</c:if>
						<c:if test="${rightForm.authAttNoprint != 'true'}">
							<c:if test="${empty rightForm.authAttPrintNames}">
								<!-- <bean:message bundle="sys-right" key="right.no.restr" /> -->
								<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
									    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
									        <!-- （为空则本组织人员可使用） -->
									        <bean:message  bundle="sys-right" key="right.no.restr.outter" arg0="${ecoName}" />
									    <% } else { %>
									        <!-- （为空则所有内部人员可使用） -->
									        <bean:message  bundle="sys-right" key="right.no.restr.inner" />
									    <% } %>
									<% } else { %>
									    <!-- （为空则所有人可使用） -->
									    <bean:message  bundle="sys-right" key="right.no.restr" />
									<% } %>
							</c:if>
							<c:if test="${not empty rightForm.authAttPrintNames}">
								${rightForm.authAttPrintNames}
							</c:if>			
						</c:if>
					<%} %>
				
				<% }else{ %>
				<%--起草人可修改默认附件权限时--%>	
					<%--附件拷贝--%>
					<% if(propertyNameSet.contains("authAttCopys")){ 
						boolean isJGEnabled = com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled(); 
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
						<html:checkbox property="authAttNocopy" value="1" onclick="refreshDisplay(this,'copyDiv')"/>
						<bean:message key="right.att.authAttNocopy" bundle="sys-right"/>
						<br>
						<div id="copyDiv" class="mb_10" <c:if test="${rightForm.authAttNocopy == 'true'}">style="display:none"</c:if> > 
							<xform:address textarea="true" mulSelect="true" propertyId="authAttCopyIds" propertyName="authAttCopyNames" style="width:97%;height:90px;" ></xform:address>
							<br>
							<span class="com_help">
								<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
								    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
								        <!-- 拷贝 -->
								        <!-- （为空则本组织人员可使用） -->
								        <bean:message  bundle="sys-right" key="right.att.authAttCopys.organizationNote" arg0="${ecoName}" />
								    <% } else { %>
								        <!-- （为空则所有内部人员可使用） -->
								       <bean:message key="right.att.authAttCopys.note" bundle="sys-right"/>
								    <% } %>
								<% } else { %>
								    <!-- （为空则所有人可使用） -->
								    <bean:message  bundle="sys-right" key="right.att.authAttCopys.nonOrganizationNote" />
								<% } %>
							</span>
						</div>
					<%} %>
					<%--附件下载--%>
					<% if(propertyNameSet.contains("authAttDownloads")){ %>
					    <%if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGPDFEnabled()){%>
						<bean:message bundle="sys-right" key="right.att.authAttDownloads.pdf" />
						<%}else{ %>
						<bean:message bundle="sys-right" key="right.att.authAttDownloads" />
						<%}%>
						<html:checkbox property="authAttNodownload" value="1" onclick="refreshDisplay(this,'downloadDiv')"/>
						<bean:message key="right.att.authAttNodownload" bundle="sys-right"/>
						<br>
						<div id="downloadDiv" class="mb_10" <c:if test="${rightForm.authAttNodownload == 'true'}">style="display:none"</c:if> >
							<xform:address textarea="true" mulSelect="true" propertyId="authAttDownloadIds" propertyName="authAttDownloadNames" style="width:97%;height:90px;" ></xform:address>
							<br>
							<span class="com_help">
							<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
							    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
							      <!-- 下载 -->
							        <!-- （为空则本组织人员可使用） -->
							        <bean:message  bundle="sys-right" key="right.att.authAttDownloads.organizationNote" arg0="${ecoName}" />
							    <% } else { %>
							        <!-- （为空则所有内部人员可使用） -->
							       <bean:message key="right.att.authAttDownloads.note" bundle="sys-right"/>
							    <% } %>
							<% } else { %>
							    <!-- （为空则所有人可使用） -->
							    <bean:message  bundle="sys-right" key="right.att.authAttDownloads.nonOrganizationNote" />
							<% } %>
							</span>
						</div>
					<%} %>
					<%--附件打印--%>
					<% if(propertyNameSet.contains("authAttPrints")){ %>
					    <%if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGPDFEnabled()){%>
						<bean:message bundle="sys-right" key="right.att.authAttPrints.pdf" />
						<%}else{ %>
						<bean:message bundle="sys-right" key="right.att.authAttPrints" />
						<%}%>
						<html:checkbox property="authAttNoprint" value="1" onclick="refreshDisplay(this,'printDiv')"/>
						<bean:message key="right.att.authAttNoprint" bundle="sys-right"/>
						<br>
						<div id="printDiv" class="mb_10" <c:if test="${rightForm.authAttNoprint == 'true'}">style="display:none"</c:if> >
							<xform:address textarea="true" mulSelect="true" propertyId="authAttPrintIds" propertyName="authAttPrintNames" style="width:97%;height:90px;" ></xform:address>
							<br>
							<span class="com_help">
							<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
							    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
							       <!-- 打印 -->
							        <!-- （为空则本组织人员可使用） -->
							        <bean:message  bundle="sys-right" key="right.att.authAttPrints.organizationNote" arg0="${ecoName}" />
							    <% } else { %>
							        <!-- （为空则所有内部人员可使用） -->
							       <bean:message key="right.att.authAttPrints.note" bundle="sys-right"/>
							    <% } %>
							<% } else { %>
							    <!-- （为空则所有人可使用） -->
							    <bean:message  bundle="sys-right" key="right.att.authAttPrints.nonOrganizationNote" />
							<% } %>
							</span>
						</div>
					<%} %>
				<% } %>
				<div class="div_img">
	                <bean:message bundle="sys-right" key="right.att.surport" />
					<span style="position: relative;top: 6px;" ><img src="${KMSS_Parameter_ContextPath}sys/right/resource/images/Word.png" height="20" width="20" title="MS Word "/></span>
					<span style="position: relative;top: 6px;" ><img src="${KMSS_Parameter_ContextPath}sys/right/resource/images/Excel.png" height="20" width="20" title="MS Excel"/></span>
					<span style="position: relative;top: 6px;" ><img src="${KMSS_Parameter_ContextPath}sys/right/resource/images/PowerPoint.png" height="20" width="20" title="MS PowerPoint"/></span>
					<span style="position: relative;top: 6px;" ><img src="${KMSS_Parameter_ContextPath}sys/right/resource/images/Project.png" height="20" width="20" title="MS Project"/></span>
					<span style="position: relative;top: 6px;" ><img src="${KMSS_Parameter_ContextPath}sys/right/resource/images/Visio.png" height="20" width="20" title="MS Visio"/></span>
					<%if(JgWebOffice.isJGPDFEnabled()){%>
						<span style="position: relative;top: 6px;" ><img src="${KMSS_Parameter_ContextPath}sys/right/resource/images/AdobePdf.png" height="20" width="20" title="PDF"/></span>
					<%} %>
			    </div>
			</td>
		</tr>	
	<%} %>
			
<script>
function refreshDisplay(obj,divName){
	var divObj = document.getElementById(divName);
	divObj.style.display=(obj.checked?"none":"");
}
</script>
