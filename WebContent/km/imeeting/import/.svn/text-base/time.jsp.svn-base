<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ page import="com.landray.kmss.km.imeeting.forms.KmImeetingMainForm"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
	Date now=new Date();
	Boolean isBegin=false,isEnd=false,isFeedBackDeadline = false,canEnter = false,isDoing=false;
	KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm)request.getAttribute("kmImeetingMainForm");
	//提前结束时间
	String earlyDate = kmImeetingMainForm.getFdEarlyFinishDate();
	if(kmImeetingMainForm.getFdHoldDate()!=null && kmImeetingMainForm.getFdFinishDate()!=null){
		// 会议已开始
		if (DateUtil.convertStringToDate(kmImeetingMainForm.getFdHoldDate(),
				ResourceUtil.getString("date.format.datetime")).getTime() < now.getTime()) {
			isBegin = true;
		}
		
		// 会议已结束
		if (DateUtil.convertStringToDate(kmImeetingMainForm.getFdFinishDate(),
				ResourceUtil.getString("date.format.datetime")).getTime() < now.getTime()){
			isEnd = true;
		}
		//提前结束的会议 显示已结束
		if(StringUtil.isNotNull(earlyDate) && (DateUtil.convertStringToDate(earlyDate,
				ResourceUtil.getString("date.format.datetime")).getTime() < now.getTime())) {
			isEnd = true;
		}

		//正在进行
		if((DateUtil.convertStringToDate(kmImeetingMainForm.getFdHoldDate(),
				ResourceUtil.getString("date.format.datetime")).getTime() <= now.getTime()) && (now.getTime()<= DateUtil.convertStringToDate(kmImeetingMainForm.getFdFinishDate(),
				ResourceUtil.getString("date.format.datetime")).getTime())){
			isDoing = true;
		}
		// 视频会议可入会
		if( "3".equals(kmImeetingMainForm.getDocStatusFirstDigit()) && (isEnd == false)){
			canEnter = true;
		}
	}
	if(kmImeetingMainForm.getFdFeedBackDeadline()!=null){
		// 回执截止时间
		if (DateUtil.convertStringToDate(kmImeetingMainForm.getFdFeedBackDeadline(),
				ResourceUtil.getString("date.format.datetime")).getTime() < now.getTime()) {
			isFeedBackDeadline = true;
		}
		request.setAttribute("isFeedBackDeadline", isFeedBackDeadline);
	} else {
		request.setAttribute("isFeedBackDeadline", "noFeedBackDeadline");
	}
	
	request.setAttribute("isBegin", isBegin);
	request.setAttribute("isEnd", isEnd);
	request.setAttribute("isDoing", isDoing);
	request.setAttribute("canEnter", canEnter);
	request.setAttribute("now", now.getTime());
%>