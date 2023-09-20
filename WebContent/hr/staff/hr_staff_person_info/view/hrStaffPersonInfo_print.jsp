<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="title">
		${ hrStaffPersonInfoForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
	</template:replace>
	<template:replace name="body">
		<script language="JavaScript">
			seajs.use(['theme!form']);
			Com_IncludeFile("jquery.js|dialog.js|doclist.js");
			<%
			String p_config = request.getParameter("p_config");
			java.util.ArrayList<String> showConfig = new java.util.ArrayList<String>();
			if (p_config == null || p_config.length() == 0 ) {
				showConfig.add("info"); // 员工信息
				showConfig.add("contact"); // 联系信息
				if (UserUtil.checkRole("ROLE_HRSTAFF_EXPERIENCE")) {
					showConfig.add("experience"); // 个人经历
				}
				if (UserUtil.checkRole("ROLE_HRSTAFF_EMOLUMENT")) {
					showConfig.add("emolument"); // 薪酬福利
				}
			} else {
				String[] configs = p_config.split(";");
				for (int i = 0; i < configs.length; i ++) {
					String cfg = configs[i];
					if (cfg != null && cfg.length() != 0 ) {
						showConfig.add(cfg);
					}
				}
			}
			String defValue = "";
			for (int i = 0; i < showConfig.size(); i ++) {
				defValue += ",'" + showConfig.get(i) + "'";
			}
			defValue = defValue.substring(1);
			%>
			var defValue = [<%=defValue%>];
			var defOptions = [{id:'info', name:'<bean:message bundle="hr-staff" key="table.HrStaffPersonInfo" />'}
			                  ,{id:'contact', name:'<bean:message bundle="hr-staff" key="hrStaffPersonInfo.contactInfo" />'}
			                  <kmss:authShow roles="ROLE_HRSTAFF_EXPERIENCE">
			                  ,{id:'experience', name:'<bean:message bundle="hr-staff" key="table.hrStaffPersonExperience" />'}
			                  </kmss:authShow>
			                  <kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
			                  ,{id:'emolument', name:'<bean:message bundle="hr-staff" key="table.hrStaffEmolumentWelfare" />'}
			                  </kmss:authShow>
			                  ];
			function ShowPrintList() {
				var optionData = new KMSSData();
				optionData.AddHashMapArray(defOptions);
				var valueData = new KMSSData();
				for (var i = 0; i < defValue.length; i ++) {
					var defV = defValue[i];
					for (var j = 0; j < defOptions.length; j ++) {
						var opt = defOptions[j];
						if (opt.id == defV) {
							valueData.AddHashMap(opt);
						}
					}
				}
				
				var dialog = new KMSSDialog(true, true);
				dialog.AddDefaultOption(optionData);
				dialog.AddDefaultValue(valueData);
				dialog.SetAfterShow(function(rtn) {
					if (rtn == null || rtn.IsEmpty()) {
						return ;
					}
					var value = '';
					var values = rtn.GetHashMapArray();
					
					for (var i = 0; i < values.length; i ++) {
						value += ';' + values[i].id;
					}
					var url = Com_SetUrlParameter(window.location.href, 'p_config', value);
					setTimeout(function(){window.location.href = url;},500);
				});
				dialog.Show();
			}
			
			var flag = 0;
			function ZoomFontSize(size) {
				//当字体缩小到一定程度时，缩小字体按钮变灰不可点击
				if(flag>=0||flag==-5){
					flag = flag+size;
				}
				if(flag<0){
					$("#zoomOut").prop("disabled",true);
					$("#zoomOut").css("background-color","#A8A8A8");
				}else{
					$("#zoomOut").prop("disabled",false);
					$("#zoomOut").css("background-color","");
					$("#zoomOut").prop("className","lui_form_button");
				} 
				var root = document.getElementById("printTable");
				var i = 0;
				for (i = 0; i < root.childNodes.length; i++) {
					SetZoomFontSize(root.childNodes[i], size);
				}
				var tag_fontsize;
				if(root.currentStyle){
				    tag_fontsize = root.currentStyle.fontSize;  
				}  
				else{  
				    tag_fontsize = getComputedStyle(root, null).fontSize;  
				} 
				root.style.fontSize = parseInt(tag_fontsize) + size + 'px';
			}
			function SetZoomFontSize(dom, size) {
				if (dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
					for (var i = 0; i < dom.childNodes.length; i ++) {
						SetZoomFontSize(dom.childNodes[i], size);
					}
					var tag_fontsize;
					if(dom.currentStyle){  
					    tag_fontsize = dom.currentStyle.fontSize;  
					}  
					else{  
					    tag_fontsize = getComputedStyle(dom, null).fontSize;  
					} 
					dom.style.fontSize = parseInt(tag_fontsize) + size + 'px';
				}
			}
			function ClearDomWidth(dom) {
				if (dom != null && dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
						if (dom.style.whiteSpace == 'nowrap') {
							dom.style.whiteSpace = 'normal';
						}
						if (dom.style.display == 'inline') {
							dom.style.wordBreak  = 'break-all';
							dom.style.wordWrap  = 'break-word';
						}
					ClearDomsWidth(dom);
				}
			}
			function ClearDomsWidth(root) {
				for (var i = 0; i < root.childNodes.length; i ++) {
					ClearDomWidth(root.childNodes[i]);
				}
			}
			
			function expandXformTab(){
				var xformArea = $("#_xform_detail");
				if(xformArea.length>0){
					var tabs = $("#_xform_detail table.tb_label");
					if(tabs.length>0){
						for(var i=0; i<tabs.length; i++){
							var id = $(tabs[i]).prop("id");
							if(id==null || id=='') continue;
							$(tabs[i]).toggleClass("tb_normal");
							tabs[i].deleteRow(0);
							var tmpFun = function(idx,trObj){
								var trObj = $(trObj);
								var tmpTitleTr = $("<tr class='tr_normal_title'><td align='left'>" + trObj.attr("LKS_LabelName") + "</td></tr>");
								trObj.before(tmpTitleTr);
							};
							var trArr = $("#"+id+" >tbody > tr[LKS_LabelName]");
							if(trArr.length<1){
								trArr = $("#"+id+" > tr[LKS_LabelName]");
							}
							trArr.each(tmpFun).show();
						}
					}
				}
			}
			Com_AddEventListener(window, "load", function() {
				ClearDomWidth(document.getElementById("info_content"));
				expandXformTab();
				//清除链接样式
				$('#_xform_detail a').css('text-decoration','none');
				$('a[id^=thirdCtripXformPlane_]').removeAttr('onclick');
				$('a[id^=thirdCtripXformHotel_]').removeAttr('onclick');
			});
		
			var HKEY_Root,HKEY_Path,HKEY_Key;   
			HKEY_Root="HKEY_CURRENT_USER";   
			HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";   
			var head,foot,top,bottom,left,right;  
			  
			//取得页面打印设置的原参数数据  
			function PageSetup_temp() {    
			  var Wsh=new ActiveXObject("WScript.Shell");   
			  HKEY_Key="header";   
			  //取得页眉默认值  
			  head = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
			  HKEY_Key="footer";   
			  //取得页脚默认值  
			  foot = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
			  HKEY_Key="margin_bottom";   
			  //取得下页边距  
			  bottom = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
			  HKEY_Key="margin_left";   
			  //取得左页边距  
			  left = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
			  HKEY_Key="margin_right";   
			  //取得右页边距  
			  right = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
			  HKEY_Key="margin_top";   
			  //取得上页边距  
			  top = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
			  
			}  
			  
			//设置网页打印的页眉页脚和页边距  
			function PageSetup_Null() {     
			  var Wsh=new ActiveXObject("WScript.Shell");   
			  HKEY_Key="header";   
			  //设置页眉（为空）  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");   
			  HKEY_Key="footer";   
			  //设置页脚（为空）  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");   
			  HKEY_Key="margin_bottom";   
			  //设置下页边距（0）  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   
			  HKEY_Key="margin_left";   
			  //设置左页边距（0）  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   
			  HKEY_Key="margin_right";   
			  //设置右页边距（0）  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   
			  HKEY_Key="margin_top";   
			  //设置上页边距（8）  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0");   
			
			}   
			//设置网页打印的页眉页脚和页边距为默认值   
			function  PageSetup_Default() {     
			  var Wsh=new ActiveXObject("WScript.Shell");   
			  HKEY_Key="header";   
			  HKEY_Key="header";   
			  //还原页眉  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,head);   
			  HKEY_Key="footer";   
			  //还原页脚  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,foot);   
			  HKEY_Key="margin_bottom";   
			  //还原下页边距  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,bottom);   
			  HKEY_Key="margin_left";   
			  //还原左页边距  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,left);   
			  HKEY_Key="margin_right";   
			  //还原右页边距  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,right);   
			  HKEY_Key="margin_top";   
			  //还原上页边距  
			  Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,top);    
			}  
			  
			function printorder() {  
				try {
			        window.print();
				} catch (e) {
					alert('<bean:message bundle="hr-staff" key="hr.staff.print.error" />');
				}
			}

			function setTagNames(names) {
				var tagNames = [];
				$.each(names.split(" "), function(i, n){
					tagNames.push(n);
				});
				$("#span_tagNames").empty().append(tagNames.join(", "));
			}

			LUI.ready(function() {
				setTagNames("${hrStaffPersonInfoForm.sysTagMainForm.fdTagNames}");
			});
		</script>
		<style type="text/css">
			#title {
				font-size: 22px;
				color: #000;
			}
			
			.tr_label_title {
				margin: 28px 0px 10px 0px;
				border-left: 3px solid #46b1fc
			}
			
			.tr_label_title .title {
				font-weight: 900;
				font-size: 16px;
				color: #000;
				text-align: left;
				margin-left: 8px;
			}
			
			.page_line {
				background-color: red;
				height: 1px;
				border: none;
				width: 100%;
				position: absolute;
				overflow: hidden;
			}
			
			a:hover {
				color: #333;
				text-decoration: none;
			}
			
			#optBarDiv.btnprint {
				display: block;
			}
			
			#printTable {
				width: 980px;
				margin-bottom: 20px;
			}
			
			#printTable .tb_normal>tbody>tr>td {
				border: 1px #9d8f8f solid !important;
			}
			
			@media print {
				#optBarDiv,#S_OperationBar,#optBarDiv.btnprint {
					display: none;
				}
				.new_page {
					page-break-before: always;
				}
				.page_line {
					display: none;
				}
				body {
					font-size: 12px;
				}
				#printTable .tb_noborder,#printTable table .tb_noborder,#printTable .tb_noborder td {
					border: none;
				}
				#printTable .tr_label_title {
				}
				#printTable {
					width: 100%;
					margin-bottom: 0px;
				}
				#printTable .tb_normal>tbody>tr>td {
					border: 1px #9d8f8f solid !important;
				}
			}
		</style>
		<!-- <OBJECT ID='WebBrowser' NAME="WebBrowser" WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></OBJECT> -->
		<center>
			<div id="optBarDiv" class="btnprint" style="text-align: right; padding-right: 10px; padding-bottom: 20px; width: 980px;">
				<!-- 放大文字 -->
				<input class="lui_form_button" type="button" id="zoomIn" value="<bean:message key="hr.staff.btn.zoom.in" bundle="hr-staff"/>" onclick="ZoomFontSize(5);">
				<!-- 缩小文字 -->
				<input class="lui_form_button" type="button" id="zoomOut" value="<bean:message key="hr.staff.btn.zoom.out" bundle="hr-staff"/>" onclick="ZoomFontSize(-5);">
				<!-- 打印 -->
				<input class="lui_form_button" type="button" value="<bean:message key="button.print"/>" onclick="printorder();">
				<!-- 打印设置 -->
				<input class="lui_form_button" type="button" value="<bean:message key="hr.staff.btn.printConfig" bundle="hr-staff"/>" onclick="ShowPrintList();">
				<c:import url="/sys/common/exportButton.jsp" charEncoding="UTF-8">
					<c:param name="oldStyle" value="true"></c:param>
					<c:param name="showHtml" value="false"></c:param>
				</c:import>
				<!-- 关闭 -->
				<input class="lui_form_button" type="button" value="<bean:message key="button.close"/>" onclick="window.close();">
			</div>
		</center>
		<form name="hrStaffPersonInfoForm" method="post" action="<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do"/>">
			<center>
				<div id="printTable" style="border: none; font-size: 12px;">
				
					<!-- 简历 头部 Starts -->
					<div class="lui_hr_staff_resume_head">
						<div class="staff_info">
							<!-- 头像 -->
							<div class="staff_photo"><img src="${LUI_ContextPath}/sys/person/image.jsp?personId=${hrStaffPersonInfoForm.fdOrgPersonId}&size=b&s_time=${LUI_Cache}"/></div>
							<!-- 名称 -->
							<h2>
								<span>${ hrStaffPersonInfoForm.fdName }</span>
							</h2>
							<!-- 岗位和部门 -->
							<p class="staff_post_info">
								<span>${ hrStaffPersonInfoForm.fdOrgParentsName }</span>
								<em>|</em>
								<span>${ hrStaffPersonInfoForm.fdOrgPostNames }</span>
							</p>
							<!-- 标签 -->
							<% if(com.landray.kmss.hr.staff.util.HrStaffPersonUtil.isShowLabel(request)) { %>
							<p class="staff_label_info">
								<span>${ lfn:message('hr-staff:hrStaffPersonInfo.fdtag') }</span>
								<span id="span_tagNames"></span>
							</p>
							<% } %>
						</div>
					</div>
					<!-- 简历 头部 Ends -->
					
					<div printTr="true" style="border: none;">
					
					<%
						for(String cfg : showConfig) {
							if("info".equals(cfg)) { // 员工信息
					%>
							<div>
								<div class="tr_label_title">
									<div class="title"><bean:message bundle="hr-staff" key="table.HrStaffPersonInfo" /></div>
								</div>
								<table class="tb_normal" width=100%>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgParent" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdOrgParentsName }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus" />
										</td>
										<td width="35%">
											<sunbor:enumsShow value="${ hrStaffPersonInfoForm.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" />
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgPosts" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdOrgPostNames }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffingLevel" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdStaffingLevelName }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdName }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSex" />
										</td>
										<td width="35%">
											<sunbor:enumsShow value="${ hrStaffPersonInfoForm.fdSex }" enumsType="sys_org_person_sex" />
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfBirth" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdDateOfBirth }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAge" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdAge }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffNo" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdStaffNo }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIdCard" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdIdCard }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkTime" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdWorkTime }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdUninterruptedWorkTime" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdUninterruptedWorkTime }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTimeOfEnterprise" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdTimeOfEnterprise }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkingYears" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdWorkingYears }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialExpirationTime" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdTrialExpirationTime }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmploymentPeriod" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdEmploymentPeriod }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffType" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdStaffType }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNameUsedBefore" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdNameUsedBefore }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNation" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdNation }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPoliticalLandscape" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdPoliticalLandscape }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfGroup" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdDateOfGroup }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfParty" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdDateOfParty }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestEducation" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdHighestEducation }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestDegree" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdHighestDegree }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdMaritalStatus" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdMaritalStatus }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHealth" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdHealth }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStature" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdStature }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWeight" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdWeight }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLivingPlace" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdLivingPlace }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNativePlace" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdNativePlace }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHomeplace" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdHomeplace }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAccountProperties" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdAccountProperties }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdRegisteredResidence" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdRegisteredResidence }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResidencePoliceStation" />
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdResidencePoliceStation }
										</td>
									</tr>
									<%-- 引入动态属性 --%>
									<c:import url="/sys/property/custom_field/custom_fieldView.jsp" charEncoding="UTF-8" />
								</table>
							</div>
					<%
							}
							if("contact".equals(cfg)) { // 联系信息
					%>
							<div>
								<div class="tr_label_title">
									<div class="title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.contactInfo" /></div>
								</div>
								<table class="tb_normal" width=100%>
									<tr>
										<td width="15%" class="td_normal_title">
											${ lfn:message('hr-staff:hrStaffPersonInfo.fdMobileNo') }
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdMobileNo }
										</td>
										<td width="15%" class="td_normal_title">
											${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmail') }
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdEmail }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeLocation') }
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdOfficeLocation }
										</td>
										<td width="15%" class="td_normal_title">
											${ lfn:message('hr-staff:hrStaffPersonInfo.fdWorkPhone') }
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdWorkPhone }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContact') }
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdEmergencyContact }
										</td>
										<td width="15%" class="td_normal_title">
											${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContactPhone') }
										</td>
										<td width="35%">
											${ hrStaffPersonInfoForm.fdEmergencyContactPhone }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											${ lfn:message('hr-staff:hrStaffPersonInfo.fdOtherContact') }
										</td>
										<td width="85%" colspan="3">
											${ hrStaffPersonInfoForm.fdOtherContact }
										</td>
									</tr>
								</table>
							</div>
					<%						
							}
							if("experience".equals(cfg)) { // 个人经历
					%>
							<div>
								<div class="tr_label_title">
									<div class="title"><bean:message bundle="hr-staff" key="table.hrStaffPersonExperience" /></div>
								</div>
								<table class="tb_normal" width=100%>
									<div class="lui_tabpage_float_content_l">
										<div class="lui_tabpage_float_content_r">
											<div class="lui_tabpage_float_content_c">
												<div>
													<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
														<!-- 个人经历 列表 Starts -->
														<div id="personExperiences" class="staff_resume_itemlist_content">
															<dl>
																<dt>
																	<h3 class="reusme_item_title" id="experienceContract">
																		&nbsp;&nbsp;<span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.contract') }</span>
																	</h3>
																</dt>
																<dd>
																	<!-- 合同信息 Starts -->
																	<c:import url="/hr/staff/hr_staff_person_experience/import/contract_view.jsp" charEncoding="UTF-8">
																		<c:param name="personInfoId" value="${param.fdId}" />
																		<c:param name="isPrint" value="true" />
																	</c:import>
																	<!--合同信息 End-->
																</dd>
																<dt>
																	<h3 class="reusme_item_title" id="experienceWork">
																		&nbsp;&nbsp;<span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.work') }</span>
																	</h3>
																</dt>
																<dd>
																	<!-- 工作经历 Starts -->
																	<c:import url="/hr/staff/hr_staff_person_experience/import/work_view.jsp" charEncoding="UTF-8">
																		<c:param name="personInfoId" value="${param.fdId}" />
																		<c:param name="isPrint" value="true" />
																	</c:import>
																	<!--工作经历 End-->
																</dd>
																<dt>
																	<h3 class="reusme_item_title" id="experienceEducation">
																		&nbsp;&nbsp;<span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.education') }</span>
																	</h3>
																</dt>
																<dd>
																	<!-- 教育经历 Starts -->
																	<c:import url="/hr/staff/hr_staff_person_experience/import/education_view.jsp" charEncoding="UTF-8">
																		<c:param name="personInfoId" value="${param.fdId}" />
																		<c:param name="isPrint" value="true" />
																	</c:import>
																	<!-- 教育经历 End-->
																</dd>
																<dt>
																	<h3 class="reusme_item_title" id="experienceTraining">
																		&nbsp;&nbsp;<span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.training') }</span>
																	</h3>
																</dt>
																<dd>
																	<!-- 培训记录 Starts -->
																	<c:import url="/hr/staff/hr_staff_person_experience/import/training_view.jsp" charEncoding="UTF-8">
																		<c:param name="personInfoId" value="${param.fdId}" />
																		<c:param name="isPrint" value="true" />
																	</c:import>
																	<!-- 培训记录 End-->
																</dd>
																<dt>
																	<h3 class="reusme_item_title" id="experienceQualification">
																		&nbsp;&nbsp;<span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.qualification') }</span>
																	</h3>
																</dt>
																<dd>
																	<!-- 资格证书 Starts -->
																	<c:import url="/hr/staff/hr_staff_person_experience/import/qualification_view.jsp" charEncoding="UTF-8">
																		<c:param name="personInfoId" value="${param.fdId}" />
																		<c:param name="isPrint" value="true" />
																	</c:import>
																	<!-- 资格证书 End-->
																</dd>
																<dt>
																	<h3 class="reusme_item_title" id="experienceBonusMalus">
																		&nbsp;&nbsp;<span>${ lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }</span>
																	</h3>
																</dt>
																<dd>
																	<!-- 奖励信息 Starts -->
																	<c:import url="/hr/staff/hr_staff_person_experience/import/bonusMalus_view.jsp" charEncoding="UTF-8">
																		<c:param name="personInfoId" value="${param.fdId}" />
																		<c:param name="isPrint" value="true" />
																	</c:import>
																	<!-- 奖励信息 End -->
																</dd>
															</dl>
														</div>
														<!-- 个人经历 列表 Ends -->
													</div>
													<div data-lui-mark="panel.content.operation" class="lui_portlet_operations clearfloat"> </div>
												</div>
											</div>
										</div>
									</div>
								</table>
							</div>
					<%
							}
							if("emolument".equals(cfg)) { // 薪酬福利
					%>
							<div>
								<div class="tr_label_title">
									<div class="title"><bean:message bundle="hr-staff" key="table.hrStaffEmolumentWelfare" /></div>
								</div>
								<table class="tb_normal" width=100%>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollName" />
										</td>
										<td width="35%">
											${ hrStaffEmolumentWelfare.fdPayrollName }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollBank" />
										</td>
										<td width="35%">
											${ hrStaffEmolumentWelfare.fdPayrollBank }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollAccount" />
										</td>
										<td width="35%">
											${ hrStaffEmolumentWelfare.fdPayrollAccount }
										</td>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSurplusAccount" />
										</td>
										<td width="35%">
											${ hrStaffEmolumentWelfare.fdSurplusAccount }
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSocialSecurityNumber" />
										</td>
										<td colspan="3">
											${ hrStaffEmolumentWelfare.fdSocialSecurityNumber }
										</td>
									</tr>
								</table>
							</div>
					<%
							}
						}
					%>
					</div>
				</div>
			</center>
		</form>
		<script>
		function outputPDF() {
			seajs.use(['lui/export/export'],function(exp) {
				exp.exportPdf(document.getElementById('printTable'),{
					title:'${ hrStaffPersonInfoForm.fdName }'
				});
			});
		}
		</script>
	</template:replace>
</template:include>
