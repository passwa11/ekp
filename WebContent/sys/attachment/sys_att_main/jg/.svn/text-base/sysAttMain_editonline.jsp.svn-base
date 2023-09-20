<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.forms.SysAttMainForm"%>
<%-- 加载扩展信息 --%>
<% com.landray.kmss.sys.attachment.util.PluginUtil.getInstance().setEditOnline(request); %>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckEdit_js.jsp"%>

<c:set var="fdAttMainId" value="${sysAttMainForm.fdId}" scope="request"></c:set>
<%@ include file="/sys/attachment/sys_att_main/jg/sysAttMain_CheckSupport.jsp"%>

<%
	SysAttMainForm attMainFrm = (SysAttMainForm)request.getAttribute("sysAttMainForm");
	String fdFileName = attMainFrm.getFdFileName();
	pageContext.setAttribute("p_currentPerson", UserUtil.getKMSSUser().getUserId());
	pageContext.setAttribute("p_fdFileName", fdFileName);
%>

<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("jg_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<script>Com_IncludeFile("data.js|json2.js");</script>
	<div id="optBarDiv">
		<table class="tb_noborder">
		<tr><td id="_button_${sysAttMainForm.fdKey}_JG_Attachment_TD"></td>
		</tr>
		</table>
		<input type=button value="<bean:message key="button.update"/>"
				onclick="return Attach_EditOnlineSubmit();"/>
		<script>
		var curUserId = "${p_currentPerson}";
		var curFileName = "${p_fdFileName}";
		function Attach_EditOnlineSubmit() {
			
			//如果是从合同模块进入此页面，判断父页面是否已经关闭
			if ('com.landray.kmss.km.agreement.model.KmAgreementApply' == jg_attachmentObject_editonline.fdModelName
					&& 'mainOnline' == jg_attachmentObject_editonline.fdKey) {
				var checkErrorFlag = false;
				if (window.opener && window.opener.closed) {
					checkErrorFlag = true;
				} else if (window.opener == null) {
					checkErrorFlag = true;
				}
				if (checkErrorFlag) {
		     		seajs.use('lui/dialog', function(dialog) {
			     		dialog.alert("<bean:message bundle='sys-attachment' key='sysAttachment.agreement.parent.window.close'/>",function() {
			    			Com_Parameter.CloseInfo = null;
			    			window.close();
			     		});
			     	});
		     		return false;
				} else {
					//通知父页面
					if (window.opener.refreshKmAgreementApplyMain) {
						window.opener.refreshKmAgreementApplyMain();
					}
				}
			}

			//提交表单校验
			for(var i=0; i<Com_Parameter.event["submit"].length; i++){
				if(!Com_Parameter.event["submit"][i]()){
					return false;
				}
			}
			//提交表单消息确认
			for(var i=0; i<Com_Parameter.event["confirm"].length; i++){
				if(!Com_Parameter.event["confirm"][i]()){
					return false;
				}
			}
			Com_Parameter.CloseInfo = null;
			window.close();
			return true;
		}
		</script>
		<input type="button" value="<bean:message key="button.close"/>"
			onclick="closeWin();">
	</div>
	<%-- 加载扩展JSP片断 --%>
	<c:forEach items="${editOnlineMap['jsp']}" var="jsp">
		<c:import url="${jsp}" charEncoding="UTF-8">
			<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
			<c:param name="fdAttType" value="office" />
			<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
			<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
			<c:param name="editMode" value="2" />		
			<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
			<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
		</c:import>	
	</c:forEach>
	<table class="tb_normal" width=100% height="95%" style="margin-top: -10px;">
		<tr>
		<td valign="top">
		  <%--提示当前在线编辑用户的div--%>
		  <div id="warnDiv">
          </div>
		<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_OCX.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
			<c:param name="fdAttType" value="office" />
			<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
			<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
			<c:param name="editMode" value="2" />		
			<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
			<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
			<c:param name="canPrint" value="${canPrint}" />
			<c:param name="attHeight" value="${not empty HtmlParam.attHeight?HtmlParam.attHeight:'100%'}" />
			<c:param name="trackRevisions" value="1" />			
		</c:import>			
		</td>		
		</tr>
	</table>
	<script type="text/javascript">
		Com_SetWindowTitle("${fdFileName}");
  		var url = window.location.href;
  		var fdId = Com_GetUrlParameter(url,"fdId");
  		var fdKey = '${sysAttMainForm.fdKey}';
  		var fdModelId = '${sysAttMainForm.fdModelId}';
  		var fdModelName = '${sysAttMainForm.fdModelName}';
  		if(fdModelId == null){
  			fdModelId = "";
  		}
  		if(fdModelName == null){
  			fdModelName = "";
  		}
  		var jg_attachmentObject_editonline = new JG_AttachmentObject(fdId,fdKey, fdModelName, fdModelId, "office", "edit");
  		jg_attachmentObject_editonline.userId = "<%=com.landray.kmss.util.UserUtil.getUser().getFdId()%>";
  		jg_attachmentObject_editonline.userName = "<%=com.landray.kmss.util.UserUtil.getUser().getFdName()%>";
  		<c:if test="${canPrint=='1'}">
  			jg_attachmentObject_editonline.canPrint = true;
  		</c:if>
  		<c:if test="${canCopy=='1'}">
  			jg_attachmentObject_editonline.canCopy = true;	
  		</c:if>	
  	    //在线编辑打开，默认显示留痕
  		jg_attachmentObject_editonline.showRevisions = true;

  		<%-- 加载扩展JSP片断 --%>
  		<c:forEach items="${editOnlineMap['script']}" var="script">
  			<c:import url="${script}" charEncoding="UTF-8">
				<c:param name="fdKey" value="${sysAttMainForm.fdKey}" />
				<c:param name="fdAttType" value="office" />
				<c:param name="fdModelId" value="${sysAttMainForm.fdModelId}" />
				<c:param name="fdModelName" value="${sysAttMainForm.fdModelName}" />
				<c:param name="editMode" value="2" />		
				<c:param name="attachmentId" value="${sysAttMainForm.fdId}" />
				<c:param name="fdFileName" value="${sysAttMainForm.fdFileName}" />
			</c:import>	
  		</c:forEach>

  		function closeWin(){
  			Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
  			chromeHideJG(0);
  			if (!confirm(Com_Parameter.CloseInfo)){
  				chromeHideJG(1);
 				return;
  			}else{
  				//清除当前在线编辑用户的信息
  		    	clearEdit(fdId,fdModelId,fdModelName,fdKey);
  		    	// if(jg_attachmentObject_editonline.ocxObj.WebClose()){
  			 	window.close();
  				//}
  		    }
  		}
  		
  		//隐藏显示金格控件，调用common.js中方法不生效，这里重写一个
  		function chromeHideJG(value) {	
  			try{
  				if (navigator.userAgent.indexOf("Chrome") >= 0) {
  					if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
  						$("object[id*='JGWebOffice_']").each(function(i,_obj){
  							_obj.HidePlugin(value);
  						});	
  					}
  				}
  			}catch(e){}
  		}
  		
  		
  		Com_Parameter.event["submit"].push( function() {
  			if(jg_attachmentObject_editonline.updateTimer){
  				clearInterval(jg_attachmentObject_editonline.updateTimer);
  				return true;
  			}else{
  				return true;
  			}
		});
  		
  		Com_AddEventListener(window, "load", function() {
  			setTimeout(function(){
	            jg_attachmentObject_editonline.load(encodeURIComponent("${sysAttMainForm.fdFileName}"), "");
	            jg_attachmentObject_editonline.show();
	            if(jg_attachmentObject_editonline.ocxObj){
	 	  		   //在线编辑打开，默认显示留痕
	 	  		   jg_attachmentObject_editonline.ocxObj.Active(true);
	 				if(jg_attachmentObject_editonline.showRevisions == true){
	 					jg_attachmentObject_editonline.ocxObj.WebSetRevision(true,true,true,true);
	 				}
	 	  			if(!jg_attachmentObject_editonline.isFirst && jg_attachmentObject_editonline.hasShowButton){
	 	  	  			//非编辑状态下隐藏按钮栏
	 	  				 $("#S_OperationBar").remove();
	 	  			}
	            }
  			},1000);
  			
  			var winHeight = 550;
  			if (window.innerHeight)
  				winHeight = window.innerHeight;
  			else if ((document.body) && (document.body.clientHeight))
  				winHeight = document.body.clientHeight;
  			var obj1 = document.getElementById("JGWebOffice_${sysAttMainForm.fdKey}");
  			if(obj1){
  				obj1.setAttribute("height", (winHeight-80)+"px");
  			}
  		});
  		

  		//WebClose方法放到unload事件里边执行会导致火狐和谷歌浏览器崩溃
  		Com_AddEventListener(window, "beforeunload", function() {
  			if (navigator.userAgent.indexOf("Firefox") >= 0 || navigator.userAgent.indexOf("Chrome") >= 0){ 
		  			if (jg_attachmentObject_editonline.hasLoad) {		  			
		  				try {	  						  				  					    
		  					if (jg_attachmentObject_editonline.ocxObj && !jg_attachmentObject_editonline.ocxObj.WebClose()) {
		  						jg_attachmentObject_editonline.setStatusMsg(jg_attachmentObject_editonline.ocxObj.Status);
		  						alert(jg_attachmentObject_editonline.ocxObj.Status);
		  					}
		  				} catch (e) {
		  					alert("jg_unLoad error: " + e.description);
		  				}
		  			}
	  			}
  	  		})
  		
  		Com_AddEventListener(window, "unload", function() {
  	  		//清除当前在线编辑用户的信息
  			clearEdit(fdId,fdModelId,fdModelName,fdKey);
  			jg_attachmentObject_editonline.unLoad();
  		});

		//独立打开合同编辑页面窗口时，提交或者保存，对当前文档进行清稿
		//并将清稿的文件保存为临时Key(tmpJgProcessDoc)，查看页提交的时候，使用这个Key生成版本文件
		Com_Parameter.event["submit"].push(function() {
			if (window.opener) {
				var url = window.opener.location.href;
				if (url.indexOf("kmAgreementApply.do?method=view") > -1 && jg_attachmentObject_editonline.ocxObj.Modify) {
					__saveDoc();
				}
			}
			return true;
		});

		function __saveDoc() {
			// 记录原始数据
			var _WebUrl = jg_attachmentObject_editonline.ocxObj.WebUrl;
			var _AllowEmpty = jg_attachmentObject_editonline.ocxObj.AllowEmpty;
			var _fdId = jg_attachmentObject_editonline.ocxObj.WebGetMsgByName("_fdId");
			__log("保存过程文档-开始");
			try {
				var rtn;
				var tempPath;
				var fileName;
				if (window.userOpSysType.indexOf("Win") == -1
						&& navigator.userAgent.indexOf("Firefox") >= 0) {
					//国产化金格没有操作文件系统的接口，因此不做备份
				} else {
					// 备份原文档
					tempPath = jg_attachmentObject_editonline.activeObj.FileSystem.GetTempPath();
					var tempFileType = jg_attachmentObject_editonline.ocxObj.fileType;
					if (!tempFileType) {
						tempFileType = ".doc";
					}
					fileName = new Date().getTime() + tempFileType;
					__log("备份原文档：" + tempPath + fileName);
					rtn = jg_attachmentObject_editonline.ocxObj.WebSaveLocalFile(tempPath + fileName);
					__log("备份结果：" + rtn);
				}

				// 指定保存路径
				jg_attachmentObject_editonline.ocxObj.ClearRevisions();
				jg_attachmentObject_editonline.ocxObj.WebUrl = jg_attachmentObject_editonline.ocxObj.WebUrl+"?method=comparison&_addition=1";
				jg_attachmentObject_editonline.ocxObj.AllowEmpty = true;
				jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_fdId", new Date().getTime());
				jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_userId", curUserId);
				// 保存文件
				jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_fdKey", "tmpJgProcessDoc");
				jg_attachmentObject_editonline.setFileName(curFileName);
				rtn = jg_attachmentObject_editonline.ocxObj.WebSave();
				__log("保存文件结果：" + rtn);

				if (window.userOpSysType.indexOf("Win") == -1
						&& navigator.userAgent.indexOf("Firefox") >= 0) {
					//国产化金格没有操作文件系统的接口，因此不做备份
				} else {
					// 加载备份文档
					rtn = jg_attachmentObject_editonline.ocxObj.WebOpenLocalFile(tempPath + fileName);
					__log("加载备份文档结果：" + rtn);
				}
				__log("保存过程文档-结束");
			} catch (e) {
				if(window.console) {
					console.error(e);
				}
			}

			// 还原数据
			jg_attachmentObject_editonline.ocxObj.WebUrl = _WebUrl;
			jg_attachmentObject_editonline.ocxObj.AllowEmpty = _AllowEmpty;
			jg_attachmentObject_editonline.ocxObj.WebSetMsgByName("_fdId", _fdId);
		}

		/**
		 * 显示控制台日志
		 * @param msg
		 * @private
		 */
		function __log(msg) {
			if(window.console) {
				console.log(msg);
			}
		}
  </script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
