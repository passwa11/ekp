<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.io.IOUtil"%>
<%@page import="com.landray.kmss.util.FileMimeTypeUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.util.ServerTypeUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.code.spring.SpringBean"%>
<%@page import="com.landray.kmss.sys.zone.model.SysZonePersonInfo"%>
<%@page import="com.landray.kmss.sys.attachment.util.ImageCropUtil"%>
<%@page import="com.landray.kmss.sys.zone.constant.SysZoneConstant"%>
<%@page import="com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%
	response.reset();
	if (StringUtil.isNotNull(request.getHeader("If-Modified-Since"))) {
		response.setStatus(304);
	} else {
		long expires = 7 * 24 * 60 * 60;
		long nowTime = System.currentTimeMillis();
		response.addDateHeader("Last-Modified", nowTime + expires);
		response.addDateHeader("Expires", nowTime + expires * 1000);
		response.addHeader("Cache-Control", "max-age=" + expires);

		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		String size = request.getParameter("size");
		String personId = request.getParameter("personId");
		List list = null;
		if(StringUtil.isNull(personId)) {
			// 默认取当前用户id
			personId = UserUtil.getKMSSUser().getUserId();
		}else{
			if(personId.indexOf("w")>-1){
				personId = personId.substring(0,personId.indexOf("w"));			
			}
			String fdKey = SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[0];
			if (SysZoneConstant.MEDIUM_PHOTO.equals(size)) {
				fdKey = SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[1];
			} else if (SysZoneConstant.SMALL_PHOTO.equals(size)) {
				fdKey = SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[2];
			}
			String modelName = SysZonePersonInfo.class.getName();
			list = sysAttMainService.findByModelKey(modelName, personId, fdKey);
			//旧的key值
			if (list.isEmpty()) {
				list = sysAttMainService.findByModelKey(modelName, personId, "zonePersonInfo");
			}
		}
		if (list==null || list.isEmpty()) {
			String defaultHeadImageUrl = PersonInfoServiceGetter.getPersonDefaultHeadImageUrl(personId);
			response.setContentType(FileMimeTypeUtil.getContentType(defaultHeadImageUrl));
			request.getRequestDispatcher(defaultHeadImageUrl).forward(request, response);
		} else {
			SysAttMain sysAtt = (SysAttMain) list.get(0);
			response.setContentType(FileMimeTypeUtil.getContentType(sysAtt.getFdFileName()));
			IOUtil.write(sysAttMainService.getInputStream(sysAtt),response.getOutputStream());
		}
		if(ServerTypeUtil.getServerType()!=ServerTypeUtil.WEBLOGIC){
			out.clear();  
			out = pageContext.pushBody(); 
		}
	}
%>