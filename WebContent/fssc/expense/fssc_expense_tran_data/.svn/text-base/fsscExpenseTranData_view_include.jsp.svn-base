<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseTranData') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdTranDataList_Form" align="center" tbdraggable="true">
	<tr align="center" class="tr_normal_title">
		<td style="width:40px;">
			${lfn:message('page.serial')}
		</td>
		<td>
			${lfn:message('fssc-expense:fsscExpenseTranData.fdCrdNum')}
		</td>
		<td>
			${lfn:message('fssc-expense:fsscExpenseTranData.fdActChiNam')}
		</td>
		<td>
			${lfn:message('fssc-expense:fsscExpenseTranData.fdTrsDte')}
		</td>
		<td>
			${lfn:message('fssc-expense:fsscExpenseTranData.fdTrxTim')}
		</td>
		<td>
			${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurAmt')}
		</td>
		<td>
			${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurCod')}
		</td>
		<td>
			${lfn:message('fssc-expense:fsscExpenseTranData.fdTrsCod')}
		</td>
	</tr>
	<c:forEach items="${fsscExpenseMainForm.fdTranDataList_Form}" var="fdTranDataList_FormItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" class="docListTr">
			<td class="docList" align="center">
				${vstatus.index+1}
			</td>
			<td class="docList" align="center">
				<%-- 卡号--%>
				<input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdId" value="${fdTranDataList_FormItem.fdId}" />
				<input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdTranDataId" value="${fdTranDataList_FormItem.fdTranDataId}" />
				<input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdState" value="${fdTranDataList_FormItem.fdState}" />
				<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdCrdNum" _xform_type="text">
					<xform:text property="fdTranDataList_Form[${vstatus.index}].fdCrdNum" showStatus="view" style="width:95%;" />
				</div>
			</td>
			<td class="docList" align="center">
				<%-- 持卡人中文名称--%>
				<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdActChiNam" _xform_type="text">
					<xform:text property="fdTranDataList_Form[${vstatus.index}].fdActChiNam" showStatus="view" style="width:95%;" />
				</div>
			</td>
			<td class="docList" align="center">
				<%-- 交易日期--%>
				<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrsDte" _xform_type="datetime">
					<xform:datetime property="fdTranDataList_Form[${vstatus.index}].fdTrsDte" showStatus="view" dateTimeType="date" style="width:95%;" />
				</div>
			</td>
			<td class="docList" align="center">
				<%-- 交易时间--%>
				<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrxTim" _xform_type="text">
					<xform:text property="fdTranDataList_Form[${vstatus.index}].fdTrxTim" showStatus="view" style="width:95%;" />
				</div>
			</td>
			<td class="docList" align="center">
				<%-- 交易金额--%>
				<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdOriCurAmt" _xform_type="text">
					<xform:text property="fdTranDataList_Form[${vstatus.index}].fdOriCurAmt" showStatus="view" style="width:95%;" />
				</div>
			</td>
			<td class="docList" align="center">
				<%-- 交易币种--%>
				<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdOriCurCod" _xform_type="text">
					<xform:text property="fdTranDataList_Form[${vstatus.index}].fdOriCurCod" showStatus="view" style="width:95%;" />
				</div>
			</td>
			<td class="docList" align="center">
				<%-- 交易类型--%>
				<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrsCod" _xform_type="select">
					<xform:select property="fdTranDataList_Form[${vstatus.index}].fdTrsCod" htmlElementProperties="id='fdTrsCod'" showStatus="view">
						<xform:enumsDataSource enumsType="fssc_tran_data_trsCod" />
					</xform:select>
				</div>
			</td>
		</tr>
	</c:forEach>
</table>
</ui:content>
<br/>