<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%
  String corpId = DingConfig.newInstance().getDingCorpid();
%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="content">
	    <div class='lui_form_title_frame'>
                 <div class='lui_form_subject'>
                     	测试钉盘
                 </div>
                 <div class='lui_form_baseinfo'>

                 </div>
             </div>
             <div>
                 
                  <br/><br/>
                 <input type="button" value="下载钉盘文件" onclick="downloadSpaceFile()">
                               
                 
             </div>
             <script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
            
			
						
             <script type="text/javascript">
	require([ 'dojo/ready', 'mui/device/adapter', 'dojo/query', "dojo/request","mui/dialog/Tip"],
			function(ready,adapter,query, request, Tip){
		
		//downloadSpaceFile
		window.downloadSpaceFile = function(){
			var options = {
					corpId:'<%=corpId%>'
			};
			adapter.downloadFromDing(options);
			
		}	
		
	})
	
	
	
	</script>      
		
	</template:replace>
</template:include>