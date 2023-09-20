<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%
	KMSSUser kmssUser = UserUtil.getKMSSUser();
	String str = kmssUser.getLocale().getLanguage();
	request.setAttribute("language", str);
%>

<script type="text/javascript">
	Com_IncludeFile("data.js|dialog.js|jquery.js");
	//Com_IncludeFile("tag.js","${KMSS_Parameter_ContextPath}sys/tag/resource/js/","js",true);
	top.window.language = "${language}";
	
	/*修改统一分类*/
	function modifyCategory(canClose) {
		seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
			dialog.simpleCategory({
				modelName: 'com.landray.kmss.kms.category.model.KmsCategoryMain',
				authType: 2,
				idField: 'kmsCategoryKnowledgeRelForm.kmsCategoryIds',
				nameField: 'kmsCategoryKnowledgeRelForm.kmsCategoryNames',
				mulSelect: true,
				canClose: true,
				notNull : false 
			});
		});
	}
</script>
		
		<html:hidden property="kmsCategoryKnowledgeRelForm.fdMainId"/> 
		<html:hidden property="kmsCategoryKnowledgeRelForm.fdModelName" value="${HtmlParam.modelName}"/>
		<c:set var="mainFormNameInCate" value="${HtmlParam.formName}"></c:set>
		<c:set var="fdInputWidth" value="95%"></c:set>
		<c:if test="${HtmlParam.fdInputWidth != null}">
			<c:set var="fdInputWidth" value="${HtmlParam.fdInputWidth}"></c:set>
		</c:if>
		<td class="td_normal_title" width=15% nowrap>
			<bean:message bundle="kms-category" key="table.kmsCategoryMain"/>
		</td>
		<td colspan="3" width="85%">
			<xform:dialog
				htmlElementProperties="placeholder=${lfn:message('kms-category:kmsCategoryMain.category.placeholder.selectKmsCategory')}"
				propertyId="kmsCategoryKnowledgeRelForm.kmsCategoryIds"
				propertyName="kmsCategoryKnowledgeRelForm.kmsCategoryNames" 
				style="width:${fdInputWidth}">
				modifyCategory(true);
			</xform:dialog>
		</td>
		


