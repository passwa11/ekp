<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ page language="java" import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" import="com.landray.kmss.sys.zone.service.ISysZonePersonInfoService"%>
<%@ page language="java" import="com.landray.kmss.sys.zone.model.SysZoneNavigation"%>
<%@ page language="java" import="com.landray.kmss.sys.zone.model.SysZoneNavLink"%>
<%@ page language="java" import="net.sf.json.JSONArray" %>
<%@ page language="java" import="net.sf.json.JSONObject" %>
<%@ page language="java" import="java.util.List"%>
<%@ page language="java" import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" import="com.landray.kmss.common.dao.HQLInfo"%>
<%@ page language="java" import="com.landray.kmss.common.service.IBaseService"%>

<%@page language="java" import="com.landray.kmss.util.StringUtil" %>
<%@page language="java" import="java.util.ArrayList" %>

<%@page language="java" import="com.landray.kmss.sys.profile.util.ProfileMenuUtil" %>

<%	
	String taText = request.getParameter("zone_TA_text");
	ISysZonePersonInfoService service = 
			(ISysZonePersonInfoService)SpringBeanUtil.getBean("sysZonePersonInfoService");
	List<SysZoneNavigation> sysZoneNavigationList = service.getZonePersonNav("mobile");
	JSONArray rtn = new JSONArray();
	String frontpageText = ResourceUtil.getString("sysZonePerson.4m.frontpage", "sys-zone");
	rtn = JSONArray.fromObject("[{'id':'home','text': '"+ frontpageText +"'  }]");

	List<SysZoneNavLink> navList = new ArrayList<SysZoneNavLink>();
	if(ProfileMenuUtil.moduleExist("/kms/lecturer")){//有讲师模块
		String fdId = request.getParameter("fdId");
	   	Boolean isLecturer = false;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmsLecturerMain.fdPerson.fdId = :userId");
		hqlInfo.setParameter("userId",fdId);
		IBaseService kmsLecturerMainService = (IBaseService) SpringBeanUtil
			.getBean("kmsLecturerMainService");
		List  lecturerMainList =  kmsLecturerMainService.findList(hqlInfo);
		if(lecturerMainList!=null&&!lecturerMainList.isEmpty()){
			isLecturer = true;
		}
		
	 	if(isLecturer){//该用户为讲师
			
        	if(ProfileMenuUtil.moduleExist("/kms/train")){//有线下培训模块
            	//授课记录
            	SysZoneNavLink kmsLecturerTrain = new SysZoneNavLink();
            	kmsLecturerTrain.setFdName(ResourceUtil.getString("kms-lecturer:kmsLecturerTrain"));
            	String fdUrl = "/kms/lecturer/mobile/kms_lecturer_navigation/kmsLecturerNavigation_zone_train.jsp?fdId="+fdId;
            	kmsLecturerTrain.setFdUrl(fdUrl);
            	kmsLecturerTrain.setFdTarget("_self");
            	navList.add(kmsLecturerTrain);
        	}
        	
           	if(ProfileMenuUtil.moduleExist("/kms/learn")){
            	//在线课程
            	SysZoneNavLink kmsLecturerCourse = new SysZoneNavLink();
            	kmsLecturerCourse.setFdName(ResourceUtil.getString("kms-lecturer:kmsLecturerCourse"));
            	String fdUrl = "/kms/lecturer/mobile/kms_lecturer_navigation/kmsLecturerNavigation_zone_course.jsp?fdId="+fdId;
            	kmsLecturerCourse.setFdUrl(fdUrl);
            	kmsLecturerCourse.setFdTarget("_self");
            	navList.add(kmsLecturerCourse);
           	}


		}
	}
	if (sysZoneNavigationList != null && !sysZoneNavigationList.isEmpty()) {
		navList.addAll(sysZoneNavigationList.get(0).getFdLinks());
	}
	//System.out.println(navList);

	if(navList!=null&&!(navList.isEmpty())){
		for(SysZoneNavLink nav : navList) {
			JSONObject obj = new JSONObject();
			obj.put("id", nav.getFdId());
			obj.put("target" , nav.getFdTarget());
			obj.put("url", nav.getFdUrl());
			obj.put("key", nav.getFdServerKey());
			obj.put("isUserDef", nav.getFdIsUserDef());
			String text = nav.getFdName();
			if(StringUtil.isNotNull(taText)) {
				if(StringUtil.isNotNull(text)&&!"TA".equals(text) && text.indexOf("TA") > -1) {
					text = text.replace("TA",taText );
				}
			}
			obj.put("text",text);
			rtn.add(obj);
		}
	}
	out.print(rtn.toString());
%>