<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat,java.util.Locale,com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil,com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,net.sf.json.JSONArray,net.sf.json.JSONObject,java.util.Date,java.util.Calendar,com.landray.kmss.sys.attend.util.AttendUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
		<%
			String isAcrossDay = request.getParameter("isAcrossDay");
			Date date = AttendUtil.getDate(new Date(), 0);
			if("true".equals(isAcrossDay)) {
				date = AttendUtil.getDate(new Date(), -1);
			}
			String dateStr = "";
			if("zh".equals(UserUtil.getKMSSUser(request).getLocale().getLanguage())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E",Locale.CHINA);
				dateStr = sdf.format(date);
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat(ResourceUtil.getString("date.format.date"), Locale.ENGLISH);
				SimpleDateFormat weekFormat = new SimpleDateFormat("E",Locale.ENGLISH);
				dateStr = dateFormat.format(date) + " " + weekFormat.format(date);
			}
			request.setAttribute("userId", UserUtil.getUser().getFdId());
			request.setAttribute("userName", UserUtil.getUser().getFdName());
			request.setAttribute("signDate", date.getTime());
		%>
		<div id="scrollView" data-dojo-type="mui/list/StoreScrollableView">
		    <li data-dojo-type="sys/attend/mobile/resource/js/list/AttendCategorySelect" 
		    	data-dojo-props='store:${HtmlParam.cateList},currentCategoryId:"${HtmlParam.cateId}"' class="muiEkpSubClockInTitle">
	      	</li>
		  	<li data-dojo-type="sys/attend/mobile/resource/js/list/AttendHeadTip" class="muiAttendBussTip"
		  		data-dojo-props='fdCategoryId:"${HtmlParam.cateId}"'></li>
		    <div class="muiEkpSubClockInHeader">
            	<div class="muiEkpSubHeadicon">
		          	<img class="muiImg" />
            	</div>
            	<ul>
	                <li>
	                    <p class="muiFontSizeMS">${userName }</p>
	                </li>
	                <li>
	                    <span class="muiFontSizeSS"><%=dateStr %></span>
	                </li>
            	</ul>
        	</div>
		    
		   	<input type="hidden" id="isRestDay" value="${HtmlParam.isRestDay}">
		   	<input type="hidden" id="isAcrossDay" value="${HtmlParam.isAcrossDay}">
		   	<input type="hidden" id="fdDeviceIds" value="${HtmlParam.fdDeviceIds}">
			<input type="hidden" id="fdWorkDate" value="${signDate}">

		    <c:if test="${HtmlParam.isRestDay=='true' }">
		    	<div class="restDayContent" style="background:#fff;height:16rem">
			    	<section>
			    		<div style="text-align:center;padding-top:1.5rem;">
				   	     	<img height=100 alt="" src="${LUI_ContextPath}/sys/attend/mobile/resource/image/nodata.png">
				   	     </div>
				    	<div style="text-align:center;color:#999999;font-size:12px;">${ lfn:message('sys-attend:mui.no.sign') }</div>
			    	</section>
			    </div>
		    </c:if>
		  
		    <section class="muiSignInPanel">
		    	<div class="muiSignInPanelBody muiSignInRecord">
					<ul data-dojo-type="mui/list/JsonStoreList"
		                data-dojo-mixins="sys/attend/mobile/resource/js/list/AttendItemListMixin" class="muiSignInflowList"
						data-dojo-props="isRestDay:'${HtmlParam.isRestDay }',nodataText:'<bean:message bundle="sys-attend" key="mui.no.sign"/>',nodataImg:'${LUI_ContextPath }/sys/attend/mobile/resource/image/nodata.png',url:'/sys/attend/sys_attend_main/sysAttendMain.do?method=listAttend&orderby=&categoryId=${HtmlParam.cateId}&signDate=${signDate }',lazy:false">
					</ul>
				</div>
		    </section>
		</div>
