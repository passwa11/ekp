<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffConfig" %>
<%
	HrStaffConfig hrStaffConfig = new HrStaffConfig();
	boolean fdSelfView = "true".equals(hrStaffConfig.getFdSelfView());
	pageContext.setAttribute("fdSelfView", fdSelfView);
	boolean statisticsAuth = UserUtil.checkRole("ROLE_HRSTAFF_REPORT");
	pageContext.setAttribute("statisticsAuth", statisticsAuth);
	boolean selfViewAuth = UserUtil.checkAuthentication("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list","GET");
	pageContext.setAttribute("selfViewAuth", selfViewAuth);
%>

<template:include ref="mobile.list" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="mobile.hr.staff.index.1"/>
	</template:replace>
	<template:replace name="head">
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/index.css">
	   <script src="resource/js/rem.js"></script>
	   <style>
	   	#content{
	   		height:100%;
	   	}
	   </style>
	</template:replace>
	<template:replace name="content">
		<div class="file-overview-box">
			<%
				if(!UserUtil.checkAuthentication("/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list","GET")) {
			%>
				<iframe frameborder="no" border="0" scrolling="no" src="${LUI_ContextPath }/third/pda/resource/jsp/mobile/e403.jsp" width="100%;" height="100%;"></iframe>
			<%
				}
			%>
			<div class="file-overview-content">
				<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list" requestMethod="GET">
				  <!-- 搜索框 -->
				  <div data-dojo-type='<%=request.getContextPath()%>/hr/staff/mobile/resource/js/search/search.js'
					data-dojo-props="preUrl:'index'"
				  ></div>
				  <!-- 内容框 -->
				  <div class="file-overview-main">
					<!-- 在职数据 -->
					<div class="file-overview-data-show">
					  <!-- 人数标题 -->
					  <div class="file-overview-data-show-title fod-dsa-onpost">
						<div class="fod-dst-img">
						  <i class="fod-dst-img-icon"></i>
						  <span>
								 <bean:message bundle="hr-staff" key="mobile.hr.staff.index.2"/>
						  </span>
						</div>
						<div class="fod-dst-num">
						  <span class="count-span-onPost">
							1234
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <i class="fod-dst-icon-jump"></i>
						</div>
					  </div>
					  <!-- 全职员工 -->
					  <div class="file-overview-data-show-all">
						<div class="fod-dsa-all">
						  <span class="count-span-onFull">
							1000
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <div><bean:message bundle="hr-staff" key="mobile.hr.staff.index.4"/></div>
						</div>
						<div class="fod-dsa-official">
						  <span class="count-span-onFormal">
							890
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <div><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.official"/></div>
						</div>
						<div class="fod-dsa-trial">
						  <span class="count-span-onTrial">
							110
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <div><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.trial"/></div>
						</div>
					  </div>
					  <!-- 入职兼职实习员工 -->
					  <div class="file-overview-data-show-duties">
						<div class="fod-dsa-parttime">
						  <span class="count-span-onPart">
							110
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <div><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.parttime"/></div>
						</div>
						<div class="fod-dsa-practice">
						  <span class="count-span-onPractive">
							24
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <div><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.practice"/></div>
						</div>
						<div class="fod-dsa-monthEntry">
						  <span class="count-span-onEntry">
							10
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <div><bean:message bundle="hr-staff" key="mobile.hr.staff.index.5"/></div>
						</div>
					  </div>
					</div>
					<!-- 离职数据 -->
					<div class="file-overview-leava-data-show fod-dsa-monthLeave">
					  <div class="file-overview-data-show-title">
						<div class="fod-dst-img">
						  <i class="fod-dst-img-icon fod-dst-img-icon-lzyg"></i>
						  <span>
								<bean:message bundle="hr-staff" key="mobile.hr.staff.index.6"/>
						  </span>
						</div>
						<div class="fod-dst-num">
						  <span class="count-span-onLeave">
							66
							<b><bean:message bundle="hr-staff" key="mobile.hr.staff.index.3"/></b>
						  </span>
						  <i class="fod-dst-icon-jump"></i>
						</div>
					  </div>
					</div>
				  </div>
				</kmss:auth>
				<!-- 底栏 -->
		      <div class="file-overview-footer">
					<div>
					  <i class="fof-overview-icon fof-overview-icon-active"></i>
					  <span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.1"/></span>
					</div>
		        <kmss:authShow roles="ROLE_HRSTAFF_REPORT">
					<div onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/mobile/statistics.jsp'">
					  <i class="fof-data-icon"></i>
					  <span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.7"/></span>
					</div>
		        </kmss:authShow>
		        <c:choose>
			        <c:when test="${fdSelfView }">
			        	<div onclick="window.location.href='${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${KMSS_Parameter_CurrentUserId}'">
			          		<i class="fof-file-icon"></i>
			          		<span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.8"/></span>
			        	</div>
			        </c:when>
			        <c:otherwise>
			        	<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${KMSS_Parameter_CurrentUserId}">
			        		<div onclick="window.location.href='${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${KMSS_Parameter_CurrentUserId}'">
			          			<i class="fof-file-icon"></i>
			          			<span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.8"/></span>
			        		</div>
			        	</kmss:auth>
			        </c:otherwise>
		        </c:choose>
		      </div>
		    </div>
		  </div>
		  <script>
		  require(["dojo/ready","dojo/request","dojo/query","dojo/on"],function(ready,request,query,on){
			  //人数统计
			  var countUrl = "<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getHrStaffMobileIndex";
			  request(countUrl).then(function(response){
				  ready(function(){
					  try{
						  if(response){
							  var data = JSON.parse(response);
							  data.map(function(item){
								  try{
									  query(".count-span-"+item.name)[0].innerHTML=item.value+"<b><bean:message bundle='hr-staff' key='mobile.hr.staff.index.3'/></b>";
								  }catch(e){

								  }
							  })
						  }
					  }catch(e){

					  }

				  })
			  })
			on(query("div[class*=fod-dsa]"),"click",function(){
				var staffType = this.className.split("-").pop();
				var staffValue = query("span[class*=count-span]",this)[0].innerText.match(/\d+/g);
				var url = "./list.jsp?staffType="+staffType+"&staffTypeValue="+staffValue;

				window.location.href=encodeURI(url)
			})
		  })
			//设置连接
		  require(["dojo/ready"], function(ready) {
			  ready(function() {
			  	if(!${selfViewAuth}){
					if(${fdSelfView}){
						window.location.href='${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${KMSS_Parameter_CurrentUserId}';
					}else{
						if(${statisticsAuth}){
							window.location.href='<%=request.getContextPath()%>/hr/staff/mobile/statistics.jsp';
						}
					}
				}
			  });
		  });

		  </script>
	</template:replace>
</template:include>