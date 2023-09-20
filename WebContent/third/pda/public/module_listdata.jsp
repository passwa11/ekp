<%@ include file="/resource/jsp/common.jsp"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%
	response.setHeader("pragma", "no-cache");
	response.setHeader("cache-control", "no-cache");
	response.setHeader("expires", "0");
	String appFlag="";
	if(PdaFlagUtil.checkClientIsPdaApp(request))
		appFlag="isAppflag=1";
	Page queryPage= (Page)request.getAttribute("queryPage");
	JSONObject viewObj=new JSONObject();
	int docCount=queryPage.getTotalrows();
	viewObj.accumulate("pageCount",queryPage.getTotal());//所有页数
	viewObj.accumulate("pageno",queryPage.getPageno());  //当前页码
	viewObj.accumulate("count",docCount);                //文档总数
	if(queryPage.getEnd()<docCount-1)
		viewObj.accumulate("nextPageStart",queryPage.getEnd()+1); //下页开始数
	JSONArray docArr=new JSONArray();
	for(Object modelObj:queryPage.getList()){
		String subject="";
		try{
			subject = (String)PropertyUtils.getProperty(modelObj,"docSubject");
		}catch(Exception e){
			try{
				subject = (String)PropertyUtils.getProperty( modelObj,"fdName");
			}catch(Exception e1){
				subject = "";
			}
		}
		JSONObject obj=new JSONObject();
		obj.accumulate("subject",subject);
		
		SysOrgElement docCreator = null;
		String docCreatorName="";
		try{
			docCreator = (SysOrgElement) PropertyUtils.getProperty( modelObj,"docCreator");
			docCreatorName = docCreator == null ?"":docCreator.getFdName();
		}catch(Exception e){
			try{
				docCreator = (SysOrgElement) PropertyUtils.getProperty( modelObj,"FdCreator");
				docCreatorName = docCreator == null ?"":docCreator.getFdName();
			}catch(Exception e1){
				docCreatorName = "";
			}
		}
		
		Date fdCreateTime = null;
		try{
			fdCreateTime = (Date) PropertyUtils.getProperty( modelObj,"docCreateTime");
		}catch(Exception e){
			try{
				fdCreateTime = (Date) PropertyUtils.getProperty(modelObj,"FdCreateTime");
			}catch(Exception e1){
				fdCreateTime = null;
			}
		}
		obj.accumulate("type","doc");
		obj.accumulate("summary",docCreatorName +" " + (fdCreateTime!=null?DateUtil.convertDateToString(fdCreateTime,DateUtil.PATTERN_DATETIME):""));
		String url = PdaFlagUtil.formatUrl(request, ModelUtil.getModelUrl(modelObj));
		url= url + (url.indexOf("?")>-1?"&":"?") +(StringUtil.isNull(appFlag)?"":appFlag);
		obj.accumulate("url",url);
		docArr.element(obj);
	}
	viewObj.accumulate("docs",docArr);
	pageContext.setAttribute("viewObj",viewObj.toString());
%>
${viewObj}
