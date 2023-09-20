<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,
	com.landray.kmss.util.*,
	com.landray.kmss.third.ding.oms.DingOmsConfig,
	com.landray.kmss.third.ding.model.OmsRelationModel,
	com.landray.kmss.third.ding.service.IOmsRelationService
	" %>

<center>
<%
String type = request.getParameter("type");
if("andrawu".equals(type)){
		
		IOmsRelationService	omsRelationService = (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
		List list = omsRelationService.findList(null, null);
		for (int i = 0; i < list.size(); i++) {
			OmsRelationModel model = (OmsRelationModel) list.get(i);
			omsRelationService.delete(model);
		}

		DingOmsConfig dingOmsConfig = new DingOmsConfig();
		dingOmsConfig.setLastUpdateTime(null);
		dingOmsConfig.save();
		
		out.print("清除OK");
}else{
%>

<form method="post">
<input type="password" name="type">
<input type="submit" value="提交">
</form>
<%
}
	
%>
</center>
