<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.KmssMessageWriter,com.landray.kmss.util.KmssReturnPage" %>
<%   
	KmssMessageWriter msgWriter = null;
	if(request.getAttribute("KMSS_RETURNPAGE")!=null){
		msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
	}else{
		msgWriter = new KmssMessageWriter(request, null);
	}
	if(request.getHeader("accept")!=null){
		if(request.getHeader("accept").indexOf("application/json") >=0){
			out.write(msgWriter.DrawJsonMessage(false).toString());
			return;
		}
	}
	response.setHeader("lui-status","true");
%>
<template:include file="/third/pda/template.jsp" compatibleMode="true">
	<template:replace name="title">
		<bean:message key="return.systemInfo"/>
	</template:replace>
	<template:replace name="head">
		<style>
			.muiLoginPage{background-color: #f0eff5;}
		</style>
	</template:replace>
	<template:replace name="content">
			<div class="noPowerTipWrapper successTip gray">
		        <div class="icon">
		            <i class="mui mui-right"></i>
		        </div>
		        <input name="inp_return" type="hidden" value="${sessionScope['S_DocLink']}"/>
		        <p class="tips"><%=msgWriter.DrawTitle(false)%></p>
		        <c:if test="${param['isAppflag']!='1' or (sessionScope['S_DocLink']!=null and sessionScope['S_DocLink']!='')}">
			        <br/><font style="color:purple;" id="_timeArea"></font><%=ResourceUtil.getString(request.getSession(), "third-pda","phone.return.msg") %>
					<a name='href_return' style="text-decoration: underline;cursor: pointer;" onclick="redirectTo();"><%=ResourceUtil.getString(request.getSession(), "third-pda","phone.view.back")%></a>
					<script type="text/javascript">Com_AddEventListener(window,'load',function(){
						window.timeInteval();
					});
					</script>
		        </c:if>
	    	</div>
	    
		<script type="text/javascript">
		require(['dojo/ready','mui/back/BackButton'],function(ready,BackButton){
			var time = 0;
			window.doBack = function(){
				var b = new BackButton();
				b.doBack();
			};
			window.redirectTo = function(){
				var docLink = document.getElementsByName("inp_return")[0];
				if(docLink.value == ''){
					if('${sessionScope["S_CurModule"]}'!=''){
						gotoList();
					}else{
						if("${param['isAppflag']=='1'}"=='true'){
							history.go(-1);
						}else
							gotoUrl('<c:url value="/third/pda/index.jsp"/>');
					}
				}else{
					if(docLink.value.indexOf("/")==0)
						location.replace(Com_SetUrlParameter(Com_Parameter.ContextPath + docLink.value.substring(1),'_reload','1'));
					else
						location.replace(Com_SetUrlParameter(docLink.value,'_reload','1'));
				}
			};
			window.timeInteval = function(){
				var _timeArea = document.getElementById("_timeArea");
				if(time==3){
					redirectTo();
					//doBack();
					return;
				}
				_timeArea.innerHTML = 3 - time;
				time ++;
				setTimeout("timeInteval()",1000);
			};
			window.gotoUrl = function(url){
				if(window.stopBubble)
					window.stopBubble();
				location=url;
			};
			window.gotoList = function(){
				var fdId='${param.fdModuleId}';
				var mobile = '${param._mobile}';
				if (mobile == '1' || mobile == 'true') {
					if(window.stopBubble)
						window.stopBubble();
					history.back();
					return;
				}
				if(fdId!=''){
					var hrefUrl='<c:url value="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=open&fdId=${param.fdModuleId}"/>';
					gotoUrl(hrefUrl);
				}else{
					if(window.stopBubble)
						window.stopBubble();
					history.back();
				}
			}
		});
			
		</script>
	</template:replace>
</template:include>
		