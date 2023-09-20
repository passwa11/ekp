<%@ page import="com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService" %>
<%@ page import="com.landray.kmss.util.ArrayUtil" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.landray.kmss.eop.basedata.model.EopBasedataCompany" %>
<%@ page import="com.landray.kmss.common.model.IBaseModel" %>
<%@ page import="org.apache.commons.beanutils.PropertyUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String fdModelId = request.getParameter("fdModelId");
	String fdModelName = request.getParameter("fdModelName");
	IFsscVoucherMainService fsscVoucherMainService = (IFsscVoucherMainService) SpringBeanUtil.getBean("fsscVoucherMainService");
	List<Map<String, Object>> voucherList = fsscVoucherMainService.getVoucherInfo(fdModelId, fdModelName);
	if(ArrayUtil.isEmpty(voucherList)){
		%>
			<%--<%@ include file="/resource/jsp/list_norecord.jsp"%>--%>
			<script>
				//如果没值就隐藏
				setTimeout(function(){
					$("div .lui_tabpage_float_nav_item_c").each(function () {
						if("${lfn:message('fssc-voucher:fsscVoucherMain.title.message')}" == $(this).html()){
							$(this).parent().parent().parent().hide();
						}
					});
					//右侧页签显示
					$("div .lui_tabpanel_sucktop_navs_item_c").each(function () {
						if("${lfn:message('fssc-voucher:fsscVoucherMain.title.message')}" == $(this).find("span").html()){
							$(this).parent().parent().hide();
						}
					});
				}, 1000);
			</script>
		<%
	}else{
		request.setAttribute("voucherList", voucherList);
		IBaseModel baseModel = fsscVoucherMainService.findByPrimaryKey(fdModelId, fdModelName, true);
		if(PropertyUtils.isWriteable(baseModel, "fdCompany")){
			EopBasedataCompany company = (EopBasedataCompany) PropertyUtils.getProperty(baseModel, "fdCompany");
			if(null!= company){
				//true 勾选了SAP
				request.setAttribute("fdIsChechedSAP", "SAP".equals(company.getFdJoinSystem())?true:false);
			}
		}
		%>
<c:forEach items="${voucherList}" var="voucherInfo" varStatus="vstatus">
	<table class="tb_normal" width="100%">
		<tr>
			<td colspan="6">
				${lfn:message('fssc-voucher:fsscVoucherMain.di')}${vstatus.index+1}${lfn:message('fssc-voucher:fsscVoucherMain.di.end')}
				&nbsp;&nbsp;&nbsp;
				<c:if test="${voucherInfo['fdBookkeepingStatus'] != '30'}">
					<input type="button" class="newbutton" value="<bean:message bundle="fssc-voucher" key="button.edit.voucher" />" onclick="Com_OpenWindow('${LUI_ContextPath}/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=edit&fdId=${voucherInfo['fdId']}','_blank');">
					&nbsp;
					<kmss:auth requestURL="/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=add&fdId=">
						<input type="button" class="newbutton" value=' <bean:message bundle="fssc-voucher" key="button.add.voucher" />' onclick="addVoucher()">
					</kmss:auth>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.docFinanceNumber')}
			</td>
			<td width="16.6%">
				<%-- 财务凭证号--%>
				<c:out value="${voucherInfo['docFinanceNumber']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.docNumber')}
			</td>
			<td width="16.6%">
				<%-- 费控凭证号--%>
				<c:out value="${voucherInfo['docNumber']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdModelNumber')}
			</td>
			<td width="16.6%">
				<%-- 来源单据编号--%>
				<c:if test="${not empty voucherInfo['fdModelNumber']}" >
					<a href="${LUI_ContextPath}${voucherInfo['fdModelUrl']}" target="_blank">${voucherInfo['fdModelNumber']}</a>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseVoucherType')}
			</td>
			<td width="16.6%">
				<%-- 凭证类型--%>
				<c:out value="${voucherInfo['fdBaseVoucherTypeName']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdVoucherDate')}
			</td>
			<td width="16.6%">
				<%-- 凭证日期--%>
				<c:out value="${voucherInfo['fdVoucherDate']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingDate')}
			</td>
			<td width="16.6%">
				<%-- 记账日期--%>
				<c:out value="${voucherInfo['fdBookkeepingDate']}" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdAccountingYear')}
			</td>
			<td width="16.6%">
				<%-- 会计年度--%>
				<c:out value="${voucherInfo['fdAccountingYear']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdPeriod')}
			</td>
			<td width="16.6%">
				<%-- 期间--%>
				<c:out value="${voucherInfo['fdPeriod']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdBaseCurrency')}
			</td>
			<td width="16.6%">
				<%-- 凭证货币--%>
				<c:out value="${voucherInfo['fdBaseCurrencyName']}" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdCompanyName')}
			</td>
			<td width="16.6%">
				<%-- 公司--%>
				<c:out value="${voucherInfo['fdCompanyName']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdCompanyCode')}
			</td>
			<td width="16.6%">
				<%-- 公司编号--%>
				<c:out value="${voucherInfo['fdCompanyCode']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdNumber')}
			</td>
			<td width="16.6%">
				<%-- 单据数--%>
				<c:out value="${voucherInfo['fdNumber']}" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdVoucherText')}
			</td>
			<td width="16.6%">
				<%-- 凭证抬头文本--%>
				<c:out value="${voucherInfo['fdVoucherText']}" />
			</td>
			<td class="td_normal_title" width="16.6%">
					${lfn:message('fssc-voucher:fsscVoucherMain.fdPushType')}
			</td>
			<td width="16.6%">
				<%-- 推送方式--%>
				<sunbor:enumsShow value="${voucherInfo['fdPushType']}" enumsType="fssc_voucher_fd_push_type" />
			</td>
			<td class="td_normal_title" width="16.6%">
                  ${lfn:message('fssc-voucher:fsscVoucherRuleConfig.fdMergeEntry')}
            </td>
			<td width="16.6%">
				<%-- 合并分录--%>
				<sunbor:enumsShow value="${voucherInfo['fdMergeEntry']}" enumsType="fssc_voucher_fd_merge_entry" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingStatus')}
			</td>
			<td colspan="5" width="16.6%">
				<%-- 记账状态--%>
				<sunbor:enumsShow value="${voucherInfo['fdBookkeepingStatus']}" enumsType="fssc_voucher_fd_bookkeeping_status" />
			</td>
		</tr>
		<c:if test="${voucherInfo['fdBookkeepingStatus'] == '11'}">
			<tr>
				<td class="td_normal_title" width="16.6%">
					${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingMessage')}
				</td>
				<td colspan="5" width="16.6%">
					<%-- 记账失败原因--%>
					<c:out value="${voucherInfo['fdBookkeepingMessage']}" />
				</td>
			</tr>
		</c:if>
		<tr>
			<td colspan="6" width="100%">
				<span class="lui_icon_s lui_icon_s_icon_18"></span>${ lfn:message('fssc-voucher:fsscVoucherMain.fd_detail') }
			</td>
		</tr>
		<tr>
			<td colspan="6" width="100%">
				<div style="width:100%; max-height:250px; overflow-x:scroll;overflow-y:auto;">
					<table id="List_ViewTable" class="tb_normal" width="100%;">
						<tr align="center" class="tr_normal_title">
							<td style="width:40px;">${lfn:message('fssc-voucher:fsscVoucherDetail.fdOrder')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdType')}	</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseAccountsCode')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseAccountsName')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCostCenter')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseErpPerson')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCashFlow')}	</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCustomer')}	</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseSupplier')}	</td>
							<td class="fdBaseWbs" style="display: none;">${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseWbs')}</td>
							<td class="fdBaseInnerOrder" style="display: none;">${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseInnerOrder')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseProject')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdBasePayBank')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdContractCode')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdDept')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdMoney')}</td>
							<td>${lfn:message('fssc-voucher:fsscVoucherDetail.fdVoucherText')}</td>
						</tr>
						<c:forEach items="${voucherInfo['voucherDetailList']}" var="voucherDetail" varStatus="vstatus">
							<tr>
								<td>${vstatus.index+1}</td>
								<td><sunbor:enumsShow value="${voucherDetail['fdType']}" enumsType="fssc_voucher_fd_type" /></td>
								<td><c:out value="${voucherDetail['fdAccountsCode']}" /></td>
								<td><c:out value="${voucherDetail['fdAccountsName']}" /></td>
								<td><c:out value="${voucherDetail['fdCostCenterName']}" /></td>
								<td><c:out value="${voucherDetail['fdBaseErpPersonName']}" /></td>
								<td><c:out value="${voucherDetail['fdBaseCashFlowName']}" /></td>
								<td><c:out value="${voucherDetail['fdBaseCustomerName']}" /></td>
								<td><c:out value="${voucherDetail['fdBaseSupplierName']}" /></td>
								<td class="fdBaseWbs" style="display: none;"><c:out value="${voucherDetail['fdBaseWbsName']}" /></td>
								<td class="fdBaseInnerOrder" style="display: none;"><c:out value="${voucherDetail['fdBaseInnerOrderName']}" /></td>
								<td><c:out value="${voucherDetail['fdBaseProjectName']}" /></td>
								<td><c:out value="${voucherDetail['fdBasePayBankName']}" /></td>
								<td><c:out value="${voucherDetail['fdContractCode']}" /></td>
								<td><c:out value="${voucherDetail['fdDeptName']}" /></td>
								<td><fmt:formatNumber value="${voucherDetail['fdMoney']}" type="number"	pattern="####.##" minFractionDigits="2" /></td>
								<td><c:out value="${voucherDetail['fdVoucherText']}" /></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</td>
		</tr>
	</table>
	<table border="0px" width="100%">
	<tr>
		<td colspan="6" align="right">
			<%--制单人--%>
			${lfn:message('fssc-voucher:fsscVoucherMain.docCreatorName')}:${voucherInfo['docCreatorName']}&nbsp;&nbsp;
			<%--记账人--%>
			${lfn:message('fssc-voucher:fsscVoucherMain.fdBookkeepingPerson')}:${voucherInfo['fdBookkeepingPersonName']}&nbsp;&nbsp;
		</td>
	</tr>
	</table>
	<br/>
	<br/>
</c:forEach>
<style>
	.newbutton {
		background-color:rgb(71, 181, 230);
		border-collapse:collapse;
		border-spacing:0;
		color:#fff;
		cursor:pointer;
		display:inline-block;
		font-size:12px;
		line-height:25px;
		margin:0px 5px 0px 0px;
		min-width:50px;
		padding: 0px 10px 0px 10px;
		text-align:center;
		vertical-align:top;
		white-space:nowrap;
	}
</style>
<script>
	$(function () {
	    //初始化SAP字段
		var fdIsChechedSAP = '${fdIsChechedSAP}';
		if(fdIsChechedSAP == 'true'){
            $(".fdBaseWbs").show();
            $(".fdBaseInnerOrder").show();
		}else{
            $(".fdBaseWbs").hide();
            $(".fdBaseInnerOrder").hide();
		}
    });

    <c:if test="${param.fdIsVoucherVariant=='true'}">
		/*******************************************
		 * voucherVariant附加选项节点，未记账不允许提交
		 *******************************************/
		Com_Parameter.event["submit"].push(function(){ // 通过submit队列来添加校验函数，这样校验失败，会终止表单提交。
			if('${param.fdBookkeepingStatus}' != "30"){
				var oprGroup = $("input[name='oprGroup']:checked").val();
				var outerText=window.event.target.outerText;
				if("${ lfn:message('sys-lbpmservice:button.saveFormData') }"==outerText){
					return true;
				}
				//handler_pass:通过
				if(oprGroup && (oprGroup.indexOf("handler_pass")>-1)){
					seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
						//未记账不允许提交！
						dialog.alert("${lfn:message('fssc-voucher:no.bookkeeping.error')}");
					});
					return false;
				}
			}
			return true;
		});
    </c:if>

    //新增制证
    function addVoucher() {
        var fdModelId = '${param.fdModelId}';
        var fdModelName = '${param.fdModelName}';
        var fdModelNumber = '${param.fdModelNumber}';
        window.open('${LUI_ContextPath}/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=addVoucher&fdModelId='+fdModelId+'&fdModelName='+fdModelName+'&fdModelNumber='+fdModelNumber+"&fdPushType=1");
    }

    //记账
    function bookkeeping(){
        var fdModelId = '${param.fdModelId}';
        var fdModelName = '${param.fdModelName}';
        if(!fdModelId || !fdModelName){
            return;
        }
        seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
            dialog.confirm('${ lfn:message("fssc-voucher:button.bookkeeping.confirm") }', function(isOk) {
                if(isOk) {
                    var del_load = dialog.loading("${lfn:message('fssc-voucher:bookkeeping.loading')}");
                    $.post('${LUI_ContextPath}/fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=bookkeeping&fdId=${fdModelId}',
                        $.param({"fdModelId":fdModelId,"fdModelName":fdModelName},true),function(data){
                            if(del_load != null){
                                del_load.hide();
                            }
                            var fdIsBoolean = data.fdIsBoolean;//true 记账成功 false 记账失败
                            //记账成功
                            if(fdIsBoolean){
                                setTimeout(function(){
                                    window.location.reload();//刷新页面
                                },1000);
                            }
                            var messageStr = data.message;
                            if(messageStr && messageStr.length > 0){
                                dialog.alert(messageStr);
                            }
                        },'json');
                }
            });
        });
    }


</script>
		<%
	}
%>
<script>
    //重新制证
    function refreshVoucher() {
        var fdModelId = '${param.fdModelId}';
        var fdModelName = '${param.fdModelName}';
        seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
            dialog.confirm('${lfn:message("fssc-voucher:button.refresh.voucher.confirm") }', function(isOk) {
                if(isOk) {
                    var params={"fdModelId":fdModelId,"fdModelName":fdModelName};
                    $.ajax( {
                    	url: "${KMSS_Parameter_ContextPath}fssc/voucher/fssc_voucher_main/fsscVoucherMain.do?method=refreshVoucher&fdId="+fdModelId+"&fdModelName="+fdModelName,  //权限校验需要这两个参数
                        type: 'POST', // POST or GET
                        dataType:"json",
                        data:params,
                        async:false,    //用同步方式   async. 默认是true，即为异步方式
                        success:function(data){
                            if(data.fdIsBoolean == "true"){
                                dialog.success("${lfn:message('return.optSuccess')}");
                                setTimeout(function(){
                                    window.location.reload();//刷新页面
                                },2500);
                            }else{
                                dialog.alert("${lfn:message('fssc-voucher:button.refresh.voucher.error.message')}".replace("%text%", data.messageStr))
                            }
                        }
                    });
                }
            });
        });
    }
</script>
