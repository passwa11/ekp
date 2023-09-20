<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="${lfn:message('km-review:kmReviewDocumentLableName.baseInfo') }" titleicon="lui-fm-icon-2">
	<!-- 草稿状态的文档默认选中基本信息页签 -->
	<c:if test="${sysAttendMainExcForm.docStatus=='10'}">
		<script>
			LUI.ready(function(){
				setTimeout(function(){
					$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
				},200);
			});
		</script>
	</c:if>
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!--申请人-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-attend" key="sysAttendMain.docCreatorName" />
			</td>
			<td>
				<xform:text property="docCreatorId" showStatus="noShow"/> 
				<c:out value="${ sysAttendMainForm.docCreatorName}"></c:out>
			</td>
		</tr>
		<!--所属部门-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-attend" key="sysAttendMain.dept" />
			</td>
			<td>
				<c:out value="${ sysAttendMainForm.docCreatorDept}"></c:out>
			</td>
		</tr>
		<!--申请时间-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-attend" key="sysAttendMain.Creatordate" />
			</td>
			<td>
				<c:out value="${ sysAttendMainExcForm.docCreateTime}"></c:out>
			</td>
		</tr>
		<!--打卡时间-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime" />
			</td>
			<td>
				<c:out value="${ sysAttendMainForm.docCreateTime}"></c:out>
			</td>
		</tr>
		
		<!-- 打卡状态 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus1" />
			</td>
			<td>
				<c:choose>
					<c:when
						test="${sysAttendMainExcForm.fdAttendMainStatus=='1' && sysAttendMainExcForm.fdAttendOutside=='true'}">
									${ lfn:message('sys-attend:sysAttendMain.fdOutside') }
							</c:when>
					<c:otherwise>
						<sunbor:enumsShow
							value="${sysAttendMainExcForm.fdAttendMainStatus}"
							enumsType="sysAttendMain_fdStatus" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<!--打卡地点-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation1" />
			</td>
			<td>
				<c:out value="${ sysAttendMainExcForm.fdAttendMainLocation}"></c:out>
			</td>
		</tr>
	</table>
</ui:content>