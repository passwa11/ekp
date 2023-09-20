<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPost" %>
<%@page import="com.landray.kmss.sys.config.util.LicenseUtil" %>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String userName = UserUtil.getKMSSUser().getUserName();
	String loginName = UserUtil.getKMSSUser().getPerson().getFdLoginName();
	String dept = UserUtil.getKMSSUser().getDeptName();
	String telphone = UserUtil.getKMSSUser().getPerson().getFdMobileNo();
	String email = UserUtil.getKMSSUser().getPerson().getFdEmail();
	List<SysOrgPost> posts = UserUtil.getKMSSUser().getPerson().getFdPosts();
	String licenseId =LicenseUtil.get("license-customer-id");
	String title="";
	Object[] array=posts.toArray();
	for(int i=0;i<array.length;i++){
		SysOrgPost sopost =(SysOrgPost)array[i];
		title+=sopost.getFdName()+",";
	}
	if(title.length()>0){
		title=title.substring(0,title.length()-1);
	}
	request.setAttribute("userName",userName);
	request.setAttribute("loginName",loginName);
	request.setAttribute("dept",dept);
	request.setAttribute("telphone",telphone);
	request.setAttribute("email",email);
	request.setAttribute("title",title);
	request.setAttribute("licenseId",licenseId);
%>
<script type="text/javascript">
var userName = '${requestScope.userName}';
var loginName = '${requestScope.loginName}';
var dept = '${requestScope.dept}';
var telphone = '${requestScope.telphone}';
var email = '${requestScope.email}';
var title = '${requestScope.title}';
var licenseId = '${requestScope.licenseId}';
</script>