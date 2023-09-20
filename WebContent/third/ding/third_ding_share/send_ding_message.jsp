<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="java.lang.String"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    //模块名称
   String name=request.getParameter("fdModelName");
   request.setAttribute("name", name);

   //主文档ID(和modelName对应)
   String id=request.getParameter("fdId");
   request.setAttribute("id", id);

   //推送到钉钉工作通知的地址，默认根据ModelName和id自动构建
   String url=request.getParameter("url");
   if(StringUtil.isNull(url)){
	   url="";
   }
   request.setAttribute("url",url);
   
   //主题标识
   String docSubject = request.getParameter("docSubject");
   if(StringUtil.isNull(docSubject)){
	   docSubject = "docSubject";
   }
   
   //推送图片的的 fd_key  (默认是Attachment)
  // String fdKey = request.getParameter("fdKey");
   //if(StringUtil.isNull(fdKey)){
	//   fdKey = "Attachment";
 //  }
   
   //推送内容  contentMap
   String contentMap = request.getParameter("contentMap");
   if(StringUtil.isNull(contentMap)){
	   contentMap = "";
   }
   String dingEnable = DingConfig.newInstance().getDingEnabled();
   
%>

<%
    //钉钉集成开启了才出现发送工作通知的按钮
   if(StringUtil.isNotNull(dingEnable)&& "true".equals(dingEnable)){
%>	
	<ui:button text="${lfn:message('third-ding:third.ding.send.work.notify')}" order="1" onclick="send2ding();">
    </ui:button>
	<script>
	function send2ding(){
        seajs.use([ 'sys/ui/js/dialog' ],function(dialog){
			dialog.build({
							config : {
								width : 650,
								height : 350,
								lock : true,
								cache : false,
								title : "${lfn:message('third-ding:third.ding.send.work.notify')}",
								content : {
									type : "iframe",
									url : "/third/ding/third_ding_share/send_dingMsg.jsp?fdModelName=<%=name%>&fdId=<%=id%>&docSubject=<%=docSubject%>&contentMap=<%=contentMap%>&url=<%=url%>"
								}
							},
							callback : function(value,
									dialog) {

							},
							actor : {
								type : "default",
								animate : false
							},
							trigger : {
								type : "AutoClose",
								timeout : 200
							}

						}).show();
	   });
   }

	</script>
<%   
   }
%>



