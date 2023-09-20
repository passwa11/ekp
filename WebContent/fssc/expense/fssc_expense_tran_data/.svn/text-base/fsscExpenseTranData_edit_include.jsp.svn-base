<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseTranData') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdTranDataList_Form" align="center" tbdraggable="true">
    <tr align="center" class="tr_normal_title">
        <td style="width:20px;"></td>
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
        <td style="width:80px;">
        </td>
    </tr>
    <tr KMSS_IsReferRow="1" style="display:none;" class="docListTr">
        <td class="docList" align="center">
            <input type='checkbox' name='DocList_Selected' />
        </td>
        <td class="docList" align="center" KMSS_IsRowIndex="1">
            !{index}
        </td>
        <td class="docList" align="center">
            <%-- 卡号--%>
            <input type="hidden" name="fdTranDataList_Form[!{index}].fdId" value="" disabled="true" />
            <input type="hidden" name="fdTranDataList_Form[!{index}].fdTranDataId" value="" />
            <input type="hidden" name="fdTranDataList_Form[!{index}].fdState" value="" />
            <div id="_xform_fdTranDataList_Form[!{index}].fdCrdNum" _xform_type="text">
                <xform:text property="fdTranDataList_Form[!{index}].fdCrdNum" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdCrdNum')}" validators=" maxLength(19)" style="width:95%;" />
            </div>
        </td>
        <td class="docList" align="center">
            <%-- 持卡人中文名称--%>
            <div id="_xform_fdTranDataList_Form[!{index}].fdActChiNam" _xform_type="text">
                <xform:text property="fdTranDataList_Form[!{index}].fdActChiNam" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdActChiNam')}" validators=" maxLength(50)" style="width:95%;" />
            </div>
        </td>
        <td class="docList" align="center">
            <%-- 交易日期--%>
            <div id="_xform_fdTranDataList_Form[!{index}].fdTrsDte" _xform_type="datetime">
                <xform:datetime property="fdTranDataList_Form[!{index}].fdTrsDte" showStatus="readOnly" dateTimeType="date" style="width:95%;" />
            </div>
        </td>
        <td class="docList" align="center">
            <%-- 交易时间--%>
            <div id="_xform_fdTranDataList_Form[!{index}].fdTrxTim" _xform_type="text">
                <xform:text property="fdTranDataList_Form[!{index}].fdTrxTim" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdTrxTim')}" validators=" maxLength(8)" style="width:95%;" />
            </div>
        </td>
        <td class="docList" align="center">
            <%-- 交易金额--%>
            <div id="_xform_fdTranDataList_Form[!{index}].fdOriCurAmt" _xform_type="text">
                <xform:text property="fdTranDataList_Form[!{index}].fdOriCurAmt" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurAmt')}" validators=" number" style="width:95%;" />
            </div>
        </td>
        <td class="docList" align="center">
            <%-- 交易币种--%>
            <div id="_xform_fdTranDataList_Form[!{index}].fdOriCurCod" _xform_type="text">
                <xform:text property="fdTranDataList_Form[!{index}].fdOriCurCod" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurCod')}" validators=" maxLength(3)" style="width:95%;" />
            </div>
        </td>
        <td class="docList" align="center">
            <%-- 交易类型--%>
            <div id="_xform_fdTranDataList_Form[!{index}].fdTrsCod" _xform_type="select">
                <xform:select property="fdTranDataList_Form[!{index}].fdTrsCod" htmlElementProperties="id='fdTrsCod'" showStatus="readOnly">
                    <xform:enumsDataSource enumsType="fssc_tran_data_trsCod" />
                </xform:select>
            </div>
        </td>
        <td class="docList" align="center">
            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
            </a>
        </td>
    </tr>
    <c:forEach items="${fsscExpenseMainForm.fdTranDataList_Form}" var="fdTranDataList_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1" class="docListTr">
            <td class="docList" align="center">
                <input type="checkbox" name="DocList_Selected" />
            </td>
            <td class="docList" align="center">
                ${vstatus.index+1}
            </td>
            <td class="docList" align="center">
                <%-- 卡号--%>
                <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdId" value="${fdTranDataList_FormItem.fdId}" />
                <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdTranDataId" value="${fdTranDataList_FormItem.fdTranDataId}" />
                <input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdState" value="${fdTranDataList_FormItem.fdState}" />
                <div id="_xform_fdTranDataList_Form[${vstatus.index}].fdCrdNum" _xform_type="text">
                    <xform:text property="fdTranDataList_Form[${vstatus.index}].fdCrdNum" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdCrdNum')}" validators=" maxLength(19)" style="width:95%;" />
                </div>
            </td>
            <td class="docList" align="center">
                <%-- 持卡人中文名称--%>
                <div id="_xform_fdTranDataList_Form[${vstatus.index}].fdActChiNam" _xform_type="text">
                    <xform:text property="fdTranDataList_Form[${vstatus.index}].fdActChiNam" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdActChiNam')}" validators=" maxLength(50)" style="width:95%;" />
                </div>
            </td>
            <td class="docList" align="center">
                <%-- 交易日期--%>
                <div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrsDte" _xform_type="datetime">
                    <xform:datetime property="fdTranDataList_Form[${vstatus.index}].fdTrsDte" showStatus="readOnly" dateTimeType="date" style="width:95%;" />
                </div>
            </td>
            <td class="docList" align="center">
                <%-- 交易时间--%>
                <div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrxTim" _xform_type="text">
                    <xform:text property="fdTranDataList_Form[${vstatus.index}].fdTrxTim" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdTrxTim')}" validators=" maxLength(8)" style="width:95%;" />
                </div>
            </td>
            <td class="docList" align="center">
                <%-- 交易金额--%>
                <div id="_xform_fdTranDataList_Form[${vstatus.index}].fdOriCurAmt" _xform_type="text">
                    <xform:text property="fdTranDataList_Form[${vstatus.index}].fdOriCurAmt" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurAmt')}" validators=" number" style="width:95%;" />
                </div>
            </td>
            <td class="docList" align="center">
                <%-- 交易币种--%>
                <div id="_xform_fdTranDataList_Form[${vstatus.index}].fdOriCurCod" _xform_type="text">
                    <xform:text property="fdTranDataList_Form[${vstatus.index}].fdOriCurCod" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurCod')}" validators=" maxLength(3)" style="width:95%;" />
                </div>
            </td>
            <td class="docList" align="center">
                <%-- 交易类型--%>
                <div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrsCod" _xform_type="select">
                    <xform:select property="fdTranDataList_Form[${vstatus.index}].fdTrsCod" htmlElementProperties="id='fdTrsCod'" showStatus="readOnly">
                        <xform:enumsDataSource enumsType="fssc_tran_data_trsCod" />
                    </xform:select>
                </div>
            </td>
            <td class="docList" align="center">
                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</ui:content>
<br/>