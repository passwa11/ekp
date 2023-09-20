<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/sys/praise/sysPraiseAndNegativeMain_view_js.jsp"%>
<%@page import="com.landray.kmss.sys.praise.service.ISysPraiseMainService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.praise.model.SysPraiseMain"%>

<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/praise/style/view.css" />
<c:set var="formName" value="${requestScope[param.formName]}"/>

<%
	ISysPraiseMainService sysPraiseMainService = 
			(ISysPraiseMainService) SpringBeanUtil.getBean("sysPraiseMainService");
	Boolean isNegative = sysPraiseMainService.checkPraised(UserUtil.getUser().getFdId(),
							request.getParameter("fdModelId"),request.getParameter("fdModelName"),SysPraiseMain.SYSPRAISEMAIN_NEGATIVE);
	pageContext.setAttribute("isNegative",isNegative);
	if(isNegative){
		String cancelNegative = ResourceUtil.getString("sys-praise:sysPraiseMain.cancel.negative");
		pageContext.setAttribute("negativetitle",cancelNegative);
	}else{
		String negative = ResourceUtil.getString("sys-praise:sysPraiseMain.negative");
		pageContext.setAttribute("negativetitle",negative);
	}
	Boolean isPraised = sysPraiseMainService.checkPraised(UserUtil.getUser().getFdId(),
			request.getParameter("fdModelId"),request.getParameter("fdModelName"),"");
	pageContext.setAttribute("isPraised",isPraised);
	
	if(isPraised){
		String cancelPraise = ResourceUtil.getString("sys-praise:sysPraiseMain.cancel.praise");
		pageContext.setAttribute("praisetitle",cancelPraise);
	}else{
		String praise = ResourceUtil.getString("sys-praise:sysPraiseMain.praise");
		pageContext.setAttribute("praisetitle",praise);
	}
%>
<input type="hidden" name="isPraised" value="${isPraised}" id="check_${HtmlParam.fdModelId}">
<span id="aid_${HtmlParam.fdModelId}" 
		praise-data-modelid="${HtmlParam.fdModelId}" praise-data-modelname="${HtmlParam.fdModelName}" style="position:relative;">
	<span onclick="sysPraise('${JsParam.fdModelId}');" class="cur" data-lui-id='${HtmlParam.fdModelId}' >
		<span id="praise_icon" class="${isPraised == true ? 'sys_unpraise' : 'sys_praise'}" title="${praisetitle}"></span>
		<span id="praise_count" class="praise_count">
			${(not empty param.docPraiseCount) ? (param.docPraiseCount) : (formName.docPraiseCount)}
		</span>
	</span>
	<div class="lui_praise_layer" style="display: none;">
		<html:hidden property="showPraiserCount" value="${HtmlParam.showPraiserCount}"/>
		<div class="bg">
			<table class="lui_praise_table">
				<tr>
					<td>
						<div class="content layer_person">
							<div class="lui_praise_close_d" id="praise_close">
								<span class="lui_praise_close_s"></span>
							</div>
							<ul class="person_list clearfix" id="praisedPerson_list">
							</ul>
							<div id="praise_page_list" class="praise_page_list">
								<a class="praise_page_set">
									<span>
										<em onclick="prePage('${JsParam.fdModelId}');" data-praise-mark="1" class="praise_icon_l" id="btn_preno"></em>
									</span>
									<span>
										<em onclick="nextPage('${JsParam.fdModelId}');" data-praise-mark="1" class="praise_icon_r" id="btn_nextno"></em>
									</span>
								</a>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<div class="app_arrow app_arrow_p"></div>
		</div>
	</div>
</span>
<input type="hidden" name="isNegative" value="${isNegative}" id="checkNegative_${HtmlParam.fdModelId}">
<span id="aidNegative_${HtmlParam.fdModelId}" 
		negative-data-modelid="${HtmlParam.fdModelId}" negative-data-modelname="${HtmlParam.fdModelName}" style="position:relative;">
	<span onclick="sysPraiseNegative('${JsParam.fdModelId}');" class="cur" data-lui-id='${HtmlParam.fdModelId}' >
		<span id="negative_icon" class="${isNegative == true ? 'sys_negative' : 'sys_unNegative'}" title="${negativetitle}"></span>
		<span id="negative_count" class="negative_count">
			${(not empty param.docNegativeCount) ? (param.docNegativeCount) : (formName.docNegativeCount)}
		</span>
	</span>		
<div class="lui_negative_layer" style="display: none;">
<html:hidden property="showNegativeCount" value="${HtmlParam.showNegativeCount}"/>
<div class="bg">
			<table class="lui_negative_table">
				<tr>
					<td>
					<div class="content layer_person">
							<div class="lui_negative_close_d" id="negative_close">
								<span class="lui_negative_close_s"></span>
							</div>
							<ul class="person_list clearfix" id="negativePerson_list">
							</ul>
							<div id="negative_page_list" class="negative_page_list">
								<a class="negative_page_set">
									<span>
										<em onclick="preNegativePage('${JsParam.fdModelId}');" data-negative-mark="1" class="praise_icon_l" id="btn_negative_preno"></em>
									</span>
									<span>
										<em onclick="nextNegativePage('${JsParam.fdModelId}');" data-negative-mark="1" class="praise_icon_r" id="btn_negative_nextno"></em>
									</span>
								</a>
							</div>
					</div>
					</td>
				</tr>
			</table>
			<div class="app_arrow app_arrow_p"></div>
	</div>
</div>
</span>