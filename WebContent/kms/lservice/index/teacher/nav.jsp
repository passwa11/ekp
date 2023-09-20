<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.kms.lservice.util.UrlsUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
.change_button{
	color: #4285F4;
	display: inline-block;	
	font-family: PingFangSC-Regular;
}
.change_role{
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: #262626;
    font-style: normal;
}
.change_name{
	font-family: PingFangSC-Medium;
	font-size: 18px;
	color: #262626;
}
.change_dept{
	font-family: PingFangSC-Regular;
	font-size: 14px;
	color: #888888;
	margin-bottom: 15px;
	margin-top: 6px;
}
.change_content{
	background: #fff;
    width: 96px;
    z-index: 1000;
    position: absolute;
    line-height: 35px;
    box-shadow: 0 1px 3px 0 rgb(153 179 210 / 20%);
    display: none;
}	
.change_content span{
	display: block;
	color: #333333;
    font-size: 14px;
    font-family: MicrosoftYaHei;
}
.change_content span:hover{
	background-color: rgba(230,234,238,0.40);
}
.change_content_on{
	background-color: rgba(230,234,238,0.40);
}
</style>
	<%
	String type = "teacher";
	String  roleTxt  = "";
	if(StringUtil.isNotNull(type)) {
		roleTxt = ResourceUtil.getString("kms-lservice:lservice.role." + type);
	}
	String modelName = request.getParameter("modelName");
	
	String studentUrl = UrlsUtil.getStudentUrlByModelNameAndType(modelName);
	if (StringUtil.isNull(studentUrl))
		studentUrl = "";
	
	String teacherUrl = UrlsUtil.getTeacherUrlByModelNameAndType(modelName);
	if(StringUtil.isNull(teacherUrl))
		teacherUrl = "";
	
	String adminUrl = UrlsUtil.getAdminUrlByModelNameAndType(modelName);
	if(StringUtil.isNull(adminUrl))
		adminUrl = "";
	%>
		<div style="text-align: center;margin-bottom: 25px;">
			<img src="${KMSS_Parameter_ContextPath}sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=<%=UserUtil.getUser().getFdId() %>" style="border-radius: 50%;" height="70px" width="70px">
			<div class="change_name"><%=UserUtil.getUserName(request)%></div>
			<div class="change_dept"><%=UserUtil.getUser().getFdParentsName()%></div>
			<div>
				<span>
					<i class="lservice-role lservice_iconfont" style="font-style: normal;font-size: 14px;margin-right: 3px;"></i>
						<i class="change_role"><%=roleTxt%> | </i> <a class="change_button" id="role" >${lfn:message('kms-lservice:lservice.index.role.switch') }    <i class="lui_caret"></i>
							<ul class="change_content" id="content">
							<%
								if(StringUtil.isNotNull(studentUrl) && UserUtil.checkAuthentication(studentUrl, "GET")) {
							%>
								<span class="<%="student".equals(type) ? "change_content_on" : ""%>" data-url="<%=studentUrl%>">${lfn:message('kms-lservice:lservice.role.student') }</span>
							<%
								}
							%>
							<%
								if(StringUtil.isNotNull(teacherUrl) && UserUtil.checkAuthentication(teacherUrl, "GET")) {
							%>
								<span class="<%="teacher".equals(type) ? "change_content_on" : ""%>" data-url="<%=teacherUrl%>">${lfn:message('kms-lservice:lservice.role.teacher') }</span>
							<%
								}
							%>
							<%
								if(StringUtil.isNotNull(adminUrl) && UserUtil.checkAuthentication(adminUrl, "GET")) {
							%>
								<span class="<%="admin".equals(type) ? "change_content_on" : ""%>" data-url="<%=adminUrl%>">${lfn:message('kms-lservice:lservice.role.admin') }</span>
							<%
								}
							%>
							</ul>
						</a>
				</span>
			</div>
		</div>
<c:import url="/kms/lservice/index/common/nav.jsp" charEncoding="UTF-8">
	<c:param name="type" value="teacher"></c:param>
</c:import>
<script>
seajs.use(["lui/jquery", "lui/util/env"], function($, env) {
	$("[data-url]").on("click" , function(evt) {
		var $t = $(evt.currentTarget), url =  $t.attr("data-url");
		if( $t.hasClass("change_content_on")) {
			return;
		}
		if(url) {
			LUI.pageOpen(env.fn.formatUrl(url), '_iframe');
		}
	});
	$("#role").mouseover(function () {
	    $("#content").show();
	});
	$("#role").mouseout(function () {
	    $("#content").hide();
	});
});


</script>