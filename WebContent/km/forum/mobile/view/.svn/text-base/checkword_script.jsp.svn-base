<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

function  forum_wordCheck(content,warnText){
	var result = true;
	var url = "${LUI_ContextPath}/sys/profile/sysCommonSensitiveConfig.do?method=getIsHasSensitiveword";
	var data ={formName:'kmForumPostForm',content:encodeURIComponent(content)};
	request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				var json = eval(data);
				var flag = json.flag;
				var hasWarn = (flag==true || flag=='true');
				if(hasWarn){
					Tip.fail({text:warnText,width:200,height:100});
				}
				result = !hasWarn;
			},function(data){
				result = true;
			});
	return result;
}