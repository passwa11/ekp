<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.lang.String" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%  
    String dingPortalUrlPc =StringUtil.formatUrl("/km/review/km_review_ui/dingSuit/moduleindex.jsp?nav=/km/review/tree_ding.jsp&ddtab=true&showTopBar=true");
    //获取后台配置的高级版后台跳转url，setAttribute使js能取到
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map orgMap = sysAppConfigService.findByKey("com.landray.kmss.third.ding.model.DingConfig");
	if (orgMap != null && orgMap.containsKey("dingPortalUrl")) {
		String dingPortalUrl = orgMap.get("dingPortalUrl")+"";
		if(StringUtil.isNotNull(dingPortalUrl)){
			dingPortalUrlPc = StringUtil.formatUrl(dingPortalUrl);
		}
	}
	response.sendRedirect(dingPortalUrlPc);
%>
