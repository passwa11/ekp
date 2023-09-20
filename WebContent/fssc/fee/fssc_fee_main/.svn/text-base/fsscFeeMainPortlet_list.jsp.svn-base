<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="fsscFeeMain" list="${queryPage.list }" custom="false">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="docSubject" headerClass="width250" styleClass="width250"  title="${ lfn:message('fssc-fee:fsscFeeMain.docSubject') }" escape="false" style="text-align:left;min-width:100px">
		  <a class="com_subject textEllipsis" title="${fsscFeeMain.docSubject}" href="${LUI_ContextPath}/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=view&fdId=${fsscFeeMain.fdId}" target="_blank">
		  	[${fsscFeeMain.docTemplate.fdName}]&nbsp;&nbsp;<c:out value="${fsscFeeMain.docSubject}"/>
		  </a>
		</list:data-column>
		<list:data-column  col="docNumber"  title="${ lfn:message('fssc-fee:fsscFeeMain.docNumber') }">
		    ${fsscFeeMain.docNumber}
		</list:data-column>
		<list:data-column  col="docCreateTime"  title="${ lfn:message('fssc-fee:fsscFeeMain.docCreateTime') }">
		   <kmss:showDate value="${fsscFeeMain.docCreateTime}" type="date"/>
		</list:data-column>
		<list:data-column  col="docStatus" headerClass="width120" styleClass="width120" title="${ lfn:message('fssc-fee:fsscFeeMain.docStatus') }">
		    <sunbor:enumsShow enumsType="common_status" value="${fsscFeeMain.docStatus}"></sunbor:enumsShow>
		</list:data-column>
		 <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-fee:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscFeeMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-fee:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscFeeMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
	</list:data-columns>
</list:data>
