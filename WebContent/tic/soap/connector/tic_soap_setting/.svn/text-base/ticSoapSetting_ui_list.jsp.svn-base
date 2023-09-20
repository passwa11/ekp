<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticSoapSetting" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="docSubject" title="${ lfn:message('tic-soap-connector:ticSoapSetting.docSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticSoapSetting.docSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdWsdlUrl" title="${ lfn:message('tic-soap-connector:ticSoapSetting.fdWsdlUrl') }" escape="false" style="text-align:center;">
			<c:out value="${ticSoapSetting.fdWsdlUrl}" />
		</list:data-column>
		<list:data-column col="fdProtectWsdl" title="${ lfn:message('tic-soap-connector:ticSoapSetting.fdProtectWsdl') }" escape="false" style="text-align:center;">
			<sunbor:enumsShow value="${ticSoapSetting.fdProtectWsdl}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column col="fdCheck" title="${ lfn:message('tic-soap-connector:ticSoapSetting.fdCheck') }" escape="false" style="text-align:center;">
			<sunbor:enumsShow value="${ticSoapSetting.fdCheck}" enumsType="common_yesno" />
		</list:data-column>
				<list:data-column headerClass="width100" col="operations" title="${lfn:message('tic-core-common:ticCoreCommon.newCreateFunc')}" escape="false">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth
						requestURL="/tic/soap/connector/tic_soap_main/ticSoapMain.do?method=add&wsServerSettingId=${ticRestSetting.fdId}"
						requestMethod="GET">
           <a class="btn_txt" style="color:#4285f4;"
					href="javascript:addFunc('${ticSoapSetting.fdId}')">${lfn:message('tic-core-common:ticCoreCommon.newCreateFunc')}</a></kmss:auth></div></div>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
