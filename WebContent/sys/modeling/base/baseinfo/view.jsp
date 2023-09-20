<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('sys-modeling-base:modeling.baseinfo.BasicInformation')}</span>
		</h2>
		
			<script>
				Com_IncludeFile("doclist.js");
			</script>
			<center>
				<div style="margin:auto auto 60px;">
					<table class="tb_normal" width=95%>
						<tr>
						<%--应用名称 --%>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-base:modeling.baseinfo.ApplicationName')}
							</td>
							<td colspan="3" width=85%>
								<xform:text property="fdAppName" style="width:50%" />
								<c:if test="${modelingApplicationForm.fdValid eq 'true' }">
									<a href="javascript:void(0);" style="color:#1b83d8;float:right;" onclick="linkToIndex();">
										${lfn:message('sys-modeling-base:modeling.app.home')}
									</a>								
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-base:modeling.baseinfo.ApplicationPath')}
							</td>
							<td colspan="3" width=85%>
								<!-- <span style="padding:0px 5px">sys/modeling/</span> -->
								<xform:text property="fdUrl" style="width:10%" />
							</td>
						</tr> 
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-base:modeling.app.desrc')}
							</td>
							<td colspan="3" width=85%>
								<xform:textarea property="fdAppDesc" style="width:85%" />
							</td>
						</tr> 
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-base:modeling.app.icon')}
							</td>
							<td width=35%>
								<i class="iconfont_nav ${modelingApplicationForm.fdIcon}" style="color:#999;font-size:40px;"></i>
							</td>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-base:modeling.app.status')}
							</td>
							<td width=35%>
								<c:if test="${modelingApplicationForm.fdValid eq 'true' }">
									${lfn:message('sys-modeling-base:modeling.app.status.open')}
								</c:if>
								<c:if test="${modelingApplicationForm.fdValid eq 'false' }">
									${lfn:message('sys-modeling-base:modeling.app.status.forbid')}
								</c:if>
							</td>
						</tr>
						<kmss:ifModuleExist path="/dbcenter/echarts/">
							<tr>
								<td class="td_normal_title" width=15%>
									${lfn:message('sys-modeling-base:modeling.baseinfo.ChartCenter')}
								</td>
								<td colspan="3" width=85%>
									<c:if test="${modelingApplicationForm.fdEnableDbCenter eq 'true' }">
										${lfn:message('sys-modeling-base:modeling.app.status.open')}
									</c:if>
									<c:if test="${modelingApplicationForm.fdEnableDbCenter eq 'false' }">
										${lfn:message('sys-modeling-base:modeling.app.status.forbid')}
									</c:if>
								</td>
							</tr>
						</kmss:ifModuleExist>
						<tr>
							<td colspan="4">
								<div class="model-details">
									<span>${lfn:message('sys-modeling-base:modelingAppListview.fdModel')}</span>
									<div class="model-details-tables">
										<c:import url="/sys/modeling/base/model_detailstable.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="modelingApplicationForm" />
											<c:param name="status" value="view"></c:param>
										</c:import>
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-base:modeling.app.maintainablePerson')}
							</td>
							<td colspan="3" width=85%>
								<bean:write name="modelingApplicationForm" property="authEditorNames"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('sys-modeling-base:modeling.baseinfo.CanReader')}
							</td>
							<td colspan="3" width=85%>
								<div id="Cate_AllUserId">
									<bean:write name="modelingApplicationForm" property="authReaderNames"/>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</center>
		
	 	<script type="text/javascript">
	 		var modeling_validation = $KMSSValidation();
	 		
	 		function Cate_CheckNotReaderFlag(el){
	 			document.getElementById("Cate_AllUserId").style.display=el.checked?"none":"";
	 			document.getElementById("Cate_AllUserNote").style.display=el.checked?"none":"";
	 			el.value=el.checked;
	 		}
	 		
	 		function Cate_Win_Onload(){
	 			Cate_CheckNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
	 		}

	 		Com_AddEventListener(window, "load", Cate_Win_Onload);
	 		
	 		function linkToIndex(){
	 			var url = "${LUI_ContextPath}/sys/modeling/main/index.jsp?fdAppId=${param.fdId}";
 				Com_OpenWindow(url);
	 		}
	 		
	 	</script>
	</template:replace>
</template:include>
