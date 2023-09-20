<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="mobile.list" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.${param.staffType}" /><bean:message bundle="hr-staff" key="table.HrStaffPersonInfo"/>
	</template:replace>
	<template:replace name="head">
		  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/reset.css">		   
		 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/list.css">
		 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/criteria.css">
		 <script src="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/rem/rem.js"></script>
		<style>
			.mblEdgeToEdgeList{
				background-color: unset;
			}
			.statistics{
				font-size: 1.2rem;
  				color: #9DB0CE;
  				margin:.8rem 0 0rem 1.5rem;
			}
			html,body{
			    background: #f5f7fa!important;
			}
		</style>
	</template:replace>
	<template:replace name="content">
	<%
		request.setAttribute("mobileField", true);
		String fdNatureWork = HrStaffPersonUtil.buildPersonInfoSettingHtml("fdNatureWork", request);
	%>
	<div id="scrollView">
		<div class="file-overview-box">
		    <div class="file-overview-content">
			  <div data-dojo-type='<%=request.getContextPath()%>/hr/staff/mobile/resource/js/search/search.js'
			  	data-dojo-props="preUrl:'index',paramValue:'${param.staffType}'"
			  ></div>		
		      <div class="hr-list-criteria">
		      	<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/criteria/Criteria.js">
		      		<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/criteria/select/select.js"
		      			data-dojo-props="docSubject:'${lfn:message('hr-staff:mobile.hr.staff.list.1') }',data:'<%=fdNatureWork %>',fieldName:'fdNatureWork'"
		      		></div>
		      		<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/criteria/select/select.js"
		      			data-dojo-props="docSubject:'${lfn:message('hr-staff:hrStaffPersonInfo.fdSex') }',data:[{name:'${lfn:message('hr-staff:mobile.hr.staff.list.2') }',value:'M'},{name:'${lfn:message('hr-staff:mobile.hr.staff.list.3') }',value:'F'}],fieldName:'fdSex'"
		      		></div>
<%-- 		      		<c:if test="${param.staffType eq 'all' }">
			      		<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/criteria/select/select.js"
			      			data-dojo-props="defaultValue:'员工状态',data:[{}],fieldName:'fdSex'"
			      		></div>
		      		</c:if> --%>
	  				<div class="moreBtn">
	  					<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/criteria/moreButton/moreButton.js"
	  						data-dojo-props="subject:'${lfn:message('sys-ui:layout.tabpanel.collapse.more') }'"
	  					>	
	  						<div class="moreBtnBox">
	  							<div class="more-btn-item">
	  								<div class="more-btn-item-title">${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }</div>
	  								<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/criteria/moreButton/moreCheckBox.js"
	  									 data-dojo-mixins="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/criteria/moreButton/moreCheckBoxDate.js"
	  								></div>
	  							</div>
	 							<div class="more-btn-item">
						      			<div class="addressSubject"><span>${lfn:message('hr-staff:mobile.hr.staff.list.4') }</span></div>
							      		<div id="criteriaOrgPost" data-dojo-type="<%=request.getContextPath()%>/sys/mobile/js/mui/form/Address.js" 
						  					data-dojo-props="type:'ORG_TYPE_POST',idField:'fdOrgPostId' ,nameField:'fdOrgPostName' ,curNames:'',curIds:'',splitStr:';' ,showStatus:'edit'"></div>
					  				 							
	 							</div>
	 							<div class="more-btn-item">
					  					<div class="addressSubject"><span>${lfn:message('hr-staff:mobile.hr.staff.list.5') }</span></div>
							      		<div id="criteriaOrgDept" data-dojo-type="<%=request.getContextPath()%>/sys/mobile/js/mui/form/Address.js" 
						  					data-dojo-props="type:'ORG_TYPE_DEPT',idField:'fdOrgDeptIds' ,nameField:'fdOrgDeptNames' ,curNames:'',curIds:'',splitStr:';' ,showStatus:'edit'"></div>
	 							</div>
	  						</div>
	  					</div>
	  				</div> 					
		      	</div>
		      </div> 
		      <%-- 	<div class="statistics">共${param.staffTypeValue}人<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.${param.staffType}" />${param.staffType eq 'all'?"员工":'' }</div> --%>
		      	<%
		      		String staffType = request.getParameter("staffType");
		      		String urlParam = "";
		      		if(!staffType.equals("onpost") && !staffType.equals("parttime")){
		      			urlParam="&q._fdStatus="+staffType;
		      		}
		      		if(staffType.equals("parttime")){
		      			urlParam="&q._fdNatureWork=兼职";
		      		}
		      		if(staffType.equals("monthEntry")){
		      			urlParam="&q._fdType="+staffType+"&personStatus=all";
		      		}
		      		if(staffType.equals("monthLeave")){
		      			urlParam ="&q._fdType="+staffType+"&personStatus=all";
		      		}
		      		
		      		if(staffType.equals("all")){
		      			//urlParam="&q._fdStatus=official&q._fdStatus=temporary&q._fdStatus=trial&q._fdStatus=trialDelay";
		      			urlParam="&q._fdStatus=all";
		      		}
		      	%>
		      	<div  data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/list/StoreScrollableView">
					<ul
				    	data-dojo-type="mui/list/JsonStoreList"
				    	data-dojo-mixins="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/list/staffPersonInfoListMixin.js"
				    	data-dojo-props="initUrl:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list<%=urlParam %>',lazy:false"
				    >
					</ul>
				</div>	
			</div>
		</div>
	</div>

	</template:replace>
</template:include>