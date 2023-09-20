<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.lbpmservice.handsignture.JgIWebRevisionUtil"%>
<%
	request.setAttribute("downloadUrl",JgIWebRevisionUtil.getJGIWebRevisionDownLoadUrl());
	request.setAttribute("version",JgIWebRevisionUtil.getJGIWebRevisionVersion());
%>
<OBJECT id="${param.iWebRevisionObjectId }" name="iWebRevisionObject" classid="clsid:2294689C-9EDF-40BC-86AE-0438112CA439" codebase="<c:url value='${downloadUrl}'/>#version=<c:url value='${version}'/>" width="100%" height="100px">
   <param name="WebUrl" value="${KMSS_Parameter_ContextPath }sys/lbpmservice/signature/handSignture/iWebServer.jsp">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
   <param name="RecordID" value="${param.recordID }">    <!-- RecordID:本文档记录编号 -->
   <param name="FieldName" value="${param.fieldName }">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
   <param name="UserName" value="${param.userName }">    <!-- UserName:签名用户名称 -->
   <param name="PenColor" value="#FF0000">     <!-- PenColor:笔的颜色，采用网页色彩值  默认值:#000000  -->
   <param name="BorderStyle" value="0">    <!-- BorderStyle:边框，0:无边框 1:有边框  默认值:1  -->
   <param name="EditType" value="0">    <!-- EditType:默认签章类型，0:签名 1:文字  默认值:0  -->
   <param name="ShowPage" value="1">    <!-- ShowPage:设置默认显示页面，0:电子印章,1:网页签批,2:文字批注  默认值:0  -->
   <param name="PenWidth" value="2">     <!-- PenWidth:笔的宽度，值:1 2 3 4 5   默认值:2  -->
   <param name="FontSize" value="11">    <!-- FontSize:文字大小，默认值:11 -->
   <param name="SignatureType" value="0">    <!-- SignatureType:签章来源类型，0表示从服务器数据库中读取签章，1表示从硬件密钥盘中读取签章，2表示从本地读取签章，并与ImageName(本地签章路径)属性相结合使用  默认值:0 -->
   <param name="InputList" value="同意\r\n不同意\r\n请上级批示\r\n已阅">
</OBJECT>

