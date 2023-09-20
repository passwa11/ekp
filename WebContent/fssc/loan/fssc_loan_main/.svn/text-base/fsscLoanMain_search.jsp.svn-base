<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<title><bean:message bundle="fssc-loan" key="fsscLoanMain.search.title" /></title>
<%
	String orgType = "ORG_TYPE_POSTORPERSON";
	if("config".equals(request.getParameter("type"))) {
		orgType += "|ORG_TYPE_DEPT";
	}
	request.setAttribute("orgType", orgType);
%>
<script>
    Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_main/", 'js', true);
    Com_IncludeFile("doclist.js|dialog.js|calendar.js|jquery.js|validation.js");
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	Com_IncludeFile("plugin.js");   
</script>
<script>
//导出
function exportResult()
{
		var fdCompanyId = $('input[name="fdCompanyId"]').val();
		if(fdCompanyId==''){
	       alert('<bean:message bundle="fssc-loan" key="fsscLoanMain.search.company.isNull"/>');
	       return;
		}
		var form=document.forms[0];
		form.method.value="exportResult";
		document.getElementsByName("method_GET")[0].value ="exportResult";
		form.submit();
		form.method.value="loanSearchList";
}
</script>



<html:form action="/fssc/loan/fssc_loan_main/fsscLoanMain.do" method="get" target="searchIframe">

<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.close"/>"	onclick='window.close();'>
</div>
<center>
<p class="txttitle">
	<bean:message bundle="fssc-loan" key="fsscLoanMain.search.title" />
</p>
<table class="tb_normal" width=90%>
		<tr>
		    <!-- 费用归属公司  -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.fdCompany"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" required="true" showStatus="edit"  subject="${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}" style="width:35%;">
					oldFdCompanyId = $('[name=fdCompanyId]').val();
					dialogSelect(false,'eop_basedata_company_getCompanyByPerson','fdCompanyId','fdCompanyName',null,null,selectFdCompanyNameCallback);
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 成本中心 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.fdCostCenter"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}" style="width:35%;">
					dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'selectType':'search'});
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 分类模板 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.docTemplate"/>
			</td>
			<td  width=23% >
				<xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" showStatus="edit" style="width:35%;">
					selectCategory();					
				</xform:dialog>
			</td>
		</tr>
		<tr>
		    <!-- 借款金额(本位币)  -->
			<td width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.fdLoanMoney"/>
			</td>
			<td  width=23% >
				<select name="fdLoanMoneyType" onchange="refreshLogicDisplay(this, 'span_fdLoanMoney2');">
					<option value="eq"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.money.equal" /></option>
					<option value="sm"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.money.smallThan" /></option>
					<option value="ngt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.money.notGreaterThan" /></option>
					<option value="gt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.money.greaterThan" /></option>
					<option value="nsm"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.money.notSmallThan" /></option>
					<option value="bt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.money.between" /></option>
				</select>
				<input type="text" style="width:27.3%" name="fdLoanMoney" validate="currency-dollar min(0)" class="inputSgl">
				<span id="span_fdLoanMoney2" style="display:none">
					<bean:message bundle="fssc-loan" key="fsscLoanMain.search.between.leftStr" />
					<input type="text" style="width:35%" name="fdLoanMoney2" class="inputSgl">
					<bean:message bundle="fssc-loan" key="fsscLoanMain.search.between.rightStr" />
				</span>
			</td>
		</tr>
		<tr>
		    <!-- 填单日期 -->
			<td width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.docCreateTime"/>
			</td>
			<td  width=23% >
				<select name="docCreateTimeType" onchange="refreshLogicDisplay(this, 'span_docCreateTime2');">
					<option value="eq"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.equal" /></option>
					<option value="el"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.earlierThan" /></option>
					<option value="nlt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.notLaterThan" /></option>
					<option value="lt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.laterThan" /></option>
					<option value="nel"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.notEarlierThan" /></option>
					<option value="bt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.between" /></option>
				</select>
				<div class="inputselectsgl" onclick="selectDate('docCreateTime');" style="width: 28.5%">
					<div class="input">
						<input name="docCreateTime" type="text"  readonly="">
					</div>
					<div class="inputdatetime"></div>
				</div>
				<span id="span_docCreateTime2" style="display:none">
					<bean:message bundle="fssc-loan" key="fsscLoanMain.search.between.leftStr" />
					<div class="inputselectsgl" onclick="selectDate('docCreateTime2');" style="width: 28.5%">
						<div class="input">
							<input name="docCreateTime2" type="text"  readonly="">
						</div>
						<div class="inputdatetime"></div>
					</div>
					<bean:message bundle="fssc-loan" key="fsscLoanMain.search.between.rightStr" />
				</span>
			 </td>
		</tr>
		<tr>
		    <!-- 预计还款日期 -->
			<td width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.fdExpectedDate"/>
			</td>
			<td  width=23% >
				<select name="fdExpectedDateType" onchange="refreshLogicDisplay(this, 'span_fdExpectedDate2');">
					<option value="eq"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.equal" /></option>
					<option value="el"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.earlierThan" /></option>
					<option value="nlt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.notLaterThan" /></option>
					<option value="lt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.laterThan" /></option>
					<option value="nel"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.notEarlierThan" /></option>
					<option value="bt"><bean:message bundle="fssc-loan" key="fsscLoanMain.search.time.between" /></option>
				</select>
				<div class="inputselectsgl" onclick="selectDate('fdExpectedDate');" style="width: 28.5%">
					<div class="input">
						<input name="fdExpectedDate" type="text"  readonly="">
					</div>
					<div class="inputdatetime"></div>
				</div>
				
				<span id="span_fdExpectedDate2" style="display:none">
					<bean:message bundle="fssc-loan" key="fsscLoanMain.search.between.leftStr" />
					<div class="inputselectsgl" onclick="selectDate('fdExpectedDate2');" style="width: 28.5%">
						<div class="input">
							<input name="fdExpectedDate2" type="text"  readonly="">
						</div>
						<div class="inputdatetime"></div>
					</div>
					<bean:message bundle="fssc-loan" key="fsscLoanMain.search.between.rightStr" />
				</span>
			 </td>
		</tr>
		<tr>
		    <!-- 实际借款人 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.fdLoanPerson"/>
			</td>
			<td  width=23% >
				<xform:address propertyId="fdLoanPersonId" propertyName="fdLoanPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.search.fdLoanPerson')}" style="width:35%;" onValueChange="changeLoanPersonName">
				</xform:address>
				<span>
					<a href="javascript:selectInvalidLoanPerson();">${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }</a>
				</span>
			</td>
		</tr>
		<tr>
		    <!-- 负责人 -->
			<td  width=10% class="td_normal_title">
				<bean:message bundle="fssc-loan" key="fsscLoanMain.search.fdLoanChargePerson"/>
			</td>
			<td  width=23% >
				<xform:address propertyId="fdLoanChargePersonId" propertyName="fdLoanChargePersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" subject="${lfn:message('fssc-loan:fsscLoanMain.search.fdLoanChargePerson')}" style="width:35%;" onValueChange="changeLoanChargePerson">
				</xform:address>
				<span>
					<a href="javascript:selectInvalidChargePerson();">${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }</a>
				</span>
			</td>
		</tr>
  </table>
<br>
<input type="button" class="bt" value="<bean:message key="button.search"/>" onclick="searchList()"/>
<input type="reset" class="bt" value="<bean:message key="button.reset" />" onclick="doReset();"/>
<input type="button" class="bt"value="<bean:message key="button.export"/>" onclick="exportResult();">
<input type="hidden" name="method" value="loanSearchList"/>
<input type="hidden" name="fdType" value="search"/>
</center>
	<script language="JavaScript">
		function selectCategory(){
	    	seajs.use(['lui/dialog'],function(dialog){
				dialog.simpleCategoryForNewFile(formOption.templateName, null,false,function(data){
					if(!data){
						return;
					}
					$("[name=fdCategoryId]").val(data.id);
					$("[name=fdCategoryName]").val(decodeURI(data.name));
				});
			})
	    }
	
		seajs.use(['lui/dialog','lang!fssc-loan','lang!','lui/util/env'],function(dialog,lang,comlang,env){
			// 修改借款人
			window.changeLoanPersonName = function () {
				var fdLoanPersonName = $("input[name='fdLoanPersonName']")[0].value;
				if (fdLoanPersonName == null || fdLoanPersonName == "") {
					showErrorMessage($("input[name='fdLoanPersonName']")[0], "${ lfn:message('fssc-loan:fsscLoanMain.search.fdLoanPersonNotNull') }", true);
					return;
				}
				$($("input[name='fdLoanPersonName']")[1]).val(fdLoanPersonName);
			};
			
			// 修改转移接收人
			window.changeLoanChargePerson = function () {
				var fdChargePerson = $("input[name='fdLoanChargePersonName']")[0].value;
				if (fdChargePerson == null || fdChargePerson == "") {
					showErrorMessage($("input[name='fdLoanChargePersonName']")[0], "${ lfn:message('fssc-loan:fsscLoanMain.search.fdLoanChargePersonNotNull') }", true);
					return;
				}
				$($("input[name='fdLoanChargePersonName']")[1]).val(fdChargePerson);
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
		});
		
        function refreshLogicDisplay(obj, id){
            var spanObj = document.getElementById(id);
            if(obj.options[obj.selectedIndex].value=="bt")
                spanObj.style.display = "";
            else
                spanObj.style.display = "none";
        }

        var oldFdCompanyId;
        //选择公司回调函数
        function selectFdCompanyNameCallback(rtnData){
            if(oldFdCompanyId != $("input[name='fdCompanyId']").val()){//公司改动
                $("input[name='fdCostCenterId']").val("");//成本中心
                $("input[name='fdCostCenterName']").val("");
            }
        }
        

        function doReset(){
        	//清空选择值
        	$("[name='fdCompanyId']").val("");
        	$("[name='fdCostCenterId']").val("");
        	$("[name='fdCategoryId']").val("");
        	$("[name='fdLoanMoney']").val("");
        	$("[name='docCreateTime']").val("");
        	$("[name='fdExpectedDate']").val("");
        	$("[name='fdLoanPersonId']").val("");
        	$("[name='fdLoanChargePersonId']").val("");
            $('#searchIframe').attr("src","");
        }

        function searchList(){
      	  	$("input[name='fdType']").val("");
      		Com_SubmitNoEnabled(document.fsscLoanMainForm, 'loanSearchList')
      	}
        
        function exportResult(){
            $("input[name='fdType']").val("export");
            Com_SubmitNoEnabled(document.fsscLoanMainForm, 'loanSearchList');
        }
        
     	

     	// 选择无效借款人
		function selectInvalidLoanPerson() {
			var _orgType = "${orgType}".replace("|", ",");
			Dialog_AddressList(false,'fdLoanPersonId','fdLoanPersonName',';','sysHandoverService&authCurrent=true&orgType='+_orgType,changeLoanPersonName,
			'sysHandoverService&authCurrent=true&orgType='+_orgType+'&keyword=!{keyword}',null,null,"${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }");
		}
     	
		// 选择无效转移接收人
		function selectInvalidChargePerson() {
			var _orgType = "${orgType}".replace("|", ",");
			Dialog_AddressList(false,'fdLoanChargePersonId','fdLoanChargePersonName',';','sysHandoverService&authCurrent=true&orgType='+_orgType,changeLoanChargePerson,
			'sysHandoverService&authCurrent=true&orgType='+_orgType+'&keyword=!{keyword}',null,null,"${ lfn:message('sys-handover:sysHandoverConfigMain.select.invalid.organization') }");
		}
		
	</script>
</html:form>
<div>
    <iframe src="" name="searchIframe" id="searchIframe" align="top" onload="this.height=searchIframe.document.body.scrollHeight" width="97%"  Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No>
    </iframe>
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
