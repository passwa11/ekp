<%@page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
	KmArchivesConfig kmArchivesConfig = new KmArchivesConfig();
	request.setAttribute("fdMaxRenewDate", kmArchivesConfig.getFdMaxRenewDate());
%>

<template:replace name="head">
	<style type="text/css">
		.lui_paragraph_title {
			font-size: 15px;
			color: #15a4fa;
			padding: 15px 0px 5px 0px;
		}
		
		.lui_paragraph_title span {
			display: inline-block;
			margin: -2px 5px 0px 0px;
		}
	</style>
	<script type="text/javascript">
		var editOption = {
			formName : 'kmArchivesBorrowForm',
			modelName : 'com.landray.kmss.km.archives.model.KmArchivesBorrow',
			templateName : 'com.landray.kmss.km.archives.model.KmArchivesTemplate',
			subjectField : 'docSubject',
			mode : ''

		};
		Com_IncludeFile("security.js");
		Com_IncludeFile("domain.js");
		Com_IncludeFile("main_edit.js","${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
		function selectDept() {
			var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do?method=getApplicantDept";
			var fdBorrowerId = document.getElementsByName("fdBorrowerId")[0];
			$(".detailBorrows").each(function() {
				$(this).val(fdBorrowerId.value);
			});
			$.ajax({
				type : "post",
				url : url,
				data : {
					fdBorrowerId : fdBorrowerId.value
				},
				async : false, //用同步方式 
				success : function(data) {
					var results = eval("(" + data + ")");
					if (results['deptId'] != ""
							&& results['deptName'] != "") {
						//document.getElementsByName("docDeptId")[0].value = results['deptId'];
						//document.getElementsByName("docDeptName")[0].value = results['deptName'];
						var kmssData = new KMSSData();
						kmssData.AddHashMap({
							deptId : results['deptId'],
							deptName : results['deptName']
						});
						kmssData.PutToField("deptId:deptName",
								"docDeptId:docDeptName", "", false);
					} else {
						//document.getElementsByName("docDeptId")[0].value = "";
						//document.getElementsByName("docDeptName")[0].value = "";
						var address = Address_GetAddressObj("docDeptName");
						address.emptyAddress(true);
					}
				}
			});
		}
	</script>
</template:replace>
<template:replace name="title">
	<c:choose>
		<c:when test="${kmArchivesBorrowForm.method_GET == 'add' }">
			<c:out
				value="${ lfn:message('operation.create') } - ${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
		</c:when>
		<c:otherwise>
			<c:out value="${kmArchivesBorrowForm.docSubject} - " />
			<c:out
				value="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
		</c:otherwise>
	</c:choose>
</template:replace>
<template:replace name="toolbar">
	<!-- 软删除配置 -->
	<c:if test="${kmArchivesBorrowForm.docDeleteFlag ==1}">
		<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
	</c:if>
	<c:if test="${kmArchivesBorrowForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:choose>
				<c:when test="${ kmArchivesBorrowForm.method_GET == 'edit' }">
					<c:if test="${ kmArchivesBorrowForm.docStatus=='10' || kmArchivesBorrowForm.docStatus=='11' }">
						<ui:button text="${ lfn:message('button.savedraft') }"
							onclick="submitForm('10','update');" />
					</c:if>
					
					<c:if test="${ kmArchivesBorrowForm.docStatus=='10' || kmArchivesBorrowForm.docStatus=='11' || kmArchivesBorrowForm.docStatus=='20' }">
						<c:choose>
							<c:when test="${ param.approveModel eq 'right' }">
								<ui:button text="${ lfn:message('button.submit') }" styleClass="lui_widget_btn_primary" isForcedAddClass="true"
									onclick="submitForm('20','update');" />
							</c:when>
							<c:otherwise>
								<ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:when>
				<c:when test="${ kmArchivesBorrowForm.method_GET == 'add' }">
					<ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save');" />
					<c:choose>
						<c:when test="${ param.approveModel eq 'right' }">
							<ui:button text="${ lfn:message('button.submit') }" styleClass="lui_widget_btn_primary" isForcedAddClass="true"
									order="2" onclick="submitForm('20','save');" />
						</c:when>
						<c:otherwise>
							<ui:button text="${ lfn:message('button.submit') }" order="2" onclick="submitForm('20','save');" />
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>

			<ui:button text="${ lfn:message('button.close') }" order="5"
				onclick="Com_CloseWindow();" />
		</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav">
		<ui:menu-item text="${ lfn:message('home.home') }"
			icon="lui_icon_s_home" />
		<ui:menu-item
			text="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
	</ui:menu>
</template:replace>