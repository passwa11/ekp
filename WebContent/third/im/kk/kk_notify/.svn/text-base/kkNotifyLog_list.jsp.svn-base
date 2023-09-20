<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("calendar.js|jquery.js");
</script>
<script type="text/javascript">

  function reloadFrame(){
			var rtnTime1 =$("#rtnTime1").val();
			var sendTime1 =$("#sendTime1").val();
			var rtnTime2 =$("#rtnTime2").val();
			var sendTime2 =$("#sendTime2").val();
			var fdSubject=$("#fdSubject").val();
			var path="${KMSS_Parameter_ContextPath}third/im/kk/kkNotifyLog.do?method=list";
			var params=[];
			if(rtnTime1){
			params.push("rtnTime1="+rtnTime1);
			}
			if(rtnTime2){
				params.push("rtnTime2="+rtnTime2);
				}
			if(sendTime1){
				params.push("sendTime1="+sendTime1);
				}
			if(sendTime2){
				params.push("sendTime2="+sendTime2);
				}
			if(fdSubject){
				params.push("fdSubject="+fdSubject);
				}
			if(params.length>0){
				path=path+"&"+params.join("&");
				}
			$("#listframe").attr("src",path);
			

	  }
</script>
<center>
<p>
<h3>查询</h3>
</p>
<html:form action="/third/im/kk/kkNotifyLog.do">
	<table class="tb_normal" width="98%">
		<tr>
			<td class="td_normal_title"><strong>
				<bean:message bundle="third-im-kk" key="kkNotifyLog.sendTime" /></strong></td>
			<td colspan="3"><input type="text" id="sendTime1" name="sendTime1" value="" class="inputsgl" subject="<bean:message bundle="third-im-kk" key="kkNotifyLog.sendTime" />" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('sendTime1');">选择</a>
				到
			<input type="text" id="sendTime2" name="sendTime2" value="" class="inputsgl" subject="<bean:message bundle="third-im-kk" key="kkNotifyLog.sendTime" />" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('sendTime2');">选择</a>
			之间
			</td>
		</tr>
		<tr>
		<td class="td_normal_title"><strong><bean:message
				bundle="third-im-kk" key="kkNotifyLog.rtnTime" /></strong></td>
			<td colspan="3"><input type="text" id="rtnTime1" name="rtnTime1" value="" class="inputsgl" subject="<bean:message bundle="third-im-kk" key="kkNotifyLog.sendTime" />" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('rtnTime1');">选择</a>
				到
				<input type="text" id="rtnTime2" name="rtnTime2" value="" class="inputsgl" subject="<bean:message bundle="third-im-kk" key="kkNotifyLog.sendTime" />" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('rtnTime2');">选择</a>
			之间
			</td>
		
		</tr>
		<tr>
			<td class="td_normal_title"><strong><bean:message
				bundle="third-im-kk" key="kkNotifyLog.fdSubject" /></strong></td>
			<td colspan="3">
			  <input style="width: 80%" type="text" id="fdSubject" name="fdSubject" value="" class="inputsgl" subject="<bean:message bundle="third-im-kk" key="kkNotifyLog.fdSubject" />"  /> 
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" colspan="4">
				<center><input type=button value="查询" onclick="reloadFrame()"></center>
			</td>
		</tr>
	</table>
	<div style="padding-top: 20px"><iframe id="listframe"
		src="${KMSS_Parameter_ContextPath}third/im/kk/kkNotifyLog.do?method=list"
		width=100% height=100% frameborder=0 scrolling=no> </iframe></div>

</html:form></center>
<%@ include file="/resource/jsp/view_down.jsp"%>