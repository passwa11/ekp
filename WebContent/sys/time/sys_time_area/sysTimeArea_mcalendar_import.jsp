<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="java.util.*"%>
<script type="text/javascript">
	Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.js|optbar.js");
</script>
<script type="text/javascript">

	//初始化
	window.onload=function(){
		var div_success=document.getElementById("div_successCount");
		var div_ignore=document.getElementById("div_ignoreCount");
		var div_failure=document.getElementById("div_failureCount");
		if(div_success.innerHTML==""){
			div_success.innerHTML="0";
		}
		if(div_ignore.innerHTML==""){
			div_ignore.innerHTML="0";
		}
		if(div_failure.innerHTML==""){
			div_failure.innerHTML="0";
		}
	};

	//检查是否有文件上传
	function checkFile(){
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			alert("<bean:message bundle='sys-time' key='sysTimeArea.import.file.required'/>");
			return false ;
		}
		//添加参数:是否更新存在数据
		var form=document.getElementsByName("sysTimeAreaForm")[0];
		document.getElementsByName("submit")[0].disabled ="disabled";
		return true ;
	}

	//展开出错列表
	function showMoreErrInfo(srcImg){
		var obj = document.getElementById("div_errorCell");
		if(obj.style.display=="none"){
			obj.style.display="block";
			srcImg.src = Com_Parameter.StylePath + "msg/minus.gif";
		}else{
			obj.style.display="none";
			srcImg.src = Com_Parameter.StylePath + "msg/plus.gif";
		}
	}

	//改变上传附件,重置导出结果
	function resetResult(){
		var uploadProcess=document.getElementById("div_uploadProcess");
		uploadProcess.innerHTML="<bean:message bundle='sys-time' key='sysTimeArea.title.uploadNotDo'/>";
		document.getElementById("div_successCount").innerHTML="0";
		document.getElementById("div_ignoreCount").innerHTML="0";
		document.getElementById("div_failureCount").innerHTML="0";
		document.getElementById("div_errorCell").innerHTML="";
	}
	
</script>
<html:form action="/sys/time/sys_time_area/sysTimeArea.do?method=importExcel&fdId=${JsParam.fdId}" 
	enctype="multipart/form-data" onsubmit="return checkFile();">
	<p class="txttitle">
		<bean:message bundle="sys-time" key="sysTimeArea.import.title" />
	</p>
	<br />
	<center>
		<table class="tb_normal" width=60%>
			<tr>
				<td  width=15%>
					<bean:message bundle="sys-time" key="sysTimeArea.file" />
				</td>
				<td width=85%>
					<html:file property="file" style="width:70%" styleClass="inputsgl" onchange="resetResult();"/>&nbsp;&nbsp;
				
				</td>
			</tr>
			
			<%-- <tr>
				<td  width=15%>
					<bean:message bundle="sys-time" key="sysTimeArea.title.select" />
				</td>
				<td width=15%>
					<label><input type="checkbox" name="isUpdate"  /></label>
					<bean:message bundle="sys-time" key="sysTimeArea.tip.select" />
				</td>
			</tr> --%>
			
			<tr><td colspan="2">&nbsp;</td></tr>
			
			<tr>
				<td colspan="2" >
					<bean:message bundle="sys-time" key="sysTimeArea.tip.upload" />
				</td>
			</tr>
			
			<tr><td colspan="2">&nbsp;</td></tr>
			
			<tr>
				<td colspan="2">
					<bean:message bundle="sys-time" key="sysTimeArea.tip.uploadResult" /><br />
					<table class="tb_noborder" width="90%">
						<tr height="25px">
							<td class="msglist" colspan="3">
								<bean:message bundle="sys-time" key="sysTimeArea.title.uploadProcess"/>
								<span id="div_uploadProcess">
									<c:if test="${empty result}">
										<bean:message bundle="sys-time" key="sysTimeArea.title.uploadNotDo"/>
									</c:if>
									<c:if test="${not empty result}">
										<font color="red"><bean:message bundle="sys-time" key="sysTimeArea.title.uploadFinish"/></font>
									</c:if>
								</span>
							</td>
						</tr>
						<tr height="25px">
							<td class="msglist">
								<bean:message bundle="sys-time" key="sysTimeArea.title.successCount"/>
								<span id="div_successCount">${result.successCount}</span>
							</td>
							<td class="msglist" align="left">
									<bean:message bundle="sys-time" key="sysTimeArea.title.ignoreCount"/>
									<span id="div_ignoreCount">${result.ignoreCount}</span>
							</td>
							<td class="msglist" align="left">
									<bean:message bundle="sys-time" key="sysTimeArea.title.failCount"/>
									<span id="div_failureCount">${result.failCount}</span>
							</td>
						</tr>
						<tr height="25px">
							<td class="msglist" colspan="2">
								<image src='${KMSS_Parameter_StylePath}msg/plus.gif' onclick='showMoreErrInfo(this);' style='cursor:pointer'>
								<bean:message bundle="sys-time" key="sysTimeArea.title.detail"/>
								<br>
								<div id="div_errorCell" style="display:none;margin-left: 20px;width:80%;">
									<c:forEach items="${result.errorMsgs}" var="errorMsg" varStatus="vstatus">
										${errorMsg.value}<br />
									</c:forEach>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		
		</table>
		<center>
			<input name="submit" type="submit" value="<bean:message bundle="sys-time" key="sysTimeArea.btn.upload" />">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="<bean:message key="button.close"/>" onclick="window.close();">
		</center>
	</center>
	<html:hidden property="method_GET" />
	<html:hidden property="fdId" />
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>