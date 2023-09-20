<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@ page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%
	List allSysAttMains  = new ArrayList();
	List sysAttMains = new ArrayList();
	int attCount  = 0;
	try{
		String _modelName = request.getParameter("fdModelName");
		String _modelId = request.getParameter("fdModelId");
		String _key = request.getParameter("fdKey");
		if(StringUtil.isNotNull(_modelName) 
				&& StringUtil.isNotNull(_modelId)){
			String cacheKey = _modelName + "_" + _modelId;
			List cacheAtts = (List)request.getAttribute(cacheKey);
			if(cacheAtts!=null && !cacheAtts.isEmpty()){
				allSysAttMains = cacheAtts;
			}else{
				String caheFlag = (String)request.getAttribute(cacheKey+"_flag");
				if(!"1".equals(caheFlag)){
					ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
					allSysAttMains = ((ISysAttMainCoreInnerDao) sysAttMainService.getBaseDao()).findAttListByModel(_modelName,_modelId);
				}else{
					allSysAttMains = new ArrayList();
				}
			}
			//附件个数
			for(int i = 0;i < allSysAttMains.size();i++){
				SysAttMain s = (SysAttMain)allSysAttMains.get(i);
				if(_key.equals(s.getFdKey())){
					attCount++;
					sysAttMains.add(s);
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	JSONArray array = new JSONArray();
	for(int i = 0; i < sysAttMains.size(); i++){
		JSONObject json = new JSONObject();
		SysAttMain sysAttMain = (SysAttMain)sysAttMains.get(i);
		json.put("fdId",sysAttMain.getFdId());
		json.put("name", sysAttMain.getFdFileName());
		json.put("size",sysAttMain.getFdSize());
		json.put("type",sysAttMain.getFdContentType());
		boolean canDownload = false;
		String href = "/sys/attachment/sys_att_main/sysAttMain.do?method=viewDownload&fdId=" + sysAttMain.getFdId();
		if(UserUtil.checkAuthentication("/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="+sysAttMain.getFdId(), "GET")){
			href = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="+sysAttMain.getFdId();
			canDownload = true;
		}
		json.put("canDownload", canDownload);
		json.put("href", href);
		json.put("thumb","/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=" + sysAttMain.getFdId());
		json.put("viewPicHref","/third/pda/attdownload.jsp?open=1");
		boolean hasViewer = false;
		if (SysAttViewerUtil.isConverted(sysAttMain)
				&& sysAttMain.getFdContentType().indexOf("video") < 0
				|| sysAttMain.getFdContentType().indexOf("mp4") >= 0||
				sysAttMain.getFdContentType().indexOf("audio") >= 0) {
			hasViewer = false;
		}
		json.put("hasViewer",hasViewer);
		boolean canEdit = false;
		if(UserUtil.checkAuthentication("/sys/attachment/sys_att_main/sysAttMain.do?method=edit&v=true&fdId="+sysAttMain.getFdId(), "GET")){
			canEdit = true;
		}
		json.put("canEdit",canEdit);
		boolean canRead = false;
		if(UserUtil.checkAuthentication("/sys/attachment/sys_att_main/sysAttMain.do?method=read&fdId="+sysAttMain.getFdId(), "GET")){
			canRead = true;
		}
		json.put("canRead",canRead);
		array.add(json);
	}
	out.print(array.toString());
%>