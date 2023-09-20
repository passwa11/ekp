<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.km.forum.model.KmForumConfig"%>
	   <%
	     String isCheckWord = new KmForumConfig().getIsWordCheck();
	     request.setAttribute("isCheckWord",isCheckWord);
	   %>
<script type="text/javascript">
		seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			//判断是否含有敏感词
			window.checkIsHasSenWords= function(formName,content,attr) {
				var result = false;
    			var url = "${LUI_ContextPath}/sys/profile/sysCommonSensitiveConfig.do?method=getIsHasSensitiveword";
				var data ={formName:formName,content:encodeURIComponent(content)};
				LUI.$.ajax({
					url: url,
					type: 'post',
					dataType: 'json',
					async: false,
					data: data,
					success: function(data, textStatus, xhr) {
					    var json = eval(data);
					    var flag = json.flag;
					    if(flag){
					    	result="<bean:message bundle='sys-profile' key='sys.profile.sensitive.word.warn'/>".replace("{0}",attr).replace("{1}",json.senWords);
					    }
					}
				});
				return result;
		    };

			    
		  });
</script>