<%@page import="com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter"%>
<%@ page import="com.landray.kmss.sys.person.interfaces.PersonImageService"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.PersonZoneHelp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%
	PersonImageService personImageservice = PersonZoneHelp.getPersonImageService();
	String url = personImageservice.getHeadimageChangeUrl();
	if(!PersonInfoServiceGetter.isFullPath(url)){
		url = request.getContextPath() + url;
	}
	request.setAttribute("headImageChangeUrl", url);
%>
<div class="lui_person_usertitle">
    <div>
    
     	<div id="sys_person_userpic" class="lui_person_userpic">
			<img src="<person:headimageUrl personId="${KMSS_Parameter_CurrentUserId}" size="b" contextPath="true" />" id="sys_person_userpic_img" />	
			<div id="sys_person_usermask" class="lui_person_usermask" style="white-space: nowrap; ">
	    		<bean:message bundle="sys-person" key="info.head"/>
	    	</div>
		</div>
		
    </div>
    <div class="lui_person_author" style="text-align: center;padding: 5px 10px;">
		<c:if test="${empty noShowSettingOption }"> 
	    	<a class="com_author" href="${ LUI_ContextPath }/sys/person/setting.do?setting=sys_zone_person_info" target="_blank">
	    </c:if>
    	<c:out value="<%= UserUtil.getKMSSUser().getUserName() %>" />
	    <c:if test="${empty noShowSettingOption }"> 
	    	</a>
	    </c:if>	
    </div>
    <div class="lui_person_dept">
    	<span><c:out value="<%=UserUtil.getKMSSUser().getDeptName()%>" /></span>
    </div>
    <div class="clr"></div>
</div>
<script>
	seajs.use(['lui/jquery', 'lui/topic'],function($, topic){
		$(function(){
			var setting = Com_GetUrlParameter(location.href,'setting');
			if(setting != 'sys_zone_person_photo'){
				var pic = $('#sys_person_userpic'),
				 	mask = $('#sys_person_usermask');
				pic.css('cursor','pointer');
				pic.click(function(){
					var url='${headImageChangeUrl}'+ "&j_iframe=true&j_aside=false";
					LUI.pageOpen(url, '_rIframe');
					//window.open('${headImageChangeUrl}');
				}).mouseover(function(){
					mask.show();
				}).mouseout(function(){
					mask.hide();
				});
			}
			
			topic.subscribe('lui.crop.success', function(data) {
				if(data && data.modelId) {
					if(data.modelId == '${KMSS_Parameter_CurrentUserId}') {
						var img = $('#sys_person_userpic_img');
						var src = img.attr("src");
						if(src) {
							var _s = Com_SetUrlParameter(src, "s_time" , new Date().getTime());
							img.attr("src", _s);
						}
					}
				}
			})
		});
	});
</script>
