<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true">
   <template:replace name="loading">
		<c:import url="/sys/attend/mobile/view_banner.jsp" charEncoding="UTF-8">
			<c:param name="formBeanName" value="sysAttendMainExcForm"></c:param>
			<c:param name="loading" value="true"></c:param>
		</c:import>
	</template:replace> 
	<template:replace name="title">
		${ lfn:message('sys-attend:sysAttendMain.fdExcStatus') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>
		<script type="text/javascript">
	   	require(["dojo/store/Memory","dojo/topic"],function(Memory, topic){
	   		window._narStore = new Memory({data:[{'text':'<bean:message bundle="sys-mobile" key="mui.mobile.info" />',
	   			'moveTo':'_contentView','selected':true},{'text':'<bean:message bundle="sys-mobile"  key="mui.mobile.review.record" />',
	   			'moveTo':'_noteView'}]});
	   		topic.subscribe("/mui/navitem/_selected",function(evtObj){
	   			setTimeout(function(){topic.publish("/mui/list/resize");},150);
	   		});
	   	});
	   </script>
	</template:replace>
	<template:replace name="content"> 
		<div data-dojo-type="mui/view/DocScrollableView" id="scrollView" class="muiSignExc gray">
           	<c:import url="/sys/attend/mobile/view_banner.jsp" charEncoding="UTF-8">
				<c:param name="formBeanName" value="sysAttendMainExcForm"></c:param>
			</c:import> 

			<div data-dojo-type="mui/fixed/Fixed" id="fixed">
				<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
					<div data-dojo-type="mui/nav/NavBarStore"
						data-dojo-props="store:_narStore"></div>
				</div>
			</div>
			<div data-dojo-type="dojox/mobile/View" id="_contentView">
			<div class="muiFormContent" style="background:#fff;padding: 0rem 0 0 1rem;">
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td class="muiTitle">
								${ lfn:message('sys-attend:mui.people.name') }
							</td><td>
								<c:out value="${sysAttendMainExcForm.fdAttendMainDocCreatorName}" />
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime1"/>
							</td><td>
								<c:if test="${empty sysAttendMainExcForm.fdAttendTime} ">
									<%-- 兼容 --%>
									<c:out value="${sysAttendMainExcForm.fdAttendMainCreateTime}" />
								</c:if>
								${sysAttendMainExcForm.fdAttendTime}
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus1"/>
							</td><td>
								<c:if test="${sysAttendMainExcForm.fdAttendMainState=='2'}">
									<span style="color:blue;">
										${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }
									</span>
								</c:if>
								<c:if test="${sysAttendMainExcForm.fdAttendMainState!='2'}">
									<span style="color:red;">
										<c:choose>
											<c:when test="${sysAttendMainExcForm.fdAttendMainStatus=='1' && sysAttendMainExcForm.fdAttendOutside=='true'}">
												${ lfn:message('sys-attend:sysAttendMain.fdOutside') }
											</c:when>
											<c:otherwise>
												<sunbor:enumsShow value="${sysAttendMainExcForm.fdAttendMainStatus}" enumsType="sysAttendMain_fdStatus" />
											</c:otherwise>
										</c:choose>	
									</span>
								</c:if>
								
							</td>
						</tr>
						<c:if test="${not empty sysAttendMainExcForm.fdAttendMainLocation }">
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation1"/>
							</td>
							<td>
								<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
								<c:set var="fdLocationCoordinate" value="${sysAttendMainExcForm.fdAttendMainLat}${','}${sysAttendMainExcForm.fdAttendMainLng}"/>
								<map:location propertyName="fdLocation" nameValue="${sysAttendMainExcForm.fdAttendMainLocation }"
									propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }" 
									showStatus="view" mobile="true"></map:location>
							</td>
						</tr>
						</c:if>
						<tr>
							<td class="muiTitle">
								<bean:message bundle="sys-attend" key="sysAttendMainExc.fdHandler"/>
							</td>
							<td colspan="2">
								<kmss:showWfPropertyValues idValue="${sysAttendMainExcForm.fdId}" propertyName="handlerName" mobile="true"/>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								${ lfn:message('sys-attend:mui.reason') }
							</td>
							<td colspan="2">
								<xform:textarea showStatus="view" property="fdDesc" mobile="true" />
							</td>
						</tr>
						<tr>
							<td colspan="2" id="td_attachments">
								<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="sysAttendMainExcForm"></c:param>
									<c:param name="fdKey" value="attachment" />
								</c:import>
							</td>
						</tr>
						
					</table>
				</div>
				</div>
				<c:if test="${not empty sysAttendMainExcForm.docStatus}">
				<div data-dojo-type="dojox/mobile/View" id="_noteView">
					<div class="muiFormContent muiFlowInfoW">
						<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
							<c:param name="fdModelId" value="${sysAttendMainExcForm.fdId }"/>
							<c:param name="fdModelName" value="com.landray.kmss.sys.attend.model.SysAttendMainExc"/>
							<c:param name="formBeanName" value="sysAttendMainExcForm"/>
						</c:import>
					</div>
				</div>
				</c:if>
				<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
					docStatus="${sysAttendMainExcForm.docStatus}"
					editUrl="/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=edit&fdId=${param.fdId }" 
					formName="sysAttendMainExcForm"
					viewName="lbpmView"
					allowReview="true">
				</template:include>
		</div>
		<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysAttendMainExcForm" />
			<c:param name="fdKey" value="attendMainExc" />
			<c:param name="viewName" value="lbpmView" />
			<c:param name="backTo" value="scrollView" />
		</c:import>
	</template:replace>
</template:include>