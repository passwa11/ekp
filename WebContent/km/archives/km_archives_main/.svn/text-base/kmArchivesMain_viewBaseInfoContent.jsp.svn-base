<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 草稿状态的文档默认选中基本信息页签 -->
<c:if test="${kmArchivesMainForm.docStatus=='10'}">
	<ui:event event="layoutDone">
		$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
    </ui:event>
</c:if>
<ui:content title="${lfn:message('km-archives:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!-- 档案名称 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.docSubject" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.docSubject }"></c:out>
			</td>
		</tr>
		<!-- 所属分类 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesTemplate.fdName" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.docTemplateName }"></c:out>
			</td>
		</tr>
		<!-- 档案编号 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.docNumber" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.docNumber }"></c:out>
			</td>
		</tr>
		<!-- 所属卷库 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.fdLibrary" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.fdLibrary }"></c:out>
			</td>
		</tr>
		<!-- 卷库年度 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.fdVolumeYear" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.fdVolumeYear }"></c:out>
			</td>
		</tr>
		<!-- 保管期限 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="table.kmArchivesPeriod" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.fdPeriod }"></c:out>
			</td>
		</tr>
		<!-- 保管单位 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.fdUnit" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.fdUnit }"></c:out>
			</td>
		</tr>
		<!-- 保管员 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.fdStorekeeper" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.fdStorekeeperName }"></c:out>
			</td>
		</tr>
		<!-- 档案有效期 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.fdValidityDate }"></c:out>
			</td>
		</tr>
		<!-- 秘密程度 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.fdDenseLevel" />
			</td>
			<td>
				<c:choose>
					<c:when test="${not empty kmArchivesMainForm.fdDenseLevel}">
						<c:out value="${ kmArchivesMainForm.fdDenseLevel }"></c:out>
					</c:when>
					<c:otherwise>
						<c:out value="${ kmArchivesMainForm.fdDenseName }"></c:out>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.docCreator" />
			</td>
			<td>
				<xform:text property="docCreatorId" showStatus="noShow"/> 
				<c:out value="${ kmArchivesMainForm.docCreatorName }"></c:out>
			</td>
		</tr>
		<!-- 归档日期 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.fdFileDate" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.fdFileDate }"></c:out>
			</td>
		</tr>
		<!-- 创建时间 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesMain.docCreateTime" />
			</td>
			<td>
				<c:out value="${ kmArchivesMainForm.docCreateTime }"></c:out>
			</td>
		</tr>
		<c:if test="${!empty kmArchivesMainForm.fdPrintState && (kmArchivesMainForm.fdPrintState eq '0' || kmArchivesMainForm.fdPrintState eq '2')}">
			<tr>
				<td colspan="4" style="text-align: center;color: red;font-weight: bold;">
					<c:if test="${kmArchivesMainForm.fdPrintState eq '0'}">
						${lfn:message('km-archives:kmArchivesMain.fdPrintState.progress')}
					</c:if>
					<c:if test="${kmArchivesMainForm.fdPrintState eq '2'}">
						${lfn:message('km-archives:kmArchivesMain.fdPrintState.failed')}
					</c:if>
				</td>
			</tr>
		</c:if>
	</table>
</ui:content>