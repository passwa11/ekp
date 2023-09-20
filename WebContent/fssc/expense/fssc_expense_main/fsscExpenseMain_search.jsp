<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<title>${ lfn:message('fssc-expense:py.BaoXiaoTaiZhang')}</title>
<script>
    Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_main/", 'js', true);
    Com_IncludeFile("doclist.js|dialog.js|calendar.js|jquery.js|validation.js");
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	Com_IncludeFile("plugin.js");   
</script>
<%
	String orgType = "ORG_TYPE_POSTORPERSON";
	if("config".equals(request.getParameter("type"))) {
		orgType += "|ORG_TYPE_DEPT";
	}
	request.setAttribute("orgType", orgType);
%>
<html:form action="/fssc/expense/fssc_expense_main/fsscExpenseMain.do">
<center>
<p class="txttitle">
	${ lfn:message('fssc-expense:py.BaoXiaoTaiZhang')}
</p>
<table class="tb_normal" width=90%>
		<tr>
		    <!-- 费用归属公司  -->
			<td  width=10% class="td_normal_title" >
				<bean:message bundle="fssc-expense" key="fsscExpenseMain.fdCompany"/>
			</td>
			<td  width=23%">
				<xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" required="true"   subject="${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}" style="width:35%;">
					selectCompany();
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 成本中心 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-expense" key="fsscExpenseMain.fdCostCenter"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'selectType':'search'});
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 项目 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-expense" key="fsscExpenseMain.fdProject"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdProjectId" propertyName="fdProjectName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_project_project','fdProjectId','fdProjectName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':'1'});
				</xform:dialog>
			</td>
		</tr>
			<kmss:ifModuleExist path="/fssc/proapp">
				<tr>
				    <!-- 项目立项 -->
					<td  width=10% class="td_normal_title">
						<bean:message bundle="fssc-expense" key="fsscExpenseMain.fdProappName"/>
					</td>
					<td  width=23% >
						<xform:dialog propertyId="fdProappId" propertyName="fdProappName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdProappName')}" style="width:35%;">
							dialogSelect(false,'fssc_expense_main_selectProapp','fdProappId','fdProappName',null,{source:'ledger'});
						</xform:dialog>
					</td>
				</tr>
			</kmss:ifModuleExist>
			<tr>
		    <!-- 报销类别 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-expense" key="fsscExpenseMain.docTemplate"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseMain.docTemplate')}" style="width:35%;">
					selectCategory();					
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 费用类型 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-expense" key="fsscExpenseDetail.fdExpenseItem"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdExpenseItemId" propertyName="fdExpenseItemName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_expense_item_selectExpenseItem','fdExpenseItemId','fdExpenseItemName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'type':'all','fdCategoryId':$('[name=fdCategoryId]').val()});
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 填单人 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-expense" key="fsscExpenseMain.docCreator"/>
			</td>
			<td  width=23% >
				<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseMain.docCreator')}" style="width:35%;" onValueChange="changeFromName()">
				</xform:address>
				<span>
					<a href="javascript:selectInvalidCreator();">${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }</a>
				</span>
			</td>
		</tr>
		<tr>
		    <!-- 报销人 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-expense" key="fsscExpenseMain.fdClaimant"/>
			</td>
			<td  width=23% >
				<xform:address propertyId="fdClaimantId" propertyName="fdClaimantName" orgType="ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}" style="width:35%;" onValueChange="changeClaimantName()">
				</xform:address>
				<span>
					<a href="javascript:selectInvalidClaimant();">${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }</a>
				</span>
			</td>
		</tr>
		<tr>
		    <!-- 填单日期 -->
			<td width=10% class="td_normal_title">
				<bean:message bundle="fssc-expense" key="fsscExpenseMain.docCreateTime"/>
			</td>
			<td  width=23% >
				<xform:datetime property="docCreateTime1" dateTimeType="date" subject="${lfn:message('fssc-expense:fsscExpenseMain.docCreateTime')}" showStatus="edit" style="width: 28.5%"></xform:datetime>
				~
				<xform:datetime property="docCreateTime2" dateTimeType="date" subject="${lfn:message('fssc-expense:fsscExpenseMain.docCreateTime')}" showStatus="edit" style="width: 28.5%"></xform:datetime>
			 </td>
		</tr>
  </table>
<br>
<input type="button" class="bt" value="<bean:message key="button.search"/>" onclick="searchList('search');"/>
<input type="reset" class="bt" value="<bean:message key="button.reset" />" onclick="doReset()"/>
<input type="button" class="bt"value="<bean:message key="button.export"/>" onclick="exportResult();">
<input type="hidden" name="fdType" value="search"/>
</center>
	<script language="JavaScript">
	window.selectCompany=function(){
     	$("[name=fdCostCenterId]").val("");
    	$("[name=fdCostCenterName]").val("");
    	$("[name=fdProjectId]").val("");
    	$("[name=fdProjectName]").val("");
    	$("[name=fdCategoryId]").val("");
    	$("[name=fdCategoryName]").val("");
    	$("[name=fdExpenseItemId]").val("");
    	$("[name=fdExpenseItemName]").val("");
    	$("[name=fdProappId]").val("");
    	$("[name=fdProappName]").val("");
    	dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',false,null,function(data){
    		if(!data){
				return;
			}
			$("[name=fdCompanyId]").val(data[0].fdId);
			$("[name=fdCompanyName]").val(data[0].fdName);
    	});
    	 
	} 
	 seajs.use(['lui/dialog','lang!fssc-expense','lang!','lui/util/env'],function(dialog,lang,comlang,env){ 
        window. selectCategory=function(){
				dialog.simpleCategoryForNewFile(formOption.templateName, null,false,function(data){
					if(!data){
						return;
					}
					$("[name=fdCategoryId]").val(data.id);
					$("[name=fdCategoryName]").val(decodeURI(data.name));
				});	
        }
         
         window.searchList=function(param){
         	var fdCompanyName=$("[name=fdCompanyName]").val();
         	if(!fdCompanyName&&param=="search"){
         		dialog.alert(lang['tips.pleaseSelectCompany']);
         		return;
         	}
         	var url = '/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=listDetail';
         	url = Com_SetUrlParameter(url,'fdCompanyId',$("[name=fdCompanyId]").val());
         	url = Com_SetUrlParameter(url,'fdProappId',$("[name=fdProappId]").val());
         	url = Com_SetUrlParameter(url,'fdCostCenterId',$("[name=fdCostCenterId]").val());
         	url = Com_SetUrlParameter(url,'fdProjectId',$("[name=fdProjectId]").val());
         	url = Com_SetUrlParameter(url,'fdCategoryId',$("[name=fdCategoryId]").val());
         	url = Com_SetUrlParameter(url,'fdExpenseItemId',$("[name=fdExpenseItemId]").val());
         	url = Com_SetUrlParameter(url,'docCreatorId',$("[name=docCreatorId]").val());
         	url = Com_SetUrlParameter(url,'fdClaimantId',$("[name=fdClaimantId]").val());
         	url = Com_SetUrlParameter(url,'docCreateTime1',$("[name=docCreateTime1]").val());
         	url = Com_SetUrlParameter(url,'docCreateTime2',$("[name=docCreateTime2]").val());
         	LUI("listview").source.setUrl(url);
         	LUI("listview").source.get();
         }
        
        window.doReset=function(){
        	$("[name=fdCompanyId]").val("");
        	$("[name=fdCompanyName]").val("");
        	$("[name=fdCostCenterId]").val("");
        	$("[name=fdCostCenterName]").val("");
        	$("[name=fdProjectId]").val("");
        	$("[name=fdProjectName]").val("");
        	$("[name=fdCategoryId]").val("");
        	$("[name=fdCategoryName]").val("");
        	$("[name=fdExpenseItemId]").val("");
        	$("[name=fdExpenseItemName]").val("");
        	$("[name=docCreatorId]").val("");
        	$("[name=docCreatorName]").val("");
        	emptyNewAddress("docCreatorName",null,null,false);
        	$("[name=fdClaimantId]").val("");
        	$("[name=fdClaimantName]").val("");
        	emptyNewAddress("fdClaimantName",null,null,false);
        	$("[name=docCreateTime1]").val("");
        	$("[name=docCreateTime2]").val("");
        	$("[name=fdProappId]").val("");
        	$("[name=fdProappName]").val("");
        	searchList("doReset");
        }

        window.exportResult=function(){
        	var url = '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=exportDetail';
        	url = Com_SetUrlParameter(url,'fdCompanyId',$("[name=fdCompanyId]").val());
        	url = Com_SetUrlParameter(url,'fdCostCenterId',$("[name=fdCostCenterId]").val());
        	url = Com_SetUrlParameter(url,'fdProjectId',$("[name=fdProjectId]").val());
        	url = Com_SetUrlParameter(url,'fdCategoryId',$("[name=fdCategoryId]").val());
        	url = Com_SetUrlParameter(url,'fdExpenseItemId',$("[name=fdExpenseItemId]").val());
        	url = Com_SetUrlParameter(url,'docCreatorId',$("[name=docCreatorId]").val());
        	url = Com_SetUrlParameter(url,'fdClaimantId',$("[name=fdClaimantId]").val());
        	url = Com_SetUrlParameter(url,'docCreateTime1',$("[name=docCreateTime1]").val());
        	url = Com_SetUrlParameter(url,'docCreateTime2',$("[name=docCreateTime2]").val());
        	url = Com_SetUrlParameter(url,'fdProappId',$("[name=fdProappId]").val());
           	window.open(url);
        }
        
     	// 修改创建人
		window.changeFromName = function () {
			var fdFromName = $("input[name='docCreatorName']")[0].value;
			if (fdFromName == null || fdFromName == "") {
				showErrorMessage($("input[name='docCreatorName']")[0], "${ lfn:message('fssc-expense:fsscExpenseMain.docCreatorNotNull') }", true);
				return;
			}
			$($("input[name='docCreatorName']")[1]).val(fdFromName);
		}
		
		// 修改报销人
		window.changeClaimantName = function () {
			var fdClaimantName = $("input[name='fdClaimantName']")[0].value;
			if (fdClaimantName == null || fdClaimantName == "") {
				showErrorMessage($("input[name='fdClaimantName']")[0], "${ lfn:message('fssc-expense:fsscExpenseMain.fdClaimantNotNull') }", true);
				return;
			}
			$($("input[name='fdClaimantName']")[1]).val(fdClaimantName);
		}
		
		// 显示错误信息
		window.showErrorMessage = function (input, message, flag) {
			if ($("#validate_hand").length == 0) {
				var parent = $(input).parent().parent();
				if (flag)
					parent = parent.parent();
				//parent.append('<div class="validation-advice" id="validate_hand" _reminder="true"><table class="validation-table"><tbody><tr><td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td><td class="validation-advice-msg">' + message + '</td></tr></tbody></table></div>');
			}
		};
	})
	
		// 选择无效创建人员
		function selectInvalidCreator() {
			var _orgType = "${orgType}".replace("|", ",");
			Dialog_AddressList(false,'docCreatorId','docCreatorName',';','sysHandoverService&authCurrent=true&orgType='+_orgType,changeFromName,
			'sysHandoverService&authCurrent=true&orgType='+_orgType+'&keyword=!{keyword}',null,null,"${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }");
		}
	 
		// 选择无效报销人员
		function selectInvalidClaimant() {
			var _orgType = "${orgType}".replace("|", ",");
			Dialog_AddressList(false,'fdClaimantId','fdClaimantName',';','sysHandoverService&authCurrent=true&orgType='+_orgType,changeClaimantName,
			'sysHandoverService&authCurrent=true&orgType='+_orgType+'&keyword=!{keyword}',null,null,"${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }");
		}
	</script>
</html:form>
<br>
<div>
    <!-- 列表 -->
    <list:listview id="listview">
        <ui:source type="AjaxJson">
            {url:''}
        </ui:source>
        <!-- 列表视图 -->
        <list:colTable isDefault="false" rowHref="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=view&fdId=!{docMain.fdId}" name="columntable">
            <list:col-serial/>
            <kmss:ifModuleExist path="/fssc/proapp">
            	<c:set var="proappFlag" value="true" />
			</kmss:ifModuleExist>
			<c:choose>
			     <c:when test="${proappFlag == 'true'}">
			     	<list:col-auto props="docSubject;docNumber;fdCompany.fdName;docTemplate.fdName;fdProject.fdName;fdProappName;fdExpenseItem.fdName;fdApprovedStandardMoney;docStatus;docCreator.fdName;" />
			     </c:when>      
			     <c:otherwise>
			     	<list:col-auto props="docSubject;docNumber;fdCompany.fdName;docTemplate.fdName;fdProject.fdName;fdExpenseItem.fdName;fdApprovedStandardMoney;docStatus;docCreator.fdName;" />
			     </c:otherwise>
			</c:choose>
		</list:colTable>
    </list:listview>
    <!-- 翻页 -->
    <list:paging />
</div>
<style>
.bt{
		    background: none repeat scroll 0 0 #47b5e6;
		    border: 1px solid #35a1d0;
		    color: #fff;
		    cursor: pointer;
		    font-size: 14px;
		    height: 25px;
		    line-height: 25px;
		    margin-right: 14px;
		    text-align: center;
		    width: 60px;
		}
</style>
<script src="${LUI_ContextPath }/eop/basedata/resource/js/quickSelect.js">
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
