<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<c:out value="${fsscFeeMainForm.docSubject}"></c:out>
	</template:replace>
	<template:replace name="head">
	   <script type="text/javascript">
	   	require(["dojo/store/Memory","dojo/topic"],function(Memory, topic){
	   		window._narStore = new Memory({data:[{'text':'${lfn:message("fssc-fee:table.fsscFeeMain")}',
	   			'moveTo':'_contentView','selected':true},{'text':'${lfn:message("fssc-fee:fsscFeeMain.mobile.flow")} ',
	   			'moveTo':'_noteView'}]});
	   		topic.subscribe("/mui/navitem/_selected",function(evtObj){
	   			setTimeout(function(){topic.publish("/mui/list/resize");},150);
	   		});
	   	});
	   </script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/fssc/fee/fssc_fee_main/fsscFeeMain.do">		
		<div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" class="muiFlowBack">
			<div id="_banner" data-dojo-type="mui/view/ViewBanner" data-dojo-props="
				docStatus:'${fsscFeeMainForm.docStatus}',
				icon:'<person:headimageUrl contextPath="true" personId="${fsscFeeMainForm.docCreatorId}" size="m" />',
				creator:'${fsscFeeMainForm.docCreatorName}',
				docSubject:'<c:out value="${ fsscFeeMainForm.docSubject }" />'"></div>
			<div class="muiFlowInfoW">
					<div data-dojo-type="mui/fixed/Fixed" id="fixed">
						<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
							<div data-dojo-type="mui/nav/NavBarStore" data-dojo-props="store:_narStore">
							</div>
						</div>
					</div>
					<div data-dojo-type="dojox/mobile/View" id="_contentView">
						<div data-dojo-type="mui/table/ScrollableHContainer">
							<div data-dojo-type="mui/table/ScrollableHView" class="muiFormContent">
								<c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="fsscFeeMainForm" />
									<c:param name="fdKey" value="feeMainDoc" />
									<c:param name="backTo" value="scrollView" />
								</c:import>
							</div>
						</div>
					</div>
					<div data-dojo-type="dojox/mobile/View" id="_noteView">
						<div class="muiFormContent">
							<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${fsscFeeMainForm.fdId }"/>
								<c:param name="fdModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain"/>
								<c:param name="formBeanName" value="fsscFeeMainForm"/>
							</c:import>
							<xform:isExistRelationProcesses relationType="parent">
								<xform:showParentProcesse mobile="true" />
							</xform:isExistRelationProcesses>
								
							<xform:isExistRelationProcesses relationType="subs">
								<xform:showSubProcesses mobile="true"/>
							</xform:isExistRelationProcesses>
						</div>
					</div>
				</div>
			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
			      docStatus="${fsscFeeMainForm.docStatus}" 
			      editUrl="javascript:window.building();"
				  formName="fsscFeeMainForm"
				  viewName="lbpmView"
				  allowReview="true">
				<template:replace name="flowArea">
					<c:import url="/sys/bookmark/mobile/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="fdModelName"
							value="com.landray.kmss.fssc.fee.model.FsscFeeMain"></c:param>
						<c:param name="fdModelId" value="${fsscFeeMainForm.fdId}"></c:param>
						<c:param name="fdSubject" value="${fsscFeeMainForm.docSubject}"></c:param>
						<c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark')}"></c:param>
						<c:param name="showOption" value="label"></c:param>
					</c:import>
				</template:replace>
				<template:replace name="publishArea">
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" >
						<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
						  <c:param name="fdModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain"></c:param>
						  <c:param name="fdModelId" value="${fsscFeeMainForm.fdId}"></c:param>
						  <c:param name="fdSubject" value="${fsscFeeMainForm.docSubject}"></c:param>
						  <c:param name="showOption" value="label"></c:param>
					  </c:import>
				</ul>
				</template:replace>
			</template:include>
		</div>
		<c:if test="${fsscFeeMainForm.docStatus < '30' }">
					<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscFeeMainForm" />
						<c:param name="fdKey" value="feeMainDoc" />
						<c:param name="backTo" value="scrollView" />
						<c:param name="onClickSubmitButton" value="Com_Submit(document.fsscFeeMainForm, 'update');" />
					</c:import>
					<script type="text/javascript">
						require(["mui/form/ajax-form!fsscFeeMainForm"]);
					</script>
		</c:if>
	</html:form>
	</template:replace>
</template:include>
