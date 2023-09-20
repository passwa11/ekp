<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.third.mall.service.IThirdMallAuthorizeService,com.landray.kmss.third.mall.model.ThirdMallAuthorize"%>
<%@ page import="java.util.List,com.landray.kmss.sys.config.util.LicenseUtil,com.landray.kmss.sys.cluster.util.NetUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.third.mall.util.MallUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	IThirdMallAuthorizeService authService = (IThirdMallAuthorizeService) SpringBeanUtil.getBean("thirdMallAuthorizeService");
	String customer = LicenseUtil.get("license-customer-id");
	String corpID = MallUtil.getCustomerId();
	String fdClientName = LicenseUtil.get("license-to");
	String fdEnabel = "";
	String fdBusKeys = "";
	String msg = "";
	if (StringUtil.isNull(customer)) {
		if (StringUtil.isNull(fdClientName)) {
			fdClientName = MallUtil.getMd5Name();
		}
		msg = "<span style='color:red;'>（"+
				ResourceUtil.getString("kmReuseCommon.noClientRight","third-mall")
				+"，IP: " + NetUtil.getLocalAddress() + " ）</span>";
	}
	List<ThirdMallAuthorize> list = authService.findClientAuthorize(corpID);
	if(!list.isEmpty()){
		ThirdMallAuthorize auth = list.get(0);
		fdBusKeys = auth.getFdBusKeys();
		fdEnabel = auth.getFdEnable();
	}
	request.setAttribute("__fdClientId", corpID);
	request.setAttribute("__fdMallEnable", fdEnabel);
	request.setAttribute("__fdBusKeys", fdBusKeys);
	request.setAttribute("_fdClientName", fdClientName);
	request.setAttribute("_msg", msg);
%>