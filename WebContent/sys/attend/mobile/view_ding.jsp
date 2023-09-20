<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService,com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@ page
	import="com.landray.kmss.sys.attend.forms.SysAttendSynDingForm,com.landray.kmss.sys.attend.model.SysAttendCategory"%>
<%@ page
	import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head"></template:replace>
	<template:replace name="content">
		<%
			ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
							.getBean("sysOrgCoreService");
					ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
							.getBean("sysAttendCategoryService");
					SysAttendSynDingForm main = (SysAttendSynDingForm) request
							.getAttribute("sysAttendSynDingForm");
					if (main != null) {
						SysOrgElement fdPerson = sysOrgCoreService
								.findByPrimaryKey(main.getFdPersonId());
						pageContext.setAttribute("fdPerson", fdPerson);
						String cateId = sysAttendCategoryService
								.getAttendCategory(fdPerson);
						SysAttendCategory category = (SysAttendCategory) sysAttendCategoryService
								.findByPrimaryKey(cateId);
						pageContext.setAttribute("categoryName",
								category == null ? "" : category.getFdName());
					}
		%>
		<div id="scrollView" data-dojo-type="mui/view/DocScrollableView"
			data-dojo-mixins="mui/form/_ValidateMixin">
			<div data-dojo-type="mui/panel/AccordionPanel">
				<div data-dojo-type="mui/panel/Content"
					data-dojo-props="title:'${ lfn:message('sys-attend:table.sysAttendSynDing') }',icon:'mui-ul'">
					<div class="muiFormContent">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendCategory.attend.fdName') }</td>
								<td>
									<div id="_xform_categoryName" _xform_type="text">
										<c:out value="${categoryName }" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.docCreator') }</td>
								<td>
									<div id="_xform_FdName" _xform_type="text">
										<c:out value="${fdPerson.getFdName() }"></c:out>
									</div>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.export.dept') }</td>
								<td>
									<div id="_xform_FdParent" _xform_type="text">
										<c:out value="${fdPerson.getFdParent().getFdName() }"></c:out>
									</div>
								</td>
							</tr>
							<tr>
								<c:set var="hasLocation"
									value="${not empty sysAttendSynDingForm.fdSourceType}"></c:set>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.docCreateTime') }</td>
								<td >
									<div id="_xform_fdUserCheckTime" _xform_type="text">
										<kmss:showDate
											value="${sysAttendSynDingForm.fdUserCheckTime }"
											type="datetime"></kmss:showDate>
									</div>
								</td>
							</tr>
							<c:if test="${hasLocation }">
								<tr>
									<td class="muiTitle">
										${lfn:message('sys-attend:sysAttendCategoryRule.fdMode') }</td>
									<td>
										<div id="_xform_fdSourceType" _xform_type="text">
											<sunbor:enumsShow
												value="${sysAttendSynDingForm.fdSourceType}"
												enumsType="sysAttendMain_fdSourceType" />
										</div>
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.fdLocation') }</td>
								<td>${sysAttendSynDingForm.fdUserAddress}</td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.fdStatus') }</td>
								<td>
									<div id="_xform_fdTimeResult" _xform_type="text">
										<c:choose>
											<c:when
												test="${not empty sysAttendSynDingForm.fdInvalidRecordType}">${lfn:message('sys-attend:sysAttendMain.fdStatus.invalid') }</c:when>
											<c:when
												test="${sysAttendSynDingForm.fdTimeResult =='Normal' && sysAttendSynDingForm.fdLocationResult=='Outside'}">${lfn:message('sys-attend:sysAttendMain.outside') }</c:when>
											<c:otherwise>
												<c:choose>
													<c:when
														test="${sysAttendSynDingForm.fdTimeResult=='Late' || sysAttendSynDingForm.fdTimeResult=='SeriousLate'  || sysAttendSynDingForm.fdTimeResult=='Absenteeism' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.late') }</c:when>
													<c:when
														test="${sysAttendSynDingForm.fdTimeResult =='Early' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.left') }</c:when>
													<c:when
														test="${sysAttendSynDingForm.fdTimeResult =='Normal' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }</c:when>
													<c:when
														test="${sysAttendSynDingForm.fdTimeResult =='NotSigned' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }</c:when>
													<c:when
														test="${sysAttendSynDingForm.fdTimeResult =='Trip' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.business') }</c:when>
													<c:when
														test="${sysAttendSynDingForm.fdTimeResult =='Leave' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }</c:when>
													<c:when
														test="${sysAttendSynDingForm.fdTimeResult =='Outgoing' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</div>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.fdDesc') }</td>
								<td><c:choose>
										<c:when
											test="${not empty sysAttendSynDingForm.fdInvalidRecordType}">${lfn:message('sys-attend:sysAttendSynDing.remarkDesc') }</c:when>
										<c:otherwise>
												${sysAttendSynDingForm.fdOutsideRemark}
											</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.export.fdDeviceInfo') }
								</td>
								<td><sunbor:enumsShow
										value="${sysAttendSynDingForm.fdSourceType}"
										enumsType="sysAttendMain_fdSourceType" /></td>
							</tr>
							<tr>
								<td class="muiTitle">
									${lfn:message('sys-attend:sysAttendMain.fdAppName') }</td>
								<td><c:choose>
										<c:when test="${sysAttendSynDingForm.fdAppName=='dingding' }">
												${ lfn:message('sys-attend:sysAttendMain.fdAppName.dingDing') }
											</c:when>
										<c:when test="${empty sysAttendSynDingForm.fdAppName }">
												${ lfn:message('sys-attend:sysAttendMain.fdAppName.ekp') }
											</c:when>
										<c:otherwise>
												${sysAttendSynDingForm.fdAppName}
											</c:otherwise>
									</c:choose></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</template:replace>
</template:include>