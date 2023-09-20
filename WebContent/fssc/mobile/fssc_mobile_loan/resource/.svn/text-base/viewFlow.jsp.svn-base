<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<c:out value="${fsscLoanMainForm.docSubject}"></c:out>
	</template:replace>
	<template:replace name="content">
	<div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
		<div data-dojo-type="mui/panel/AccordionPanel">
			<xform:isExistRelationProcesses relationType="parent">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:lbpm.parentprocesses')}',icon:'mui-ul'">
                            <xform:showParentProcesse mobile="true" />
                        </div>
                    </xform:isExistRelationProcesses>
                    <xform:isExistRelationProcesses relationType="subs">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:lbpm.subprocesses')}',icon:'mui-ul'">
                            <xform:showParentProcesse mobile="true" />
                        </div>
                    </xform:isExistRelationProcesses>
                    <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:py.LiuChengChuLi')}',icon:'mui-ul'">
                        <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
                            <c:param name="fdModelId" value="${fsscLoanMainForm.fdId}" />
                            <c:param name="fdModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                            <c:param name="formBeanName" value="fsscLoanMainForm" />
                        </c:import>
                    </div>

                </div>
                <c:if test="${fsscLoanMainForm.docStatus >=20}">
                <template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" editUrl="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=edit&fdId=${param.fdId }" formName="fsscLoanMainForm" viewName="lbpmView" allowReview="true">
                    <template:replace name="flowArea">


                        <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="fsscLoanMainForm"></c:param>
                            <c:param name="showNum" value="false"></c:param>
                            <c:param name="showOption" value="label"></c:param>
                        </c:import>
                    </template:replace>
                    <template:replace name="publishArea">


                        <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="fsscLoanMainForm"></c:param>
                            <c:param name="showOption" value="label"></c:param>
                        </c:import>
                    </template:replace>
                </template:include>
                </c:if>
            </div>


            <c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscLoanMainForm" />
                <c:param name="fdKey" value="fsscLoanMain" />
                <c:param name="backTo" value="scrollView" />
            </c:import>
            <script type="text/javascript">
                require(["mui/form/ajax-form!fsscLoanMainForm"]);
            </script>
	</template:replace>
</template:include>
