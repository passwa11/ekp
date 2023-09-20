<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.File,
	com.landray.kmss.sys.appconfig.model.BaseAppConfig
	" %>
	
	<%
		String prefixDing = "";
		try {
			boolean dingExist = new File(request.getRealPath("/third/ding")).exists();
			if (dingExist) {
				BaseAppConfig dingConfig = (BaseAppConfig) Class.forName("com.landray.kmss.third.ding.model.DingConfig")
						.newInstance();
				if ("true".equals(dingConfig.getDataMap().get("dingEnabled"))) {
					prefixDing = "ding";
				}
			}
			boolean ldingExist = new File(request.getRealPath("/third/lding")).exists();
			if (ldingExist) {
				BaseAppConfig ldingConfig = (BaseAppConfig) Class
						.forName("com.landray.kmss.third.lding.model.LdingConfig").newInstance();
				if ("true".equals(ldingConfig.getDataMap().get("ldingEnabled"))) {
					prefixDing = "lding";
				}
			}
			boolean govdingExist = new File(request.getRealPath("/third/govding")).exists();
			if (govdingExist) {
				BaseAppConfig govdingConfig = (BaseAppConfig) Class
						.forName("com.landray.kmss.third.govding.model.GovDingConfig").newInstance();
				if ("true".equals(govdingConfig.getDataMap().get("dingEnabled"))) {
					prefixDing = "govding";
				}
			}
			request.setAttribute("prefixDing", prefixDing);
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
<script type="text/javascript">
	var dingConfig = {
		prefixDing : '${prefixDing}'
	}
</script>