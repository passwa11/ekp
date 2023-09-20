<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<title>${ lfn:message('fssc-fee:py.listLedger')}</title>
<script>
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/fee/fssc_fee_main/", 'js', true);
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
<html:form action="/fssc/fee/fssc_fee_main/fsscFeeMain.do">
<center>
<p class="txttitle">
	${ lfn:message('fssc-fee:py.listLedger')}
</p>
<table class="tb_normal" width=90%>
		<tr>
		    <!-- 费用归属公司  -->
			<td  width=10% class="td_normal_title" >
				<bean:message bundle="fssc-fee" key="control.standardRule.company"/>
			</td>
			<td  width=23%">
				<xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit"  subject="${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}" style="width:35%;">
					selectCompany();
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 成本中心组 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-fee" key="fsscFeeMapp.fdCostGroup"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdCostCenterGroupId" propertyName="fdCostCenterGroupName" showStatus="edit" subject="${lfn:message('fssc-fee:control.budgetRule.costCenter')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_cost_center_selectCostCenterGroup','fdCostCenterGroupId','fdCostCenterGroupName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'selectType':'search'});
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 成本中心 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-fee" key="control.budgetRule.costCenter"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="edit" subject="${lfn:message('fssc-fee:control.budgetRule.costCenter')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'selectType':'search'});
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 项目 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-fee" key="control.budgetRule.project"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdProjectId" propertyName="fdProjectName" showStatus="edit" subject="${lfn:message('fssc-fee:control.budgetRule.project')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_project_project','fdProjectId','fdProjectName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':'1'});
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 费用类型 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-fee" key="control.budgetRule.expenseItem"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdExpenseItemId" propertyName="fdExpenseItemName" showStatus="edit" subject="${lfn:message('fssc-fee:control.budgetRule.expenseItem')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_expense_item_selectExpenseItem','fdExpenseItemId','fdExpenseItemName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'type':'all'});
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 填单人 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-fee" key="fsscFeeMain.docCreator"/>
			</td>
			<td  width=23% >
				<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('fssc-fee:fsscFeeMain.docCreator')}" style="width:35%;" onValueChange="changeFromName()">
				</xform:address>
				<span>
					<a href="javascript:selectInvalidCreator();">${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }</a>
				</span>
			</td>
		</tr>
		<tr>
		    <!-- 填单日期 -->
			<td width=10% class="td_normal_title">
				<bean:message bundle="fssc-fee" key="fsscFeeMain.docCreateTime"/>
			</td>
			<td  width=23% >
				<xform:datetime property="docCreateTime1" dateTimeType="date" subject="${lfn:message('fssc-fee:fsscFeeMain.docCreateTime')}" showStatus="edit" style="width: 28.5%"></xform:datetime>
				~
				<xform:datetime property="docCreateTime2" dateTimeType="date" subject="${lfn:message('fssc-fee:fsscFeeMain.docCreateTime')}" showStatus="edit" style="width: 28.5%"></xform:datetime>
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
    	dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',false,null,function(data){
    		if(!data){
				return;
			}
			$("[name=fdCompanyId]").val(data[0].fdId);
			$("[name=fdCompanyName]").val(data[0].fdName);
    	});
    	 
	} 
	 seajs.use(['lui/dialog','lang!fssc-fee','lang!','lui/util/env'],function(dialog,lang,comlang,env){ 
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
         	var url = '/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=listLedger';
         	url = Com_SetUrlParameter(url,'fdCompanyId',$("[name=fdCompanyId]").val());
         	url = Com_SetUrlParameter(url,'fdProappId',$("[name=fdProappId]").val());
         	url = Com_SetUrlParameter(url,'fdCostCenterId',$("[name=fdCostCenterId]").val());
         	url = Com_SetUrlParameter(url,'fdCostCenterGroupId',$("[name=fdCostCenterGroupId]").val());
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
			$("[name=fdCostCenterGroupId]").val("");
			$("[name=fdCostCenterGroupName]").val("");
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
        	var url = '${LUI_ContextPath}/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=exportLedger';
        	url = Com_SetUrlParameter(url,'fdCompanyId',$("[name=fdCompanyId]").val());
        	url = Com_SetUrlParameter(url,'fdCostCenterId',$("[name=fdCostCenterId]").val());
			url = Com_SetUrlParameter(url,'fdCostCenterGroupId',$("[name=fdCostCenterGroupId]").val());
        	url = Com_SetUrlParameter(url,'fdProjectId',$("[name=fdProjectId]").val());
        	url = Com_SetUrlParameter(url,'fdCategoryId',$("[name=fdCategoryId]").val());
        	url = Com_SetUrlParameter(url,'fdExpenseItemId',$("[name=fdExpenseItemId]").val());
        	url = Com_SetUrlParameter(url,'docCreatorId',$("[name=docCreatorId]").val());
        	url = Com_SetUrlParameter(url,'fdClaimantId',$("[name=fdClaimantId]").val());
        	url = Com_SetUrlParameter(url,'docCreateTime1',$("[name=docCreateTime1]").val());
        	url = Com_SetUrlParameter(url,'docCreateTime2',$("[name=docCreateTime2]").val());
           	window.open(url);
        }  
        
     	// 修改创建人
		window.changeFromName = function () {
			var fdFromName = $("input[name='docCreatorName']")[0].value;
			if (fdFromName == null || fdFromName == "") {
				showErrorMessage($("input[name='docCreatorName']")[0], "${ lfn:message('fssc-fee:fsscFeeMain.docCreatorNotNull') }", true);
				return;
			}
			$($("input[name='docCreatorName']")[1]).val(fdFromName);
		};
		
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
        <list:colTable isDefault="false" rowHref="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=view&fdId=!{docMain.fdId}" name="columntable">
            <list:col-serial/>
			<list:col-auto props="docSubject;docNumber;fdCompanyName;fdCostCenterGroupName;fdCostCenterName;fdProjectName;fdExpenseItemName;fdTotalMoney;fdUsedMoney;fdUsingMoney;fdUsableMoney" />
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
