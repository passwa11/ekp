<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
(function(){
	var path="/sys/lbpmservice/node/votenode/";
	lbpm.globals.includeFile(path+"operation_handler_agree.js");
	lbpm.globals.includeFile(path+"operation_handler_disagree.js");
	lbpm.globals.includeFile(path+"operation_handler_abstain.js");
})();

//定义常量
(function(constant){
})(lbpm.constant);

</script>