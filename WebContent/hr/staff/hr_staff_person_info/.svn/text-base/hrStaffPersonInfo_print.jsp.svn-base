<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/person_info.css?s_cache=${LUI_Cache}"/>
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
				showConfig.add("postInfo");//在职信息
				showConfig.add("staffStatus");//员工状态
				showConfig.add("trackRecord");//任职记录
				showConfig.add("leaveInfo");//在职信息
				showConfig.add("familyInfo");//家庭信息
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
			                  </kmss:authShow>,{id:'staffStatus',name:'员工状态'},{id:'trackRecord',name:'任职记录'},{id:'postInfo',name:'在职信息'},{id:'familyInfo',name:'家庭信息'}
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

/* 			function setTagNames(names) {
				var tagNames = [];
				$.each(names.split(" "), function(i, n){
					tagNames.push(n);
				});
				$("#span_tagNames").empty().append(tagNames.join(", "));
			} */

			LUI.ready(function() {
				setTagNames("${hrStaffPersonInfoForm.sysTagMainForm.fdTagNames}");
			});
		</script>
	  	  <script type="text/javascript">
				var tag_params = {
					"model" : "view",
					"fdTagNames" : "${fn:escapeXml(hrStaffPersonInfoForm.sysTagMainForm.fdTagNames)}",
					"render" : "drawStaffPersonTag"
				};
				Com_IncludeFile("dialog.js");
				Com_IncludeFile("tag.js", "${LUI_ContextPath}/sys/tag/resource/js/", "js", true);
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
				min-width: 980px;
				max-width:1200px;
				margin-bottom: 20px;
			}
			
			#printTable .tb_normal>tbody>tr>td {
				border: 1px #e3e3e3 solid !important;
			}
			#printTable .tb_normal>tbody>tr{
				border: none !important;
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
				body{
					page-break-inside:avoid;
				}
				table {
					page-break-inside:avoid;
				}
			}
			.lui-personnel-file-baseInfo-box{
				background-size:cover;
			}
			.lui-personnel-file-baseInfo-box-info{
				min-width:980px;
				max-width:1200px;
				margin:0 auto;
			}
			.lui-personnel-file-staffInfo{
				min-width:980px;
				max-width:1200px;
			}
			.lui-personnel-file-staffInfo table{
				width:100%;
				margin-left:30px;
			}
			.lui-split-line{
				margin-left:30px;
				height:2px;
				border-bottom:1px solid #f2f2f2;
				margin:20px 0;
			}
			.lui-personnel-file-staffInfo .borderTable{
				min-width:100%;
				max-width:100%;
			}
			.staff_resume_itemlist_content dl dd{
				border-left:none;
				padding:0;
				margin-left:30px;
				margin-top:20px;
			}
			.staff_resume_itemlist_content dl dt{
				margin-top:20px;
				margin-left:25px;
			}
			.staff_resume_itemlist_content .reusme_item_title span{
				font-size: 16px;
				color: #666666!important;
			}
		.lui-personnel-file-baseInfo-box{
			padding:0!important;
			min-width:100%!important;
			max-width:100%!important;
			top:43px;
			z-index:28;
			background: url('../resource/images/baseInfo_bg.jpg') no-repeat center center;
			background-size:cover;
		}
		.lui-personnel-file-baseInfo-box-info{
			height:140px;
		}
		.tagsTitle{
			text-align:left;
			margin-bottom:0;
			float:left;
		}
		.tagsList{
			float:left;
			margin-left:40px;
		}
		.tagsList span{
			line-height:20px;
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
					<div class="lui-personnel-file-baseInfo-box">
				      <div class="lui-personnel-file-baseInfo-box-info">
				        <div class="lui-personnel-file-baseInfo-avatar">
				        	<%
								pageContext.setAttribute("s_time", System.currentTimeMillis());
							%>
				            <div class="lui-personnel-file-baseInfo-avatar-box">
								<img src="${LUI_ContextPath}/sys/person/resource/images/head${not empty hrStaffPersonInfoForm.fdSex?(hrStaffPersonInfoForm.fdSex eq 'M'?"_man":"_lady"):""}.png"/>
								<span><i class="lui-person-info-sex-${not empty hrStaffPersonInfoForm.fdSex?(hrStaffPersonInfoForm.fdSex eq 'M'?"male":"female"):""}"></i></span>
				            </div>
				            <div class="lui-personnel-file-baseInfo-detail">
				              <div class="userInfo">
				                <span class="userInfo-name">${hrStaffPersonInfoForm.fdName}</span>
				                <span class="userInfo-workType">
				                	<c:if test="${not empty hrStaffPersonInfoForm.fdStatus}">
				                		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.${hrStaffPersonInfoForm.fdStatus}"/>
				                	</c:if>
				                </span>
				              </div>
				              <div class="userInfo-duty">
				                	${hrStaffPersonInfoForm.fdOrgParentName } ${hrStaffPersonInfoForm.fdOrgPostNames}
				              </div>
				            </div> 
				        </div>
				        <div class="lui-personnel-file-baseInfo-service">
				            <div class="serveicTitle">
				              <span>本公司工龄</span>
				            </div>
				            <div class="serviceLength">
				              <span class="serviceYear">${not empty hrStaffPersonInfoForm.fdWorkingYears?hrStaffPersonInfoForm.fdWorkingYears:"-" }</span>
				             <!--  <span class="serviceMonth">6</span>月 -->
				            </div>
				        </div>
				        <% if(com.landray.kmss.hr.staff.util.HrStaffPersonUtil.isShowLabel(request)) { %>
				        <div class="lui-personnel-file-baseInfo-tags">
			 	          <div class="tagsTitle">
				            <span>${ lfn:message('hr-staff:hrStaffPersonInfo.fdtag') }</span>
				          </div>
				          <div class="tagsList"></div> 
				         <% } %> 
				        </div>
				      </div>
				    </div>
				<div id="printTable" style="border: none; font-size: 12px;">
					<div printTr="true" style="border: none;">
					<%
						for(String cfg : showConfig) {
							if("info".equals(cfg)) { // 员工信息
					%>
							<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_personInfo.jsp" charEncoding="UTF-8">
								<c:param name="print" value="true"/>
							</c:import>
					<%
							}
							if("postInfo".equals(cfg)) { // 在职信息
					%>		
				      	<c:choose>
				      	<c:when test="${(hrStaffPersonInfoForm.fdStatus != 'leave')&&(hrStaffPersonInfoForm.fdStatus != 'retire')&&(hrStaffPersonInfoForm.fdStatus != 'dismissal')}">							
							<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_workingInfo.jsp" charEncoding="UTF-8">
								<c:param name="print" value="true"/>
							</c:import>
						</c:when>
        				<c:otherwise>
        					<!-- 离职信息 -->
							<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_leaveOffice.jsp" charEncoding="UTF-8">
								<c:param name="print" value="true"/>
							</c:import>
						</c:otherwise>
						</c:choose>
					<%
							}
							if("staffStatus".equals(cfg)) { // 员工状态
					%>
							<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_staffStatus.jsp" charEncoding="UTF-8">
								<c:param name="print" value="true"/>
							</c:import>
					<%
							}
							if("contact".equals(cfg)) { // 联系信息
					%>
							<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_connect.jsp" charEncoding="UTF-8">
								<c:param name="print" value="true"/>
							</c:import>	
					<%
							}
							if("trackRecord".equals(cfg)) { // 任职记录
					%>
							<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_recordJob.jsp" charEncoding="UTF-8">
								<c:param name="print" value="true"/>
								<c:param name="personInfoId" value="${param.fdId }"/>
							</c:import>
					<%
							}
							if("familyInfo".equals(cfg)) { // 家庭信息
					%>
							<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_familyInfo.jsp" charEncoding="UTF-8">
								<c:param name="print" value="true"/>
								<c:param name="personInfoId" value="${param.fdId }"/>
							</c:import>								
					
					<%						
							}
							if("experience".equals(cfg)) { // 个人经历
					%>
							<div>
								<div class="tr_label_title">
									<div class="title"><bean:message bundle="hr-staff" key="table.hrStaffPersonExperience" /></div>
								</div>
								<div class="lui-split-line"></div>
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
							<div class="lui-personnel-file-staffInfo">
								<div class="tr_label_title">
									<div class="title"><bean:message bundle="hr-staff" key="table.hrStaffEmolumentWelfare" /></div>
								</div>
								<table  width=100%>
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

			if (window.tag_opt == null) {
				window.tag_opt = new TagOpt('com.landray.kmss.sys.zone.model.SysZonePersonInfo', 
						'${hrStaffPersonInfoForm.fdId}', '', tag_params);
			}
			// 绘制标签
			function drawStaffPersonTag(rtns){
				var html = "";
				var classname = "class='tag_tagSignSpecial'";
				for (var i = 0; i < rtns.length; i++) {
					var rtn = rtns[i];
					html += "<span><a href='" + rtn.href + "' "
							+ (rtn.isSpecial == 1 ? classname : "")
							+ " target='_blank'><label>" + rtn.text
							+ "</label></a></span>";
				}
				$(".lui-personnel-file-baseInfo-tags-edit").html("<span class='hr_staff_btn' onclick='addTags();'><span>${lfn:message('hr-staff:hrStaffPerson.editTags')}</span></span>");
				$(".lui-personnel-file-baseInfo-tags-edit").html(html);
			}
			function addTags() {
				seajs.use([ 'lui/dialog' ],
					function(dialog) {
						dialog.iframe(
							"/sys/tag/sys_tag_main/sysTagMain.do?method=editTag&fdModelId=${hrStaffPersonInfoForm.fdId}&fdModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo&fdQueryCondition=${hrStaffPersonInfoForm.sysTagMainForm.fdTagNames}",
							"${lfn:message('sys-tag:sysTagMain.edit')}",
							null, {
								width : 500,
								height : 250
							});
					});
			}

			function setTagNames(names) {
				$("#span_tagNames").empty();
				if(names){
					$.each(names.split(";"), function(i, n){
						$(".tagsList").append("<span><label>" + n + "</label></span>");
					});
				}
			}

			LUI.ready(function() {
				window.tag_opt.onload();
			});
		</script>
	</template:replace>
</template:include>
