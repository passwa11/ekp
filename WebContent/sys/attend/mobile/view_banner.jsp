<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<c:set var="sysAttendMainExcForm" value="${requestScope[param.formBeanName]}" />
<div class="muiFlowInfoW muiFormContent">
	<div class="muiFormSubject">
		<xform:text property="docSubject"></xform:text>
	</div> 
	<c:if test="${sysAttendMainExcForm.docStatus eq '30'}">
   		<div class="muiProcessStatus"  <c:if test="${empty param.loading or param.loading == 'false' }">id=processStatusDiv</c:if>  >
       		<i class="mui mui-processPass"></i>
      	</div>
    </c:if>
   	<c:if test="${sysAttendMainExcForm.docStatus eq '00'}">
    	<div class="muiDiscardStatus" <c:if test="${empty param.loading or param.loading == 'false' }">id="discardStatusDiv"</c:if> >
       		<i class="mui mui-processDiscard"></i>
      	</div>
   	</c:if>
	 <table class="muiSimple muiFlowTable" cellpadding="0" cellspacing="0">
		<tr>
			<td rowspan="2" class="muiFlowIconTd">
				 <div class="muiProcessIcon">
                    <img class="muiProcessImg" src='<person:headimageUrl contextPath="true"  personId="${sysAttendMainExcForm.fdAttendMainDocCreatorId}" size="m" />'/>
                </div>
                <div class="muiFlowCreator">
                	<xform:text property="fdAttendMainDocCreatorName"></xform:text>
                </div>
			</td>
			<td class="muiFlowSummary">
				<div>${lfn:message('sys-attend:sysAttendMain.docCreateTime1') }：
					<c:if test="${empty sysAttendMainExcForm.fdAttendTime} ">
									<c:out value="${sysAttendMainExcForm.fdAttendMainCreateTime}" />
								</c:if>
								${sysAttendMainExcForm.fdAttendTime}
				 </div>
			</td>
		</tr>
	</table>
</div>
<c:if test="${not empty param.loading and param.loading == 'true' }">
	<div style="height: 100%;padding-top: 10rem;">
		<%@ include file="/sys/mobile/extend/combin/loading.jsp"%>
	</div>
</c:if>