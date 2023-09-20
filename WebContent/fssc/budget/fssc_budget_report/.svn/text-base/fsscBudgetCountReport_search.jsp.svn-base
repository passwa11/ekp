<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
	String orgType = "ORG_TYPE_POSTORPERSON";
	if("config".equals(request.getParameter("type"))) {
		orgType += "|ORG_TYPE_DEPT";
	}
	request.setAttribute("orgType", orgType);
%>
<template:include ref="default.edit">
	<template:replace name="head">
        <script type="text/javascript">
	        Com_IncludeFile("security.js|common.js");
	        Com_IncludeFile("domain.js");
	        Com_IncludeFile("form.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_report/", 'js', true);
            Com_IncludeFile("fsscBudgetReport.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_report/", 'js', true);
        </script>
        <script>
		     var validation = $KMSSValidation();
		     if (document.addEventListener) {//如果是Firefox      
		         document.addEventListener("keypress", otherHandler, true);  
		        } else {  
		            document.attachEvent("onkeypress", ieHandler);  
		        }  
		      function otherHandler(evt) { 
		          if (evt.keyCode == 13) {  
		         	 FS_Search();  
		           }  
		       }  
		        function ieHandler(evt) {  
		          if (evt.keyCode == 13) {  
		         	 FS_Search();  
		            }  
		       }
     </script>
    </template:replace>
    <template:replace name="content">
    <html:form action="/fssc/budget/fssc_budget_data/fsscBudgetData.do" method="get" target="searchIframe">
    	<center>
			<p class="txttitle">
				<p class="txttitle"><bean:message bundle="fssc-budget" key="message.budget.count.report"/></p>
			</p>
			<table class="tb_normal" style="width:100%">
					<!-- 预算方案 -->
					<tr>
						<td style="width:20%;" class="td_normal_title">
							${lfn:message('fssc-budget:fsscBudgetData.fdBudgetScheme')}
						</td>
						<td  style="width:70%;" >
                             <xform:dialog showStatus="edit" required="true" propertyName="fdSchemeName" propertyId="fdSchemeId" subject="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetScheme')}" style="width:95%;">
                              	dialogSelect(false,'eop_basedata_fdBudgetScheme','fdSchemeId','fdSchemeName',function(data){displaySearch(data);});
                              </xform:dialog>
                              <xform:text property="fdDimension" showStatus="noShow"></xform:text>
						</td>
					</tr>
					<!-- 公司 -->
					<tr id="fdDimension2" style="display:none;">
						<td style="width:20%;" class="td_normal_title">
							${lfn:message('fssc-budget:fsscBudgetData.fdCompany')}
						</td>
						<td  style="width:70%;" >
                             <xform:dialog showStatus="edit" propertyName="fdCompanyName" propertyId="fdCompanyId" subject="${lfn:message('fssc-budget:fsscBudgetData.fdCompany')}" style="width:95%;">
                              	dialogSelect(false,'eop_basedata_fdCompany','fdCompanyId','fdCompanyName',function(data){changeCompany(data);});
                              </xform:dialog>
						</td>
					</tr>
					<!-- 成本中心组 -->
					<tr>
						<td style="width:20%;" class="td_normal_title">
							${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}
						</td>
						<td  style="width:70%;" >
                             <xform:dialog showStatus="edit" propertyName="fdCostCenterGroupName" propertyId="fdCostCenterGroupId" subject="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}" style="width:95%;">
                              			dialogSelect(false,'eop_basedata_cost_center_group_fdCostCenterGroup','fdCostCenterGroupId','fdCostCenterGroupName',null);

                              </xform:dialog>
						</td>
					</tr>
					<!-- 预算科目 -->
					<tr id="fdDimension8" style="display:none;">
						<td style="width:20%;" class="td_normal_title">
							${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}
						</td>
						<td  style="width:70%;" >
                             <xform:dialog showStatus="edit" propertyName="fdBudgetItemName" propertyId="fdBudgetItemId" subject="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}" style="width:95%;">
                             	selectBudgetItem();
                              </xform:dialog>
						</td>
					</tr>
					<tr>
						<td style="width:20%;" class="td_normal_title">
							${lfn:message('fssc-budget:fsscBudgetData.fdYear')}
						</td>
						<td  style="width:70%;" >
							<xform:select property="fdYear" showStatus="edit" showPleaseSelect="false" >
					   		</xform:select>
						</td>
					</tr>
					<tr>
						<td style="width:20%;" class="td_normal_title">
							${lfn:message('fssc-budget:fsscBudgetData.fdPeriod')}
						</td>
						<td  style="width:70%;" >
							<span id="periodSpan"></span>
						</td>
					</tr>
					<input type="hidden" name="endFlag" />
			  </table>
		<br>
		<ui:button text="${ lfn:message('button.search') }" onclick="FSSC_Search('countReport');" />
		<ui:button text="${ lfn:message('button.reset') }"  onclick="FSSC_Reset('countReport');" />
		<ui:button text="${ lfn:message('button.export') }" onclick="exportResult('exportCountReport');" />
</center>
<script language="JavaScript">

//修改员工
window.changePerson = function () {
	var fdPersonName = $("input[name='fdPersonName']")[0].value;
	if (fdPersonName == null || fdPersonName == "") {
		showErrorMessage($("input[name='fdPersonName']")[0], "${ lfn:message('fssc-budget:fsscBudgetData.fdPersonNotNull') }", true);
		return;
	}
	$($("input[name='fdPersonName']")[1]).val(fdPersonName);
};

//显示错误信息
window.showErrorMessage = function (input, message, flag) {
	if ($("#validate_hand").length == 0) {
		var parent = $(input).parent().parent();
		if (flag)
			parent = parent.parent();
		//parent.append('<div class="validation-advice" id="validate_hand" _reminder="true"><table class="validation-table"><tbody><tr><td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td><td class="validation-advice-msg">' + message + '</td></tr></tbody></table></div>');
	}
};
//选择无效创建人员
function selectInvalidPerson() {
	var _orgType = "${orgType}".replace("|", ",");
	Dialog_AddressList(false,'fdPersonId','fdPersonName',';','sysHandoverService&authCurrent=true&orgType='+_orgType,changePerson,
	'sysHandoverService&authCurrent=true&orgType='+_orgType+'&keyword=!{keyword}',null,null,"${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }");
}

if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
	setTimeout(function(){FS_ChangePeriodType("1")},2000);
}else{//非IE
	LUI.ready(function(){
		FS_ChangePeriodType("1");
	});
};
</script>
</html:form>
<div>
    <iframe src="" name="searchIframe" id="searchIframe" align="top" onload="this.height=searchIframe.document.body.scrollHeight;" width="97%"  Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No>
    </iframe>
</div>
</template:replace>
</template:include>
