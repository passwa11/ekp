<%@ page language="java" pageEncoding="UTF-8"
	import="com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>
<%@ page import="com.landray.kmss.sys.organization.forms.SysOrgPersonForm"%>
<%
PasswordSecurityConfig securityConfig = new PasswordSecurityConfig();
String isEnable = securityConfig.getMobileNoUpdateCheckEnable();
String isEmailEnable = securityConfig.getAlterEmailEnabled();
%>
<template:include ref="person.cfg">
	<template:replace name="title">
		<template:super /> - ${lfn:message('sys-zone:sysZonePersonInfo') }
	</template:replace>
	<template:replace name="head">
	     <script>
    		window.CKEDITOR_BASEPATH='${ LUI_ContextPath}/resource/ckeditor/';
        </script>
		<script>
			seajs.use(['theme!zone']);
			Com_IncludeFile("security.js");
		</script>
	</template:replace>
	<template:replace name="content">
	<ui:tabpanel layout="sys.ui.tabpanel.list">
	    <ui:content title="${lfn:message('sys-zone:sysZonePersonInfo') }">
		<div class="lui_zone_per_right">
			<ul class="lui_zone_per_tab">
					<%-- 基本信息 --%>
			       <li class="lui_zone_per_header">
						<span class="title"><bean:message bundle="sys-zone" key="sysZonePersonInfo.basicInfo" /></span>
						<div class="content-r lui_zone_baseInfo_title">
							<span class="email" id="fdemail"><c:out value="${lfn:escapeHtml(sysOrgPerson.fdEmail)}"/></span>
							<span class="tel" id="fdtel"><c:out value="${sysOrgPerson.fdWorkPhone}"/></span>
							<span class="phone" id="fdphone"><c:out value="${lfn:escapeHtml(sysOrgPerson.fdMobileNo)}"/></span>
						</div>
						<span  class="lui_zone_base_up arrow"><bean:message key="button.edit" /></span>
					</li>
					<li class="lui_zone_edit_content_show lui_zone_edit_content_hide">
					    <table width=100% class="lui-per-r-t">
							<tr>
								<td><html:form
										action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do"
										styleId="sysZonePersonInfoForm">
										<table class="tb_normal" width=90%>
											<html:hidden property="fdId" value="${KMSS_Parameter_CurrentUserId}" />
											<tr>
												<%--姓名 --%>
												<td width="15%" class="td_normal_title"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.username" />
												</td>
												<td width="85%">
												<xform:text property="personName" showStatus="view" ></xform:text>
												</td>
											</tr>
											<tr>
												<%--电子邮件--%>
												<td width="15%" class="td_normal_title"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.email" /></td>
												<%if("true".equals(isEmailEnable)){ %>		
													<td width="85%">
														<xform:text property="email" value="${lfn:escapeHtml(sysOrgPerson.fdEmail)}" style="width:50%" showStatus="edit" validators="email maxLength(200)"></xform:text>
													</td>
												<%}else{ %>
													<td width="85%">
														${lfn:escapeHtml(sysOrgPerson.fdEmail)}
														<div style="display:none">
															<xform:text property="email" value="${lfn:escapeHtml(sysOrgPerson.fdEmail)}" showStatus="readOnly" validators="email maxLength(200)"></xform:text>
														</div>
													</td>
												<%} %>
											</tr>
											<tr>
												
												<%--手机号码--%>
												<td width="15%" class="td_normal_title"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.fdMobilePhone" /></td>
												<td width="85%">
												<%if("true".equals(isEnable)){ %>
												<xform:text property="mobilPhone" validators="phone uniqueMobileNo"
														style="width:50%" value="${lfn:escapeHtml(sysOrgPerson.fdMobileNo)}"  showStatus="edit" 
														htmlElementProperties="readonly=readonly placeholder='${ lfn:message('sys-organization:sysOrgPerson.moblieNo.tips') }'"/>
														<input type="button" value="<bean:message
														bundle="sys-zone" key="sysZonePersonInfo.fdMobilePhone.update" />" onclick="updateMobileNoControl();"></input>
												<%}else{ %>
												<xform:text property="mobilPhone" validators="phone uniqueMobileNo" 
														style="width:50%" value="${lfn:escapeHtml(sysOrgPerson.fdMobileNo) }" showStatus="edit" 
														htmlElementProperties="placeholder='${ lfn:message('sys-organization:sysOrgPerson.moblieNo.tips') }'"/>
												<%} %>
														</td>
											</tr>
											<tr>
												<%--短号--%>
												<td width="15%" class="td_normal_title"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.fdShortNo" /></td>
												<td width="85%"><xform:text property="fdShortNo"  
														style="width:90%"  value="${sysOrgPerson.fdShortNo}"  /></td>
											</tr>
											<tr>
												<%--公司电话--%>
												<td width="15%" class="td_normal_title"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.fdCompanyPhone" /></td>
												<td width="85%"><xform:text property="fdCompanyPhone"  
														style="width:90%"  value="${sysOrgPerson.fdWorkPhone}"/></td>
											</tr>
											<tr>
												<%--性别--%>
												<td width="15%" class="td_normal_title"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.fdSex" /></td>
												<td width="85%">
													<sunbor:enums property="fdSex" value="${sysOrgPerson.fdSex}" enumsType="sys_org_person_sex" elementType="radio" />
												</td>
											</tr>
											<tr>
												<%--默认语言--%>
												<td width="15%" class="td_normal_title"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.fdDefaultLang" /></td>
												<td width="85%">
													<%
													SysOrgPersonForm sysOrgPersonForm = (SysOrgPersonForm) request
															.getAttribute("sysOrgPersonForm");
													out.write(sysOrgPersonForm.getLangSelectHtml(request,
															"fdDefaultLang", sysOrgPersonForm.getFdDefaultLang()));
													%>
												</td>
											</tr>
											<tr>
												<td width="15%" class="td_normal_title">
													${lfn:message('sys-zone:sysZonePrivate.privacy.setting.person') }
												</td>
												<td width="85%">
													<label>
														<html:checkbox property="isContactPrivate" value="1" >
															${lfn:message('sys-zone:sysZonePrivate.isContactPrivate.hide') }
														</html:checkbox>
													</label>
													<label>
														<html:checkbox property="isDepInfoPrivate" value="1" >
															${lfn:message('sys-zone:sysZonePrivate.isDepInfoPrivate.hide') }
														</html:checkbox>
													</label>
												</td>
											</tr>
											<tr>
											     <td colspan="2" align="center">
											     	 <ui:button text="${lfn:message('button.save') }" order="2" onclick="formSubmit();"></ui:button>
											    </td>						
										    </tr>
										</table>
										<html:hidden property="method_GET" />
									</html:form>  
								</td>
							</tr>
						</table>
					</li>
					<%-- 个人签名 --%>
					<li class="lui_zone_per_header">
						<span class="title"><bean:message bundle="sys-zone" key="sysZonePersonInfo.personalSign" /></span>
						<div id="fdSignatureTextSpan" class="content-r textEllipsis">
							<c:out value="${sysZonePersonInfoForm.fdSignature}"/>
						</div>
						<span  class="lui_zone_base_up arrow"><bean:message key="button.edit" /></span>
					</li>
					<li class="lui_zone_edit_content_show lui_zone_edit_content_hide">
					   <table width=100% class="lui-per-r-t" id="signature">
						    <tr>
						     	<td align="center">
						    		<textarea validate="maxLength(600)" 
						    			class="lui_zone_edit_signature" id="fdSignatureArea"><c:out value="${sysZonePersonInfoForm.fdSignature}"/></textarea>
						    	</td>
						    </tr>
						    <tr>
						    	<td  align="center">
						    		<ui:button text="${lfn:message('button.save') }" order="2" onclick="saveFdSignature(this);"/>
						    	</td>
						    </tr>
					    </table>
					</li>
					<%-- 个人资料 --%>
					<li class="lui_zone_per_header">
						<span class="title"><bean:message bundle="sys-zone" key="sysZonePersonInfo.personalInfo" /></span>
						
						<div class="content-r" style="display: none" id="personalInfoDefault">
							<bean:message bundle="sys-zone" key="sysZonePersonInfo.personalInfoDefault" />
						</div>
						<script>
						 seajs.use(["lui/topic", "lui/jquery"], function(topic, $) {
							 topic.subscribe("sys/zone/personInfo/data", function(data) {
								if(data && data.isSelfData == false) {
									$("#personalInfoDefault").show();
								}
							 });
						 });
						</script>
					    
					    <span  class="lui_zone_base_up arrow"><bean:message key="button.edit" /></span>
					</li>
					<li class="lui_zone_edit_content_show lui_zone_edit_content_hide staffYpage_detailResume" id="person_data_details">
						<c:import url="/sys/zone/sys_zone_personInfo/sysZonePersonInfo_personData_edit_import.jsp" charEncoding="UTF-8"> 
							<c:param name="userId" value="${KMSS_Parameter_CurrentUserId}"/>
							<c:param name="method" value="edit"/>
						</c:import>
					</li>
					<%-- 个人标签 --%>
					 <kmss:authShow roles="ROLE_SYSTAG_DEFAULT" >
					 <% request.setAttribute("isTag", "isTag") ;%>
                    </kmss:authShow>
                  
				   <li  <c:choose><c:when test="${not empty isTag}">
                   class="lui_zone_per_header"
                   </c:when>
                   <c:otherwise>  class="lui_zone_per_header header"</c:otherwise>
                   </c:choose>     ><span class="title"><bean:message bundle="sys-zone" key="sysZonePersonInfo.personalTag" /></span>
						<div id="tags" class="content-r">${lfn:escapeHtml(sysZonePersonInfoForm.sysTagMainForm.fdTagNames)}</div>
						 <kmss:authShow roles="ROLE_SYSTAG_DEFAULT" ><span  class="lui_zone_base_up arrow"><bean:message key="button.edit" /></span> </kmss:authShow>
                    </li>
                   
                    <li class="lui_zone_edit_content_show  lui_zone_edit_content_hide" id="tag_li">
                       <div style="width:90%;margin:0 auto">
							<c:import url="/sys/zone/import/sysTagMain_ui_edit.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="sysZonePersonInfoForm" />
									<c:param name="fdKey" value="zonePersonInfoDoc" /> 
									<c:param name="modelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo" />
									<c:param name="method"  value="edit"/>
									<c:param name="dialogElement" value=".lui_zone_per_right"></c:param>
							</c:import>
						</div>
                    </li>
                    <%-- 个人简历 --%>
					<li class="lui_zone_per_header" >
						<span class="title"><bean:message bundle="sys-zone" key="sysZonePersonInfo.personalResume" /></span> 
						<div class="content-r textEllipsis" id="resumeName">
							<c:out value="${personResume.fdFileName}"/>
						</div>
						<span  class="lui_zone_base_up arrow"><bean:message key="button.edit" /></span>
					</li>
					<li class="lui_zone_edit_content_show lui_zone_edit_content_hide">
						<input type="hidden" id="oldResumeId" name="oldResumeId" value="">
				        <input type="hidden" id="resumeId" name="resumeId" value="">
				        <input type="hidden" id="isDel" value="1">
						<table class="tb_normal" width=100%>
							<tr>
								<td align="center" >
									<ui:button text="${ lfn:message('sys-zone:sysZonePersonInfo.saveResume') }" onclick="saveResume();" id="saveResume" />
								</td>
							</tr>
							<tr>
								<td>
									<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do">
										<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								          <c:param name="fdKey" value="personResume"/>
								          <c:param name="fdMulti" value="false" />
								          <c:param name="enabledFileType" value="*.doc;*.docx;*.ppt;*.pptx" />
								          <c:param name="uploadText" value="${ lfn:message('sys-zone:sysZonePersonInfo.uploadText') }" />
								          <c:param name="fdModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
			          					  <c:param name="fdModelId" value="${sysZonePersonInfoForm.fdId}"/>
								        </c:import> 
							        </html:form>
								</td>
							</tr>
							<tr>
									<td>
										<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do">
											<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
												<c:param name="formName" value="sysZonePersonInfoForm" />
												<c:param name="moduleModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo" />
											</c:import>
										</html:form>
									</td>
							</tr>
						</table>
					</li>
			</ul> 
		</div>
	</ui:content>
	</ui:tabpanel>
 <script type="text/javascript">
	Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|doclist.js|dialog.js");
</script>
<!-- 引入通用的手机号校验规则 -->
<c:import url="/sys/organization/sys_org_person/common_mobileNo_check.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="sysZonePersonInfoForm" />
</c:import>
 <script>
    seajs.use(['theme!module']);
 	var valid = $KMSSValidation(document.forms['sysZonePersonInfoForm']);
 	function formSubmit() {
 		if (!valid.validate()) {
			return;
 		}
		seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
			$.ajax({
				url : '${LUI_ContextPath}/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=saveOrgPersonInfo',
				type : 'POST',
				dataType : 'text',
				async : false,
				data : $("#sysZonePersonInfoForm").serialize(),
				success:function(data) {
					if(data == 'true') {
						$("#fdphone").text($("[name='mobilPhone']").val());
						$("#fdemail").text($("[name='email']").val());
						$("#fdtel").text($("[name='fdCompanyPhone']").val());
						<%if("true".equals(isEnable)){ %>
						$("[name='mobilPhone']").attr("readonly","readonly"); 
						<% } %>
						dialog.success('<bean:message key="return.optSuccess" />', $('#sysZonePersonInfoForm'));
						
					} else {
						dialog.failure('<bean:message key="return.optFailure" />', $('#sysZonePersonInfoForm'));
					}
				} 
			});
		});
 	}

	function saveLang() {
		seajs.use(["lui/jquery","lui/dialog"], function($, dialog) {
			var fdDefaultLang = $("[name='fdDefaultLang']").val();
			$.ajax({
				url : '${LUI_ContextPath}/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=saveOrgLang',
				type : 'POST',
				dataType : 'json',
				async : false,
				data : {
						"fdDefaultLang" : fdDefaultLang
						},
				success:function(data) {
					if(data&&data.langText) {
						$("#fdLangTextSpan").text(data.langText);
						dialog.success('<bean:message key="return.optSuccess" />');
					}
				},
				error: function() {
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			});
		});
	}
 	
 	// 个性签名保存
	function saveFdSignature() {
		var validate = $KMSSValidation(document.getElementById("signature"));
		if(!validate.validate()) {
			return;
		}
		seajs.use(['lui/jquery', 'lui/dialog','lui/util/env'], function($, dialog, env){
			 var fdSignatureArea=$.trim($("#fdSignatureArea").val());
			 var sign = encodeURIComponent(fdSignatureArea);
			 $.ajax({
					url : '${LUI_ContextPath}/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=saveFdSignature',
					type : 'POST',
					dataType:"json",
					async : false,
					data :{fdSignatureArea:sign},
					success : function(data) {
						if (data == true) {
							dialog.success('<bean:message key="return.optSuccess" />', $("#signature"));
							$("#fdSignatureTextSpan").html(env.fn.formatText($.trim($("#fdSignatureArea").val())));
						} else {
							dialog.failure('<bean:message key="return.optFailure" />',$("#signature"));
						}
					},
					error : function(data) {
						if( data.responseJSON.message == null){
							dialog.failure('<bean:message key="return.optFailure" />');
						}else{
							dialog.failure(data.responseJSON.message[0].msg);
		                }
					}
				});
		});
	}
	LUI.ready(function(){	
		seajs.use('lui/jquery',function($){
			$(".lui_zone_per_tab .lui_zone_per_header").click(function(){
				
				if($(this).hasClass("header")){
				 return
				}
				else{
					if($(this).next().hasClass("lui_zone_edit_content_hide")){
						 $(this).next().show();
						 $(this).next().removeClass("lui_zone_edit_content_hide");
						 $(this).find(".arrow").html("${lfn:message('sys-zone:sysZonePersonInfo.packup')}");
					 }else{
						 $(this).next().hide();
						 $(this).next().addClass("lui_zone_edit_content_hide");
						 $(this).find(".arrow").html("${lfn:message('button.edit')}");
					 }
				}
			 });
		});
	});
			/*
			*简历上传
			*/
			var fileId = null;
			var attName = '';
			var hasSaveResume = false;
			attachmentObject_personResume.on("uploadSuccess", function() {
				//清空附件机制生成的信息
				var fileList =  attachmentObject_personResume.fileList;
				fileId = fileList[fileList.length - 1].fileKey;
				attName = fileList[fileList.length - 1].fileName;
				//fileList.length = 0;
				//展示附件
				$("#person_resume_content").show();
				$("#person_resume_content_show").text(attName);
				$("#resumeId").val(fileId);
				$("#saveResume").show(); 
			});
			$(document).on("alterName", function() {
				var fileList =  attachmentObject_personResume.fileList;
				fileId = fileList[fileList.length - 1].fileKey;
				attName = fileList[fileList.length - 1].fileName; 
			}); 
			attachmentObject_personResume.on("editDelete", function(file) {
				$('#person_resume_content').hide();
				$("#resumeId").val("");
				$("#isDel").val("0");
				$("#saveResume").show();
				attName = '';
			});
			/*
			*保存简历
			*/
			function saveResume() {
				seajs.use(['lui/jquery', 'lui/dialog','lui/util/env'], function($, dialog, env) {
					<c:if test="${personResume == null}">
						if($("#resumeId").val() == "" && !hasSaveResume){	
								dialog.failure("${ lfn:message('sys-zone:sysZonePersonInfo.uploadResume') }");
							return;
						}
					</c:if>
					var load 
						 = dialog.loading("${ lfn:message('sys-zone:sysZonePersonInfo.resumeSaveLoading') }");
					$.ajax({
						type:"post",
						url:"${LUI_ContextPath}/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=saveResume",		
						data:{
								//personId:"${KMSS_Parameter_CurrentUserId}",
								fileName:base64Encode(attName),
							 	fileId:$("#resumeId").val(),
							 	authAttDownloadIds:$("input[name='authAttDownloadIds']")[0].value,
							 	authAttNodownload:$("input[name='authAttNodownload']")[0].checked,
							 	isDel:$("#isDel").val()
							 //	authAttNodownload:$("checkbox[name='authAttNodownload']")[0].val()
							 },
						dataType: 'json',
						success:function(data) {
							load.hide();
							if(data && data.code == 1) {
								$("#saveResume").hide();
								dialog.success("${ lfn:message('sys-zone:sysZonePersonInfo.saveSuccess') }");
								$("#resumeName").text(attName);
								hasSaveResume = true;
							} else {
								if(data && data.msg) {
									dialog.failure(data.msg);
								} else {
									dialog.failure("${ lfn:message('sys-zone:sysZonePersonInfo.saveFailure') }");
								}
							}
						},
						error:function() {
							load.hide();
							dialog.failure("${ lfn:message('sys-zone:sysZonePersonInfo.saveFailure') }");
						}
					});
					
				});
			}
			/*
			*删除简历
			*/
			function delPersonResume() {
				seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
					dialog.confirm("${ lfn:message('sys-zone:sysZonePersonInfo.confirmDelete') }", function(flag, d) {
						if (flag) {
							$('#person_resume_content').hide();
							$("#resumeId").val("");
							$("#isDel").val("0");
							$("#saveResume").show();
							attName = '';
						} else {
							//nothing
						}
					});
				});
			}

			function updateMobileNoControl() {
				seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
					dialog.iframe('/sys/zone/sys_zone_personInfo/validate.jsp',"${ lfn:message('sys-zone:sysZonePersonInfo.password.input') }",function(value){
						if(value=="true"){
								$("[name='mobilPhone']").removeAttr("readonly"); 
						}else if(value=="false"){
						}
					 },{height:'175',width:'300'});
				});
		 	}

			
		</script>
	</template:replace>
</template:include>
