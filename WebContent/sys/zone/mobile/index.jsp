<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page import="com.landray.kmss.sys.zone.util.SysZoneConfigUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java"  import="com.landray.kmss.util.StringUtil" %>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<bean:message bundle="sys-zone" key="sysZonePerson.address.list" />
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-zone.js"/>
		<mui:min-file name="mui-zone-list.css"/>
	</template:replace>
	<template:replace name="content">
		<%
			SysOrgPerson user = UserUtil.getUser(request);
			if(user.getFdParent()!=null){
				pageContext.setAttribute("parent",user.getFdParent());
			}
		%>
	<script>
	var ORG_TYPE_ORG = 0x1; // 机构
	var ORG_TYPE_DEPT = 0x2; // 部门
	var ORG_TYPE_POST = 0x4; // 岗位
	var ORG_TYPE_PERSON = 0x8; // 个人
	var ORG_TYPE_GROUP = 0x10; // 群组
	var ORG_TYPE_ROLE = 0x20;
	var ORG_TYPE_ORGORDEPT = ORG_TYPE_ORG | ORG_TYPE_DEPT; // 机构或部门
	var ORG_TYPE_POSTORPERSON = ORG_TYPE_POST | ORG_TYPE_PERSON; // 岗位或个人
	var ORG_TYPE_ALLORG = ORG_TYPE_ORGORDEPT | ORG_TYPE_POSTORPERSON; // 所有组织架构类型
	var ORG_TYPE_ALL = ORG_TYPE_ALLORG | ORG_TYPE_GROUP; // 所有组织架构类型+群组
	var ORG_FLAG_AVAILABLEYES = 0x100; // 有效标记
	var ORG_FLAG_AVAILABLENO = 0x200; // 无效标记
	var ORG_FLAG_AVAILABLEALL = ORG_FLAG_AVAILABLEYES | ORG_FLAG_AVAILABLENO; // 包含有效和无效标记
	var ORG_FLAG_BUSINESSYES = 0x400; // 业务标记
	var ORG_FLAG_BUSINESSNO = 0x800; // 非业务标记
	var ORG_FLAG_BUSINESSALL = ORG_FLAG_BUSINESSYES | ORG_FLAG_BUSINESSNO; // 包含业务和非业务标记

	var currrtPersonId = "${KMSS_Parameter_CurrentUserId}";
	</script>
	
	
	<%
		JSONArray commmunicateList = SysZoneConfigUtil.getCommnicateList();
		JSONArray extendContact = new JSONArray();
	     for(Object map: commmunicateList) { 
	    	 JSONObject json = (JSONObject)map;
	    	 if(!"communicate_mobile".equals(json.get("showType"))) {
     	           	continue;
     	      }
	    	 JSONObject _json = new JSONObject();
	    	 _json.put("id" , json.get("unid")); 
	    	 _json.put("icon" ,json.get("icon")); 
	    	 _json.put("order" ,json.get("order"));
	    	 String text = json.get("text") != null ? json.get("text").toString() : "";
	    	 if(StringUtil.isNotNull(text)) {
	    		 String msgText = ResourceUtil.getString(text);
	    		 if(StringUtil.isNotNull(msgText)) {
	    			 text = msgText;
	    		 }
	    	 }
	    	 _json.put("text", text);
	    	 String href = json.getString("href");
	    	 String path = "";
    		 String key = (String)json.get("server"); 
    		 String localKey = SysZoneConfigUtil.getCurrentServerGroupKey();
    		 if(StringUtil.isNotNull(key) && !key.equals(localKey)) { 
           		path = SysZoneConfigUtil.getServerUrl(key);
           	 } else {
           		path = request.getContextPath();
           	 }
    		 _json.put("href", path + href);
    		 _json.put("replace","personId");
	    	 extendContact.add(_json);
	     }
	    
	%>
	<script>
		window.extendContact = <%=extendContact%>;
	</script>
	
	<div data-dojo-type="mui/category/CategoryNavInfo" data-dojo-props="key:'zoneAdress'"></div>
	
	<div id='_address_sgl_view_zoneAdress' data-dojo-type="dojox/mobile/ScrollableView"
		 data-dojo-mixins="mui/category/_ViewScrollResizeMixin" data-dojo-props="scrollBar:false,threshold:100,key:'zoneAdress'">
		 
		<%--  搜索工具   --%>
		<div id='_address_sgl_search_zoneAdress' data-dojo-type="sys/zone/mobile/js/address/AddressZoneSearchBar" 
			data-dojo-props="orgType:ORG_TYPE_PERSON,key:'zoneAdress',height:'3.8rem',searchUrl:'/sys/zone/mobile/address/sysZoneAddress.do?method=searchList&keyword=!{keyword}&orgType=!{orgType}'">
		</div>
		
		<%--  面包屑导航 (支持文字过长横向滑动)   --%>
		<div data-dojo-type="dojox/mobile/ScrollableView" data-dojo-props="scrollDir:'h',height:'3rem',scrollBar:false" class="mui_zone_list_breadcrumb_vav_sv" >
		  <div data-dojo-type="sys/zone/mobile/js/address/AddressBreadcrumbNav" data-dojo-props="curId:'${parent.fdId}',curName:'${parent.fdName}',key:'zoneAdress'"></div>
		</div>
		
		<%-- 组织、人员列表   --%>
		<ul data-dojo-type="sys/zone/mobile/js/address/AddressZoneList" data-dojo-mixins="sys/zone/mobile/js/address/AddressZoneItemListMixin,sys/zone/mobile/js/_AddressZoneListContactMixin"
			data-dojo-props="key:'zoneAdress',dataUrl:'/sys/zone/mobile/address/sysZoneAddress.do?method=addressList&parentId=!{parentId}&orgType=!{selType}'">
		</ul>
		
	</div>

	</template:replace>
</template:include>

