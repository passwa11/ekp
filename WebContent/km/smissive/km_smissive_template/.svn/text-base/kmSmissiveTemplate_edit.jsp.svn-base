<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil" %>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed()%>"/>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
     pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
     pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
      //WPS加载项
     pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
	//WPS中台
	pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));

	Boolean isWindows = Boolean.FALSE;
	if("windows".equals(JgWebOffice.getOSType(request))){
		isWindows = Boolean.TRUE;
	}
	request.setAttribute("isWindowsInOAassist", isWindows);
%>
<kmss:windowTitle
	subjectKey="km-smissive:table.kmSmissiveTemplate"
	moduleKey="km-smissive:table.kmSmissiveMain" />
<script language="JavaScript">
	Com_IncludeFile("dialog.js|ajax.js|jquery.js");
	var _isWpsCloudEnable = "${_isWpsCloudEnable}";
	var _isWpsWebOfficeEnable = "${_isWpsWebOfficeEnable}";
	var _isWpsWebOffice = "${_isWpsWebOffice}";
	var _isWpsCenterEnable = "${_isWpsCenterEnable}";
	var wpsoaassistEmbed = "${wpsoaassistEmbed}";
	var isWindowsInOAassist = "${isWindowsInOAassist}";
	//当前页签是否是word 内嵌加载项
	var curTabWordIsEmbeddedAddons = false;
	var wpsFlag = false;
	var wpsCenterFlag = false;
	var callBackreturnValue=true;
	
	function fn_checkUniqueCodePre() {
	     createXmlHttpRequest();
	     if (xmlHttpRequest) {
	       	xmlHttpRequest.onreadystatechange = ajaxCallBack;
	       	var url = "kmSmissiveTemplate.do?method=checkUniqueCodePre&fdId=${kmSmissiveTemplateForm.fdId}";
	       	//url = url + "&fdCodePre=" + encodeURIComponent(document.getElementsByName("fdCodePre")[0].value);
	       	url = url + "&fdCodePre=" + document.getElementsByName("fdCodePre")[0].value;
	       	url = encodeURI(url);
	       	url = encodeURI(url);
	        //alert(url);
	        xmlHttpRequest.open("GET", url, true);
	        xmlHttpRequest.setRequestHeader("Content-Type", "text/html;charset=UTF-8" );
	        xmlHttpRequest.send();
	      }
	}
	
	function ajaxCallBack(){
		if (xmlHttpRequest.readyState == 4) { // Complete
		     if (xmlHttpRequest.status == 200) { // OK response
				var responseText = xmlHttpRequest.responseText;
				if(responseText == 'false'){
					alert('<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdCodePre.check"/>');
					//document.getElementsByName("fdCodePre")[0].select();
					callBackreturnValue=false;
				}
		     } else {
		       alert("Problem with server response:\n " + xmlHttpRequest.statusText);
		     }
	    }
	}
	function showBaseLabel(){
		setTimeout("Doc_SetCurrentLabel('Label_Tabel', 1, true);", 300);
		// 添加标签切换事件
		var table = document.getElementById("Label_Tabel");
		if(table != null && window.Doc_AddLabelSwitchEvent){
			if(_isWpsCloudEnable == "true" || _isWpsWebOfficeEnable == "true" || _isWpsWebOffice == "true" ){
				Doc_AddLabelSwitchEvent(table, "showWps");
			}else if(_isWpsCenterEnable == "true") {
				Doc_AddLabelSwitchEvent(table, "showWpsCenter");
			}
			else{
				Doc_AddLabelSwitchEvent(table, "swtichShowJG");
			}
		}
	}
	function showWps(tableName,index){

		var type=document.getElementsByName("fdNeedContent")[0];
		var trs = document.getElementById(tableName).rows;

		// if(type.value==""){
		// 	type.value = "1";
		// }
		if(_isWpsWebOffice == "true"&&wpsoaassistEmbed=="true"
            &&isWindowsInOAassist=="false" && type.value == "1"
            && wpsFlag ) {
			wpsFlag = false;
		}

		if("1" == type.value && trs[index].id =="tr_content"){
			$("#wordEdit").show();
			if(!wpsFlag){
				if(_isWpsCloudEnable == "true"){
					wps_cloud_mainContent.load();
				}else if(_isWpsWebOfficeEnable == "true"){
					wps_mainContent.load();
				}else if(_isWpsWebOffice == "true"&&wpsoaassistEmbed=="true"&&isWindowsInOAassist=="false"){
					curTabWordIsEmbeddedAddons = true;
					setTimeout(function(){
						wps_linux_mainContent.load();
					},500);
				}

				wpsFlag = true;
			}

		}else{
			$("#wordEdit").hide();
			//内嵌加载项切换页签时保存当前内容
			if('${pageScope._isWpsWebOffice}' == "true"&&"${wpsoaassistEmbed}"=="true"&&"${isWindowsInOAassist}"=="false"&&curTabWordIsEmbeddedAddons){
				curTabWordIsEmbeddedAddons = false;
				wps_linux_mainContent.setTmpFileByAttKey();
				wps_linux_mainContent.isCurrent=false;
			}
		}
	}
	function showWpsCenter(tableName,index){
		var type=document.getElementsByName("fdNeedContent")[0];
		var trs = document.getElementById(tableName).rows;
		if("1" == type.value && trs[index].id =="tr_content"){
			$("#wordEdit").show();
			if(!wpsCenterFlag){
				wps_center_mainContent.load();
				var styleOffice = document.getElementById("office-iframe");
				styleOffice.style.height='550px';
				styleOffice.style.width='100%';
				wpsCenterFlag = true;
			}
		}else{
			$("#wordEdit").hide();
		}
	}
	function swtichShowJG(tableName, index){
		var trs = document.getElementById(tableName).rows;
		var type=document.getElementsByName("fdNeedContent")[0];
		if(type.value==""){
			type.value = "1";
		}
		if(trs[index].id == "tr_content" && type.value == "1"){
			$("#missiveButtonDiv").show();
			$("#content").css({
				left:'34px',
				top:'180px',
				width:'95%',
				height:'550px'
			});
			chromeHideJG_2015(1);
		}else{
			$("#missiveButtonDiv").hide();
			$("#content").css({
				left:'0px',
				top:'0px',
				width:'0px',
				height:'0px'
			});
			chromeHideJG_2015(0);
		}
	}
	function validateSubmitForm(){
		//检查编号规则是否已经定义过；
		
	}
	
	Com_AddEventListener(window,"load",showBaseLabel);

	function checkEditType(value){
		var type=document.getElementsByName("fdNeedContent")[0];
		if(value==""){
			value = "0";
		}
		if("1" == value){
			if(_isWpsCloudEnable == "true" || _isWpsWebOfficeEnable == "true" || _isWpsWebOffice == "true" ){
				$("#wordEdit").show();
				if(!wpsFlag){
					if(_isWpsCloudEnable == "true"){
						wps_cloud_mainContent.load();
					}else if(_isWpsWebOfficeEnable == "true"){
						wps_mainContent.load();
					}else if(_isWpsWebOffice == "true"&&wpsoaassistEmbed=="true"&&isWindowsInOAassist=="false"){
						curTabWordIsEmbeddedAddons = true;
						setTimeout(function(){
							wps_linux_mainContent.load();
						},500);
					}
					wpsFlag = true;
				}
			} else if(_isWpsCenterEnable == "true") {
				$("#wordEdit").show();
				if(!wpsCenterFlag){
					if(_isWpsCenterEnable == "true"){
						wps_center_mainContent.load();
						var styleOffice = document.getElementById("office-iframe");
						styleOffice.style.height='550px';
						styleOffice.style.width='100%';
					}
					wpsCenterFlag = true;
				}
			}else{
				$("#wordEdit").css({ 
					width:'100%',
					height:'550px'
	
				});
				chromeHideJG_2015(1);
				$("#missiveButtonDiv").show();
				var obj = document.getElementById("JGWebOffice_mainContent");
				 setTimeout(function(){
					 if(obj&&Attachment_ObjectInfo['mainContent']&&!jg_attachmentObject_mainContent.hasLoad){
						jg_attachmentObject_mainContent.load();
						jg_attachmentObject_mainContent.show();
						jg_attachmentObject_mainContent.ocxObj.Active(true);
					 }
				 },1000);
			}
		} else {
			wpsFlag = false;
			if(_isWpsCloudEnable == "true" || _isWpsWebOfficeEnable == "true" || _isWpsWebOffice == "true" || _isWpsWebOffice == "true" || _isWpsCenterEnable == "true"){
				$("#wordEdit").hide();
			}else{
				$("#wordEdit").css({
					width:'0px',
					height:'0px'
				});
				chromeHideJG_2015(0);
				$("#missiveButtonDiv").hide();
			}
			if('${pageScope._isWpsWebOffice}' == "true"&&"${wpsoaassistEmbed}"=="true"&&"${isWindowsInOAassist}"=="false"&&curTabWordIsEmbeddedAddons){
				curTabWordIsEmbeddedAddons = false;
				wps_linux_mainContent.setTmpFileByAttKey();
				wps_linux_mainContent.isCurrent=false;
			}
		}
		type.value = value;
	}

	Com_Parameter.event["submit"].push(function(){
		//提交时判断是模板还是分类，如果是分类则移除页面控件对象
		var type =  document.getElementsByName("fdNeedContent");
		var flag = false;
		if(type[0].value==""){
			type[0].value = "0";
		}
		var obj = document.getElementById("JGWebOffice_mainContent");
			  if(type[0].value !="1"){
				  if(obj&&Attachment_ObjectInfo['mainContent']&&jg_attachmentObject_mainContent.hasLoad){
					jg_attachmentObject_mainContent.unLoad();
				  }
			     $("#wordEdit").remove();
			     flag = true;
		      }else{
		    	  if(obj&&Attachment_ObjectInfo['mainContent']&&jg_attachmentObject_mainContent.hasLoad){
					jg_attachmentObject_mainContent.ocxObj.Active(true);
			        if(!jg_attachmentObject_mainContent._submit()){
	    		    	return false;
	    		    }
		    	  }
		        flag = true;
		     }
		     return flag;
		});
</script>

<html:form action="/km/smissive/km_smissive_template/kmSmissiveTemplate.do" onsubmit="return validateKmSmissiveTemplateForm(this);">

		<%--简单分类按钮 --%>
		<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmSmissiveTemplateForm" />
		</c:import>

<p class="txttitle"><bean:message  bundle="km-smissive" key="table.kmSmissiveTemplate"/></p>

<center>
<html:hidden property="fdId"/>
<table id="Label_Tabel" width="95%">
	<%-- 类别 --%>
	<c:import url="/sys/simplecategory/include/sysCategoryMain_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmSmissiveTemplateForm" />
			<c:param name="requestURL" value="/km/smissive/km_smissive_template/kmSmissiveTemplate.do?method=add" />
			<c:param name="fdModelName" value="${param.fdModelName}" />
	</c:import>
	<tr LKS_LabelName="<bean:message bundle="km-smissive" key="kmSmissiveTemplate.label.baseinfo" />"><td>
	
		<table class="tb_normal" width=100%>
			<% if(!com.landray.kmss.sys.number.util.NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.km.smissive.model.KmSmissiveMain")){ %>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdCodePre"/>
				</td><td width=85% colspan="3">
					<html:text property="fdCodePre" style="width:90%" onblur="fn_checkUniqueCodePre();"/>
					<span class="txtstrong">*</span>
					<br>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdCodePre.describeone"/>
					<br>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdCodePre.describetwo"/>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdYear"/>
				</td><td width=35%>
					<html:text property="fdYear"/>
					<span class="txtstrong">*</span>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdYear.describetwo"/>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdCurNo"/>
				</td><td width=35%>
					<html:text property="fdCurNo"/>
					<span class="txtstrong">*</span>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdCurNo.describetwo"/>
				</td>
			</tr>
			<%} %>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpTitle"/>
				</td><td width=85% colspan="3">
					<html:text property="fdTmpTitle" style="width:90%"/>
					<span class="txtstrong">*</span>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpUrgency"/>
				</td><td width=35%>
					<sunbor:enums property="fdTmpUrgency"
								enumsType="km_smissive_urgency" elementType="select"
								bundle="km-smissive" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpSecret"/>
				</td><td width=35%>
					<sunbor:enums property="fdTmpSecret"
								enumsType="km_smissive_secret" elementType="select"
								bundle="km-smissive" />
				</td>
			</tr>
			
			
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpMainDept"/>
				</td><td width=35%>
					<xform:address propertyId="fdTmpMainDeptId" propertyName="fdTmpMainDeptName" orgType="ORG_TYPE_ORGORDEPT" style="width:40%" ></xform:address>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpIssuer"/>
				</td><td width=35%>
					<xform:address propertyId="fdTmpIssuerId" propertyName="fdTmpIssuerName" orgType="ORG_TYPE_PERSON" style="width:40%" ></xform:address>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpSendDept"/>
				</td><td colspan="3" width=35%>
					<xform:address textarea="true" mulSelect="true" propertyId="fdTmpSendDeptIds" propertyName="fdTmpSendDeptNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" ></xform:address>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpCopyDept"/>
				</td><td colspan="3" width=35%>
					<xform:address textarea="true" mulSelect="true" propertyId="fdTmpCopyDeptIds" propertyName="fdTmpCopyDeptNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" ></xform:address>
				</td>
				
			</tr>
			
			
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.fdTmpFlowFlag"/>
				</td><td width=85% colspan="3">
					<html:checkbox name="kmSmissiveTemplateForm" property="fdTmpFlowFlag">
						<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.radio.true"/>
					</html:checkbox>
					<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.radio.describe"/>
				</td>
			</tr>
			
			<!-- 标签机制 -->
			<c:import url="/sys/tag/include/sysTagTemplate_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="kmSmissiveTemplateForm" />
				<c:param name="fdKey" value="smissiveDoc" /> 
			</c:import>
			<!-- 标签机制 -->
		</table>
	</td></tr>
	
	<!-- 加入机制 -->
	<%-- 以下代码为在线编辑的代码 --%>
	<tr id="tr_content" LKS_LabelName="<bean:message  bundle="km-smissive" key="kmSmissiveTemplate.label.content"/>">
		<td id="td_content">
		  <table id="base_info" class="tb_normal" width=100%>
			<%-- 编辑方式 --%>
			<html:hidden property="fdNeedContent" />
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="kmSmissiveTemplate.fdNeedContent" bundle="km-smissive" />
				</td>
				<td width="85%">
					<xform:radio property="fdEditType" showStatus="edit" value="${kmSmissiveTemplateForm.fdNeedContent}" onValueChange="checkEditType">
						<xform:enumsDataSource enumsType="kmSmissiveTemplate_fdNeedContent" />
					</xform:radio>
				</td>
			</tr>
			<tr>
			 <td  colspan="2">
			 	<c:choose>
					<c:when test="${_isWpsCloudEnable}">
				    	<div id="wordEdit">
							<div id="missiveButtonDiv" style="text-align:right">
						   		&nbsp;
							   	<a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
							       <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
							    </a>
							</div>
							<div>
								<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="mainContent" />
									<c:param name="load" value="false" />
									<c:param name="bindSubmit" value="false"/>	
									<c:param name="fdModelId" value="${kmSmissiveTemplateForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
									<c:param name="formBeanName" value="kmSmissiveTemplateForm" />
									<c:param name="fdTemplateKey" value="mainContent" />
									<c:param name="buttonDiv" value="missiveButtonDiv" />
								</c:import>
					  		</div>
						</div>
					</c:when>
					<c:when test="${_isWpsCenterEnable}">
						<div id="wordEdit">
							<div id="missiveButtonDiv" style="text-align:right">
								&nbsp;
								<a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
									<bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
								</a>
							</div>
							<div>
								<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="mainContent" />
									<c:param name="load" value="false" />
									<c:param name="bindSubmit" value="false"/>
									<c:param name="fdModelId" value="${kmSmissiveTemplateForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
									<c:param name="formBeanName" value="kmSmissiveTemplateForm" />
									<c:param name="fdTemplateKey" value="mainContent" />
									<c:param name="buttonDiv" value="missiveButtonDiv" />
								</c:import>
							</div>
						</div>
					</c:when>
					<c:when test="${_isWpsWebOffice}">
				    	<div id="wordEdit">
							<div id="missiveButtonDiv" style="text-align:right">
						   		&nbsp;
							   	<a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
							       <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
							    </a>
							</div>
							<div>
							<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="mainContent" />
											<c:param name="fdMulti" value="false" />
											<c:param name="fdModelId" value="${kmSmissiveTemplateForm.fdId}" />
											<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
											<c:param name="bindSubmit" value="false"/>
										    <c:param name="isTemplate" value="true"/>
											<c:param name="fdTemplateKey" value="mainContent" />
											<c:param name="templateBeanName" value="kmSmissiveTemplateForm" />
											<c:param name="addToPreview" value="false" />
											<c:param name="redhead" value="${redhead}" />
											<c:param name="bookMarks" value="${bookmarkJson}" />
											<c:param name="nodevalue" value="${nodevalue}" />
											<c:param name="wpsExtAppModel" value="kmSmissive" />
											<c:param  name="signtrue"  value="${signtrue}"/>
											<c:param name="canDownload" value="${canDownload}" />
											<c:param name="newFlag" value="true" />
											<c:param  name="hideReplace"  value="true"/>
											<c:param name="showDelete" value="false" />
											<c:param name="load" value="false" />
											<c:param name="formBeanName" value="kmSmissiveTemplateForm" />
										</c:import>
								
					  		</div>
						</div>
					</c:when>
					<c:when test="${_isWpsWebOfficeEnable}">
						<div id="wordEdit">
							<div id="missiveButtonDiv" style="text-align:right">
						   		&nbsp;
							   	<a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
							       <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
							    </a>
							</div>
							<div>
								<c:import url="/sys/attachment/sys_att_main/wps/sysAttMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="mainContent" />
									<c:param name="load" value="false" />
									<c:param name="bindSubmit" value="false"/>	
									<c:param name="fdModelId" value="${kmSmissiveTemplateForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
									<c:param name="fdTemplateKey" value="mainContent" />
									<c:param name="formBeanName" value="kmSmissiveTemplateForm" />
									<c:param name="buttonDiv" value="missiveButtonDiv" />
								</c:import>
					  		</div>
						</div>
					</c:when>
					<c:otherwise>
					   <%
					   		String jgOcxVersion = JgWebOffice.getJGBigVersion();
					   		if (null != jgOcxVersion && jgOcxVersion.equals(JgWebOffice.JG_OCX_BIG_VERSION_2015)) {
					   			request.setAttribute("show",true);
					   		} else {
								if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>-1||request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT")>-1){
									request.setAttribute("show",true);
								}
						        if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGMULEnabled()){
						        	request.setAttribute("show",true);
						        }
					   		}
						%>
					   <c:if test="${show}">
						   <div id="wordEdit" style="height: 1px;width: 1px;overflow:hidden;">
				               <div id="missiveButtonDiv" style="text-align:right"> &nbsp;
								   <a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
							        <bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
							       </a>
								</div>
								<%
									// 金格启用模式
									if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()) {
										pageContext.setAttribute("sysAttMainUrl", "/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp");
									} else {
										pageContext.setAttribute("sysAttMainUrl", "/sys/attachment/sys_att_main/sysAttMain_edit.jsp");
									}
								%>
								<c:import url="${sysAttMainUrl}" charEncoding="UTF-8">
									<c:param name="fdKey" value="mainContent" />
									<c:param name="fdAttType" value="office" />
									<c:param name="load" value="false" />
									<c:param name="fdModelId" value="${kmSmissiveTemplateForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
									<c:param name="buttonDiv" value="missiveButtonDiv" />
									<c:param name="bindSubmit" value="false"/>
									<c:param name="isTemplate" value="true"/>
									<c:param  name="attHeight" value="550"/>
								</c:import>						
							</div>
					   </c:if>
			   		</c:otherwise>
			   	</c:choose>
			  </td>
			</tr>
		   </table>
		</td>
	</tr>
	<%-- 以上代码为在线编辑的代码 --%>
		
	<%-- 以下代码为嵌入流程模板标签的代码 --%>
	<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="kmSmissiveTemplateForm" />
		<c:param name="fdKey" value="smissiveDoc" />
		<c:param name="fdModelName"
			value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
		<c:param name="messageKey" value="km-smissive:kmSmissive.label.flow" />
	</c:import>
	<%-- 以上代码为嵌入流程模板标签的代码 --%>
	
	<%-- 以下代码为嵌入默认权限模板标签的代码 --%>
	<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />"><td>
		<table class="tb_normal" width=100%>
			<c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmSmissiveTemplateForm" />
				<c:param name="moduleModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
			</c:import>
		</table>
	</td></tr>
	<%-- 以上代码为嵌入默认权限模板标签的代码 --%>
	
	<%----发布机制开始--%>
	<c:import url="/sys/news/include/sysNewsPublishCategory_edit.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmSmissiveTemplateForm" />
		<c:param name="fdKey" value="smissiveDoc" /> 
		<c:param name="messageKey" value="km-smissive:kmSmissiveTemplate.label.publish" />
	</c:import>
	<%----发布机制结束--%>
	
	<%-- 以下代码为嵌入关联机制的代码 --%>
	<tr	LKS_LabelName="<bean:message bundle="sys-relation" key="sysRelationMain.tab.label" />">
		<c:set var="mainModelForm" value="${kmSmissiveTemplateForm}" scope="request" />
		<c:set var="currModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" scope="request"/>
		<td>
			<%@ include	file="/sys/relation/include/sysRelationMain_edit.jsp"%>
		</td>
	</tr>
	<%-- 以上代码为嵌入关联机制的代码 --%>
	
	<%----编号机制开始--%>
	<% if(com.landray.kmss.sys.number.util.NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.km.smissive.model.KmSmissiveMain")){ %>
	<c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmSmissiveTemplateForm" />
		<c:param name="modelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain"/>
	</c:import>
	<%} %>
	<%----编号机制结束--%>
	<!-- 规则机制 -->
	<c:import url="/sys/rule/sys_ruleset_temp/sysRuleTemplate_edit.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmSmissiveTemplateForm" />
		<c:param name="fdKey" value="smissiveDoc" />
		<c:param name="templateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate"></c:param>
	</c:import>
		<%-- 提醒中心 --%>
	<kmss:ifModuleExist path="/sys/remind/">
		<c:import url="/sys/remind/include/sysRemindTemplate_edit.jsp" charEncoding="UTF-8">
			<%-- 模板Form名称 --%>
			<c:param name="formName" value="kmSmissiveTemplateForm" />
			<%-- KEY --%>
			<c:param name="fdKey" value="smissiveDoc" />
			<%-- 模板全名称 --%>
			<c:param name="templateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
			<%-- 主文档全名称 --%>
			<c:param name="modelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
			<%-- 主文档模板属性 --%>
			<c:param name="templateProperty" value="fdTemplate" />
			<%-- 模块路径 --%>
			<c:param name="moduleUrl" value="km/smissive" />
		</c:import>
	</kmss:ifModuleExist>

</table>

</center>
<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="kmSmissiveTemplateForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>

<script language="javascript" for="window" event="onload">
	//checkEditType("${kmSmissiveTemplateForm.fdNeedContent}", null);
	//var obj = document.getElementsByName("mainContent_bookmark");	//公司产品JS库有问题，其中生成的按钮没有ID属性，导致ie6不能够用该语句查找
	//obj[0].style.display = "none";
	var tt = document.getElementsByTagName("INPUT");
	for(var i=0;i<tt.length;i++){
		
		if(tt[i].name == "mainContent_showRevisions"){
			tt[i].style.display = "none";
		}
		if(tt[i].name == "mainContent_printPreview"){
			tt[i].style.display = "none";
		}
	}

	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
		var fdCodePre=document.getElementsByName("fdCodePre")[0];
		var fdYear=document.getElementsByName("fdYear")[0];
		var fdCurNo=document.getElementsByName("fdCurNo")[0];
		if(fdCodePre!=null)
		{
			var txt=fdCodePre.value;
			if(txt.length==0)
			{
				alert('编号规则不能为空！');
				return false;
			}
			else if(txt.indexOf('%年号%')==-1 || txt.indexOf('%流水号%')==-1)
			{
				alert('编号规则不规范，请参考示例：蓝凌[%年号%]%流水号%号  进行调整');
				return false;
			}
			else
			{
				fn_checkUniqueCodePre();
				if(!callBackreturnValue)
				{
					return false;
				}
			}
		}

		if(fdYear!=null)
		{
			var txt2=fdYear.value;
			if(isNotNumFour(txt2))
			{
				alert('当前年号数据格式不正确，请输入四位数字');
				return false;
			}
			
		}

		if(fdCurNo!=null)
		{
			var txt3=fdCurNo.value;
			if(isNotNumOne(txt3))
			{
				alert('当前流水号数据格式不正确，请输入数字');
				return false;
			}
			
		}
		
		return true;
	};
	function isNotNumFour(str) {
		var patrn=/^[0-9]{4}$/; 
		if (!patrn.exec(str)) return true ;
		else
			return false ;
	}
	function isNotNumOne(str) {
		var patrn=/^[0-9]{1,}$/; 
		if (!patrn.exec(str)) return true ;
		else
			return false ;
	}
	
</script>