<%@ page import="java.util.ArrayList"%>
<%@ page import="com.landray.kmss.sys.notify.service.ISysNotifyTodoService"%>
<%@ page import="com.landray.kmss.sys.notify.service.spring.SysNotifyEmailServiceImp"%>
<%@ page import="com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter"%>
<%@ page import="com.landray.kmss.sys.person.interfaces.PersonImageService"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.PersonZoneHelp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%
	PersonImageService personImageservice = PersonZoneHelp.getPersonImageService();
	ISysNotifyTodoService todoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
	String curUserID = UserUtil.getUser(request).getFdId();
	// 查询代办数量
	Long toDoCount = todoService.getTodoCount(curUserID, 13);
	request.setAttribute("toDoCount", toDoCount);
	// 查询待阅数量
	Long toViewCount = todoService.getTodoCount(curUserID, 2);
	request.setAttribute("toViewCount", toViewCount);
	
	// 查询待处理邮件数量
	SysNotifyEmailServiceImp emailNotifyService = new SysNotifyEmailServiceImp();
	String importMailNumJsp = emailNotifyService.getEmailNumsJspFromPlugin();
	request.setAttribute("isShowEmail", emailNotifyService.isShowEmails());
	if(StringUtils.isNotBlank(importMailNumJsp)){
		request.setAttribute("importMailNumJsp",importMailNumJsp);
	}
	String[] postNames=UserUtil.getKMSSUser(request).getPostNames();
	if(postNames!=null){
		request.setAttribute("hasPost", true);
		request.setAttribute("postNames", postNames);
		request.setAttribute("lastPost", postNames.length-1);
	}else{
		request.setAttribute("hasPost", false);
		request.setAttribute("postNames", new ArrayList());
	}
%>
<div class="lui_person_info_usertitle">
	<div class="lui_person_info_usertitle_head">
		<!-- 个人设置 icon -->
		<div title="${lfn:message('sys-person:person.setting') }" class="lui_person_info_setting">
			<a href="${ LUI_ContextPath }/sys/person/"><span></span></a>
		</div>

		<div id="sys_person_userpic" class="lui_person_info_userpic" >
			<img src="<person:headimageUrl personId="${KMSS_Parameter_CurrentUserId}" size="b" contextPath="true" />" id="sys_person_userpic_img" />	
		</div>
		<div class="lui_person_info_author" >
				<h3><c:out value="<%= UserUtil.getKMSSUser().getUserName() %>" /></h3>
		</div>
	</div>
    <div class="lui_person_info_line">
    	<span><c:out value="<%=UserUtil.getKMSSUser().getDeptName()%>" />
    	<c:if test="${hasPost }">
	    	<c:forEach items="${postNames }" varStatus="status" var="postName">
	    		<c:out value="${postName }" /><c:if test="${status.index!=lastPost }">,</c:if>
	    	</c:forEach>
    	</c:if>
    	</span>
    </div>
    <div class="lui_person_info_todo">
     	<ul>
            <li><a href="${ LUI_ContextPath }/sys/notify/index.jsp#j_path=%2Fprocess&amp;dataType=todo" target="_blank"><em>${toDoCount }</em><span>待办</span></a></li>
            <li><a href="${ LUI_ContextPath }/sys/notify/index.jsp#j_path=%2Fread&amp;dataType=toview" target="_blank"><em>${toViewCount }</em><span>待阅</span></a></li>
            <% /**  显示 邮件数量    **/ %>
			<c:if test="${isShowEmail eq true}">
				<c:catch var="exception">
					<c:if test="${not empty importMailNumJsp}">
						<li id="emailNum"><a href="" target="_blank"><em>0</em><span>邮件</span></a></li>
						<div style="display: none" >
			    			<iframe id="emailFrame" src="${ LUI_ContextPath }${importMailNumJsp}" ></iframe>
			    		</div>
			    	</c:if>
				</c:catch> 
				 <c:if test="${not empty exception}">
				 	<script type="text/javascript">
						document.getElementById('emailNum').style.display="none";
					</script>
				 </c:if>
			</c:if>
        </ul>
    </div>
    <div class="clr"></div>
	<script type="text/javascript">
    	var postsSpan=$("#${param.LUIID}").find("lui_person_info_line span");
    	var text=postsSpan.text();
    	postsSpan.text(".");
    	var height=postsSpan.height();
    	postsSpan.text(text);
    	for(var i=text.length-2; height>5 && i>0 && postsSpan.height()-4>height; i--){
    		postsSpan.text(text.substring(0, i)+'...');
    	}
    	if(${isShowEmail=='true'}){
    		$("#${param.LUIID}").find(".lui_person_info_todo ul li").css("width","33.3%");
    	}else{
    		$("#${param.LUIID}").find(".lui_person_info_todo ul li").css("width","50%");
    	}
    </script>
</div>
