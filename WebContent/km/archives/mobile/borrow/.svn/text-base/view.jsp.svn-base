<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="loading">
		<c:import url="/km/archives/mobile/borrow/view_banner.jsp" charEncoding="UTF-8">
			<c:param name="formBeanName" value="kmArchivesBorrowForm"></c:param>
			<c:param name="loading" value="true"></c:param>
		</c:import>
	</template:replace>
	<template:replace name="title">
		<c:out value="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-calendar.js"/>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/archives/mobile/resource/css/archives_borrow_view.css?s_cache=${MUI_Cache}" />
	   	<mui:min-file name="mui-resource-view.css"/>
	   <script type="text/javascript">
		   	require(["dojo/store/Memory","dojo/topic"],function(Memory, topic){
		   		window._narStore = new Memory({data:[{'text':'<bean:message bundle="sys-mobile" key="mui.mobile.info" />',
		   			'moveTo':'_contentView','selected':true},{'text':'<bean:message bundle="sys-mobile" key="mui.mobile.review.record" />',
		   			'moveTo':'_noteView'}]});
		   		topic.subscribe("/mui/navitem/_selected",function(evtObj){
		   			setTimeout(function(){topic.publish("/mui/list/resize");},150);
		   		});
		   	});
	   </script>
	</template:replace>
	<template:replace name="content">
		<form name="kmArchivesBorrowForm" method="post" action="${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do">	
			<div id="scrollView" data-dojo-type="mui/view/DocScrollableView"   data-dojo-mixins="mui/form/_ValidateMixin" class="muiFlowBack">
				<c:import url="/km/archives/mobile/borrow/view_banner.jsp" charEncoding="UTF-8">
					<c:param name="formBeanName" value="kmArchivesBorrowForm"></c:param>
				</c:import>
				<div class="muiFlowInfoW">
					<div data-dojo-type="mui/fixed/Fixed" id="fixed">
						<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
							<div data-dojo-type="mui/nav/NavBarStore" data-dojo-props="store:_narStore">
							</div>
						</div>
					</div>
					
					<div data-dojo-type="dojox/mobile/View" id="_contentView">
						<div class="muiFormContent">
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<tr>
									<td class="muiTitle">
										${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDate')}
									</td>
									<td>
										<xform:datetime property="fdBorrowDate" mobile="true"></xform:datetime>
									</td>
								</tr>
								<tr>
									<td class="muiTitle">
										${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}
									</td>
									<td>
										<xform:address propertyName="fdBorrowerName" propertyId="fdBorrowerId" subject="借阅人" mobile="true" />
									</td>
								</tr>
								<tr>
									<td class="muiTitle">
										${lfn:message('km-archives:kmArchivesBorrow.docDept')}
									</td>
									<td>
										<xform:address propertyName="docDeptName" propertyId="docDeptId" subject="所属部门" mobile="true" />
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div style="position: relative;">
											<div class="muiFormEleTip"><span class="muiFormEleTitle" style="display: inherit;">${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDetails')}</span></div>
											<%@include file="/km/archives/mobile/borrow/view_detail.jsp"%>
										</div>
									</td>
								</tr>
								<tr>
									<td class="muiTitle">
										${lfn:message('km-archives:kmArchivesBorrow.fdBorrowReason')}
									</td>
									<td>
										<xform:textarea property="fdBorrowReason" mobile="true" />
									</td>
								</tr>
								<tr>
									<td class="muiTitle">
										${lfn:message('km-archives:kmArchivesBorrow.attBorrow')}
									</td>
									<td>
										<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="kmArchivesBorrowForm" />
											<c:param name="fdKey" value="attBorrow" />
										</c:import>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div data-dojo-type="dojox/mobile/View" id="_noteView">
						<div class="muiFormContent">
							<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${kmArchivesBorrowForm.fdId }"/>
								<c:param name="fdModelName" value="com.landray.kmss.km.archives.model.KmArchivesBorrow"/>
								<c:param name="formBeanName" value="kmArchivesBorrowForm"/>
							</c:import>
						</div>
					</div>
				</div>
				<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp"  
					  editUrl="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=edit&fdId=${param.fdId }"
				      formName="kmArchivesBorrowForm"
				      viewName="lbpmView"
				      allowReview="true">
				</template:include>
			</div>
			<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesBorrowForm" />
				<c:param name="fdKey" value="kmArchivesBorrow" />
				<c:param name="viewName" value="lbpmView" />
				<c:param name="backTo" value="scrollView" />
				<c:param name="onClickSubmitButton" value="Com_Submit(document.kmArchivesBorrowForm, 'approve');" />
			</c:import>
			<script type="text/javascript">
					require(["mui/form/ajax-form!kmArchivesBorrowForm"]);
			</script>
		</form>
	</template:replace>
</template:include>
