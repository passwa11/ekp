<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil,com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed()%>"/>
<%
	//加载项 暂时屏蔽
	pageContext.setAttribute("_isWpsAddonsEnable", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
	Boolean isWindows = Boolean.FALSE;
	if("windows".equals(JgWebOffice.getOSType(request))){
		isWindows = Boolean.TRUE;
	}
	pageContext.setAttribute("isWindowsInOAassist", isWindows);
%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<ui:content title="${ lfn:message('km-review:kmReviewDocumentLableName.reviewContent') }" toggle="false" >
			<table class="tb_normal" width=100%>			
			<html:hidden property="fdSignEnable" />
				<!--主题-->
				<tr>
					<td align="right" style="border-right: 0px;" width=15%>
						<bean:message bundle="km-review" key="kmReviewMain.docSubject" /></td>
					<td style="border-left: 0px !important;">
						    <div style="width:80%;display: inline-block;">
								<c:if test="${kmReviewMainForm.titleRegulation==null || kmReviewMainForm.titleRegulation=='' }">
									<xform:text property="docSubject"  style="width:97%;" className="inputsgl"/>
								</c:if>
								<c:if test="${kmReviewMainForm.editDocSubject eq 'true' && kmReviewMainForm.titleRegulation!=null && kmReviewMainForm.titleRegulation!='' }">
									<xform:text property="docSubject" style="width:97%;height:auto;color:#333;" className="inputsgl"  value="${lfn:message('km-review:kmReviewMain.docSubject.info') }" />
								</c:if>
								<c:if test="${kmReviewMainForm.editDocSubject ne 'true' && kmReviewMainForm.titleRegulation!=null && kmReviewMainForm.titleRegulation!='' }">
									<xform:text property="docSubject" style="width:97%;height:auto;color:#333;" className="inputsgl" showStatus="readOnly" value="${lfn:message('km-review:kmReviewMain.docSubject.info') }" />
								</c:if>
							</div>
							<c:choose>
								<c:when test="${enableModule.enableSysCirculation eq 'false' || kmReviewMainForm.fdCanCircularize ne 'true'}">
									<html:hidden property="fdCanCircularize"/>
								</c:when>
								<c:otherwise>
									<div style="display: inline-block;float:right;">
										<ui:switch property="fdCanCircularize" enabledText="${ lfn:message('km-review:kmReviewMain.fdCanCircularize.yes') }"  disabledText="${ lfn:message('km-review:kmReviewMain.fdCanCircularize.no') }"  checked="${kmReviewMainForm.fdCanCircularize}"></ui:switch>
									</div>
								</c:otherwise>
							</c:choose>
					</td> 
				</tr>
			</table>
			<br>
			<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
				<c:choose>
					<c:when test="${kmReviewMainForm.fdUseWord == 'true'}">
						<table class="tb_normal" style="border-top:0;" width="100%">
							<tr>
								<td colspan="2">
								    
									<div id="wordEdit">
										<div id="wordEditWrapper"></div>
										<div id="wordEditFloat" style="position: absolute;width:0px;height:0px;filter:alpha(opacity=0);opacity:0;">
										<div id="reviewButtonDiv" style="text-align:right;"></div>
										
										
										<%
     											pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
     											pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
												pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));
     											
										%>

										<c:choose>
													<c:when test="${pageScope._isWpsCloudEnable == 'true'}">
														<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
															<c:param name="fdKey" value="mainContent" />
															<c:param name="load" value="true" />
															<c:param name="bindSubmit" value="false"/>	
															<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
															<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
															<c:param name="formBeanName" value="kmReviewMainForm" />
															<c:param name="fdTemplateModelId" value="${kmReviewMainForm.fdTemplateId}" />
															<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
															<c:param name="fdTemplateKey" value="mainContent" />
															<c:param name="fdTempKey" value="${kmReviewMainForm.fdTemplateId}" />
														</c:import>
													</c:when>
													<c:when test="${pageScope._isWpsWebOfficeEnable == 'true'}">
														<c:import url="/sys/attachment/sys_att_main/wps/sysAttMain_edit.jsp" charEncoding="UTF-8">
															<c:param name="fdKey" value="mainContent" />
															<c:param name="load" value="true" />
															<c:param name="bindSubmit" value="false"/>	
															<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
															<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
															<c:param name="formBeanName" value="kmReviewMainForm" />
															<c:param name="fdTemplateModelId" value="${kmReviewMainForm.fdTemplateId}" />
															<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
															<c:param name="fdTemplateKey" value="mainContent" />
															<c:param name="fdTempKey" value="${kmReviewMainForm.fdTemplateId}" />
														</c:import>
													</c:when>
													<c:when test="${pageScope._isWpsCenterEnable == 'true'}">
														<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
															<c:param name="fdKey" value="mainContent" />
															<c:param name="load" value="true" />
															<c:param name="bindSubmit" value="false"/>
															<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
															<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
															<c:param name="formBeanName" value="kmReviewMainForm" />
															<c:param name="fdTemplateModelId" value="${kmReviewMainForm.fdTemplateId}" />
															<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
															<c:param name="fdTemplateKey" value="mainContent" />
															<c:param name="fdTempKey" value="${kmReviewMainForm.fdTemplateId}" />
														</c:import>
													</c:when>
													<c:when test="${pageScope._isWpsAddonsEnable == 'true'}">
														<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit.jsp" charEncoding="UTF-8">
																<c:param name="fdKey" value="mainContent" />
																<c:param name="fdMulti" value="false" />
																<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
																<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
																<c:param name="formBeanName" value="kmReviewMainForm" />
																<c:param name="fdTemplateModelId" value="${kmReviewMainForm.fdTemplateId}" />
																<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
																<c:param name="fdTemplateKey" value="mainContent" />
																<c:param name="templateBeanName" value="kmReviewTemplateForm" />
																<c:param name="showDelete" value="false" />
																<c:param name="wpsExtAppModel" value="kmReviewMain" />
																<c:param name="canRead" value="false" />
																<c:param name="canEdit" value="true" />
																<c:param name="canPrint" value="false" />
																<c:param name="addToPreview" value="false" />
																<c:param  name="hideTips"  value="true"/>
																<c:param  name="hideReplace"  value="true"/>
																<c:param  name="canChangeName"  value="true"/>
																<c:param  name="filenameWidth"  value="250"/>
																<c:param name="load" value="false" />
															</c:import>
													</c:when>
													
													<c:otherwise>
														<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
																<c:param name="fdKey" value="mainContent" />
																<c:param name="fdAttType" value="office" />
																<c:param name="bindSubmit" value="false"/>
																<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
																<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
																<c:param name="fdTemplateModelId" value="${kmReviewMainForm.fdTemplateId}" />
																<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
																<c:param name="fdTemplateKey" value="editonline_krt" />
																<c:param name="templateBeanName" value="kmReviewTemplateForm" />
																<c:param name="buttonDiv" value="reviewButtonDiv" />
																<c:param name="showDefault" value="true"/>
																<c:param name="load" value="false" />
																<c:param name="isToImg" value="false"/>
																<c:param  name="attHeight" value="580"/>
															</c:import>
													</c:otherwise>
											</c:choose>
							
										</div>
									</div>
								</td>
							</tr>	
						</table>
					</c:when>
					<c:otherwise>
						<table class="tb_normal" width=100%>
							<tr>
								<td colspan="2">
									<kmss:editor property="docContent" width="95%"/>
								</td>
							</tr>
							<!-- 相关附件 -->
							<tr KMSS_RowType="documentNews">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-review" key="kmReviewMain.attachment" />
								</td>
								<td>
									<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdAttType" value="byte" />
										<c:param name="fdMulti" value="true" />
										<c:param name="fdImgHtmlProperty" />
										<c:param name="fdKey" value="fdAttachment" />
										<c:param name="formBeanName" value="kmReviewMainForm" />
										<c:param name="uploadAfterSelect" value="true" />
									</c:import>
								</td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>	
			</c:if>
			<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
				<%-- 表单 --%>
				<div id="kmReviewXform">
					<c:import url="/sys/xform/include/sysForm_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmReviewMainForm" />
						<c:param name="fdKey" value="reviewMainDoc" />
						<c:param name="messageKey" value="km-review:kmReviewDocumentLableName.reviewContent" />
						<c:param name="useTab" value="false" />
					</c:import>
					<br>
				</div>
			</c:if>
			<!-- 盖章文件 -->
			<c:if test="${kmReviewMainForm.fdSignEnable}">
				<table class="tb_normal" width=100%>
				 	<tr>
				 	<td class="td_normal_title" width=15%>
				 		<bean:message bundle="km-review" key="kmReviewMain.fdSignEnable"/>
				 	</td>
				 	<c:if test="${kmReviewMainForm.method_GET=='add'}">
				 		<td width="85%" colspan="3" >
				 			<div style="padding:10px 0"><font color="red"><bean:message bundle="km-review" key="kmReviewMain.file.big"/></font></div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="fdSignFile" />
								<c:param name="fdModelId" value="${param.fdId }" />
								<c:param name="enabledFileType" value=".pdf;.doc;.xls;.ppt;.docx;.xlsx;.pptx;.jpg;.jpeg;.png;" />
								<c:param name="uploadAfterSelect" value="true" />
								<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
								<c:param name="fdRequired" value="true" />
							</c:import>
						</td>
				 	</c:if>
				 	<c:if test="${kmReviewMainForm.method_GET=='edit'}">
				 		<td width="85%" colspan="3" >
				 			<div style="padding:10px 0"><font color="red"><bean:message bundle="km-review" key="kmReviewMain.file.big"/></font></div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="fdSignFile" />
								<c:param name="fdModelId" value="${param.fdId }" />
								<c:param name="uploadAfterSelect" value="true" />
								<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
								<c:param name="fdRequired" value="true" />
							</c:import>
						</td>
				 	</c:if>
				 	</tr>
			 	</table>
			 </c:if> 
		</ui:content>
	</c:when>
	<c:when test="${param.contentType eq 'baseInfo'}">
		<ui:content title="${ lfn:message('km-review:kmReviewTemplateLableName.baseInfo') }">
			<table class="tb_normal" width=100%>
				<!--关键字-->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewKeyword.fdKeyword" />
					</td>
					<td colspan=3>
						<xform:text property="fdKeywordNames" style="width:97%" />
					</td>
				</tr>
				<!--流程类别-->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewTemplate.fdName" />
					</td>
					<td colspan=3>
						<html:hidden property="fdTemplateId" /> 
						<c:out value="${ kmReviewMainForm.fdTemplateName}"/>
					</td>
				</tr>
				<!--申请人-->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewMain.docCreatorName" />
					</td>
					<td width=35%>
						<c:out value="${ kmReviewMainForm.docCreatorName}"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewMain.fdNumber" />
					</td>
					<td width=35%>
						<c:if test="${kmReviewMainForm.fdNumber!=null}">
							<html:text property="fdNumber" readonly="true" style="width:95%"/>
						</c:if>
						<c:if test="${kmReviewMainForm.fdNumber==null}">
							<bean:message bundle="km-review" key="kmReviewMain.fdNumber.message" />
						</c:if>
					</td>
				</tr>
				<!--部门-->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewMain.department" />
					</td>
					<td>
						<c:out value="${ kmReviewMainForm.fdDepartmentName}"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewMain.docCreateTime" />
					</td>
					<td width=35%>
						<c:out value="${ kmReviewMainForm.docCreateTime}"/>
						<html:hidden property="docCreateTime" />
					</td>
				</tr>
				<!--状态-->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewMain.docStatus" />
					</td>
					<td>
						<c:if test="${kmReviewMainForm.docStatus=='00'}">
							<bean:message key="status.discard"/>
						</c:if>
						<c:if test="${kmReviewMainForm.docStatus=='10'}">
							<bean:message key="status.draft"/>
						</c:if>
						<c:if test="${kmReviewMainForm.docStatus=='11'}">
							<bean:message key="status.refuse"/>
						</c:if>
						<c:if test="${kmReviewMainForm.docStatus=='20'}">
							<bean:message key="status.examine"/>
						</c:if>
						<c:if test="${kmReviewMainForm.docStatus=='30'}">
							<bean:message key="status.publish"/>
						</c:if>
						<c:if test="${kmReviewMainForm.docStatus=='31'}">
							<bean:message key="status.feedback" bundle="km-review"/>
						</c:if>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="kmReviewMain.docPublishTime" />
					</td>
					<td width=35%>
						<bean:write name="kmReviewMainForm" property="docPublishTime" />
					</td>
				</tr>
				<!--实施反馈人-->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="table.kmReviewFeedback" />
					</td>
					<td colspan=3>
					<html:hidden property="fdFeedbackModify" /> 
					<c:choose>
						<c:when test="${kmReviewMainForm.fdFeedbackModify=='1'}">
						  <xform:dialog icon="orgelement" propertyId="fdFeedbackIds" style="width:95%" propertyName="fdFeedbackNames">
							Dialog_Address(true, 'fdFeedbackIds','fdFeedbackNames', ';',null);
						  </xform:dialog>
						</c:when>
						<c:otherwise>
						  <html:hidden property="fdFeedbackIds" /> 
						  <bean:write name="kmReviewMainForm" property="fdFeedbackNames" />
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<%-- 所属场所 --%>
				<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
                     <c:param name="id" value="${kmReviewMainForm.authAreaId}"/>
                </c:import> 
				
				<!--适用岗位-->
				<%--适用岗位不要了，modify by zhouchao--%>
				<%--<tr>
					<td class="td_normal_title" width=15%><bean:message
						bundle="km-review" key="table.kmReviewPost" /></td>
					<td colspan=3><html:hidden property="fdPostIds" /> <html:textarea
						property="fdPostNames" style="width:80%" readonly="true" /> <a
						href="#"
						onclick="Dialog_Address(true, 'fdPostIds','fdPostNames', ';',ORG_TYPE_POST);"><bean:message
						key="dialog.selectOther" /></a></td>
				</tr>
				--%>
				<!--其他属性-->
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="km-review" key="table.sysCategoryProperty" />
					</td>
						
					<%-----辅类别--原来为不可选  现按照规章制度的 改为 可选或多选
					                   --modify by zhouchao 20090520--%>						
					<td colspan=3>
						<xform:dialog propertyId="docPropertyIds" propertyName="docPropertyNames" style="width:95%" >
							Dialog_property(true, 'docPropertyIds','docPropertyNames', ';', ORG_TYPE_PERSON);
						</xform:dialog>		
					</td>	
				</tr>
			</table>
		</ui:content> 
	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<!-- 版本锁机制 -->
		<c:import url="/component/locker/import/componentLockerVersion_import.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmReviewMainForm" />
		</c:import>
		<%--阅读机制 --%>
		<c:import url="/sys/readlog/import/sysReadLog_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmReviewMainForm" />
		</c:import>
		<c:if test="${param.approveModel ne 'right'}">
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmReviewMainForm" />
				<c:param name="fdKey" value="reviewMainDoc" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
			</c:import>
		</c:if>
		 <%--权限机制 --%>
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmReviewMainForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
		</c:import>
		<c:if test="${kmReviewMainForm.syncDataToCalendarTime=='flowSubmitAfter' || kmReviewMainForm.syncDataToCalendarTime=='flowPublishAfter'}">
		<ui:content title="${ lfn:message('sys-agenda:module.sys.agenda.syn') }">
			<!--日程机制(表单模块) 开始-->
			<table class="tb_normal" width=100%>
				<tr>
				   <td width="15%"  class="tb_normal">
				   		<%--同步时机--%>
				       	<bean:message bundle="sys-agenda" key="module.sys.agenda.syn.time" />
				   </td>
				   <td width="85%" colspan="3">
				       <xform:radio property="syncDataToCalendarTime" showStatus="edit">
				       		<xform:enumsDataSource enumsType="kmReviewMain_syncDataToCalendarTime" />
						</xform:radio>
				   </td>
				</tr>
				<tr>
					<td colspan="4" style="padding: 0px;">
						 <c:import url="/sys/agenda/import/sysAgendaMain_formula_edit.jsp"	charEncoding="UTF-8">
						    <c:param name="formName" value="kmReviewMainForm" />
						    <c:param name="fdKey" value="reviewMainDoc" />
						    <c:param name="fdPrefix" value="sysAgendaMain_formula_edit" />
						    <c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
						    <%--可选字段 1.syncTimeProperty:同步时机字段； 2.noSyncTimeValues:当syncTimeProperty为此值时，隐藏同步机制 --%>
							<c:param name="syncTimeProperty" value="syncDataToCalendarTime" />
							<c:param name="noSyncTimeValues" value="noSync" />
						 </c:import>
					</td>
				</tr>
			</table>
			<!--日程机制(表单模块) 结束-->
		</ui:content>
		</c:if>
	</c:when>
</c:choose>

<script type="text/javascript">
	Com_AddEventListener(window, "load", function() {
		if ("${pageScope._isWpsCenterEnable}" == "true") {
			setTimeout(function(){
				wps_center_mainContent.load();
			}, 500);
		}
		setTimeout(function(){
			//内嵌加载项
			if('${pageScope._isWpsAddonsEnable}' == "true"&&"${wpsoaassistEmbed}"=="true"&&"${isWindowsInOAassist}"=="false"&&$('#wpsLinux_mainContent')){
				$('#wpsLinux_mainContent').css('height','550px');
			}
		},600)
	});
</script>