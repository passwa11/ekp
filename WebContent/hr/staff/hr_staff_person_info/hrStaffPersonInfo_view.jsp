<%@page import="com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm"%>
<%@page import="com.landray.kmss.hr.organization.model.HrOrganizationPost"%>
<%@page import="com.landray.kmss.hr.organization.service.IHrOrganizationElementService"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
	if(StringUtil.isNotNull(request.getParameter("readOnly"))&&request.getParameter("readOnly").equals("true")){
		request.setAttribute("readOnly",true);
	}
%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		<c:if test="${not empty hrStaffPersonInfoForm.fdName }">
			${ hrStaffPersonInfoForm.fdName } -
		</c:if>
		 ${ lfn:message('hr-staff:module.hr.staff') }
	</template:replace>
	<template:replace name="head">
	  <link rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/common_view.css">
  	  <link rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/person_info.css">
  	  <script type="text/javascript">
			var tag_params = {
				"model" : "view",
				"fdTagNames" : "${fn:escapeXml(hrStaffPersonInfoForm.sysTagMainForm.fdTagNames)}",
				"render" : "drawStaffPersonTag"
			};
			Com_IncludeFile("dialog.js");
			Com_IncludeFile("tag.js", "${LUI_ContextPath}/sys/tag/resource/js/", "js", true);
	  </script>
	</template:replace>
	<template:replace name="toolbar">
	<c:if test="${param.workbench }">
		<style>
			.lui_form_path_frame{
				top:0px!important;
			}
		</style>
	</c:if>
	<c:if test="${!param.workbench }">
		<div class="sysuimenunav">
			<ui:menu layout="sys.ui.menu.nav"  id="categoryId">
				<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/"></ui:menu-item>
				<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff"></ui:menu-item>
				<ui:menu-item text="${ lfn:message('hr-staff:table.HrStaffPersonInfo') }"></ui:menu-item>
			</ui:menu>
		</div>

		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:if test="${!readOnly}">
			<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
				<c:if test="${hrStaffPersonInfoForm.fdStatus != 'leave' && hrStaffPersonInfoForm.fdStatus != 'retire' && hrStaffPersonInfoForm.fdStatus != 'dismissal' }">
					<%-- <c:choose>
						<c:when test="${empty hrStaffPersonInfoForm.fdOrgPersonId }">
							<ui:button text="开通账号"
								onclick="openAccount();" order="1" />
						</c:when>
						<c:otherwise>
							<ui:button text="修改在职信息"
								onclick="openAccount();" order="1" />
						</c:otherwise>
					</c:choose> --%>
					<c:if test="${not empty hrStaffPersonInfoForm.fdOrgPersonId }">
						<ui:button text="${ lfn:message('sys-organization:sysOrgPerson.button.changePassword')}"
							onclick="Com_OpenWindow('${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=chgPwd&fdId=${hrStaffPersonInfoForm.fdOrgPersonId}','_bank');" order="2" />
					</c:if>
				</c:if>
				<ui:button text="${lfn:message('button.edit')}"
					onclick="Com_OpenWindow('${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=edit&fdId=${param.fdId}','_self');" order="3">
				</ui:button>
			</kmss:authShow>
			<kmss:authShow roles="ROLE_HRSTAFF_PRINT">
			<ui:button text="${lfn:message('button.print')}" order="4" onclick="Com_OpenWindow('${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=print&fdId=${param.fdId}','_blank');">
			</ui:button>
			</kmss:authShow>
			<c:if test="${empty hrStaffPersonInfoForm.fdOrgPersonId || isAvailable == false}">
				<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
				<ui:button text="${lfn:message('button.delete')}" order="5" onclick="deleteInfo('${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=delete&fdId=${param.fdId}');">
				</ui:button>
				</kmss:authShow>
			</c:if>
			<c:if test="${!empty hrStaffPersonInfoForm.fdOrgPersonId}">
				<ui:button text="${lfn:message('hr-staff:hr.staff.btn.zone')}"
					onclick="Com_OpenWindow('${LUI_ContextPath}/sys/zone/index.do?userid=${param.fdId}','_blank');" order="6">
				</ui:button>
			</c:if>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</c:if>
	</template:replace>
	<template:replace name="path">
		
		<div class="lui-personnel-file-baseInfo-box">
	      <div class="lui-personnel-file-baseInfo-box-info">
	        <div class="lui-personnel-file-baseInfo-avatar">
	        	<%
					pageContext.setAttribute("s_time", System.currentTimeMillis());
				%>
	            <div class="lui-personnel-file-baseInfo-avatar-box">
	              <img src="${LUI_ContextPath}/sys/person/image.jsp?personId=${hrStaffPersonInfoForm.fdOrgPersonId}&size=b&s_time=${s_time}"/>
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
	                <c:if test="${hrToEkpEnable == true && not empty hrStaffPersonInfoForm.fdPostIds}">
	                	<%
	                		HrStaffPersonInfoForm hrStaffPersonInfoForm = (HrStaffPersonInfoForm)request.getAttribute("hrStaffPersonInfoForm");
	                		IHrOrganizationElementService hrOrganizationElementService = (IHrOrganizationElementService)SpringBeanUtil.getBean("hrOrganizationElementService");
	                	    HrOrganizationPost post= (HrOrganizationPost)hrOrganizationElementService.findByPrimaryKey(hrStaffPersonInfoForm.getFdPostIds());
	                	    request.setAttribute("fdPost", post);
	                	%>
	                	<c:if test="${fdPost.fdIsKey == 'true' }">
		                	<span class="userInfo-workType">
		                		关键岗位
		                	</span>
	                	</c:if>
	                	<c:if test="${fdPost.fdIsSecret == 'true' }">
		                	<span class="userInfo-workType">
		                		涉密岗位
		                	</span>
	                	</c:if>
	                </c:if>
	              </div>
	              <div class="userInfo-duty" title="${hrStaffPersonInfoForm.fdOrgParentName } ${hrStaffPersonInfoForm.fdOrgPostNames}">
	                	${hrStaffPersonInfoForm.fdParentName } ${hrStaffPersonInfoForm.fdPostNames}
	              </div>
	            </div> 
	        </div>
	        <div class="lui-personnel-file-baseInfo-service">
	            <div class="serveicTitle">
	              <span><bean:message key="hrStaffPersonInfo.fdWorkingYears" bundle="hr-staff"/></span>
	            </div>
	            <div class="serviceLength">
	              <span class="serviceYear">${not empty hrStaffPersonInfoForm.fdWorkingYears?hrStaffPersonInfoForm.fdWorkingYears:"-" }</span>
	             <!--  <span class="serviceMonth">6</span>月 -->
	            </div>
	        </div>
	        <kmss:auth requestURL="/sys/tag/sys_tag_main/sysTagMain.do?method=editTag&fdModelId=${hrStaffPersonInfoForm.fdId }&fdModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo">
	        <% if(com.landray.kmss.hr.staff.util.HrStaffPersonUtil.isShowLabel(request)) { %>
	        <div class="lui-personnel-file-baseInfo-tags">
 	          <div class="tagsTitle">
	            <span>${ lfn:message('hr-staff:hrStaffPersonInfo.fdtag') }</span>
				<c:if test="${!readOnly}">
	            	<span class="lui-personnel-file-baseInfo-tags-edit">${lfn:message('button.edit')}</span>
	            <!-- <span class="lui-print" style="margin-left:10px;cursor: pointer" onclick="printpage()">打印(临时打印按钮)</span> -->
				</c:if>
	          </div>
	          <div class="tagsList"></div> 
	         <% } %> 
	        </div>
	        </kmss:auth>
	      </div>
	    </div>
	
	</template:replace>	
	<template:replace name="content"> 
	  <div class="lui-personnel-file">
	    <!-- main -->
	    <div class="lui-personnel-file-baseInfo-main">
	      <div class="lui-personnel-file-baseInfo-main-aside">
	        <ul class="titleList">
				<li class="staffInfoLi"><a href="javascript:void(0);"><bean:message key="mobile.hr.staff.view.2" bundle="hr-staff"/></a></li>
	          <c:if test="${(hrStaffPersonInfoForm.fdStatus != 'leave')&&(hrStaffPersonInfoForm.fdStatus != 'retire')&&(hrStaffPersonInfoForm.fdStatus != 'dismissal')}">
	          	<li class="dutyInfoLi"><a href="javascript:void(0);"><bean:message key="mobile.hr.staff.view.4" bundle="hr-staff"/></a></li>
	          </c:if>
	          <c:if test="${(hrStaffPersonInfoForm.fdStatus == 'leave' )||( hrStaffPersonInfoForm.fdStatus =='retire')||(hrStaffPersonInfoForm.fdStatus =='dismissal')}">
	          	<li class="departureInfoLi"><a href="javascript:void(0);"><bean:message key="mobile.hr.staff.view.5" bundle="hr-staff"/></a></li>
	          </c:if>
	          <li class="staffStatusLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonInfo.fdStatus" bundle="hr-staff"/></a></li>
	          <li class="contactInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonInfo.contactInfo" bundle="hr-staff"/></a></li>
	          <kmss:authShow roles="ROLE_HRSTAFF_EXPERIENCE">
	          	<li class="familyInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPerson.family" bundle="hr-staff"/></a></li>
	          	<li class="onDutyLi"><a href="javascript:void(0);"><bean:message key="hrStaffTrackRecord.record" bundle="hr-staff"/></a></li>
	          	<li class="personnelExperienceLi"><a href="javascript:void(0);"><bean:message key="table.hrStaffPersonExperience" bundle="hr-staff"/></a></li>
	          	<li class="contractInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonExperience.type.contract" bundle="hr-staff"/></a></li>
	          	<li class="rewardAndPunishmentInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonExperience.type.bonusMalus" bundle="hr-staff"/></a></li>
	          </kmss:authShow>
	          <kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
	          	<li class="personnelSalaryLi"><a href="javascript:void(0);"><bean:message key="hr.staff.nav.benefits" bundle="hr-staff"/></a></li>
	          </kmss:authShow>
	          <kmss:authShow roles="ROLE_HRSTAFF_ATTENDANCE">
	          	<li class="personnelHolidayLi"><a href="javascript:void(0);"><bean:message key="table.hrStaffAttendanceManage" bundle="hr-staff"/></a></li>
	          </kmss:authShow>
	          <kmss:authShow roles="ROLE_HRSTAFF_LOG_VIEW">
	          	<li class="staffDynamicLi"><a href="javascript:void(0);"><bean:message key="porlet.employee.dynamic" bundle="hr-staff"/></a></li>
	          </kmss:authShow>
	        </ul>
	        <div class="verticalLine"></div>
	      </div>
	      <div class="lui-personnel-file-baseInfo-main-content">
	        <!-- 个人信息 -->
			<jsp:include page="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_personInfo.jsp" flush="true">
				<jsp:param name= "readOnly" value= "${readOnly}" />
			</jsp:include>
			<c:if test="${(hrStaffPersonInfoForm.fdStatus != 'leave')&&(hrStaffPersonInfoForm.fdStatus != 'retire')&&(hrStaffPersonInfoForm.fdStatus != 'dismissal')}">
	        <!-- 在职信息 -->
				<jsp:include page="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_workingInfo.jsp" flush="true">
					<jsp:param name= "readOnly" value= "${readOnly}" />
				</jsp:include>
			</c:if>
			<c:if test="${(hrStaffPersonInfoForm.fdStatus == 'leave' )||( hrStaffPersonInfoForm.fdStatus =='retire')||(hrStaffPersonInfoForm.fdStatus =='dismissal')}">
	         <!-- 离职信息 -->
				<jsp:include page="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_leaveOffice.jsp" flush="true">
					<jsp:param name= "readOnly" value= "${readOnly}" />
				</jsp:include>
			</c:if>
	        <!-- 员工状态 -->
			  <jsp:include page="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_staffStatus.jsp" flush="true">
				  <jsp:param name= "readOnly" value= "${readOnly}" />
			  </jsp:include>
	        <!-- 联系信息 -->
			  <jsp:include page="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_connect.jsp" flush="true">
				  <jsp:param name= "readOnly" value= "${readOnly}" />
			  </jsp:include>
	        <kmss:authShow roles="ROLE_HRSTAFF_EXPERIENCE">
				<!-- 家庭信息 -->
				<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_familyInfo.jsp" charEncoding="UTF-8">
					<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name= "readOnly" value= "${readOnly}" />
				</c:import>
		        <!-- 任职记录 -->
				<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_recordJob.jsp" charEncoding="UTF-8">
					<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name= "readOnly" value= "${readOnly}" />
				</c:import>
		         <!-- 个人经历 -->
		        <c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_workExper.jsp" charEncoding="UTF-8">
					<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name="anchor" value="${param.anchor}" />
					<c:param name= "readOnly" value= "${readOnly}" />
				</c:import>
		         <!-- 合同信息 -->
					<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_docInfo.jsp" charEncoding="UTF-8">
		        	<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name="anchor" value="${param.anchor}" />
					<c:param name= "readOnly" value= "${readOnly}" />
		        </c:import>
		         <!-- 奖惩信息 -->
		        <c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_reward.jsp" charEncoding="UTF-8">
		        	<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name="anchor" value="${param.anchor}" />
					<c:param name= "readOnly" value= "${readOnly}" />
		        </c:import>
			</kmss:authShow>
			<kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
	         	<!-- 薪酬福利 -->
				<c:import url="/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=getByPerson&personInfoId=${hrStaffPersonInfoForm.fdId}" charEncoding="UTF-8" >
					<c:param name="paramKey" value="welfare" />
				</c:import>
			</kmss:authShow>
	         <kmss:authShow roles="ROLE_HRSTAFF_ATTENDANCE">
	         	<!-- 个人假期 -->
			 	<%@ include file="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_vacation.jsp"%>
			 </kmss:authShow>
	         <!-- 员工动态 -->
	         <kmss:authShow roles="ROLE_HRSTAFF_LOG_VIEW">
	         	<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_staffRecord.jsp" charEncoding="UTF-8">
	         		<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
	         	</c:import>
			 </kmss:authShow>
	      </div>
	    </div>
	  </div>
	  
	<input id="personInfoId" type="hidden" value="${hrStaffPersonInfoForm.fdId}"/>
	<script src="${LUI_ContextPath}/hr/staff/resource/js/personInfo_part.js"></script>
		<style>
			.sysuimenunav{
				max-width:1200px;
				width:200px;
				padding:4px 0;
				margin-left:5%;
				position: fixed;
				top: 0;
				z-index: 28;
				left: 0;
			}

			.lui_form_path_frame{
				padding:0!important;
				min-width:100%!important;
				max-width:100%!important;
				position:fixed;
				top:43px;
				z-index:28;
				background: url('${LUI_ContextPath}/hr/staff/resource/images/baseInfo_bg.jpg') no-repeat center center;
				background-color:#E3EDF9;
				background-size:cover;
			}
			.lui-personnel-file-baseInfo-box-info {
				max-width:1200px;
				margin:0 auto;
				height:100%;
			}
			.lui-personnel-file-baseInfo-box{
				max-width:1200px;
				min-width:980px;
				margin:0 auto;
			}
			body{
				margin-top:0!important;
			}
			.lui_form_content{
				margin-top:180px;
				padding:0;
			}
			.upload_list_filename_view{
				line-height: 18px;
			}
		</style>
	<script>
		seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
			window.deleteInfo = function(delUrl){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(isOk) {
					if(isOk){
						Com_OpenWindow(delUrl,'_self');
					}	
				});
				return;
			};
			
			window.changePwd = function(fdId){
				
			};
	     });
	
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
				$(".tagsList").html(html);
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
				$.each(names.split(" "), function(i, n){
					$(".tagsList").append("<label>" + n + "</label>");
				});
			}
			function getIndex(){
				var showType ="${param.anchor}";
				if('experienceContract'==showType){
					return 7;
				}else if('experienceEducation'==showType || 'experienceQualification'==showType || 'experienceTraining'==showType || 'experienceWork'==showType ){
					return 6;
				}else if('experienceBonusMalus'==showType){
					return 8;
				}else if('experienceFamily'==showType){
					return 4;
				}else if('experienceTrackRecord'==showType){
					return 5;
				}		
				
				return 0;
			}
			LUI.ready(function() {
				window.tag_opt.onload();
				//setTimeout(, 500);
				var showIndex =getIndex();
				if(showIndex > 0){
					window.initScorll(showIndex); 
				} 
				var __userlandArray=Com_Parameter.Lang.split('-');
				var __userland=__userlandArray[1];
				
				if(__userland=="us"){
					$(".titleList li a").removeClass("lui-personnel-file-active");
					
					$(".lui-personnel-file-baseInfo-main-aside").css("width","14%");
					$(".lui-personnel-file-baseInfo-main-aside .titleList").css("width","150px");
					//$(".lui-personnel-file-baseInfo-main-aside .titleList li a:before").css("right","-80px");
					$(".lui-personnel-file-baseInfo-main-aside .titleList li a").addClass('ustitleListli');
					$(".titleList li.lui-personnel-file-active a").addClass('ustitleListliafter');
					//$(".titleList li.lui-personnel-file-active c:after").css("right","-84px");
					//$(".lui-personnel-file-baseInfo-main-aside").addRule('a:after','right:-84px');
					$(".lui-personnel-file-baseInfo-main-content").css("width","85%");
					$(".lui-personnel-file-baseInfo-main-content").css("margin-left","7px");
					$(".lui-personnel-file-staffInfo").css("max-width","900px");
				}
				else{
					$(".lui-personnel-file-baseInfo-main-aside .titleList li a").addClass('titleListli');
					$(".titleList li.lui-personnel-file-active a").addClass('titleListliafter');
				}
				
				$(".lui-personnel-file-baseInfo-main-aside .titleList li").click(function(){
					if(__userland=="us"){
						$(".titleList li a").removeClass("ustitleListliafter");
						$(this).find("a").addClass('ustitleListliafter');
					}else{
						$(".titleList li a").removeClass("titleListliafter");
						$(this).find("a").addClass('titleListliafter');
					}
					
				});
			});
			
			function reSizeNav(){
				var __userlandArray=Com_Parameter.Lang.split('-');
				var __userland=__userlandArray[1];
				if(__userland=="us"){
					$(".lui-personnel-file-baseInfo-main-aside").css("width","14%");
					$(".lui-personnel-file-baseInfo-main-aside .titleList").css("width","150px");
					//$(".lui-personnel-file-baseInfo-main-aside .titleList li a:before").css("right","-80px");
					$(".lui-personnel-file-baseInfo-main-aside .titleList li a").addClass('ustitleListli');
					$(".titleList li.lui-personnel-file-active a").addClass('ustitleListliafter');
					//$(".titleList li.lui-personnel-file-active c:after").css("right","-84px");
					//$(".lui-personnel-file-baseInfo-main-aside").addRule('a:after','right:-84px');
					$(".lui-personnel-file-baseInfo-main-content").css("width","85%");
					$(".lui-personnel-file-baseInfo-main-content").css("margin-left","7px");
					$(".lui-personnel-file-staffInfo").css("max-width","900px");
				}
				else{
					$(".lui-personnel-file-baseInfo-main-aside .titleList li a").addClass('titleListli');
					$(".titleList li.lui-personnel-file-active a").addClass('titleListliafter');
				}
			}
	</script>
	</template:replace>
</template:include>