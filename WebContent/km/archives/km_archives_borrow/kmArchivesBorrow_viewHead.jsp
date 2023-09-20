<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

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
</template:replace>
<template:replace name="title">
	<c:out value="${kmArchivesBorrowForm.docSubject} - " />
	<c:out value="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
</template:replace>
<template:replace name="toolbar">
	<script>
		function deleteDoc(delUrl) {
			seajs.use([ 'lui/dialog' ], function(dialog) {
				Com_Delete_Get(delUrl,'com.landray.kmss.km.archives.model.KmArchivesBorrow');
			});
		}

		function archivesRenew(borrowId) {
			var url = "/km/archives/km_archives_renew/kmArchivesRenew.do?method=add&fdBorrowId="
					+ borrowId;
			seajs.use([ 'lui/dialog' ],function(dialog) {
				dialog.iframe(url,'${ lfn:message("km-archives:table.kmArchivesRenew") }',function(value) {
								
				}, {
					"width" : 1000,
					"height" : 500
					});
			});
		}
		
		function borrowAgain(borrowId) {
			var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add&fdBorrowId="+ borrowId;
			window.open(url,"_blank");
		}
		
	</script>
	<c:if test="${kmArchivesBorrowForm.docDeleteFlag ==1}">
		<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
	</c:if>
	<c:if test="${kmArchivesBorrowForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5" var-navwidth="90%" style="display:none;">
			<c:if test="${ kmArchivesBorrowForm.docStatus=='10' || kmArchivesBorrowForm.docStatus=='11' || kmArchivesBorrowForm.docStatus=='20' }">
				<!--edit-->
				<kmss:auth requestURL="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=edit&fdId=${param.fdId}">
					<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('kmArchivesBorrow.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
				</kmss:auth>
			</c:if>
			<!--delete-->
			<kmss:auth requestURL="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=delete&fdId=${param.fdId}">
				<ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('kmArchivesBorrow.do?method=delete&fdId=${param.fdId}');" order="4" />
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
			<!-- 续借 -->
			<c:if test="${kmArchivesBorrowForm.docStatus=='30' && kmArchivesBorrowForm.fdBorrowerId==currentUserId }">
				<c:choose>
					<c:when test="${canRenew eq 'true' }">
						<ui:button text="${lfn:message('km-archives:button.renew')}" order="6" onclick="archivesRenew('${param.fdId}');" />
					</c:when>
					<c:otherwise>
						<c:if test="${canReBorrow eq 'true' }">
							<ui:button text="${lfn:message('km-archives:kmArchives.borrowAgain')}" order="6" onclick="borrowAgain('${param.fdId}');" />
						</c:if>
					</c:otherwise>
				</c:choose>
				
			</c:if>
		</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav">
		<ui:menu-item text="${ lfn:message('home.home') }"
			icon="lui_icon_s_home" href="/" target="_self" />
		<ui:menu-item
			text="${ lfn:message('km-archives:table.kmArchivesBorrow') }"
			href="/km/archives/" target="_self" />
	</ui:menu>
</template:replace>