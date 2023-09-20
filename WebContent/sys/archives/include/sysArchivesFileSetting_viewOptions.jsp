<%@page import="java.util.HashMap"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	String type = request.getParameter("type");
	String selected = request.getParameter("selected");
	String modelName = request.getParameter("modelName");
	String templateService = request.getParameter("templateService");
	String templateId = request.getParameter("templateId");
	String otherModelName = request.getParameter("otherModelName");
	ISysArchivesFileTemplateService service = (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
	if(StringUtil.isNotNull(otherModelName)){
		String[] otherModelNames = otherModelName.split("[,;]");
		String[] modelNames = new String[otherModelNames.length + 1];
		modelNames[0] = modelName;
		for (int i = 1; i < modelNames.length; i++) {
			modelNames[i] = otherModelNames[i - 1];
		}
		boolean isSelected = false;
		for (int i = 0; i < modelNames.length; i++) {
			String name = modelNames[i];
			SysDictModel dict = SysDataDict.getInstance().getModel(ModelUtil.getModelClassName(name));
			String messageKey = dict.getMessageKey();
			if(StringUtil.isNotNull(messageKey)){
				messageKey = ResourceUtil.getString(messageKey, request.getLocale());
			}
			Map<String, String> map = new HashMap<String, String>();
			if(name.equals(modelName)){
				map = service.getOptions(name, type, templateService, templateId);
			} else {
				map = service.getOptions(name, type, null, null);
			}
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				if((name+":"+key).equals(selected)) {
					out.println(messageKey+" - "+map.get(key));
					isSelected = true;
					break;
				}
			}
			if(isSelected){
				break;
			}
		}
	} else {
		Map<String, String> map = service.getOptions(modelName, type, templateService, templateId);
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if(key.equals(selected)) {
				out.println(map.get(key));
				break;
			}
		}
	}
%>