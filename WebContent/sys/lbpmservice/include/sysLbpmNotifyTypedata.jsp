<%@ page language="java" contentType="application/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page import="org.springframework.context.ApplicationContext
		,com.landray.kmss.framework.plugin.core.config.IExtension
		,com.landray.kmss.framework.service.plugin.Plugin
		,com.landray.kmss.util.StringUtil
		,org.springframework.web.context.support.WebApplicationContextUtils
		,com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmBaseInfo
		,com.landray.kmss.sys.lbpmservice.api.LbpmService
		,com.landray.kmss.sys.lbpm.engine.api.ProcessHolder
		,com.landray.kmss.sys.lbpm.engine.api.ProcessDefinitionHolder
		,com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition
		,com.landray.kmss.sys.lbpm.engine.builder.AbstractHandlerNode
		,com.landray.kmss.sys.lbpm.engine.builder.ProcessDefinition
		,com.landray.kmss.sys.notify.interfaces.ISysNotifyConfigService"%>

<%
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(session
					.getServletContext());
	LbpmService lbpmService = (LbpmService) ctx.getBean("lbpmService");
	String processId = org.apache.commons.lang.StringEscapeUtils.escapeHtml(request.getParameter("processId"));
	String nodeId = org.apache.commons.lang.StringEscapeUtils.escapeHtml(request.getParameter("nodeId"));
	ProcessHolder ph = lbpmService.load(processId);
	ProcessDefinition pd= ph.getProcessDefinitionInfo().getDefinition();
	NodeDefinition nd = pd.findActivity(nodeId);
	String notifyType="";
	if (nd instanceof AbstractHandlerNode) {
		notifyType = ((AbstractHandlerNode) nd).getNotifyType();
	}
	if (StringUtil.isNull(notifyType)) {
			notifyType = pd.getNotifyType();
	}
	if (StringUtil.isNull(notifyType)) {
		try {
			notifyType = new LbpmBaseInfo().getDefaultNotifyType();
		} catch (Exception e) {
		}
	}
	if (StringUtil.isNull(notifyType)) {
		notifyType = "todo";
	}

	String prevNotifyType = request.getParameter("notifyType");
	if (StringUtil.isNotNull(prevNotifyType)) {
		notifyType = prevNotifyType;
	}

	//System.out.println(notifyType);
%>
<%
	String EXTENSION_POINT = "com.landray.kmss.sys.notify";
	String ITEM_NAME = "notifyType";
	String PARAM_KEY = "key";
	String PARAM_NAME = "name";
	String PARAM_SERVICE = "service";
	IExtension[] extensions = Plugin.getExtensions(EXTENSION_POINT, "*",
			ITEM_NAME);
	StringBuffer notifyValues = new StringBuffer();
	StringBuffer notifyNames = new StringBuffer();
	String[] optionKeys = null;
	String[] optionValues = null;

	for (int i = 0; i < extensions.length; i++) {
		//获取配置中心的配置值，过滤已开启的扩展点
		ISysNotifyConfigService configService = Plugin.getParamValue(extensions[i],
				PARAM_SERVICE);
		if(configService !=null){
			boolean enable = configService.getNotifyType();
			if(!enable) continue;
		}
		// 获得通知方式的key与对应名称
		String key = (String) Plugin
				.getParamValue(extensions[i], PARAM_KEY);

		String name = (String) Plugin.getParamValue(extensions[i],
				PARAM_NAME);

		if (name != null
				&& (name.indexOf("{") != -1 || name.indexOf("}") != -1)) {
			name = name.substring(1, name.length() - 1);
		}
		notifyValues.append(key).append(";");
		notifyNames.append(name).append(";");
	}
	if (StringUtil.isNotNull(notifyValues.toString())
			&& StringUtil.isNotNull(notifyNames.toString())) {
		optionValues = notifyValues.substring(0, notifyValues.length() - 1)
				.split(";");
		optionKeys = notifyNames.substring(0, notifyNames.length() - 1)
				.split(";");
	}

	String fieldName = "__notify_type_4opr_" + nodeId;
	String hiddenId = fieldName + "_param";

	StringBuffer jsCode = new StringBuffer();
	jsCode.append("var fields=document.getElementsByName('" + fieldName
			+ "');");
	jsCode.append("var values='';");
	jsCode.append("for(var i=0; i<fields.length; i++) "
			+ "if(fields[i].checked) values+=';'+fields[i].value;");
	jsCode.append("if(values!='') document.getElementById('" + hiddenId
			+ "').value=values.substring(1);");
	jsCode.append("else document.getElementById('" + hiddenId
			+ "').value='';");

	//jsCode.append("alert(document.getElementById('" + hiddenId+ "').value)");

	StringBuffer sb = new StringBuffer();
	sb.append("<input type=hidden name='_notifyType_node' value='"+nodeId+"'>");
	String s="";int n=0;
	//if(optionValues.length>1){
		for (int i = 0; optionValues != null && i < optionValues.length; i++) {
			sb.append("<label class='lui-lbpm-checkbox'><input type='checkbox'  name='"+fieldName+"' value='"+optionValues[i]+"'");
			if(notifyType.indexOf(optionValues[i])!=-1){
				sb.append(" checked ");
				if(n>0){
					s+=";";
				}
				s+=optionValues[i];
				n++;
			}
			sb.append("onclick=\"" + jsCode + "\"  onchange = \"if(window.inputCheckboxChange){window.inputCheckboxChange(this);}\"><span class='checkbox-label'>"+optionKeys[i]+"</span></label>&nbsp;");
		}
	//}else if(optionValues.length==1){
	//	s="todo";
	//}
	sb.append("<input id='"+hiddenId+"' type=hidden name='_notifyType_"+nodeId+"' value='"+s+"'>");
	out.print(sb.toString());
	//System.out.println(sb.toString());
%>