<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	Com_IncludeFile("ding.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);
	Com_IncludeFile("lbpmProcess.js", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/js/","js",true);
	$(function(){
		var method = '${kmReviewMainForm.method_GET}';
		if("add" == method){
			createSubject();
		}else{
			var docSubject = '${kmReviewMainForm.docSubject}';
			if(!docSubject){
				createSubject();
			}else{
				var titleRegulation = '${kmReviewMainForm.titleRegulation}';
				var editDocSubject = '${kmReviewMainForm.editDocSubject}';
				if(!titleRegulation&&editDocSubject=='true'){
					$("#docSubject").val(docSubject);
					document.getElementById("docSubjectSpan").innerHTML= docSubject;
				}
			}
		}
		
	    $(document).click(function(){
	    	subjectEvent("doc");
	    });
	
	    $("#subject_td").click(function(){
	    	subjectEvent("stop");
	    });

	    $('#docSubject').bind('keyup', function(event) {
	    　　if (event.keyCode == "13") {
	    　　　　subjectEvent("doc");
	    　　}
	    });
	});
	function subjectEvent(flag){
		var subject_hidden = $("#subject_hidden").val();
    	if("1" == subject_hidden){
    		if("stop" == flag){
	       		event.stopPropagation();
    		}
    		if("doc" == flag){
    			updateSubject ();
    		}
    	}
	}
	
	function createSubject(){
		var titleRegulation = '${kmReviewMainForm.titleRegulation}';
		if(!titleRegulation){
			/*此处代码逻辑：没有主题生成规则的情况下：用户手动输入、废弃"用户+提交的+流程名称"规则*/
			/*ouyu 2021-12-01 详细说明见单号 148502*/
			<%--var fdTemplateName = '${kmReviewMainForm.fdTemplateName}';--%>
			<%--var docCreatorName = '${kmReviewMainForm.docCreatorName}';--%>
			<%--fdTemplateName= fdTemplateName.substring(fdTemplateName.lastIndexOf("\/")+1,fdTemplateName.length);--%>
			<%--$("#docSubject").val(""+docCreatorName+"提交的"+fdTemplateName);--%>
			<%--if(${kmReviewMainForm.editDocSubject eq 'true'}){--%>
			<%--	//如果是没有“主题自动生成规则”，但是可以“编辑主题”的情况--%>
			<%--	document.getElementById("docSubjectSpan").innerHTML= docCreatorName+"提交的"+fdTemplateName;--%>
			<%--}--%>
		}else{
			//如果是有“主题自动生成规则”,那么就给标题默认设置为“提交时自动生成”
			var spanVal = "${lfn:message('km-review:kmReviewMain.docSubject.info')}";
			$("#docSubject").val(spanVal);
		}
	}
</script>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
	<ui:tabpanel id="tabpanel_title_edit">
		<ui:content title="" id="title_edit" toggle="false" >
			<table class="tb_normal" width=100%>
			<html:hidden property="fdSignEnable" />
				<!--主题-->
				<tr>
					<td id="subject_td" style="border-left: 0px !important;">
						    <div style="display: inline-block;">
						    <input type="hidden" id="subject_hidden" value="0" />
						    <c:if test="${kmReviewMainForm.method_GET=='add' || kmReviewMainForm.method_GET=='edit'}">
								 <%--<c:choose>
									<c:when test="${null != kmReviewMainForm.titleRegulation && '' != kmReviewMainForm.titleRegulation}">
										<span id ="docSubjectSpanAuto" style="font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;margin-left: 40px;display:none;">主题名称提交时自动生成</span>
									</c:when>
									<c:otherwise>
									    <span id ="docSubjectSpan" onclick="updateSubject();" style="font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;margin-left: 40px;display: inline-block;"></span>
									    <span id ="docSubjectUpdateSpan" style="display: inline-block;" onclick="updateSubject();"></span>
										<xform:text property="docSubject"  htmlElementProperties="id='docSubject'" showStatus="edit" style="width:97%;font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;display:none;margin-left: 40px;" value="" className="inputsgl lui_review_subject" />
									</c:otherwise>
								</c:choose>--%>
								<!-- #133744 模板主题自动生成，勾选主题可编辑，新建页面主题不可编辑 开始 -->
								<c:choose>
									<c:when test="${kmReviewMainForm.titleRegulation != null && kmReviewMainForm.titleRegulation != ''}">
										<%--如果有“主题自动生成规则”，也可以“编辑主题”的情况--%>
										<c:if test="${kmReviewMainForm.editDocSubject eq 'true'}">
											<span id ="docSubjectSpan" onclick="updateSubject();" style="font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;margin-left: 40px;">${lfn:message('km-review:kmReviewMain.docSubject.info')}</span>
											<span id ="docSubjectUpdateSpan" style="display: inline-block;" onclick="updateSubject();"></span>
											<xform:text property="docSubject"  htmlElementProperties="id='docSubject'" showStatus="edit" style="width:97%;font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;display:none;margin-left: 40px;" value="" className="inputsgl lui_review_subject" />
										</c:if>
										<%--如果有“主题自动生成规则”，不可以“编辑主题”的情况--%>
										<c:if test="${kmReviewMainForm.editDocSubject ne 'true'}">
											<xform:text property="docSubject"  htmlElementProperties="id='docSubject'" showStatus="readOnly" style="width:97%;font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;;margin-left: 40px;" value="${lfn:message('km-review:kmReviewMain.docSubject.info')}" className="inputsgl lui_review_subject" />
										</c:if>
									</c:when>
									<c:when test="${kmReviewMainForm.titleRegulation == null || kmReviewMainForm.titleRegulation == ''}">
										<%--如果是没有“主题自动生成规则”，统一可以“编辑主题”的情况--%>
										<xform:text property="docSubject" required="true" htmlElementProperties="id='docSubject' placeholder='请输入主题'" showStatus="edit" style="width:97%;font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;display:inline-block;margin-left: 40px;" value="" className="inputsgl lui_review_subject" />
										<span class="txtstrong">*</span>
									</c:when>
								</c:choose>
								<!-- #133744 模板主题自动生成，勾选主题可编辑，新建页面主题不可编辑 结束 -->
						    </c:if>
						    <%--<c:if test="${kmReviewMainForm.method_GET=='edit'}">
								    <span id ="docSubjectSpan" onclick="updateSubject();" style="font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;margin-left: 40px;display: inline-block;"></span>
								    <span id ="docSubjectUpdateSpan" style="display: inline-block;" onclick="updateSubject();"></span>
									<xform:text property="docSubject"  htmlElementProperties="id='docSubject'" showStatus="edit" style="width:97%;font-size: 20px;color: #111F2C;font-family: PingFangSC-Medium;letter-spacing: 0;display:none;margin-left: 40px;" value="" className="inputsgl lui_review_subject" />
						    </c:if>--%>
							</div>
							<html:hidden property="fdCanCircularize"/>
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
								<td width="99.6"></td>
								<td >
									<kmss:editor property="docContent" width="95%"/>
								</td>
							</tr>
							<!-- 相关附件 -->
							<tr KMSS_RowType="documentNews">
								<td class="td_normal_title dingAttachmentTitle" width=15%>
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
		</ui:tabpanel>
	</c:when>
</c:choose>

<%--流程--%>
<c:import url="/sys/workflow/import/sysWfProcess_edit_flow_ding.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmReviewMainForm" />
	<c:param name="fdKey" value="reviewMainDoc" />
	<c:param name="showHistoryOpers" value="true" />
	<c:param name="isExpand" value="true" />
	<c:param name="approveType" value="right" />
	<c:param name="approvePosition" value="right" />
</c:import>
