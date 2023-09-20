<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileThirdUtils"%>
<%
	request.setAttribute("____third___module___", MobileThirdUtils.getSignInfo(request));
%>
<script type="text/javascript">
var module = ${____third___module___};
module = module || {};
if(module['file']){
	require(['mui/util'],function(util){
		var url = util.formatUrl( module['file'] );
		require([url],function(third){
			if(third && third.init){
				third.init();
			}
		});
	});
}
</script>
