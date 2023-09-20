<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@ page import="com.landray.kmss.common.forms.ExtendForm" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeConfig" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeField" %>
<%@ page import="java.util.Map,java.util.Iterator,java.util.List,java.util.ArrayList" %>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffConfig" %>
<%
	HrStaffConfig hrStaffConfig = new HrStaffConfig();
	boolean fdSelfView = "true".equals(hrStaffConfig.getFdSelfView());
	pageContext.setAttribute("fdSelfView", fdSelfView);
%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		${ hrStaffPersonInfoForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/reset.css">
  		<link rel="stylesheet" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/person.css">
  		<script src="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/rem/rem.js"></script>
  		<style>
  			.muiListNoDataArea{
  				min-height:20rem;
  			}
  		</style>
	</template:replace>
	<template:replace name="content">
		<div class="personal_profile_box">
	    <div class="personal_profile_content">
	      <div class="personal_profile_bg">
	      </div>
	      <div class="personal_profile_content_message">
	        <!-- 个人信息 -->
	        <div class="personal_profile_userMessage">
	          <div class="ppu_avatar">
	            <img class="ppu_avatar_icon" alt="" src="<person:headimageUrl contextPath='true' size='m' personId='${hrStaffPersonInfoForm.fdOrgPersonId }'/>" />
	          </div>
	          <div class="ppu_name">
	            <span class="ppu_name-text">
	            	${hrStaffPersonInfoForm.fdName }
	            </span>
	            <span>
	            	${hrStaffPersonInfoForm.fdOrgParentName }${empty hrStaffPersonInfoForm.fdOrgPostNames?"":"-" }${hrStaffPersonInfoForm.fdOrgPostNames }
	            </span>
	          </div>
	          <c:set var="tagvalue" value="${fn:escapeXml(hrStaffPersonInfoForm.sysTagMainForm.fdTagNames)}" scope="request"></c:set>
	          <%
	          	String tags = request.getAttribute("tagvalue").toString();
	          	tags.replaceAll( "\'","");
	          	tags.replaceAll( "<","");
	          	tags.replaceAll( ">","");
	          %>
	          <kmss:auth requestURL="/sys/tag/sys_tag_main/sysTagMain.do?method=editTag&fdModelId=${hrStaffPersonInfoForm.fdId }&fdModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo">
	          <% if(com.landray.kmss.hr.staff.util.HrStaffPersonUtil.isShowLabel(request)) { %>
	          	<div class="ppu_label" data-dojo-type="hr/staff/mobile/resource/js/SysTag"
					data-dojo-props="modelId:'${hrStaffPersonInfoForm.fdId }',modelName:'com.landray.kmss.sys.zone.model.SysZonePersonInfo',tag:'<%=tags %>'">
				</div>
	          <% } %>
	          </kmss:auth>
	          <div class="ppu_bp">
	           <%--  <c:if test="${not empty hrStaffPersonInfoForm.fdDateOfBirth }"> --%>
		            <div class="ppu_bp_brithday_content">
		              <i class="ppu_bp-brithday"></i>
		              <c:set var="unknown" value="${lfn:message('hr-staff:hrStaffPersonReport.unknown') }"></c:set>
		              <span>
		              	${empty hrStaffPersonInfoForm.fdDateOfBirth?unknown:hrStaffPersonInfoForm.fdDateOfBirth}
		              </span>
		            </div>
	          <%--   </c:if> --%>
	           <%--  <c:if test="${not empty hrStaffPersonInfoForm.fdMobileNo }"> --%>
		            <div class="ppu_bp_phone_content">
		              <i class="ppu_bp_phone"></i>
		              <c:set var="emptyValue" value="${lfn:message('hr-staff:mobile.hr.staff.view.1') }"></c:set>
		              <span>
		              	${empty hrStaffPersonInfoForm.fdMobileNo?emptyValue:hrStaffPersonInfoForm.fdMobileNo}
		              </span>
		            </div>
	          <%--   </c:if> --%>
	          </div>
	        </div>
	        <!-- 个人信息 -->
	        <div class="personal_profile_content_card">
	        	<div class="ppc_c_title" onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=personInfo'">
	        		<div>
	        			<i class="ppc_c_title-icon"></i>
	        			<span><bean:message bundle="hr-staff" key="mobile.hr.staff.view.2"/></span>
	        		</div>
	        		<i class="ppc_c_detail_more-icon" ></i>
	        	</div>
	        	<div class="ppc_c_content">
	        		<div class="ppc_c_list">
	        			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName"/></div>
	        			<div class="ppc_c_list_body">
	        				${hrStaffPersonInfoForm.fdName }
	        			</div>
	        		</div>
	        		<div class="ppc_c_list">
	        			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSex"/></div>
	        			<div class="ppc_c_list_body">
	        				<c:set var="male" value="${lfn:message('hr-staff:mobile.hr.staff.list.2') }"></c:set>
	        				<c:set var="female" value="${lfn:message('hr-staff:mobile.hr.staff.list.3') }"></c:set>
	        				<c:if test="${not empty hrStaffPersonInfoForm.fdSex}">
             					${hrStaffPersonInfoForm.fdSex eq "M"?male:female}
             				</c:if>
	        			</div>
	        		</div>
	        		<div class="ppc_c_list">
	        			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAge"/></div>
	        			<c:set var="unWrite" value="${lfn:message('hr-staff:mobile.hr.staff.view.3') }"></c:set>
	        			<div class="ppc_c_list_body">
	        				${not empty hrStaffPersonInfoForm.fdAge?hrStaffPersonInfoForm.fdAge:unWrite}
	        			</div>
	        		</div>
	        		<div class="ppc_c_list">
	        			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTimeOfEnterprise"/></div>
	        			<div class="ppc_c_list_body">
	        				${hrStaffPersonInfoForm.fdTimeOfEnterprise }
	        			</div>
	        		</div>
	        		<div class="ppc_c_list">
	        			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkTime"/></div>
	        			<div class="ppc_c_list_body">
	        				${not empty hrStaffPersonInfoForm.fdWorkTime?hrStaffPersonInfoForm.fdWorkTime:unWrite }
	        			</div>
	        		</div>
	        		<div class="ppc_c_list">
	        			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdUninterruptedWorkTime"/></div>
	        			<div class="ppc_c_list_body">
	        				${not empty hrStaffPersonInfoForm.fdUninterruptedWorkTime?hrStaffPersonInfoForm.fdUninterruptedWorkTime:unWrite }
	        			</div>
	        		</div>
	        		<div class="ppc_c_list">
	        			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkingYears"/></div>
	        			<div class="ppc_c_list_body">
	        				${hrStaffPersonInfoForm.fdWorkingYears }
	        			</div>
	        		</div>
	        		<%
	        			Object form = TagUtils.getFormBean(request);
	        			if(form instanceof ExtendForm) {
	        				ExtendForm extendForm = (ExtendForm)form;
	        				String modelName = extendForm.getModelClass().getName();
	        				Map<String, String> dynamicAttrMap = extendForm.getCustomPropMap();
	        				DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
	        				if(dynamicConfig != null) {
	        					// 复制一份出来操作，否则会删除原有的数据
	        					List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
	        					for(int i=0; i<_list.size(); i++){
	        						DynamicAttributeField field = _list.get(i);
	        						String value = "";
	        						String name = field.getFieldName();
	        						if("com.landray.kmss.sys.organization.model.SysOrgElement".equals(field.getFieldType())){
	        							String isMulti = field.getIsMulti();
		        						pageContext.setAttribute("isMulti", isMulti);
		        						String mName = modelName;
		        						mName = mName.replace(".", "_");
		        						if("true".equals(isMulti)){
		        							name += "_"+mName+"_Names";
		        						}else{
		        							name += "_"+mName+"_Name";
		        						}
	        						}
	        						Object obj = extendForm.getCustomPropMap().get(name);
	        						if(obj != null) {
	        							value = obj.toString();
	        						}
	        						pageContext.setAttribute("value", value);
	        		%>
	        						<div class="ppc_c_list">
	        							<div class="ppc_c_list_head">
	        							<%=field.getFieldTextByCurrentLang() %>
	        							</div>
	        							<div class="ppc_c_list_body">
	        								<%
	        									if(!field.getFieldEnums().isEmpty()){ // 枚举类型
	        								%>
	        									<%=field.getFieldEnum(value) %>
	        								<%
	        									}else{ // 其它类型显示文本域
	        								%>
	        									<%=value%>
	        								<%		
	        									}
	        								%>
	        							</div>
	        						</div>
	        		<%				
	        					}
	        				}
	        			}
	        		%>
	        	</div>
	        </div>
	        <c:if test="${(hrStaffPersonInfoForm.fdStatus != 'leave')&&(hrStaffPersonInfoForm.fdStatus != 'retire')&&(hrStaffPersonInfoForm.fdStatus != 'dismissal')}">
		        <!-- 在职信息 -->
		        <div class="personal_profile_content_card">
		          <div class="ppc_c_title" onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=onPost'">
		            <div>
		              <i class="ppc_c_title-icon"></i>
		              <span><bean:message bundle="hr-staff" key="mobile.hr.staff.view.4"/></span>
		            </div>
		            <i class="ppc_c_detail_more-icon"></i>
		          </div>
		          <div class="ppc_c_content">
						<div class="ppc_c_list">
			              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffNo"/></div>
			              <div class="ppc_c_list_body">
			              	${not empty hrStaffPersonInfoForm.fdStaffNo?hrStaffPersonInfoForm.fdStaffNo:unWrite }
			              </div>
			            </div>
			            <div class="ppc_c_list">
			              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgParentOrg"/></div>
			              <div class="ppc_c_list_body">
			        		${not empty hrStaffPersonInfoForm.fdOrgParentOrgName?hrStaffPersonInfoForm.fdOrgParentOrgName:unWrite }
			              </div>
			            </div>
		            	<div class="ppc_c_list">
			              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgParent"/></div>
			              <div class="ppc_c_list_body">
			              	${not empty hrStaffPersonInfoForm.fdOrgParentName?hrStaffPersonInfoForm.fdOrgParentName:unWrite }
			              </div>
			            </div>
		            	<div class="ppc_c_list">
			              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgPosts"/></div>
			              <div class="ppc_c_list_body">
			              	${not empty hrStaffPersonInfoForm.fdPostNames?hrStaffPersonInfoForm.fdPostNames:unWrite }
			              </div>
			            </div>			            	
		          </div>
		        </div>
	        </c:if>
	        <c:if test="${(hrStaffPersonInfoForm.fdStatus == 'leave' )||( hrStaffPersonInfoForm.fdStatus =='retire')||(hrStaffPersonInfoForm.fdStatus =='dismissal')}">
	        	<!-- 离职信息 -->
	        	<div class="personal_profile_content_card">
	        		<div class="ppc_c_title" onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=leave'">
	        			<div>
	        				<i class="ppc_c_title-icon"></i>
		              		<span><bean:message bundle="hr-staff" key="mobile.hr.staff.view.5"/></span>
		            	</div>
		            	<i class="ppc_c_detail_more-icon" ></i>
		          	</div>
		          	<div class="ppc_c_content">
		          		<div class="ppc_c_list">
		          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveApplyDate"/></div>
		          			<div class="ppc_c_list_body">
		          				${hrStaffPersonInfoForm.fdLeaveApplyDate }
		          			</div>
		          		</div>
		          		<div class="ppc_c_list">
		          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveTime"/></div>
		          			<div class="ppc_c_list_body">
		          				${hrStaffPersonInfoForm.fdLeaveTime }
		          			</div>
		          		</div>
		          		<div class="ppc_c_list">
		          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveReason"/></div>
		          			<div class="ppc_c_list_body">
		          				${hrStaffPersonInfoForm.fdLeaveReason }
		          			</div>
		          		</div>
		          	</div>
	        	</div>
	        </c:if>
	        <!-- 员工状态 -->
	        <div class="personal_profile_content_card">
	        	<div class="ppc_c_title" onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=staffStatus'">
        			<div>
        				<i class="ppc_c_title-icon"></i>
	              		<span><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus"/></span>
	            	</div>
	            	<i class="ppc_c_detail_more-icon" ></i>
	          	</div>
	          	<div class="ppc_c_content">
	          		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus"/></div>
	          			<div class="ppc_c_list_body">
	          				<c:choose>
	          					<c:when test="${not empty hrStaffPersonInfoForm.fdStatus }">
	          						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.${hrStaffPersonInfoForm.fdStatus}"/>
	          					</c:when>
	          					<c:otherwise>
	          						<bean:message bundle="hr-staff" key="hrStaffPersonReport.unknown" />
	          					</c:otherwise>
	          				</c:choose>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffType"/></div>
	          			<div class="ppc_c_list_body">
	          				${not empty hrStaffPersonInfoForm.fdStaffType?hrStaffPersonInfoForm.fdStaffType:unWrite}
	          			</div>
	          		</div>
	          		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEntryTime"/></div>
	          			<div class="ppc_c_list_body">
	          				${not empty hrStaffPersonInfoForm.fdEntryTime?hrStaffPersonInfoForm.fdEntryTime:unWrite}
	          			</div>
	          		</div>
	          	</div>
	        </div>
	        <!-- 联系信息 -->
	        <div class="personal_profile_content_card">
	        	<div class="ppc_c_title" onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=editType&fdId=${hrStaffPersonInfoForm.fdId }&key=connect'">
	        		<div>
        				<i class="ppc_c_title-icon"></i>
	              		<span><bean:message bundle="hr-staff" key="hrStaffPersonInfo.contactInfo"/></span>
	            	</div>
	            	<i class="ppc_c_detail_more-icon" ></i>
	        	</div>
	        	<div class="ppc_c_content">
	        		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdMobileNo"/></div>
	          			<div class="ppc_c_list_body">
	          				${not empty hrStaffPersonInfoForm.fdMobileNo?hrStaffPersonInfoForm.fdMobileNo:unWrite }
	          			</div>
	          		</div>
	          		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmail"/></div>
	          			<div class="ppc_c_list_body">
	          				${not empty hrStaffPersonInfoForm.fdEmail?hrStaffPersonInfoForm.fdEmail:unWrite }
	          			</div>
	          		</div>
	          		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOfficeLocation"/></div>
	          			<div class="ppc_c_list_body">
	          				${not empty hrStaffPersonInfoForm.fdOfficeLocation?hrStaffPersonInfoForm.fdOfficeLocation:unWrite }
	          			</div>
	          		</div>
	        	</div>
	        </div>
	        <c:choose>
	        	<c:when test="${fdSelfView }">
	        		<c:import url="/hr/staff/mobile/personInfo/view_experience.jsp"></c:import>
	        		<!-- 薪酬福利 -->
					<c:import url="/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=getByPerson&personInfoId=${hrStaffPersonInfoForm.fdId}" charEncoding="UTF-8" >
						<c:param name="paramKey" value="welfare" />
					</c:import>
					<kmss:authShow roles="ROLE_SYS_TIME_DEFAULT">
					<!-- 个人假期 -->
			        <div class="personal_profile_content_card" onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/mobile/personInfo/view/vacation.jsp?personInfoId=${hrStaffPersonInfoForm.fdId }'">
			          <div class="ppc_c_title ppc_c_title_border">
			            <div>
			              <i class="ppc_c_title-icon"></i>
			              <span><bean:message bundle="hr-staff" key="hr.staff.nav.attendance.management"/></span>
			            </div>
			            <i class="ppc_c_detail_more-icon"></i>
			          </div>
			        </div>
			        </kmss:authShow>
			        <!-- 员工动态 -->
			        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
			        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffPersonInfoLogListMixin"
			        	data-dojo-props="url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=list&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
			        	<div class="ppc_c_title">
							<div>
							  <i class="ppc_c_title-icon"></i>
							  <span><bean:message bundle="hr-staff" key="porlet.employee.dynamic"/></span>
							</div>
						</div>
			        </div>
	        	</c:when>
	        	<c:otherwise>
	        		<kmss:authShow roles="ROLE_HRSTAFF_EXPERIENCE">
	        			<c:import url="/hr/staff/mobile/personInfo/view_experience.jsp"></c:import>
	        		</kmss:authShow>
	        		<kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
		        		<!-- 薪酬福利 -->
						<c:import url="/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=getByPerson&personInfoId=${hrStaffPersonInfoForm.fdId}" charEncoding="UTF-8" >
							<c:param name="paramKey" value="welfare" />
						</c:import>
	        		</kmss:authShow>
	        		<kmss:authShow roles="ROLE_SYS_TIME_DEFAULT">
	        		<kmss:authShow roles="ROLE_HRSTAFF_ATTENDANCE">
				        <!-- 个人假期 -->
				        <div class="personal_profile_content_card" onClick="window.location.href='<%=request.getContextPath()%>/hr/staff/mobile/personInfo/view/vacation.jsp?personInfoId=${hrStaffPersonInfoForm.fdId }'">
				          <div class="ppc_c_title ppc_c_title_border">
				            <div>
				              <i class="ppc_c_title-icon"></i>
				              <span><bean:message bundle="hr-staff" key="hr.staff.nav.attendance.management"/></span>
				            </div>
				            <i class="ppc_c_detail_more-icon"></i>
				          </div>
				        </div>
			        </kmss:authShow>
			        </kmss:authShow>
			        <kmss:authShow roles="ROLE_HRSTAFF_LOG_VIEW">
				        <!-- 员工动态 -->
				        <div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList" 
				        	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffPersonInfoLogListMixin"
				        	data-dojo-props="url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=list&personInfoId=${hrStaffPersonInfoForm.fdId}',lazy:false">
				        	<div class="ppc_c_title">
								<div>
								  <i class="ppc_c_title-icon"></i>
								  <span><bean:message bundle="hr-staff" key="porlet.employee.dynamic"/></span>
								</div>
							</div>
				        </div>
			        </kmss:authShow>
	        	</c:otherwise>
	        </c:choose>
	        </div>
	      </div>
	    </div>
	    <!-- 底栏 -->
	    <div class="file-overview-footer">
	        <div onclick="window.location.href='<%=request.getContextPath()%>/hr/staff/mobile/newIndex.jsp?selfViewAuth=true'">
	          <i class="fof-overview-icon"></i>
	          <span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.1"/></span>
	        </div>
	        <kmss:authShow roles="ROLE_HRSTAFF_REPORT">
	        	<div onclick="window.location.href='<%=request.getContextPath()%>/hr/staff/mobile/statistics.jsp'">
	          		<i class="fof-data-icon fof-data-icon-active"></i>
	          		<span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.7"/></span>
	        	</div>
	        </kmss:authShow>
	        <div>
		        <i class="fof-file-icon fof-file-icon-active"></i>
		        <span>
		        	<c:choose>
		        		<c:when test="${hrStaffPersonInfoForm.fdOrgPersonId eq KMSS_Parameter_CurrentUserId }">
		        			<bean:message bundle="hr-staff" key="mobile.hr.staff.index.8"/>
		        		</c:when>
		        		<c:otherwise>
		        			${hrStaffPersonInfoForm.fdName }<bean:message bundle="hr-staff" key="mobile.hr.staff.view.6"/>
		        		</c:otherwise>
		        	</c:choose>
		        </span>
		    </div>
	    </div>
	  </div>
	  <script>
		require(['dijit/registry', 'dojo/dom', 'dojo/on', 'dojo/dom-attr', 'dojo/topic', 'mui/util', 'dojo/_base/lang',
                 'mui/dialog/Tip', 'dojo/domReady', 'dojo/query', 'dojo/touch', 'dojo/dom-construct', 'dojo/dom-style', 'dojo/dom-class', 'mui/dialog/Confirm'],
        		function(registry, dom, on, domAttr, topic, util, lang, Tip, ready, query, touch, domConstruct, domStyle, domClass, Confirm){
			ready(function(){
				var connect = query('.ppu_bp_phone_content')[0];
				if(connect){
					on(connect,touch.press,function(e){
						topic.publish('hr/staff/callMobile','${hrStaffPersonInfoForm.fdMobileNo}');
					});
				}
			});
			topic.subscribe('hr/staff/callMobile',function(fdMobileNo){
				if(fdMobileNo != ''){
					Confirm('<span>确定给${hrStaffPersonInfoForm.fdName}打电话吗</span>','拨打电话',function(check){
						if(check)
							location.href = "tel:" + fdMobileNo;
					});
				}else{
					Tip.tip({icon:'mui mui-warn', text:'这位亲没留下任何联系方式！！',width:'260',height:'60'});
				}
			});
			window.addTableList=function(mo){
				var personInfoId = Com_GetUrlParameter(window.location.href,"fdId");
				switch(mo){
				case 'trackrecord':
					var trackUrl ="hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=add"; 
					window.location.href=dojo.config.baseUrl+trackUrl+'&&personInfoId='+personInfoId;
					break;
				case 'familyinfo':
					var familyUrl ="hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=add"; 
					window.location.href=dojo.config.baseUrl+familyUrl+'&&personInfoId='+personInfoId;	
					break;
				case 'work':
					var workUrl ="hr/staff/hr_staff_person_experience/work/hrStaffPersonExperienceWork.do?method=add"; 
					window.location.href=dojo.config.baseUrl+workUrl+'&&personInfoId='+personInfoId;	
					break;
				case 'training':
					var trainingUrl ="hr/staff/hr_staff_person_experience/training/hrStaffPersonExperienceTraining.do?method=add"; 
					window.location.href=dojo.config.baseUrl+trainingUrl+'&&personInfoId='+personInfoId;	
					break;			
				case 'bonusmalus':
					var bonusmalusUrl ="hr/staff/hr_staff_person_experience/bonusMalus/hrStaffPersonExperienceBonusMalus.do?method=add"; 
					window.location.href=dojo.config.baseUrl+bonusmalusUrl+'&&personInfoId='+personInfoId;	
					break;
				case 'contract':
					var contractUrl ="hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=add"; 
					window.location.href=dojo.config.baseUrl+contractUrl+'&&personInfoId='+personInfoId+'&type=contract';	
					break;
				case 'education':
					var educationUrl ="hr/staff/hr_staff_person_experience/education/hrStaffPersonExperienceEducation.do?method=add"; 
					window.location.href=dojo.config.baseUrl+educationUrl+'&&personInfoId='+personInfoId+'&type=education';	
					break;
				case 'qualification':
					var educationUrl ="hr/staff/hr_staff_person_experience/qualification/hrStaffPersonExperienceQualification.do?method=add"; 
					window.location.href=dojo.config.baseUrl+educationUrl+'&&personInfoId='+personInfoId+'&type=qualification';	
					break;					
				}
			}
			window.expandContent = function(domNode){
				var domDiv = $(domNode).parents('div')[1];
				var display = domAttr.get(domDiv,'data-display'),
					newdisplay = (display == 'none' ? '' : 'none');
				domAttr.set(domDiv,'data-display',newdisplay);
				var content = query('.ppc_c_content',domDiv)[0];
				if(newdisplay == 'none'){
					domStyle.set(content,'display','none');
					domClass.add(domNode,'up');
				}else{
					domStyle.set(content,'display','');
					domClass.remove(domNode,'up');
				}
			}; 
		});
	  </script>
	</template:replace>
</template:include>