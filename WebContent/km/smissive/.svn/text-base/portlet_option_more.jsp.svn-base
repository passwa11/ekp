<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
<script>
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
var mydoc = getUrlParam('mydoc');
if(mydoc!=null){
	if(mydoc =='unExecuted' ){//待审
		window.location.href = "${LUI_ContextPath}/km/smissive/#j_path=/listApproval&amp;mydoc=approval";
	}else if(mydoc =='executed'){//已审
		window.location.href = "${LUI_ContextPath}/km/smissive/#j_path=/listApproved&amp;mydoc=approved";
	}else{//起草
		window.location.href = "${LUI_ContextPath}/km/smissive/#j_path=/listCreate&amp;mydoc=create";
	}
}
</script>