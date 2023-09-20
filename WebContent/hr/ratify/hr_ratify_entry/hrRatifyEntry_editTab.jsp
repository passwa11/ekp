<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.hr.ratify.util.HrRatifyUtil,com.landray.kmss.sys.organization.model.SysOrganizationConfig" %>
    
<% 
SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
if(UserUtil.getUser().getFdParentOrg() != null) {
    pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
} else {
    pageContext.setAttribute("currentOrg", "");
} %>
<c:if test="${hrRatifyEntryForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
<template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifyEntryForm"></c:param>
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
		<form id="hrRatifyEntryForm" name="hrRatifyEntryForm" method="post" action ="${KMSS_Parameter_ContextPath}hr/ratify/hr_ratify_entry/hrRatifyEntry.do">
	</c:if>
	<html:hidden property="fdId" />
    <html:hidden property="docStatus" />
    <html:hidden property="docTemplateId" />
    <html:hidden property="method_GET" />
    <c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpage collapsed="true" id="reviewTabPage">
				<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					})
				</script>
				<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyEntryForm" />
					<c:param name="fdKey" value="${fdTempKey }" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="baseInfo"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_entry/hrRatifyEntry_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	
	<script>
		var _validation = $KMSSValidation(document.forms['hrRatifyEntryForm']);
      	//切换入职员工填写模式
		window.SetEntryNameField =function() {
			var checkBox = document.getElementById("isRecruit");
			var checked = checkBox.checked;
			//box区域
			var checkBoxSpan = document.getElementById("checkBoxSpan");
			//招聘模块
			var isRecruitSpan = document.getElementById("isRecruitSpan");	
			var notRecruitSpan = document.getElementById("notRecruitSpan");
			var isRecruitSelect = document.getElementsByName("fdRecruitEntryName")[0];
			var isRecruitInput = document.getElementsByName("fdEntryName")[0];
			_validation.removeElements(isRecruitInput);
			_validation.removeElements(isRecruitSelect);
			if (true == checked) {
				//输入框样式
				//checkBoxSpan.style.bottom="0px";
				isRecruitSpan.style.display="";
				notRecruitSpan.style.display="none";
				_validation.addElements(isRecruitSelect,'required');
				$(isRecruitInput).removeAttr('validate');
				$(isRecruitInput).removeAttr('_validate');
			} else {
			    //输入框样式
				//checkBoxSpan.style.bottom="10px";
				isRecruitSpan.style.display="none";
				notRecruitSpan.style.display="";
				_validation.addElements(isRecruitInput,'required');
				_validation.addElements(isRecruitInput,'maxLength(200)');
				$(isRecruitSelect).removeAttr('validate');
				$(isRecruitSelect).removeAttr('_validate');
			}
		}
		Com_AddEventListener(window, "load", function() {
			SetEntryNameField();
		});
        seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env'],function($,dialog,topic,env){
        	window.reloadForm = function(value){
        		var url=Com_SetUrlParameter(location.href, "fdEntryId", value); 
        		document.hrRatifyEntryForm.action = url; 
        		//ajax 刷新整个form内的数据
       			var options={
       				url:  url, 	
       			 	target :'#hrRatifyEntryForm',
       				error : function() {
 					  alert('加载页面' + url + '时出错！')
				    }
        		};
       			document.hrRatifyEntryForm.submit(function() { 
        	        $(this).ajaxSubmit(options); 
        	        return false; 
        	  	}); 
    			
        	};
        	window.selectStaffEntry = function(){ 
        		var staffId = $('[name="fdRecruitEntryId"]').val();
        		var staffName = $('[name="fdRecruitEntryName"]').val();
        		var url="/hr/ratify/import/showStaffEntryDialog.jsp?staffId="+ staffId + "&staffName=" + encodeURI(staffName);
        		dialog.iframe(url,'选择待入职员工',function(arg){
        			if(arg){ 
        				reloadForm(arg.staffId);
        			} 
        		},{width:800,height:520});
        	};
        	//自定义校验器:工作经历——结束时间不能早于开始时间
    		_validation.addValidator('compareFdStartDateHistory','结束时间不能早于开始时间',function(v, e, o){
    			var result = true; 
    			var fdStartDateHistory=$('[name="fdStartDateHistory"]');
    			var fdEndDateHistory=$('[name="fdEndDateHistory"]');
    			if( fdStartDateHistory.val() && fdEndDateHistory.val() ){
    				var start=Com_GetDate(fdStartDateHistory.val());
    				var end=Com_GetDate(fdEndDateHistory.val());
    				if(end.getTime() < start.getTime()){
    					result = false;
    				}
    			}
    			return result;
    		});
    		//自定义校验器:教育记录——毕业日期不能早于入学日期
    		_validation.addValidator('compareFdEntranceDate','毕业日期不能早于入学日期',function(v, e, o){
    			var result = true; 
    			var fdEntranceDate=$('[name="fdEntranceDate"]');
    			var fdGraduationDate=$('[name="fdGraduationDate"]');
    			if( fdEntranceDate.val() && fdGraduationDate.val() ){
    				var start=Com_GetDate(fdEntranceDate.val());
    				var end=Com_GetDate(fdGraduationDate.val());
    				if(end.getTime() < start.getTime()){
    					result = false;
    				}
    			}
    			return result;
    		});
    		//自定义校验器:培训记录——结束时间不能早于开始时间
    		_validation.addValidator('compareFdStartDateTrain','结束时间不能早于开始时间',function(v, e, o){
    			var result = true; 
    			var fdStartDateTrain=$('[name="fdStartDateTrain"]');
    			var fdEndDateTrain=$('[name="fdEndDateTrain"]');
    			if( fdStartDateTrain.val() && fdEndDateTrain.val() ){
    				var start=Com_GetDate(fdStartDateTrain.val());
    				var end=Com_GetDate(fdEndDateTrain.val());
    				if(end.getTime() < start.getTime()){
    					result = false;
    				}
    			}
    			return result;
    		});
    		//自定义校验器:资格证书——失效日期不能早于颁发日期
    		_validation.addValidator('compareFdIssueDate','失效日期不能早于颁发日期',function(v, e, o){
    			var result = true; 
    			var fdIssueDate=$('[name="fdIssueDate"]');
    			var fdInvalidDate=$('[name="fdInvalidDate"]');
    			if( fdIssueDate.val() && fdInvalidDate.val() ){
    				var start=Com_GetDate(fdIssueDate.val());
    				var end=Com_GetDate(fdInvalidDate.val());
    				if(end.getTime() < start.getTime()){
    					result = false;
    				}
    			}
    			return result;
    		});
    		// 验证手机号是否已被注册
    		function startsWith(value, prefix) {
				return value.slice(0, prefix.length) === prefix;
			}
    		_validation.addValidator('uniqueMobileNo','手机号已被注册',function(value, e, o){
    			if(startsWith(value, "+86")) {
    				// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
    				value = value.slice(3, value.length)
    			}
    			if(startsWith(value, "+")) {
    				value = value.replace("+", "x")
    			}
    			var fdId = document.getElementsByName("fdId")[0].value;
    			//从待确认人事档案中过来的
    			var fdRecruitEntryId = document.getElementsByName("fdRecruitEntryId")[0].value;
    			var url = Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&mobileNo="+ value + "&fdId="+fdId+"&checkType=unique";
    			if (fdRecruitEntryId){
    				url = url + "&fdRecruitEntryId="+fdRecruitEntryId;
				}
				url = encodeURI( url + "&date=" + new Date());
    			var result = _CheckUnique(url);
    			
    			if (!result) 
    				return false;
    			return true;
    		});
    	});
        var isLoginSpecialChar = <%=isLoginSpecialChar%>;
    	
    	var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
    	<%  if("true".equals(isLoginSpecialChar)){%>
    		errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
    	<% } %>
		var LoginNameValidators = {
			'uniqueLoginName' : {
				error : "<bean:message key='sysOrgPerson.error.loginName.mustUnique' bundle='sys-organization' />",
				test : function (value) {
     				var fdId = document.getElementsByName("fdId")[0].value;
     				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&loginName=" + value+"&fdId="+fdId+"&checkType=unique&date="+new Date());
     				var result = _CheckUnique(url);
     				if (!result) 
     					return false;
     					return true;
     			}
     		},
     		'invalidLoginName': {
     			error : "<bean:message key='sysOrgPerson.error.newLoginNameSameOldName' bundle='sys-organization' />",
     			test  : function(value) {
     				if (LoginNameValidators["lgName"] && (LoginNameValidators["lgName"]==value)){
     					return true;
     				}
					LoginNameValidators["lgName"]=null;
					var fdId = document.getElementsByName("fdId")[0].value;
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&loginName=" + value+"&fdId="+fdId+"&checkType=invalid&date="+new Date());
					var result = _CheckUnique(url);
					if (!result){ 
						return false;
					}else{
						return true;
					}	
     			}
     		},			
	     	'normalLoginName':{
     			error:errorMsg,
     			test:function(value){
     				var pattern;
     					
   					<% if("true".equals(isLoginSpecialChar)){%>
   						pattern=new RegExp("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$");
   					<% }else{ %>
   						pattern=new RegExp("^[A-Za-z0-9_]+$");
   					<% }%>
     					
     				if(pattern.test(value)){
     					return true;
     				}else{
     					return false;
     				}
     			}
     		},
     		"uniqueStaffNo" : {
     			error : "<bean:message key='hrStaffPersonInfo.staffNo.unique.err' bundle='hr-staff' />",
     			test : function (value) {
     				var fdId = document.getElementsByName("fdId")[0].value;
     				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&staffNo="
     					+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
     				var result = _CheckUnique(url);
     				if (!result) 
     					return false;
     					return true;
     			}
     		},
     		'uniqueNo' : {
     			error : "<bean:message key='organization.error.fdNo.mustUnique' bundle='sys-organization' />",
     			test : function(v, e, o) {
      				if (v.length < 1)
      					return true;
      				var fdId = document.getElementsByName("fdId")[0].value,
      					fdNo = document.getElementsByName("fdNo")[0].value;
      				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgElementService&fdOrgType=8"
      						+ "&fdId=" + fdId + "&fdNo=" + fdNo + "&_=" + new Date());
      				return _CheckUnique(url);
      			}
     		}
    	};
     	_validation.addValidators(LoginNameValidators);
     			
     	//校验登录名是否与系统中失效的登录名一致
     	function _CheckUnique(url) {
     		var xmlHttpRequest;
     		if (window.XMLHttpRequest) { // Non-IE browsers
     			xmlHttpRequest = new XMLHttpRequest();
     		} else if (window.ActiveXObject) { // IE
     			try {
     				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
     			} catch (othermicrosoft) {
     				try {
     					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
     				} catch (failed) {
     					xmlHttpRequest = false;
     				}
     			}
     		}
     		if (xmlHttpRequest) {
     			xmlHttpRequest.open("GET", url, false);
     			xmlHttpRequest.send();
     			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
     			if (result != "") {
     				return false;
     			}
     		}
     		return true;
     	}
	</script>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<%--流程--%>
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="hrRatifyEntryForm" />
                    <c:param name="fdKey" value="${fdTempKey }" />
                    <c:param name="isExpand" value="true" />
                    <c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
                </c:import>
                
                <!-- 关联机制(与原有机制有差异) -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyEntryForm" />
					<c:param name="approveType" value="right" />
					<c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
			<%--关联机制(与原有机制有差异)--%>
			<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifyEntryForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>
</c:if>