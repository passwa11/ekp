<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

 	<c:set var="totalPerson" value="${map.onpost + map.waitentry + map.waitleave }" scope="request"></c:set>
 	<c:set var="onpost" value="${map.onpost }" scope="request"></c:set>
 	<c:set var="waitentry" value="${map.waitentry}" scope="request"></c:set>
 	<c:set var="waitleave" value="${map.waitleave }" scope="request"></c:set>
 	<%
 		int total = Integer.parseInt(request.getAttribute("totalPerson").toString());
 		int post = Integer.parseInt(request.getAttribute("onpost").toString());
 		int entry = Integer.parseInt(request.getAttribute("waitentry").toString());
 		int leave = Integer.parseInt(request.getAttribute("waitleave").toString());
 		String postWidth = "";
 		String entryWidth = "";
 		String leaveWidth = "";
 		String valignCls = "";
 		if(total>0){
 			valignCls = "account-vertical";
 			double perentValue =0;
 			if(post>0){
 				perentValue = ((double)post)/total;
 				if(perentValue<0.02&&perentValue>0){
 					perentValue+=0.01;
 				}
 				perentValue=perentValue*75;
 				postWidth = "style='width:"+perentValue+"%'";
 			}
 			if(entry>0){
 				perentValue = ((double)entry)/total;
 				if(perentValue<0.02&&perentValue>0){
 					perentValue+=0.01;
 				}
 				perentValue=perentValue*75;
 				entryWidth = "style='width:"+perentValue+"%'";
 			}
 			if(leave>0){
 				perentValue = ((double)leave)/total;
 				if(perentValue<0.02&&perentValue>0){
 					perentValue+=0.01;
 				}
 				perentValue=perentValue*75;
 				leaveWidth = "style='width:"+perentValue+"%'";
 			}    			
 		}
 	%>
<div class="ld-stripFigure">
    <h3>
    	<c:choose>
    		<c:when test="${param.fdType == 'post'}">
    			${lfn:message('hr-organization:hr.organization.info.emp.post')}
    		</c:when>
    		<c:otherwise>
    			${lfn:message('hr-organization:hr.organization.info.emp.org')}
    		</c:otherwise>
    	</c:choose>
    	 ${lfn:message('hr-organization:hr.organization.info.emp.info')}
    </h3>
    <div class="ld-person-account-box <%=valignCls%>">
        <div class="ld-person-account-total">                       
           <%--  <div class="ld-person-account-tip">
            	<p>${map.onpost + map.waitentry + map.waitleave }人&nbsp; <span>总计</span></p>
            </div> --%>
            <div class="ld-person-account-progress">
             	<div class="ld-person-account-progress-onpost ld-person-account-tip">
             		<div <%=postWidth %>></div>
             		<p>${map.onpost}${lfn:message('hr-organization:hr.organization.info.emp.p')} &nbsp;<span>${lfn:message('hr-organization:hr.organization.info.emp.jobing')}</span></p>
             	</div>
             	<div class="ld-person-account-progress-waitentry ld-person-account-tip">
             		<div <%=entryWidth %>></div>
             		<p>${map.waitentry}${lfn:message('hr-organization:hr.organization.info.emp.p')}&nbsp;<span>${lfn:message('hr-organization:hr.organization.info.emp.job')}</span></p>
             	</div>
             	<div class="ld-person-account-progress-waitleave ld-person-account-tip">
             		<div <%=leaveWidth %>></div>
             		<p>${map.waitleave}${lfn:message('hr-organization:hr.organization.info.emp.p')}&nbsp;<span>${lfn:message('hr-organization:hr.organization.info.emp.leave')}</span></p>
             	</div>                       	
            </div>
        </div>

    </div>
</div>