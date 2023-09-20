<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsConfig"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil" %>

<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
   	 SysNewsConfig sysNewsConfig = new SysNewsConfig();
     pageContext.setAttribute("ImageW",sysNewsConfig.getfdImageW());
     pageContext.setAttribute("ImageH",sysNewsConfig.getfdImageH());
     pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
     pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
     //WPS加载项
     pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
 	 //判断配置的金格控件类型
 	 String jgPluginType = com.landray.kmss.sys.attachment.util.JgWebOffice.getJGPluginType();
 	 pageContext.setAttribute("_jgPluginType", jgPluginType);
	//wps中台
	pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));

%>
<style>
	div.water-mark{
		z-index: 999;
	}
</style>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<div class="lui_form_content_frame" style="padding-top:20px">		
		    <%-- 新闻信息页签--%>
			<table class="tb_simple" width=100%>				
				<!-- 新闻主题 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-news"
						key="sysNewsMain.docSubject" /></td>
					<td colspan=3>
						<xform:text property="docSubject" style="width:97%" validators="senWordsValidator(sysNewsMainForm)"/>
					</td>
				</tr>
				<tr>
					<%-- 作者 --%>
					<td class="td_normal_title" width=15%><bean:message bundle="sys-news"  key="sysNewsMain.publisher" />
					</td>
					<td width=35% >
					  <span id="isWriterSpan" >
				         	  	<xform:text property="fdWriter" style="width:94%;" isLoadDataDict="false" subject="${lfn:message('sys-news:sysNewsMain.publisher')}"  required="true"/>
					  </span>
					  
					  <span id="notWriterSpan" style="display:none">
					 		   <xform:address onValueChange="onFdAuthorIdChange" isLoadDataDict="false" required="true"  style="width:94%" subject="${lfn:message('sys-news:sysNewsMain.publisher')}" propertyId="fdAuthorId" propertyName="fdAuthorName" orgType='ORG_TYPE_PERSON' className="input"></xform:address>
					  </span></br>
					  <span id="checkBoxSpan" >
						       <html:checkbox property="fdIsWriter" onclick="SetAuthorField();" styleId="isWriter" />
							   <bean:message bundle="sys-news" key="sysNewsMain.fdIsWriter" />
					 </span>
					</td>
					<!-- 所属部门 -->
					<td id="notWriterDeptSpan1" class="td_normal_title" width=15%>
						<bean:message bundle="sys-news"  key="sysNewsMain.publishUnit" />
					</td>
					<td id="notWriterDeptSpan2" width=35%>
					    <xform:address isLoadDataDict="false" required="true" style="width:94%" propertyId="fdDepartmentId" subject="${lfn:message('sys-news:sysNewsMain.publishUnit')}" propertyName="fdDepartmentName" orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT'></xform:address>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-news" key="sysNewsMain.docOverdueTime" />
					</td>
					<td width=35%>
						<xform:datetime property="docOverdueTime" style="width:94%;" dateTimeType="date" validators="after"></xform:datetime>
						</br>
						<font color="red"><bean:message bundle="sys-news" key="sysNewsMain.docOverdueTime.tips" /></font>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-news" key="sysNewsMain.comment" />
					</td>
					<td width=35%>
						 <ui:switch property="fdCanComment" enabledText="${ lfn:message('sys-news:sysNewsMain.fdCanComment.yes') }"  disabledText="${ lfn:message('sys-news:sysNewsMain.fdCanComment.no') }"  checked="${sysNewsMainForm.fdCanComment}"></ui:switch>
					</td>
			     </tr>
				<tr>
					<%-- 摘要 --%>
					<td class="td_normal_title" width=15%><bean:message
						bundle="sys-news" key="sysNewsMain.fdDescription" /></td>
					<td width="85%" colspan="3">
						<xform:textarea property="fdDescription"  style="width:97%;height:90px" validators="senWordsValidator(sysNewsMainForm)"/>
					</td>
				</tr>
				<%-- 编辑方式 --%>
				<html:hidden property="fdContentType" />
				<html:hidden property="fdHtmlContent" />
				<html:hidden property="fdSignEnable" />
				<c:if test="${sysNewsMainForm.method_GET=='add'}">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-news" key="sysNewsMain.fdContentType" />
					</td>
					<td colspan="3">
						<xform:radio property="fdEditType" showStatus="edit" value="${sysNewsMainForm.fdContentType}" onValueChange="checkEditType">
							<xform:enumsDataSource enumsType="sysNewsMain_fdContentType" />
						</xform:radio>
					</td>
				</tr>
				</c:if>
				
				<!-- 新闻内容 -->
				<tr>
					<td class="td_normal_title"  width="15%"></td>
					<td colspan="3">
						<c:if test="${sysNewsMainForm.fdIsLink&& not empty sysNewsMainForm.fdLinkUrl}">
							<bean:message bundle="sys-news" key="SysNewsMain.linkNews" />
							<a href='<c:url value="${sysNewsMainForm.fdLinkUrl}"/>'/>
								${sysNewsMainForm.docContent}
							</a>
						</c:if> 
						<c:if test="${empty sysNewsMainForm.fdIsLink || empty sysNewsMainForm.fdLinkUrl}">
							<div id="rtfEdit" 
							<c:if test="${sysNewsMainForm.fdContentType!='rtf'}">style="display:none"</c:if>>
								<xform:rtf property="docContent" width="97%" toolbarSet="Default" validators="senWordsValidator(sysNewsMainForm)"></xform:rtf>
							</div>
							
							<!-- display的值none时，会影响金格加载，启用WPS时需要将display设置为none -->
							<c:choose>
								<c:when test="${pageScope._isWpsCloudEnable == 'true' or pageScope._isWpsWebOfficeEnable == 'true' or pageScope._isWpsCenterEnable == 'true'}">
									<div id="wordEdit" <c:if test="${sysNewsMainForm.fdContentType=='rtf'}">style="display:none;width:0px;height:0px;"</c:if>>
								</c:when>
								<c:otherwise>
									<div id="wordEdit" style="width:0px;height:0px;">
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${pageScope._isWpsCloudEnable == 'true'}">
									<c:if test="${(sysNewsMainForm.method_GET=='add')||(sysNewsMainForm.method_GET=='edit'&&sysNewsMainForm.fdContentType!='rtf')}">
										<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="editonline" />
											<c:param name="load" value="true" />
											<c:param name="bindSubmit" value="false"/>	
											<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
											<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
											<c:param name="formBeanName" value="sysNewsMainForm" />
											<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
											<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
											<c:param name="fdTemplateKey" value="editonline" />
											<c:param name="fdTempKey" value="${param.fdTemplateId}" />
										</c:import>
									</c:if>
								</c:when>
								<c:when test="${pageScope._isWpsCenterEnable == 'true'}">
									<c:if test="${(sysNewsMainForm.method_GET=='add')||(sysNewsMainForm.method_GET=='edit'&&sysNewsMainForm.fdContentType!='rtf')}">
										<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="editonline" />
											<c:param name="load" value="false" />
											<c:param name="bindSubmit" value="false"/>
											<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
											<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
											<c:param name="formBeanName" value="sysNewsMainForm" />
											<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
											<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
											<c:param name="fdTemplateKey" value="editonline" />
											<c:param name="fdTempKey" value="${param.fdTemplateId}" />
										</c:import>
									</c:if>
								</c:when>
								<c:when test="${pageScope._isWpsWebOfficeEnable == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="load" value="true" />
										<c:param name="bindSubmit" value="false"/>	
										<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										<c:param name="formBeanName" value="sysNewsMainForm" />
										<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
										<c:param name="fdTemplateKey" value="editonline" />
										<c:param name="fdTempKey" value="${param.fdTemplateId}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsWebOffice == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="fdMulti" value="false" />
										<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										<c:param name="formBeanName" value="sysNewsMainForm" />
										<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
										<c:param name="fdTemplateKey" value="editonline" />
										<c:param name="templateBeanName" value="sysNewsTemplateForm" />
										<c:param name="showDelete" value="false" />
										<c:param name="wpsExtAppModel" value="sysNews" />
										<c:param name="canRead" value="false" />
										<c:param name="canEdit" value="true" />
										<c:param name="canPrint" value="false" />
										<c:param name="addToPreview" value="false" />
										<c:param  name="hideTips"  value="true"/>
										<c:param  name="hideReplace"  value="false"/>
										<c:param  name="canChangeName"  value="true"/>
										<c:param  name="filenameWidth"  value="250"/>
										<c:param name="load" value="false" />
										<c:param name="formBeanName" value="sysNewsMainForm" />
									</c:import>
								</c:when>
								<c:otherwise>
									<c:if test="${(sysNewsMainForm.method_GET=='add' && pageScope._isJGEnabled == 'true') || (sysNewsMainForm.method_GET=='edit' && pageScope._isJGEnabled == 'true' && sysNewsMainForm.fdContentType=='word')}">
										<div id="missiveButtonDiv" style="text-align:right; display: none;"></div>
										<div id="wordEditWrapper"></div>
										<div id="wordEditFloat" style="position: absolute; width: 1px; height: 1px; filter: alpha(opacity = 0); opacity: 0;">
											<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckJgSupport.jsp"%>
											<c:if test="${supportJg eq 'supported'}">
											<div id="buttonDiv" style="text-align: right; padding-bottom: 5px">
											  <a href="javascript:void(0);" class="attopenLocal" onclick="openLocal();">
											     <bean:message bundle="sys-news" key="SysNewsMain.tools.openLocal"/>
											  </a>&nbsp;
											  <c:if test="${pageScope._jgPluginType ne '2003'}">
												  <a href="javascript:void(0);" class="attfullSize" onclick="fullSize();">
												     <bean:message bundle="sys-news" key="SysNewsMain.tools.fullsize"/>
												  </a>
											  </c:if>
											</div>	
											</c:if>									
											<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="editonline" />
												<c:param name="fdAttType" value="office" />
												<c:param name="load" value="false" />
												<c:param name="bindSubmit" value="false"/>													
												<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
												<c:param name="formBeanName" value="sysNewsMainForm" />
												<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
												<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
												<c:param name="fdTemplateKey" value="editonline" />
												<c:param name="templateBeanName" value="sysNewsTemplateForm" />
												<c:param name="buttonDiv" value="missiveButtonDiv" />
												<c:param  name="attHeight" value="600"/>
												<c:param name="isToImg" value="false"/>
											</c:import>
										</div>
									</c:if>
								</c:otherwise>
							</c:choose>
							</div>
						</c:if>
						<c:if test="${(sysNewsMainForm.method_GET=='add')||(sysNewsMainForm.method_GET=='edit'&&sysNewsMainForm.fdContentType=='att')}">
							<!-- pdf附件上传 -->
							<div id="attEdit" style="display:none; border: 1px solid #d5d5d5; height:160px">
									<div class="lui_form_subhead">
										<bean:message bundle="sys-news" key="sysNewsMain.upload"></bean:message>
									</div>
									<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="newsMain" />
										<c:param name="uploadAfterSelect" value="true" />
										<c:param name="fdMulti" value="false" />
										<c:param name="fdImgHtmlProperty" />
										<c:param name="fdModelId" value="${sysNewsMainForm.fdId }" />
										<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										<c:param name="canRead" value="false"/>
										<c:param name="canPrint" value="false"/>
										<c:param name="enabledFileType" value="*.doc;*.docx;*.ppt;*.pptx;*.pdf;*.txt;*.xls;*.xlsx;*.wps" />
									</c:import>
							</div>
						</c:if>
					</td>
				</tr>
				<!-- 附件 -->
				<tr KMSS_RowType="documentNews">
					<td class="td_normal_title" width=15% valign="top"><bean:message
						bundle="sys-news" key="sysNewsMain.attachment" /></td>
					<td colspan=3><c:import
						url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="fdAttType" value="byte" />
						<c:param name="uploadAfterSelect" value="true" />
						<c:param name="fdMulti" value="true" />
						<c:param name="fdShowMsg" value="true" />
						<c:param name="fdImgHtmlProperty" />
						<c:param name="fdKey" value="fdAttachment" />
						<c:param name="fdModelId" value="${param.fdId }" />
						<c:param name="fdModelName"
							value="com.landray.kmss.sys.news.model.SysNewsMain" />
					</c:import></td>
			    </tr>
			    <!-- 盖章文件 -->
			 	<c:if test="${sysNewsMainForm.fdSignEnable}">
			 	<c:if test="${sysNewsMainForm.method_GET=='add'}">
			 		<tr>
				 		<td class="td_normal_title" width=15%>
				 			<bean:message bundle="sys-news" key="sysNewsSummary.fdSignFile"/>
				 		</td>
				 		<td width="85%" colspan="3" >
				 			<div style="padding:10px 0"><font color="red"><bean:message bundle="sys-news" key="sysNewsSummary.file.big"/></font></div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="fdSignFile" />
								<c:param name="fdModelId" value="${param.fdId }" />
								<c:param name="enabledFileType" value=".pdf;.doc;.xls;.ppt;.docx;.xlsx;.pptx;.jpg;.jpeg;.png;" />
								<c:param name="uploadAfterSelect" value="true" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="fdRequired" value="true" />
							</c:import>
						</td>
				 	</tr>
			 	</c:if>
			 	<c:if test="${sysNewsMainForm.method_GET=='edit'}">
			 		<tr>
				 		<td class="td_normal_title" width=15%>
				 			<bean:message bundle="sys-news" key="sysNewsSummary.fdSignFile"/>
				 		</td>
				 		<td width="85%" colspan="3" >
				 			<div style="padding:10px 0"><font color="red"><bean:message bundle="sys-news" key="sysNewsSummary.file.big"/></font></div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="fdSignFile" />
								<c:param name="fdModelId" value="${param.fdId }" />
								<c:param name="uploadAfterSelect" value="true" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="fdRequired" value="true" />
							</c:import>
						</td>
				 	</tr>
			 	</c:if>
				 	
			 	</c:if>
				
					<%-- 标题图片--%>
				<tr>
					<td class="td_normal_title" width="15%" valign="top"><bean:message
						bundle="sys-news" key="sysNewsMain.fdMainPicture" /></td>
					<td colspan="3">		
					 	<c:import
							url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
							charEncoding="UTF-8">
							<c:param name="fdKey" value="Attachment" />
							<c:param name="fdMulti" value="false" />
							<c:param name="fdAttType" value="pic" />
							<c:param name="fdImgHtmlProperty" value="width=120" />
							<c:param name="fdModelId" value="${param.fdId }" />
							<c:param name="fdModelName"
								value="com.landray.kmss.sys.news.model.SysNewsMain" />
							<%-- 图片设定大小 --%>
							<c:param name="picWidth" value="${ImageW}" />
							<c:param name="picHeight" value="${ImageH}" />
							<c:param name="proportion" value="false" />
							<c:param name="fdLayoutType" value="pic"/>
							<c:param name="fdPicContentWidth" value="${ImageW}"/>
							<c:param name="fdPicContentHeight" value="${ImageH}"/>
							<c:param name="fdViewType" value="pic_single"/>
						</c:import>
						<br>
					    <bean:message bundle="sys-news" key="sysNewsMain.config.desc" />
						 <font color="red">${ImageW}(<bean:message bundle="sys-news" key="sysNewsMain.config.width"/>)*${ImageH}(<bean:message bundle="sys-news" key="sysNewsMain.config.height"/>)</font>										  				 
					</td>
				</tr>
				<tr>
				  <%--更多设置按钮--%>
					<td colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" onclick="showMoreSet();" id="showMoreSet" class="task_slideDown">
						<bean:message bundle="sys-news" key="sysNewsMain.more.set.slideDown" /></a>
					</td>		
				</tr>  
				<tr id="show_more_set_id" style="display:none">
				 <td colspan="4" width="100%">
						<table class="tb_simple" width="100%">				
				  			<!-- 新闻重要度 -->
								<tr>	
									<td class="td_normal_title" width=15%><bean:message
										bundle="sys-news" key="sysNewsTemplate.fdImportance" /></td>
									<td colspan="3"><sunbor:enums property="fdImportance"
										enumsType="sysNewsMain_fdImportance" elementType="radio" />
									</td>
								</tr>				
								<tr>
									<!-- 发布时间 -->
									<td class="td_normal_title" width=15%><bean:message
										bundle="sys-news" key="sysNewsMain.docPublishTime" /></td>
									<td width=35%>
										<xform:datetime property="docPublishTime" style="width:100%" dateTimeType="datetime"></xform:datetime>
										<br>
										<span style="color:red"><bean:message bundle="sys-news" key="sysNewsMain.docPublishTime.desc" /></span>
									</td>
									<!-- 新闻来源 -->
									<td class="td_normal_title" width="15%">
									<bean:message
										bundle="sys-news" key="sysNewsMain.fdNewsSource" />
									</td>
									<td width=35%>
									    <html:hidden property="fdTemplateId" />
										<html:hidden property="fdTemplateName" />
										<xform:text property="fdNewsSource" style="width:94%;"/>
		            		      </td>
						     </tr>
							<!-- 标签机制 -->
							<c:import url="/sys/tag/import/sysTagMain_edit.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="sysNewsMainForm" />
								<c:param name="fdKey" value="newsMainDoc" /> 
								<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="fdQueryCondition" value="fdTemplateId;fdDepartmentId" /> 
							</c:import>
							<%-- 所属场所 --%>
			                <c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
			                    <c:param name="id" value="${sysNewsMainForm.authAreaId}"/>
			                </c:import> 
					     </table>
			       </td>
				</tr>
			</table>
		</div>	
	</c:when>
	<c:when test="${param.contentType eq 'right'}">
		<%--权限机制 --%>
		<c:choose>
			<c:when test="${empty sysNewsMainForm.docStatus || sysNewsMainForm.docStatus == 10}">
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsMainForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
					<c:param name="order" value="1" />
				</c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsMainForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
					<c:param name="order" value="${sysNewsMainForm.docStatus>=30 ? 9 : 10}" />
				</c:import>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>