<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.kms.loperation.service.IKmsLoperationStuTotalService"%>
<%@page import="com.landray.kmss.kms.loperation.model.KmsLoperationStuTotal" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	IKmsLoperationStuTotalService  kmsLoperationStuTotalService = 
	 		(IKmsLoperationStuTotalService)SpringBeanUtil.getBean("kmsLoperationStuTotalService");
	KmsLoperationStuTotal stuTotal = 
			kmsLoperationStuTotalService.addFdIdByUserId(UserUtil.getKMSSUser(request).getUserId());
	Float credit = stuTotal.getFdCreditSum();
	pageContext.setAttribute("creditSum",
			String.format("%.0f", credit));
	Integer medalNum = 0;
	Integer diplomaNum = 0;
	Double ltime = 0d;
	if(stuTotal != null &&  stuTotal.getFdTotal() != null) {
		if(stuTotal.getFdTotal().getFdMedalNum() != null && stuTotal.getFdTotal().getFdMedalNum() >= 0) {
			medalNum = stuTotal.getFdTotal().getFdMedalNum();
		}
		if(stuTotal.getFdTotal().getFdDiplomaNum() != null && stuTotal.getFdTotal().getFdDiplomaNum() >= 0) {
			diplomaNum = stuTotal.getFdTotal().getFdDiplomaNum();
		}
		if(stuTotal.getFdTotal().getFdTime() != null && stuTotal.getFdTotal().getFdTime() > 0) {
			ltime = stuTotal.getFdTotal().getFdTime();
		}
		
	}
	pageContext.setAttribute("medalNum", medalNum);
	pageContext.setAttribute("diplomaNum", diplomaNum);
	pageContext.setAttribute("ltime", ltime);
	
	
%>
	
	<li><a  href="${LUI_ContextPath}/kms/loperation/student/" target="_blank">
			${ltime}</a>
		<p>${lfn:message('kms-loperation:kmsLoperationStuTotal.time') }(分钟)</p>
	</li>
		
	<kmss:ifModuleExist path="/kms/medal">	
		<li><a  href="${LUI_ContextPath}/kms/medal/#cri.q=medalType%3Acreate" target="_blank">
			${medalNum}</a>
			<p>${lfn:message('kms-loperation:kmsLoperationStuTotal.medal') }</p>
		</li>
	</kmss:ifModuleExist>
	
	<kmss:ifModuleExist path="/kms/diploma">
		<li><a href="${LUI_ContextPath}/kms/diploma/main/student/" target="_blank">
				${diplomaNum}</a>
			<p>${lfn:message('kms-loperation:kmsLoperationStuTotal.diploma') }</p>
		</li>
	</kmss:ifModuleExist>
