<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.util.AttendUtil,com.landray.kmss.sys.attend.model.SysAttendMain"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="sysAttendMain" list="${queryPage.list}" varIndex="status">
		<%
			SysAttendMain main = (SysAttendMain) pageContext.getAttribute("sysAttendMain");
			if(main.getFdOffType() != null && Integer.valueOf(5).equals(main.getFdStatus())) {
				pageContext.setAttribute("fdOffTypeText", AttendUtil.getLeaveTypeText(main.getFdOffType()));
			}
		%>
		<c:if test="${sysAttendMain.fdHisCategory.fdType eq 1}">
			<c:set var='fdNameTitle' value="${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }" />
			<c:set var='_fdStatus' value="${ lfn:message('sys-attend:sysAttendMain.fdStatus1') }" />
			<c:set var='_docCreateTime' value="${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }" />
			<c:set var='_fdLocation' value="${ lfn:message('sys-attend:sysAttendMain.fdLocation1') }" />
			<c:set var='_docCreator' value="${lfn:message('sys-attend:sysAttendMain.docCreator')  }" />
			<list:data-column col="fdCategory.fdName" title="${fdNameTitle}">
				<c:out value="${sysAttendMain.fdHisCategory.fdName}" />
			</list:data-column>
		</c:if>
		<c:if test="${sysAttendMain.fdCategory.fdType eq 2}">
			<c:set var='fdNameTitle' value="${ lfn:message('sys-attend:sysAttendCategory.custom.fdName') }" />
			<c:set var='_fdStatus' value="${ lfn:message('sys-attend:sysAttendMain.fdStatus') }" />
			<c:set var='_docCreateTime' value="${ lfn:message('sys-attend:sysAttendMain.docCreateTime1') }" />
			<c:set var='_fdLocation' value="${ lfn:message('sys-attend:sysAttendMain.fdLocation') }" />
			<c:set var='_docCreator' value="${lfn:message('sys-attend:sysAttendMain.docCreator1')  }" />
			<list:data-column col="fdCategory.fdName" title="${fdNameTitle}">
				<c:out value="${sysAttendMain.fdCategory.fdName}" />
			</list:data-column>
		</c:if>
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column col="_fdStatus" title="${ _fdStatus}">
			<c:out value="${sysAttendMain.fdStatus}"></c:out>
		</list:data-column>
		<list:data-column col="fdStatus" title="${ _fdStatus }">
			<c:if test="${sysAttendMain.fdHisCategory.fdType eq 1}">
				<c:choose>
					<c:when test="${(sysAttendMain.fdStatus=='0'|| sysAttendMain.fdStatus=='2' || sysAttendMain.fdStatus=='3') && sysAttendMain.fdState==2 }">
						 ${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }
					</c:when>
					<c:otherwise>
						<sunbor:enumsShow value="${sysAttendMain.fdStatus}" enumsType="sysAttendMain_fdStatus" />
					</c:otherwise>
				</c:choose>
				<c:if test="${sysAttendMain.fdOutside}">
					(${ lfn:message('sys-attend:sysAttendMain.outside')})
				</c:if>
				<c:if test="${sysAttendMain.fdStatus=='5' && not empty sysAttendMain.fdOffType && not empty fdOffTypeText}">
					（${fdOffTypeText}）
				</c:if>
			</c:if>
			<c:if test="${sysAttendMain.fdCategory.fdType eq 2}">
				<sunbor:enumsShow value="${sysAttendMain.fdStatus}" enumsType="sysAttendMain_fdStatus" />
			</c:if>
		</list:data-column>
		<list:data-column property="docCreateTime" headerClass="width120" title="${_docCreateTime }">
		</list:data-column>
		<list:data-column property="docAlterTime" title="${ lfn:message('sys-attend:sysAttendMain.docAlterTime') }">
		</list:data-column>
		<list:data-column property="fdDesc" title="${ lfn:message('sys-attend:sysAttendMain.fdDesc') }">
		</list:data-column>
		<list:data-column property="fdLng" title="${ lfn:message('sys-attend:sysAttendMain.fdLng') }">
		</list:data-column>
		<list:data-column property="fdLat" title="${ lfn:message('sys-attend:sysAttendMain.fdLat') }">
		</list:data-column>
		<list:data-column col="fdLocation" title="${_fdLocation}">
			${not empty sysAttendMain.fdWifiName ? sysAttendMain.fdWifiName : sysAttendMain.fdLocation}
		</list:data-column>
		<list:data-column col="fdCategory.fdId" title="${ lfn:message('sys-attend:sysAttendMain.fdCategory') }">
			<c:out value="${sysAttendMain.fdCategory.fdId}" />
		</list:data-column>
		

		<list:data-column col="fdCategory.fdStatus" title="">
			<c:out value="${sysAttendMain.fdCategory.fdStatus}" />
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${_docCreator }">
			<c:if test="${sysAttendMain.fdOutPerson==null }">
				<c:out value="${sysAttendMain.docCreator.fdName}" />
			</c:if>
			<c:if test="${sysAttendMain.fdOutPerson!=null }">
				${sysAttendMain.fdOutPerson.fdName}(${sysAttendMain.fdOutPerson.fdPhoneNum})
			</c:if>
		</list:data-column>
		<list:data-column col="fdState" title="${ lfn:message('sys-attend:sysAttendMain.fdState') }">
			<c:choose>
				<c:when test="${(sysAttendMain.fdStatus=='0'|| sysAttendMain.fdStatus=='2' || sysAttendMain.fdStatus=='3') && sysAttendMain.fdState==null }">
					${ lfn:message('sys-attend:sysAttendMain.fdState.undo') }
				</c:when>
				<c:otherwise>
					<c:if test="${sysAttendMain.fdStatus !='4' && sysAttendMain.fdStatus !='5' && sysAttendMain.fdStatus !='6'}">
						<c:if test="${excMap[sysAttendMain.fdId] != null }">
							<sunbor:enumsShow value="${excMap[sysAttendMain.fdId].docStatus}" enumsType="sysAttendMainExc_docStatus" />
						</c:if>
						<c:if test="${excMap[sysAttendMain.fdId] == null }">
							<sunbor:enumsShow value="${sysAttendMain.fdState}" enumsType="sysAttendMain_fdState" />
						</c:if>
					</c:if>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column property="fdOutTarget" title="" escape="false">
		</list:data-column>
		<list:data-column col="docCreatorImg" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }" escape="false">
			<person:headimageUrl contextPath="true" personId="${sysAttendMain.docCreator.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="fdSignType" title="${ lfn:message('sys-attend:sysAttendMain.export.fdSignType') }" escape="false">
			<c:choose>
			    <c:when test="${sysAttendMain.fdAppName=='dingding'}">
                    <sunbor:enumsShow value="${sysAttendMain.fdSourceType}" enumsType="sysAttendMain_fdSourceType" />
                </c:when>
				<c:when test="${not empty sysAttendMain.fdWifiName}">
					${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.wifi') }
				</c:when>
				<c:when test="${sysAttendMain.fdAppName !='dingding' && not empty sysAttendMain.fdLocation}">
					${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.map') }
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdOutPersonId" title="" escape="false">
			${sysAttendMain.fdOutPerson.fdId}
		</list:data-column>
		<list:data-column col="fdOutPersonName" title="" escape="false">
			${sysAttendMain.fdOutPerson.fdName}
		</list:data-column>
		<list:data-column col="fdOutPersonPhoneNum" title="" escape="false">
			${sysAttendMain.fdOutPerson.fdPhoneNum}
		</list:data-column>
		<list:data-column col="fdPersonType" title="" escape="false">
			<c:choose>
				<c:when test="${sysAttendMain.fdOutPerson!=null }">
					${ lfn:message('sys-attend:sysAttendMain.fdPersonType.outer') }
				</c:when>
				<c:when test="${sysAttendMain.fdOutTarget }">
					${ lfn:message('sys-attend:sysAttendMain.fdPersonType.inner.out') }
				</c:when>
				<c:otherwise>
					${ lfn:message('sys-attend:sysAttendMain.fdPersonType.inner') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="patchOper" escape="false" title="${ lfn:message('list.operation') }" style="min-width: 50px;">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:addPatch('${sysAttendMain.fdId}','${sysAttendMain.fdCategory.fdId}');">${lfn:message('sys-attend:sysAttendSignPatch.addPatch')}</a>
				</div>
			</div>
		</list:data-column>
		<list:data-column col="fdIsPatch" title="${ lfn:message('sys-attend:sysAttendSignPatch.fdIsPatch') }" escape="false">
			<c:if test="${sysAttendMain.fdSignPatch!=null}">
				${ lfn:message('sys-attend:sysAttendSignPatch.addPatch') }
			</c:if>
		</list:data-column>
		<list:data-column col="fdPatchPerson" title="${ lfn:message('sys-attend:sysAttendSignPatch.fdPatchPerson') }" escape="false">
			${sysAttendMain.fdSignPatch.fdPatchPerson.fdName}
		</list:data-column>
		<list:data-column property="fdSignPatch.fdPatchTime" title="${ lfn:message('sys-attend:sysAttendSignPatch.fdPatchTime') }" escape="false">
		</list:data-column>
		<list:data-column col="fdAppName" title="${ lfn:message('sys-attend:sysAttendMain.export.fdAppName') }" escape="false">
            <c:choose>
				<c:when test="${sysAttendMain.fdAppName=='dingding' }">
					${ lfn:message('sys-attend:sysAttendMain.fdAppName.dingDing') }
				</c:when>
				<c:when test="${sysAttendMain.fdAppName=='qywx' }">
					${ lfn:message('sys-attend:sysAttendMain.fdAppName.qywx') }
				</c:when>
				<c:when test="${empty sysAttendMain.fdAppName }">
					${ lfn:message('sys-attend:sysAttendMain.fdAppName.ekp') }
				</c:when>
				<c:otherwise>
					${sysAttendMain.fdAppName}
				</c:otherwise>
			</c:choose>
        </list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>