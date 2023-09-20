<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
	
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/lib/form.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/organization.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/reset.css">	
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/nice-select.css">		
	</template:replace>
	<template:replace name="content">
		<script src="../resource/weui_switch.js"></script>
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<div class="hr_contract_sign_wrap">
			<html:form action="/hr/organization/hr_organization_post/hrOrganizationPost.do">
				<div class="lui_hr_tb_simple_wrap">
					<table class="tb_simple lui_hr_tb_simple trlineheight40">
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.fdName"/>
							</td>
							<td class="newInput">
								<c:if test="${'true' eq isUniqueGroupName}">
									<xform:text property="fdName" required="true" showStatus="edit" style="width:80%" validators="uniqueName"></xform:text>
								</c:if>
								<c:if test="${!('true' eq isUniqueGroupName)}">
									<xform:text property="fdName" required="true" showStatus="edit" style="width:80%"></xform:text>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.fdCode"/>
							</td>
							<td class="newInput">
								<xform:text title="${lfn:message('hr-hr-organization:hrOrganizationPost.fdCode') }" property="fdNo" showStatus="edit" style="width:80%" validators="uniqueFdNo"></xform:text>
							</td>
						</tr>
						
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.org"/>
							</td>
							<td class="newAddress">
								<!-- 父级组织被禁用辉报错 -->
								<xform:address isHrAddress="true" propertyId="fdParentId" propertyName="fdParentName" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit"></xform:address> 
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.seq"/>
							</td>
							<td class="newSelect">
								<xform:dialog propertyId="fdPostSeqId" propertyName="fdPostSeqName" showStatus="edit" style="width:50%;">
                                    dialogSelect(false,'hr_organization_post_seq','fdPostSeqId','fdPostSeqName');
                                </xform:dialog>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.leader"/>
							</td>
							<td class="newAddress">
								<xform:address isHrAddress="true" propertyId="fdThisLeaderId" propertyName="fdThisLeaderName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.grade.scope"/>
							</td>
							<td>
								<div class="">
									<div id="grade_mix" class="select" style="float:left;">
										<select name="fdGradeMixId" class="newHtmlSelect">
											<option value="">
												<bean:message key="page.firstOption"/>
											</option>
										</select>								
									</div>
									<div class="select-group-split-line"></div>
									<div id="grade_max" class="select" style="float:left;">
										<select name="fdGradeMaxId" class="newHtmlSelect">
											<option value=""><bean:message key="page.firstOption"/></option>
										</select>								
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.rank.scope"/>
							</td>
							<td class="newHtmlSelect">
								<div class="">
									<div id="rank_mix" class="select" style="float:left;">
										<select name="fdRankMixId" class="newHtmlSelect">
											<option value=""><bean:message key="page.firstOption"/></option>
										</select>								
									</div>
									<div class="select-group-split-line"></div>
									<div id="rank_max" class="select" style="float:left;">
										<select name="fdRankMaxId" class="newHtmlSelect">
											<option value=""><bean:message key="page.firstOption"/></option>
										</select>								
									</div>
								</div>							
							</td>
						</tr>
					</table>
					<table class="tb_simple lui_hr_tb_simple">
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.is.key"/>
							</td>
							<td>
								<%-- 是否启用--%>
                                <html:hidden property="fdIsKey" /> 
								<label class="weui_switch">
									<span class="weui_switch_bd" id="weui_switch_iskey">
										<input type="checkbox" ${'true' eq hrOrganizationPostForm.fdIsKey ? 'checked' : '' } />
										<span></span>
										<small></small>
									</span>
									<span id="fdIsKeyText"></span>
								</label>
								<script type="text/javascript">
									function setFdIsKeyText(status) {
										if(status == 'true' || status == true) {
											$("#fdIsKeyText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.true" />');
										} else {
											$("#fdIsKeyText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.false" />');
										}
									}
									$("#weui_switch_iskey :checkbox").on("click", function() {
										var status = $(this).is(':checked');
										$("input[name=fdIsKey]").val(status);
										setFdIsKeyText(status);
									});
									setFdIsKeyText('${hrOrganizationPostForm.fdIsKey}');
								</script>
							</td>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationPost.is.secret"/>
							</td>
							<td>
								<%-- 是否启用--%>
                                <html:hidden property="fdIsSecret" /> 
								<label class="weui_switch">
									<span class="weui_switch_bd" id="weui_switch_isSecret">
										<input type="checkbox" ${'true' eq hrOrganizationPostForm.fdIsSecret ? 'checked' : '' } />
										<span></span>
										<small></small>
									</span>
									<span id="fdIsSecretText"></span>
								</label>
								<script type="text/javascript">
									function setFdIsSecretText(status) {
										if(status == 'true' || status == true) {
											$("#fdIsSecretText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.true" />');
										} else {
											$("#fdIsSecretText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.false" />');
										}
									}
									$("#weui_switch_isSecret :checkbox").on("click", function() {
										var status = $(this).is(':checked');
										$("input[name=fdIsSecret]").val(status);
										setFdIsSecretText(status);
									});
									setFdIsSecretText('${hrOrganizationPostForm.fdIsSecret}');
								</script>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								<bean:message bundle="hr-organization" key="hrOrganizationElement.fdIsBusiness"/>
							</td>
							<td>
								<ui:switch property="fdIsBusiness"  enabledText="${ lfn:message('hr-organization:hrOrganizationPost.fdIsAvailable.true') }" disabledText="${ lfn:message('hr-organization:hrOrganizationPost.fdIsAvailable.false') }"></ui:switch>
							</td>
						</tr>
					</table>
				</div>
				<html:hidden property="fdId" />
				<html:hidden property="method_GET" />
			</html:form>
		</div>
	</template:replace>
</template:include>
<script>
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("form.js");
	Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/organization/hr_organization_post/", 'js', true);
	Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/hr/organization/resource/js/", 'js', true);
	var _validation = $KMSSValidation();
	var gradeData = null;
	var rankData = null;
	
	
	function covertData(data){
		var jsonData = [];
		for(var i=0;i<data.length;i++){
			var obj={};
			for(var j = 0;j<data[i].length;j++){
				obj[data[i][j]['col']]=data[i][j]['value'];
			}
			jsonData.push(obj);
		}
		return jsonData;
	}
	//岗位名称补全部门名称
	function changeParent(rtnVal) {
		console.log(rtnVal)
		<c:if test="${'true' eq ispostRelationdept}">
		if(rtnVal && rtnVal.length > 0) {
			$("input[name=fdName]").val(rtnVal[1] + "_");
			}
		</c:if>
		}

	var NameValidators = {
			'uniqueName' : {
				error : '<bean:message bundle="hr-organization" key="hr.organization.info.tip.25" />',
				test : function (value) {
					var result = null;
					var fdId = '${hrOrganizationPostForm.fdId}';
					$.ajax({
				        type: "post",
				        url: "${LUI_ContextPath}/hr/organization/hr_organization_post/hrOrganizationPost.do?method=checkNameUnique",
				        data: {"fdName":value, "fdId":fdId},
				        async : false,
				        dataType: "json",
				        success: function (data ,textStatus, jqXHR){
				        	result = data.result;
				        }
			    	 });
					return result;
			    }
			}
		};
	
	_validation.addValidators(NameValidators);
	
	var FdNoValidators = {
			'uniqueFdNo' : {
				error : '<bean:message bundle="hr-organization" key="hr.organization.info.tip.26" />',
				test : function (value) {
					var result = null;
					var fdId = '${hrOrganizationPostForm.fdId}';
					$.ajax({
				        type: "post",
				        url: "${LUI_ContextPath}/hr/organization/hr_organization_post/hrOrganizationPost.do?method=checkFdNoUnique",
				        data: {"fdNo":value, "fdId":fdId},
				        async : false,
				        dataType: "json",
				        success: function (data ,textStatus, jqXHR){
				        	result = data.result;
				        }
			    	 });
					return result;
			    }
			}
		};
	
	_validation.addValidators(FdNoValidators);
	function resetSelect(reset){
		var reset = reset||[];
		for(var i = 0;i<reset.length;i++){
			$(reset[i]+" select").niceSelect("reset");
		}
	}
	
	seajs.use(['lui/dialog','lui/jquery','lui/topic','${LUI_ContextPath}/hr/organization/resource/js/jquery-select.js'], function(dialog,$,topic,select) {
		$.fn.niceSelect = select.niceSelect;
		var loaing = dialog.loading();
		$.ajax({
	        type: "post",
	        url: "${LUI_ContextPath}/hr/organization/hr_organization_grade/hrOrganizationGrade.do?method=data",
	        data: {rowsize:999},
	        async : false,
	        dataType: "json",
	        success: function (data ,textStatus, jqXHR){
	        	if(data){
	        		gradeData = data.datas;
	        		loaing.hide();
	        	}
	        }
		 });
		
		$.ajax({
	        type: "post",
	        url: "${LUI_ContextPath}/hr/organization/hr_organization_rank/hrOrganizationRank.do?method=data",
	        data: {rowsize:999},
	        async : false,
	        dataType: "json",
	        success: function (data ,textStatus, jqXHR){
	        	if(data){
	        		rankData = data.datas;
	        	}
	        }
		 })		
		var gradeMixValue = {
				name:"<%=request.getAttribute("grademixName")%>",
				id:"<%=request.getAttribute("grademixId")%>",
				weight:"<%=request.getAttribute("grademixWeight")%>"
			}
		var gradeMaxValue={
				name:"<%=request.getAttribute("grademaxName")%>",
				id:"<%=request.getAttribute("grademaxId")%>",
				weight:"<%=request.getAttribute("grademaxWeight")%>"
			};
		var rankMixValue={
				name:"${hrOrganizationPostForm.fdRankMixName}",
				id:"${hrOrganizationPostForm.fdRankMixId}",
				weight:"${hrOrganizationPostForm.fdRankMixWeight}"
			}
		var rankMaxValue = {
				name:"${hrOrganizationPostForm.fdRankMaxName}",
				id:"${hrOrganizationPostForm.fdRankMaxId}",
				weight:"${hrOrganizationPostForm.fdRankMaxWeight}"
			}
		
		$("#grade_mix select").niceSelect(null,gradeMixValue['name']!="null"?gradeMixValue:null);
		$("#grade_max select").niceSelect(null,gradeMaxValue['name']!="null"?gradeMaxValue:null);
		$("#rank_mix select").niceSelect(null,rankMixValue['name']?rankMixValue:null);
		$("#rank_max select").niceSelect(null,rankMaxValue['name']?rankMaxValue:null);
		topic.subscribe("nice/select/selected",function(id){
			if(id=="grade_mix"){
				resetSelect(["#rank_mix","#rank_max"]);
			
			}
			if(id=="grade_max"){
				resetSelect(["#rank_mix","#rank_max"]);
			}				
		})
		topic.subscribe("nice/select/click",function(id){
			var grade_Data = covertData(gradeData);
			var rank_Data = covertData(rankData);
			if(id=="grade_mix"){
				var maxId = $("#grade_max option:selected").attr("value");
				var selectedId = $("#grade_mix option:selected").attr("value");
				var maxWeight= $("#grade_max option:selected").attr("data-weight");
				$("#grade_mix .list li").not(":first").remove();
				$("#grade_mix select option").not(":first").remove();
				for(var i = 0;i<grade_Data.length;i++){
					var isSelected = "";
					if((Number(grade_Data[i]['fdWeight'])<=Number(maxWeight))||(!maxId)){
						if(selectedId==grade_Data[i]['fdId']){
							isSelected="selected=true"
						}
						$("#grade_mix .list").append("<li class='option' data-weight='"+grade_Data[i]['fdWeight']+"' value='"+grade_Data[i]['fdId']+"'>"+grade_Data[i]['fdName']+"</li>");
						$("#grade_mix select").append("<option "+isSelected+" grade-weight="+grade_Data[i]['fdGradeWeight']+" data-weight='"+grade_Data[i]['fdWeight']+"' value='"+grade_Data[i]['fdId'] +"'>"+grade_Data[i]['fdName']+"</option>");
						
					}
				}
			}
			if(id=="grade_max"){
				var mixId = $("#grade_mix option:selected").attr("value");
				var mixWeight= $("#grade_mix option:selected").attr("data-weight");
				var selectedId = $("#grade_max option:selected").attr("value");
				$("#grade_max .list li").not(":first").remove();
				$("#grade_max select option").not(":first").remove();
				for(var i = 0;i<grade_Data.length;i++){
						var isSelected = "";
						if((Number(grade_Data[i]['fdWeight'])>=Number(mixWeight))||(!mixId)){
							if(selectedId==grade_Data[i]['fdId']){
								isSelected="selected=true"
							}
							$("#grade_max select").append("<option "+isSelected+" grade-weight="+grade_Data[i]['fdGradeWeight']+" data-weight='"+grade_Data[i]['fdWeight']+"' value='"+grade_Data[i]['fdId'] +"'>"+grade_Data[i]['fdName']+"</option>");
							$("#grade_max .list").append("<li class='option' data-weight='"+grade_Data[i]['fdWeight']+"' value='"+grade_Data[i]['fdId'] +"'>"+grade_Data[i]['fdName']+"</li>");
						}
				}
			}
			if(id=="rank_mix"){
				var mixId = $("#rank_max option:selected").attr("value");
				var mixWeight= $("#rank_max option:selected").attr("data-weight");
				var grademixWeight = $("#grade_mix option:selected").attr("data-weight")||-999;
				var grademaxWeight = $("#grade_max option:selected").attr("data-weight")||999;
				$("#rank_mix .list li").not(":first").remove();
				$("#rank_mix select option").not(":first").remove();	
				for(var i = 0;i<rank_Data.length;i++){
						if(grademixWeight<=rank_Data[i]['fdGradeWeight']&&rank_Data[i]['fdGradeWeight']<=grademaxWeight){
							if((Number(rank_Data[i]['fdWeight'])<=Number(mixWeight))||(!mixId)){
								$("#rank_mix select").append("<option data-weight='"+rank_Data[i]['fdWeight']+"' value='"+rank_Data[i]['fdId'] +"'>"+rank_Data[i]['fdName']+"</option>");
								$("#rank_mix .list").append("<li class='option' data-weight='"+rank_Data[i]['fdWeight']+"' value='"+rank_Data[i]['fdId'] +"'>"+rank_Data[i]['fdName']+"</li>");
							}
						}
				}
			}
			if(id=="rank_max"){
				var mixId = $("#rank_mix option:selected").attr("value");
				var mixWeight= $("#rank_mix option:selected").attr("data-weight");
				var grademixWeight = $("#grade_mix option:selected").attr("data-weight")||-999;
				var grademaxWeight = $("#grade_max option:selected").attr("data-weight")||999;				
				$("#rank_max .list li").not(":first").remove();
				$("#rank_max select option").not(":first").remove();				
				for(var i = 0;i<rank_Data.length;i++){
					if(grademixWeight<=rank_Data[i]['fdGradeWeight']&&rank_Data[i]['fdGradeWeight']<=grademaxWeight){
						if((Number(rank_Data[i]['fdWeight'])>=Number(mixWeight))||(!mixId)){
							$("#rank_max select").append("<option data-weight='"+rank_Data[i]['fdWeight']+"' value='"+rank_Data[i]['fdId'] +"'>"+rank_Data[i]['fdName']+"</option>");
							$("#rank_max .list").append("<li class='option' data-weight='"+rank_Data[i]['fdWeight']+"' value='"+rank_Data[i]['fdId'] +"'>"+rank_Data[i]['fdName']+"</li>");
						}
					}
				}
			}			
		})
		window._submit = function(){
			if ($KMSSValidation().validate()) {
				var method_GET = '${hrOrganizationPostForm.method_GET}';
				var url = '${LUI_ContextPath}/hr/organization/hr_organization_post/hrOrganizationPost.do?method=save';
				if(method_GET == 'edit'){
					url = '${LUI_ContextPath}/hr/organization/hr_organization_post/hrOrganizationPost.do?method=update';
				}
			
				$.post(url, $(document.hrOrganizationPostForm).serialize(),function(data){
				   if(data!=""){
					  	dialog.success('<bean:message key="return.optSuccess" />');
						setTimeout(function (){
							window.$dialog.hide("success");
						}, 1500);
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				})
			}
		}
	});
</script>
