<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" showQrcode="false">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<script>
			Com_IncludeFile("doclist.js");
		</script>
		<style type="text/css">
			.add-btn{color: #37ace1 !important;text-decoration: underline;}
			#locationsList tr:first-child td{padding-top: 0}
			.tb_simple > tbody > tr > td{word-break:break-word}
		</style>
	</template:replace>
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendCategoryForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-attend:module.sys.attend') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysAttendCategoryForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="onSubmitMethod('update');"></ui:button>
				</c:when>
				<c:when test="${ sysAttendCategoryForm.method_GET == 'add'}">
					<ui:button text="${ lfn:message('button.save') }" onclick="onSubmitMethod('save');"></ui:button>
					<c:if test="${empty sysAttendCategoryForm.fdAppId }">
						<ui:button text="${ lfn:message('button.saveadd') }" onclick="onSubmitMethod('saveadd');"></ui:button>
					</c:if>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-attend:sysAttendCategory.custom') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/attend/sys_attend_category/sysAttendCategory.do">
			<html:hidden property="fdOrder" />
			<html:hidden property="fdAppId" />
			<html:hidden property="fdAppName" />
			<html:hidden property="fdAppKey" />
			<html:hidden property="fdAppUrl" />
			<html:hidden property="fdStatus" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreatorName" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdId" />
			<html:hidden property="fdTemplateId" />
			<html:hidden property="method_GET" />
			<html:hidden property="docStatus" value="30" />
			<%-- 签到类型 --%>
			<html:hidden property="fdType" />
			<div class="lui_form_content_frame" style="padding-top:20px">
				<div class="lui-singin-creatPage">
					<%-- 基本信息 --%>
					<div class="lui-singin-creatPage-panel">
				    	<div class="lui-singin-creatPage-panel-body">
							<table class="tb_simple" width="100%">
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.custom.fdName"/>
									</td>
									<td>
										<xform:text property="fdName" style="width:95%" subject="${ lfn:message('sys-attend:sysAttendCategory.custom.fdName') }"/>
									</td>
								</tr>
								<c:if test="${not empty sysAttendCategoryForm.fdAppName }">
								<tr>
									<td colspan="2">
										<span class="lui-singin-fdAppName-title" style="margin-left:25px">
											<bean:message bundle="sys-attend" key="sysAttendCategory.fdAppName"/> ：
										</span>
										<span class="lui-singin-fdAppName">${ sysAttendCategoryForm.fdAppName}</span>
									</td>
								</tr>
								</c:if>
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdManager"/>
									</td>
									<td>
										<xform:address propertyId="fdManagerId" propertyName="fdManagerName" orgType="ORG_TYPE_PERSON" mulSelect="false"
											subject="${ lfn:message('sys-attend:sysAttendCategory.fdManager') }" required="true"
											style="width:95%" />
									</td>
								</tr>
								<%-- 所属场所 --%>
								<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
								    <c:param name="id" value="${sysAttendCategoryForm.authAreaId}"/>
								</c:import>
								<c:if test="${empty sysAttendCategoryForm.fdAppName }">
								<tr>
									<td colspan="2">
										<span class="lui-singin-fdAppName-title" style="margin-left:25px">
											<bean:message bundle="sys-attend" key="table.sysAttendCategoryTemplate"/> ：
										</span>
										<span id="fdTemplateName" class="lui-singin-fdAppName">
											<c:out value="${sysAttendCategoryForm.fdTemplateName }" />
										</span>
									</td>
								</tr>
								</c:if>
							</table>
				    	</div>
			    	</div>
			    	<%-- 签到范围 --%>
			    	<div class="lui-singin-creatPage-panel">
					    <div class="lui-singin-creatPage-panel-heading">
					        <h2 class="lui-singin-creatPage-panel-heading-title"><span id="signTargetsTitle">${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }</span></h2>
					    </div>
					    <%-- 人员 --%>
					    <div class="lui-singin-creatPage-panel-body" id="signTargetsBody">
							<table class="tb_simple" width="100%">
								<tr>
									<td class="td_normal_title" style="vertical-align: top;">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdTargets"/>
									</td>
									<td>
										<xform:address propertyId="fdTargetIds" propertyName="fdTargetNames" 
											subject="${ lfn:message('sys-attend:sysAttendCategory.fdTargets') }"
											mulSelect="true" orgType="ORG_TYPE_ALLORG" textarea="false" style="width:95%" required="true" />
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="sys-attend" key="sysAttendCategory.fdExcTargets"/>
									</td>
									<td>
										<xform:address propertyId="fdExcTargetIds" propertyName="fdExcTargetNames" 
											mulSelect="true" orgType="ORG_TYPE_POSTORPERSON" style="width:95%" />
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
									</td>
									<td>
										<xform:checkbox property="fdIsAllowView" showStatus="edit">
							   				<xform:simpleDataSource value="true">
							   					${ lfn:message('sys-attend:sysAttendCategory.fdIsAllowView') }
							   					<span style="color: red;margin-left: 10px;font-size: 13px;">${ lfn:message('sys-attend:sysAttendCategory.fdIsAllowView.tips') }</span>
							   				</xform:simpleDataSource>
							 			</xform:checkbox>
									</td>
								</tr>
								<c:if test="${not empty sysAttendCategoryForm.fdAppName }">
									<tr>
										<td class="td_normal_title">
										</td>
										<td>
											<xform:checkbox property="fdUnlimitTarget" showStatus="edit">
								   				<xform:simpleDataSource value="true">
								   					<bean:message bundle="sys-attend" key="sysAttendCategory.fdUnlimitTarget"/>
								   					<span style="color: red;margin-left: 10px;font-size: 13px;"><bean:message bundle="sys-attend" key="sysAttendCategory.fdUnlimitTarget.tips"/></span>
								   				</xform:simpleDataSource>
								 			</xform:checkbox>
										</td>
									</tr>
									<tr>
										<td class="td_normal_title">
										</td>
										<td>
											<xform:checkbox property="fdUnlimitOuter" showStatus="edit">
												<xform:simpleDataSource value="true">
								   					<bean:message bundle="sys-attend" key="sysAttendCategory.fdUnlimitOuter"/>
								   					<span style="color: red;margin-left: 10px;font-size: 13px;"><bean:message bundle="sys-attend" key="sysAttendCategory.fdUnlimitOuter.tips"/></span>
								   				</xform:simpleDataSource>
								 			</xform:checkbox>
										</td>
									</tr>
								</c:if>
							</table>
					    </div>
				    </div>
				    <%-- 签到时间 --%>
				    <div class="lui-singin-creatPage-panel">
				    	<div class="lui-singin-creatPage-panel-heading">
					        <h2 class="lui-singin-creatPage-panel-heading-title"><span id="signTimeTitle">${ lfn:message('sys-attend:sysAttendCategory.attendDate') }</span></h2>
					    </div>
					    <div class="lui-singin-creatPage-panel-body" id="signTimeBody">
					    	<%-- 签到日期 --%>
					    	<div class="lui-singin-creatPage-table">
					    		<div class="caption">${ lfn:message('sys-attend:sysAttendCategory.attendDate') }</div>
					    		<div class="content">
					    			<div class="lui-singin-creatPage-tab">
					    				<input type="hidden" name="fdPeriodType" />
					    				<ul class="lui-singin-creatPage-tab-heading">
					    					<li class="lui-sign-date-type" id='weekDate' onclick="changeSignDateType('1')">
					    						<a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdPeriodType.week') }</a>
					    					</li>
					    					<li class="lui-sign-date-type" id='customDate' onclick="changeSignDateType('2')">
					    						<a href="javascript:void(0);">${ lfn:message('sys-attend:sysAttendCategory.fdPeriodType.custom') }</a>
					    					</li>
					    				</ul>
					    				<div class="lui-singin-creatPage-tab-body">
					    					<%-- 周期 --%>
					    					<table id="weekTB" class="tb_simple" width="100%">
					    						<%-- 星期 --%>
					    						<tr>
													<td class="td_normal_title td_tab_title">
														<bean:message bundle="sys-attend" key="sysAttendCategory.fdWeek"/>
													</td>
													<td>
														<xform:checkbox property="fdWeek"
															subject="${ lfn:message('sys-attend:sysAttendCategory.fdWeek') }"
															isArrayValue="false">
															<xform:enumsDataSource enumsType="sysAttendCategory_fdWeek" />
														</xform:checkbox>
													</td>
												</tr>
												<%-- 追加排除日期 --%>
												<tr>
													<td class="td_normal_title td_tab_title">
														<bean:message bundle="sys-attend" key="sysAttendCategory.fdTimes"/>
													</td>
													<td>
														<%--追加日期明细表 --%>
														<table id='appendDateList' class="tb_simple" width="100%">
															<tr KMSS_IsReferRow="1" style="display:none">
																<td>
																	<input type="hidden" name="fdTimes[!{index}].fdId" /> 
																	<input type="hidden" name="fdTimes[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																	<xform:datetime htmlElementProperties="data-templ-id='appendDateTempl'"
																		property="fdTimes[!{index}].fdTime" 
																		subject="${ lfn:message('sys-attend:sysAttendCategory.fdTimes') }"
																		dateTimeType="date"
																		required="true"
																		style="width:93%"></xform:datetime>
																	<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																		<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																	</a>
																</td>
															</tr>
															<c:forEach items="${sysAttendCategoryForm.fdTimes}" var="fdTimesItem" varStatus="vstatus">
																<tr KMSS_IsContentRow="1">
																	<td>
																		<input type="hidden" name="fdTimes[${vstatus.index}].fdId" value="${fdTimesItem.fdId}" /> 
																		<input type="hidden" name="fdTimes[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																		<xform:datetime property="fdTimes[${vstatus.index}].fdTime" 
																			subject="${ lfn:message('sys-attend:sysAttendCategory.fdTimes') }"
																			dateTimeType="date"
																			required="true"
																			style="width:93%"></xform:datetime>
																		<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																			<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																		</a>
																	</td>
																</tr>
															</c:forEach>
															<tr>
																<td colspan="2">
																	<a href="javascript:void(0);" class="add-btn" onclick="DocList_AddRow('appendDateList');" title="${lfn:message('doclist.add')}">
																		${ lfn:message('sys-attend:sysAttendCategory.new.date') }
																	</a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="td_normal_title td_tab_title">
														<bean:message bundle="sys-attend" key="sysAttendCategory.fdExcTimes"/>
													</td>
													<td>
														<%-- 排除日期明细表 --%>
														<table id="exceptDateList" class="tb_simple" width="100%">
															<tr KMSS_IsReferRow="1" style="display:none">
																<td>
																	<input type="hidden" name="fdExcTimes[!{index}].fdId" /> 
																	<input type="hidden" name="fdExcTimes[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																	<xform:datetime htmlElementProperties="data-templ-id='exceptDateTempl'" 
																		property="fdExcTimes[!{index}].fdExcTime" 
																		subject="${ lfn:message('sys-attend:sysAttendCategory.fdExcTimes') }"
																		dateTimeType="date"
																		required="true"
																		style="width:93%"></xform:datetime>
																	<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																		<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																	</a>
																</td>
															</tr>
															<c:forEach items="${sysAttendCategoryForm.fdExcTimes}" var="fdExcTimesItem" varStatus="vstatus">
																<tr KMSS_IsContentRow="1">
																	<td>
																		<input type="hidden" name="fdExcTimes[${vstatus.index}].fdId" value="${fdExcTimesItem.fdId}" /> 
																		<input type="hidden" name="fdExcTimes[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																		<xform:datetime property="fdExcTimes[${vstatus.index}].fdExcTime" 
																			subject="${ lfn:message('sys-attend:sysAttendCategory.fdExcTimes') }"
																			dateTimeType="date"
																			required="true"
																			style="width:93%"></xform:datetime>
																		<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																			<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																		</a>
																	</td>
																</tr>
															</c:forEach>
															<tr>
																<td colspan="2">
																	<a href="javascript:void(0);" class="add-btn" onclick="DocList_AddRow('exceptDateList');" title="${lfn:message('doclist.add')}">
																		${ lfn:message('sys-attend:sysAttendCategory.new.date') }
																	</a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
					    					</table>
					    					<%-- 自定义日期 --%>
					    					<table id="customDateTB" class="tb_simple" width="100%">
					    						<tr>
					    							<td class="td_normal_title td_tab_title">
														<bean:message bundle="sys-attend" key="sysAttendCategoryTime.fdTime"/>
													</td>
													<td>
														<%-- 自定义日期明细表 --%>
														<table id="customDateList" class="tb_simple" width="100%">
															<tr KMSS_IsReferRow="1" style="display:none">
																<td>
																	<input type="hidden" name="fdTimes[!{index}].fdId" /> 
																	<input type="hidden" name="fdTimes[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																	<xform:datetime htmlElementProperties="data-templ-id='customDateTempl'"
																		property="fdTimes[!{index}].fdTime" 
																		subject="${ lfn:message('sys-attend:sysAttendCategory.fdTimes') }"
																		dateTimeType="date"
																		required="true"
																		style="width:93%" validators="afterToday"></xform:datetime>
																	<c:if test="${empty fromModel }">
																		<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																			<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																		</a>
																	</c:if>
																</td>
															</tr>
															<c:forEach items="${sysAttendCategoryForm.fdTimes}" var="fdTimesItem" varStatus="vstatus">
																<tr KMSS_IsContentRow="1">
																	<td>
																		<input type="hidden" name="fdTimes[${vstatus.index}].fdId" value="${fdTimesItem.fdId}" /> 
																		<input type="hidden" name="fdTimes[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																		<xform:datetime property="fdTimes[${vstatus.index}].fdTime" 
																			subject="${ lfn:message('sys-attend:sysAttendCategory.fdTimes') }"
																			dateTimeType="date"
																			required="true"
																			style="width:93%" validators="afterToday"></xform:datetime>
																		<c:if test="${empty fromModel }">
																			<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
																				<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																			</a>
																		</c:if>
																	</td>
																</tr>
															</c:forEach>
															<c:if test="${empty fromModel }">
																<tr>
																	<td colspan="2">
																		<a href="javascript:void(0);" class="add-btn" onclick="DocList_AddRow('customDateList');" title="${lfn:message('doclist.add')}">
																			${ lfn:message('sys-attend:sysAttendCategory.new.date') }
																		</a>
																	</td>
																</tr>
															</c:if>
														</table>
													</td>
					    						</tr>
					    					</table>
					    				</div>
					    			</div>
					    		</div>
					    	</div>					    	
					    	<%-- 开放关闭打卡时间 --%>
					    	<div class="lui-singin-creatPage-table">
					    		<table class="tb_simple" width="100%">
					    			<tr>
					    				<td class="td_normal_title" style="width:100px">
											<bean:message bundle="sys-attend" key="sysAttendCategory.fdStartTime"/>
										</td>
										<td width=40%>
											<xform:datetime minuteStep="1" property="fdStartTime" dateTimeType="time" validators="beforeEnd" style="width:90%"></xform:datetime>
										</td>
										<td class="td_normal_title" style="width:100px">
											<bean:message bundle="sys-attend" key="sysAttendCategory.fdEndTime"/>
										</td>
										<td>
											<xform:datetime minuteStep="1" property="fdEndTime" dateTimeType="time" validators="afterStart afterNow" style="width:90%"></xform:datetime>
										</td>
					    			</tr>
					    		</table>
					    	</div>
					    	<%-- 迟到时间 --%>
					    	<c:if test="${not empty sysAttendCategoryForm.fdAppName }">
					    		<div class="lui-singin-creatPage-table">
					    		<table class="tb_simple" width="100%">
					    			<tr>
					    				<td class="td_normal_title" style="width:100px">
											${ lfn:message('sys-attend:sysAttendCategoryRule.lateTime') }
										</td>
										<td >
											<xform:datetime minuteStep="1" property="fdRule[0].fdInTime" validators="betweenTime afterNow" dateTimeType="time" style="width:100px;"></xform:datetime>								
											<span style="padding-left: 8px;line-height: 2.4;"><bean:message bundle="sys-attend" key="sysAttendCategoryRule.lateTime.setting"/></span>
										</td>										
					    			</tr>
					    		</table>
					    	</div>
					    	</c:if>
				    	</div>
			    	</div>
			    	
		    		<%-- 签到方式 --%>
			    	<div class="lui-singin-creatPage-panel">
			    		<div class="lui-singin-creatPage-panel-heading">
			    			<h2 class="lui-singin-creatPage-panel-heading-title"><span id="signTypeTitle">${ lfn:message('sys-attend:sysAttendCategoryRule.fdMode') }</span></h2>
			    		</div>
			    		<div class="lui-singin-creatPage-panel-body" id="signTypeBody">
			    			<div class="lui-singin-creatPage-table">
			    				<div class="caption" style="padding-top: 15px;">${ lfn:message('sys-attend:sysAttendCategory.singin.map') }</div>
			    				<div class="content">
					    			<table class="tb_simple" width="100%" id="fdLimitLocations">
			   							<%-- 范围 --%>
			   							<tr id="fdLimitTR" style="display: none;">
			   								<td>
			   									<xform:text property="fdRule[0].fdLimit" subject="${ lfn:message('sys-attend:sysAttendCategoryRule.fdLimit') }" validators="" required="true" style="width:80px;margin-right:8px;" htmlElementProperties="disabled='disabled'"></xform:text>
												<bean:message bundle="sys-attend" key="sysAttendCategoryRule.fdLimit.setting"/>
			   								</td>
			   							</tr>
			   							<%-- 地点 --%>
			   							<tr>
			   								<td>
			   									<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
			   									<table id="locationsList" class="tb_simple" width="100%">
			   										<tr KMSS_IsReferRow="1" style="display:none">
			   											<td>
			   												<input type="hidden" name="fdLocations[!{index}].fdId" /> 
															<input type="hidden" name="fdLocations[!{index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
															<map:location propertyName="fdLocations[!{index}].fdLocation" propertyCoordinate="fdLocations[!{index}].fdLocationCoordinate" 
																subject="${ lfn:message('sys-attend:sysAttendCategory.fdLocations') }"
																style="width:97%;float:left;" required="true"></map:location>
															<a href="javascript:void(0);" onclick="deleteLocation();" title="${lfn:message('doclist.delete')}">
																<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
															</a>
														</td>
			   										</tr>
			   										<c:forEach items="${sysAttendCategoryForm.fdLocations}" var="fdLocationsItem" varStatus="vstatus">
			   											<tr KMSS_IsContentRow="1">
			   												<td width="90%">
			   													<input type="hidden" name="fdLocations[${vstatus.index}].fdId" value="${fdLocationsItem.fdId}" /> 
																<input type="hidden" name="fdLocations[${vstatus.index}].fdCategoryId" value="${sysAttendCategoryForm.fdId}" />
																<map:location propertyName="fdLocations[${vstatus.index}].fdLocation" propertyCoordinate="fdLocations[${vstatus.index}].fdLocationCoordinate" 
																	subject="${ lfn:message('sys-attend:sysAttendCategory.fdLocations') }"
																	style="width:97%;float:left;" required="true"></map:location>
																<a href="javascript:void(0);" onclick="deleteLocation();" title="${lfn:message('doclist.delete')}">
																	<img src="${KMSS_Parameter_ContextPath}sys/attend/resource/images/delete_btn.png"/>
																</a>
															</td>
			   											</tr>
			   										</c:forEach>
													<tr>
														<td colspan="2">
															<a href="javascript:void(0);" class="add-btn" onclick="addLocation();" title="${lfn:message('doclist.add')}">
																${ lfn:message('sys-attend:sysAttendCategory.new.location') }
															</a>
														</td>
													</tr>
			   									</table>
			   								</td>
			   							</tr>
			   						</table>
			    				</div>
			    			</div>
			    			<%-- 二维码 --%>
			    			<c:if test="${not empty sysAttendCategoryForm.fdAppName }">
			    			<div class="lui-singin-creatPage-table">
			    				<div class="caption" style="padding-top: 16px;">${ lfn:message('sys-attend:sysAttendCategory.sign.qrcode') }</div>
			    				<div class="content">
			    					<table class="tb_simple" width="100%">
			   							<tr>
			   								<td>
			   									<xform:text property="fdQRCodeTime" validators="required min(5) max(1000)" style="width:80px;margin-right:8px;"></xform:text>${ lfn:message('sys-attend:sysAttendCategory.fdQRCodeTime.tips') }
			   								</td>
			   							</tr>
			   						</table>
			    				</div>
	   						</div>
	   						</c:if>
	   						
	   						<%-- 开启打卡签到 --%>
	   						<c:if test="${not empty sysAttendCategoryForm.fdAppKey && sysAttendCategoryForm.fdAppKey=='kmImeeting' }">
			    			<div class="lui-singin-creatPage-table">
			    				<div class="caption" style="padding-top: 16px;"></div>
			    				<div class="content">
			    					<table class="tb_simple" width="100%">
			   							<tr>
			   								<td>
			   									<xform:checkbox property="fdPermState" style="margin-right: 5px">
					    							<xform:simpleDataSource value="true">${ lfn:message('sys-attend:mui.PermState.Title') }</xform:simpleDataSource>
					    						</xform:checkbox>
			   								</td>
			   							</tr>
			   						</table>
			    				</div>
	   						</div>
			    			</c:if>
			    			
			    		</div>
			    	</div>
			    	
			    	<div class="lui-singin-creatPage-panel">
			    		<div class="lui-singin-creatPage-panel-heading">
					        <h2 class="lui-singin-creatPage-panel-heading-title"><span id="signNotifyTitle">${ lfn:message('sys-attend:sysAttendCategory.notify.title') }</span></h2>
					    </div>
			    		<div class="lui-singin-creatPage-panel-body" id="signNotifyBody">
			    			<table class="tb_simple" width="100%">
			    				<tr>
			    					<td class="td_normal_title">
			    						${ lfn:message('sys-attend:sysAttendCategory.notify.custom.on') }
			    					</td>
			    					<td>
			    						<xform:text property="fdNotifyOnTime" subject="${ lfn:message('sys-attend:sysAttendCategory.fdNotifyAttendOnTime') }" validators="digits min(1) max(1000) notifyAfterNow" style="width:50px;margin-right:8px;"></xform:text>${ lfn:message('sys-attend:sysAttendCategory.notify.on.tips') }
			    					</td>
			    				</tr>
			    				<tr>
			    					<td class="td_normal_title">
			    						${ lfn:message('sys-attend:sysAttendCategory.notify.result') }
			    					</td>
			    					<td>
			    						<xform:checkbox property="fdNotifyResult" style="margin-right: 5px">
			    							<xform:simpleDataSource value="true">${ lfn:message('sys-attend:sysAttendCategory.notify.custom.result.tips') }</xform:simpleDataSource>
			    						</xform:checkbox>
			    					</td>
			    				</tr>
			    			</table>
			    		</div>
			    	</div>
			    </div>
			</div>
			<ui:tabpage expand="false">
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendCategoryForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.sys.attend.model.SysAttendCategory" />
				</c:import>
			</ui:tabpage>
		</html:form>
	</template:replace>
</template:include>
<script>
	DocList_Info.push("appendDateList","exceptDateList","customDateList","locationsList");
	var cateValidation = $KMSSValidation(document.forms['sysAttendCategoryForm']);
	LUI.ready(function(){
		<c:if test="${sysAttendCategoryForm.method_GET=='add' && empty sysAttendCategoryForm.fdAppName && empty JsParam.fdTemplateId}">
	    	seajs.use(['sys/ui/js/dialog'],	function(dialog) {
				dialog.simpleCategoryForNewFile('com.landray.kmss.sys.attend.model.SysAttendCategoryTemplate',
						'/sys/attend/sys_attend_category/sysAttendCategory.do?method=add&type=custom&fdTemplateId=!{id}',
						false, function(res) {
							if(!res) {
								window.close();
							}
						}, null, null, "_self", true);
			});
		</c:if>
		<c:if test="${sysAttendCategoryForm.method_GET=='edit' && empty sysAttendCategoryForm.fdAppName && empty sysAttendCategoryForm.fdTemplateId}">
	    	seajs.use(['lui/dialog'],function(dialog){
	    		dialog.simpleCategory('com.landray.kmss.sys.attend.model.SysAttendCategoryTemplate','fdTemplateId','fdTemplateName', 
	    				false, function(res){
	    			if(res && res['id'] && res['name']) {
	    				$('[name="fdTemplateId"]:hidden').val(res['id']);
	    				$('#fdTemplateName').html(res['name']);
	    			} else {
	    				window.close();
	    			}
	    		}, null, true, null, true);
	    	});
		</c:if>
		initSignDateType( '${sysAttendCategoryForm.fdPeriodType }');
		bindEvent();
		if('${sysAttendCategoryForm.fdAppId}'){
			$('#weekDate,#customDate').unbind('click');
			$('#weekDate,#customDate').removeAttr('onclick');
			$('#weekDate,#customDate').addClass('disable');
		}
		
		$('#locationsList').on('table-add',function(e,row){
			var idx = row.rowIndex;
			$(row).find('[data-location-container="fdLocations['+ idx +'].fdLocation"]').empty();
			var options ='{"id":"fdLocations['+ idx +'].fdLocation","propertyName":"fdLocations['+ idx +'].fdLocation","propertyCoordinate":"fdLocations['+ idx +'].fdLocationCoordinate","nameValue":"","coordinateValue":"","showStatus":"edit","style":"width:97%;float:left;","subject":"${ lfn:message("sys-attend:sysAttendCategory.fdLocations") }","required":true,"validators":"required"}';
			seajs.use(['sys/attend/map/resource/js/LocationInit.js'],function(init){
				init(JSON.parse(options));
			});
		});
		$('#locationsList').on('table-delete',function(evt, data){
			if(data) {
				var tbInfo = DocList_TableInfo['locationsList'];
				for(var i = 0; i<tbInfo.lastIndex; i++) {
					var optTR = tbInfo.DOMElement.rows[i];
					var doms = optTR.getElementsByTagName('div');
					// 更新data-location-container属性中的序号
					for(var k=0; k<doms.length; k++){
						if($(doms[k]).attr("data-location-container")){
							var fieldValue = $(doms[k]).attr("data-location-container").replace(/\[\d+\]/g, "[!{index}]").replace(/\.\d+\./g, ".!{index}.");
							fieldValue = fieldValue.replace(/!\{index\}/g, i - tbInfo.firstIndex);
							$(doms[k]).attr("data-location-container", fieldValue);
						}
					}
				}
			}
		});
		setTimeout(function(){
			if($('#locationsList div[data-location-container]').length > 0) {
				showAndAbled('fdLimitTR');
			} else {
				hideAndDisabled('fdLimitTR');
			}
		}, 300);
	});
	// 初始化日期类型
	window.initSignDateType = function initSignDateType(v) {
		if(!v) 
			return;
		var periodField = $('input[name="fdPeriodType"]:hidden');
		if(v == '1'){
			periodField.val('1');
			showAndAbled('weekTB');
			hideAndDisabled('customDateTB');
			$('[data-templ-id="customDateTempl"]').removeAttr('disabled');
			$("#weekDate").addClass('active');
			$("#customDate").removeClass('active');
		} else if ( v == '2'){
			periodField.val('2');
			showAndAbled('customDateTB');
			hideAndDisabled('weekTB');
			$('[data-templ-id="appendDateTempl"]').removeAttr('disabled');
			$('[data-templ-id="exceptDateTempl"]').removeAttr('disabled');
			$("#customDate").addClass('active');
			$("#weekDate").removeClass('active');
		}
	};
	
	
	// 签到日期类型
	window.changeSignDateType = function changeSignDateType(v) {
		if(!v) 
			return;
		var periodField = $('input[name="fdPeriodType"]:hidden');
		if(v == '1'){
			periodField.val('1');
			showAndAbled('weekTB');
			hideAndDisabled('customDateTB');
			$("#weekDate").addClass('active');
			$("#customDate").removeClass('active');
		} else if ( v == '2'){
			periodField.val('2');
			showAndAbled('customDateTB');
			hideAndDisabled('weekTB');
			$("#customDate").addClass('active');
			$("#weekDate").removeClass('active');
		}
	};
	
	window.deleteLocation = function() {
		var locationCount = $('#locationsList div[data-location-container]').length;
		if(locationCount == 1) {
			hideAndDisabled('fdLimitTR');
		}
		DocList_DeleteRow_ClearLast();
	};
	
	window.addLocation = function() {
		var locationCount = $('#locationsList div[data-location-container]').length;
		if(locationCount == 0) {
			showAndAbled('fdLimitTR');
		}
		DocList_AddRow('locationsList');
		$('#fdLimitLocations').find('input[name$="fdLocation"]').attr("validate","checkLocationRepeated required");
	};
	var showAndAbled = function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.removeAttr('disabled');
		parentDom.show();
	};
	
	var hideAndDisabled= function(id) {
		var parentDom = $('#' + id);
		if(!parentDom)
			return;
		var childInputs = parentDom.find(':input');
		if(childInputs)
			childInputs.prop('disabled', 'disabled');
		parentDom.hide();
	};
	
	var bindEvent = function(){
		$('#signTargetsTitle').on('click',function(){
			$('#signTargetsBody').slideToggle();
		});
		$('#signTimeTitle').on('click',function(){
			$('#signTimeBody').slideToggle();
		});
		$('#signNotifyTitle').on('click',function(){
			$('#signNotifyBody').slideToggle();
		});
		$('#signTypeTitle').on('click',function(){
			$('#signTypeBody').slideToggle();
		});
	}
	cateValidation.addValidator('beforeEnd', "${ lfn:message('sys-attend:sysAttendCategory.custom.validate.beforeEnd') }", function(v,e,o){
		var firstEnd = $('input[name="fdStartTime"]').val();
		var secondEnd = $('input[name="fdEndTime"]').val();
		if(firstEnd && secondEnd){
			return v<secondEnd;
		}
		return true;
	});
	cateValidation.addValidator('afterStart', "${ lfn:message('sys-attend:sysAttendCategory.custom.validate.afterStart') }", function(v,e,o){
		var firstEnd = $('input[name="fdStartTime"]').val();
		var secondEnd = $('input[name="fdEndTime"]').val();
		if(firstEnd && secondEnd){
			return v>=firstEnd;
		}
		return true;
	});
	
	cateValidation.addValidator('betweenTime', "${ lfn:message('sys-attend:sysAttendCategory.custom.validate.betweenTime') }", function(v,e,o){
		var firstEnd = $('input[name="fdStartTime"]').val();
		var secondEnd = $('input[name="fdEndTime"]').val();
		if(v && firstEnd && secondEnd){
			return v>=firstEnd && v<=secondEnd;
		}
		return true;
	});
	cateValidation.addValidator('lateTimeRequire', "${ lfn:message('sys-attend:sysAttendCategory.custom.validate.lateTimeRequire') }", function(v,e,o){
		var fdInTime = $('input[name="fdRule[0].fdInTime"]').val();
		if(fdInTime){
			return v && v>0;
		}
		return true;
	});
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/data/source','sys/attend/resource/js/dateUtil'], function($, dialog , topic, Source, dateUtil){
		cateValidation.addValidator('afterToday', "${ lfn:message('sys-attend:sysAttendCategory.fdTimes.validate.afterToday') }", function(v,e,o){
			if(v){
				var dateObj = dateUtil.parseDate(v);
				if(dateObj){
					var today = new Date();
					today.setHours(0,0,0,0);
					if(dateObj.getTime() < today.getTime()){
						return false;	
					}
				}
			}
			return true;
		});
		cateValidation.addValidator('afterNow', "${ lfn:message('sys-attend:sysAttendCategory.fdStartTime.validate.afterNow') }", function(v,e,o){
			if(v){
				var fdPeriodType = $('[name="fdPeriodType"]:hidden').val();
				if(fdPeriodType == '2') {//自定义日期
					var dateFields = $('input[name^="fdTimes["][name$="].fdTime"]:enabled');
					if(dateFields.length == 1 && dateFields.val()){//只有一个日期时校验
						var dateObj = dateUtil.parseDate(dateFields.val())
						if(dateObj) {
							var today = new Date();
							today.setHours(0,0,0,0);
							if(dateObj.getTime() == today.getTime()){//日期是今天时才校验
								var mins = parseInt(v.split(':')[0]) * 60 + parseInt(v.split(':')[1]);
								return isAfterNow(mins);
							}
						}
					}
				}
			}
			return true;
		});
		cateValidation.addValidator('notifyAfterNow', "${ lfn:message('sys-attend:sysAttendCategory.fdNotifyOnTime.validate.notifyAfterNow') }", function(v,e,o){
			if(v) {
				var fdPeriodType = $('[name="fdPeriodType"]:hidden').val();
				if(fdPeriodType == '2') {//自定义日期
					var dateFields = $('input[name^="fdTimes["][name$="].fdTime"]:enabled');
					if(dateFields.length == 1 && dateFields.val()){//只有一个日期时校验
						var dateObj = dateUtil.parseDate(dateFields.val())
						if(dateObj) {
							var today = new Date();
							today.setHours(0,0,0,0);
							if(dateObj.getTime() == today.getTime()){//日期是今天时才校验
								var startTime = $('input[name="fdRule[0].fdInTime"]:enabled').val() || $('input[name="fdStartTime"]:enabled').val();
								var mins = parseInt(startTime.split(':')[0]) * 60 + parseInt(startTime.split(':')[1]);
								mins = mins - parseInt(v);
								return isAfterNow(mins);
							}
						}
					}
				}
			}
			return true;
		});
		var isAfterNow = function(mins) {
			var now = new Date();
			var nowMins = now.getHours() * 60 + now.getMinutes();
			if(mins < nowMins){
				return false;
			} else {
				return true;
			}
		};
		window.onSubmitMethod = function (method){
			var fdPeriodType = $('[name="fdPeriodType"]:hidden').val();
			$('input[name="fdStatus"]').val("1");
			if(fdPeriodType == '2') {//自定义日期
				var dateFields = $('input[name^="fdTimes["][name$="].fdTime"]:enabled');
				if(dateFields.length <= 1){//日期大于一个时才判断
					Com_Submit(document.sysAttendCategoryForm, method);
					return;
				}
				for(var i=0; i<dateFields.length; i++) {
					var dateValue = $(dateFields[i]).val();
					if(!dateValue) {
						continue;
					}
					var dateObj = dateUtil.parseDate(dateValue);
					if(!dateObj) {
						continue;
					}
					var today = new Date();
					today.setHours(0,0,0,0);
					if(dateObj.getTime() == today.getTime()){ //日期是今天
						var startTime = $('input[name="fdRule[0].fdInTime"]:enabled').val() || $('input[name="fdStartTime"]:enabled').val();
						var endTime = $('input[name="fdEndTime"]:enabled').val();
						var fdNotifyOnTime = $('input[name="fdNotifyOnTime"]').val();
						var error = '';
						var result = true;
						if(endTime) {
							var endMins = parseInt(endTime.split(':')[0]) * 60 + parseInt(endTime.split(':')[1]);
							if(!isAfterNow(endMins)){
								error += "${ lfn:message('sys-attend:sysAttendCategory.fdStartTime.confirm.tips') }" + '<br/>';
								result = false;
							}
						}
						if(startTime && fdNotifyOnTime) {
							var startMins = parseInt(startTime.split(':')[0]) * 60 + parseInt(startTime.split(':')[1]);
							startMins = startMins - parseInt(fdNotifyOnTime);
							if(!isAfterNow(startMins)){
								error += "${ lfn:message('sys-attend:sysAttendCategory.fdNotifyOnTime.confirm.tips') }" + '<br/>' + "${ lfn:message('sys-attend:sysAttendCategory.confirm.tips') }";
								result = false;
							}
						}
						if(!result){
							dialog.confirm(error, function(value){
								if(value) {
									Com_Submit(document.sysAttendCategoryForm, method);
								}
							})
							return;
						}
						break;
					}
				}
			}
			Com_Submit(document.sysAttendCategoryForm, method);
		}
		//地点签到重复校验
		window.isLocationRepeated=function(location){
			var fdLocations = $('#fdLimitLocations').find('input[name$="fdLocation"]');
			if(!fdLocations||fdLocations.length==1){
				return true;
			}
			var arr=[];
			for(var i =0;i<fdLocations.length;i++){
				if(location==$(fdLocations[i]).val()){
					arr.push(location);
				}
			}
			if(arr.length>1){
				return false;
			}
			return true;
		};
		cateValidation.addValidator('checkLocationRepeated',"${ lfn:message('sys-attend:sysAttendCategory.validate.location.duplicate') }", function(v, e, o) {
			if (!v) {
				return true;
			}
			return isLocationRepeated(v);
		});
	});
</script>